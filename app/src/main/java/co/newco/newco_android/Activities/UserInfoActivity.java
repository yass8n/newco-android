package co.newco.newco_android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.Adapters.SessionListAdapter;
import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Models.Session;
import co.newco.newco_android.Models.User;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.Network.UsersData;
import co.newco.newco_android.R;
import retrofit.Call;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class UserInfoActivity extends ActionBarActivity {
    private StickyListHeadersListView sessionsList;
    private UsersData usersData = UsersData.getInstance();
    private List<Session> sessions;
    private ActionBarActivity activity;
    private List<Call> calls;
    private String username;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        activity = this;
        calls = new ArrayList<>();
        sessions = new ArrayList<>();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sessionsList = (StickyListHeadersListView) findViewById(R.id.sessionList);

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
        calls.addAll(usersData.getUserSessions(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                user = usersData.getUserByUsername().get(username);
                setContent();
                sessions = user.getSessions();
                sessions = sessions == null ? new ArrayList<Session>() : sessions;
                SessionListAdapter adapter = new SessionListAdapter(activity, sessions, null);
                sessionsList.setAdapter(adapter);
            }

            @Override
            public void handleError(Throwable t) {

            }
        }));
    }

    private void setContent() {
        TextView name = (TextView) findViewById(R.id.name);
        String company = user.getCompany() == null || user.getCompany().length() < 1 ? "" : " @ " + user.getCompany();
        name.setText(user.getName() + company);
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(user.getAbout());
    }

}
