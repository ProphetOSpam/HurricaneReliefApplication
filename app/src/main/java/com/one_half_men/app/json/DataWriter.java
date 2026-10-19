package com.one_half_men.app.json;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

import com.one_half_men.app.db.BaseDatabase;
import com.one_half_men.app.db.Database;

import io.avaje.jsonb.JsonType;

public class DataWriter {
    // TODO: maybe have each database store where it writes to?
    public static <V> void writeDatabase(JsonType<BaseDatabase<V>> type, Database<V> db) {
        writeDatabase(type, db, new File(Constants.TEST));
    }

    public static <V> void writeDatabase(JsonType<BaseDatabase<V>> type, Database<V> db, File file) {
        try {
            type.toJson(db.base, new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Couldn't write database of type %s", type));
        }
    }

    public static <V> void writeDatabase(JsonType<BaseDatabase<V>> type, Database<V> db, Writer writer)
            throws IOException {
        type.toJson(db.base, writer);
    }
}
