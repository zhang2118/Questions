package controllers;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Created by Wensheng on 15/9/22.
 */
public class CommentController extends Controller{

    final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    public static Result addCommentOnPhoto(String photoId, String comment) {
         if(photoId != null && comment != null){
             String accessToken = Http.Context.current().session().get("access_token").toString();
             Facebook facebook = new FacebookFactory().getInstance();
             facebook.setOAuthAccessToken(new AccessToken(accessToken));
             try {
                 facebook.commentPhoto(photoId, comment);

             }catch(FacebookException e){
                 logger.error(e.getMessage());
                 return internalServerError();
             }
             return ok();
         }
        return ok();
    }
}
