package security;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class SecuredAction extends Action.Simple {

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        String login = ctx.session().get("login");
        if (login != null) {
            return delegate.call(ctx);
        } else {
            return CompletableFuture.completedFuture(unauthorized());
        }
    }
}
