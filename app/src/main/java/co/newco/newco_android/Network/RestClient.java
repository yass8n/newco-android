package co.newco.newco_android.Network;

import com.squareup.okhttp.OkHttpClient;

import co.newco.newco_android.Interfaces.RestAPI;
import retrofit.Retrofit;
import retrofit.GsonConverterFactory;


/**
 * Created by jayd on 10/14/15.
 */
public class RestClient {

    private static RestAPI REST_CLIENT;
    private static String ROOT = "http://newcobaybridgefestivals2015.sched.org";


    static {
        setupRestClient();
    }

    public static RestAPI get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());
        /* Somehow you can add headers to every request, have yet to figure that out
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("User-Agent", "Your-App-Name");
                request.addHeader("Accept", "application/vnd.yourapi.v1.full+json");
            }
        };

*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        REST_CLIENT = retrofit.create(RestAPI.class);
    }
}
