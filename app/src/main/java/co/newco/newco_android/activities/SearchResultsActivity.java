package co.newco.newco_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.adapters.SessionListAdapter;
import co.newco.newco_android.interfaces.SimpleResponsehandler;
import co.newco.newco_android.Models.Session;
import co.newco.newco_android.Models.User;
import co.newco.newco_android.network.SessionData;
import co.newco.newco_android.network.UsersData;
import co.newco.newco_android.R;
import retrofit.Call;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class SearchResultsActivity extends ActionBarActivity {
    private ActionBarActivity activity;
    private List<Call> calls;
    private SessionData sessionData = SessionData.getInstance();
    private UsersData usersData = UsersData.getInstance();
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        activity = this;
        calls = new ArrayList<>();
        Intent intent = getIntent();
        query = intent.getStringExtra("query").toLowerCase();
        TextView header = (TextView) findViewById(R.id.header);
        header.setText(query);

        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        venuesList.removeAllViews();
        LinearLayout presentersList = (LinearLayout) findViewById(R.id.presentersList);
        presentersList.removeAllViews();
        LinearLayout companiesList = (LinearLayout) findViewById(R.id.companiesList);
        companiesList.removeAllViews();
        LinearLayout attendeesList = (LinearLayout) findViewById(R.id.attendeesList);
        attendeesList.removeAllViews();
        StickyListHeadersListView sessionsList = (StickyListHeadersListView) findViewById(R.id.sessionList);

        // search in venues
        for(final String venue : sessionData.getVenueByName().keySet()){
            if(sessionData.getVenueByName().get(venue).get(0).getVenue().toLowerCase().contains(query)
                    || sessionData.getVenueByName().get(venue).get(0).getAddress().toLowerCase().contains(query)){
                venues.add(venue);
                View view = getLayoutInflater().inflate(R.layout.user_list_item, null);
                venuesList.addView(view);
                TextView title = (TextView) view.findViewById(R.id.row_title);
                title.setText(venue);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, VenueInfoActivity.class);
                        intent.putExtra("name", venue);
                        startActivity(intent);
                    }
                });

            }
        }

        //search in presenters
        for(final User presenter : usersData.getSpeakers()){
            if(presenter.getAbout().toLowerCase().contains(query) || presenter.getCompany().toLowerCase().contains(query) ||
                    presenter.getName().toLowerCase().contains(query)){
                presenters.add(presenter);
                View view = getLayoutInflater().inflate(R.layout.user_list_item, null);
                presentersList.addView(view);
                TextView title = (TextView) view.findViewById(R.id.row_title);
                title.setText(presenter.getName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, UserInfoActivity.class);
                        intent.putExtra("username", presenter.getUsername());
                        startActivity(intent);
                    }
                });
            }
        }

        //search in companies
        for(final User company : usersData.getCompanies()){
            if(company.getAbout().toLowerCase().contains(query) || company.getCompany().toLowerCase().contains(query) ||
                    company.getName().toLowerCase().contains(query)){
                companies.add(company);
                View view = getLayoutInflater().inflate(R.layout.user_list_item, null);
                companiesList.addView(view);
                TextView title = (TextView) view.findViewById(R.id.row_title);
                title.setText(company.getName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, UserInfoActivity.class);
                        intent.putExtra("username", company.getUsername());
                        startActivity(intent);
                    }
                });
            }
        }

        //search in attendees
        for(final User attendee : usersData.getAttendees()){
            if(attendee.getAbout().toLowerCase().contains(query) || attendee.getCompany().toLowerCase().contains(query) ||
                    attendee.getName().toLowerCase().contains(query)){
                attendees.add(attendee);
                View view = getLayoutInflater().inflate(R.layout.user_list_item, null);
                attendeesList.addView(view);
                TextView title = (TextView) view.findViewById(R.id.row_title);
                title.setText(attendee.getName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, UserInfoActivity.class);
                        intent.putExtra("username", attendee.getUsername());
                        startActivity(intent);
                    }
                });
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
        setListViewHeightBasedOnChildren(sessionsList);
    }
    public static void setListViewHeightBasedOnChildren(StickyListHeadersListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}

