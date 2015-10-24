package co.newco.newco_android.Network;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.newco.newco_android.AppController;
import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.models.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jayd on 10/23/15.
 */
public class UserData {
    private static UserData instance = null;
    private List<User> users = null;
    private List<User> attendees;

    public static UserData getInstance(){
        if(instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public List<User> getUsers() {
        return users;
    }


    public Call getUsersData(final SimpleResponsehandler callback){
        Call<List<User>> call = null;
        if(users == null){
            call = RestClient.getInstance().get().listUsers();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                    users = response.body();
                    setupUserData();
                    callback.handleResponse();
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.e("getUsersData error", t.getMessage());

                }
            });
        }
        else{
            callback.handleResponse();
        }
        return call;
    }

    protected void setupUserData(){
        attendees = new ArrayList<>();
        for(User user : users){
            List<String> userRole = new ArrayList<>(Arrays.asList(user.getRole().split(", ")));
            if(userRole.contains("attendee")){
                attendees.add(user);
            }
        }
    }
}
