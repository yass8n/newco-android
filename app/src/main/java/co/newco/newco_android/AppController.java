package co.newco.newco_android;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import co.newco.newco_android.Network.RestClient;
import co.newco.newco_android.models.Session;
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
    private Hashtable<String, ArrayList<Session>> sessionGroupHash = null;

    public Hashtable<String, ArrayList<Session>> getSessionGroupDayHash() {
        return sessionGroupDayHash;
    }

    public void setSessionGroupDayHash(Hashtable<String, ArrayList<Session>> sessionGroupDayHash) {
        this.sessionGroupDayHash = sessionGroupDayHash;
    }

    private Hashtable<String, ArrayList<Session>> sessionGroupDayHash = null;

    public Hashtable<String, ArrayList<Session>> getSessionGroupHash() {
        return sessionGroupHash;
    }

    public void setSessionGroupHash(Hashtable<String, ArrayList<Session>> sessionGroupHash) {
        this.sessionGroupHash = sessionGroupHash;
    }

    public interface handleSessionResponse{
        void handleSessionResponse();
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

    public void getSessionData(final handleSessionResponse sess){
        if(sessions == null) {
            Call<List<Session>> call = RestClient.getInstance().get().listSessions();
            call.enqueue(new Callback<List<Session>>() {
                @Override
                public void onResponse(Response<List<Session>> response, Retrofit retrofit) {
                    sessions = response.body();
                    setupSessionData(sessions);
                    sess.handleSessionResponse();
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("getSessionData error", t.getMessage());
                }
            });
        }
        else{
            sess.handleSessionResponse();
        }
    }

    protected void setupSessionData(List<Session> sessions){
        sessionGroupHash = new Hashtable<>();
        sessionGroupDayHash = new Hashtable<>();

        for(Session sess : sessions){
            if(!sessionGroupHash.containsKey(sess.getEvent_start())){
                sessionGroupHash.put(sess.getEvent_start(), new ArrayList<Session>());
            }
            if(!sessionGroupDayHash.containsKey(sess.getStart_date())){
                sessionGroupDayHash.put(sess.getStart_date(), new ArrayList<Session>());
            }

            sessionGroupHash.get(sess.getEvent_start()).add(sess);
            sessionGroupDayHash.get(sess.getStart_date()).add(sess);
        }
    }

    protected void initSingletons() {
        RestClient.getInstance().setRoot(currentUrl);
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
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
}

