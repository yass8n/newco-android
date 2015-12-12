package co.newco.newco_android.interfaces;

/**
 * Created by jayd on 11/10/15.
 */
public interface StringResponseHandler {
    void handleResponse(String s);
    void handleError(Throwable t);
}
