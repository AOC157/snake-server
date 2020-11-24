package com.company.models;

import com.company.parser.JSONStringReader;

import java.util.ArrayList;
import java.util.List;

public final class JSONArray extends JSON {
    List<Field<?>> items = new ArrayList<>();

    public JSONArray() {

    }

    private <T> void put(T item) {
        items.add(new Field<>(item));
    }

    private <T> T get(int index) {
        return (T) items.get(index).getItem();
    }

    public void putInt(int item) {
        this.put(item);
    }

    public void putDouble(double item) {
        this.put(item);
    }

    public void putString(String item) {
        this.put(item);
    }

    public void putJSONObject(JSONObject item) {
        this.put(item);
    }

    public void putBoolean(boolean item) {
        this.put(item);
    }

    public void putJSONArray(JSONArray item) {
        this.put(item);
    }

    public int getInt(int index) {
        try {
            return get(index);
        } catch (ClassCastException e) {
            throw new RuntimeException("Field is not integer");
        }
    }

    public double getDouble(int index) {
        try {
            return get(index);
        } catch (ClassCastException e) {
            throw new RuntimeException("Field is not double");
        }
    }

    public String getString(int index) {
        try {
            return get(index);
        } catch (ClassCastException e) {
            throw new RuntimeException("Field is not string");
        }
    }

    public boolean getBoolean(int index) {
        try {
            return get(index);
        } catch (ClassCastException e) {
            throw new RuntimeException("Field is not boolean");
        }
    }

    public JSONObject getJSONObject(int index) {
        try {
            return get(index);
        } catch (ClassCastException e) {
            throw new RuntimeException("Field is not JSONObject");
        }
    }

    public JSONArray getJSONArray(int index) {
        try {
            return get(index);
        } catch (ClassCastException e) {
            throw new RuntimeException("Field is not JSONArray");
        }
    }

    public int length() {
        return items.size();
    }

    @Override
    public String toString() {
        if (items.isEmpty()) return "" + JSONStringReader.BRACKET_OPEN + JSONStringReader.BRACKET_CLOSE;
        StringBuilder builder = new StringBuilder();
        builder.append(JSONStringReader.BRACKET_OPEN);
        boolean appendCama = false;
        for (Field item : items){
            if (appendCama){
                builder.append(JSONStringReader.CAMA);
            }
            appendCama = true;
            builder.append(item.toString());
        }
        builder.append(JSONStringReader.BRACKET_CLOSE);
        return builder.toString();
    }
}
