package poc.library.dropwizard.utils;

import lombok.experimental.UtilityClass;

import javax.ws.rs.core.Response;

@UtilityClass
public class ResourceUtils {

    public static <T> Response renderCreated(Try<T> result) {
        return render(result, Response.Status.CREATED);
    }

    public static <T> Response renderOk(Try<T> result) {
        return render(result, Response.Status.OK);
    }

    public static <T> Response render(Try<T> result, Response.Status okStatus) {
        if (result.isLeft()) {
            return Response.status(okStatus).entity(result.getLeft()).build();
        }

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), result.getRight()).build();
    }
}
