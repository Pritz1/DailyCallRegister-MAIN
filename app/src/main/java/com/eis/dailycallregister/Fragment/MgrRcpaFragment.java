package com.eis.dailycallregister.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Activity.MgrRcpaDrList;
import com.eis.dailycallregister.Activity.Quiz;
import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.MgrRCPARes;
import com.eis.dailycallregister.Pojo.RcpahqpsrlistItem;
import com.eis.dailycallregister.Pojo.TestlstItem;
import com.eis.dailycallregister.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MgrRcpaFragment extends Fragment {

    View view;
    ViewDialog progressDialoge;

    RecyclerView rcpahqpsrlist;

   public List<RcpahqpsrlistItem> hqpsrlist=new ArrayList<>();

    final String[] mthyr = Global.date.split("-");

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("RCPA");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_mgr_rcpa, container, false);
        progressDialoge = new ViewDialog(getActivity());
        rcpahqpsrlist =view.findViewById(R.id.rcpahqpsrlist);

        hqListAdapter();
        callApi();
       return view;
    }

    public  void callApi()
    {
        progressDialoge.show();

        retrofit2.Call<MgrRCPARes> call1 = RetrofitClient
                .getInstance().getApi().getHqPsrListUnderMgr(Global.ecode,Global.dbprefix,mthyr[0],mthyr[1],Global.emplevel);
   //     Log.d("call1",call1.toString());

        call1.enqueue(new Callback<MgrRCPARes>() {

            @Override
            public void onResponse(retrofit2.Call<MgrRCPARes> call1, Response<MgrRCPARes> response) {
                progressDialoge.dismiss();
                MgrRCPARes res = response.body();
                //Log.d("response 2",res.toString());
                if (response != null) {
                    hqpsrlist = res.getHqpsrlist();
                    rcpahqpsrlist.setVisibility(View.VISIBLE);
                    rcpahqpsrlist.getAdapter().notifyDataSetChanged();
                }else {
                    Snackbar.make(view, "Data Is Not Recived From Database", Snackbar.LENGTH_LONG).show();

                }

            }
            @Override
            public void onFailure(Call<MgrRCPARes> call, Throwable t) {
                progressDialoge.dismiss();
              //  Log.d("OnFilure","onFailure");
                Snackbar snackbar = Snackbar.make(view, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
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

    private void hqListAdapter()
    {
        rcpahqpsrlist.setNestedScrollingEnabled(false);
        rcpahqpsrlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcpahqpsrlist.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.mgr_rcpa_hqpsrlist_adapter, viewGroup, false);
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
                final RcpahqpsrlistItem model = hqpsrlist.get(i);

            //    Log.d("hqpsrlist.get(i);",hqpsrlist.get(i).toString());

              //  Log.d("Mode2",model.getHname());

                myHolder.hqad.setText(model.getHname()+"/"+model.getEname());

                myHolder.itemView.setTag(i);
              myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),MgrRcpaDrList.class);
                        intent.putExtra("netid", model.getNetid());
                        intent.putExtra("emplevel", model.getLevel1());
                        intent.putExtra("ename", model.getEname());
                        intent.putExtra("hname", model.getHname());
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                        startActivity(intent, bndlanimation);
                    }
                });
            }
            @Override
            public int getItemCount() {
                return hqpsrlist.size();
            }

            class Holder extends RecyclerView.ViewHolder {
                TextView hqad;

                public Holder(@NonNull View itemView) {
                    super(itemView);
                    hqad = itemView.findViewById(R.id.hq);
                }
            }
        });
    }
}
