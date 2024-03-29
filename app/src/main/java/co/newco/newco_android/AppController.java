package co.newco.newco_android;

import android.app.Application;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import co.newco.newco_android.network.RestClient;
import co.newco.newco_android.network.SessionData;
import co.newco.newco_android.network.UsersData;
import co.newco.newco_android.Fragments.SliderListFragment;

/**
 * Created by jayd on 10/17/15.
 * Follows a singleton pattern to share resources across activities
 */
public class AppController extends Application {
    private static AppController mInstance;
    private String currentUrl = "http://newcobaybridgefestivals2015.sched.org";


    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    protected void initSingletons() {
        RestClient.getInstance().setRoot(currentUrl);
        SessionData.getInstance();
        UsersData.getInstance();
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
        RestClient.getInstance().setRoot(currentUrl);
    }

    public SlidingMenu createSliderMenu(ActionBarActivity activity) {
        SlidingMenu menu = new SlidingMenu(activity);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setTouchmodeMarginThreshold(500);
        menu.setBehindWidth(800);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_frame);
        SliderListFragment sl = new SliderListFragment();
        sl.setMenu(menu);
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame, sl)
                .commit();
        return menu;
    }

    public void Toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}

