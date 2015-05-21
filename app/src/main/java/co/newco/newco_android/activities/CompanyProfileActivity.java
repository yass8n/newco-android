package co.newco.newco_android.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import co.newco.newco_android.R;
import co.newco.newco_android.fragments.ProductFragment;


public class CompanyProfileActivity extends ActionBarActivity {
    FragmentPagerAdapter adapterViewPager;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile_bottom);
        initializeVariables();
    }
    private void initializeVariables(){
        ViewPager v_pager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        v_pager.setAdapter(adapterViewPager);
        setViewPageIndicator(v_pager);
    }
    private void setViewPageIndicator(ViewPager v_pager){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout indicator = (LinearLayout) findViewById(R.id.view_page_indicator);
        for (int i = 0; i < MyPagerAdapter.NUM_ITEMS; i++){
            View bulb = inflater.inflate(R.layout.bulb, indicator, false);
            indicator.addView(bulb);
        }
        lightUpBulb(0);
        v_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int page) {
                lightUpBulb(page);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }
    private void lightUpBulb(int page){
        final LinearLayout indicator = (LinearLayout) findViewById(R.id.view_page_indicator);
        for (int i = 0; i < MyPagerAdapter.NUM_ITEMS; i++) {
            ImageView bulb = (ImageView) indicator.getChildAt(i);
            if (i == page) {
                bulb.setImageResource(R.color.white);
            }else{
                bulb.setImageResource(R.color.light_gray);
            }
        }
    }
    private static class MyPagerAdapter extends FragmentPagerAdapter {
        public static int NUM_ITEMS = 3;
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return ProductFragment.newInstance("title 1", "descadasd asd asd as d", position, NUM_ITEMS);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return ProductFragment.newInstance("title 2", "asdnasdjh kalsjhdkl asldha jkldf kldjs", position, NUM_ITEMS);
                case 2: // Fragment # 1 - This will show SecondFragment
                    return ProductFragment.newInstance("title 3", "jadhsfjl klakdsf kljashdf l", position, NUM_ITEMS);
                default:
                    return null;
            }
        }
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}
