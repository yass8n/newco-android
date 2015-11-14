package co.newco.newco_android.Interfaces;

import java.util.List;

import co.newco.newco_android.Models.Session;

import co.newco.newco_android.Models.User;
import co.newco.newco_android.Models.UserSession;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jayd on 10/14/15.
 */
public interface RestAPI {
    @GET("/api/session/export")
    Call<List<Session>> listSessions();

    @GET("/api/user/get?by=username&fields=username,name,email,twitter_uid,fb_uid,position,location,company,privacy_mode")
    Call<User> getUser(@Query("term") String term);

    @GET("/api/user/list?fields=username,name,email,twitter_uid,fb_uid,lastactive,position,location,company,sessions,url,about,privacy_mode,role,phone,avatar,id")
    Call<List<User>> listUsers();

    @GET("/api/auth/login")
    Call<String> login(@Query("username") String username, @Query("password") String password);

    @GET("/api/going/add")
    Call<String> signupForSession(@Query("session") String userkey, @Query("sessions") String sessionId);

    @GET("/api/user/get?by=email&fields=username,name,email,twitter_uid,fb_uid,position,location,company,privacy_mode")
    Call<User> getUserInfo(@Query("term") String email);

    @GET("/api/going/list")
    Call<List<String>> getUserSessIds(@Query("session") String session, @Query("username") String username);

    @GET("/api/user/sessions")
    Call<List<UserSession>> getUserSessions();

    @GET("/api/session/seats?type=all&by=id&fields=avatar,name,username")
    Call<List<User>> getUsersAttendingSession(@Query("id") String id);
}