package com.example.amand.rep;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amand.rep.model.User;
import com.example.amand.rep.model.UserLocalStore;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAccount extends AppCompatActivity {

    TextView tvName, tvPhone, tvEmail;
    private String strName, strPhone, strEmail, strCodUser;
    private ArrayList<User> userList;

    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);


        tvName = (TextView)findViewById(R.id.tvName);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        tvEmail = (TextView)findViewById(R.id.tvEmail);

        strCodUser = MainActivity.cod_user;
        strName = MainActivity.name_user;
        strPhone = MainActivity.phone_user;
        strEmail = MainActivity.email_user;

        tvName.setText(strName);
        tvEmail.setText(strEmail);
        tvPhone.setText(strPhone);

        userLocalStore = new UserLocalStore(this);

        /*BindDictionary<User> dict = new BindDictionary<User>();
        dict.addStringField(R.id.tvName, new StringExtractor<User>() {
            @Override
            public String getStringValue(User user, int position) {
                return user.name_user;
            }
        });

        dict.addStringField(R.id.tvPhone, new StringExtractor<User>() {
            @Override
            public String getStringValue(User user, int position) {
                return user.phone_user;
            }
        });

        dict.addStringField(R.id.tvEmail, new StringExtractor<User>() {
            @Override
            public String getStringValue(User user, int position) {
                return user.email_user;
            }
        });*/

    }

    public void showPasswordDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText etOldPassword = (EditText) dialogView.findViewById(R.id.etOldPassword);
        final EditText etNewPassword = (EditText) dialogView.findViewById(R.id.etNewPassword);
        final EditText etNewPasswordConf = (EditText) dialogView.findViewById(R.id.etNewPasswordConf);

        dialogBuilder.setTitle("Alteração da Password");
        //dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void btnEditPassword_onClick(View view){
        showPasswordDialog();
    }

    public void showUserDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custm_perfil_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText etPhone = (EditText) dialogView.findViewById(R.id.etPhone);
        final EditText etEmail = (EditText) dialogView.findViewById(R.id.etEmail);
        etEmail.setText(strEmail);
        etPhone.setText(strPhone);

        dialogBuilder.setTitle("Editar Perfil");
        dialogBuilder.setMessage(tvName.getText().toString());
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                HashMap<String, String> postDataUser = new HashMap<>();

                if (! etPhone.getText().toString().equals(strPhone)){
                    postDataUser.put("new_phone", "newPhone");
                }

                if (! etEmail.getText().toString().equals(strEmail)){
                    postDataUser.put("new_mail", "newMail");
                }

                postDataUser.put("cod_user", strCodUser);
                postDataUser.put("phone_user", etPhone.getText().toString());
                postDataUser.put("email_user", etEmail.getText().toString());

                updateUserInfo(postDataUser);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void btnEditUser_onClick(View view){
       showUserDialog();
    }

    private void updateUserInfo(HashMap<String, String> postData){
        PostResponseAsyncTask updateUserTask = new PostResponseAsyncTask(MyAccount.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                Log.d("MyAccount", result);
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(), "Perfil alterado com sucesso! Por favor volte a entrar!", Toast.LENGTH_SHORT).show();
                    userLocalStore.clearData();
                    userLocalStore.setUserLoggedIn(false);
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }else if(result.equals("email_already_exists")){
                    Toast.makeText(getApplicationContext(), "O email já existe.", Toast.LENGTH_SHORT).show();
                }else if(result.equals("phone_already_exists")){
                    Toast.makeText(getApplicationContext(), "O nº telemóvel já existe.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }

            }
        });
        updateUserTask.execute("http://10.0.3.3:81/estagio/rep/updateUser.php");
        //updateUserTask.execute("http://reptest.site88.net/updateUser.php");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
