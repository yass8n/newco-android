package co.newco.newco_android;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import co.newco.newco_android.Network.RestClient;
import co.newco.newco_android.models.Session;
import co.newco.newco_android.models.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jayd on 10/17/15.
 * Follows a singleton pattern to share resources across activities
 */
public class AppController extends Application {
    private static AppController mInstance;
    private String currentUrl = "http://newcobaybridgefestivals2015.sched.org";
    private List<Session> sessions = null;
    private List<User> users = null;
    private Hashtable<String, ArrayList<Session>> sessionGroupHash = null;
    private Hashtable<String, String> colorsHash = null;
    private List<User> attendees;

    public Hashtable<String, String> getColorsHash() {
        return colorsHash;
    }

    public Hashtable<String, ArrayList<Session>> getSessionGroupDayHash() {
        return sessionGroupDayHash;
    }

    private Hashtable<String, ArrayList<Session>> sessionGroupDayHash = null;

    public Hashtable<String, ArrayList<Session>> getSessionGroupHash() {
        return sessionGroupHash;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public interface handleResponse{
        void handleResponse();
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    public void getUsersData(final handleResponse callback){
        if(users == null){
            Call<List<User>> call = RestClient.getInstance().get().listUsers();
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
    }

    public void getSessionData(final handleResponse callback){
        if(sessions == null) {
            Call<List<Session>> call = RestClient.getInstance().get().listSessions();
            call.enqueue(new Callback<List<Session>>() {
                @Override
                public void onResponse(Response<List<Session>> response, Retrofit retrofit) {
                    sessions = response.body();
                    setupSessionData();
                    callback.handleResponse();
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("getSessionData error", t.getMessage());
                }
            });
        }
        else{
            callback.handleResponse();
        }
    }

    protected void setupSessionData(){
        sessionGroupHash = new Hashtable<>();
        sessionGroupDayHash = new Hashtable<>();
        colorsHash = new Hashtable<>();

        int colorsCount = 0;
        for(Session sess : sessions){
            if(!sessionGroupHash.containsKey(sess.getEvent_start())){
                sessionGroupHash.put(sess.getEvent_start(), new ArrayList<Session>());
            }
            if(!sessionGroupDayHash.containsKey(sess.getStart_date())){
                sessionGroupDayHash.put(sess.getStart_date(), new ArrayList<Session>());
            }


            if(sess.getEvent_type() != null) {
                String[] event_types = sess.getEvent_type().split(",");
                for(String event_type : event_types){
                    String et = event_type.trim();
                    if(!colorsHash.containsKey(et)){
                        colorsHash.put(et, COLOR_HASH.get(COLOR_HASH.keySet().toArray()[colorsCount%COLOR_HASH.keySet().size()]));
                    }
                }
                colorsCount++;
            }
            sessionGroupHash.get(sess.getEvent_start()).add(sess);
            sessionGroupDayHash.get(sess.getStart_date()).add(sess);
        }
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

    protected void initSingletons() {
        RestClient.getInstance().setRoot(currentUrl);
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
        RestClient.getInstance().setRoot(currentUrl);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public void Toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    public static HashMap<String, String> COLOR_HASH = new HashMap<String, String>(){{
        put("red", "#F3827F");
        put("green", "#9EDF7D");
        put("blue", "#B7CDFF");
        put("orange", "#FFBC57");
        put("purple", "#c488f6");
        put("teal", "#88F4F6");
        put("yellow", "#FFFC36");
        put("pink", "#F688E0");
        put("dark_blue", "#3b70e8");
        put("dark_red", "#f23631");
        put("dark_purple", "#86509b");
        put("dark_orange", "#f47c15");
        put("middle_blue", "#3697FF");
        put("lime_green", "#62FF36");
        put("dark_teal", "#1FB1D1");
        put("dark_yellow", "#B7BF1F");
        put("grey", "#eeeeee");
        put("light_orange", "#fbe0a9");
        put("light_pink", "#EFC8FE");
        put("faded_turquoise", "#80c5ca");
        put("brown", "#d79e80");
        put("turquoise", "#44F4C4");
        put("purpleish", "#68669e");
        put("puke", "#7d7c27");
    }};
}

