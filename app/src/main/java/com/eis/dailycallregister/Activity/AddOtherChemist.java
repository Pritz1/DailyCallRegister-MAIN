package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.ChemistdataItem;
import com.eis.dailycallregister.Pojo.DoctornamelistItem;
import com.eis.dailycallregister.Pojo.SetChemistkeyPerRes;
import com.eis.dailycallregister.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOtherChemist extends AppCompatActivity {


    EditText chmkeypername, chmphoneno,chmname;
    Button takeselfie;
    AppCompatSpinner chemistdoctor;
    LinearLayout ll1;
    ScrollView chmscr;
    String cntcd = "", docname = "",doccntcd="";
    ViewDialog progressDialoge;


    public List<String> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_other_chemist);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Add Other Chemist</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        docname = getIntent().getStringExtra("drname");
        doccntcd = getIntent().getStringExtra("doccntcd");

        chmname=findViewById(R.id.chmname);
        chmkeypername = findViewById(R.id.chmkeypername);
        chmphoneno = findViewById(R.id.chmphoneno);
        takeselfie = findViewById(R.id.takeselfie);
        chmscr = findViewById(R.id.chmscr);
        chemistdoctor = findViewById(R.id.chemistdoctor);
        ll1 = findViewById(R.id.ll1);
        progressDialoge = new ViewDialog(this);

        if (docname != null) {
            arrayList.add(docname);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.eis.dailycallregister.Activity.AddOtherChemist.this, android.R.layout.simple_spinner_item, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chemistdoctor.setAdapter(adapter);
            chemistdoctor.setPrompt("Tagged Doctor(s)");
            ll1.setVisibility(View.VISIBLE);
            //  chmdoc=chemistdoctor.getSelectedItem().toString();
            //  checkDate(chmdoc);
        }else{
            Toast.makeText(AddOtherChemist.this,"Doctor List Is Empty",Toast.LENGTH_SHORT).show();

        }

        takeselfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String doctorname = chemistdoctor.getSelectedItem().toString().trim();
                String chemistname=chmname.getText().toString().trim();
                String keycontactper = chmkeypername.getText().toString().trim();
                String phonenumber = chmphoneno.getText().toString().trim();

                if (chemistname.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(chmscr, "Please Enter chemistname ", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else if (keycontactper.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(chmscr, "Please Enter Contact Person Name ", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (phonenumber.isEmpty() || phonenumber.length() < 10) {
                    Snackbar snackbar = Snackbar.make(chmscr, "Please Enter Valid Phone Number", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    sendData(doctorname,chemistname,keycontactper, phonenumber);
                }
            }
        });
     //  getChemistKeyPer();
    }

    public void sendData(String doctorname,String chemistname, String keycontactper, String phonenumber) {
        Intent intent = new Intent(AddOtherChemist.this, CapNUpSelfie.class);
        intent.putExtra("doctorname", doctorname);
        intent.putExtra("chemistname", chemistname);
        intent.putExtra("keycontactper", keycontactper);
        intent.putExtra("phonenumber", phonenumber);
        intent.putExtra("cntcd", cntcd);
        intent.putExtra("doccntcd", doccntcd);
        intent.putExtra("flag", "ADD");
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(AddOtherChemist.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
        startActivity(intent, bndlanimation);
    }

    /*public void getChemistKeyPer() {
        retrofit2.Call<SetChemistkeyPerRes> call = RetrofitClient.getInstance().getApi().getChemistData(cntcd, sttype, Global.dbprefix);
        call.enqueue(new Callback<SetChemistkeyPerRes>() {
            @Override
            public void onResponse(Call<SetChemistkeyPerRes> call, Response<SetChemistkeyPerRes> response) {
                SetChemistkeyPerRes res = response.body();
                Log.d("res", res.toString());
                if (!res.isError()) {
                    chemistdata = res.getChemistdata();
                    ChemistdataItem cd = chemistdata.get(0);
                    if (chemistdata.size() > 0) {
                        chmkeypername.setText(cd.getKeyp());
                        chmphoneno.setText(cd.getMobileno());
                    } else {
                        chmkeypername.setText(null);
                        chmphoneno.setText(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<SetChemistkeyPerRes> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(chmscr, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getChemistKeyPer();
                            }
                        });
                snackbar.show();

            }
        });
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        com.eis.dailycallregister.Activity.AddOtherChemist.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}



