package co.newco.newco_android.Network;

import android.util.Log;

import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Interfaces.StringResponseHandler;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jayd on 11/8/15.
 */
public class CurrentUserData {
    private static CurrentUserData instance = null;

    private String currentUserKey = null;

    public String getCurrentUserKey() {
        return currentUserKey;
    }

    public void resetCurrentUserKey() {
        this.currentUserKey = null;
    }

    public static CurrentUserData getInstance(){
        if(instance == null) {
            instance = new CurrentUserData();
        }
        return instance;
    }

    public Call userLogin(String username, String password, final SimpleResponsehandler callback){
        Call<String> call = null;
        if(currentUserKey == null){
            call = RestClient.getInstance().get().login(username, password);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    currentUserKey = response.body();
                    callback.handleResponse();
                }
                @Override
                public void onFailure(Throwable t) {
                    callback.handleError(t);
                    Log.e("getSessionData error", t.getMessage());
                }
            });
        }
        else{
            callback.handleResponse();
        }
        return call;
    }
    public Call signupForSession(String sessionid, final StringResponseHandler callback){
        Call<String> call = null;
        call = RestClient.getInstance().get().signupForSession(sessionid, currentUserKey);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                callback.handleResponse(response.body());
            }
            @Override
            public void onFailure(Throwable t) {
                callback.handleError(t);
                Log.e("getSessionData error", t.getMessage());

            }
        });
        return call;
    }
}
