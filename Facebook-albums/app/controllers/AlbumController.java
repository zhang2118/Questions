package controllers;

import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.json.DataObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.albums;

import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by Wensheng on 15/9/22.
 */
public class AlbumController extends Controller {

    final static Logger logger = LoggerFactory.getLogger(AlbumController.class);

    /*
    * Render the album list view
    * */
    public static Result getAlbums(){
        return ok(albums.render(""));
    }
    /*
    * API for returning album list to javascript
    * */
    public static Result getAlbumList(){
        String accessToken = Http.Context.current().session().get("access_token").toString();
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAccessToken(new AccessToken(accessToken));
        try{
            List<Album> albumList = facebook.getAlbums();
            return ok(toJson(albumList));
        }catch (FacebookException e){
            Http.Context.current().session().clear();
            logger.error(e.getMessage());
        }
        return redirect("/");
    }
    /*
    * The method will return corresponded photos in a specific album.
    * */
    public static Result album(Long id){
        if (id == null){
            return notFound();
        }
        String accessToken = Http.Context.current().session().get("access_token").toString();
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAccessToken(new AccessToken(accessToken));
        try{
            List<Photo> photoList = facebook.getAlbumPhotos(id.toString());
            return ok(toJson(photoList));
        }catch (FacebookException e){
            logger.error(e.getMessage());
        }
        return ok();
    }

    /*
    * The method aims to provide with album deletion operation.
    * But I did not find corresponded API in Facebook4j.
    * */
    public static Result deleteAlbum(Long id){

        return ok();
    }

    /*
    * The method aims to provide with album update operation.
    * But I did not find corresponded API in Facebook4j.
    * */
    public static Result updateAlbum(String album){
        try{
            Album newAlbum = DataObjectFactory.createAlbum(album);
            String accessToken = Http.Context.current().session().get("access_token").toString();
            Facebook facebook = new FacebookFactory().getInstance();
            facebook.setOAuthAccessToken(new AccessToken(accessToken));
        }catch (FacebookException e){
            logger.error(e.getMessage());
        }


        return ok();
    }

}
