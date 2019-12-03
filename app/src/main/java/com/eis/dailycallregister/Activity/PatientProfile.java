package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientProfile extends AppCompatActivity {

    EditText patientnm,parentnm,patientage,phoneno;
    RadioGroup rdgrp;
    Button submit;
    ScrollView scr;
    String cntcd="";
    ViewDialog progressDialoge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_details);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>PATIENT DETAIL FORM</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        cntcd=getIntent().getStringExtra("cntcd");

        patientnm=findViewById(R.id.patientnm);
        parentnm=findViewById(R.id.parentnm);
        patientage=findViewById(R.id.patientage);
        phoneno=findViewById(R.id.phoneno);
        rdgrp=findViewById(R.id.rdgrp);
        submit=findViewById(R.id.submit);
        scr=findViewById(R.id.scr);
        progressDialoge=new ViewDialog(this);

       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               checkForm();

           }
       });

    }

    public void  checkForm() {
        String patientname = patientnm.getText().toString().trim();
        String parentname = parentnm.getText().toString().trim();
        String age = patientage.getText().toString().trim();
        String phonenumber = phoneno.getText().toString().trim();
//        int selectedId = rdgrp.getCheckedRadioButtonId();

        if (patientname.isEmpty()) {
            Snackbar snackbar = Snackbar.make(scr, "Please Enter Patient name", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (age.isEmpty()) {
            Snackbar snackbar = Snackbar.make(scr, "Please Enter Age", Snackbar.LENGTH_LONG);
            snackbar.show();

        } else if (phonenumber.isEmpty() || phonenumber.length() < 10) {
            Snackbar snackbar = Snackbar.make(scr, "Please Enter Valid Phone Number", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (rdgrp.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(scr, "Please Select Gender", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            int selectedId = rdgrp.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            String gender = radioButton.getText().toString().trim();

            if(gender.equalsIgnoreCase("Male"))
            {
                gender="M";
            }else{
                gender="F";
            }

            /*Log.d("patientname",patientname);
            Log.d("parentname",parentname);
            Log.d("age",age);
            Log.d("phonenumber",phonenumber);
            Log.d("gender",gender);
            Log.d("cntcd",cntcd);
            Log.d("Global.ecode",Global.ecode);
            Log.d("Global.netid",Global.netid);*/

            submitForm(patientname, age, parentname, phonenumber, gender);//, parentname, age, phonenumber, gender

        }

    }

    public void submitForm(String patientname,String age,String parentname,String phonenumber,String gender)//
    {
        progressDialoge.show();
        Call<DefaultResponse> call1=RetrofitClient.getInstance().getApi().savePatientData(Global.netid,cntcd,patientname,parentname,phonenumber,gender,Global.ecode,age,Global.dbprefix);///*parentname,gender,phonenumber,
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
              //  Log.d("response",response.body().toString());
                progressDialoge.dismiss();
                DefaultResponse res=response.body();
               // Log.d("response",res.getErrormsg());
                if (!res.isError()){
                    Toast.makeText(PatientProfile.this,res.getErrormsg(),Toast.LENGTH_SHORT).show();
                    patientnm.getText().clear();
                    parentnm.getText().clear();
                    patientage.getText().clear();
                    phoneno.getText().clear();
                    rdgrp.clearCheck();
                }
                else{
                   // Log.d("else","else");
                    Toast.makeText(PatientProfile.this,res.getErrormsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                progressDialoge.dismiss();
               //  Log.d("onFailure",t.toString());
                Toast.makeText(PatientProfile.this,"Failed To Create Record",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_list_eye_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.patientlisteye) {
            Intent intent = new Intent(PatientProfile.this, PatientList.class);
            intent.putExtra("cntcd",cntcd);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(PatientProfile.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
            startActivity(intent, bndlanimation);
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return true;
    }



    @Override
    public void onBackPressed() {
        finish();
        PatientProfile.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
