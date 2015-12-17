package co.newco.newco_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.adapters.SessionListAdapter;
import co.newco.newco_android.interfaces.SimpleResponsehandler;
import co.newco.newco_android.models.Session;
import co.newco.newco_android.network.SessionData;
import co.newco.newco_android.R;
import retrofit.Call;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class VenueInfoActivity extends ActionBarActivity {
    private List<Call> calls;
    private ActionBarActivity activity;
    private SessionData sessionData = SessionData.getInstance();
    private StickyListHeadersListView sessionsList;
    private TextView address;
    private String venueName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_info);
        calls = new ArrayList<>();
        activity = this;
        sessionsList = (StickyListHeadersListView) findViewById(R.id.sessionList);
        address = (TextView) findViewById(R.id.address);

        TextView header = (TextView) findViewById(R.id.header);

        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        venueName = intent.getStringExtra("name");

        header.setText(venueName);

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

                List<Session> sessions = sessionData.getVenueByName().get(venueName);
                address.setText(sessions.get(0).getAddress());
                sessionsList.setAdapter(new SessionListAdapter(activity, sessions, null));
            }

            @Override
            public void handleError(Throwable t) {
            }
        }));
    }

}
