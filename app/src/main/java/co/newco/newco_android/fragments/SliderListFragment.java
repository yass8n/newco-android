package co.newco.newco_android.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import co.newco.newco_android.AppController;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.R;
import co.newco.newco_android.activities.AttendeesListActivity;
import co.newco.newco_android.activities.SessionTypeListActivity;
import co.newco.newco_android.models.Session;

/**
 * Created by jayd on 10/22/15.
 */
public class SliderListFragment extends ListFragment {
    List<String> cats;
    View headerView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View list =  inflater.inflate(R.layout.slider_list, null);
        //headerView = getLayoutInflater(savedInstanceState).inflate(R.layout.slider_list_header_row, null);
        return list;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SliderListAdapter adapter = new SliderListAdapter(getActivity());
        //we assume we have this data
        cats = new ArrayList<String>(SessionData.getInstance().getColorsHash().keySet());
        Collections.sort(cats, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareToIgnoreCase(rhs);            }
        });
        for (int i = 0; i < cats.size(); i++) {
            adapter.add(new SliderItem(cats.get(i)));
        }
        //if(headerView != null) getListView().addHeaderView(headerView);

        setListAdapter(adapter);
    }

    private class SliderItem {
        public String tag;
        public SliderItem(String tag) {
            this.tag = tag;
        }
    }

    public class SliderListAdapter extends ArrayAdapter<SliderItem> {

        public SliderListAdapter(Context context) {
            super(context, 0);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.slider_list_row, null);
            }
            TextView title = (TextView) convertView.findViewById(R.id.row_title);
            title.setText(getItem(position).tag);

            ImageView circle = (ImageView) convertView.findViewById(R.id.imageButton);
            GradientDrawable background = (GradientDrawable) circle.getBackground();
            background.setColor(Color.parseColor(SessionData.getInstance().getColorsHash().get(cats.get(position))));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(position) {
                        default:
                            AppController.getInstance().Toast("Session type:" + cats.get(position));
                            Intent intent = new Intent(getActivity(), SessionTypeListActivity.class);
                            intent.putExtra("sessionType", cats.get(position));
                            startActivity(intent);
                    }
                }
            });
            return convertView;
        }

    }
}