package com.example.myinput;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_kampus";
    private static final int DATABASE_VERSION = 2; // Increased version for schema update

    private static final String TABLE_MAHASISWA = "mahasiswa";
    private static final String COL_NPM = "npm";
    private static final String COL_NAMA = "nama";
    private static final String COL_TANGGAL = "tanggal_lahir";
    private static final String COL_ALAMAT = "alamat";
    private static final String COL_TELEPON = "nomor_telepon";
    private static final String COL_EMAIL = "email";
    private static final String COL_FAKULTAS = "fakultas";
    private static final String COL_JURUSAN = "jurusan";
    private static final String COL_TAHUN_MASUK = "tahun_masuk";
    private static final String COL_FOTO_PROFIL = "foto_profil";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Dipanggil saat database pertama kali dibuat
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_MAHASISWA + " (" +
                COL_NPM + " TEXT PRIMARY KEY, " +
                COL_NAMA + " TEXT, " +
                COL_TANGGAL + " TEXT, " +
                COL_ALAMAT + " TEXT, " +
                COL_TELEPON + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_FAKULTAS + " TEXT, " +
                COL_JURUSAN + " TEXT, " +
                COL_TAHUN_MASUK + " TEXT, " +
                COL_FOTO_PROFIL + " TEXT)";
        db.execSQL(createTable);
    }

    // Dipanggil saat upgrade versi database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add new columns for existing tables
            db.execSQL("ALTER TABLE " + TABLE_MAHASISWA + " ADD COLUMN " + COL_TELEPON + " TEXT DEFAULT ''");
            db.execSQL("ALTER TABLE " + TABLE_MAHASISWA + " ADD COLUMN " + COL_EMAIL + " TEXT DEFAULT ''");
            db.execSQL("ALTER TABLE " + TABLE_MAHASISWA + " ADD COLUMN " + COL_FAKULTAS + " TEXT DEFAULT ''");
            db.execSQL("ALTER TABLE " + TABLE_MAHASISWA + " ADD COLUMN " + COL_JURUSAN + " TEXT DEFAULT ''");
            db.execSQL("ALTER TABLE " + TABLE_MAHASISWA + " ADD COLUMN " + COL_TAHUN_MASUK + " TEXT DEFAULT ''");
            db.execSQL("ALTER TABLE " + TABLE_MAHASISWA + " ADD COLUMN " + COL_FOTO_PROFIL + " TEXT DEFAULT ''");
        }
    }

    // INSERT DATA
    public boolean tambahMahasiswa(Mahasiswa mhs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NPM, mhs.getNpm());
        values.put(COL_NAMA, mhs.getNama());
        values.put(COL_TANGGAL, mhs.getTanggalLahir());
        values.put(COL_ALAMAT, mhs.getAlamat());
        values.put(COL_TELEPON, mhs.getNomorTelepon());
        values.put(COL_EMAIL, mhs.getEmail());
        values.put(COL_FAKULTAS, mhs.getFakultas());
        values.put(COL_JURUSAN, mhs.getJurusan());
        values.put(COL_TAHUN_MASUK, mhs.getTahunMasuk());
        values.put(COL_FOTO_PROFIL, mhs.getFotoProfil());

        long result = db.insert(TABLE_MAHASISWA, null, values);
        return result != -1; // true jika berhasil
    }

    // GET SEMUA DATA
    public ArrayList<Mahasiswa> getSemuaMahasiswa() {
        ArrayList<Mahasiswa> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MAHASISWA, null);

        if (cursor.moveToFirst()) {
            do {
                String npm = cursor.getString(cursor.getColumnIndexOrThrow(COL_NPM));
                String nama = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAMA));
                String tanggal = cursor.getString(cursor.getColumnIndexOrThrow(COL_TANGGAL));
                String alamat = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALAMAT));
                
                // Handle new columns with default empty values for backward compatibility
                String telepon = getColumnValue(cursor, COL_TELEPON, "");
                String email = getColumnValue(cursor, COL_EMAIL, "");
                String fakultas = getColumnValue(cursor, COL_FAKULTAS, "");
                String jurusan = getColumnValue(cursor, COL_JURUSAN, "");
                String tahunMasuk = getColumnValue(cursor, COL_TAHUN_MASUK, "");
                String fotoProfil = getColumnValue(cursor, COL_FOTO_PROFIL, "");

                Mahasiswa mhs = new Mahasiswa(npm, nama, tanggal, alamat, 
                                             telepon, email, fakultas, jurusan, tahunMasuk, fotoProfil);
                list.add(mhs);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    // Helper method to safely get column values
    private String getColumnValue(Cursor cursor, String columnName, String defaultValue) {
        try {
            int columnIndex = cursor.getColumnIndex(columnName);
            if (columnIndex != -1) {
                String value = cursor.getString(columnIndex);
                return value != null ? value : defaultValue;
            }
        } catch (Exception e) {
            // Column doesn't exist, return default value
        }
        return defaultValue;
    }

    public boolean updateMahasiswa(Mahasiswa mhs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAMA, mhs.getNama());
        values.put(COL_TANGGAL, mhs.getTanggalLahir());
        values.put(COL_ALAMAT, mhs.getAlamat());
        values.put(COL_TELEPON, mhs.getNomorTelepon());
        values.put(COL_EMAIL, mhs.getEmail());
        values.put(COL_FAKULTAS, mhs.getFakultas());
        values.put(COL_JURUSAN, mhs.getJurusan());
        values.put(COL_TAHUN_MASUK, mhs.getTahunMasuk());
        values.put(COL_FOTO_PROFIL, mhs.getFotoProfil());

        int result = db.update(TABLE_MAHASISWA, values, COL_NPM + " = ?", new String[]{mhs.getNpm()});
        return result > 0; // true jika ada baris yang ter-update
    }

    public boolean hapusMahasiswa(String npm) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_MAHASISWA, COL_NPM + " = ?", new String[]{npm});
        return result > 0;
    }


}
