package co.newco.newco_android.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Models.Session;
import co.newco.newco_android.Models.User;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.R;
import retrofit.Call;

public class VenuesListActivity extends ActionBarActivity {
    private ActionBarActivity activity;
    private List<Call> calls;
    private SessionData sessionData = new SessionData();
    private List<String> venues;
    private TextView loading;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues_list);
        activity = this;
        listView = (ListView) findViewById(R.id.venueslist);
        loading = (TextView) findViewById(R.id.loading);
        calls = new ArrayList<>();

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
        calls.add(sessionData.getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                venues = new ArrayList<String>(sessionData.getVenueByName().keySet());
                Collections.sort(venues);
                listView.setAdapter(new InteractiveArrayAdapter(activity));
                loading.setVisibility(View.GONE);
            }

            @Override
            public void handleError(Throwable t) {
            }
        }));
    }
    private class InteractiveArrayAdapter extends ArrayAdapter<String> {

        private final Activity context;

        public InteractiveArrayAdapter(Activity context) {
            super(context, R.layout.user_list_item, venues);
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
            text.setText(venues.get(position));

            return view;
        }
    }
}
