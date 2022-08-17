package com.example.kakjayadi_warkopsharelok.activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.activity.MainActivity;

public class HomeAdminActivity extends Activity {

    TextView buttonLogoutAdmin, menuAdmin, penjualanAdmin, akunAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        buttonLogoutAdmin = findViewById(R.id.buttonLogoutAdmin);
        menuAdmin = findViewById(R.id.menuAdmin);
        penjualanAdmin = findViewById(R.id.penjualanAdmin);
        akunAdmin = findViewById(R.id.akunAdmin);

        buttonLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("SHAREDPREFERENCES_LOGIN", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("EMAIL", null);
                editor.putString("PASSWORD", null);
                editor.putString("NAMA", null);
                editor.putString("SEBAGAI", null);
                editor.apply();

                startActivity(new Intent(HomeAdminActivity.this, MainActivity.class));
                finish();
            }
        });
        menuAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAdminActivity.this, ListMenuAdminActivity.class));
            }
        });
        penjualanAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAdminActivity.this, ListLaporanPenjualanAdminActivity.class));

            }
        });
        akunAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAdminActivity.this, ListAkunUserAdminActivity.class));

            }
        });
    }
}