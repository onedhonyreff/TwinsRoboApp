package com.codepan.twinsrobo_apps.Models;

import java.util.List;

public class ResponseModelWishlist {
    private int kodeWishlist;
    private String pesanWishlist;
    private List<DataModelWishlist> dataWishlist;

    public int getKodeWishlist() {
        return kodeWishlist;
    }

    public void setKodeWishlist(int kodeWishlist) {
        this.kodeWishlist = kodeWishlist;
    }

    public String getPesanWishlist() {
        return pesanWishlist;
    }

    public void setPesanWishlist(String pesanWishlist) {
        this.pesanWishlist = pesanWishlist;
    }

    public List<DataModelWishlist> getDataWishlist() {
        return dataWishlist;
    }

    public void setDataWishlist(List<DataModelWishlist> dataWishlist) {
        this.dataWishlist = dataWishlist;
    }
}
