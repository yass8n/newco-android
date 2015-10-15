package co.newco.newco_android.Interfaces;

import java.util.List;

import co.newco.newco_android.Network.Responses.SessionResponse;
import co.newco.newco_android.models.Session;

import co.newco.newco_android.models.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jayd on 10/14/15.
 */
public interface RestAPI {
    @GET("/api/session/export")
    Call<List<Session>> listSessions();
    @GET("/api/user/get?by=username&fields=username,name,email,twitter_uid,fb_uid,position,location,company,privacy_mode")
    Call<User> getUser(@Query("term") String term);
}