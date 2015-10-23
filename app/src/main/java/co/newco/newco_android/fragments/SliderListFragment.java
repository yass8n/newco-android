package co.newco.newco_android.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.AppController;
import co.newco.newco_android.R;

/**
 * Created by jayd on 10/22/15.
 */
public class SliderListFragment extends ListFragment {
    List<String> items;
    View headerView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View list =  inflater.inflate(R.layout.slider_list, null);
        items = new ArrayList<>();
        for(int i = 0; i < 20; i++) items.add("item"+ Integer.toString(i));
        //headerView = getLayoutInflater(savedInstanceState).inflate(R.layout.slider_list_header_row, null);
        return list;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SliderListAdapter adapter = new SliderListAdapter(getActivity());
        for (int i = 0; i < items.size(); i++) {
            adapter.add(new SliderItem(items.get(i), android.R.drawable.ic_menu_search));
        }

        //if(headerView != null) getListView().addHeaderView(headerView);

        setListAdapter(adapter);
    }

    private class SliderItem {
        public String tag;
        public int iconRes;
        public SliderItem(String tag, int iconRes) {
            this.tag = tag;
            this.iconRes = iconRes;
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

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(position) {
                        default:
                            AppController.getInstance().Toast("Error: Out of switch range!");
                    }

                }
            });
            return convertView;
        }

    }
}