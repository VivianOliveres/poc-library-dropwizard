package poc.library.dropwizard.utils;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;

@ToString
@EqualsAndHashCode
public class Either<L, R> {

    public static <Left, Right> Either<Left, Right> left(Left l) {
        return new Either<>(l, null);
    }

    public static <Left, Right> Either<Left, Right> left(Optional<Left> l) {
        return new Either<>(l, null);
    }

    public static <Left, Right> Either<Left, Right> right(Right r) {
        return new Either<>(null, r);
    }

    public static <Left, Right> Either<Left, Right> right(Optional<Right> r) {
        return new Either<>(null, r);
    }

    private final Optional<L> left;
    private final Optional<R> right;

    public Either(L left, R right) {
        this(Optional.ofNullable(left), Optional.ofNullable(right));
    }

    public Either(Optional<L> left, Optional<R> right) {
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

    public R getRight() {
        return right.get();
    }
}
