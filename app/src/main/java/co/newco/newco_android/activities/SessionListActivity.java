package co.newco.newco_android.activities;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import co.newco.newco_android.AppController;
import co.newco.newco_android.R;
import co.newco.newco_android.fragments.SliderListFragment;
import co.newco.newco_android.models.Session;
import co.newco.newco_android.models.Speaker;


public class SessionListActivity extends ActionBarActivity {
    private AppController appController = AppController.getInstance();
    private List<Session> sessions;
    private Hashtable<String, ArrayList<Session>> sessionGroupDayHash;
    private ExpandableListView sessionsList;

    private SlidingMenu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);
        final Activity activity = this;
        sessionsList = (ExpandableListView) findViewById(R.id.sessionList);
        sessionsList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Doing nothing
                return true;
            }
        });
        sessionsList.setGroupIndicator(null);

        appController.getSessionData(new AppController.handleSessionResponse() {
            @Override
            public void handleSessionResponse() {
                sessions = appController.getSessions();
                sessionGroupDayHash = appController.getSessionGroupDayHash();
                MyAdapter adapter = new MyAdapter(activity);
                sessionsList.setAdapter(adapter);
                for (int i = 0; i < adapter.getGroupCount(); i++)
                    sessionsList.expandGroup(i);
            }
        });

        menu = new SlidingMenu(this);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setTouchmodeMarginThreshold(100);
        menu.setBehindWidth(800);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_frame);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame, new SliderListFragment())
                .commit();
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

    private class MyAdapter extends BaseExpandableListAdapter {


        public LayoutInflater inflater;
        public Activity activity;
        private ArrayList<String> keys;

        public MyAdapter(Activity act) {
            activity = act;
            inflater = act.getLayoutInflater();
            keys = new ArrayList<>(sessionGroupDayHash.keySet());
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return sessionGroupDayHash.get(keys.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            Session sess = (Session) getChild(groupPosition, childPosition);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.session_list_item, null);
            }


            TextView sessionName = (TextView) convertView.findViewById(R.id.sessionName);
            sessionName.setText(sess.getName());

            TextView sessionTime = (TextView) convertView.findViewById(R.id.sessionTime);
            sessionTime.setText(sess.getEvent_start_time() + " - " + sess.getEvent_end_time());

            TextView sessionSpeaker = (TextView) convertView.findViewById(R.id.sessionSpeaker);
            String speakers = "";
            // comma seperate speakers
            for(int i = 0; sess.getSpeakers() != null && i < sess.getSpeakers().size(); i++){speakers += sess.getSpeakers().get(i).getName() + (i+1 < sess.getSpeakers().size() ?  ", "  : "");};
            sessionSpeaker.setText(speakers);

            TextView sessionVenue = (TextView) convertView.findViewById(R.id.sessionVenue);
            sessionVenue.setText(sess.getVenue());

            TextView sessionSeats = (TextView) convertView.findViewById(R.id.sessionSeats);
            sessionSeats.setText(sess.getSeats_title());
            GradientDrawable background = (GradientDrawable) sessionSeats.getBackground();
            background.setColor(Color.parseColor("#000000"));

            RelativeLayout sessionItem = (RelativeLayout) convertView.findViewById(R.id.sessionItem);
            String colorFromEvent = sess.getSessionColor();

            background = (GradientDrawable) sessionItem.getBackground();
            background.setColor(Color.parseColor(colorFromEvent));

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return sessionGroupDayHash.get(keys.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return sessionGroupDayHash.get(keys.get(groupPosition));
        }

        @Override
        public int getGroupCount() {
            return keys.size();
        }

        @Override
        public void onGroupCollapsed(int groupPosition) {
            super.onGroupCollapsed(groupPosition);
        }

        @Override
        public void onGroupExpanded(int groupPosition) {
            super.onGroupExpanded(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.session_list_header, null);
            }
            TextView text = (TextView) convertView.findViewById(R.id.text);
            Session firstSess = sessionGroupDayHash.get(keys.get(groupPosition)).get(0);

            text.setText(firstSess.getEvent_start_weekday() + ", " + firstSess.getEvent_start_month() + " " + firstSess.getEvent_start_day());
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}