package com.example.vaccisafe;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String year_dob;
    String month_dob;
    String day_dob;
    private RequestQueue mQueue;
    private CoordinatorLayout coordinatorLayout;
    private EditText gui_emailid;
    private EditText gui_password;
    private EditText gui_fname;
    private EditText gui_lname;
    private EditText gui_mobile_number;
    private Spinner gui_gender_spinner;
    private Spinner gui_blood_group;
    private EditText gui_address;
    private EditText gui_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        gui_emailid = findViewById(R.id.email_addr);

        gui_emailid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    String getEmailId = gui_emailid.getText().toString();

                    // Check if email id is valid or not
                    if (!isEmailValid(getEmailId)) {
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Invalid Email!", Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();
                    }
                }
            }
        });

        // fill the gender spinner
        gui_gender_spinner = findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gui_gender_spinner.setAdapter(adapter);

        // fill the gender spinner
        gui_blood_group = findViewById(R.id.blood_group);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.blood_groups, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gui_blood_group.setAdapter(adapter2);


    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void showDatePickerDialog(View view) {
        //DialogFragment dobFragment = new DatePickerFragmentNotUsed();
        //dobFragment.show(getSupportFragmentManager(), "datePicker");
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        year_dob = String.valueOf(year);
        month_dob = String.valueOf(month + 1);
        day_dob = String.valueOf(day);

        String dob_display = "Date of Birth: " + day_dob + "/" + month_dob + "/" + year_dob;
        Log.d(TAG, "onDateSet: " + dob_display);

        TextView dob_textview = findViewById(R.id.dob_textview);
        dob_textview.setText(dob_display);
    }

    public void register_patient(View view) {

        Boolean all_correct = true;

        // gui_emailid = (EditText) findViewById(R.id.email_addr);

        gui_password = findViewById(R.id.password);
        gui_fname = findViewById(R.id.fname);
        gui_lname = findViewById(R.id.lname);
        gui_mobile_number = findViewById(R.id.mobile_number);
        gui_address = findViewById(R.id.address);
        gui_city = findViewById(R.id.city);
        gui_blood_group = findViewById(R.id.blood_group);
        gui_gender_spinner = findViewById(R.id.gender);

        String email = gui_emailid.getText().toString();
        String password = gui_password.getText().toString();
        String fname = gui_fname.getText().toString();
        String lname = gui_lname.getText().toString();
        String string_mobile_number = String.valueOf(gui_mobile_number.getText());
        String gender = gui_gender_spinner.getSelectedItem().toString();
        String blood_group = gui_blood_group.getSelectedItem().toString();
        String address = gui_address.getText().toString();
        String city = gui_city.getText().toString();

        if (is_empty(email) || is_empty(password) || is_empty(fname) || is_empty(lname) || is_empty(string_mobile_number) || is_empty(gender) || is_empty(blood_group) || is_empty(address) || is_empty(city)) {
            show_snackbar("One or more fields are empty! Please fill them up.");
        }


/*
        Check if email id is valid or not
        The field validation doesn't allow to pass through so this validation is not necessary
        if (!isEmailValid(email)) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Invalid Email! - caught on register", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            all_correct = false;
        }
*/

        int year = Integer.parseInt(year_dob);
        int month = Integer.parseInt(month_dob);
        int day = Integer.parseInt(day_dob);

        if (gender.equals("Select gender")) {
            show_snackbar("Please select your gender.");
            all_correct = false;
        }

        if (blood_group.equals("Select Blood Group")) {
            show_snackbar("Please select your blood group.");
            all_correct = false;
        }

        if (all_correct) {
            Map<String, String> obj = new HashMap<String, String>();
            // JSONObject obj = new JSONObject();

            obj.put("email", email);
            obj.put("password", password);
            obj.put("fname", fname);
            obj.put("lname", lname);
            obj.put("mobile_number", string_mobile_number);
            obj.put("gender", gender);
            obj.put("year_dob", year_dob);
            obj.put("month_dob", month_dob);
            obj.put("day_dob", day_dob);
            obj.put("blood_group", blood_group);
            obj.put("address", address);
            obj.put("city", city);

            Log.d(TAG, "register_patient: " + obj);
            // disable button
            Button register_button = findViewById(R.id.register);
            register_button.setEnabled(false);

            /*
            Log.d(TAG, "register_patient: All data correct");
            show_snackbar("all data correct");
            */

            String url = "https://hello-world-1-fvonreigsq-el.a.run.app/register";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(obj),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String pk = response.getString("pk");
                                if (pk.startsWith("(pymysql.err.IntegrityError) (1062")) {
                                    show_snackbar("Email Address already exists. Try another one or try logging in.");
                                    register_button.setEnabled(true);
                                } else {
                                    String email = response.getString("email");
                                    Log.d(TAG, "onResponse: " + pk + ' ' + email);
                                    show_snackbar(pk + " " + email);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    show_snackbar(error.toString());
                    Log.d(TAG, "onErrorResponse: " + error.toString());
                }
            });

            mQueue = VolleySingleton.getInstance(this).getRequestQueue();
            mQueue.add(jsonObjectRequest);
        }
    }


    public void show_snackbar(String message) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public Boolean is_empty(String variable) {
        return variable.isEmpty();
    }

}