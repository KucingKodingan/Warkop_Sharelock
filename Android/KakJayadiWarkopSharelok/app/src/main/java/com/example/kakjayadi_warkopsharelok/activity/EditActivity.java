package com.example.kakjayadi_warkopsharelok.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.PostUserModel;
import com.example.kakjayadi_warkopsharelok.data.model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends Activity {

    String TAG = "EditActivity";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    TextInputEditText et_nama, et_nomor_wa, et_email, et_password;
    Button btn_simpan, btn_batal;

    String id_user, email, password;

    //btn back
    ImageView btn_back_edit_akun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle bundle = getIntent().getExtras();
        id_user = bundle.getString("id_user").toString();
        email = bundle.getString("email").toString();
        password = bundle.getString("password").toString();

        btn_back_edit_akun = findViewById(R.id.btn_back_edit_akun);
        et_nama = findViewById(R.id.et_nama);
        et_nomor_wa = findViewById(R.id.et_nomor_wa);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        btn_batal = findViewById(R.id.btn_batal);
        btn_simpan = findViewById(R.id.btn_simpan);

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_back_edit_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ambilDataUser();
    }

    private void ambilDataUser() {
        ApiService.getApiConfig().getDataUserLogin("login", email, password).enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                try{
                    et_nama.setText(response.body().get(0).getNama().toString());
                    et_nomor_wa.setText(response.body().get(0).getNomor_wa().toString());
                    et_email.setText(response.body().get(0).getEmail().toString());
                    et_password.setText(response.body().get(0).getPassword().toString());

                    btn_simpan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ApiService.getApiConfig().updateDataUser("updateUser",id_user,
                                                                    et_nama.getText().toString(),
                                                                    et_nomor_wa.getText().toString(),
                                                                    et_email.getText().toString(),
                                                                    et_password.getText().toString())
                                    .enqueue(new Callback<PostUserModel>() {
                                        @Override
                                        public void onResponse(Call<PostUserModel> call, Response<PostUserModel> response) {
//                                            Toast.makeText(EditActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<PostUserModel> call, Throwable t) {
//                                            Log.d(TAG, "onFailure: "+t.getMessage().toString()+id_user);
                                            finish();
                                        }
                                    });
                        }
                    });

                }
                catch (Exception ex){
                    Toast.makeText(EditActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error: "+ex);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {

            }
        });
    }
    private void updateUser(){

    }
}