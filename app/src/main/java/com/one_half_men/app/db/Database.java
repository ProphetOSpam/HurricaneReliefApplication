package com.one_half_men.app.db;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import io.avaje.json.JsonAdapter;
import io.avaje.json.JsonDataException;
import io.avaje.json.JsonReader;
import io.avaje.json.JsonWriter;
import io.avaje.jsonb.AdapterFactory;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import io.avaje.jsonb.Types;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Database<V> {
    @Getter(AccessLevel.PACKAGE)
    protected final Map<Key<V>, V> entries;

    public Database() {
        this(new HashMap<>());
    }

    public JsonType<Database<V>> type() {
        return null;
    }

    public Key<V> create(V element) {
        Key<V> key = Key.random();
        entries.put(key, element);

        return key;
    }

    public V get(Key<V> key) {
        return entries.get(key);
    }

    public V remove(Key<V> key) {
        return entries.remove(key);
    }

    // TODO: figure out how we wanna handle nulls
    public void edit(Key<V> key, Consumer<V> consumer) {
        V element = get(key);
        consumer.accept(element);
    }

    public static final class DatabaseJsonAdapter<V> implements JsonAdapter<Database<V>> {
        private final JsonAdapter<V> valueAdapter;

        public static final AdapterFactory FACTORY = (type, jsonb) -> {
            if (Types.isGenericTypeOf(type, Database.class)) {

                return new DatabaseJsonAdapter<>(jsonb, Types.typeArguments(type));
            }
            return null;
        };

        public DatabaseJsonAdapter() {
            valueAdapter = null;
        }

        public DatabaseJsonAdapter(Jsonb jsonb, Type[] types) {
            valueAdapter = jsonb.adapter(types[0]);
        }

        @Override
        public void toJson(JsonWriter writer, Database<V> db) {
            writer.beginObject();
            for (var entry : db.entries.entrySet()) {
                Key<V> key = entry.getKey();
                if (key == null) {
                    throw new JsonDataException("Map key is null at " + writer.path());
                }
                writer.name(key.toString());
                valueAdapter.toJson(writer, entry.getValue());
            }
            writer.endObject();
        }

        @Override
        public Database<V> fromJson(JsonReader reader) {
            Database<V> db = new Database<>();
            reader.beginObject();

            while (reader.hasNextField()) {
                Key<V> key = Key.fromString(reader.nextField());
                V value = valueAdapter.fromJson(reader);
                db.entries.put(key, value);
            }

            reader.endObject();

            return db;
        }
    }
}
