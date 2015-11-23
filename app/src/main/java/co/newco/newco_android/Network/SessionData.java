package co.newco.newco_android.Network;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Models.Session;
import co.newco.newco_android.Models.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jayd on 10/23/15.
 */
public class SessionData {
    private static SessionData instance = null;

    private List<Session> sessions;
    private Hashtable<String, ArrayList<Session>> sessionGroupHash = null;
    private Hashtable<String, String> colorsHash = null;
    private Hashtable<String, ArrayList<Session>> sessionGroupDayHash = null;
    private Hashtable<String, Session> sessionByKey = null;
    private Hashtable<String, ArrayList<Session>> venueByName;

    public Hashtable<String, ArrayList<Session>> getVenueByName() {
        return venueByName;
    }

    public Hashtable<String, Session> getSessionById() {
        return sessionById;
    }

    private Hashtable<String, Session> sessionById = null;

    public static SessionData getInstance(){
        if(instance == null) {
            instance = new SessionData();
        }
        return instance;
    }


    public Hashtable<String, String> getColorsHash() {
        return colorsHash;
    }

    public Hashtable<String, ArrayList<Session>> getSessionGroupDayHash() {
        return sessionGroupDayHash;
    }


    public Hashtable<String, ArrayList<Session>> getSessionGroupHash() {
        return sessionGroupHash;
    }

    public List<Session> getSessions() {
        return sessions;
    }


    public Call getSessionData(final SimpleResponsehandler callback){
        Call<List<Session>> call = null;
        if(sessions == null) {
            call = RestClient.getInstance().get().listSessions();
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
        return call;
    }

    public Hashtable<String, Session> getSessionByKey() {
        return sessionByKey;
    }

    protected void setupSessionData(){
        sessionGroupHash = new Hashtable<>();
        sessionGroupDayHash = new Hashtable<>();
        colorsHash = new Hashtable<>();
        sessionByKey = new Hashtable<>();
        sessionById = new Hashtable<>();
        venueByName = new Hashtable<>();

        //let's sort them
        Collections.sort(sessions, new Comparator<Session>() {
            @Override
            public int compare(Session lhs, Session rhs) {
                return lhs.getStart_time_ts().compareTo(rhs.getStart_time_ts());
            }
        });
        int colorsCount = 0;
        for(Session sess : sessions){
            if(!sessionGroupHash.containsKey(sess.getEvent_start())){
                sessionGroupHash.put(sess.getEvent_start(), new ArrayList<Session>());
            }
            if(!sessionGroupDayHash.containsKey(sess.getStart_date())){
                sessionGroupDayHash.put(sess.getStart_date(), new ArrayList<Session>());
            }

            if(!venueByName.containsKey(sess.getVenue())){
                venueByName.put(sess.getVenue(), new ArrayList<Session>());
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
            venueByName.get(sess.getVenue()).add(sess);
            sessionByKey.put(sess.getEvent_key(), sess);
            sessionById.put(sess.getId(), sess);

        }
    }
    public ArrayList<Call> getUsersAttendingSession(final String session, final SimpleResponsehandler callback){
        final ArrayList<Call> calls = new ArrayList<>();
        calls.add(SessionData.getInstance().getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                final Session sess = SessionData.getInstance().getSessionById().get(session);
                if(sess.getAttendees() == null) {
                    Call call = RestClient.getInstance().get().getUsersAttendingSession(session);
                    calls.add(call);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Response response, Retrofit retrofit) {
                            List<User> attendees = (List<User>) response.body();
                            sess.setAttendees(attendees);
                            callback.handleResponse();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            callback.handleError(t);
                            Log.e("usrsAttendSess error", t.getMessage());

                        }
                    });
                }
                else{
                    callback.handleResponse();
                }
            }

            @Override
            public void handleError(Throwable t) {

            }
        }));

        return calls;
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
