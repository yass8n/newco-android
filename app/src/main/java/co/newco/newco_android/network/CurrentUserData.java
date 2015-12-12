package co.newco.newco_android.network;

import android.util.Log;

import java.util.ArrayList;

import co.newco.newco_android.AppController;
import co.newco.newco_android.interfaces.SimpleResponsehandler;
import co.newco.newco_android.interfaces.StringResponseHandler;
import co.newco.newco_android.Models.User;
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

    public User getCurrentUser() {
        return currentUser;
    }

    private User currentUser = null;

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

    public ArrayList<Call> userLogin(final String email, String password, final SimpleResponsehandler callback){
        final ArrayList<Call> calls = new ArrayList<>();
        if(currentUserKey == null){
            calls.add(RestClient.getInstance().get().login(email, password));
            calls.add(RestClient.getInstance().get().getUserInfo(email));
            calls.get(0).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    currentUserKey = response.body();
                    //callback hell lol
                    calls.get(1).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Response<User> response, Retrofit retrofit) {
                            currentUser = response.body();
                            callback.handleResponse();
                        }
                        @Override
                        public void onFailure(Throwable t) {
                            callback.handleError(t);
                            Log.e("getSessionData error", t.getMessage());
                        }
                    });
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
        return calls;
    }
    public Call signupForSession(String sessionid, final StringResponseHandler callback){
        Call<String> call = null;
        call = RestClient.getInstance().get().signupForSession(currentUserKey, sessionid);
        AppController.getInstance().Toast(currentUserKey);
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
