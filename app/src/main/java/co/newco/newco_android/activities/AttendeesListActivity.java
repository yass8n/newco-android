package co.newco.newco_android.activities;

import android.app.Activity;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.AppController;
import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.Network.UserData;
import co.newco.newco_android.R;
import co.newco.newco_android.fragments.SliderListFragment;
import co.newco.newco_android.models.User;
import retrofit.Call;

public class AttendeesListActivity extends ActionBarActivity {
    private AppController appController = AppController.getInstance();
    private SessionData sessionData = SessionData.getInstance();
    private UserData userData = UserData.getInstance();
    private ListView attendeesListView;
    private SlidingMenu menu;
    private List<User> attendeesList;
    private List<Call> calls;
    ActionBarActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendees_list);
        activity = this;
        calls = new ArrayList<>();
        attendeesListView = (ListView) findViewById(R.id.attendeesList);

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
        calls.add(userData.getUsersData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                attendeesList = userData.getAttendees();
                InteractiveArrayAdapter adapter = new InteractiveArrayAdapter(activity);
                attendeesListView.setAdapter(adapter);
            }
        }));

        calls.add(sessionData.getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                appController.createSliderMenu(activity);
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
            super(context, R.layout.user_list_item, attendeesList);
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
            text.setText(attendeesList.get(position).getName() + " - " + attendeesList.get(position).getRole());
            return view;
        }
    }
}
