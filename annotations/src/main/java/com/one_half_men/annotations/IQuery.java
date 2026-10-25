package com.one_half_men.annotations;

/**
 * A query for T.
 */
public interface IQuery<T> {
    /**
     * Returns true if the given object matches this query
     */
    public boolean matches(T obj);
}
