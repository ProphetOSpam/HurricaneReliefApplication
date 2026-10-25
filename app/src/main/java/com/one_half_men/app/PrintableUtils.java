package com.one_half_men.app;

import java.io.IOException;
import java.util.List;

import com.one_half_men.app.location.Location;
import com.one_half_men.app.location.MapTools;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.shelter.Shelter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrintableUtils {
    public static String fullRoute(Request request, Location start, Shelter shelter) {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(String.format(
                    "Shelter:\n- Name: %s\n - Location: %s\n",
                    shelter.getName(),
                    shelter.getLocation().toText()));

            sb.append(String.format(
                    "Family:\n- %s\n- %s\n",
                    request.getAuthor(),
                    String.join(
                            "\n- ",
                            request.getBeings().stream().map(being -> being.toText()).toList())));

            sb.append(String.format(
                    "Current Location to Request Route:\n%s\n",
                    String.join("\n- ", MapTools.getDrivingDirections(start, request.getLocation()))));

            sb.append(String.format(
                    "Request to Shelter Route:\n%s\n",
                    String.join("\n- ", MapTools.getDrivingDirections(request.getLocation(), shelter.getLocation()))));

            return sb.toString();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
