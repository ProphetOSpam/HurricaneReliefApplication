package com.one_half_men.app.location;

import java.io.IOException;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

public interface Location {
    public static void main(String[] args) throws ApiException, InterruptedException, IOException {
        GeoApiContext context = new GeoApiContext.Builder()
            .apiKey("AIzaSyAdwzvHxWCaJiv3pWUPrjD2bPGBg1XRPaI")
            .build();
        GeocodingResult[] result = GeocodingApi.geocode(context, "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
        System.out.println(result);
    }
}
