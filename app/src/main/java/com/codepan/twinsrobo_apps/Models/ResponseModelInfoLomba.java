package com.codepan.twinsrobo_apps.Models;

import java.util.List;

public class ResponseModelInfoLomba {
    private int kodeInfoLomba;
    private String pesanInfoLomba;
    private String jumlahInfoLomba;
    private List<DataModelInfoLomba> dataInfoLomba;

    public int getKodeInfoLomba() {
        return kodeInfoLomba;
    }

    public void setKodeInfoLomba(int kodeInfoLomba) {
        this.kodeInfoLomba = kodeInfoLomba;
    }

    public String getPesanInfoLomba() {
        return pesanInfoLomba;
    }

    public void setPesanInfoLomba(String pesanInfoLomba) {
        this.pesanInfoLomba = pesanInfoLomba;
    }

    public String getJumlahInfoLomba() {
        return jumlahInfoLomba;
    }

    public List<DataModelInfoLomba> getDataInfoLomba() {
        return dataInfoLomba;
    }

    public void setJumlahInfoLomba(String jumlahInfoLomba) {
        this.jumlahInfoLomba = jumlahInfoLomba;
    }

    public void setDataInfoLomba(List<DataModelInfoLomba> dataInfoLomba) {
        this.dataInfoLomba = dataInfoLomba;
    }
}
