package co.newco.newco_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import co.newco.newco_android.AppController;
import co.newco.newco_android.interfaces.SimpleResponsehandler;
import co.newco.newco_android.network.CurrentUserData;
import co.newco.newco_android.R;
import retrofit.Call;

public class LoginActivity extends ActionBarActivity {
    private List<Call> calls;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSubmit;
    private CurrentUserData currentUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        calls = new ArrayList<>();
        currentUserData = CurrentUserData.getInstance();

        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        calls.addAll(currentUserData.userLogin(username, password, new SimpleResponsehandler() {
            @Override
            public void handleResponse() {
                AppController.getInstance().Toast(currentUserData.getCurrentUserKey());
                AppController.getInstance().Toast(currentUserData.getCurrentUser().getUsername());
                if (currentUserData.getCurrentUserKey() != null) {
                    startActivity(new Intent(getApplicationContext(), SessionListActivity.class));
                }
            }
            @Override
            public void handleError(Throwable t) {
                AppController.getInstance().Toast("Something went wrong, please try again.");
                Log.e("login error", t.getMessage());
            }
        }));
    }

    @Override
    public void onPause() {
        super.onPause();
        //cancel all pending calls so the fucking callback doesn't get called
        // call call call
        for(Call call : calls){
            if(call != null) call.cancel();
        }
        calls = new ArrayList<>();
    }
}
