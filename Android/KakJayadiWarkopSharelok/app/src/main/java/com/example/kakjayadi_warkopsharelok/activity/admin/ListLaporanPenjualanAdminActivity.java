package com.example.kakjayadi_warkopsharelok.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.activity.ProfilePesananActivity;
import com.example.kakjayadi_warkopsharelok.adapter.ListPesananAdapter;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListLaporanPenjualanAdminActivity extends Activity {

    RecyclerView recyclerViewAdminListPesananBelumBayar, recyclerViewAdminListPesananSudahBayar, recyclerViewAdminListPesananBelumPesan;
    ListPesananAdapter listPesananAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_penjualan_admin);

        recyclerViewAdminListPesananBelumPesan = findViewById(R.id.recyclerViewAdminListPesananBelumPesan);
        recyclerViewAdminListPesananBelumBayar = findViewById(R.id.recyclerViewAdminListPesananBelumBayar);
        recyclerViewAdminListPesananSudahBayar = findViewById(R.id.recyclerViewAdminListPesananSudahBayar);

        listPesananBelumPesan();
        listPesananBelumBayar();
        listPesananSudahBayar();
    }

    private void listPesananBelumPesan(){
        ApiService.getApiConfig().getAdminListPesananKasirBelumPesan("admin_list_pesanan_pelanggan_belum_pesan")
                .enqueue(new Callback<ArrayList<PesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PesananModel>> call, Response<ArrayList<PesananModel>> response) {
                        listPesananAdapter = new ListPesananAdapter(response.body(), new ListPesananAdapter.AdapterListenerListPesanan() {
                            @Override
                            public void onClick(PesananModel pesananModel) {
                                Intent in = new Intent(ListLaporanPenjualanAdminActivity.this, ProfilePesananActivity.class);
                                in.putExtra("id_pesanan", pesananModel.getId_pesanan());
                                in.putExtra("email", pesananModel.getEmail());
                                in.putExtra("keterangan", "Belum Pesan");
                                startActivity(in);
                            }
                        });

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListLaporanPenjualanAdminActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewAdminListPesananBelumPesan.setLayoutManager(layoutManager);
                        recyclerViewAdminListPesananBelumPesan.setAdapter(listPesananAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PesananModel>> call, Throwable t) {

                    }
                });
    }

    private void listPesananBelumBayar(){
        ApiService.getApiConfig().getAdminListPesananKasirBelumBayar("admin_list_pesanan_pelanggan_belum_bayar")
                .enqueue(new Callback<ArrayList<PesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PesananModel>> call, Response<ArrayList<PesananModel>> response) {
                        listPesananAdapter = new ListPesananAdapter(response.body(), new ListPesananAdapter.AdapterListenerListPesanan() {
                            @Override
                            public void onClick(PesananModel pesananModel) {
                                Intent in = new Intent(ListLaporanPenjualanAdminActivity.this, ProfilePesananActivity.class);
                                in.putExtra("id_pesanan", pesananModel.getId_pesanan());
                                in.putExtra("email", pesananModel.getEmail());
                                in.putExtra("keterangan", "Belum Bayar");
                                startActivity(in);
                            }
                        });

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListLaporanPenjualanAdminActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewAdminListPesananBelumBayar.setLayoutManager(layoutManager);
                        recyclerViewAdminListPesananBelumBayar.setAdapter(listPesananAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PesananModel>> call, Throwable t) {

                    }
                });
    }
    private void listPesananSudahBayar(){
        ApiService.getApiConfig().getAdminListPesananKasirSudahBayar("admin_list_pesanan_pelanggan_sudah_bayar")
                .enqueue(new Callback<ArrayList<PesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PesananModel>> call, Response<ArrayList<PesananModel>> response) {
                        listPesananAdapter = new ListPesananAdapter(response.body(), new ListPesananAdapter.AdapterListenerListPesanan() {
                            @Override
                            public void onClick(PesananModel pesananModel) {
                                Intent in = new Intent(ListLaporanPenjualanAdminActivity.this, ProfilePesananActivity.class);
                                in.putExtra("id_pesanan", pesananModel.getId_pesanan());
                                in.putExtra("email", pesananModel.getEmail());
                                in.putExtra("keterangan", "Sudah Bayar");
                                startActivity(in);
                            }
                        });

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListLaporanPenjualanAdminActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewAdminListPesananSudahBayar.setLayoutManager(layoutManager);
                        recyclerViewAdminListPesananSudahBayar.setAdapter(listPesananAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PesananModel>> call, Throwable t) {

                    }
                });
    }
}