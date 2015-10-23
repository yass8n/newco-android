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
import co.newco.newco_android.R;
import co.newco.newco_android.fragments.SliderListFragment;
import co.newco.newco_android.models.User;

public class AttendeesListActivity extends ActionBarActivity {
    private AppController appController = AppController.getInstance();
    private ListView attendeesListView;
    private SlidingMenu menu;
    private List<User> attendeesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendees_list);
        final Activity activity = this;
        attendeesListView = (ListView) findViewById(R.id.attendeesList);

        appController.getUsersData(new AppController.handleResponse() {
            @Override
            public void handleResponse() {
                attendeesList = appController.getAttendees();
                InteractiveArrayAdapter adapter = new InteractiveArrayAdapter(activity);
                attendeesListView.setAdapter(adapter);
            }
        });


        menu = new SlidingMenu(this);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setTouchmodeMarginThreshold(500);
        menu.setBehindWidth(800);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_frame);
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.menu_frame, new SliderListFragment())
            .commit();
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
            super(context, R.layout.slider_list_row, attendeesList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                LayoutInflater inflator = context.getLayoutInflater();
                view = inflator.inflate(R.layout.slider_list_row, null);
            } else {
                view = convertView;
            }

            TextView text = (TextView) view.findViewById(R.id.row_title);
            text.setText(attendeesList.get(position).getName() + " - " + attendeesList.get(position).getRole());
            return view;
        }
    }
}
