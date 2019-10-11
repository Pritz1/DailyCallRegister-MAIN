package com.eis.dailycallregister.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.MgrRcpaDrRes;
import com.eis.dailycallregister.Pojo.RcpadrListItem;
import com.eis.dailycallregister.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MgrRcpaDrList extends AppCompatActivity implements SearchView.OnQueryTextListener { //by prithvi SearchView.OnQueryTextListener

     RecyclerView drlistrecvw;
     ViewDialog progressDialoge;
    RelativeLayout nsv;


    LinearLayout ll1;
    TextView hqname;
    String headqname;
    String hname,netid;

    public List<RcpadrListItem> drlist= new ArrayList<>();
    public List<RcpadrListItem> drlistcopy = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mgr_rcpa_drlist);
        hname = getIntent().getStringExtra("hname");
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>DOCTOR LIST OF "+hname+"</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        netid = getIntent().getStringExtra("netid");
      //  headqname = getIntent().getStringExtra("hname");
      //  ll1 = findViewById(R.id.ll1);

      //  searchview=findViewById(R.id.searchview);
        hqname=findViewById(R.id.hqname);
        nsv = findViewById(R.id.nsv);
        drlistrecvw=findViewById(R.id.drlistrecvw);
        progressDialoge=new ViewDialog(this);
        drListAdapter();

       /* if(! headqname .equalsIgnoreCase("")){
            hqname.setText( headqname );
            ll1.setVisibility(View.VISIBLE);
        }
*/

        callApi();

       /* if(searchview!=null)

        {
            searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }*/
    }

    /*private void search(String str)
    {
        List<RcpadrListItem> mylist= new ArrayList<>();
        for (RcpadrListItem object:drlist)
        {
            if(object.getDrname().toLowerCase().contains(str.toLowerCase()))
            {
                mylist.add(object);
            }

        }
    }*/


    public void callApi()
    {
       // Log.d("netid",netid);
        progressDialoge.show();

      retrofit2.Call<MgrRcpaDrRes> call1=RetrofitClient.getInstance().getApi().getDrList(netid,Global.dbprefix);

      call1.enqueue(new Callback<MgrRcpaDrRes>() {
          @Override
          public void onResponse(Call<MgrRcpaDrRes> call, Response<MgrRcpaDrRes> response) {
              progressDialoge.dismiss();
             Log.d("response",response.toString());
              MgrRcpaDrRes res = response.body();
            //  Log.d("response 2",res.toString());

              drlist =  res.getDrlist();
              drlistcopy = new ArrayList<>(drlist); //prithvi 10/10/2019 - to include search functionality.
          //   Log.d("hqpsrlist 1",drlist.toString());
              drlistrecvw.setVisibility(View.VISIBLE);
              drlistrecvw.getAdapter().notifyDataSetChanged();
          }
          @Override
          public void onFailure(Call<MgrRcpaDrRes> call, Throwable t) {
           //   Log.d("onFailure","onFailure");
              progressDialoge.dismiss();
              Snackbar snackbar = Snackbar.make(nsv, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
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

    private  void drListAdapter()
    {
        drlistrecvw.setNestedScrollingEnabled(false);
        drlistrecvw.setLayoutManager(new LinearLayoutManager(MgrRcpaDrList.this));
        drlistrecvw.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(MgrRcpaDrList.this).inflate(R.layout.mgr_rcpa_drlist_adapter, viewGroup, false);
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
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Holder myHolder = (Holder) viewHolder;
                final RcpadrListItem model = drlist.get(i);
                myHolder.drcdndrname.setText(model.getDrcd()+". "+model.getDrname());

                myHolder.drcdndrname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MgrRcpaDrList.this,MgrRCPA.class);
                        intent.putExtra("drcd",model.getDrcd() );
                        intent.putExtra("wnetid", model.getNetid());
                        intent.putExtra("drname", model.getDrname());
                        intent.putExtra("cntcd", model.getCntcd());
                        intent.putExtra("hname", hname);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(MgrRcpaDrList.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                        startActivity(intent, bndlanimation);
                    }
                });
            }
            @Override
            public int getItemCount() {
                return drlist.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                TextView drcdndrname;

                public Holder(@NonNull View itemView) {
                    super(itemView);
                    drcdndrname = itemView.findViewById(R.id.drcdndrname);
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
          MgrRcpaDrList.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
//prithvi -> 10/10/2019 for search functionality
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
        List<RcpadrListItem> newList = new ArrayList<>();
        drlist = new ArrayList<>();

        if(newText != null && !newText.equalsIgnoreCase("")) {
            for (RcpadrListItem obj : drlistcopy) {
                if (obj.getDrname().toLowerCase().contains(userInput)) {
                    newList.add(obj);
                }
            }

            drlist.addAll(newList);
        }else{

            drlist.addAll(drlistcopy);
        }

        drlistrecvw.getAdapter().notifyDataSetChanged();

        return true;
    }

}
