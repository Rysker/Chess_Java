package DataTypes;

import java.util.Objects;

public class Tuple<T1, T2> {
    private final T1 first;
    private final T2 second;

    public Tuple(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }
    public static <T1, T2> Tuple<T1, T2> of(T1 a, T2 b)
    {
        return new Tuple<>(a, b);
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond()
    {
        return second;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(first, tuple.first) && Objects.equals(second, tuple.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}