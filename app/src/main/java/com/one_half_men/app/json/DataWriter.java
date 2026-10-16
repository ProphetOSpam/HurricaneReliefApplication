package com.one_half_men.app.json;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.one_half_men.app.db.BaseDatabase;
import com.one_half_men.app.db.Database;

import io.avaje.jsonb.JsonType;

public class DataWriter {
    // TODO: maybe have each database store where it writes to?
    public static <V> void writeDatabase(JsonType<BaseDatabase<V>> type, Database<V> db) throws IOException {
        type.toJson(db.base, new FileWriter(Constants.TEST));
    }

    public static <V> void writeDatabase(JsonType<BaseDatabase<V>> type, Database<V> db, Writer writer)
            throws IOException {
        type.toJson(db.base, writer);
    }
}
