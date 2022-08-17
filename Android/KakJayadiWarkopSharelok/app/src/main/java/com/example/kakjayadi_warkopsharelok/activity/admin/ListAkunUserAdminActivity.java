package com.example.kakjayadi_warkopsharelok.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.activity.ProfilePesananActivity;
import com.example.kakjayadi_warkopsharelok.adapter.AkunAdapter;
import com.example.kakjayadi_warkopsharelok.adapter.ListPesananAdapter;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;
import com.example.kakjayadi_warkopsharelok.data.model.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAkunUserAdminActivity extends Activity {

    AkunAdapter listAkunAdapter;
    RecyclerView recyclerViewAdminListAkunKasir, recyclerViewAdminListAkunPelanggan;

    FloatingActionButton flAddAkunAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_akun_user_admin);

        recyclerViewAdminListAkunKasir = findViewById(R.id.recyclerViewAdminListAkunKasir);
        recyclerViewAdminListAkunPelanggan = findViewById(R.id.recyclerViewAdminListAkunPelanggan);

        flAddAkunAdmin = findViewById(R.id.flAddAkunAdmin);

        flAddAkunAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ListAkunUserAdminActivity.this, ProfileAkunActivity.class);
                in.putExtra("aksi", "tambah");
                startActivity(in);
            }
        });

        listAkunKasir();
        listAkunPelanggan();

    }

    private void listAkunKasir(){
        ApiService.getApiConfig().getAdminListAkunKasir("admin_list_akun_kasir")
                .enqueue(new Callback<ArrayList<UserModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                        listAkunAdapter = new AkunAdapter(response.body(), new AkunAdapter.AdapterListenerListAkun() {
                            @Override
                            public void onClick(UserModel userModel) {
                                Intent in = new Intent(ListAkunUserAdminActivity.this, ProfileAkunActivity.class);
                                in.putExtra("aksi", "update");
                                in.putExtra("id_user", userModel.getId_user());
                                in.putExtra("nama", userModel.getNama());
                                in.putExtra("nomor_wa", userModel.getNomor_wa());
                                in.putExtra("email", userModel.getEmail());
                                in.putExtra("password", userModel.getPassword());
                                in.putExtra("sebagai", userModel.getSebagai());
                                in.putExtra("shift", userModel.getShift());
                                startActivity(in);
//                                Toast.makeText(ListAkunUserAdminActivity.this, "akun kasir", Toast.LENGTH_SHORT).show();
                            }
                        });


                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListAkunUserAdminActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewAdminListAkunKasir.setLayoutManager(layoutManager);
                        recyclerViewAdminListAkunKasir.setAdapter(listAkunAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {

                    }
                });
    }
    private void listAkunPelanggan(){
        ApiService.getApiConfig().getAdminListAkunPelanggan("admin_list_akun_pelanggan")
                .enqueue(new Callback<ArrayList<UserModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                        listAkunAdapter = new AkunAdapter(response.body(), new AkunAdapter.AdapterListenerListAkun() {
                            @Override
                            public void onClick(UserModel userModel) {
                                Intent in = new Intent(ListAkunUserAdminActivity.this, ProfileAkunActivity.class);
                                in.putExtra("aksi", "update");
                                in.putExtra("id_user", userModel.getId_user());
                                in.putExtra("nama", userModel.getNama());
                                in.putExtra("nomor_wa", userModel.getNomor_wa());
                                in.putExtra("email", userModel.getEmail());
                                in.putExtra("password", userModel.getPassword());
                                in.putExtra("sebagai", userModel.getSebagai());
                                in.putExtra("shift", userModel.getShift());
                                startActivity(in);
                            }
                        });


                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListAkunUserAdminActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewAdminListAkunPelanggan.setLayoutManager(layoutManager);
                        recyclerViewAdminListAkunPelanggan.setAdapter(listAkunAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {

                    }
                });
    }
}