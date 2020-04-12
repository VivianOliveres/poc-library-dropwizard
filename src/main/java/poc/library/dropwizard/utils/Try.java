package poc.library.dropwizard.utils;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;

@ToString
@EqualsAndHashCode
public class Try<L> {

    public static <Left, Right> Try<Left> left(Left l) {
        return new Try<Left>(l);
    }

    public static <Left, Right> Try<Left> left(Optional<Left> l) {
        return new Try<>(l, null);
    }

    public static <Left, Right> Try<Left> right(String message) {
        return new Try<Left>(message);
    }

    private final Optional<L> left;
    private final Optional<String> right;

    public Try(L left) {
        this(Optional.ofNullable(left), Optional.empty());
    }

    public Try(String message) {
        this(Optional.empty(), Optional.of(message));
    }

    public Try(Optional<L> left, Optional<String> right) {
        this.left = left;
        this.right = right;
    }

    public boolean isLeft() {
        return left.isPresent();
    }

    public boolean isRight() {
        return right.isPresent();
    }

    public L getLeft() {
        return left.get();
    }

    public String getRight() {
        return right.get();
    }
}
