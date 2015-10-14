package co.newco.newco_android.Interfaces;

import java.util.List;

import co.newco.newco_android.Network.Responses.SessionResponse;
import co.newco.newco_android.models.Session;

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
    //okay, already diverged from the tutorial
    @Headers({
        "Content-Type: application/json;"
    })
    @GET("/api/session/export")
    Call<List<Session>> listSessions(@Query("api_key") String api_key, @Query("format") String format);
}