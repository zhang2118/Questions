package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import facebook4j.*;

import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONObject;
import play.libs.Json;
import play.mvc.*;
import play.libs.WS;
import play.libs.F.Function;

import views.html.*;

import org.slf4j.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Application extends Controller {

    final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static String accessToken ="";

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
        return redirect("https://www.facebook.com/dialog/oauth?client_id=908722389217808&redirect_uri=http://localhost:9000&scope=email,user_photos");
    }

    public static Result getAlbums(){
        return ok(albums.render(""));
    }
    public static Result albums(){
        String accessToken = Http.Context.current().session().get("access_token").toString();
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAccessToken(new AccessToken(accessToken));
        try{
            RawAPIResponse res = facebook.callGetAPI("/me/albums");
            JSONObject jsonObject = res.asJSONObject();
            return ok(Json.parse(jsonObject.toString()));
        }catch (FacebookException e){
            logger.error(e.getMessage());
        }
        return notFound("");
    }
    public static Result album(Long id){
        if (id == null){
            return notFound();
        }
        String accessToken = Http.Context.current().session().get("access_token").toString();
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAccessToken(new AccessToken(accessToken));
        try{
            RawAPIResponse res = facebook.callGetAPI("/"+id+"/photos");
            JSONObject jsonObject = res.asJSONObject();
            return ok(Json.parse(jsonObject.toString()));
        }catch (FacebookException e){
            logger.error(e.getMessage());
        }
        return ok();
    }
    public static Result getThumbnailUrl(Long id){
        if (id == null){
            return notFound();
        }
        String accessToken = Http.Context.current().session().get("access_token").toString();
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAccessToken(new AccessToken(accessToken));
        try{
            Map<String, String> parameters = new HashMap<>();
            parameters.put("type","thumbnail");
//            RawAPIResponse res = facebook.callGetAPI("/" + id.toString() + "/picture/", parameters);
            URL photoUrl = facebook.getPhotoURL(id.toString());
            return ok(Json.toJson(photoUrl));

        }catch (FacebookException e){
            logger.error(e.getMessage());
        }
        return ok();
    }
}
