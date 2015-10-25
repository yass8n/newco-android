package co.newco.newco_android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.newco.newco_android.R;

/**
 * Created by yass8n on 5/20/15.
 */
public class ProductFragment extends Fragment {
    // Store instance variables
    private String title;
    private String description;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    // newInstance constructor for creating fragment with arguments
    public static ProductFragment newInstance(String title, String description) {
        ProductFragment fragmentFirst = new ProductFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("description", description);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
        description = getArguments().getString("description");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        TextView view_title = (TextView) view.findViewById(R.id.title);
        view_title.setText(title);
        final TextView view_description = (TextView) view.findViewById(R.id.description);
        view_description.setText(description);
        view_description.setMovementMethod(new ScrollingMovementMethod());
//        view_description.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
//            @Override
//            public void onSwipeLeft() {
//                Global.log("LEFT");
//            }
//
//            @Override
//            public void onSwipeRight() {
//                Global.log("RIGHT");
//            }
//        });
        return view;
    }

}