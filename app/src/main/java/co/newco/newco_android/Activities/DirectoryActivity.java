package co.newco.newco_android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.AppController;
import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.R;
import retrofit.Call;

public class DirectoryActivity extends ActionBarActivity {
    private List<Call> calls;
    private ActionBarActivity acitivity;
    private ImageButton btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        calls = new ArrayList<>();
        acitivity = this;

        btnMenu = (ImageButton) findViewById(R.id.btn_menu);


        Button btn_presenters = (Button) findViewById(R.id.btn_presenters);
        btn_presenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UsersListActivity.class);
                intent.putExtra("role", "speaker");
                startActivity(intent);
            }
        });

        Button btn_companies = (Button) findViewById(R.id.btn_companies);
        btn_companies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UsersListActivity.class);
                intent.putExtra("role", "company");
                startActivity(intent);
            }
        });

        Button btn_volunteers = (Button) findViewById(R.id.btn_volunteers);
        btn_volunteers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UsersListActivity.class);
                intent.putExtra("role", "volunteer");
                startActivity(intent);
            }
        });

        Button btn_attendees = (Button) findViewById(R.id.btn_attendees);
        btn_attendees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UsersListActivity.class);
                intent.putExtra("role", "attendee");
                startActivity(intent);
            }
        });
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
        calls.add(SessionData.getInstance().getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                final SlidingMenu menu = AppController.getInstance().createSliderMenu(acitivity);
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
            }

            @Override
            public void handleError(Throwable t) {
                return;
            }
        }));
    }
}
