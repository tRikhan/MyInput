package com.example.myinput;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.InputStream;

public class MahasiswaDetailActivity extends AppCompatActivity {

    TextView tvNpm, tvNama, tvTanggal, tvAlamat, tvTelepon, tvEmail, tvFakultas, tvJurusan, tvTahunMasuk;
    ImageView ivProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_detail);

        // Initialize views
        tvNpm = findViewById(R.id.tvNpm);
        tvNama = findViewById(R.id.tvNama);
        tvTanggal = findViewById(R.id.tvTanggal);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvTelepon = findViewById(R.id.tvTelepon);
        tvEmail = findViewById(R.id.tvEmail);
        tvFakultas = findViewById(R.id.tvFakultas);
        tvJurusan = findViewById(R.id.tvJurusan);
        tvTahunMasuk = findViewById(R.id.tvTahunMasuk);
        ivProfilePhoto = findViewById(R.id.ivProfilePhoto);

        // Get data from Intent
        String npm = getIntent().getStringExtra("npm");
        String nama = getIntent().getStringExtra("nama");
        String tanggal = getIntent().getStringExtra("tanggal");
        String alamat = getIntent().getStringExtra("alamat");
        String telepon = getIntent().getStringExtra("telepon");
        String email = getIntent().getStringExtra("email");
        String fakultas = getIntent().getStringExtra("fakultas");
        String jurusan = getIntent().getStringExtra("jurusan");
        String tahunMasuk = getIntent().getStringExtra("tahunMasuk");
        String fotoProfil = getIntent().getStringExtra("fotoProfil");

        // Display data
        tvNpm.setText("NPM: " + (npm != null ? npm : "-"));
        tvNama.setText("Nama: " + (nama != null ? nama : "-"));
        tvTanggal.setText("Tanggal Lahir: " + (tanggal != null ? tanggal : "-"));
        tvAlamat.setText("Alamat: " + (alamat != null ? alamat : "-"));
        tvTelepon.setText("Telepon: " + (telepon != null && !telepon.isEmpty() ? telepon : "-"));
        tvEmail.setText("Email: " + (email != null && !email.isEmpty() ? email : "-"));
        tvFakultas.setText("Fakultas: " + (fakultas != null && !fakultas.isEmpty() ? fakultas : "-"));
        tvJurusan.setText("Jurusan: " + (jurusan != null && !jurusan.isEmpty() ? jurusan : "-"));
        tvTahunMasuk.setText("Tahun Masuk: " + (tahunMasuk != null && !tahunMasuk.isEmpty() ? tahunMasuk : "-"));

        // Set profile photo
        loadProfilePhoto(fotoProfil);

    }
    
    private void loadProfilePhoto(String fotoPath) {
        System.out.println("loadProfil: " + fotoPath);
        
        if (fotoPath != null && !fotoPath.isEmpty() && !fotoPath.equals("")) {
            try {
                // Load image from internal storage file path
                Bitmap bitmap = BitmapFactory.decodeFile(fotoPath);
                
                if (bitmap != null) {
                    ivProfilePhoto.setImageBitmap(bitmap);
                    ivProfilePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return;
                } else {
                    // Check if file exists
                    File imageFile = new File(fotoPath);
                    if (imageFile.exists()) {
                        System.out.println("File ada tapi gagal dekode");
                    } else {
                        System.out.println("file tidak ada: " + fotoPath);
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception load gambar: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("fotopath kosong");
        }

        ivProfilePhoto.setImageResource(R.drawable.loginpageicon);
        ivProfilePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}