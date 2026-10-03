package com.one_half_men.json;

import java.io.FileWriter;
import java.io.IOException;

import com.one_half_men.db.Database;

import tools.jackson.databind.ObjectMapper;

public class DataWriter {
    // TODO: maybe have each database store where it writes to?
    public static <V> void writeDatabase(Database<V> db) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new FileWriter(Constants.TEST), db);
    }
}
