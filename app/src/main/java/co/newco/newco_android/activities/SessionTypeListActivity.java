package co.newco.newco_android.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.newco.newco_android.Adapters.SessionListAdapter;
import co.newco.newco_android.AppController;
import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.R;
import co.newco.newco_android.Models.Session;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class SessionTypeListActivity extends ActionBarActivity {
    private AppController appController = AppController.getInstance();
    private SessionData sessionData = SessionData.getInstance();
    private List<Session> sessions;
    private StickyListHeadersListView sessionsList;
    private String sessionType;

    private SlidingMenu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_type_list);
        final ActionBarActivity activity = this;
        Intent intent = getIntent();
        sessionType = intent.getStringExtra("sessionType");



        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView header = (TextView) findViewById(R.id.header);

        header.setText(sessionType);


        sessionsList = (StickyListHeadersListView) findViewById(R.id.sessionList);

        sessionData.getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                sessions = sessionData.getSessions();
                menu = appController.createSliderMenu(activity);

                ArrayList<Session> filteredSessions = new ArrayList<Session>();
                for(Session sess : sessions){
                    List<String> types = Arrays.asList(sess.getEvent_type().split(", "));
                    if(types.contains(sessionType)){
                        filteredSessions.add(sess);
                    }
                }

                SessionListAdapter adapter = new SessionListAdapter(activity, filteredSessions, sessionType);
                sessionsList.setAdapter(adapter);
            }

            @Override
            public void handleError(Throwable t) {
                return;
            }
        });


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

    @Override
    public boolean onCreateOptionsMenu(Menu notMyMenu) {
        if (menu.isMenuShowing()) {
            menu.showContent();
        } else {
            menu.toggle(true);
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.showContent();
        } else {
            super.onBackPressed();
        }
    }
}