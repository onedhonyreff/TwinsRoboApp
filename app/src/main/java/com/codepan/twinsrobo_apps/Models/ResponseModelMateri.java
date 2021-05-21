package com.codepan.twinsrobo_apps.Models;

import java.util.List;

public class ResponseModelMateri {
    private int kodeResponseMateri;
    private String pesanResponseMateri;
    private List<DataModelMateri> dataMateri;

    public int getKodeResponseMateri() {
        return kodeResponseMateri;
    }

    public void setKodeResponseMateri(int kodeResponseMateri) {
        this.kodeResponseMateri = kodeResponseMateri;
    }

    public String getPesanResponseMateri() {
        return pesanResponseMateri;
    }

    public void setPesanResponseMateri(String pesanResponseMateri) {
        this.pesanResponseMateri = pesanResponseMateri;
    }

    public List<DataModelMateri> getDataMateri() {
        return dataMateri;
    }

    public void setDataMateri(List<DataModelMateri> dataMateri) {
        this.dataMateri = dataMateri;
    }
}
