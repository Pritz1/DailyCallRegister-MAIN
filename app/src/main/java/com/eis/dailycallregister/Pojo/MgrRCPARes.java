package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MgrRCPARes
{

    @SerializedName("hqpsrlist")
    private List<RcpahqpsrlistItem> hqpsrlist;

    @SerializedName("error")
    private boolean error;

    public List<RcpahqpsrlistItem> getHqpsrlist() {
        return hqpsrlist;
    }

    public void setHqpsrlist(List<RcpahqpsrlistItem> hqpsrlist) {
        this.hqpsrlist = hqpsrlist;
    }

    public void setError(boolean error){
        this.error = error;
    }

    public boolean isError(){
        return error;
    }

    @Override
    public String toString(){
        return
                "GetRCPAhqpsrList{" +
                        "rcpahqpsrlist = '" + hqpsrlist + '\'' +
                        ",error = '" + error + '\'' +
                        "}";
    }
}
