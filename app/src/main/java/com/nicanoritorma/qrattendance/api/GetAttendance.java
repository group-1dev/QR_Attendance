package com.nicanoritorma.qrattendance.api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class GetAttendance {

    private static GetAttendance instance;
    private RequestQueue requestQueue;
    private Context context;

    private GetAttendance(Context context)
    {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized GetAttendance getInstance(Context context) {
        if (instance == null) {
            instance = new GetAttendance(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
}
