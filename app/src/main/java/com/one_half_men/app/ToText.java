package com.one_half_men.app;

/**
 * Common interface meant for human-readable text,
 * not to be confused with {@code toString()} which is meant for debugging
 */
public interface ToText {
    /**
     * Human-readable text meant for more interactive use and sometimes debugging.
     * Output should be more readable than {@code toString()} but for some objects
     * with many attributes having this as the canonical text representation may not
     * make sense. Use your own discretion for what this should output.
     */
    public String toText();
}
