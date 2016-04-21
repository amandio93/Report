package com.example.amand.rep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.amand.rep.model.MySingleton;
import com.example.amand.rep.model.Report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportInfo extends AppCompatActivity {

    private static String strCodReport = "";

    TextView tvNReport, tvState, tvDateOpen, tvDateFinish, tvProblem,
            tvDescProblem, tvLocal, tvDescLocal, tvUrgency, tvHelper;

    private String strState, strDateOpen, strDateFinish, strProblem,
        strDescProblem, strLocal, strDesclocal, strUrgency, strHelper, strImageName;

    public ArrayList<Report> reportList;

    public String resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info);

        strCodReport = getIntent().getStringExtra("codReport");

        tvNReport = (TextView)findViewById(R.id.tvNRepot);
        tvNReport.setText("CR/" + strCodReport);

        tvState = (TextView)findViewById(R.id.tvState);
        tvDateOpen = (TextView)findViewById(R.id.tvDateOpen);
        tvDateFinish = (TextView)findViewById(R.id.tvDateFinish);
        tvProblem = (TextView)findViewById(R.id.tvProblem);
        tvDescProblem = (TextView)findViewById(R.id.tvDescProblem);
        tvLocal = (TextView)findViewById(R.id.tvLocal);
        tvDescLocal = (TextView)findViewById(R.id.tvDescLocal);
        tvUrgency = (TextView)findViewById(R.id.tvUrgency);
        tvHelper = (TextView)findViewById(R.id.tvHelper);

        showReportInfo();

        //reportList = new JsonConverter<Report>().toArrayList(resp, Report.class);

        /*for(Report rep : reportList){
            strState = rep.name_state;
            strDateOpen = rep.date_open_report;
            strDateFinish = rep.date_finish_report;
            strProblem = rep.name_problem;
            strDescProblem = rep.description_problem_report;
        }*/

        /*tvState.setText(strState);
        tvDateOpen.setText(strDateOpen);
        tvDateFinish.setText(strDateFinish);
        tvProblem.setText(strProblem);
        tvDescProblem.setText(strDescProblem);*/
    }

    private void showReportInfo(){
        String url3 = "http://10.0.3.3:81/estagio/rep/showReportInfo.php";

        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                resp = response;

                Log.d("response", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof com.android.volley.TimeoutError){
                    Toast.makeText(getApplicationContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(getApplicationContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(getApplicationContext(), "Authentication Failure Error", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof com.android.volley.NetworkError){
                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof com.android.volley.ServerError){
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof com.android.volley.ParseError){
                    Toast.makeText(getApplicationContext(), "JSON Parse Error", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cod_report", strCodReport);
                return params;
            }

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "MyAndroid");
                headers.put("Accept-Language", "english");
                return headers;
            }*/
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest3);

    }


}
