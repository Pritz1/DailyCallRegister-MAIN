package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.ChemistProRes;
import com.eis.dailycallregister.Pojo.ChemistprofilelistItem;
import com.eis.dailycallregister.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChemistProfileList extends AppCompatActivity implements SearchView.OnQueryTextListener  {

    RecyclerView pchemistlistrv;
    ViewDialog progressDialoge;
    Button addnewchemist;
    ConstraintLayout nsv;

    public List<ChemistprofilelistItem> chemistprofilelist=new ArrayList<>();
    public List<ChemistprofilelistItem> chemistprofilelistcopy = new ArrayList<>();

    final String[] mthyr = Global.date.split("-");
    String yr=mthyr[0];
    String mth=mthyr[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemist_profile_list);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Chemist List</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        pchemistlistrv=findViewById(R.id.pchemistlistrv);
        nsv=findViewById(R.id.nsv);
        addnewchemist=findViewById(R.id.addnewchemist);

        progressDialoge=new ViewDialog(this);

        ChemistListAdapter();
        callapi();


        addnewchemist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChemistProfileList.this,DrListToAddOtherChemist.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ChemistProfileList.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent,bndlanimation);
            }
        });

    }

    public void callapi()
    {
        progressDialoge.show();
        /*Log.d("mth",mth);
        Log.d("yr",yr);
        Log.d("Global.netid",Global.netid);
        Log.d("Global.dbprefix",Global.dbprefix);*/

        retrofit2.Call<ChemistProRes> call1 = RetrofitClient.getInstance().getApi().chemistProfileList(Global.netid,mth,yr,Global.dbprefix);
        //Log.d("call1",call1.toString());
        call1.enqueue(new Callback<ChemistProRes>() {
            @Override
            public void onResponse(Call<ChemistProRes> call, Response<ChemistProRes> response) {
                progressDialoge.dismiss();
                ChemistProRes res=response.body();
               Log.d("Response",res.toString());

                    chemistprofilelist = res.getChemistprofilelist();
                    chemistprofilelistcopy = new ArrayList<>(chemistprofilelist);
                    pchemistlistrv.setVisibility(View.VISIBLE);
                    pchemistlistrv.getAdapter().notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ChemistProRes> call, Throwable t) {
                progressDialoge.dismiss();
                //Log.d("error ",t.toString());
                Snackbar snackbar = Snackbar.make(nsv, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                callapi();
                            }
                        });
                snackbar.show();
            }
        });
    }

   public void ChemistListAdapter()
    {
        pchemistlistrv.setNestedScrollingEnabled(false);
        pchemistlistrv.setLayoutManager(new LinearLayoutManager(ChemistProfileList.this));
        pchemistlistrv.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(ChemistProfileList.this).inflate(R.layout.chemist_profile_list_adapter,viewGroup, false);
                Holder holder=new Holder(view);
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
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Holder myHolder = (Holder) viewHolder;
                final ChemistprofilelistItem model = chemistprofilelist.get(i);
               // Log.d("stname",model.getStname());
                myHolder.chemistname.setText(model.getStname());

                if (model.getStatus().equalsIgnoreCase("A")) {
                    myHolder.chmimage.setImageResource(R.drawable.ic_chemist_green);
                } else {
                    myHolder.chmimage.setImageResource(R.drawable.ic_chemist_red);
                }
                myHolder.chemistname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChemistProfileList.this, ChemistDetails.class);
                        intent.putExtra("stcd",model.getStcd() );
                        intent.putExtra("sttype", model.getSttype());
                        intent.putExtra("chemistname", model.getStname());
                        intent.putExtra("cntcd", model.getCntcd());
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ChemistProfileList.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                        startActivity(intent, bndlanimation);
                    }
                });
            }
            @Override
            public int getItemCount() {
                return chemistprofilelist.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                TextView chemistname;
                ImageView  chmimage;

                public Holder(@NonNull View itemView) {
                    super(itemView);
                    chemistname=itemView.findViewById(R.id.chemistname);
                    chmimage=itemView.findViewById(R.id.chmimage);
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
        //finish();
        Intent intent = new Intent(ChemistProfileList.this, HomeActivity.class);
        intent.putExtra("openfrag","home");
        Bundle bndlanimation=ActivityOptions.makeCustomAnimation(ChemistProfileList.this,R.anim.trans_right_in, R.anim.trans_right_out).toBundle();
        startActivity(intent,bndlanimation);
        // PatientDrList.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");
        LinearLayout first = (LinearLayout)searchView.getChildAt(0);
        LinearLayout second = (LinearLayout)first.getChildAt(2);
        LinearLayout third = (LinearLayout)second.getChildAt(1);
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete)third.getChildAt(0);
        autoComplete.setHintTextColor(getResources().getColor(R.color.charcoal));
        autoComplete.setTextColor(getResources().getColor(R.color.charcoal));
        searchView.setOnQueryTextListener(this);
        ImageView searchClose = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setImageResource(R.drawable.ic_clear_24dp);
        //Field mCollapseIcon = searchView.get;

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        List<ChemistprofilelistItem> newList = new ArrayList<>();
        chemistprofilelist = new ArrayList<>();

        if(newText != null && !newText.equalsIgnoreCase("")) {
            for (ChemistprofilelistItem obj : chemistprofilelistcopy) {
                if (obj.getStname().toLowerCase().contains(userInput)) {
                    newList.add(obj);
                }
            }

            chemistprofilelist.addAll(newList);
        }else{

            chemistprofilelist.addAll(chemistprofilelistcopy);
        }

        pchemistlistrv.getAdapter().notifyDataSetChanged();

        return true;
    }




}
