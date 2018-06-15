package com.example.entreprenur.mobileshop.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserRegister {
    @SerializedName("Ime")
    @Expose
    public String Ime;

    @SerializedName("Prezime")
    @Expose
    public String Prezime;

    @SerializedName("Email")
    @Expose
    public String Email;

    @SerializedName("Lozinka")
    @Expose
    public String Lozinka;

    @SerializedName("KorisnickoIme")
    @Expose
    public String KorisnickoIme;

    @SerializedName("Status")
    @Expose
    public Boolean Status;

    @SerializedName("DatumRegistracije")
    @Expose
    public Date datumRegistracije;

    public Date getDatumRegistracije() {
        return datumRegistracije;
    }

    public void setDatumRegistracije(Date datumRegistracije) {
        this.datumRegistracije = datumRegistracije;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLozinka() {
        return Lozinka;
    }

    public void setLozinka(String lozinka) {
        Lozinka = lozinka;
    }

    public String getKorisnickoIme() {
        return KorisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        KorisnickoIme = korisnickoIme;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
