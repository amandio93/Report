package com.example.amand.rep;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.amand.rep.model.User;
import com.example.amand.rep.model.UserLocalStore;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static String cod_user ="";
    public static String name_user ="";
    public static String email_user ="";
    public static String phone_user ="";

    private ArrayList<User> userList;

    UserLocalStore userLocalStore;

    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent login = getIntent();
        email_user = login.getStringExtra("email");*/

        userLocalStore = new UserLocalStore(this);
        email_user = userLocalStore.getLoggedInUser().email_user;

    }

    public void tvLogOut_onClick(View view){
        userLocalStore.clearData();
        userLocalStore.setUserLoggedIn(false);
        startActivity(new Intent(this, Login.class));
    }

    public void btnAccount_onClick(View view){
        startActivity(new Intent(getApplicationContext(), MyAccount.class));
    }

    public void btnReportList_onClick(View view){
        startActivity(new Intent(getApplicationContext(), ReportList.class));
    }

    public void btnReport_onClick(View view){
        startActivity(new Intent(getApplicationContext(), OpenReport.class));
    }

    private void showUserInfo(HashMap<String, String> postData){
        PostResponseAsyncTask showUserTask = new PostResponseAsyncTask(this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                Log.d("MyAccount", result);

                userList = new JsonConverter<User>().toArrayList(result, User.class);
                for(User u: userList){
                    name_user = u.name_user;
                    email_user = u.email_user;
                    phone_user = u.phone_user;
                    cod_user = String.valueOf(u.cod_user);
                }
            }
        });
        showUserTask.execute("http://10.0.3.3:81/estagio/rep/showUser.php");
        //showUserTask.execute("http://reptest.site88.net/showUser.php");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("log", String.valueOf(userLocalStore.getUserLoggedIn()));

        if (!email_user.equals("")){
            setUserDetail();
        }else {
            startActivity(new Intent(this, Login.class));
        }
    }

    private void setUserDetail(){
        HashMap<String, String> postData = new HashMap<>();
        postData.put("email_user", email_user);
        Log.d("email", MainActivity.email_user);

        if (!email_user.equals("")){
            showUserInfo(postData);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

}
