package com.example.bloodbank.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodbank.R;
import com.example.bloodbank.Utils.Endpoints;
import com.example.bloodbank.Utils.VolleySingleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name, email, mobile, bloodgroup, password;
    private Button submit;
    Context context;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        context = this;
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        mobile = findViewById(R.id.et_mobile);
        bloodgroup = findViewById(R.id.et_bloodgroup);
        password = findViewById(R.id.et_password);
        submit = findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString().trim();
                String e = email.getText().toString().trim();
                String m = mobile.getText().toString().trim();
                String b = bloodgroup.getText().toString().trim();
                String p = password.getText().toString().trim();


                firebaseAuth.createUserWithEmailAndPassword(e, p)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    User u1 = new User(user.getUid(),name,mobile,blood_group);
                                    myRef.child(user.getUid()).setValue(u1);
                                    Intent intent = new Intent(RegistrationActivity.this, MakeRequestActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });


            }
        });


    }



}





//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String etname, etcity, etmobile, etbloodgroup, etpassword;
//                etname = name.getText().toString();
//                etcity = city.getText().toString();
//                etmobile = mobile.getText().toString();
//                etbloodgroup = bloodgroup.getText().toString();
//                etpassword = password.getText().toString();
//                if(isValid(etname, etcity,etmobile, etbloodgroup, etpassword)){
//                    register(etname, etcity, etmobile, etbloodgroup, etpassword);
//                }
//            }
//        });
//    }
//
//    private void register(final String name, final String city, final String mobile, final String blood_group, final String password ){
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.reg_url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String ServerResponse) {
//                        if(ServerResponse.equals("Success")){
//                            Toast.makeText(RegistrationActivity.this, ServerResponse, Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
//                            RegistrationActivity.this.finish();
//                        }
//                        else {
//                            Toast.makeText(RegistrationActivity.this,ServerResponse,Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT)
//                                .show();
//                        Log.d("VOLLEY", volleyError.toString());
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", name);
//                params.put("city", city);
//                params.put("number", mobile);
//                params.put("blood_group", blood_group);
//                params.put("password", password);
//                return params;
//            }
//
//        };
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//
//    }
//
//    private boolean isValid(String name, String city, String mobile, String blood_group, String password){
//        List<String> valid_blood_groups = new ArrayList<>();
//        valid_blood_groups.add("A+");
//        valid_blood_groups.add("A-");
//        valid_blood_groups.add("B+");
//        valid_blood_groups.add("B-");
//        valid_blood_groups.add("AB+");
//        valid_blood_groups.add("AB-");
//        valid_blood_groups.add("O+");
//        valid_blood_groups.add("O-");
//
//
//        if(name.isEmpty()){
//            showmessage("Name is required !");
//            return false;
//        }
//        else if(city.isEmpty()){
//            showmessage("City is required !");
//            return false;
//        }
//        else if(mobile.length() != 11){
//            showmessage("Invalid mobile number ! number should be of 11 digits !");
//            return false;
//        }
//        else if(!valid_blood_groups.contains(blood_group)){
//            showmessage("Invalid Blood Group ! Please select from "+valid_blood_groups);
//            return false;
//        }
//        else if(password.isEmpty()){
//            showmessage("Password is required !");
//            return false;
//        }
//
//
//
//        return true;
//    }
//
//
//    private void showmessage(String msg){
//        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
//    }
//