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

import co.newco.newco_android.activities.SearchResultsActivity;
import co.newco.newco_android.activities.VenuesListActivity;
import co.newco.newco_android.AppController;
import co.newco.newco_android.network.SessionData;
import co.newco.newco_android.R;
import co.newco.newco_android.activities.SessionTypeListActivity;

/**
 * Created by jayd on 10/22/15.
 */
public class SliderListFragment extends ListFragment {
    List<String> cats;
    SlidingMenu menu;

    public SliderListFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View list =  inflater.inflate(R.layout.slider_list, null);

        EditText search = (EditText) list.findViewById(R.id.et_search);
        search.setBackgroundColor(Color.WHITE);
        search.setTextColor(Color.BLACK);
        search.setInputType(InputType.TYPE_CLASS_TEXT);
        search.setSingleLine();
        search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                menu.toggle(false);
                Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                intent.putExtra("query", v.getText().toString());
                startActivity(intent);
                return false;
            }
        });

        TextView venue = (TextView) list.findViewById(R.id.tv_venue);

        venue.setText("Venues");
        venue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle(false);
                Intent intent = new Intent(getActivity(), VenuesListActivity.class);
                startActivity(intent);
            }
        });



        return list;
    }

    @Override
    public void onPause(){
        super.onPause();
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

    public void setMenu(SlidingMenu menu) {
        this.menu = menu;
    }

    private class SliderItem {
        public String text;
        public SliderItem(String text) {
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
            title.setText(item.text);

            GradientDrawable background = (GradientDrawable) circle.getBackground();
            background.setColor(Color.parseColor(SessionData.getInstance().getColorsHash().get(item.text)));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu.toggle();
                    //close
                    menu.showContent();
                    AppController.getInstance().Toast("Session type:" + "\"" + item.text + "\"");
                    Intent intent = new Intent(getActivity(), SessionTypeListActivity.class);
                    intent.putExtra("sessionType", item.text);
                    startActivity(intent);
                }
            });
            return convertView;
        }

    }
}