package poc.library.dropwizard.utils;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@ToString
@EqualsAndHashCode
public class Try<L> {

    public static <Left> Try<Left> left(Left l) {
        return new Try<>(l);
    }

    public static <Left> Try<Left> left(Optional<Left> l) {
        return new Try<>(l, null);
    }

    public static <Left> Try<Left> right(String message) {
        return new Try<>(message);
    }

    private final Optional<L> left;
    private final Optional<String> message;

    public Try(L left) {
        this(Optional.ofNullable(left), Optional.empty());
    }

    public Try(String message) {
        this(Optional.empty(), Optional.of(message));
    }

    public Try(Optional<L> left, Optional<String> message) {
        this.left = left;
        this.message = message;
    }

    public boolean isLeft() {
        return left.isPresent();
    }

    public L getLeft() {
        return left.get();
    }

    public String getMessage() {
        return message.get();
    }

    public <Other> Try<Other> map(Function<L, Other> mapper) {
        if (isLeft()) {
            return Try.left(mapper.apply(left.get()));
        }

        return Try.right(message.get());
    }

    public <Other, Result> Try<Result> zip(Try<Other> other, BiFunction<L, Other, Result> zipper) {
        if (isLeft() && other.isLeft()) {
            return Try.left(zipper.apply(left.get(), other.getLeft()));
        }

        if(isLeft()) {
            return Try.right(other.getMessage());
        }

        return Try.right(message.get());
    }

}
