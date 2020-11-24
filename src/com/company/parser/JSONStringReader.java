package com.company.parser;

import com.company.utils.NumberUtils;

public class JSONStringReader {

    public static final char ACULAD_OPEN = '{';
    public static final char ACULAD_CLOSE = '}';
    public static final char DOUBLE_DOT = ':';
    public static final char CAMA = ',';
    public static final char DOUBLE_QUOTATION = '"';
    public static final char BRACKET_OPEN = '[';
    public static final char BRACKET_CLOSE = ']';

    public static final String SPECIAL_TOKEN = "{}:,\"[]";

    String content;
    int index = 0;

    boolean isInQuotation = false;

    public JSONStringReader(String content) {
        if (content == null)
            throw new IllegalArgumentException("json content cannot be null");
        this.content = content;
    }

    public String readKey() {
        String key = readString();
        readDoubleDot();
        return key;
    }

    public void readAculadOpen() {
        char ch = read();
        if (ch != ACULAD_OPEN) {
            throwException();
        }
    }

    public void readAculadClose() {
        char ch = read();
        if (ch != ACULAD_CLOSE) {
            throwException();
        }
    }

    public void readBracketOpen() {
        char ch = read();
        if (ch != BRACKET_OPEN) {
            throwException();
        }
    }

    public void readBracketClose() {
        char ch = read();
        if (ch != BRACKET_CLOSE) {
            throwException();
        }
    }

    public int readInt() {
        StringBuilder builder = new StringBuilder();
        char ch;
        while ((ch = getUpComingChar()) != CAMA && ch != ACULAD_CLOSE && ch != BRACKET_CLOSE) {
            read();
            if (NumberUtils.isValidIntegerCharacter(ch)) {
                builder.append(ch);
            } else {
                throwException();
            }
        }
        return Integer.parseInt(builder.toString());
    }

    public double readDouble() {
        StringBuilder builder = new StringBuilder();
        char ch;
        while ((ch = getUpComingChar()) != CAMA && ch != ACULAD_CLOSE && ch != BRACKET_CLOSE) {
            read();
            if (NumberUtils.isValidDoubleChar(ch)) {
                builder.append(ch);
            } else {
                throwException();
            }
        }
        return Double.parseDouble(builder.toString());
    }

    public boolean readBoolean() {
        StringBuilder builder = new StringBuilder();
        isInQuotation = true;
        char ch;
        while ((ch = getUpComingChar()) != CAMA && ch != ACULAD_CLOSE && ch != BRACKET_CLOSE) {
            read();
            builder.append(ch);
        }
        isInQuotation = false;
        return Boolean.parseBoolean(builder.toString());
    }

    public String readString() {
        readQuotation();
        isInQuotation = true;
        StringBuilder builder = new StringBuilder();
        char ch;
        while ((ch = read()) != DOUBLE_QUOTATION) {
            builder.append(ch);
        }
        String text = builder.toString();
        isInQuotation = false;
        return text;
    }

    public void readDoubleDot() {
        char ch = read();
        if (ch != DOUBLE_DOT) {
            throwException();
        }
    }

    public void readCama() {
        char ch = read();
        if (ch != CAMA) {
            throwException();
        }
    }

    public void readQuotation() {
        char ch = read();
        if (ch != DOUBLE_QUOTATION) {
            throwException();
        }
    }

    private char read() {
        char ch;
        if (!isInQuotation) {
            while ((ch = content.charAt(index)) == ' ' || ch == '\n') {
                index++;
            }
            if (isValidCharacter(ch)) {
                index++;
                return ch;
            } else {
                throwException();
            }
        } else {
            ch = content.charAt(index);
            index++;
            return ch;
        }
        return '\0';
    }

    public char getUpComingChar() {
        char ch;
        int tmpIndex = index;
        while ((ch = content.charAt(tmpIndex)) == ' ' || ch == '\n') {
            tmpIndex++;
        }
        return ch;
    }

    private void throwException() {
        throw new RuntimeException("Input String is not a valid JSON, error index: " + index);
    }

    private boolean isValidCharacter(char ch) {
        return isInQuotation || SPECIAL_TOKEN.contains("" + ch) ||
                NumberUtils.isValidDoubleChar(ch);
    }

    public boolean isUpcomingDouble() {
        boolean isDotSeen = false;
        int backupIndex = index;
        char ch;
        while ((ch = read()) != CAMA && ch != ACULAD_CLOSE && ch != BRACKET_CLOSE) {
            isDotSeen = isDotSeen || ch == '.';
        }
        index = backupIndex;
        return isDotSeen;
    }
}
