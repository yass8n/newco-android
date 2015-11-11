package co.newco.newco_android.Interfaces;

import retrofit.Response;

/**
 * Created by jayd on 10/23/15.
 */
public interface SimpleResponsehandler {
    void handleResponse();
    void handleError(Throwable t);
}

