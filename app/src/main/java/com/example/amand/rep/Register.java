package com.example.amand.rep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private EditText etName, etPhone, etEmail, etPassword, etEmailConf, etPasswordConf;
    private Button btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etName = (EditText)findViewById(R.id.etName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etPhone = (EditText)findViewById(R.id.etPhone);
        btnRegist = (Button)findViewById(R.id.btnRegist);

        etEmailConf = (EditText)findViewById(R.id.etEmailConf);
        etPasswordConf = (EditText)findViewById(R.id.etPasswordConf);

    }

    public void btnCancel_onClick(View view){
        super.onBackPressed();
    }

    public void btnRegist_onClick(View view){

        if (etName.getText().toString().equals("") || etPassword.getText().toString().equals("") ||  etPhone.getText().toString().equals("")
                || etPasswordConf.getText().toString().equals("") || etEmail.getText().toString().equals("") || etEmailConf.getText().toString().equals("")){
            Toast.makeText(this.getApplicationContext(), "Por favor preencher todos os campos.", Toast.LENGTH_SHORT).show();
        }else if(! etEmail.getText().toString().equals(etEmailConf.getText().toString())){
            Toast.makeText(this.getApplicationContext(), "Os emails não correspondem.", Toast.LENGTH_SHORT).show();
        }else if( ! etPassword.getText().toString().equals(etPasswordConf.getText().toString())){
            Toast.makeText(this.getApplicationContext(), "As passwords não correspondem.", Toast.LENGTH_SHORT).show();
        }else if(! isEmailValid(etEmail.getText().toString())){
            Toast.makeText(this.getApplicationContext(), "O email não é valido.", Toast.LENGTH_SHORT).show();
        }else{
            registUser();
        }


    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void registUser(){
        HashMap<String, String> postData = new HashMap<>();
        postData.put("name_user", etName.getText().toString());
        postData.put("phone_user", etPhone.getText().toString());
        postData.put("email_user", etEmail.getText().toString());
        postData.put("password_user", etPassword.getText().toString());

        PostResponseAsyncTask taskInsertUser = new PostResponseAsyncTask(Register.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                if(result.equals("success")){
                    //startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                    Toast.makeText(getApplicationContext(), "Registo efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                }else if(result.equals("email_already_exists")){
                    Toast.makeText(getApplicationContext(), "O email já existe.", Toast.LENGTH_SHORT).show();
                }else if(result.equals("phone_already_exists")){
                    Toast.makeText(getApplicationContext(), "O nº telemóvel já existe.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }
            }
        });
        taskInsertUser.execute("http://10.0.3.3:81/estagio/rep/doRegist.php");
        //taskInsertUser.execute("http://reptest.site88.net/doRegist.php");
    }

}
