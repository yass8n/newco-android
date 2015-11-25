package co.newco.newco_android.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.Adapters.SessionListAdapter;
import co.newco.newco_android.AppController;
import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Models.Session;
import co.newco.newco_android.Models.User;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.Network.UsersData;
import co.newco.newco_android.R;
import retrofit.Call;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class SearchResultsActivity extends ActionBarActivity {
    private List<Call> calls;
    private SessionData sessionData = SessionData.getInstance();
    private UsersData usersData = UsersData.getInstance();
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        calls = new ArrayList<>();
        Intent intent = getIntent();
        query = intent.getStringExtra("query").toLowerCase();
    }


    @Override
    public void onPause() {
        super.onPause();
        //cancel all pending calls so the fucking callback doesn't get called
        // call call call
        for (Call call : calls) {
            if (call != null) call.cancel();
        }
        calls = new ArrayList<>();
    }

    @Override
    public void onResume() {


        super.onResume();
        calls.add(sessionData.getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                calls.add(usersData.getUsersData(new SimpleResponsehandler() {
                    @Override
                    public void handleResponse() {
                        calls.addAll(usersData.buildCompanyData(new SimpleResponsehandler() {
                            @Override
                            public void handleResponse() {
                                search();
                            }

                            @Override
                            public void handleError(Throwable t) {
                                return;
                            }
                        }));
                    }

                    @Override
                    public void handleError(Throwable t) {
                        return;
                    }
                }));
            }

            @Override
            public void handleError(Throwable t) {
                return;
            }
        }));
    }
    // we need to search
    // session
    //   name
    //   location
    //   description
    //   companies
    //   attendees
    // users
    //   name
    //   description
    //   company
    // vendors
    //   name
    //   location
    private void search() {
        List<String> venues = new ArrayList<>();
        List<User> presenters = new ArrayList<>();
        List<User> companies = new ArrayList<>();
        List<User> attendees = new ArrayList<>();
        List<Session> sessions = new ArrayList<>();

        TextView loading = (TextView) findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        LinearLayout venuesList = (LinearLayout) findViewById(R.id.venueslist);
        LinearLayout presentersList = (LinearLayout) findViewById(R.id.presentersList);
        LinearLayout companiesList = (LinearLayout) findViewById(R.id.companiesList);
        LinearLayout attendeesList = (LinearLayout) findViewById(R.id.attendeesList);
        StickyListHeadersListView sessionsList = (StickyListHeadersListView) findViewById(R.id.sessionList);

        // search in venues
        for(String venue : sessionData.getVenueByName().keySet()){
            if(sessionData.getVenueByName().get(venue).get(0).getVenue().toLowerCase().contains(query)
                    || sessionData.getVenueByName().get(venue).get(0).getAddress().toLowerCase().contains(query)){
                venues.add(venue);
                View view = getLayoutInflater().inflate(R.layout.user_list_item, null);
                venuesList.addView(view);
                TextView title = (TextView) view.findViewById(R.id.row_title);
                title.setText(venue);
            }
        }

        //search in presenters
        for(User presenter : usersData.getSpeakers()){
            if(presenter.getAbout().toLowerCase().contains(query) || presenter.getCompany().toLowerCase().contains(query) ||
                    presenter.getName().toLowerCase().contains(query)){
                presenters.add(presenter);
                View view = getLayoutInflater().inflate(R.layout.user_list_item, null);
                presentersList.addView(view);
                TextView title = (TextView) view.findViewById(R.id.row_title);
                title.setText(presenter.getName());
            }
        }

        //search in companies
        for(User company : usersData.getCompanies()){
            if(company.getAbout().toLowerCase().contains(query) || company.getCompany().toLowerCase().contains(query) ||
                    company.getName().toLowerCase().contains(query)){
                companies.add(company);
                View view = getLayoutInflater().inflate(R.layout.user_list_item, null);
                presentersList.addView(view);
                TextView title = (TextView) view.findViewById(R.id.row_title);
                title.setText(company.getName());
            }
        }

        //search in attendees
        for(User attendee : usersData.getAttendees()){
            if(attendee.getAbout().toLowerCase().contains(query) || attendee.getCompany().toLowerCase().contains(query) ||
                    attendee.getName().toLowerCase().contains(query)){
                attendees.add(attendee);
                View view = getLayoutInflater().inflate(R.layout.user_list_item, null);
                presentersList.addView(view);
                TextView title = (TextView) view.findViewById(R.id.row_title);
                title.setText(attendee.getName());
            }
        }


        // search sessions
        //   name
        //   location
        //   description
        //   companies
        //   speakers
        for(Session sess : sessionData.getSessions()){
           if(sess.getName().toLowerCase().contains(query) || sess.getAddress().toLowerCase().contains(query) || sess.getVenue().toLowerCase().contains(query)){
               sessions.add(sess);
               continue;
           }
            // search in people
            List<User> sessionPeople = sess.getSpeakers() == null ? new ArrayList<User>() : new ArrayList<>(sess.getSpeakers());

            if(sess.getArtists() != null) sessionPeople.addAll(sess.getArtists());
            for(User user : sessionPeople){
                if(user.getName().toLowerCase().contains(query)){
                    sessions.add(sess);
                    break;
                }
            }
        }

        sessionsList.setAdapter(new SessionListAdapter(this, sessions, null));
    }
}