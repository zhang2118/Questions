package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.*;
import play.libs.WS;
import play.libs.F.Function;

import views.html.*;

import org.slf4j.*;

public class LoginController extends Controller {

    final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    public static Result index(String code) {
        if (code != null && !Http.Context.current().session().containsKey("access_token")){
            //get OAuthToken
            logger.debug(code);
            return async(
                    WS.url("https://graph.facebook.com/v2.3/oauth/access_token")
                            .setQueryParameter("client_id", "908722389217808")
                            .setQueryParameter("redirect_uri", "http://localhost:9000/")
                            .setQueryParameter("client_secret", "ab71de43ccaa56d104654c43650f6a50")
                            .setQueryParameter("code", code).get().map(
                            new Function<WS.Response, Result>() {
                                public Result apply(WS.Response response) {
                                    logger.debug(response.toString());
                                    JsonNode node = Json.parse(response.getBody());
                                    if (node.get("access_token") != null) {
                                        Http.Context.current().session().put("access_token", node.get("access_token").asText());
                                    }
                                    return redirect("/getAlbums");
                                }
                            }
                    )
            );

        }else if(Http.Context.current().session().containsKey("access_token")){
            return redirect("/getAlbums");
        }
        return ok(index.render(""));
    }

    public static Result login(){
        return redirect("https://www.facebook.com/dialog/oauth?client_id=908722389217808&redirect_uri=http://localhost:9000&scope=email,user_photos,publish_actions");
    }



}
