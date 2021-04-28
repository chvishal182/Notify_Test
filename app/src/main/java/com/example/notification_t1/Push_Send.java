package com.example.notification_t1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class Push_Send extends AppCompatActivity {

    String userToken;
    String title,body;
    Context mContext;
    Activity mActivity;


    private     RequestQueue requestQueue;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey ="AAAAkmfHssw:APA91bFQbZ66LqCirm6wEcxCX3nAUAOOqx5Dmd6Db4LXZo-QxeDcfguxdfKKScjDMfKEyy3sRoUV_dVHuo6NQxGbXKXs6DeeLKxdXnyiUdmXhnZeC3y30aiE-eNmMzvvAXRaEX9_knYh";



    public Push_Send(String userToken,String title,String body,Context mContext,Activity mActivity)
    {
        this.userToken = userToken;
        this.title = title;
        this.body = body;
        this.mContext = mContext;
        this.mActivity = mActivity;


    }

    public  void sendNotifications() throws JSONException {
        requestQueue = Volley.newRequestQueue(mContext);//requesting an activity from our side
        JSONObject mainObj = new JSONObject();// creating an JSON object to access the user through Token
        try {
                mainObj.put("to",userToken);
                JSONObject notifyObj = new JSONObject();// creating an JSON object to play with the notifications
                notifyObj.put("title",title);//title of our notification
                notifyObj.put("body",body);//body of our notification
                notifyObj.put("icon","R.drawable.ic_baseline_notifications_active_24"); //icon of our notification

            mainObj.put("notification",notifyObj);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {


                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;


                }
            };
            requestQueue.add(jsonObjectRequest);
        }

        catch (JSONException e){
            e.printStackTrace();
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_push__send);


    }
}