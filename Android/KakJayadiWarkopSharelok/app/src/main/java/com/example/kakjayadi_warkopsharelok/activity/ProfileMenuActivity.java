package com.example.kakjayadi_warkopsharelok.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.AngkaRibuan;
import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;
import com.example.kakjayadi_warkopsharelok.data.model.PostUserModel;
import com.example.kakjayadi_warkopsharelok.activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Import menjadi rupiah


public class ProfileMenuActivity extends Activity {

    final static String TAG = "ProfileActivity";

    AngkaRibuan obj;
    ImageView iv_gambar_profile_menu;
    TextView tv_nama_menu_profile_menu,
            tv_kategori_menu_profile_menu,
            tv_jenis_menu_profile_menu,
            tv_harga_menu_profile_menu;
    String idMenu, gambarMenu, namaMenu, kategoriMenu, jenisMenu, hargaMenu;
    ImageView btn_back_profile;

    //Cek login
    SharedPreferences sp;
    String SHAREDPREFERENCES_CEK;
    String SHAREDPREFERENCES_CEK_NAMA;

    //Tambah Pesanan
    LinearLayout ly_hitung_pesanan_pelanggan_profile;
    Button btn_kurang_pesanan, btn_tambah_pesanan, btn_pesan_menu_profile_menu;
    TextView tv_jumlah_pesanan_profile;
    int jumlahPesanan;

    //cek update
    boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile_menu);

        iv_gambar_profile_menu = findViewById(R.id.iv_gambar_profile_menu);
        tv_nama_menu_profile_menu = findViewById(R.id.tv_nama_menu_profile_menu);
        tv_kategori_menu_profile_menu = findViewById(R.id.tv_kategori_menu_profile_menu);
        tv_jenis_menu_profile_menu = findViewById(R.id.tv_jenis_menu_profile_menu);
        tv_harga_menu_profile_menu = findViewById(R.id.tv_harga_menu_profile_menu);
        btn_back_profile = findViewById(R.id.btn_back_profile);

        Bundle bundle = getIntent().getExtras();
        idMenu = bundle.getString("id_menu");
        gambarMenu = bundle.getString("gambar").toString().trim();
        namaMenu = bundle.getString("nama_menu");
        kategoriMenu = bundle.getString("kategori");
        jenisMenu = bundle.getString("jenis_menu");
        hargaMenu = bundle.getString("harga");

        obj = new AngkaRibuan();
        obj.setAngkaRibuan(hargaMenu.length(), hargaMenu);

        tv_nama_menu_profile_menu.setText(namaMenu);
        tv_kategori_menu_profile_menu.setText(kategoriMenu);
        tv_jenis_menu_profile_menu.setText(jenisMenu);
        tv_harga_menu_profile_menu.setText(obj.getAngkaRibuan());

        //Gambar lewat url
        Picasso.get()
                .load(ApiService.URL+"/warkop_sharelock/image/menu/"+gambarMenu)
                .error(R.drawable.no_image)
                .into(iv_gambar_profile_menu);

        //Button Back
        btn_back_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Tambah Pesanan
        ly_hitung_pesanan_pelanggan_profile = findViewById(R.id.ly_hitung_pesanan_pelanggan_profile);
        btn_kurang_pesanan = findViewById(R.id.btn_kurang_pesanan);
        btn_tambah_pesanan = findViewById(R.id.btn_tambah_pesanan);
        tv_jumlah_pesanan_profile = findViewById(R.id.tv_jumlah_pesanan_profile);
        btn_pesan_menu_profile_menu = findViewById(R.id.btn_pesan_menu_profile_menu);

        //Cek Login
        sp = getSharedPreferences("SHAREDPREFERENCES_LOGIN", Context.MODE_PRIVATE);
        SHAREDPREFERENCES_CEK = sp.getString("EMAIL", null);
        SHAREDPREFERENCES_CEK_NAMA = sp.getString("NAMA", null);
        if(SHAREDPREFERENCES_CEK == null){
            ly_hitung_pesanan_pelanggan_profile.setVisibility(View.GONE);
        }
        else if(SHAREDPREFERENCES_CEK_NAMA == null){
            ly_hitung_pesanan_pelanggan_profile.setVisibility(View.GONE);
        }
        else{
            ly_hitung_pesanan_pelanggan_profile.setVisibility(View.VISIBLE);
            jumlahPesanan(SHAREDPREFERENCES_CEK, idMenu);
        }

        btn_kurang_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlahPesanan = Integer.parseInt(tv_jumlah_pesanan_profile.getText().toString().trim());
                if(jumlahPesanan != 0){
                    jumlahPesanan--;
                    tv_jumlah_pesanan_profile.setText(String.valueOf(jumlahPesanan));
                    String jumlah = String.valueOf(Integer.valueOf(hargaMenu)*jumlahPesanan);
                    obj.setAngkaRibuan(jumlah.length(), jumlah);
                    btn_pesan_menu_profile_menu.setText("Pesan - "+obj.getAngkaRibuan());
                }
                else{
                    //Minimal pesan
//                    Toast.makeText(ProfileMenuActivity.this, "Min", Toast.LENGTH_SHORT).show();
                }
                if (jumlahPesanan == 0){
                    if(update == true){
                        //update
//                        Toast.makeText(ProfileMenuActivity.this, "update", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        btn_pesan_menu_profile_menu.setVisibility(View.GONE);
                    }
                }
            }
        });

        btn_tambah_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlahPesanan = Integer.parseInt(tv_jumlah_pesanan_profile.getText().toString().trim());
                if(jumlahPesanan<30){
                    jumlahPesanan++;
                    tv_jumlah_pesanan_profile.setText(String.valueOf(jumlahPesanan));
                    String jumlah = String.valueOf(Integer.valueOf(hargaMenu)*jumlahPesanan);
                    obj.setAngkaRibuan(jumlah.length(), jumlah);
                    btn_pesan_menu_profile_menu.setText("Pesan - "+obj.getAngkaRibuan());
                }
                else{
                    //Maksimak PPesan 30
                    Toast.makeText(ProfileMenuActivity.this, "Maks", Toast.LENGTH_SHORT).show();
                }
                if(jumlahPesanan>0){
                    btn_pesan_menu_profile_menu.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_pesan_menu_profile_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String total;
                total = String.valueOf(Integer.valueOf(hargaMenu)*Integer.valueOf(tv_jumlah_pesanan_profile.getText().toString().trim()));
                Log.d("ProfileMenuActivity2", "onClick: "+total);
                if(update == true && Integer.valueOf(tv_jumlah_pesanan_profile.getText().toString())>1 ){
                    //update
                    updatePesanan(tv_jumlah_pesanan_profile.getText().toString().trim(), total, idMenu, SHAREDPREFERENCES_CEK);

                }
                else if(update == true && tv_jumlah_pesanan_profile.getText().equals("0")){
                    Toast.makeText(ProfileMenuActivity.this, "hapus "+SHAREDPREFERENCES_CEK+" dan "+idMenu, Toast.LENGTH_SHORT).show();
                    deletePesanan(SHAREDPREFERENCES_CEK, idMenu);
                }
                else{
                    postDataPesanan(SHAREDPREFERENCES_CEK,SHAREDPREFERENCES_CEK_NAMA,idMenu,namaMenu,kategoriMenu, jenisMenu,hargaMenu,tv_jumlah_pesanan_profile.getText().toString(),total,"","belum pesan");
                }
            }
        });
    }

    private void jumlahPesanan(String email, String idMenu){
        ApiService.getApiConfig().getJumlahPesananMenu("jumlah_menu",email,idMenu)
                .enqueue(new Callback<ArrayList<PesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PesananModel>> call, Response<ArrayList<PesananModel>> response) {
//                        Toast.makeText(ProfileMenuActivity.this, ""+response.body().get(0).getJumlah(), Toast.LENGTH_SHORT).show();

                        tv_jumlah_pesanan_profile.setText(response.body().get(0).getJumlah());
                        if(Integer.valueOf(response.body().get(0).getJumlah().toString().trim())>0){
                            String jumlah = String.valueOf(Integer.valueOf(hargaMenu)*Integer.valueOf(response.body().get(0).getJumlah()));
                            obj.setAngkaRibuan(jumlah.length(), jumlah);
                            btn_pesan_menu_profile_menu.setText("Pesan - "+obj.getAngkaRibuan());

                            btn_pesan_menu_profile_menu.setVisibility(View.VISIBLE);
                            update = true;
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PesananModel>> call, Throwable t) {

                    }
                });
    }

    private void postDataPesanan(String email, String nama, String id_menu, String menu, String kategori, String jenis_menu, String harga, String jumlah, String total, String kasir, String keterangan){
        ApiService.getApiConfig().postPesanan("postPesanan",email,nama,id_menu,menu,kategori,jenis_menu,harga,jumlah,total,kasir,keterangan)
                .enqueue(new Callback<PesananModel>() {
                    @Override
                    public void onResponse(Call<PesananModel> call, Response<PesananModel> response) {
                        Log.d(TAG, "onResponse: "+response.body());
                        Toast.makeText(ProfileMenuActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<PesananModel> call, Throwable t) {
                        Toast.makeText(ProfileMenuActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void updatePesanan(String jumlah, String total, String id_pesanan, String email){
        ApiService.getApiConfig().updatePesanan("updatePesanan", jumlah, total, id_pesanan, email)
                .enqueue(new Callback<PesananModel>() {
                    @Override
                    public void onResponse(Call<PesananModel> call, Response<PesananModel> response) {

                    }

                    @Override
                    public void onFailure(Call<PesananModel> call, Throwable t) {
                        Toast.makeText(ProfileMenuActivity.this, "Berhasil update", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void deletePesanan(String email, String idMenu){
        ApiService.getApiConfig().deletePesanan("deletePesanan", email, idMenu)
                .enqueue(new Callback<PesananModel>() {
                    @Override
                    public void onResponse(Call<PesananModel> call, Response<PesananModel> response) {
                        Toast.makeText(ProfileMenuActivity.this, "Berhasil update", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<PesananModel> call, Throwable t) {
                        Toast.makeText(ProfileMenuActivity.this, "Berhasil update", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}