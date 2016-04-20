package com.example.amand.rep;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amand.rep.model.Local;
import com.example.amand.rep.model.Problem;
import com.example.amand.rep.model.Urgency;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class OpenReport extends AppCompatActivity {

    private ArrayList<Problem> problemsList;
    private ArrayAdapter adapterProblems;

    private ArrayList<Local> localList;
    private ArrayAdapter adapterLocal;

    private ArrayList<Urgency> urgecyList;
    private ArrayAdapter adapterUrgency;

    Spinner spProblem, spLocal, spUrgency;
    EditText etDescProblem, etDescLocal;

    String strProblem, strDescProblem, strLocal, strDescLocal, strCodUser, strUrgency, strDateOpen, strImageName;

    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;

    final int CAMERA_REQUEST = 13323;
    final int GALLERY_REQUEST = 22131;

    String selectedPhoto;

    Button btnSearchPhoto, btnPhoto;
    TextView tvImageProgress;

    Calendar c;
    SimpleDateFormat sdf;
    SimpleDateFormat sdfImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_report);
        adapterProblems = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        spProblem = (Spinner)findViewById(R.id.spProblem);

        adapterLocal = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        spLocal = (Spinner)findViewById(R.id.spLocal);

        adapterUrgency = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        spUrgency = (Spinner)findViewById(R.id.spUrgency);


        etDescProblem = (EditText)findViewById(R.id.etDescProblem);
        etDescLocal = (EditText)findViewById(R.id.etDescLocal);

        showProblems();
        showLocals();
        showUrgencies();

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        btnPhoto = (Button)findViewById(R.id.btnPhoto);
        btnSearchPhoto = (Button)findViewById(R.id.btnSearchPhoto);
        tvImageProgress = (TextView)findViewById(R.id.tvImageProgress);

        c = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        sdfImage = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

    }

    public void showProblems(){
        PostResponseAsyncTask showProblemsTask = new PostResponseAsyncTask(this, new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                Log.d("MyAccount", result);

                problemsList = new JsonConverter<Problem>().toArrayList(result, Problem.class);
                for(Problem p: problemsList){
                    adapterProblems.add(p.name_problem);
                    Log.i("prob", p.name_problem);
                }
                spProblem.setAdapter(adapterProblems);
            }
        });
        showProblemsTask.execute("http://10.0.3.3:81/estagio/rep/getProblem.php");
        //showProblesTask.execute("http://reptest.site88.net/getProblem.php");
    }

    public void showLocals(){
        PostResponseAsyncTask showLocalTask = new PostResponseAsyncTask(this, new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                Log.d("MyAccount", result);

                localList = new JsonConverter<Local>().toArrayList(result, Local.class);
                for(Local l: localList){
                    adapterLocal.add(l.name_local);
                    Log.i("loc", l.name_local);
                }
                spLocal.setAdapter(adapterLocal);
            }
        });
        showLocalTask.execute("http://10.0.3.3:81/estagio/rep/getLocal.php");
        //showLocalTask.execute("http://reptest.site88.net/getLocal.php");
    }

    public void showUrgencies(){
        PostResponseAsyncTask showUrgencyTask = new PostResponseAsyncTask(this, new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                Log.d("MyAccount", result);

                urgecyList = new JsonConverter<Urgency>().toArrayList(result, Urgency.class);
                for(Urgency u: urgecyList){
                    adapterUrgency.add(u.name_urgency);
                    Log.i("urg", u.name_urgency);
                }
                spUrgency.setAdapter(adapterUrgency);
            }
        });
        showUrgencyTask.execute("http://10.0.3.3:81/estagio/rep/getUrgency.php");
        //showUrgencyTask.execute("http://reptest.site88.net/getUrgency.php");
    }

    public void btnSend_onClick(View view){
        if(checkNull()){
            uploadPhoto();
        }
    }

    private boolean checkNull(){
        strProblem = spProblem.getSelectedItem().toString();
        strDescProblem = etDescProblem.getText().toString();
        strLocal = spLocal.getSelectedItem().toString();
        strDescLocal = etDescLocal.getText().toString();
        strCodUser = MainActivity.cod_user;
        strUrgency = spUrgency.getSelectedItem().toString();
        strDateOpen = sdf.format(c.getTime());
        strImageName = MainActivity.cod_user + "_" + sdfImage.format(c.getTime());

        if(strProblem.equals("") || strDescProblem.equals("") || strLocal.equals("") || strDescLocal.equals("")
                || strUrgency.equals("") || tvImageProgress.equals("Nenhuma imagem")){
            Toast.makeText(this, "Por favor preencher todos os campos.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    public void btnPhono_onClick(View view){
        try {
            startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
            cameraPhoto.addToGallery();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Something Wrong while taking photos", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnSearchPhoto_onClick(View view){
        startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
    }

    private void uploadPhoto(){
        if(selectedPhoto == null || selectedPhoto.equals("")){
            Toast.makeText(getApplicationContext(), "No Image Selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Bitmap bitmap = ImageLoader.init().from(selectedPhoto).requestSize(1024, 1024).getBitmap();
            String encodedImage = ImageBase64.encode(bitmap);

            HashMap<String, String> postData = new HashMap<String, String>();
            postData.put("image", encodedImage);
            postData.put("image_name", strImageName);

            PostResponseAsyncTask task = new PostResponseAsyncTask(OpenReport.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    if(s.contains("uploaded_success")){
                        Toast.makeText(getApplicationContext(), "Image Uploaded Successfully.",
                                Toast.LENGTH_SHORT).show();
                        tvImageProgress.setText("Imagem enviada!");
                        registReport();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error while uploading.",
                                Toast.LENGTH_SHORT).show();
                        tvImageProgress.setText("Erro no upload...");

                    }
                }
            });

            task.execute("http://10.0.3.3:81/estagio/rep/upload.php");
            //task.execute("http://reptest.site88.net/upload.php");

            task.setEachExceptionsHandler(new EachExceptionsHandler() {
                @Override
                public void handleIOException(IOException e) {
                    Toast.makeText(getApplicationContext(), "Cannot Connect to Server.",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleMalformedURLException(MalformedURLException e) {
                    Toast.makeText(getApplicationContext(), "URL Error.",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleProtocolException(ProtocolException e) {
                    Toast.makeText(getApplicationContext(), "Protocol Error.",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                    Toast.makeText(getApplicationContext(), "Encoding Error.",
                            Toast.LENGTH_SHORT).show();
                }
            });


        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),
                    "Something Wrong while encoding photos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                String photoPath = cameraPhoto.getPhotoPath();
                selectedPhoto = photoPath;
                strImageName = photoPath.substring(photoPath.lastIndexOf("/")+1);
                tvImageProgress.setText("Imagem selecionada");
            }
            else if(requestCode == GALLERY_REQUEST){
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                strImageName = photoPath.substring(photoPath.lastIndexOf("/")+1);
                tvImageProgress.setText("Imagem selecionada");
            }
        }
    }

    private void registReport(){
        HashMap<String, String> postData = new HashMap<>();
        postData.put("name_problem", strProblem);
        postData.put("description_problem_report", strDescProblem);
        postData.put("name_local", strLocal);
        postData.put("description_local_report", strDescLocal);
        postData.put("name_urgency", strUrgency);
        postData.put("name_image_report", strImageName);
        postData.put("date_open_report", strDateOpen);
        postData.put("cod_user", strCodUser);

        PostResponseAsyncTask taskInsertReport = new PostResponseAsyncTask(OpenReport.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                if(result.equals("success")){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Report efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }
            }
        });
        taskInsertReport.execute("http://10.0.3.3:81/estagio/rep/doReport.php");
        //taskInsertReport.execute("http://reptest.site88.net/doReport.php");
    }



}
