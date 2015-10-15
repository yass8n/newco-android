package co.newco.newco_android.activities;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.newco.newco_android.Network.RestClient;
import co.newco.newco_android.R;
import co.newco.newco_android.models.Session;
import co.newco.newco_android.models.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

public class SessionListActivity extends ActionBarActivity {
    private ListView sessionsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);
        sessionsList = (ListView) findViewById(R.id.sessionList);
        /*******
         *******
         *
         * This was added just to test retrofit
         *
         *******
         *******/
        RestClient restClient = RestClient.getInstance();
        restClient.setRoot("http://newcobaybridgefestivals2015.sched.org");
        Call<List<Session>> call = restClient.get().listSessions();
        call.enqueue(new Callback<List<Session>>() {
            @Override
            public void onResponse(retrofit.Response<List<Session>> response, Retrofit retrofit) {
                Log.e(response.toString(), "t");
                ArrayList<String> sessionsNames = new ArrayList<String>();
                for (Session sess : response.body()) {
                    Log.i(sess.getId(), " (" + (sess.getSpeakers() != null ? sess.getSpeakers().get(0).getName() : "none!") + ")");
                    sessionsNames.add(sess.getName());
                }
                StableArrayAdapter adapter = new StableArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, sessionsNames);
                sessionsList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Callback failure", t.getMessage());
            }
        });
        Call<User> userCall = restClient.get().getUser("yaseenaniss");
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(retrofit.Response<User> response, Retrofit retrofit) {
                Log.e("email", response.body().getEmail());

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Callback failure", t.getMessage());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_session_list, menu);
        return true;
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

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
