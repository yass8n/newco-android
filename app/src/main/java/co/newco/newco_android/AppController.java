package co.newco.newco_android;

import android.app.Application;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import co.newco.newco_android.Network.RestClient;
import co.newco.newco_android.Network.SessionData;
import co.newco.newco_android.Network.UserData;
import co.newco.newco_android.activities.SessionListActivity;
import co.newco.newco_android.fragments.SliderListFragment;
import co.newco.newco_android.models.Session;
import co.newco.newco_android.models.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

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

    public interface handleResponse{
        void handleResponse();
    }

    protected void initSingletons() {
        RestClient.getInstance().setRoot(currentUrl);
        SessionData.getInstance();
        UserData.getInstance();
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
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame, new SliderListFragment())
                .commit();
        return menu;
    }

    public void Toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }




}

