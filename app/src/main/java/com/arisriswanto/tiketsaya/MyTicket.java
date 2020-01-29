package com.arisriswanto.tiketsaya;

public class MyTicket {

    String nama_wisata, lokasi;
    String jumlah_ticket;

    // Constructor


    public MyTicket() {
    }

    public MyTicket(String nama_wisata, String lokasi, String jumlah_ticket) {
        this.nama_wisata = nama_wisata;
        this.lokasi = lokasi;
        this.jumlah_ticket = jumlah_ticket;
    }


    // Setter & Getter

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJumlah_ticket() {
        return jumlah_ticket;
    }

    public void setJumlah_ticket(String jumlah_ticket) {
        this.jumlah_ticket = jumlah_ticket;
    }
}
