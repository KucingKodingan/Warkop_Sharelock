package com.example.kakjayadi_warkopsharelok.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.AngkaRibuan;
import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.adapter.MenuAdapter;
import com.example.kakjayadi_warkopsharelok.adapter.MenuRekomendasiAdapter;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.MenuModel;
import com.example.kakjayadi_warkopsharelok.data.model.MenuRekomendasiModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekomendasiActivity extends Activity {

    AutoCompleteTextView autoCompleteTextViewKategori, autoCompleteTextViewJenisMenu,
            autoCompleteTextViewBatasHargaAwal, autoCompleteTextViewBatasHargaAkhir;
    String[] array_jenis_menu, array_kategori, arrayHarga;
    String kategori, jenisMenu, hargaAwal, hargaAkhir;
    String TAG = "RekomendasiActivity";
    MenuRekomendasiAdapter menuRekomendasiAdapter;
    RecyclerView recyclerViewRekomendasiMenu;
    ImageView btn_back_rekomendasi_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rekomendasi);

        recyclerViewRekomendasiMenu = findViewById(R.id.recyclerViewRekomendasiMenu);
        btn_back_rekomendasi_menu = findViewById(R.id.btn_back_rekomendasi_menu);

        btn_back_rekomendasi_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        kategori = bundle.getString("kategori").trim();
        jenisMenu = bundle.getString("jenisMenu").trim();
        hargaAwal = bundle.getString("hargaAwal").trim();
        hargaAkhir = bundle.getString("hargaAkhir").trim();

        Log.d("RekomendasiActivity", "onCreate: "+kategori+" dan "+jenisMenu+" dan "+hargaAwal+" dan "+hargaAkhir);

//        getDataRekomendasi();
        ambilDataMenuRekomendasi();

    }

    private void getDataRekomendasi(){
//        ApiService.getApiConfig().getMenuRekomendasi("Minuman Ice", "Non Coffee", "5000", "13000")
        ApiService.getApiConfig().getMenuRekomendasi(kategori, jenisMenu, hargaAwal, hargaAkhir)
                .enqueue(new Callback<ArrayList<MenuRekomendasiModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MenuRekomendasiModel>> call, Response<ArrayList<MenuRekomendasiModel>> response) {
                        try{
                            Log.d(TAG, "onResponse: " + response.body());
                            Log.d(TAG, "onResponse: " + response.body().get(0).getKapasitas());
                        }
                        catch (Exception ex){
                            Log.d(TAG, "onResponse: "+ex);
                            Toast.makeText(RekomendasiActivity.this, "Error: "+ex, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<MenuRekomendasiModel>> call, Throwable t) {
                        Log.d(TAG, "onFailure: ");
                    }
                });
    }

    private void ambilDataMenuRekomendasi() {
        ApiService.getApiConfig().getMenuRekomendasi(kategori, jenisMenu, hargaAwal, hargaAkhir)
                .enqueue(new Callback<ArrayList<MenuRekomendasiModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MenuRekomendasiModel>> call, Response<ArrayList<MenuRekomendasiModel>> response) {

                        Log.d(TAG, "onResponse: "+response.body());

                        menuRekomendasiAdapter = new MenuRekomendasiAdapter(response.body(), new MenuRekomendasiAdapter.AdapterListener() {
                            @Override
                            public void onClickRekomendasi(MenuRekomendasiModel menuModel) {
                                String kapasitas = menuModel.getKapasitas();
                                String nama_menu = menuModel.getNama_menu();

                                if(kapasitas.equals("ada")){
                                    Intent intent = new Intent(RekomendasiActivity.this, ProfileMenuActivity.class);

                                    intent.putExtra("nama_menu", menuModel.getNama_menu());
                                    intent.putExtra("kategori", menuModel.getKategori());
                                    intent.putExtra("jenis_menu", menuModel.getJenis_menu());
                                    intent.putExtra("harga", menuModel.getHarga());
                                    intent.putExtra("gambar", menuModel.getGambar());
                                    intent.putExtra("kapasitas", menuModel.getKapasitas());
                                    startActivity(intent);
                                }
                                else if(kapasitas.equals("habis")){
                                    Toast.makeText(RekomendasiActivity.this, nama_menu+" Habis", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        RecyclerView.LayoutManager layoutManager =
                                new GridLayoutManager(RekomendasiActivity.this, 2);
                        recyclerViewRekomendasiMenu.setLayoutManager(layoutManager);
                        recyclerViewRekomendasiMenu.setAdapter(menuRekomendasiAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<MenuRekomendasiModel>> call, Throwable t) {

                    }
                });
    }

    private void getData(){
        //Jenis Menu
        array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_minuman);

        autoCompleteTextViewJenisMenu = findViewById(R.id.autoCompleteTextViewJenisMenu);
        autoCompleteTextViewJenisMenu.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(this, R.layout.item_dropdown, array_jenis_menu);
        autoCompleteTextViewJenisMenu.setAdapter(arrayAdapterJenisMenu);
        autoCompleteTextViewJenisMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(RekomendasiActivity.this, "nilai: "+autoCompleteTextViewJenisMenu.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //Kategori
        array_kategori = getResources().getStringArray(R.array.kategori);

        autoCompleteTextViewKategori = findViewById(R.id.autoCompleteTextViewKategori);
        autoCompleteTextViewKategori.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_dropdown, array_kategori);

        autoCompleteTextViewKategori.setAdapter(arrayAdapter);
        autoCompleteTextViewKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kategori = autoCompleteTextViewKategori.getText().toString().trim();
                Toast.makeText(RekomendasiActivity.this, "nilai: "+autoCompleteTextViewKategori.getText().toString().trim(), Toast.LENGTH_SHORT).show();

                if(kategori.equals("Makanan")){
                    Toast.makeText(RekomendasiActivity.this, "Makanan: "+autoCompleteTextViewKategori.getText().toString(), Toast.LENGTH_SHORT).show();

                    autoCompleteTextViewJenisMenu.setText("Makanan Berat");
                    array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_makanan);
                    ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(getApplicationContext(), R.layout.item_dropdown, array_jenis_menu);
                    autoCompleteTextViewJenisMenu.setAdapter(arrayAdapterJenisMenu);
                }
                else if(kategori.equals("Minuman Ice")||kategori.equals("Minuman Hot")){
                    autoCompleteTextViewJenisMenu.setText("Coffee");
                    array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_minuman);
                    ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(getApplicationContext(), R.layout.item_dropdown, array_jenis_menu);
                    autoCompleteTextViewJenisMenu.setAdapter(arrayAdapterJenisMenu);
                }
            }
        });

        //Batas Harga
        autoCompleteTextViewBatasHargaAwal = findViewById(R.id.autoCompleteTextViewBatasHargaAwal);
        autoCompleteTextViewBatasHargaAkhir = findViewById(R.id.autoCompleteTextViewBatasHargaAkhir);

        arrayHarga = getResources().getStringArray(R.array.harga);
        for(int i=0; i<arrayHarga.length; i++){
            AngkaRibuan angkaRibuan = new AngkaRibuan();
            int panjangAngka = arrayHarga[i].length();
            angkaRibuan.setAngkaRibuan(panjangAngka, arrayHarga[i].toString());
            arrayHarga[i] = angkaRibuan.getAngkaRibuan();
        }
        autoCompleteTextViewBatasHargaAwal.setInputType(View.AUTOFILL_TYPE_NONE);
        ArrayAdapter arrayAdapterHargaAwal = new ArrayAdapter(this, R.layout.item_dropdown, arrayHarga);
        autoCompleteTextViewBatasHargaAwal.setAdapter(arrayAdapterHargaAwal);

        autoCompleteTextViewBatasHargaAkhir.setInputType(View.AUTOFILL_TYPE_NONE);
        ArrayAdapter arrayAdapterHargaAkhir = new ArrayAdapter(this, R.layout.item_dropdown, arrayHarga);
        autoCompleteTextViewBatasHargaAkhir.setAdapter(arrayAdapterHargaAkhir);
    }
}