package com.one_half_men.app.request;

public class CloseReason {
    private CloseReason.Type type;
    private String message;

    public static enum Type {
        RESOLVED,
        RETRACTED;
    }

    private CloseReason(CloseReason.Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public static CloseReason resolved(String message) {
        return null;
    }

    public static CloseReason retracted(String message) {
        return null;
    }
}
