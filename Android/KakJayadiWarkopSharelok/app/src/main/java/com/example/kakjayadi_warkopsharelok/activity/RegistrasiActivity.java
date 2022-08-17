package com.example.kakjayadi_warkopsharelok.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.PostUserModel;
import com.example.kakjayadi_warkopsharelok.data.model.UserModel;
import com.example.kakjayadi_warkopsharelok.util.LoadingAlertDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrasiActivity extends Activity {

    EditText et_registrasi_nama, et_registrasi_nomor_wa, et_registrasi_email, et_registrasi_password;
    TextView tv_pindah_login;
    Button btn_daftar;
    String TAG = "RegistrasiActivity";
    ImageView btn_back_registrasi;

    //loading
    LoadingAlertDialog loading = new LoadingAlertDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registrasi);

        et_registrasi_nama = findViewById(R.id.et_registrasi_nama);
        et_registrasi_nomor_wa = findViewById(R.id.et_registrasi_nomor_wa);
        et_registrasi_email = findViewById(R.id.et_registrasi_email);
        et_registrasi_password = findViewById(R.id.et_registrasi_password);
        btn_daftar = findViewById(R.id.btn_daftar);
        btn_back_registrasi = findViewById(R.id.btn_back_registrasi);

        tv_pindah_login = findViewById(R.id.tv_pindah_login);
        tv_pindah_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_registrasi_nama.getText().toString().trim().length() == 0){
                    et_registrasi_nama.setError("Tidak Boleh Kosong");
                }
                else if(et_registrasi_nomor_wa.getText().toString().trim().length() == 0){
                    et_registrasi_nomor_wa.setError("Tidak Boleh Kosong");
                }
                else if(et_registrasi_email.getText().toString().trim().length() == 0){
                    et_registrasi_email.setError("Tidak Boleh Kosong");
                }
                else if(et_registrasi_password.getText().toString().trim().length() == 0){
                    et_registrasi_password.setError("Tidak Boleh Kosong");
                }
                else{
                    loading.alertDialogLoading(RegistrasiActivity.this);
                    getDataUserCekRegistrasi();
                }
            }
        });
        btn_back_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataUserCekRegistrasi(){
        ApiService.getApiConfig().getDataUserCekRegistrasi("cek_registrasi", et_registrasi_email.getText().toString().trim().toLowerCase(), "pelanggan")
                .enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                if(response.body().size() > 0){
                    Toast.makeText(RegistrasiActivity.this, "Username Sudah Ada", Toast.LENGTH_SHORT).show();
                    loading.alertDialogCancel();
                }
                else{
                    postDataUser();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {

                Log.d(TAG, "onResponse: "+t.getMessage());
            }
        });
    }

    private void postDataUser(){
            ApiService.getApiConfig().postDataUser("tambahUser",et_registrasi_nama.getText().toString().trim(),
                            et_registrasi_nomor_wa.getText().toString().trim(),
                            et_registrasi_email.getText().toString().trim().toLowerCase().toLowerCase(),
                            et_registrasi_password.getText().toString().trim().toLowerCase(),
                            "pelanggan","none")
                    .enqueue(new Callback<PostUserModel>() {
                        @Override
                        public void onResponse(Call<PostUserModel> call, Response<PostUserModel> response) {
                            Toast.makeText(RegistrasiActivity.this, "Berhasil Membuat Akun", Toast.LENGTH_SHORT).show();
                            loading.alertDialogCancel();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<PostUserModel> call, Throwable t) {
                            Toast.makeText(RegistrasiActivity.this, "Berhasil Membuat Akun", Toast.LENGTH_SHORT).show();
                            loading.alertDialogCancel();
                            finish();
                        }
                    });
    }
}