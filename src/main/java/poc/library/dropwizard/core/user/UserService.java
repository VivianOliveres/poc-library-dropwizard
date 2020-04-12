package poc.library.dropwizard.core.user;

import poc.library.dropwizard.core.user.db.UsersRepo;
import poc.library.dropwizard.domain.User;
import poc.library.dropwizard.utils.Try;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UsersRepo repo;

    public UserService(UsersRepo repo) {
        this.repo = repo;
    }

    public Try<User> deleteUser(long userId) {
        Optional<User> maybeUser = repo.findUserById(userId);
        if (maybeUser.isEmpty()) {
            return Try.right("Can not find User[" + userId + "]");
        }

        int rowsDeleted = repo.deleteUserById(userId);
        if (rowsDeleted <= 0) {
            return Try.right("Can not delete User[" + userId + "]");
        }

        return Try.left(maybeUser);
    }

    public Try<User> insertUser(User user) {
        long userId = repo.insert(user.getFirstName(), user.getFamilyName(), user.getBirthDate(), user.getMembershipDate());
        if (userId <= 0) {
            return Try.right("Can not insert User: " + user);
        }

        Optional<User> maybeUser = repo.findUserById(userId);
        return Try.left(maybeUser);
    }

    public Try<List<User>> getUsers() {
        List<User> users = repo.findUsers();
        return Try.left(users);
    }

    public Try<User> getUserById(long userId) {
        Optional<User> maybeUser = repo.findUserById(userId);
        if (maybeUser.isEmpty()) {
            return Try.right("Can not find user[" + userId + "]");
        }

        return Try.left(maybeUser);
    }

    public Try<User> getUserByNames(String firstName, String familyName) {
        Optional<User> maybeUser = repo.findUserByNames(firstName, familyName);
        if (maybeUser.isEmpty()) {
            return Try.right("Can not find user[" + firstName + ", " + familyName + "]");
        }

        return Try.left(maybeUser);
    }
}
