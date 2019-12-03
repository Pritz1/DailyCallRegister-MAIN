package com.eis.dailycallregister.Activity;


import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.Pojo.PatientListRes;
import com.eis.dailycallregister.Pojo.PatientlistItem;
import com.eis.dailycallregister.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientList extends AppCompatActivity {

    RecyclerView patientlistrv;
    ViewDialog progressDialoge;
    RelativeLayout pnsv;
    String cntcd = "";


    public List<PatientlistItem> patientlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        cntcd = getIntent().getStringExtra("cntcd");

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Patient List</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        patientlistrv = findViewById(R.id.patientlistrv);
        progressDialoge = new ViewDialog(this);
        pnsv = findViewById(R.id.pnsv);

        patListAdapter();
        callApi();
    }

    public void callApi() {
        progressDialoge.show();
        Call<PatientListRes> call1 = RetrofitClient.getInstance().getApi().getPatientList(Global.netid, cntcd, Global.dbprefix);
        call1.enqueue(new Callback<PatientListRes>() {
            @Override
            public void onResponse(Call<PatientListRes> call, Response<PatientListRes> response) {

                progressDialoge.dismiss();
                PatientListRes res = response.body();
                // Log.d("res",res.toString());
                if (!res.isError()) {
                    patientlist = res.getPatientlist();
                    patientlistrv.setVisibility(View.VISIBLE);
                    patientlistrv.getAdapter().notifyDataSetChanged();
                }else {
                    Toast.makeText(PatientList.this,res.getErrormsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PatientListRes> call, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(pnsv, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                callApi();
                            }
                        });
                snackbar.show();
            }
        });
    }

    private void patListAdapter() {
        patientlistrv.setNestedScrollingEnabled(false);
        patientlistrv.setLayoutManager(new LinearLayoutManager(PatientList.this));
        patientlistrv.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(PatientList.this).inflate(R.layout.patient_list_adapter, viewGroup, false);
                Holder holder = new Holder(view);
                return holder;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder myHolder = (Holder) viewHolder;
                final PatientlistItem model = patientlist.get(i);

                myHolder.srno.setText("" + (i + 1));
                myHolder.patientname.setText(model.getPatientname());
                myHolder.phonenum.setText(model.getPhoneno());

                myHolder.deletepatient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deletePatient(model.getPatno(), cntcd,i);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return patientlist.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                TextView patientname, phonenum, srno;
                ImageView pnamet, pnoet;
                ImageButton deletepatient;

                public Holder(@NonNull View itemView) {
                    super(itemView);
                    srno = itemView.findViewById(R.id.srno);
                    patientname = itemView.findViewById(R.id.patientname);
                    phonenum = itemView.findViewById(R.id.phonenum);
                    pnamet = itemView.findViewById(R.id.pname);
                    pnoet = itemView.findViewById(R.id.phono);
                    deletepatient = itemView.findViewById(R.id.deletepatient);
                }
            }
        });
    }

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
        PatientList.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    public void deletePatient(final String patno, final String cntcd, final int position) {
        /*Log.d("patno",patno);
        Log.d("cntcd",cntcd);
        Log.d("Global.netid",Global.netid);*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete ?");
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialoge.show();
                       Call<PatientListRes> call1=RetrofitClient.getInstance().getApi().deletePatient(Global.netid,cntcd,patno,Global.dbprefix);

                        call1.enqueue(new Callback<PatientListRes>() {
                            @Override
                            public void onResponse(Call<PatientListRes> call, Response<PatientListRes> response) {
                                progressDialoge.dismiss();
                                PatientListRes res = response.body();
                                // Log.d("res",res.toString());
                                if (!res.isError()) {
                                    patientlistrv.removeViewAt(position);
                                    patientlist = res.getPatientlist();
                                    patientlistrv.setVisibility(View.VISIBLE);
                                    patientlistrv.getAdapter().notifyDataSetChanged();
                                    Toast.makeText(PatientList.this,res.getErrormsg(),Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(PatientList.this,res.getErrormsg(),Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<PatientListRes> call, Throwable t) {
                                progressDialoge.dismiss();
                                Snackbar snackbar = Snackbar.make(pnsv, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Retry", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                callApi();
                                            }
                                        });
                                snackbar.show();
                            }
                        });
                        }
                    });

        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();




}

    }


