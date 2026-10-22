package com.one_half_men.annotations;

public interface IQuery<T> {
    public boolean matches(T obj);
}
