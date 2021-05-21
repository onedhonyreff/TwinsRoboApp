package com.codepan.twinsrobo_apps.Models;

public class DataModelWishlist {
    private String idWishlist;
    private String idUser;
    private String idEvent;
    private String addedOn;

    public String getIdWishlist() {
        return idWishlist;
    }

    public void setIdWishlist(String idWishlist) {
        this.idWishlist = idWishlist;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }
}
