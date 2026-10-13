package com.one_half_men.app.json;

import java.io.FileWriter;
import java.io.IOException;

import com.one_half_men.app.db.Database;

public class DataWriter {
    // TODO: maybe have each database store where it writes to?
    public static <V> void writeDatabase(Database<V> db) throws IOException {
        db.type.toJson(db, new FileWriter(Constants.TEST));
    }
}
