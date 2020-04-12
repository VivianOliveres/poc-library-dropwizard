package poc.library.dropwizard.core.rating;

import poc.library.dropwizard.core.rating.db.RatingsRepo;
import poc.library.dropwizard.domain.Rating;
import poc.library.dropwizard.utils.Either;

import java.util.List;
import java.util.Optional;

public class RatingService {

    private RatingsRepo repo;

    public RatingService(RatingsRepo repo) {
        this.repo = repo;
    }

    public Either<Rating, String> rate(Rating rating) {
        long ratingId = repo.insertRating(rating.getUserId(), rating.getBookId().toString(), rating.getRatingValue());
        Optional<Rating> insertedRating = repo.getRatingById(ratingId);
        return Either.left(insertedRating);
    }

    public List<Rating> getRatings() {
        return repo.getRatings();
    }

    public Either<Rating, String> getRating(long ratingId) {
        Optional<Rating> maybeRating = repo.getRatingById(ratingId);
        return Either.left(maybeRating);
    }

    public Either<Rating, String> updateRating(Rating rating) {
        int rowsUpdated = repo.updateRating(rating.getRatingValue(), rating.getRatingId());
        if (rowsUpdated <= 0) {
            return Either.right("Failed to update rating: " + rating);
        }

        Optional<Rating> maybeRating = repo.getRatingById(rating.getRatingId());
        return Either.left(maybeRating);
    }

    public Either<Rating, String> deleteRating(long ratingId) {
        Optional<Rating> maybeRating = repo.getRatingById(ratingId);
        if (maybeRating.isEmpty()) {
            return Either.right("Can not delete an unknown rating[" + ratingId + "]");
        }

        int rowsUpdated = repo.deleteRating(ratingId);
        if (rowsUpdated <= 0) {
            return Either.right("Failed to delete rating[" + ratingId + "]");
        }

        return Either.left(maybeRating);
    }
}
