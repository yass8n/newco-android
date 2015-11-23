package co.newco.newco_android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import co.newco.newco_android.R;

public class VenueInfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_info);

        Intent intent = getIntent();
        String venueName = intent.getStringExtra("name");

    }

}
