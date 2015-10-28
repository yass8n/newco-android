package co.newco.newco_android.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.Adapters.SessionListAdapter;
import co.newco.newco_android.AppController;
import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.R;
import co.newco.newco_android.Models.Session;
import retrofit.Call;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class SessionListActivity extends ActionBarActivity {
    private AppController appController = AppController.getInstance();
    private SessionData sessionData = SessionData.getInstance();
    private List<Session> sessions;
    private StickyListHeadersListView sessionsList;
    private List<Call> calls;
    private ActionBarActivity activity;
    ImageButton btnMenu;
    TextView loading;

    private SlidingMenu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);
        activity = this;
        calls = new ArrayList<>();

        loading = (TextView) findViewById(R.id.loading);

        Button schedule = (Button) findViewById(R.id.btn_schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SessionListActivity.class));
            }
        });
        Button directory = (Button) findViewById(R.id.btn_directory);
        directory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DirectoryActivity.class));
            }
        });
        btnMenu = (ImageButton) findViewById(R.id.btn_menu);
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
        calls.add(sessionData.getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                sessions = sessionData.getSessions();
                menu = appController.createSliderMenu(activity);
                btnMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (menu.isMenuShowing()) {
                            menu.showContent();
                        } else {
                            menu.toggle(true);
                        }
                    }
                });
                SessionListAdapter adapter = new SessionListAdapter(activity, sessions, null);
                sessionsList.setAdapter(adapter);
                loading.setVisibility(View.GONE);
            }
        }));
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