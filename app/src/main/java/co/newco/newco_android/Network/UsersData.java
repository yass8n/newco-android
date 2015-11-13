package co.newco.newco_android.Network;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
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
public class UsersData {
    private static UsersData instance = null;
    private List<User> users = null;
    private List<User> speakers;
    private List<User> attendees;
    private List<User> volunteers;
    private List<User> companies = null;
    private String currentUserKey = null;

    public Hashtable<String, User> getUserByUsername() {
        return userByUsername;
    }

    private Hashtable<String, User> userByUsername = null;

    public Hashtable<String, User> getUserByEmail() {
        return userByEmail;
    }

    private Hashtable<String, User> userByEmail = null;

    public List<User> getCompanies() {
        return companies;
    }


    public List<User> getSpeakers() {
        return speakers;
    }

    public List<User> getVolunteers() {
        return volunteers;
    }

    public String getCurrentUserKey() {
        return currentUserKey;
    }

    public static UsersData getInstance(){
        if(instance == null) {
            instance = new UsersData();
        }
        return instance;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public List<User> getUsers() {
        return users;
    }

    public Call buildCompanyData(final SimpleResponsehandler callback){
        companies = new ArrayList<>();

        Call<List<Session>> call = SessionData.getInstance().getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                List<Session> sessions = SessionData.getInstance().getSessions();
                HashMap<String, Boolean> sessionsHash = new HashMap<String, Boolean>();
                for(Session sess : sessions){
                    String uname = "";
                    if(sess.getArtists() != null) uname = sess.getArtists().get(0).getUsername();
                    else continue;
                    sessionsHash.put(uname, true);
                }
                for(User u : users){
                    if(sessionsHash.containsKey(u.getUsername())) companies.add(u);
                }
                Collections.sort(companies, new Comparator<User>() {
                    @Override
                    public int compare(User lhs, User rhs) {
                       return lhs.getName().compareToIgnoreCase(rhs.getName());
                    }
                });

                callback.handleResponse();
            }

            @Override
            public void handleError(Throwable t) {
                return;
            }
        });
        return call;
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
        speakers = new ArrayList<>();
        volunteers = new ArrayList<>();
        userByUsername = new Hashtable<>();
        userByEmail = new Hashtable<>();

        for(User user : users){
            // put user in correct list for user role
            List<String> userRole = new ArrayList<>(Arrays.asList(user.getRole().split(", ")));
            if(userRole.contains("attendee")){
                attendees.add(user);
            }
            if(userRole.contains("speaker")){
                speakers.add(user);
            }
            if(userRole.contains("volunteer")){
                volunteers.add(user);
            }

            // create hashtable for easy lookup
            userByUsername.put(user.getUsername(), user);
            userByEmail.put(user.getEmail(), user);
        }
    }



    // this method has a terrible triple nested callback, sorry about that
    public ArrayList<Call> getUserSessIds(final String username, final SimpleResponsehandler callback) {
        final ArrayList<Call> calls = new ArrayList<>();
        // theoretically, if we're calling this then we should already have called the user data, but maybe not
        calls.add(getUsersData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                final User user = userByUsername.get(username);
                if (user.getSessions() == null) {
                    // thus begins callback hell
                    calls.add(SessionData.getInstance().getSessionData(new SimpleResponsehandler() {
                        @Override
                        public void handleResponse() {
                            //callback hell lol
                            calls.add(RestClient.getInstance().get().getUserSessIds(CurrentUserData.getInstance().getCurrentUserKey(), username));
                            calls.get(1).enqueue(new Callback() {
                                @Override
                                public void onResponse(Response response, Retrofit retrofit) {
                                    List<Session> sessions = new ArrayList<>();
                                    List<String> sessIds = (List<String>) response.body();
                                    Hashtable<String, Session> sessionByKey = SessionData.getInstance().getSessionByKey();
                                    for(String sess : sessIds){
                                        sessions.add(sessionByKey.get(sess));
                                    }
                                    user.setSessions(sessions);
                                    callback.handleResponse();
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    callback.handleError(t);
                                    Log.e("getUserSessionData error", t.getMessage());
                                }
                            });
                        }

                        @Override
                        public void handleError(Throwable t) {
                            callback.handleError(t);
                            Log.e("getSessionData error", t.getMessage());
                        }
                    }));
                }
                else{
                    callback.handleResponse();
                }
            }
            @Override
            public void handleError(Throwable t) {
                callback.handleError(t);
                Log.e("getSessionData error", t.getMessage());
            }
        }));


        return calls;
    }
}
