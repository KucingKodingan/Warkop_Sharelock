package com.example.kakjayadi_warkopsharelok.fragment;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.AngkaRibuan;
import com.example.kakjayadi_warkopsharelok.CekKoneksi;
import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.activity.RekomendasiActivity;
import com.example.kakjayadi_warkopsharelok.activity.ProfileMenuActivity;
import com.example.kakjayadi_warkopsharelok.adapter.DialogPesananAdapter;
import com.example.kakjayadi_warkopsharelok.adapter.MenuAdapter;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.MenuModel;
import com.example.kakjayadi_warkopsharelok.data.model.MenuRekomendasiModel;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;
import com.example.kakjayadi_warkopsharelok.util.LoadingAlertDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    //cek admin or pesanan

    MenuAdapter menuAdapter;
    RecyclerView rv_home_menu, recyclerViewMenuAdmin;
    View view;
    CardView btnRekomendasi;
    LinearLayout LyNoConnectionHome;
    TextView tvMuatUlangHome;

    static final String TAG = "HomeFragment";

    //AlertDialog Rekomendasi Menu
    AutoCompleteTextView autoCompleteTextViewKategori, autoCompleteTextViewJenisMenu,
            autoCompleteTextViewBatasHargaAwal, autoCompleteTextViewBatasHargaAkhir;
    String[] array_jenis_menu, array_kategori, arrayHarga;
    String hargaAwal = "5000";
    String hargaAkhir = "13000";

    //Cek login
    SharedPreferences sp;
    String SHAREDPREFERENCES_CEK=null,SHAREDPREFERENCES_CEK_NAMA=null,SHAREDPREFERENCES_CEK_SEBAGAI =null;

    //Pesanan
    CardView btn_pesanan_menu_home;
    TextView tv_jumlah_pesanan;
    String email=null;
    int jumlah_pesanan=0, harga_total_pesanan=0;

    //Loading
    LoadingAlertDialog loading = new LoadingAlertDialog();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        sp = getContext().getSharedPreferences("SHAREDPREFERENCES_LOGIN", Context.MODE_PRIVATE);
        SHAREDPREFERENCES_CEK = sp.getString("EMAIL", null);
        SHAREDPREFERENCES_CEK_NAMA = sp.getString("NAMA", null);
        SHAREDPREFERENCES_CEK_SEBAGAI = sp.getString("SEBAGAI", null);

        //deklaras
        rv_home_menu = view.findViewById(R.id.rv_home_menu);
        btnRekomendasi = view.findViewById(R.id.btnRekomendasi);
        LyNoConnectionHome = view.findViewById(R.id.LyNoConnectionHome);
        tvMuatUlangHome = view.findViewById(R.id.tvMuatUlangHome);
        recyclerViewMenuAdmin = view.findViewById(R.id.recyclerViewMenuAdmin);

        btnRekomendasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Rekomendasi Menu
                rekomendasiMenu();
//                startActivity(new Intent(getContext(), RekomendasiActivity.class));
            }
        });

        cekKoneksi();

        //Muat Ulang cek koneksi
        tvMuatUlangHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekKoneksi();
            }
        });

        //Button Profile Menu
//        ambilDataMenu();

        //Pesanan
        btn_pesanan_menu_home = view.findViewById(R.id.btn_pesanan_menu_home);
        tv_jumlah_pesanan = view.findViewById(R.id.tv_jumlah_pesanan);
//        tv_total_harga_pesanan = view.findViewById(R.id.tv_total_harga_pesanan);


        if(SHAREDPREFERENCES_CEK != null){
            email = SHAREDPREFERENCES_CEK.trim();
            alertDialogPesanan();
        }
        else{
            btn_pesanan_menu_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Belum Login", Toast.LENGTH_LONG).show();
                }
            });
        }

        return view;
    }

    private void cekKoneksi(){
        //Cek Koneksi
        if(CekKoneksi.isInternetAvailable(getContext())){
            Log.d(TAG, "cekKoneksi: Berhasil");
            LyNoConnectionHome.setVisibility(View.GONE);
//            if(SHAREDPREFERENCES_CEK_SEBAGAI != null){
//                if(SHAREDPREFERENCES_CEK_SEBAGAI.equals("admin")){
//                    Toast.makeText(getContext(), ""+SHAREDPREFERENCES_CEK_SEBAGAI, Toast.LENGTH_SHORT).show();
//                    menu.setVisibility(View.GONE);
//                    menuAdmin.setVisibility(View.VISIBLE);
//                    menuAdmin();
//                }
//                else{
//                    menu.setVisibility(View.VISIBLE);
//                    menuAdmin.setVisibility(View.GONE);
//                    ambilDataMenu();
//                }
//            }
//            else{
                ambilDataMenu();
//            }
        }
        else{
            Log.d(TAG, "cekKoneksi: Gagal");
            LyNoConnectionHome.setVisibility(View.VISIBLE);
        }
    }

    private void ambilDataMenu(){
        ApiService.getApiConfig().getDataMenu().enqueue(new Callback<ArrayList<MenuModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MenuModel>> call, Response<ArrayList<MenuModel>> response) {
                menuAdapter = new MenuAdapter(response.body(), new MenuAdapter.AdapterListener() {
                    @Override
                    public void onClick(MenuModel menuModel) {
                        String kapasitas = menuModel.getKapasitas();
                        String nama_menu = menuModel.getNama_menu();

                        if(kapasitas.equals("ada")){
                            Intent intent = new Intent(getContext(), ProfileMenuActivity.class);

                            intent.putExtra("id_menu", menuModel.getIdMenu());
                            intent.putExtra("nama_menu", menuModel.getNama_menu());
                            intent.putExtra("kategori", menuModel.getKategori());
                            intent.putExtra("jenis_menu", menuModel.getJenis_menu());
                            intent.putExtra("harga", menuModel.getHarga());
                            intent.putExtra("gambar", menuModel.getGambar());
                            intent.putExtra("kapasitas", menuModel.getKapasitas());
                            startActivity(intent);
                        }
                        else if(kapasitas.equals("habis")){
                            Toast.makeText(getContext(), nama_menu+" Habis", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                RecyclerView.LayoutManager layoutManager =
                        new GridLayoutManager(getContext(), 2);
                rv_home_menu.setLayoutManager(layoutManager);
                rv_home_menu.setAdapter(menuAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<MenuModel>> call, Throwable t) {
                Log.d("HomeFragment", "onFailure: "+t.getMessage());
            }
        });
    }

    private void rekomendasiMenu(){
        View viewRekomendasi = View.inflate(getContext(), R.layout.alert_dialog_rekomendasi_menu, null);

        //Jenis Menu
        array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_minuman);

        autoCompleteTextViewJenisMenu = viewRekomendasi.findViewById(R.id.autoCompleteTextViewJenisMenu);
        autoCompleteTextViewJenisMenu.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(getContext(), R.layout.item_dropdown, array_jenis_menu);
        autoCompleteTextViewJenisMenu.setAdapter(arrayAdapterJenisMenu);
//        autoCompleteTextViewJenisMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "nilai: "+autoCompleteTextViewJenisMenu.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //Kategori
        array_kategori = getResources().getStringArray(R.array.kategori);

        autoCompleteTextViewKategori = viewRekomendasi.findViewById(R.id.autoCompleteTextViewKategori);
        autoCompleteTextViewKategori.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.item_dropdown, array_kategori);

        autoCompleteTextViewKategori.setAdapter(arrayAdapter);
        autoCompleteTextViewKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kategori = autoCompleteTextViewKategori.getText().toString().trim();

                if(kategori.equals("Makanan")){
                    autoCompleteTextViewJenisMenu.setText("Makanan Berat");
                    array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_makanan);
                    ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(getContext(), R.layout.item_dropdown, array_jenis_menu);
                    autoCompleteTextViewJenisMenu.setAdapter(arrayAdapterJenisMenu);
                }
                else if(kategori.equals("Minuman Ice")||kategori.equals("Minuman Hot")){
                    autoCompleteTextViewJenisMenu.setText("Coffee");
                    array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_minuman);
                    ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(getContext(), R.layout.item_dropdown, array_jenis_menu);
                    autoCompleteTextViewJenisMenu.setAdapter(arrayAdapterJenisMenu);
                }
            }
        });

        //Batas Harga
        autoCompleteTextViewBatasHargaAwal = viewRekomendasi.findViewById(R.id.autoCompleteTextViewBatasHargaAwal);
        autoCompleteTextViewBatasHargaAkhir = viewRekomendasi.findViewById(R.id.autoCompleteTextViewBatasHargaAkhir);

        arrayHarga = getResources().getStringArray(R.array.harga);
        for(int i=0; i<arrayHarga.length; i++){
            AngkaRibuan angkaRibuan = new AngkaRibuan();
            int panjangAngka = arrayHarga[i].length();
            angkaRibuan.setAngkaRibuan(panjangAngka, arrayHarga[i].toString());
            arrayHarga[i] = angkaRibuan.getAngkaRibuan();
        }

        autoCompleteTextViewBatasHargaAwal.setInputType(View.AUTOFILL_TYPE_NONE);
        ArrayAdapter arrayAdapterHargaAwal = new ArrayAdapter(getContext(), R.layout.item_dropdown, arrayHarga);
        autoCompleteTextViewBatasHargaAwal.setAdapter(arrayAdapterHargaAwal);

        autoCompleteTextViewBatasHargaAkhir.setInputType(View.AUTOFILL_TYPE_NONE);
        ArrayAdapter arrayAdapterHargaAkhir = new ArrayAdapter(getContext(), R.layout.item_dropdown, arrayHarga);
        autoCompleteTextViewBatasHargaAkhir.setAdapter(arrayAdapterHargaAkhir);

        //Mengambil nilai awal dan akhir
        String[] arrayHargaOriginal = getResources().getStringArray(R.array.harga);

        autoCompleteTextViewBatasHargaAwal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hargaAwal = arrayHargaOriginal[position].toString();
            }
        });
        autoCompleteTextViewBatasHargaAkhir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hargaAkhir = arrayHargaOriginal[position].toString();
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(viewRekomendasi);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();

        Button buttonCari = viewRekomendasi.findViewById(R.id.buttonCari);
        Button buttonBatal = viewRekomendasi.findViewById(R.id.buttonBatal);
        buttonCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(hargaAwal)<=Integer.parseInt(hargaAkhir)){
                    Intent intent = new Intent(getContext(), RekomendasiActivity.class);
                    intent.putExtra("kategori", autoCompleteTextViewKategori.getText().toString());
                    intent.putExtra("jenisMenu", autoCompleteTextViewJenisMenu.getText().toString());
                    intent.putExtra("hargaAwal", hargaAwal);
                    intent.putExtra("hargaAkhir", hargaAkhir);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), "Harga Awal Lebih Kecil dari Harga Akhir", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    AlertDialog dialog;

    public void alertDialogPesanan(){
        ArrayList idPesananUpdate = new ArrayList();

        ApiService.getApiConfig().getMemesan(email, "belum pesan")
                .enqueue(new Callback<ArrayList<PesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PesananModel>> call, Response<ArrayList<PesananModel>> response) {
                        if(response.body().size() > 0){
                            Log.d(TAG, "onResponse: "+response.body());
//                            btn_pesanan_menu_home.setVisibility(View.VISIBLE);

                            for(int i=0; i<response.body().size();i++){
                                idPesananUpdate.add(response.body().get(i).getId_pesanan().trim());
                                jumlah_pesanan += Integer.parseInt(response.body().get(i).getJumlah().trim());
                                harga_total_pesanan += Integer.parseInt(response.body().get(i).getTotal().trim());
                            }

                            tv_jumlah_pesanan.setText(String.valueOf(jumlah_pesanan));
                            AngkaRibuan obj = new AngkaRibuan();
                            int panjang = String.valueOf(harga_total_pesanan).length();
                            obj.setAngkaRibuan(panjang, String.valueOf(harga_total_pesanan));
//                            tv_total_harga_pesanan.setText(obj.getAngkaRibuan());

                            btn_pesanan_menu_home.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //ALERT DIALOG PESANAN
                                    View viewPesananDialog = View.inflate(getContext(), R.layout.alert_dialog_pesanan_menu, null);
                                    RecyclerView rv_alert_dialog_pesanan;
                                    Button buttonPesan, buttonBatalPesanan;

                                    rv_alert_dialog_pesanan = viewPesananDialog.findViewById(R.id.rv_alert_dialog_pesanan);
                                    buttonPesan = viewPesananDialog.findViewById(R.id.buttonPesan);
                                    buttonBatalPesanan = viewPesananDialog.findViewById(R.id.buttonBatalPesanan);

                                    DialogPesananAdapter dialogPesananAdapter = new DialogPesananAdapter(response.body());

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                    rv_alert_dialog_pesanan.setLayoutManager(layoutManager);
                                    rv_alert_dialog_pesanan.setAdapter(dialogPesananAdapter);

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                    alertDialogBuilder.setView(viewPesananDialog);
                                    dialog = alertDialogBuilder.create();
                                    dialog.show();

                                    buttonPesan.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            loading.alertDialogLoading(getContext());
                                            memesan("",SHAREDPREFERENCES_CEK);

                                            if(SHAREDPREFERENCES_CEK_SEBAGAI.equals("kasir")){
                                                Toast.makeText(getContext(), ""+SHAREDPREFERENCES_CEK_SEBAGAI+" dan "+SHAREDPREFERENCES_CEK_NAMA, Toast.LENGTH_SHORT).show();
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.putString("NAMA", null);
                                                editor.apply();
                                            }
                                        }
                                    });

                                    buttonBatalPesanan.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<PesananModel>> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage().toString());
                    }
                });
    }

    private void memesan(String id_pesanan, String email){
        ApiService.getApiConfig().memesan("memesan",id_pesanan,email)
                .enqueue(new Callback<PesananModel>() {
                    @Override
                    public void onResponse(Call<PesananModel> call, Response<PesananModel> response) {
                        Toast.makeText(getContext(), "Berhasil memesan", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loading.alertDialogCancel();
                    }

                    @Override
                    public void onFailure(Call<PesananModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Berhasil memesan", Toast.LENGTH_SHORT).show();
                        loading.alertDialogCancel();
                        dialog.dismiss();
                    }
                });
    }


    //Jenis Menu

    //Minuman ICE
    private void minumanIce(){
        ApiService.getApiConfig().getJenisMenuMinumanIce("minuman_ice")
                .enqueue(new Callback<ArrayList<MenuRekomendasiModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MenuRekomendasiModel>> call, Response<ArrayList<MenuRekomendasiModel>> response) {

                    }

                    @Override
                    public void onFailure(Call<ArrayList<MenuRekomendasiModel>> call, Throwable t) {

                    }
                });
    }

    //Minuman HOT
    private void minumanHot(){
        ApiService.getApiConfig().getJenisMenuMinumanHot("minuman_hot")
                .enqueue(new Callback<ArrayList<MenuRekomendasiModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MenuRekomendasiModel>> call, Response<ArrayList<MenuRekomendasiModel>> response) {

                    }

                    @Override
                    public void onFailure(Call<ArrayList<MenuRekomendasiModel>> call, Throwable t) {

                    }
                });
    }

    //Makanan
    private void Makanan(){
        ApiService.getApiConfig().getJenisMenuMakanan("Makanan")
                .enqueue(new Callback<ArrayList<MenuRekomendasiModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MenuRekomendasiModel>> call, Response<ArrayList<MenuRekomendasiModel>> response) {

                    }

                    @Override
                    public void onFailure(Call<ArrayList<MenuRekomendasiModel>> call, Throwable t) {

                    }
                });
    }

}