package com.example.amand.rep;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amand.rep.model.User;
import com.example.amand.rep.model.UserLocalStore;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class Login extends AppCompatActivity implements AsyncResponse {

    EditText etEmail, etPassword;
    Button btnSignIn;
    UserLocalStore userLocalStore;

    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        userLocalStore = new UserLocalStore(this);
    }

    public void btnSignUp_onClick(View view){
        Intent register = new Intent(this, Register.class);
        startActivity(register);
    }

    public void btnSignIn_onClick(View view) {

        if (etEmail.getText().toString().equals("")) {
            Toast.makeText(this, "Por favor preencher o email.", Toast.LENGTH_SHORT).show();
        }else if (etPassword.getText().toString().equals("")){
            Toast.makeText(this, "Por favor preencher a password.", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String, String> postData = new HashMap<>();
            postData.put("email_user", etEmail.getText().toString());
            postData.put("password_user", etPassword.getText().toString());

            PostResponseAsyncTask task = new PostResponseAsyncTask(Login.this, postData);
            task.execute("http://10.0.3.3:81/estagio/rep/doLogin.php");
            //task.execute("http://reptest.site88.net/doLogin.php");
        }
    }

    @Override
    public void processFinish(String result) {
        if(result.equals("success")){
            User user = new User(etEmail.getText().toString());
            userLocalStore.storeUserData(user);
            userLocalStore.setUserLoggedIn(true);

            Intent main = new Intent(this, MainActivity.class);
            main.putExtra("email", etEmail.getText().toString());
            startActivity(main);
            finish();
            //Toast.makeText(this, "SUCCESS!!!", Toast.LENGTH_LONG).show();
        }else if(result.equals("failed")) {
            Toast.makeText(this, "Email e Password não correspondem.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
