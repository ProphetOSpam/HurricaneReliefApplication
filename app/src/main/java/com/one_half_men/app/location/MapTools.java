package com.one_half_men.app.location;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import io.avaje.json.node.JsonArray;
import io.avaje.json.node.JsonNode;
import io.avaje.json.node.JsonNodeMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Pelias & OpenRouteService API tools via api.heigit.org
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapTools {
        private static final String GEOCODING_URL = "https://api.heigit.org/pelias/v1/search";
        private static final String REVERSE_URL = "https://api.heigit.org/pelias/v1/reverse";

        private static final String DIRECTIONS_URL = "https://api.heigit.org/openrouteservice/v2/directions/driving-car";

        private static final String ROUTE_GEOJSON_URL = "https://api.heigit.org/openrouteservice/v2/directions/driving-car/geojson";

        private static final String apiKey = System.getenv("MAP_API_KEY");
        private static final HttpClient httpClient = HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();
        private static final JsonNodeMapper objectMapper = JsonNodeMapper.builder().build();

        /**
         * Converts a street address into latitude and longitude.
         * 
         * @param address Street address
         * @return Coordinates in latitude and longitude
         * @throws IOException
         * @throws InterruptedException
         */
        public static Coordinates estimateCoordinates(Address address)
                        throws IOException, InterruptedException {

                checkForApiKey();

                String encodedAddress = URLEncoder.encode(
                                address.toText(),
                                StandardCharsets.UTF_8);

                URI uri = URI.create(
                                GEOCODING_URL
                                                + "?api_key=" + apiKey
                                                + "&text=" + encodedAddress
                                                + "&boundary.country=USA"
                                                + "&size=10");

                HttpRequest request = HttpRequest.newBuilder(uri)
                                .header("Accept", "application/json")
                                .timeout(Duration.ofSeconds(15))
                                .GET()
                                .build();

                HttpResponse<String> response = httpClient.send(
                                request,
                                HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                        throw new IOException(
                                        "Could not convert: " + address
                                                        + " (HTTP " + response.statusCode() + ")"
                                                        + "\n" + response.body());
                }

                JsonArray features = (JsonArray) objectMapper
                                .fromJsonObject(response.body())
                                .get("features");

                JsonNode bestResult = findBestCoordinates(features);

                return readCoordinates(bestResult);
        }

        public static Optional<State> estimateState(Coordinates coordinates)
                        throws IOException, InterruptedException {

                checkForApiKey();

                URI uri = URI.create(
                                REVERSE_URL
                                                + "?api_key=" + apiKey
                                                + "&point.lat=" + coordinates.getLatitude()
                                                + "&point.lon=" + coordinates.getLongitude()
                                                + "&boundary.country=USA"
                                                + "&size=10");

                HttpRequest request = HttpRequest.newBuilder(uri)
                                .header("Accept", "application/json")
                                .timeout(Duration.ofSeconds(15))
                                .GET()
                                .build();

                HttpResponse<String> response = httpClient.send(
                                request,
                                HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                        throw new IOException(
                                        "Could not convert: " + coordinates
                                                        + " (HTTP " + response.statusCode() + ")"
                                                        + "\n" + response.body());
                }

                JsonArray features = (JsonArray) objectMapper
                                .fromJsonObject(response.body())
                                .get("features");

                String bestResult = findBestState(features);
                return State.fromAbbreviation(bestResult);
        }

        /**
         * Finds the shelter with the shortest driving time.
         * 
         * @param start   The location we are starting from
         * @param options The list of addresses we are choosing from
         * @return The address of the closest shelter
         * @throws IOException
         * @throws InterruptedException
         */
        public static Location findClosestLocation(Location start, Location[] options)
                        throws IOException, InterruptedException {

                Coordinates startingCoordinates = start.coordinates();

                Location closest = null;
                double shortestTime = Double.MAX_VALUE;

                for (Location shelter : options) {
                        Coordinates shelterCoordinates = shelter.coordinates();

                        double drivingTime = getDrivingTime(
                                        startingCoordinates,
                                        shelterCoordinates);

                        if (drivingTime < shortestTime) {
                                shortestTime = drivingTime;
                                closest = shelter;
                        }
                }

                return closest;
        }

        /**
         * Collects the driving directions to the shelter
         * 
         * @param start The place the route starts at
         * @param end   The place the route ends at
         * @return A string array where each item represents a direction on the route
         * @throws IOException
         * @throws InterruptedException
         */
        public static List<String> getDrivingDirections(
                        Location start,
                        Location end) throws IOException, InterruptedException {

                JsonNode route = getRoute(start.coordinates(), end.coordinates());
                JsonArray segments = (JsonArray) route.extractNode("segments");
                JsonArray steps = (JsonArray) segments.elements().getFirst().extractNode("steps");

                return steps.stream()
                                .map(node -> node.extract("instruction"))
                                .toList();
        }

        /**
         * Finds the best version of a coordinate. Based on the address we might not
         * be able to find the exact house, but maybe the city
         * 
         * @param features The information about the street address
         * @return The node representation of the closest address
         */
        private static JsonNode findBestCoordinates(JsonArray features) {
                for (JsonNode result : features.elements()) {
                        String layer = result.extractNode("properties").extractNode("layer").text();

                        if (layer.equals("address")) {
                                return result;
                        }
                }

                return features.elements().get(0);
        }

        private static String findBestState(JsonArray features) {
                for (JsonNode result : features.elements()) {
                        String layer = result.extractNode("properties").extractNode("layer").text();

                        if (layer.equals("address")) {
                                return result.extractNode("properties").extract("region_a");
                        }
                }

                return features.elements().get(0).extractNode("properties").extract("region_a");
        }

        /**
         * Covert a node into a coordinate object
         * 
         * @param result The node representing the location
         * @return The coordinates of the node in latitude and longitude
         * @throws IOException
         */
        private static Coordinates readCoordinates(JsonNode result) throws IOException {
                JsonArray coordinates = (JsonArray) result.extractNode("geometry").extractNode("coordinates");

                if (coordinates.size() < 2) {
                        throw new IOException("Coordinates were missing.");
                }

                double longitude = (Double) coordinates.elements().get(0).toPlain();
                double latitude = (Double) coordinates.elements().get(1).toPlain();

                return new Coordinates(latitude, longitude);
        }

        /**
         * Calculates the driving time in seconds between two coordinates
         * 
         * @param start Starting location
         * @param end   Ending location
         * @return The number of seconds
         * @throws IOException
         * @throws InterruptedException
         */
        public static double getDrivingTime(Coordinates start, Coordinates end)
                        throws IOException, InterruptedException {

                JsonNode route = getRoute(start, end);

                return (Double) route.extractNode("summary").extractNode("duration").toPlain();
        }

        /**
         * Calculates the route information from one location to the next
         * 
         * @param start Starting location
         * @param end   Ending location
         * @return A json node representing the route.
         * @throws IOException
         * @throws InterruptedException
         */
        private static JsonNode getRoute(Coordinates start, Coordinates end)
                        throws IOException, InterruptedException {

                String requestBody = "{ \"coordinates\": [[" +
                                start.getLongitude() + "," + start.getLatitude() +
                                "],[" +
                                end.getLongitude() + "," + end.getLatitude() +
                                "]], \"instructions\": true, \"language\": \"en\" }";

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(DIRECTIONS_URL))
                                .header("Authorization", apiKey)
                                .header("Content-Type", "application/json")
                                .timeout(Duration.ofSeconds(15))
                                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                                .build();

                HttpResponse<String> response = httpClient.send(
                                request,
                                HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                        throw new IOException("Could not get driving directions.");
                }

                JsonArray routes = (JsonArray) objectMapper
                                .fromJson(response.body())
                                .extractNode("routes");
                return routes.elements().getFirst();
        }

        /**
         * Determines the route from one address to another
         * and returns it to show on a map.
         *
         * @param start The address the route begins at
         * @param end   The address the route ends on
         * @return The Driving route to be plotted on a map
         * @throws IOException
         * @throws InterruptedException
         */
        public DrivingRoute getDrivingRoute(Location start, Location end) throws IOException, InterruptedException {
                List<Coordinates> routePoints = getRoutePoints(
                                start.coordinates(),
                                end.coordinates());

                return new DrivingRoute(start, end, routePoints);
        }

        /**
         * Determines all the points to show on the route to be plotted on a map
         * 
         * @param start The address to begin on as a coordinate
         * @param end   The address to end at as a coordinate
         * @return A list of all points to be put on the route
         * @throws IOException
         * @throws InterruptedException
         */
        private static List<Coordinates> getRoutePoints(Coordinates start, Coordinates end)
                        throws IOException, InterruptedException {

                String requestBody = "{ \"coordinates\": [[" +
                                start.getLongitude() + "," + start.getLatitude() +
                                "],[" +
                                end.getLongitude() + "," + end.getLatitude() +
                                "]]}";

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(ROUTE_GEOJSON_URL))
                                .header("Authorization", apiKey)
                                .header("Content-Type", "application/json")
                                .timeout(Duration.ofSeconds(15))
                                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                                .build();

                HttpResponse<String> response = httpClient.send(
                                request,
                                HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                        throw new IOException("Could not get the driving route.");
                }

                JsonArray features = (JsonArray) objectMapper
                                .fromJson(response.body())
                                .extractNode("features");

                JsonArray points = (JsonArray) features.elements()
                                .getFirst()
                                .extractNode("geometry")
                                .extractNode("coordinates");

                return points.stream()
                                .map(point -> {
                                        JsonArray array = (JsonArray) point;
                                        double longitude = (Double) array.elements().get(0).toPlain();
                                        double latitude = (Double) array.elements().get(1).toPlain();

                                        return new Coordinates(latitude, longitude);
                                })
                                .toList();
        }

        /**
         * Determine if we have a valid API key in our environment variable.
         */
        private static void checkForApiKey() {
                if (apiKey == null || apiKey.isBlank()) {
                        throw new IllegalStateException(
                                        "MAP_API_KEY environment variable has not been set.");
                }
        }
}
