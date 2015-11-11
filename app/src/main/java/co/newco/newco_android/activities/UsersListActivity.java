package co.newco.newco_android.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.AppController;
import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.Network.UsersData;
import co.newco.newco_android.R;
import co.newco.newco_android.Models.User;
import retrofit.Call;

public class UsersListActivity extends ActionBarActivity {
    private AppController appController = AppController.getInstance();
    private SessionData sessionData = SessionData.getInstance();
    private UsersData usersData = UsersData.getInstance();
    private ListView usersListView;
    private SlidingMenu menu;
    private List<User> usersList;
    private List<Call> calls;
    ActionBarActivity activity;
    TextView loading;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        activity = this;
        Intent intent = getIntent();
        role = intent.getStringExtra("role");
        calls = new ArrayList<>();
        usersListView = (ListView) findViewById(R.id.attendeesList);
        loading = (TextView) findViewById(R.id.loading);
    }

    @Override
    public void onPause() {
        super.onPause();
        //cancel all pending calls so the fucking callback doesn't get called
        // call call call
        for(Call call : calls){
            if(call != null) call.cancel();
        }
        calls = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        usersList = new ArrayList<User>();

        calls.add(usersData.getUsersData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                switch (role){
                    case "speaker":
                        usersList = usersData.getSpeakers();
                        break;
                    case "volunteer":
                        usersList = usersData.getVolunteers();
                        break;
                    case "attendee":
                        usersList = usersData.getAttendees();
                        break;
                    }

                //BLARGH this is dumb and I hate it
                if(role.compareTo("company") == 0) {
                    calls.add(UsersData.getInstance().buildCompanyData(new SimpleResponsehandler() {
                        @Override
                        public void handleResponse() {
                            usersList = usersData.getCompanies();
                            InteractiveArrayAdapter adapter = new InteractiveArrayAdapter(activity);
                            usersListView.setAdapter(adapter);
                            loading.setVisibility(View.GONE);
                        }

                        @Override
                        public void handleError(Throwable t) {
                            return;
                        }
                    }));
                }
                else{
                    InteractiveArrayAdapter adapter = new InteractiveArrayAdapter(activity);
                    usersListView.setAdapter(adapter);
                    loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void handleError(Throwable t) {
                return;
            }
        }));

        calls.add(sessionData.getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                appController.createSliderMenu(activity);
            }

            @Override
            public void handleError(Throwable t) {
                return;
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attendees_list, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class InteractiveArrayAdapter extends ArrayAdapter<User> {

        private final Activity context;

        public InteractiveArrayAdapter(Activity context) {
            super(context, R.layout.user_list_item, usersList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                LayoutInflater inflator = context.getLayoutInflater();
                view = inflator.inflate(R.layout.user_list_item, null);
            } else {
                view = convertView;
            }

            TextView text = (TextView) view.findViewById(R.id.row_title);
            text.setText(usersList.get(position).getName() + " - " + usersList.get(position).getRole());
            return view;
        }
    }
}
