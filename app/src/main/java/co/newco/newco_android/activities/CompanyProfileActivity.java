package co.newco.newco_android.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.okhttp.Response;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.newco.newco_android.Network.RestClient;
import co.newco.newco_android.R;
import co.newco.newco_android.fragments.ProductFragment;
import co.newco.newco_android.models.Investor;
import co.newco.newco_android.models.Job;
import co.newco.newco_android.models.News;
import co.newco.newco_android.models.Session;
import co.newco.newco_android.models.User;
import co.newco.newco_android.objects.CustomBaseAdapter;
import co.newco.newco_android.objects.Global;
import co.newco.newco_android.objects.OnSwipeTouchListener;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;


public class CompanyProfileActivity extends ActionBarActivity {
    FragmentPagerAdapter adapterViewPager;
    private Context context;
    private static CustomBaseAdapter<Investor> investorAdapter;
    private static CustomBaseAdapter<Job> jobAdapter;
    private static CustomBaseAdapter<News> newsAdapter;
    private LayoutInflater inflater;
    private boolean block_scroll = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        initializeVariables();
    }

    private void initializeVariables(){
        context = this;
        inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        initSnapshotButtons();
        initInvestors();
        initJobs();
        initNews();
        initViewPager();
    }
    private void initSnapshotButtons() {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout snapshots = (LinearLayout) findViewById(R.id.linear_snapshots);
        for(int i = 0; i < 3; i++) {
            Button button = (Button) inflater.inflate(R.layout.snapshot_button, snapshots, false);
            button.setText("Websites");
            snapshots.addView(button);
        }
    }
    private void initViewPager(){
        ViewPager v_pager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        v_pager.setAdapter(adapterViewPager);
        setViewPageIndicator(v_pager);
    }
    private void initInvestors(){
        View investors_feed = findViewById(R.id.investors_feed);
        ((TextView) investors_feed.findViewById(R.id.list_title)).setText("Investors");
        final LinearLayout investors_list = (LinearLayout) investors_feed.findViewById(R.id.list_feed);
        final ArrayList<Investor> investors = new ArrayList<>();
        for (int i = 0; i < 3; i ++){
            View view = inflater.inflate(R.layout.investors_fragment, investors_list, false);
            TextView round = (TextView) view.findViewById(R.id.co_round);
            TextView money = (TextView) view.findViewById(R.id.money);
            Investor investor = new Investor();
            investor.money = i;
            investor.round = "SEED";
            investor.pictures.add("URL");
            investors.add(investor);
            round.setText(investor.round);
            money.setText("$" + Integer.toString(investor.money) + "M");
            investors_list.addView(view);
        }
    }
    private void initJobs(){
        View jobs_feed = findViewById(R.id.jobs_feed);
        ((TextView) jobs_feed.findViewById(R.id.list_title)).setText("Jobs");
        final LinearLayout jobs_list = (LinearLayout) jobs_feed.findViewById(R.id.list_feed);
        final ArrayList<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 3; i ++){
            View view = inflater.inflate(R.layout.jobs_fragment, jobs_list, false);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView duration = (TextView) view.findViewById(R.id.duration);
            TextView location = (TextView) view.findViewById(R.id.location);
            TextView department = (TextView) view.findViewById(R.id.department);
            Job job = new Job();
            job.department = "Hardware Engineer";
            job.title = "Customer Experience Specialist";
            job.duration = "Full Time";
            job.location = "New York";
            jobs.add(job);
            title.setText(job.title);
            department.setText(job.department);
            duration.setText(job.duration);
            location.setText(job.location);
            jobs_list.addView(view);
        }
    }
    private void initNews(){
        View news_feed = findViewById(R.id.news_feed);
        ((TextView) news_feed.findViewById(R.id.list_title)).setText("News");
        final LinearLayout news_list = (LinearLayout) news_feed.findViewById(R.id.list_feed);
        final ArrayList<News> news_array = new ArrayList<>();
        for (int i = 0; i < 3; i ++){
            View view = inflater.inflate(R.layout.news_fragment, news_list, false);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView link = (TextView) view.findViewById(R.id.link);
            News news = new News();
            news.title = "How a mattress startup made $20 Million in the first 10 months";
            news.link = "MARKETWATCH.COM";
            news_array.add(news);
            title.setText(news.title);
            link.setText(news.link);
            news_list.addView(view);
        }
    }
    private void setViewPageIndicator(final ViewPager v_pager){
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
        RelativeLayout next_page = (RelativeLayout) findViewById(R.id.next_page);
        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage(v_pager, true);
            }
        });
    }
    private void nextPage(ViewPager v_pager, boolean forward){
        if (forward) {
            v_pager.setCurrentItem(v_pager.getCurrentItem() + 1, true);
        }else{
            v_pager.setCurrentItem(v_pager.getCurrentItem() - 1, true);
        }
    }
    private void lightUpBulb(int page){
        final LinearLayout indicator = (LinearLayout) findViewById(R.id.view_page_indicator);
        for (int i = 0; i < MyPagerAdapter.NUM_ITEMS; i++) {
            ImageView bulb = (ImageView) indicator.getChildAt(i);
            if (i == page) {
                bulb.setImageResource(R.color.white);
            }else{
                bulb.setImageResource(R.color.gray);
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
//            MyFragment fragment = new MyFragment();
//            fragment.setTitle(titles.get(position));
//            return fragment;
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return ProductFragment.newInstance("Title 1", "Some very long description.Some very long description.");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return ProductFragment.newInstance("Title 2", "Some very long description.");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return ProductFragment.newInstance("Title 3", "Some very long description.");
                default:
                    return null;
            }
        }

    }

}
