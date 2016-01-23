package com.cabs.form;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private int mYear, mMonth, mDay;
    TextView txtDate;
    EditText etName, etAddr, etPhone, etEmail;
    String sName,sPhone,sEmail,sAddr,dob;
    int[] err = {0, 0, 0}, verr = {1, 2, 3};

    /*For Request*/
    private static final String URL = "http://socketchat.tk/store.php";

    public static final String KEY_NAME = "n";
    public static final String KEY_ADDR = "add";
    public static final String KEY_MAIL = "e";
    public static final String KEY_PHONE = "p";
    public static final String KEY_DOB = "d";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtDate = (TextView) findViewById(R.id.tvBirth);
        etName = (EditText) findViewById(R.id.etName);
        etAddr = (EditText) findViewById(R.id.etAddress);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyData()) {
                    Log.d("TAG", "NO ERROR");
                    registerUser();
                }
            }
        });

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondPage.class));
            }
        });
    }

    private void registerUser() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Signing up...");
        pDialog.setIndeterminate(true);
        pDialog.show();

        sName = etName.getText().toString();
        sAddr = etAddr.getText().toString();
        sEmail = etEmail.getText().toString();
        sPhone = etPhone.getText().toString();

        Log.d("Parameters", sName + sAddr + sEmail + sPhone + dob);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d("Response",response);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(
                                MainActivity.this);
                        dialog.setMessage("Successful").setTitle("Registration")
                                .create().show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Some Error Occurred...Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NAME, sName);
                params.put(KEY_ADDR, sAddr);
                params.put(KEY_MAIL, sEmail);
                params.put(KEY_PHONE, sPhone);
                params.put(KEY_DOB, dob);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setErrors() {

        // verification of phone number block
        {
            String phone = etPhone.getText().toString();

            if (phone.length() == 0) {
                etPhone.setError("Field cannot be left blank.");
            }
            if (phone.length() != 10 && phone.length() != 0) {
                etPhone.setError("Enter valid Mobile Number");
            }
            if (phone.length() > 0 && phone.length() == 10) {
                Log.d("phone", phone);
                err[0] = 1;
            }
        }// end of block

        // verification of email block
        {
            String email = etEmail.getText().toString();

            if (email.length() == 0) {
                etEmail.setError("Field cannot be left blank.");
            }
            if (email.length() > 0) {
                if (emailValidator(email.toString()) == false) {
                    etEmail.setError("Enter a valid Email address");
                } else {
                    Log.d("Email", email);
                    err[1] = 2;
                }
            }
        }// end of block

        //prompt user to chose DOB
        {
            if (err[0] == 1 && err[1] == 2 && err[2] == 0) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(
                        MainActivity.this);
                dialog.setMessage("Choose Date of Birth")
                        .create().show();
            }
        }
    }


    // Email field Validator
    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    // verify all-fields function
    public boolean verifyData() {
        setErrors();
        if (Arrays.equals(err, verr)) {
            return true;// no Errors
        } else {
            return false;// Errors
        }
    }


    // Date of Birth function
    public void DatePick(View v) {
        // TODO Auto-generated method stub
        if (v == txtDate) {

            // Process to get Current Date
            final Calendar today = Calendar.getInstance();
            mYear = today.get(Calendar.YEAR);
            mMonth = today.get(Calendar.MONTH);
            mDay = today.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Calendar chosenDate = Calendar.getInstance();
                            chosenDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            chosenDate.set(Calendar.MONTH, monthOfYear);
                            chosenDate.set(Calendar.YEAR, year);

                            if (today.getTimeInMillis() < chosenDate.getTimeInMillis()) {
                                err[2] = 0;
                                AlertDialog.Builder dialog = new AlertDialog.Builder(
                                        MainActivity.this);
                                dialog.setMessage("Input Appropriate Age..").setTitle("Age")
                                        .create().show();
                            } else {
                                /* Display Selected date in textbox */
                                dob = dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year;
                                txtDate.setText(dob);
                                err[2] = 3;
                            }
                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
