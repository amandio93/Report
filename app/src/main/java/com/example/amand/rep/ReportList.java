package com.example.amand.rep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.example.amand.rep.model.Report;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportList extends AppCompatActivity {

    public ArrayList<Report> reportList;
    ListView lvReport;
    private FunDapter<Report> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        HashMap<String, String> postData = new HashMap<>();
        postData.put("cod_user", MainActivity.cod_user);

        lvReport = (ListView)findViewById(R.id.lvReport);

        PostResponseAsyncTask showReportsTask = new PostResponseAsyncTask(this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                Log.d("List", result);
                reportList = new JsonConverter<Report>().toArrayList(result, Report.class);

                BindDictionary<Report> dict = new BindDictionary<Report>();
                dict.addStringField(R.id.tvNameProblem, new StringExtractor<Report>() {
                    @Override
                    public String getStringValue(Report report, int position) {
                        return "" + report.name_problem;
                    }
                });

                dict.addStringField(R.id.tvNameLocal, new StringExtractor<Report>() {
                    @Override
                    public String getStringValue(Report report, int position) {
                        return "" + report.name_local;
                    }
                });

                dict.addStringField(R.id.tvNameUrgency, new StringExtractor<Report>() {
                    @Override
                    public String getStringValue(Report report, int position) {
                        return "" + report.name_urgency;
                    }
                });

                dict.addStringField(R.id.tvDateOpenReport, new StringExtractor<Report>() {
                    @Override
                    public String getStringValue(Report report, int position) {
                        return "" + report.date_open_report;
                    }
                });

                dict.addStringField(R.id.tvDateFinishReport, new StringExtractor<Report>() {
                    @Override
                    public String getStringValue(Report report, int position) {
                        return "" + report.date_finish_report;
                    }
                });

                dict.addStringField(R.id.tvState, new StringExtractor<Report>() {
                    @Override
                    public String getStringValue(Report report, int position) {
                        return "" + report.name_state;
                    }
                });

                dict.addStringField(R.id.tvCodReport, new StringExtractor<Report>() {
                    @Override
                    public String getStringValue(Report report, int position) {
                        return "CR/" + report.cod_report;
                    }
                });

                adapter = new FunDapter<>(
                        ReportList.this, reportList, R.layout.layout_list, dict);


                lvReport.setAdapter(adapter);

            }
        });
        showReportsTask.execute("http://10.0.3.3:81/estagio/rep/showReportList.php");

        lvReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                int nCodReportToSend = reportList.get(position).cod_report;
                Toast.makeText(getApplicationContext(), String.valueOf(nCodReportToSend), Toast.LENGTH_SHORT).show();
                Intent reportInfo = new Intent(getApplicationContext(), ReportInfo.class);
                reportInfo.putExtra("codReport", String.valueOf(nCodReportToSend));
                startActivity(reportInfo);
            }
        });

    }


}
