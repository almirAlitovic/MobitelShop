package com.example.entreprenur.mobileshop.api.models;

import java.util.Date;
import java.util.List;

public class OrderRequest {

    public int BrojNarudzbe;
    public int UkupniIznos;
    public int StatusNarudzbeId = 1;
    public int KupacId;
    public Date Datum;
    public List<OrderItem> stavke;

    public static class OrderItem {
        public int Kolicina;
        public int Cijena;
        public int Iznos;
        public int MobiteliId;
    }
}
