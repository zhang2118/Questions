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

    /*
    * This method is the entry method of this application.
    * */
    public static Result index(String code) {
        //Test if this is the first request or redirect request from Facebook
        if (code != null && !Http.Context.current().session().containsKey("access_token")){
            //request for OAuthToken from Facebook by using code received from Facebook
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

        // If the user is already logged in, the user will be redirected to album list view
        }else if(Http.Context.current().session().containsKey("access_token")){
            return redirect("/getAlbums");
        }
        return ok(index.render(""));
    }

    /*
    * When user clicks on `Login with Facebook` button, this method will be call.
    * It starts a redirect request to Facebook, where the user can approve the permissions asked.
    * */
    public static Result login(){
        return redirect("https://www.facebook.com/dialog/oauth?client_id=908722389217808&redirect_uri=http://localhost:9000&scope=email,user_photos,publish_actions");
    }



}
