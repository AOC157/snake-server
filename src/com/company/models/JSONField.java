package com.company.models;

import com.company.parser.JSONStringReader;

import java.util.Objects;

final class JSONField<T> {
    String key;
    T value;

    public JSONField(String key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JSONField<?> jsonField = (JSONField<?>) o;
        return Objects.equals(key, jsonField.key) &&
                Objects.equals(value, jsonField.value);
    }

    public boolean keyEquals(String key) {
        return this.key.equals(key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(JSONStringReader.DOUBLE_QUOTATION);
        builder.append(key);
        builder.append(JSONStringReader.DOUBLE_QUOTATION);
        builder.append(JSONStringReader.DOUBLE_DOT);
        if (value instanceof String)
            builder.append(JSONStringReader.DOUBLE_QUOTATION);
        builder.append(value.toString());
        if (value instanceof String)
            builder.append(JSONStringReader.DOUBLE_QUOTATION);
        return builder.toString();
    }
}
