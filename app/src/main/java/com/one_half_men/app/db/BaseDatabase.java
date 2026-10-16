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
class BaseDatabase<V> {
    @Getter(AccessLevel.PACKAGE)
    protected final Map<Key<V>, V> entries;

    BaseDatabase() {
        this(new HashMap<>());
    }

    public static final class DatabaseJsonAdapter<V> implements JsonAdapter<BaseDatabase<V>> {
        private final JsonAdapter<V> valueAdapter;

        public static final AdapterFactory FACTORY = (type, jsonb) -> {
            if (!Types.isGenericTypeOf(type, Database.class)) {
                return null;
            }

            Type[] typeArgs = Types.typeArguments(type);

            if (typeArgs.length != 1) {
                return null;
            }

            return new DatabaseJsonAdapter<>(jsonb, typeArgs[0]);
        };

        public DatabaseJsonAdapter(Jsonb jsonb, Type generic) {
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
