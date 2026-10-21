package com.one_half_men.app.request;

import io.avaje.jsonb.Json;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Json
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CloseReason {
    private CloseReason.Type type;
    private String message;

    public static enum Type {
        RESOLVED,
        RETRACTED;
    }

    public static CloseReason resolved(String message) {
        return new CloseReason(Type.RESOLVED, message);
    }

    public static CloseReason retracted(String message) {
        return new CloseReason(Type.RETRACTED, message);
    }
}
