package com.paradise.code.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Conditional<T> {
    private final T value;

    private static final Conditional<Object> EMPTY = Conditional.of(null);

    private Conditional(T value) {
        this.value = value;
    }

    public static <T> Conditional<T> of(T value) {
        return new Conditional<>(value);
    }

    public Conditional<T> test(Predicate<T> condition) {
        Objects.requireNonNull(condition);
        return condition.test(value)
                ? this
                : new Conditional<>(null);
    }

    public Conditional<T> thenReturn(Predicate<T> condition) {
        return value != null && condition.test(value) ? this : new Conditional<>(null);
    }

    public <U> Conditional<U> map(Function<T, U> mapper) {
        return value != null ? new Conditional<>(mapper.apply(value)) : new Conditional<>(null);
    }

    public T orElse(T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public T orElseGet(Supplier<T> defaultValueSupplier) {
        return value != null ? value : defaultValueSupplier.get();
    }

    public void ifPresent(Consumer<T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

    public boolean isPresent() {
        return value != null;
    }

    public Conditional<T> filter(Predicate<T> predicate) {
        return value != null && predicate.test(value) ? this : new Conditional<>(null);
    }

    public T get() {
        if (value != null) {
            return value;
        }
        throw new NoSuchElementException("No value present");
    }
}
