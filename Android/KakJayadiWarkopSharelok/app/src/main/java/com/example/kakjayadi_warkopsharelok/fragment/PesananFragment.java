package com.example.kakjayadi_warkopsharelok.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.activity.LoginActivity;
import com.example.kakjayadi_warkopsharelok.activity.ProfilePesananActivity;
import com.example.kakjayadi_warkopsharelok.adapter.ListPesananAdapter;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananFragment extends Fragment {
    View view;
    TextView pesanan_login;
    LinearLayout pesananBelumLogin;
    NestedScrollView pesananSudahLogin;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String SHAREDPREFERENCES_CEK = null, SHAREDPREFERENCES_CEK_SEBAGAI=null, SHAREDPREFERENCES_CEK_NAMA=null;
    RecyclerView recyclerViewListPesananBelumBayar,recyclerViewListPesananSudahBayar;

    ListPesananAdapter listPesananAdapter;

    ConstraintLayout tambahPesananKasir;
    FloatingActionButton flAdd;

    static final String TAG = "PesananFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pesanan, container, false);

        tambahPesananKasir = view.findViewById(R.id.tambahPesananKasir);
        pesanan_login = view.findViewById(R.id.pesanan_login);
        pesananSudahLogin = view.findViewById(R.id.pesananSudahLogin);
        recyclerViewListPesananBelumBayar = view.findViewById(R.id.recyclerViewListPesananBelumBayar);
        recyclerViewListPesananSudahBayar = view.findViewById(R.id.recyclerViewListPesananSudahBayar);
        flAdd = view.findViewById(R.id.flAdd);
        pesanan_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        //cek login
        pesananBelumLogin = view.findViewById(R.id.pesananBelumLogin);
        sp = getContext().getSharedPreferences("SHAREDPREFERENCES_LOGIN", Context.MODE_PRIVATE);
        edit = sp.edit();
        SHAREDPREFERENCES_CEK = sp.getString("EMAIL", null);
        SHAREDPREFERENCES_CEK_SEBAGAI = sp.getString("SEBAGAI", null);
        SHAREDPREFERENCES_CEK_NAMA = sp.getString("NAMA", null);

        if(SHAREDPREFERENCES_CEK_SEBAGAI == null){
            pesananBelumLogin.setVisibility(View.VISIBLE);
            pesananSudahLogin.setVisibility(View.GONE);
            tambahPesananKasir.setVisibility(View.GONE);
        }
        else if(SHAREDPREFERENCES_CEK_SEBAGAI.equals("pelanggan")){
            pesananBelumLogin.setVisibility(View.GONE);
            tambahPesananKasir.setVisibility(View.GONE);
            pesananSudahLogin.setVisibility(View.VISIBLE);
            listPesananPelangganBelumBayar(SHAREDPREFERENCES_CEK);
            listPesananPelangganSudahBayar(SHAREDPREFERENCES_CEK);
        }

        else if(SHAREDPREFERENCES_CEK_SEBAGAI.equals("kasir")){
            pesananBelumLogin.setVisibility(View.GONE);
            pesananSudahLogin.setVisibility(View.VISIBLE);
            if(SHAREDPREFERENCES_CEK_NAMA !=null ){
                tambahPesananKasir.setVisibility(View.GONE);
            }
            else{
                tambahPesananKasir.setVisibility(View.VISIBLE);
            }
            listPesananKasirBelumBayar();
            listPesananKasirSudahBayar();
        }

        flAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = View.inflate(getContext(), R.layout.alert_dialog_pesanan_kasir, null);
                TextInputEditText et_nama_pesanan_kasir;
                Button buttonTambahPesanan,buttonBatal;

                et_nama_pesanan_kasir = v.findViewById(R.id.et_nama_pesanan_kasir);
                buttonTambahPesanan = v.findViewById(R.id.buttonTambahPesanan);
                buttonBatal = v.findViewById(R.id.buttonBatal);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(v);
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();

                sp = getContext().getSharedPreferences("SHAREDPREFERENCES_LOGIN", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();

                buttonTambahPesanan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nama = et_nama_pesanan_kasir.getText().toString().trim();
                        edit.putString("NAMA", nama);
                        edit.apply();
                        if(sp.getString("NAMA", null) != null){
                            tambahPesananKasir.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                    }
                });

                buttonBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        return view;
    }

    private void listPesananPelangganBelumBayar(String email){
        ApiService.getApiConfig().getListPesananPelanggan("listPesanan",email)
                .enqueue(new Callback<ArrayList<PesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PesananModel>> call, Response<ArrayList<PesananModel>> response) {
                        listPesananAdapter = new ListPesananAdapter(response.body(), new ListPesananAdapter.AdapterListenerListPesanan() {
                            @Override
                            public void onClick(PesananModel pesananModel) {
                                Intent in = new Intent(getContext(), ProfilePesananActivity.class);
                                in.putExtra("id_pesanan", pesananModel.getId_pesanan());
                                in.putExtra("email", email);
                                in.putExtra("keterangan", "Belum Bayar");
                                startActivity(in);
                            }
                        });


                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewListPesananBelumBayar.setLayoutManager(layoutManager);
                        recyclerViewListPesananBelumBayar.setAdapter(listPesananAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PesananModel>> call, Throwable t) {

                    }
                });
    }
    private void listPesananPelangganSudahBayar(String email){
        ApiService.getApiConfig().getListPesananPelangganSudahBayar("list_pesanan_pelanggan_sudah_bayar",email)
                .enqueue(new Callback<ArrayList<PesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PesananModel>> call, Response<ArrayList<PesananModel>> response) {
                        listPesananAdapter = new ListPesananAdapter(response.body(), new ListPesananAdapter.AdapterListenerListPesanan() {
                            @Override
                            public void onClick(PesananModel pesananModel) {
                                Intent in = new Intent(getContext(), ProfilePesananActivity.class);
                                in.putExtra("id_pesanan", pesananModel.getId_pesanan());
                                in.putExtra("email", email);
                                in.putExtra("keterangan", "Sudah Bayar");
                                in.putExtra("nama", pesananModel.getNama());
                                startActivity(in);
                            }
                        });


                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewListPesananSudahBayar.setLayoutManager(layoutManager);
                        recyclerViewListPesananSudahBayar.setAdapter(listPesananAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PesananModel>> call, Throwable t) {

                    }
                });
    }

    private void listPesananKasirBelumBayar(){
        ApiService.getApiConfig().getListPesananKasir("list_pesanan_pelanggan")
                .enqueue(new Callback<ArrayList<PesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PesananModel>> call, Response<ArrayList<PesananModel>> response) {
                        listPesananAdapter = new ListPesananAdapter(response.body(), new ListPesananAdapter.AdapterListenerListPesanan() {
                            @Override
                            public void onClick(PesananModel pesananModel) {
                                Intent in = new Intent(getContext(), ProfilePesananActivity.class);
                                in.putExtra("id_pesanan", pesananModel.getId_pesanan());
                                in.putExtra("email", pesananModel.getEmail());
                                in.putExtra("keterangan", "Belum Bayar");
                                startActivity(in);
                            }
                        });

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewListPesananBelumBayar.setLayoutManager(layoutManager);
                        recyclerViewListPesananBelumBayar.setAdapter(listPesananAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PesananModel>> call, Throwable t) {

                    }
                });
    }
    private void listPesananKasirSudahBayar(){
        ApiService.getApiConfig().getListPesananKasirSudahBayar("list_pesanan_pelanggan_kasir_sudah_bayar")
                .enqueue(new Callback<ArrayList<PesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PesananModel>> call, Response<ArrayList<PesananModel>> response) {
                        Toast.makeText(getContext(), "berhasil", Toast.LENGTH_SHORT).show();
                        listPesananAdapter = new ListPesananAdapter(response.body(), new ListPesananAdapter.AdapterListenerListPesanan() {
                            @Override
                            public void onClick(PesananModel pesananModel) {
                                Intent in = new Intent(getContext(), ProfilePesananActivity.class);
                                in.putExtra("id_pesanan", pesananModel.getId_pesanan());
                                in.putExtra("email", pesananModel.getEmail());
                                in.putExtra("keterangan", "Sudah Bayar");
                                in.putExtra("nama", pesananModel.getNama());
                                startActivity(in);
                                Toast.makeText(getContext(), ""+pesananModel.getId_pesanan(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewListPesananSudahBayar.setLayoutManager(layoutManager);
                        recyclerViewListPesananSudahBayar.setAdapter(listPesananAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PesananModel>> call, Throwable t) {

                    }
                });
    }

}