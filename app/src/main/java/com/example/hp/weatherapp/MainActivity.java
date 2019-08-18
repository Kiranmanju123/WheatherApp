package com.example.hp.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result,result1;
      //  https://api.openweathermap.org/data/2.5/weather?q=america&appid=287ed36f7846c90465133244e8964a00

    String baseUrl= "https://api.openweathermap.org/data/2.5/weather?q=";
    String API=  "&appid=287ed36f7846c90465133244e8964a00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button) findViewById(R.id.button);
        city=(EditText) findViewById(R.id.getcity);
        result=(TextView) findViewById(R.id.result);
        result1=(TextView) findViewById(R.id.id);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String myURL = baseUrl + city.getText().toString() + API;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {



                                Log.i("JSON", "JSON: " + response);

                                try {
                                    String info = response.getString("weather");
                                   // String data=response.
                                    Log.i("INFO", "INFO: "+ info);

                                    JSONArray ar = new JSONArray(info);

                                    for (int i = 0; i < ar.length(); i++){
                                        JSONObject parObj = ar.getJSONObject(i);

                                        String myWeather = parObj.getString("main");
                                        result.setText(myWeather);

                                        String des=parObj.getString("description");
                                        result1.setText(des);


                                        Log.i("ID", "ID: " + parObj.getString("id"));
                                        Log.i("MAIN", "MAIN: " + parObj.getString("main"));
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



















                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.i("Error", "Something went wrong" + error);

                            }
                        }
                );

                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);



            }


        });




    }
}
