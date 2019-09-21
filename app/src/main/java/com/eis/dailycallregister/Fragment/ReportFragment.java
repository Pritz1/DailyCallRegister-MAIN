package com.eis.dailycallregister.Fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eis.dailycallregister.Activity.HomeActivity;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.R;

import java.time.Year;

import static com.eis.dailycallregister.Api.RetrofitClient.FMS_URL;


public class ReportFragment extends Fragment {
    public View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_report, container, false);
        getActivity().setTitle("Reports");

        redirectToBrowser();
        return view;

    }


    private void redirectToBrowser()

    {

        String msgstring = "To view reports click on View button.";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("Reports");
        builder.setMessage(Html.fromHtml(msgstring));
        builder.setPositiveButton("VIEW",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String link = FMS_URL+"eis.login.MobileSession?mode=reports&device=mobile&Elevel="+Global.emplevel+"&EmpCode="+Global.ecode+"&Ename="+Global.ename+"&DB="+Global.dbprefix+"&date="+Global.date+"&FinancialStartDate="+Global.FinancialStartDate+"&NetId="+Global.netid+"&Hname="+Global.hname;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(link));
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                        startActivity(intent, bndlanimation);
                    }
                });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        dialog.show();

    }

}

