package co.newco.newco_android.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.AppController;
import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Interfaces.StringResponseHandler;
import co.newco.newco_android.Models.Session;
import co.newco.newco_android.Models.Speaker;
import co.newco.newco_android.Models.User;
import co.newco.newco_android.Network.CurrentUserData;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.Network.UsersData;
import co.newco.newco_android.R;
import retrofit.Call;

public class SessionInfoActivity extends ActionBarActivity {
    ActionBarActivity activity;
    List<Call> calls;
    SessionData sessionData = SessionData.getInstance();
    CurrentUserData currentUserData = CurrentUserData.getInstance();
    Integer sessionId;
    Session session;
    TextView header;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_info);
        calls = new ArrayList<>();

        activity = this;
        Intent intent = getIntent();
        sessionId = intent.getIntExtra("sessionId", 0);


        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        header = (TextView) findViewById(R.id.header);

        btnSignup = (Button) findViewById(R.id.btn_signup);
        if(currentUserData.getCurrentUserKey() != null){
            btnSignup.setVisibility(View.VISIBLE);
        }

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
    public void onResume(){
        super.onResume();
        calls.add(sessionData.getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                session = sessionData.getSessions().get(sessionId);

                setHeader();
                setContent();
                setSignup();

                calls.addAll(sessionData.getUsersAttendingSession(session.getId(), new SimpleResponsehandler() {
                    @Override
                    public void handleResponse() {
                        setAttendees();
                    }

                    @Override
                    public void handleError(Throwable t) {
                        Log.e("Error", t.getMessage());
                    }
                }));
            }

            @Override
            public void handleError(Throwable t) {
                return;
            }
        }));

    }

    private void setAttendees() {
        LinearLayout attendees = (LinearLayout) findViewById(R.id.sessionAttendeesList);
        List<User> attendeesList = session.getAttendees();
        for(int i = 0; i < Math.min(attendeesList.size(), 10); i++){
            User user = attendeesList.get(i);
            TextView name = new TextView(activity);
            name.setText(user.getName());
            name.setTextColor(Color.parseColor("#000000"));
            attendees.addView(name);
        }
    }

    private void setSignup() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calls.add(currentUserData.signupForSession(session.getId(), new StringResponseHandler() {
                    @Override
                    public void handleResponse(String res) {
                        AppController.getInstance().Toast(res);
                    }

                    @Override
                    public void handleError(Throwable t) {
                    }
                }));
            }
        });
    }

    private void setContent() {
        TextView seatsTitle = (TextView) findViewById(R.id.sessionSeatsTitle);
        seatsTitle.setText(session.getSeats_title());

        TextView seatsStatus = (TextView) findViewById(R.id.sessionSeatsStatus);
        seatsStatus.setText(session.getSeats_status());

        TextView day = (TextView) findViewById(R.id.sessionDay);
        day.setText(session.getEvent_start_weekday() + ", " + session.getEvent_start_month() + " " + session.getEvent_start_day());

        TextView time = (TextView) findViewById(R.id.sessionHours);
        time.setText(session.getEvent_start_time() + " - " + session.getEvent_end_time());

        TextView venue = (TextView) findViewById(R.id.sessionVenue);
        venue.setText(session.getVenue());

        TextView addr = (TextView) findViewById(R.id.sessionAddr);
        addr.setText(session.getAddress());

        LinearLayout presenters = (LinearLayout) findViewById(R.id.sessionPresentersList);

        for(Speaker speaker : session.getSpeakers()){
            TextView name = new TextView(activity);
            name.setText(speaker.getName());
            name.setTextColor(Color.parseColor("#000000"));
            presenters.addView(name);
        }
    }

    private void setHeader() {
        header.setText(session.getName());
    }
}
