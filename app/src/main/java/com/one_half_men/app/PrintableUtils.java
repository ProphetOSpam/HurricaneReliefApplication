package com.one_half_men.app;

import java.io.IOException;

import com.one_half_men.app.location.Location;
import com.one_half_men.app.location.MapTools;
import com.one_half_men.app.request.Request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrintableUtils {
    public String fullRoute(Request request, Location start, Location end) {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(String.format(
                    "Family:\n- %s\n- %s\n",
                    request.getAuthor(), String.join(
                            "\n- ",
                            request.getBeings().stream().map(being -> being.toString()).toList())));

            sb.append(String.format(
                    "Route:\n%s\n",
                    String.join("\n- ", MapTools.getDrivingDirections(start, end))));

            return sb.toString();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
