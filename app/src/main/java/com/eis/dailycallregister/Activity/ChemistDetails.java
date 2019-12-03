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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.ChemistDoctorNameRes;
import com.eis.dailycallregister.Pojo.ChemistdataItem;
import com.eis.dailycallregister.Pojo.DoctornamelistItem;
import com.eis.dailycallregister.Pojo.SetChemistkeyPerRes;
import com.eis.dailycallregister.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChemistDetails extends AppCompatActivity {


    EditText chmkeypername,chmphoneno;
    Button takeselfie;
    AppCompatSpinner chemistdoctor;
    LinearLayout ll1,ll2;
    LinearLayout chmscr;
    String cntcd="",chemistname="",doccntcd="",sttype="";
    ViewDialog progressDialoge;
    ImageView imgcard;

    List<DoctornamelistItem> doctornamelistItemList = new ArrayList();
    public List<String> arrayList = new ArrayList<>();
    List<ChemistdataItem> chemistdata=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemist_details);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Chemist Profile</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        cntcd = getIntent().getStringExtra("cntcd");
        //  docname=getIntent().getStringExtra("drname");
        chemistname=getIntent().getStringExtra("chemistname");
        sttype=getIntent().getStringExtra("sttype");

        chmkeypername=findViewById(R.id.chmkeypername);
        chmphoneno=findViewById(R.id.chmphoneno);
        takeselfie=findViewById(R.id.takeselfie);
        chmscr=findViewById(R.id.chmscr);
        chemistdoctor=findViewById(R.id.chemistdoctor);
        ll1=findViewById(R.id.ll1);
        ll2=findViewById(R.id.ll2);
        imgcard=findViewById(R.id.imagecard);
        progressDialoge = new ViewDialog(this);

        takeselfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // checkDate(docname);
                String doctorname = chemistdoctor.getSelectedItem().toString().trim();
                String keycontactper = chmkeypername.getText().toString().trim();
                String phonenumber = chmphoneno.getText().toString().trim();

                if (keycontactper.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(chmscr, "Please Enter Contact Person Name ", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (phonenumber.isEmpty() || phonenumber.length() < 10) {
                    Snackbar snackbar = Snackbar.make(chmscr, "Please Enter Valid Phone Number", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else {

                    sendData(doctorname, keycontactper, phonenumber);
                }
            }
        });

        getDoctorName();
        getChemistKeyPer();

    }

    public void sendData(String doctorname,String keycontactper,String phonenumber)
    {
        Intent intent=new Intent(ChemistDetails.this,CapNUpSelfie.class);
        intent.putExtra("doctorname",doctorname);
        intent.putExtra("chemistname",chemistname);
        intent.putExtra("keycontactper",keycontactper);
        intent.putExtra("phonenumber",phonenumber);
        intent.putExtra("cntcd",cntcd);
        intent.putExtra("doccntcd",doccntcd);
        intent.putExtra("flag","update");
        intent.putExtra("sttype",sttype);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ChemistDetails.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
        startActivity(intent, bndlanimation);
    }

    public void getDoctorName()
    {
        /*Log.d("Global.netid",Global.netid);
        Log.d("cntcd",cntcd);*/

        progressDialoge.show();
        retrofit2.Call<ChemistDoctorNameRes> call = RetrofitClient.getInstance().getApi().getChemistDrName(Global.netid,cntcd,sttype ,Global.dbprefix);
       // Log.d("call",call.toString());
        call.enqueue(new Callback<ChemistDoctorNameRes>() {
            @Override
            public void onResponse(Call<ChemistDoctorNameRes> call, Response<ChemistDoctorNameRes> response) {
                progressDialoge.dismiss();

                ChemistDoctorNameRes res=response.body();
             //   Log.d("res",res.toString());
                if(!res.isError())
                {
                    doctornamelistItemList = res.getDoctornamelist();
                    if(doctornamelistItemList.size()>0) {

                        for (int i = 0; i < doctornamelistItemList.size(); i++) {
                            arrayList.add(doctornamelistItemList.get(i).getDrname());
                            doccntcd=doctornamelistItemList.get(i).getCntcd();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChemistDetails.this, android.R.layout.simple_spinner_item, arrayList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        chemistdoctor.setAdapter(adapter);
                        chemistdoctor.setPrompt("Tagged Doctor(s)");
                        ll1.setVisibility(View.VISIBLE);
                    }
                    /*else if (docname!=null){
                        arrayList.add(docname);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.eis.dailycallregister.Activity.ChemistDetailsForm.this, android.R.layout.simple_spinner_item, arrayList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        chemistdoctor.setAdapter(adapter);
                        chemistdoctor.setPrompt("Tagged Doctor(s)");
                        ll1.setVisibility(View.VISIBLE);
                    }*/
                    else {
                            arrayList.add(" ");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChemistDetails.this, android.R.layout.simple_spinner_item, arrayList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            chemistdoctor.setAdapter(adapter);
                            chemistdoctor.setPrompt("Tagged Doctor(s)");
                            ll1.setVisibility(View.VISIBLE);
                        }
                }else {
                    Toast.makeText(ChemistDetails.this, "Doctor Not Present !", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ChemistDoctorNameRes> call, Throwable t) {
               // Log.d("onFailure",t.toString());
                progressDialoge.dismiss();
                Toast.makeText(ChemistDetails.this, "Failed to get Doctor list !", Toast.LENGTH_LONG).show();
            }
        });
    }

   public void getChemistKeyPer()
   {
       Log.d("getExistingSelfie","getExistingSelfie");
       Log.d("Global.netid",Global.netid);
       Log.d("cntcd",cntcd);

       retrofit2.Call<SetChemistkeyPerRes> call = RetrofitClient.getInstance().getApi().getChemistData(cntcd,Global.netid ,Global.dbprefix);
       call.enqueue(new Callback<SetChemistkeyPerRes>() {
           @Override
           public void onResponse(Call<SetChemistkeyPerRes> call, Response<SetChemistkeyPerRes> response) {
               SetChemistkeyPerRes res=response.body();
               Log.d("res",res.toString());
               if(!res.isError())
               {
                    chemistdata=res.getChemistdata();
                    ChemistdataItem cd=chemistdata.get(0);
                    if(chemistdata.size()>0)
                    {
                        chmkeypername.setText(cd.getKeyp());
                        chmphoneno.setText(cd.getMobileno());
                        Glide.with(ChemistDetails.this).load(cd.getImg_url()).error(Glide.with(ChemistDetails.this).load(R.drawable.ic_photo_not_found_24dp)).into(imgcard);

                    }else{
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
   }

   /*public void getExistingSelfie()
   {
        Log.d("getExistingSelfie","getExistingSelfie");
        Log.d("Global.netid",Global.netid);
        Log.d("cntcd",cntcd);

       retrofit2.Call<DefaultResponse> call1 = RetrofitClient.getInstance().getApi().getExistingSelfie(Global.netid, cntcd, Global.dbprefix);
       call1.enqueue(new Callback<DefaultResponse>() {
           @Override
           public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
              // progressDialoge.dismiss();
               DefaultResponse res=response.body();
               Log.d("response",res.toString());
               if(!res.isError())
               {
                       ll1.setVisibility(View.VISIBLE);
                       Glide.with(ChemistDetailsForm.this).load(res.getErrormsg()).into(imgcard);
               }
           }
           @Override
           public void onFailure(Call<DefaultResponse> call, Throwable t) {
             //  progressDialoge.dismiss();
               Snackbar snackbar = Snackbar.make(ll2, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
                       .setAction("Re-try", new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               getExistingSelfie();
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
        ChemistDetails.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
