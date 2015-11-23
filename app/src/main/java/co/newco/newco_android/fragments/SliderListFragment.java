package co.newco.newco_android.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import co.newco.newco_android.Activities.SearchResultsActivity;
import co.newco.newco_android.Activities.UserInfoActivity;
import co.newco.newco_android.Activities.VenueInfoActivity;
import co.newco.newco_android.Activities.VenuesListActivity;
import co.newco.newco_android.AppController;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.R;
import co.newco.newco_android.Activities.SessionTypeListActivity;

/**
 * Created by jayd on 10/22/15.
 */
public class SliderListFragment extends ListFragment {
    List<String> cats;
    SlidingMenu menu;

    public SliderListFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View list =  inflater.inflate(R.layout.slider_list, null);
        //headerView = getLayoutInflater(savedInstanceState).inflate(R.layout.slider_list_header_row, null);
        return list;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SliderListAdapter adapter = new SliderListAdapter(getActivity());

        //add venues button
        adapter.add(new SliderItem("Search", "search"));
        adapter.add(new SliderItem("Venues", "text"));

        //we assume we have this data
        cats = new ArrayList<String>(SessionData.getInstance().getColorsHash().keySet());
        Collections.sort(cats, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareToIgnoreCase(rhs);            }
        });
        for (int i = 0; i < cats.size(); i++) {
            adapter.add(new SliderItem(cats.get(i), "cat"));
        }
        //if(headerView != null) getListView().addHeaderView(headerView);

        setListAdapter(adapter);
    }

    public void setMenu(SlidingMenu menu) {
        this.menu = menu;
    }

    private class SliderItem {
        public String text;
        public String type;
        public SliderItem(String text, String type) {
            this.type = type;
            this.text = text;
        }
    }

    public class SliderListAdapter extends ArrayAdapter<SliderItem> {

        public SliderListAdapter(Context context) {
            super(context, 0);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            final SliderItem item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.slider_list_row, null);
            }

            ImageView circle = (ImageView) convertView.findViewById(R.id.imageButton);
            TextView title = (TextView) convertView.findViewById(R.id.row_title);

            if(item.type == "search"){
                title.setVisibility(View.GONE);

                EditText search = new EditText(getActivity());
                search.setBackgroundColor(Color.WHITE);
                search.setTextColor(Color.BLACK);
                search.setInputType(InputType.TYPE_CLASS_TEXT);
                search.setSingleLine();
                RelativeLayout row = (RelativeLayout) convertView.findViewById(R.id.listRow);
                row.addView(search);
                search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        menu.toggle();
                        Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                        intent.putExtra("query", v.getText().toString());
                        startActivity(intent);
                        return false;
                    }
                });

            }
            if(item.type == "text" || item.type == "cat"){
                title.setText(item.text);
            }
            if(item.type == "cat") {
                GradientDrawable background = (GradientDrawable) circle.getBackground();
                background.setColor(Color.parseColor(SessionData.getInstance().getColorsHash().get(item.text)));
            }
            else{
                circle.setVisibility(View.GONE);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu.toggle();
                    Intent intent = null;
                    if (item.text == "Venues"){
                        intent = new Intent(getActivity(), VenuesListActivity.class);
                        startActivity(intent);
                    }
                    else if(item.type == "cat"){
                        //close
                        menu.showContent();
                        AppController.getInstance().Toast("Session type:" + "\"" + item.text + "\"");
                        intent = new Intent(getActivity(), SessionTypeListActivity.class);
                        intent.putExtra("sessionType", item.text);
                        startActivity(intent);
                    }
                }
            });
            return convertView;
        }

    }
}