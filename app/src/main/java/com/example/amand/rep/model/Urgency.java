package com.example.amand.rep.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amand on 05/04/2016.
 */
public class Urgency {
    @SerializedName("cod_urgency")
    public int cod_urgency;

    @SerializedName("name_urgency")
    public String name_urgency;
}
