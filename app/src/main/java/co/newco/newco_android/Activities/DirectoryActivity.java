package co.newco.newco_android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import co.newco.newco_android.R;

public class DirectoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

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

}
