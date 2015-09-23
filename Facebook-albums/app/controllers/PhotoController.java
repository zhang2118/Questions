package controllers;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wensheng on 15/9/22.
 */
public class PhotoController extends Controller{

    final static Logger logger = LoggerFactory.getLogger(PhotoController.class);

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
            URL photoUrl = facebook.getPhotoURL(id.toString());
            return ok(Json.toJson(photoUrl));

        }catch (FacebookException e){
            logger.error(e.getMessage());
        }
        return ok();
    }


}
