package com.example.entreprenur.mobileshop.api.models;

import java.util.List;

public class OrderResponse {

    public int Id;
    public int UkupniIznos;
    public int StatusNarudzbeId;
    public int KupacId;
    public List<OrderRequest.OrderItem> stavke;

    public static class OrderItem {
        public int Kolicina;
        public int Cijena;
        public int Iznos;
        public int MobiteliId;
    }
}
