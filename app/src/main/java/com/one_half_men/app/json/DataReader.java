package com.one_half_men.app.json;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

import com.one_half_men.app.db.BaseDatabase;
import com.one_half_men.app.db.Database;

import io.avaje.jsonb.JsonType;

public class DataReader {
    // TODO: maybe have each database store where it writes to?
    public static <V, D extends Database<V>> D readDatabase(JsonType<BaseDatabase<V>> type,
            Function<BaseDatabase<V>, D> fun) {
        return readDatabase(type, fun, new File(Constants.TEST));
    }

    public static <V, D extends Database<V>> D readDatabase(JsonType<BaseDatabase<V>> type,
            Function<BaseDatabase<V>, D> fun, File file) {
        try {
            return fun.apply(type.fromJson(new FileReader(file)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <V, D extends Database<V>> D readDatabase(JsonType<BaseDatabase<V>> type,
            Function<BaseDatabase<V>, D> fun, Reader reader) {
        return fun.apply(type.fromJson(reader));
    }
}
