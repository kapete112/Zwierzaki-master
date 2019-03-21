package com.example.zwierzaki;

public class Wlasciciel {
    String imie;
    String nazwisko;
    String pesel;
    String nr_tel;

    public Wlasciciel() {
    }

    public Wlasciciel(String imie, String nazwisko, String pesel, String nr_tel) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.nr_tel = nr_tel;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public String getNr_tel() {
        return nr_tel;
    }
}
