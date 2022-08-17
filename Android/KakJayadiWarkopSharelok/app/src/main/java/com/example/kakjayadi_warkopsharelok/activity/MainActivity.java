package com.example.kakjayadi_warkopsharelok.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.activity.admin.HomeAdminActivity;
import com.example.kakjayadi_warkopsharelok.fragment.AkunFragment;
import com.example.kakjayadi_warkopsharelok.fragment.HomeFragment;
import com.example.kakjayadi_warkopsharelok.fragment.PesananFragment;
import com.example.kakjayadi_warkopsharelok.util.LoadingAlertDialog;

public class MainActivity extends FragmentActivity {

    LinearLayout btnNavbarHome, btnNavbarPesanan, btnNavbarAkun;
    SharedPreferences sp;
    String email, nama;
    ImageView gambarHome, gambarPesanan, gambarAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btnNavbarHome = findViewById(R.id.btn_navbar_home);
        btnNavbarPesanan = findViewById(R.id.btn_navbar_pesanan);
        btnNavbarAkun = findViewById(R.id.btn_navbar_akun);

        gambarHome = findViewById(R.id.gambarHome);
        gambarPesanan = findViewById(R.id.gambarPesanan);
        gambarAkun = findViewById(R.id.gambarAkun);


        //SharedPreferences
        sp = getSharedPreferences("SHAREDPREFERENCES_LOGIN", Context.MODE_PRIVATE);

        if(sp.getString("SEBAGAI", null) != null){
            if (sp.getString("SEBAGAI", null).equals("admin")) {
                startActivity(new Intent(MainActivity.this, HomeAdminActivity.class));
                finish();
            }
        }

        btnNavbarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveHome();
            }
        });
        btnNavbarPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivePesanan();
            }
        });
        btnNavbarAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = sp.getString("EMAIL", null);
                nama = sp.getString("NAMA", null);

                if(email == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
                else if(email != null){
                    setActiveAkun();
                }
            }
        });


        setActiveHome();

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMainActivity, fragment);
        fragmentTransaction.commit();
    }

    public void setActiveHome(){
        replaceFragment(new HomeFragment());

        gambarHome.setImageResource(R.drawable.ic_baseline_home_24_aktif);
        gambarPesanan.setImageResource(R.drawable.ic_baseline_library_books_24);
        gambarAkun.setImageResource(R.drawable.ic_baseline_account_circle_24);
    }
    private void setActivePesanan(){
        replaceFragment(new PesananFragment());

        gambarHome.setImageResource(R.drawable.ic_baseline_home_24);
        gambarPesanan.setImageResource(R.drawable.ic_baseline_library_books_24_aktif);
        gambarAkun.setImageResource(R.drawable.ic_baseline_account_circle_24);
    }
    private void setActiveAkun(){
        replaceFragment(new AkunFragment());

        gambarHome.setImageResource(R.drawable.ic_baseline_home_24);
        gambarPesanan.setImageResource(R.drawable.ic_baseline_library_books_24);
        gambarAkun.setImageResource(R.drawable.ic_baseline_account_circle_24_aktif);
    }


}