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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import co.newco.newco_android.R;
import co.newco.newco_android.fragments.ProductFragment;
import co.newco.newco_android.models.Investor;
import co.newco.newco_android.models.Job;
import co.newco.newco_android.objects.CustomBaseAdapter;
import co.newco.newco_android.objects.Global;


public class CompanyProfileActivity extends ActionBarActivity {
    FragmentPagerAdapter adapterViewPager;
    private Context context;
    private static CustomBaseAdapter<Investor> investorAdapter;
    private static CustomBaseAdapter<Job> jobAdapter;
    private LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile_bottom);
        initializeVariables();
    }
    private void initializeVariables(){
        context = this;
        inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        initializeInvestors();
        initializeJobs();
        initializeNews();
        initializeViewPager();
    }
    private void initializeViewPager(){
        ViewPager v_pager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        v_pager.setAdapter(adapterViewPager);
        setViewPageIndicator(v_pager);
    }
    private void initializeInvestors(){
        class InvestorViewHolder {
            TextView round;
            LinearLayout images;
            TextView money;
        }
        View investors_feed = findViewById(R.id.investors_feed);
        ((TextView) investors_feed.findViewById(R.id.list_title)).setText("Investors");
        final ListView investors_list = (ListView) investors_feed.findViewById(R.id.list_feed);
        final ArrayList<Investor> investors = new ArrayList<>();
        for (int i = 0; i < 3; i ++){
            Investor investor = new Investor();
            investor.money = i;
            investor.round = "SEED";
            investor.pictures.add("URL");
            investors.add(investor);
        }
        investorAdapter = new CustomBaseAdapter<Investor>(investors) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                final InvestorViewHolder view_holder = new InvestorViewHolder();
                if (view == null) {
                    view = inflater.inflate(R.layout.investors_fragment, parent, false);
                    setViewHolder(view, view_holder);
                }
                final InvestorViewHolder holder = (InvestorViewHolder) view.getTag();
                final Investor investor = this.models.get(position);
                holder.round.setText(investor.round);
                holder.money.setText("$" + Integer.toString(investor.money) + "M");
                return view;
            }
            private void setViewHolder(View view, InvestorViewHolder view_holder){
                view_holder.round = (TextView) view.findViewById(R.id.co_round);
                view_holder.images = (LinearLayout) view.findViewById(R.id.co_investors);
                view_holder.money = (TextView) view.findViewById(R.id.money);
                view.setTag(view_holder);
            }
        };
        investors_list.setAdapter(investorAdapter);
        Global.setListViewHeightBasedOnChildren(investors_list);
    }
    private void initializeJobs(){
        class JobViewHolder {
            TextView title;
            TextView duration;
            TextView location;
            TextView department;
        }
        View jobs_feed = findViewById(R.id.jobs_feed);
        ((TextView) jobs_feed.findViewById(R.id.list_title)).setText("Jobs");
        final ListView jobs_list = (ListView) jobs_feed.findViewById(R.id.list_feed);
        final ArrayList<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 3; i ++){
            Job job = new Job();
            job.department = "Hardware Engineer";
            job.title = "Customer Experience Specialist";
            job.duration = "Full Time";
            job.location = "New York";
            jobs.add(job);
        }
        jobAdapter = new CustomBaseAdapter<Job>(jobs) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                final JobViewHolder view_holder = new JobViewHolder();
                if (view == null) {
                    view = inflater.inflate(R.layout.jobs_fragment, parent, false);
                    setViewHolder(view, view_holder);
                }
                final JobViewHolder holder = (JobViewHolder) view.getTag();
                final Job job = this.models.get(position);
                holder.title.setText(job.title);
                holder.department.setText(job.department);
                holder.duration.setText(job.duration);
                holder.location.setText(job.location);
                return view;
            }
            private void setViewHolder(View view, JobViewHolder view_holder){
                view_holder.title = (TextView) view.findViewById(R.id.title);
                view_holder.duration = (TextView) view.findViewById(R.id.duration);
                view_holder.location = (TextView) view.findViewById(R.id.location);
                view_holder.department = (TextView) view.findViewById(R.id.department);
                view.setTag(view_holder);
            }
        };
        jobs_list.setAdapter(jobAdapter);
        Global.setListViewHeightBasedOnChildren(jobs_list);
    }
    private void initializeNews(){
        View news_feed = findViewById(R.id.news_feed);
        ListView news_list = (ListView) news_feed.findViewById(R.id.list_feed);
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
            public void onPageSelected(int page) {lightUpBulb(page);}
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
        RelativeLayout next_page = (RelativeLayout) findViewById(R.id.next_page);
        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_pager.setCurrentItem(v_pager.getCurrentItem() + 1, true);
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
                    return ProductFragment.newInstance("title 1", "descadasd asd asd as d");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return ProductFragment.newInstance("title 2", "asdnasdjh kalsjhdkl asldha jkldf kldjs");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return ProductFragment.newInstance("title 3", "jadhsfjl klakdsf kljashdf l");
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
