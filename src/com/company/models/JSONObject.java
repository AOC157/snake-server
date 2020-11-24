package com.company.models;

import com.company.parser.JSONStringReader;

import java.util.HashSet;
import java.util.Set;

public final class JSONObject extends JSON {
    Set<JSONField<?>> items = new HashSet<>();

    public void put(String key, int value) {
        items.add(new JSONField<>(key, value));
    }

    public void put(String key, double value) {
        items.add(new JSONField<>(key, value));
    }

    public void put(String key, boolean value) {
        items.add(new JSONField<>(key, value));
    }

    public void put(String key, String value) {
        items.add(new JSONField<>(key, value));
    }

    public void put(String key, JSONObject value) {
        items.add(new JSONField<>(key, value));
    }

    public void put(String key, JSONArray value) {
        items.add(new JSONField<>(key, value));
    }

    public JSONArray getJSONArray(String key) {
        try {
            JSONField<JSONArray> jsonField = findField(key);
            if (jsonField == null) {
                throw new RuntimeException();
            }
            return jsonField.value;
        } catch (ClassCastException e) {
            throw new RuntimeException("Field " + key + " is not JSONArray");
        } catch (RuntimeException e) {
            throw new RuntimeException("Field " + key + " not found");
        }
    }

    public int getInt(String key) {
        try {
            JSONField<Integer> jsonField = findField(key);
            if (jsonField == null) {
                throw new RuntimeException();
            }
            return jsonField.value;
        } catch (ClassCastException e) {
            throw new RuntimeException("Field " + key + " is not integer");
        } catch (RuntimeException e) {
            throw new RuntimeException("Field " + key + " not found");
        }
    }

    public double getDouble(String key) {
        try {
            JSONField<Double> jsonField = findField(key);
            if (jsonField == null) {
                throw new RuntimeException();
            }
            return jsonField.value;
        } catch (ClassCastException e) {
            throw new RuntimeException("Field " + key + " is not double");
        } catch (RuntimeException e) {
            throw new RuntimeException("Field " + key + " not found");
        }
    }

    public boolean getBoolean(String key) {
        try {
            JSONField<Boolean> jsonField = findField(key);
            if (jsonField == null) {
                throw new RuntimeException();
            }
            return jsonField.value;
        } catch (ClassCastException e) {
            throw new RuntimeException("Field " + key + " is not boolean");
        } catch (RuntimeException e) {
            throw new RuntimeException("Field " + key + " not found");
        }
    }

    public String getString(String key) {
        try {
            JSONField<String> jsonField = findField(key);
            if (jsonField == null) {
                throw new RuntimeException();
            }
            return jsonField.value;
        } catch (ClassCastException e) {
            throw new RuntimeException("Field " + key + " is not String");
        } catch (RuntimeException e) {
            throw new RuntimeException("Field " + key + " not found");
        }
    }

    public JSONObject getJSONObject(String key) {
        try {
            JSONField<JSONObject> jsonField = findField(key);
            if (jsonField == null) {
                throw new RuntimeException();
            }
            return jsonField.value;
        } catch (ClassCastException e) {
            throw new RuntimeException("Field " + key + " is not JSONObject");
        } catch (RuntimeException e) {
            throw new RuntimeException("Field " + key + " not found");
        }
    }

    public Object get(String key) {
        try {
            JSONField<?> jsonField = findField(key);
            if (jsonField == null) {
                throw new RuntimeException();
            }
            return jsonField.value;
        } catch (RuntimeException e) {
            throw new RuntimeException("Field " + key + " not found");
        }
    }

    private <T> JSONField<T> findField(String key) {
        for (JSONField<?> field : items) {
            if (field.keyEquals(key)) {
                return (JSONField<T>) field;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if (items.isEmpty()) return "" + JSONStringReader.ACULAD_OPEN + JSONStringReader.ACULAD_CLOSE;
        StringBuilder builder = new StringBuilder();
        builder.append(JSONStringReader.ACULAD_OPEN);
        boolean appendCama = false;
        for (JSONField item : items){
            if (appendCama){
                builder.append(JSONStringReader.CAMA);
            }
            appendCama = true;
            builder.append(item.toString());
        }
        builder.append(JSONStringReader.ACULAD_CLOSE);

        return builder.toString();
    }
}
