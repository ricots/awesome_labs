package com.cabs.form;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SecondPage extends AppCompatActivity {

    EditText etId;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etId = (EditText) findViewById(R.id.etId);
        tvResult = (TextView) findViewById(R.id.tvResult);
        Button btnSubmit= (Button) findViewById(R.id.btnSub);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final ProgressDialog pDialog = new ProgressDialog(SecondPage.this);
                pDialog.setMessage("Retrieving Data...");
                pDialog.setIndeterminate(true);
                pDialog.show();

                String URL = "http://socketchat.tk/retrieve.php?id=" + etId.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pDialog.dismiss();
                                JSONObject jsonResponse = null;
                                try {
                                    jsonResponse = new JSONObject(response);
                                    JSONArray jsonArray = jsonResponse.getJSONArray("pid");
                                    JSONArray jObj1 = jsonArray.getJSONArray(0);
                                    JSONArray jObj2 = jObj1.getJSONArray(0);

                                    String name = jObj2.get(1).toString();
                                    String address = jObj2.get(2).toString();
                                    String phone = jObj2.get(3).toString();
                                    String email = jObj2.get(4).toString();
                                    String dob = jObj2.get(5).toString();

                                    tvResult.setText("Name : "+ name + "\n" +
                                    "Address : "+address+"\n"+"Phone : "+phone+"\n"+"Email : "+email+"\n"+"DOB : " +dob);
                                    //Toast.makeText(SecondPage.this, "Working", Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.dismiss();
                                Toast.makeText(SecondPage.this,"Some Error Occurred...Try Again",Toast.LENGTH_LONG).show();
                            }
                        });

                RequestQueue requestQueue = Volley.newRequestQueue(SecondPage.this);
                requestQueue.add(stringRequest);
            }
        });
    }

}
