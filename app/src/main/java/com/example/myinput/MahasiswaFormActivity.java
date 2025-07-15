package com.example.myinput;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;

public class MahasiswaFormActivity extends AppCompatActivity {
    EditText etNpm, etNama, etTanggal, etAlamat, etTelepon, etEmail, etFakultas, etJurusan, etTahunMasuk;
    Button btnSimpan, btnChoosePhoto;
    ImageView ivProfilePhoto;
    DatabaseHelper dbHelper;
    boolean isEdit = false;
    String selectedImagePath = "";

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_form);

        initializeViews();
        dbHelper = new DatabaseHelper(this);
        initializeImagePicker();
        setupListeners();
        checkEditMode();
    }

    private void initializeViews() {
        etNpm = findViewById(R.id.etNpm);
        etNama = findViewById(R.id.etNama);
        etTanggal = findViewById(R.id.etTanggal);
        etAlamat = findViewById(R.id.etAlamat);
        etTelepon = findViewById(R.id.etTelepon);
        etEmail = findViewById(R.id.etEmail);
        etFakultas = findViewById(R.id.etFakultas);
        etJurusan = findViewById(R.id.etJurusan);
        etTahunMasuk = findViewById(R.id.etTahunMasuk);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnChoosePhoto = findViewById(R.id.btnChoosePhoto);
        ivProfilePhoto = findViewById(R.id.ivProfilePhoto);
        
        //Set default profil
        ivProfilePhoto.setImageResource(R.drawable.loginpageicon);
    }

    private void initializeImagePicker() {
        imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        try {
                            String savedImagePath = copyImageToInternalStorage(selectedImageUri);
                            
                            if (savedImagePath != null) {
                                Bitmap savedImage = BitmapFactory.decodeFile(savedImagePath);
                                
                                if (savedImage != null) {
                                    ivProfilePhoto.setImageBitmap(savedImage);
                                    ivProfilePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    
                                    // Store the internal file path
                                    selectedImagePath = savedImagePath;
                                    
                                    Toast.makeText(this, "Foto berhasil dipilih", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Gagal menyimpan gambar", Toast.LENGTH_SHORT).show();
                            }
                            
                        } catch (Exception e) {
                            Toast.makeText(this, "Error loading image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }
            }
        );
    }

    private void setupListeners() {
        // Date picker
        etTanggal.setFocusable(false);
        etTanggal.setOnClickListener(v -> showDatePicker());

        // Image picker
        btnChoosePhoto.setOnClickListener(v -> openImagePicker());

        // Save button
        btnSimpan.setOnClickListener(v -> saveData());
    }
    
    private void checkEditMode() {
        if (getIntent().hasExtra("edit_mode")) {
            isEdit = true;
            etNpm.setEnabled(false);
            
            //Pre-fill form
            etNpm.setText(getIntent().getStringExtra("npm"));
            etNama.setText(getIntent().getStringExtra("nama"));
            etTanggal.setText(getIntent().getStringExtra("tanggal"));
            etAlamat.setText(getIntent().getStringExtra("alamat"));
            etTelepon.setText(getIntent().getStringExtra("telepon"));
            etEmail.setText(getIntent().getStringExtra("email"));
            etFakultas.setText(getIntent().getStringExtra("fakultas"));
            etJurusan.setText(getIntent().getStringExtra("jurusan"));
            etTahunMasuk.setText(getIntent().getStringExtra("tahunMasuk"));

            String fotoPath = getIntent().getStringExtra("fotoProfil");
            if (fotoPath != null && !fotoPath.isEmpty() && !fotoPath.equals("")) {
                selectedImagePath = fotoPath;
                loadImageFromPath(fotoPath);
            }
        }
    }
    
    private void loadImageFromPath(String imagePath) {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                
                if (bitmap != null) {
                    ivProfilePhoto.setImageBitmap(bitmap);
                    ivProfilePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else {
                    ivProfilePhoto.setImageResource(R.drawable.loginpageicon);
                    Toast.makeText(this, "Tidak dapat memuat foto tersimpan, menggunakan foto default", Toast.LENGTH_SHORT).show();
                }
            } else {
                ivProfilePhoto.setImageResource(R.drawable.loginpageicon);
            }
        } catch (Exception e) {
            ivProfilePhoto.setImageResource(R.drawable.loginpageicon);
            Toast.makeText(this, "Error memuat foto, menggunakan foto default", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        imagePickerLauncher.launch(intent);
    }

    private void saveData() {
        //input value
        String npm = etNpm.getText().toString().trim();
        String nama = etNama.getText().toString().trim();
        String tanggal = etTanggal.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();
        String telepon = etTelepon.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String fakultas = etFakultas.getText().toString().trim();
        String jurusan = etJurusan.getText().toString().trim();
        String tahunMasuk = etTahunMasuk.getText().toString().trim();

        // validasi input
        if (npm.isEmpty() || nama.isEmpty() || tanggal.isEmpty() || alamat.isEmpty()) {
            Toast.makeText(this, "NPM, Nama, Tanggal Lahir, dan Alamat harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // validasi format email
        if (!email.isEmpty() && !isValidEmail(email)) {
            Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!tahunMasuk.isEmpty() && !isValidYear(tahunMasuk)) {
            Toast.makeText(this, "Tahun masuk harus berupa 4 digit angka", Toast.LENGTH_SHORT).show();
            return;
        }

        // buat Mahasiswa object
        Mahasiswa mhs = new Mahasiswa(npm, nama, tanggal, alamat, telepon, email, 
                                     fakultas, jurusan, tahunMasuk, selectedImagePath);

        System.out.println("DEBUG: Saving photo path: " + selectedImagePath);

        // Save ke database
        boolean sukses;
        if (isEdit) {
            // replace foto
            String oldPhotoPath = getIntent().getStringExtra("fotoProfil");
            if (!selectedImagePath.equals(oldPhotoPath) && 
                oldPhotoPath != null && !oldPhotoPath.isEmpty() &&
                selectedImagePath != null && !selectedImagePath.isEmpty()) {
                deleteOldPhoto(oldPhotoPath);
            }
            sukses = dbHelper.updateMahasiswa(mhs);
        } else {
            sukses = dbHelper.tambahMahasiswa(mhs);
        }

        if (sukses) {
            Toast.makeText(this, isEdit ? "Data berhasil diupdate" : "Data berhasil disimpan", 
                          Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format date as yyyy-mm-dd
                    String tanggal = String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    etTanggal.setText(tanggal);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidYear(String year) {
        try {
            int yearInt = Integer.parseInt(year);
            return yearInt >= 1900 && yearInt <= Calendar.getInstance().get(Calendar.YEAR);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // metode copy gambar dari external URI ke internal
    private String copyImageToInternalStorage(Uri imageUri) {
        try {

            File internalDir = new File(getFilesDir(), "profile_photos");
            if (!internalDir.exists()) {
                internalDir.mkdirs();
            }

            String fileName = "profile_" + System.currentTimeMillis() + ".jpg";
            File internalFile = new File(internalDir, fileName);

            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            if (inputStream != null) {

                //kompresi gambar
                Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                
                if (originalBitmap != null) {

                    FileOutputStream outputStream = new FileOutputStream(internalFile);

                    originalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                    outputStream.close();
                    
                    System.out.println("gambar tersimpan di: " + internalFile.getAbsolutePath());
                    return internalFile.getAbsolutePath();
                }
            }
        } catch (Exception e) {
            System.out.println("Error copy gambar: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void deleteOldPhoto(String oldPhotoPath) {
        if (oldPhotoPath != null && !oldPhotoPath.isEmpty() && !oldPhotoPath.equals("")) {
            try {
                File oldFile = new File(oldPhotoPath);
                if (oldFile.exists()) {
                    boolean deleted = oldFile.delete();
                    System.out.println("foto terhapus: " + deleted);
                }
            } catch (Exception e) {
                System.out.println("Error hapus foto: " + e.getMessage());
            }
        }
    }
}