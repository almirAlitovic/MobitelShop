package com.example.entreprenur.mobileshop.api.models;

import java.util.List;

public class UserModel {

    public Long Id;
    public String Ime;
    public String Prezime;
    public String Email;
    public String KorisnickoIme;
    public String Lozinka;
    public Boolean Status;
    public List<Order> Narudzbe;
    public List<Order> Narudzbe1;

    public class Order {

        public Long Id;
        public String BrojNarudzbe;
        public Integer UkupniIznos;
        public Long KupacId;
        public List<OrderItems> NarudzbaStavke;
    }

    public class OrderItems {

    }
}

/*
            "Id": 1,
            "Ime": "android",
            "Prezime": null,
            "DatumRegistracije": "2018-01-22T00:00:00",
            "Email": "andro@gmail.com",
            "KorisnickoIme": "android",
            "Lozinka": "test",
            "Status": true,
            "Narudzbe": [],
            "Narudzbe1": []

*/


/*    {
            "Id": 1,
            "BrojNarudzbe": "111",
            "Datum": "2018-05-31T00:00:00",
            "UkupniIznos": 1500,
            "KupacId": 1,
            "StatusNarudzbeId": 1,
            "Kupci": null,
            "Kupci1": null,
            "NarudzbaStavke": []
       */