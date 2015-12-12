package co.newco.newco_android.network;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import co.newco.newco_android.interfaces.RestAPI;
import retrofit.Retrofit;
import retrofit.GsonConverterFactory;


/**
 * Created by jayd on 10/14/15.
 * This is a singleton class
 */

public class RestClient {
    private  String API_KEY = "08d2f1d3e2dfe3a420b228ad73413cb7";
    private static RestClient instance = null;
    private RestAPI restApi;
    private String root;

    public static RestClient getInstance(){
        if(instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    public void setRoot(String newRoot){
        root = newRoot;
        setupRestClient();
    }

    public RestAPI get() {
        return restApi;
    }

    private void setupRestClient() {

        OkHttpClient client = new OkHttpClient();
        // add default query parameters
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                HttpUrl url = chain.request().httpUrl().newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .addQueryParameter("format", "json")
                        .build();
                Request request = chain.request().newBuilder().url(url).build();

                return chain.proceed(request);
            }
        });
        // purely for debugging
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
                .baseUrl(root)
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        restApi = retrofit.create(RestAPI.class);
    }
}
