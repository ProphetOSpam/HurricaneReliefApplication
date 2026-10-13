package com.one_half_men.app.json;

import java.io.FileReader;
import java.io.IOException;

import com.one_half_men.app.db.Database;

public class DataReader {
    // TODO: maybe have each database store where it writes to?
    public static <V> Database<V> readDatabase(Database<V> db) throws IOException {
        return db.type.fromJson(new FileReader(Constants.TEST));
    }
}
