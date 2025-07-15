package com.example.myinput;

public class Mahasiswa {
    private String npm;
    private String nama;
    private String tanggalLahir;
    private String alamat;
    private String nomorTelepon;
    private String email;
    private String fakultas;
    private String jurusan;
    private String tahunMasuk;
    private String fotoProfil;

    // Constructor with all fields
    public Mahasiswa(String npm, String nama, String tanggalLahir, String alamat, 
                    String nomorTelepon, String email, String fakultas, String jurusan, 
                    String tahunMasuk, String fotoProfil) {
        this.npm = npm;
        this.nama = nama;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.fakultas = fakultas;
        this.jurusan = jurusan;
        this.tahunMasuk = tahunMasuk;
        this.fotoProfil = fotoProfil;
    }

    // Constructor for backward compatibility (old fields only)
    public Mahasiswa(String npm, String nama, String tanggalLahir, String alamat) {
        this(npm, nama, tanggalLahir, alamat, "", "", "", "", "", "");
    }

    // Getters
    public String getNpm() {
        return npm;
    }

    public String getNama() {
        return nama;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public String getEmail() {
        return email;
    }

    public String getFakultas() {
        return fakultas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public String getTahunMasuk() {
        return tahunMasuk;
    }

    public String getFotoProfil() {
        return fotoProfil;
    }

    // Setters
    public void setNpm(String npm) {
        this.npm = npm;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public void setTahunMasuk(String tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public void setFotoProfil(String fotoProfil) {
        this.fotoProfil = fotoProfil;
    }
}
