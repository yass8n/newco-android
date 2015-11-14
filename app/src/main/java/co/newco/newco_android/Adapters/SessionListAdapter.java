package co.newco.newco_android.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import co.newco.newco_android.Activities.SessionInfoActivity;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.R;
import co.newco.newco_android.Models.Session;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by jayd on 10/23/15.
 */
public class SessionListAdapter extends BaseAdapter implements StickyListHeadersAdapter {


    private LayoutInflater inflater;
    private Activity activity;
    private List<Session> sessions;
    private String sessType;

    public SessionListAdapter(Activity act, List<Session> sess, String st) {
        activity = act;
        inflater = act.getLayoutInflater();
        sessions = sess;
        sessType = st;
    }
    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public Object getItem(int position) {
        return sessions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Session sess = (Session) getItem(position);

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
        String colorFromEvent = sessType == null ? sess.getSessionColor() : SessionData.getInstance().getColorsHash().get(sessType);

        background = (GradientDrawable) sessionItem.getBackground();
        background.setColor(Color.parseColor(colorFromEvent));

        sessionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SessionInfoActivity.class);
                intent.putExtra("sessionId", position);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.session_list_header, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.text);
        Session firstSess = sessions.get(position);

        text.setText(firstSess.getEvent_start_weekday() + ", " + firstSess.getEvent_start_month() + " " + firstSess.getEvent_start_day());
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return sessions.get(position).getStart_date().hashCode();
    }

}