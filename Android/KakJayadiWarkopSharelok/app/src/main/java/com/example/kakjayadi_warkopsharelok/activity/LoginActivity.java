package com.example.kakjayadi_warkopsharelok.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.activity.admin.HomeAdminActivity;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.UserModel;
import com.example.kakjayadi_warkopsharelok.util.LoadingAlertDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    EditText et_email, et_password;
    TextView tv_pindah_registrasi;
    ImageView btn_back_login;
    Button btn_login;

    SharedPreferences sp;

    String TAG = "LoginActivityData";

    String SHAREDPREFERENCES_CEK;

    //loading
    LoadingAlertDialog loading = new LoadingAlertDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        et_email= findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        tv_pindah_registrasi = findViewById(R.id.tv_pindah_registrasi);
        btn_login = findViewById(R.id.btn_login);
        btn_back_login = findViewById(R.id.btn_back_login);

        btn_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        sp = getSharedPreferences("SHAREDPREFERENCES_LOGIN", MODE_PRIVATE);
        SHAREDPREFERENCES_CEK = sp.getString("EMAIL", null);

        if(SHAREDPREFERENCES_CEK != null){
            finish();
        }

        tv_pindah_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrasiActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.alertDialogLoading(LoginActivity.this);
                if(et_email.getText().toString().trim().length() == 0){
                    et_email.setError("Tidak Boleh Kosong");
                }
                else if(et_email.getText().toString().trim().length() == 0){
                    et_password.setError("Tidak Boleh Kosong");
                }
                else{
                    ambilDataUser();
                }
            }
        });
    }

    private void ambilDataUser() {
        ApiService.getApiConfig()
                .getDataUserLogin("login", et_email.getText().toString().trim().toLowerCase(),
                                  et_password.getText().toString().trim().toLowerCase())
                .enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                try{
                    Log.d(TAG, "onResponse: "+et_email.getText().toString()+" dan "+et_password.getText().toString());
                    Log.d(TAG, "onResponse: " + response.body().get(0).getId_user());

                    String email = response.body().get(0).getEmail();
                    String password = response.body().get(0).getPassword();

                    Log.d(TAG, "onResponse: "+email+" dan "+password);

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("EMAIL", email);
                    editor.putString("PASSWORD", password);
                    editor.putString("SEBAGAI", response.body().get(0).getSebagai());
                    editor.putString("NAMA_KASIR", null);

                    if(response.body().get(0).getSebagai().trim().equals("pelanggan")){
                        editor.putString("NAMA", response.body().get(0).getNama());
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else if(response.body().get(0).getSebagai().trim().equals("kasir")){
                        editor.putString("NAMA", null);
                        editor.putString("NAMA_KASIR", response.body().get(0).getNama());
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
//                        Toast.makeText(LoginActivity.this, "kasir", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.body().get(0).getSebagai().trim().equals("admin")){
                        editor.putString("NAMA", "admin");
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this, HomeAdminActivity.class));
                        finish();
//                        Toast.makeText(LoginActivity.this, "kasir", Toast.LENGTH_SHORT).show();
                    }

                    loading.alertDialogCancel();
                    Toast.makeText(LoginActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();

//                    editor.apply();
//                    Toast.makeText(LoginActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                }
                catch (Exception ex){
                    loading.alertDialogCancel();
                    Toast.makeText(LoginActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error: "+ex);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                loading.alertDialogCancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}