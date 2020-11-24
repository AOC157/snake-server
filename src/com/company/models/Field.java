package com.company.models;

import java.util.Objects;

public class Field<T> {
    private T item;

    public T getItem() {
        return item;
    }

    public Field(T item){
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field<?> field = (Field<?>) o;
        return Objects.equals(item, field.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }

    @Override
    public String toString() {
        return item.toString();
    }
}
