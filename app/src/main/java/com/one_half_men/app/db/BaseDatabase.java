package com.one_half_men.app.db;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.avaje.json.JsonAdapter;
import io.avaje.json.JsonDataException;
import io.avaje.json.JsonReader;
import io.avaje.json.JsonWriter;
import io.avaje.jsonb.AdapterFactory;
import io.avaje.jsonb.Jsonb;
import io.avaje.jsonb.Types;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public class BaseDatabase<V> {
    @Getter(AccessLevel.PUBLIC)
    protected final Map<Key<V>, V> entries;

    BaseDatabase() {
        this(new HashMap<>());
    }

    public static final class BaseDatabaseJsonAdapter<V> implements JsonAdapter<BaseDatabase<V>> {
        private final JsonAdapter<V> valueAdapter;

        public static final AdapterFactory FACTORY = (type, jsonb) -> {
            if (!Types.isGenericTypeOf(type, BaseDatabase.class)) {
                return null;
            }

            Type[] typeArgs = Types.typeArguments(type);

            if (typeArgs.length != 1) {
                return null;
            }

            return new BaseDatabaseJsonAdapter<>(jsonb, typeArgs[0]);
        };

        public BaseDatabaseJsonAdapter(Jsonb jsonb, Type generic) {
            valueAdapter = jsonb.adapter(generic);
        }

        @Override
        public void toJson(JsonWriter writer, BaseDatabase<V> db) {
            writer.beginObject();
            for (var entry : db.entries.entrySet()) {
                Key<V> key = entry.getKey();
                if (key == null) {
                    throw new JsonDataException("Map key is null at " + writer.path());
                }
                writer.name(key.jsonKeyString());
                valueAdapter.toJson(writer, entry.getValue());
            }
            writer.endObject();
        }

        @Override
        public BaseDatabase<V> fromJson(JsonReader reader) {
            BaseDatabase<V> db = new BaseDatabase<>();
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
