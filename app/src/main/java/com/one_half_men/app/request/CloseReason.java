package com.one_half_men.app.request;

import io.avaje.jsonb.Json;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Json
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CloseReason {
    private final CloseReason.Type type;
    private final String message;

    public static enum Type {
        RESOLVED,
        RETRACTED;
    }

    /**
     * @param message Message to be enclosed into the close reason
     *
     * @return A resolved {@link #CloseReason(CloseReason.Type, String)}
     *         with a message
     */
    public static CloseReason resolved(String message) {
        return new CloseReason(Type.RESOLVED, message);
    }

    /**
     * @param message Message to be enclosed into the close reason
     *
     * @return A retracted {@link #CloseReason(CloseReason.Type, String)}
     *         with a message
     */
    public static CloseReason retracted(String message) {
        return new CloseReason(Type.RETRACTED, message);
    }
}
