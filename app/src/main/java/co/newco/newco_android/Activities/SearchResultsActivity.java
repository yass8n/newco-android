package co.newco.newco_android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.Adapters.SessionListAdapter;
import co.newco.newco_android.AppController;
import co.newco.newco_android.Interfaces.SimpleResponsehandler;
import co.newco.newco_android.Models.Session;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.R;
import retrofit.Call;

public class SearchResultsActivity extends ActionBarActivity {
    private List<Call> calls;
    private SessionData sessionData = SessionData.getInstance();
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Intent intent = getIntent();
        query = intent.getStringExtra("query");
    }


    @Override
    public void onPause() {
        super.onPause();
        //cancel all pending calls so the fucking callback doesn't get called
        // call call call
        for (Call call : calls) {
            if (call != null) call.cancel();
        }
        calls = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        calls.add(sessionData.getSessionData(new SimpleResponsehandler() {
            @Override
            public void handleResponse() {

            }

            @Override
            public void handleError(Throwable t) {
                return;
            }
        }));
    }
}