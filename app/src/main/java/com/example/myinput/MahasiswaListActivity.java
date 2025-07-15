package com.example.myinput;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MahasiswaListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MahasiswaAdapter adapter;
    ArrayList<Mahasiswa> listMahasiswa;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this); // Inisialisasi db

        tampilkanDataMahasiswa();
    }

    // Method untuk ambil data dari SQLite
    private void tampilkanDataMahasiswa() {
        listMahasiswa = dbHelper.getSemuaMahasiswa(); // Ambil dari database
        adapter = new MahasiswaAdapter(this, listMahasiswa);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilkanDataMahasiswa(); // refresh data setiap activity dibuka lagi
    }
}