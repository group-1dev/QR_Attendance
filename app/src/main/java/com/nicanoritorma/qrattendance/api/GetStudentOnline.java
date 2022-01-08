package com.nicanoritorma.qrattendance.api;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nicanoritorma.qrattendance.BaseActivity;
import com.nicanoritorma.qrattendance.model.StudentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetStudentOnline extends BaseActivity {

    private MutableLiveData<List<StudentModel>> studentList;
    private RequestQueueSingleton requestQueueSingleton;


    public GetStudentOnline(Application application) {
        studentList = new MutableLiveData<>();
        requestQueueSingleton = RequestQueueSingleton.getInstance(application);
    }

    public LiveData<List<StudentModel>> getStudentList() {
        retrieveStudentListFromDb();
        return studentList;
    }

    public void retrieveStudentListFromDb()
    {
        List<StudentModel> list = new ArrayList<>();
        String URL = getDbUrl() + "GetStudentList.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("studentList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject data = array.getJSONObject(i);

                        String fullname = data.getString("fullname");
                        String idNumber = data.getString("idNumber");
                        String college = data.getString("department");
                        String qrCode = data.getString("qrCode");
                        list.add(new StudentModel(fullname, idNumber, college, qrCode));
                    }
                    studentList.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueueSingleton.getRequestQueue().add(request);
    }
}
