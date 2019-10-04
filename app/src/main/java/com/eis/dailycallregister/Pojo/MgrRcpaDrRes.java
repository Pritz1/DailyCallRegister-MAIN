package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MgrRcpaDrRes
{
    @SerializedName("drlist")
    private List<RcpadrListItem> drlist;

    @SerializedName("error")
    private boolean error;

    public List<RcpadrListItem> getDrlist() {
        return drlist;
    }

    public void setDrlist(List<RcpadrListItem> drlist) {
        this.drlist = drlist;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;

    }

    @Override
    public String toString() {
        return "MgrRcpaDrRes{" +
                "drlist=" + drlist +
                ", error=" + error +
                '}';
    }
}
