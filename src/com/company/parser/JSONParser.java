package com.company.parser;

import com.company.utils.NumberUtils;
import com.company.models.JSON;
import com.company.models.JSONArray;
import com.company.models.JSONObject;

public class JSONParser {
    public JSONParser() {

    }

    public <T extends JSON> T parse(String content) {
        JSONStringReader reader = new JSONStringReader(content);
        char ch = reader.getUpComingChar();
        if (ch == JSONStringReader.BRACKET_OPEN) {
            return (T) parseArray(reader);
        } else {
            return (T) parseObject(reader);
        }
    }

    private JSONArray parseArray(JSONStringReader reader) {
        reader.readBracketOpen();
        JSONArray array = new JSONArray();
        parseArrayItems(reader, array);
        reader.readBracketClose();
        return array;
    }

    private void parseArrayItems(JSONStringReader reader, JSONArray array) {
        while (reader.getUpComingChar() != JSONStringReader.BRACKET_CLOSE) {
            readArrayItem(reader, array);
            char ch = reader.getUpComingChar();
            if (ch != JSONStringReader.BRACKET_CLOSE) {
                reader.readCama();
            }
        }
    }

    private void readArrayItem(JSONStringReader reader, JSONArray array) {
        char ch = reader.getUpComingChar();
        if (ch == JSONStringReader.DOUBLE_QUOTATION) {
            String content = reader.readString();
            array.putString(content);
        } else if (ch == JSONStringReader.BRACKET_OPEN) {
            JSONArray array1 = parseArray(reader);
            array.putJSONArray(array1);
        } else if (ch == JSONStringReader.ACULAD_OPEN) {
            JSONObject object = parseObject(reader);
            array.putJSONObject(object);
        } else if (NumberUtils.isValidIntegerCharacter(ch)) {
            boolean isUpcomingDouble = reader.isUpcomingDouble();
            if (isUpcomingDouble) {
                double d = reader.readDouble();
                array.putDouble(d);
            } else {
                int number = reader.readInt();
                array.putInt(number);
            }
        } else {
            boolean b = reader.readBoolean();
            array.putBoolean(b);
        }
    }

    private JSONObject parseObject(JSONStringReader reader) {
        reader.readAculadOpen();
        JSONObject jsonObject = new JSONObject();
        parseKeyValueList(reader, jsonObject);
        return jsonObject;
    }

    private void parseKeyValueList(JSONStringReader reader, JSONObject jsonObject) {
        while (reader.getUpComingChar() != JSONStringReader.ACULAD_CLOSE) {
            readKeyValue(reader, jsonObject);
            char ch = reader.getUpComingChar();
            if (ch != JSONStringReader.ACULAD_CLOSE) {
                reader.readCama();
            }
        }
        reader.readAculadClose();
    }

    private void readKeyValue(JSONStringReader reader, JSONObject jsonObject) {
        String key = reader.readKey();
        char nextStep = reader.getUpComingChar();
        if (nextStep == JSONStringReader.DOUBLE_QUOTATION) {
            String value = reader.readString();
            jsonObject.put(key, value);
        } else if (NumberUtils.isValidIntegerCharacter(nextStep)) {
            boolean isUpcomingDouble = reader.isUpcomingDouble();
            if (isUpcomingDouble) {
                double d = reader.readDouble();
                jsonObject.put(key, d);
            } else {
                int number = reader.readInt();
                jsonObject.put(key, number);
            }
        } else if (nextStep == JSONStringReader.ACULAD_OPEN) {
            JSONObject object = parseObject(reader);
            jsonObject.put(key, object);
        } else if (nextStep == JSONStringReader.BRACKET_OPEN) {
            JSONArray array = parseArray(reader);
            jsonObject.put(key, array);
        } else {
            boolean bool = reader.readBoolean();
            jsonObject.put(key, bool);
        }
    }
}
