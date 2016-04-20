package com.example.amand.rep.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amand on 15/04/2016.
 */
public class Report {

    @SerializedName("cod_report")
    public int cod_report;

    @SerializedName("description_problem_report")
    public String description_problem_report;

    @SerializedName("desctiption_local_report")
    public String desctiption_local_report;

    @SerializedName("name_local")
    public String name_local;

    @SerializedName("name_problem")
    public String name_problem;

    @SerializedName("date_open_report")
    public String date_open_report;

    @SerializedName("date_update_report")
    public String date_update_report;

    @SerializedName("date_finish_report")
    public String date_finish_report;

    @SerializedName("name_helper")
    public String name_helper;

    @SerializedName("name_urgency")
    public String name_urgency;

    @SerializedName("name_state")
    public String name_state;

    @SerializedName("name_image_report")
    public String name_image_report;

}
