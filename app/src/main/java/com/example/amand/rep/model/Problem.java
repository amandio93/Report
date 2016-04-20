package com.example.amand.rep.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amand on 04/04/2016.
 */
public class Problem {

    @SerializedName("cod_problem")
    public int cod_problem;

    @SerializedName("name_problem")
    public String name_problem;

    //public static ArrayList<Problem> problemsList;
   // public static ArrayAdapter adapterProblems;

    public Problem (){}

   /* public void showProblems(final Context context){

        PostResponseAsyncTask showProblesTask = new PostResponseAsyncTask(context, new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                Log.d("MyAccount", result);

                problemsList = new JsonConverter<Problem>().toArrayList(result, Problem.class);
                for(Problem p: problemsList){
                    name_problem = p.name_problem;
                }
            }
        });
        showProblesTask.execute("http://10.0.3.3:81/estagio/rep/getProblems.php");

    }*/
}
