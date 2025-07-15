package com.example.myinput;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    Button btnLihatData, btnInputData, btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnLihatData = findViewById(R.id.btnLihatData);
        btnInputData = findViewById(R.id.btnInputData);
        btnInfo = findViewById(R.id.btnInfo);

        btnLihatData.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MahasiswaListActivity.class);
            startActivity(intent);
        });

        btnInputData.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MahasiswaFormActivity.class);
            startActivity(intent);
        });

        btnInfo.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, InfoActivity.class);
            startActivity(intent);
        });
    }
}