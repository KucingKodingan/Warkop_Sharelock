package com.example.kakjayadi_warkopsharelok.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.AngkaRibuan;
import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.activity.MainActivity;
import com.example.kakjayadi_warkopsharelok.activity.ProfilePesananActivity;
import com.example.kakjayadi_warkopsharelok.activity.RekomendasiActivity;
import com.example.kakjayadi_warkopsharelok.adapter.ListPesananAdapter;
import com.example.kakjayadi_warkopsharelok.adapter.MenuAdminAdapter;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.MenuModel;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;
import com.example.kakjayadi_warkopsharelok.util.LoadingAlertDialog;
import com.example.kakjayadi_warkopsharelok.util.RealPathUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMenuAdminActivity extends Activity {

    FloatingActionButton flAddMenuAdmin;
    MenuAdminAdapter menuAdminAdapter;
    RecyclerView recyclerViewMenuAdmin;

    String path;

    final static String TAG = "ListMenuAdminActivity";

    LoadingAlertDialog loading = new LoadingAlertDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        //Tambah Menu
        flAddMenuAdmin = findViewById(R.id.flAddMenuAdmin);
        flAddMenuAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogTambahMenu();
            }
        });

        //
        recyclerViewMenuAdmin = findViewById(R.id.recyclerViewMenuAdmin);
        getAllMenu();
    }

    private void getAllMenu(){
        ApiService.getApiConfig().getAllMenu().enqueue(new Callback<ArrayList<MenuModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MenuModel>> call, Response<ArrayList<MenuModel>> response) {
                menuAdminAdapter = new MenuAdminAdapter(response.body(), new MenuAdminAdapter.AdminAdapterListener() {
                    @Override
                    public void onClickEdit(MenuModel menuModel) {
                        alertDialogEdit(menuModel.getIdMenu(),menuModel.getNama_menu(), menuModel.getKategori(), menuModel.getJenis_menu(), menuModel.getHarga(), menuModel.getKapasitas(), menuModel.getGambar());
                    }

                    @Override
                    public void onClickGambar(String namaMenu, String gambar) {

                        View view = View.inflate(ListMenuAdminActivity.this, R.layout.alert_dialog_zoom_image, null);

                        TextView tVPreviewPesan = view.findViewById(R.id.tVPreviewPesan);
                        ImageView gambarMenu = view.findViewById(R.id.gambarMenu);
                        Button buttonKeluar = view.findViewById(R.id.buttonKeluar);

                        tVPreviewPesan.setText("Gambar "+namaMenu);

                        Picasso.get()
                                .load(ApiService.URL+"/warkop_sharelock/image/menu/"+gambar)
                                .error(R.drawable.no_image)
                                .into(gambarMenu);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListMenuAdminActivity.this);
                        alertDialogBuilder.setView(view);
                        AlertDialog dialog = alertDialogBuilder.create();
                        dialog.show();


                        //klik tambah
                        buttonKeluar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onClickHapus(String idMenu, String namaMenu) {
                        alertDialogHapus(idMenu, namaMenu);
//                        deleteMenuAdmin(idMenu);
                    }
                });

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListMenuAdminActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerViewMenuAdmin.setLayoutManager(layoutManager);
                recyclerViewMenuAdmin.setAdapter(menuAdminAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<MenuModel>> call, Throwable t) {

            }
        });
    }


    //tambah
    AutoCompleteTextView autoCompleteTextViewAdminKategoriMenu, autoCompleteTextViewAdminJenisMenu, autoCompleteTextViewAdminKapasitasMenu;
    String[] array_jenis_menu, array_kategori, arrayKapasitas;
    TextInputEditText etNamaMenu, etHargaMenu;
    TextView tambahGambarMenu;
    ImageView gambarTambahMenu;

    //cek

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            Context context = ListMenuAdminActivity.this;
            path = RealPathUtil.getRealPath(context, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            gambarTambahMenu.setImageBitmap(bitmap);
        }
    }

    // ALERT DIALOG
    //tambah
    private void alertDialogTambahMenu(){

        View view = View.inflate(ListMenuAdminActivity.this, R.layout.alert_dialog_admin_tambah_menu, null);

        etNamaMenu = view.findViewById(R.id.etNamaMenu);
        etHargaMenu = view.findViewById(R.id.etHargaMenu);

        //Jenis Menu
        array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_minuman);

        autoCompleteTextViewAdminJenisMenu = view.findViewById(R.id.autoCompleteTextViewAdminJenisMenu);
        autoCompleteTextViewAdminJenisMenu.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(ListMenuAdminActivity.this, R.layout.item_dropdown, array_jenis_menu);
        autoCompleteTextViewAdminJenisMenu.setAdapter(arrayAdapterJenisMenu);
//        autoCompleteTextViewAdminJenisMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ListMenuAdminActivity.this, "nilai: "+autoCompleteTextViewAdminJenisMenu.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //Kapasitas
        arrayKapasitas = getResources().getStringArray(R.array.kapasitas);
        autoCompleteTextViewAdminKapasitasMenu = view.findViewById(R.id.autoCompleteTextViewAdminKapasitasMenu);
        autoCompleteTextViewAdminKapasitasMenu.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapterKapasitas = new ArrayAdapter(ListMenuAdminActivity.this, R.layout.item_dropdown, arrayKapasitas);
        autoCompleteTextViewAdminKapasitasMenu.setAdapter(arrayAdapterKapasitas);

        //Kategori
        array_kategori = getResources().getStringArray(R.array.kategori);

        autoCompleteTextViewAdminKategoriMenu = view.findViewById(R.id.autoCompleteTextViewAdminKategoriMenu);
        autoCompleteTextViewAdminKategoriMenu.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapterKategori = new ArrayAdapter(ListMenuAdminActivity.this, R.layout.item_dropdown, array_kategori);

        autoCompleteTextViewAdminKategoriMenu.setAdapter(arrayAdapterKategori);
        autoCompleteTextViewAdminKategoriMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kategori = autoCompleteTextViewAdminKategoriMenu.getText().toString().trim();

                if(kategori.equals("Makanan")){
                    autoCompleteTextViewAdminJenisMenu.setText("Makanan Berat");
                    array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_makanan);
                    ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(ListMenuAdminActivity.this, R.layout.item_dropdown, array_jenis_menu);
                    autoCompleteTextViewAdminJenisMenu.setAdapter(arrayAdapterJenisMenu);
                }
                else if(kategori.equals("Minuman Ice")||kategori.equals("Minuman Hot")){
                    autoCompleteTextViewAdminJenisMenu.setText("Coffee");
                    array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_minuman);
                    ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(ListMenuAdminActivity.this, R.layout.item_dropdown, array_jenis_menu);
                    autoCompleteTextViewAdminJenisMenu.setAdapter(arrayAdapterJenisMenu);
                }
            }
        });



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListMenuAdminActivity.this);
        alertDialogBuilder.setView(view);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();

        //tambah gambar
        tambahGambarMenu = view.findViewById(R.id.tambahGambarMenu);
        gambarTambahMenu = view.findViewById(R.id.gambarTambahMenu);

        tambahGambarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 10);
                }
                else{
                    ActivityCompat.requestPermissions(ListMenuAdminActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });

        //klik tambah
        Button buttonTambahPesanan = view.findViewById(R.id.buttonTambahPesanan);
        Button buttonBatal = view.findViewById(R.id.buttonBatal);
        buttonTambahPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaMenu = etNamaMenu.getText().toString().trim();
                String kategoriMenu = autoCompleteTextViewAdminKategoriMenu.getText().toString().trim();
                String jenisMenu = autoCompleteTextViewAdminJenisMenu.getText().toString();
                String hargaMenu = etHargaMenu.getText().toString().trim();
                String kapasitasMenu = autoCompleteTextViewAdminKapasitasMenu.getText().toString().trim();

                boolean cek = false;

                if(namaMenu.length()==0){
                    etNamaMenu.setError("Tidak Boleh Kosong");
                    cek = true;
                }else{
                    cek = false;
                }

                if(hargaMenu.length()==0){
                    etHargaMenu.setError("Tidak Boleh Kosong");
                    cek = true;
                }else{
                    cek = false;
                }

                if(cek == false){
                    loading.alertDialogLoading(ListMenuAdminActivity.this);
                    tambahMenuBaruAdmin(dialog, namaMenu, kategoriMenu, jenisMenu, hargaMenu, kapasitasMenu);
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


    //hapus
    TextView tvNamaMenu;
    Button buttonHapusMenu, buttonBatal;
    private void alertDialogHapus(String idMenu, String namaMenu){
        View view = View.inflate(ListMenuAdminActivity.this, R.layout.alert_dialog_admin_hapus_menu, null);

        tvNamaMenu = view.findViewById(R.id.tvNamaMenu);
        buttonHapusMenu = view.findViewById(R.id.buttonHapusMenu);
        buttonBatal = view.findViewById(R.id.buttonBatal);

        tvNamaMenu.setText("Hapus "+namaMenu+" ?");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListMenuAdminActivity.this);
        alertDialogBuilder.setView(view);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();


        //klik tambah
        Button buttonTambahPesanan = view.findViewById(R.id.buttonTambahPesanan);
        Button buttonBatal = view.findViewById(R.id.buttonBatal);
        buttonHapusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.alertDialogLoading(ListMenuAdminActivity.this);
                dialog.dismiss();
                deleteMenuAdmin(idMenu);
            }
        });
        buttonBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //Edit
    private void alertDialogEdit(String idMenu,String namaMenu, String kategori, String jenisMenu, String harga, String kapasitas, String gambar){
        View view = View.inflate(ListMenuAdminActivity.this, R.layout.alert_dialog_admin_tambah_menu, null);

        etNamaMenu = view.findViewById(R.id.etNamaMenu);
        etHargaMenu = view.findViewById(R.id.etHargaMenu);

        etNamaMenu.setText(namaMenu);
        etHargaMenu.setText(harga);

        //Jenis Menu
        array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_minuman);

        autoCompleteTextViewAdminJenisMenu = view.findViewById(R.id.autoCompleteTextViewAdminJenisMenu);
        autoCompleteTextViewAdminJenisMenu.setText(jenisMenu);
        autoCompleteTextViewAdminJenisMenu.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(ListMenuAdminActivity.this, R.layout.item_dropdown, array_jenis_menu);
        autoCompleteTextViewAdminJenisMenu.setAdapter(arrayAdapterJenisMenu);
//        autoCompleteTextViewAdminJenisMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ListMenuAdminActivity.this, "nilai: "+autoCompleteTextViewAdminJenisMenu.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //Kapasitas
        arrayKapasitas = getResources().getStringArray(R.array.kapasitas);
        autoCompleteTextViewAdminKapasitasMenu = view.findViewById(R.id.autoCompleteTextViewAdminKapasitasMenu);
        autoCompleteTextViewAdminKapasitasMenu.setText(kapasitas);
        autoCompleteTextViewAdminKapasitasMenu.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapterKapasitas = new ArrayAdapter(ListMenuAdminActivity.this, R.layout.item_dropdown, arrayKapasitas);
        autoCompleteTextViewAdminKapasitasMenu.setAdapter(arrayAdapterKapasitas);

        //Kategori
        array_kategori = getResources().getStringArray(R.array.kategori);

        autoCompleteTextViewAdminKategoriMenu = view.findViewById(R.id.autoCompleteTextViewAdminKategoriMenu);
        autoCompleteTextViewAdminKategoriMenu.setText(kategori);
        autoCompleteTextViewAdminKategoriMenu.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapterKategori = new ArrayAdapter(ListMenuAdminActivity.this, R.layout.item_dropdown, array_kategori);

        autoCompleteTextViewAdminKategoriMenu.setAdapter(arrayAdapterKategori);
        autoCompleteTextViewAdminKategoriMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kategori = autoCompleteTextViewAdminKategoriMenu.getText().toString().trim();

                if(kategori.equals("Makanan")){
                    autoCompleteTextViewAdminJenisMenu.setText("Makanan Berat");
                    array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_makanan);
                    ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(ListMenuAdminActivity.this, R.layout.item_dropdown, array_jenis_menu);
                    autoCompleteTextViewAdminJenisMenu.setAdapter(arrayAdapterJenisMenu);
                }
                else if(kategori.equals("Minuman Ice")||kategori.equals("Minuman Hot")){
                    autoCompleteTextViewAdminJenisMenu.setText("Coffee");
                    array_jenis_menu = getResources().getStringArray(R.array.jenis_menu_minuman);
                    ArrayAdapter arrayAdapterJenisMenu = new ArrayAdapter(ListMenuAdminActivity.this, R.layout.item_dropdown, array_jenis_menu);
                    autoCompleteTextViewAdminJenisMenu.setAdapter(arrayAdapterJenisMenu);
                }
            }
        });

        //gambar

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListMenuAdminActivity.this);
        alertDialogBuilder.setView(view);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();

        //tambah gambar
        tambahGambarMenu = view.findViewById(R.id.tambahGambarMenu);
        gambarTambahMenu = view.findViewById(R.id.gambarTambahMenu);
        Picasso.get()
                .load(ApiService.URL+"/warkop_sharelock/image/menu/"+gambar)
                .error(R.drawable.no_image)
                .into(gambarTambahMenu);

        tambahGambarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 10);
                }
                else{
                    ActivityCompat.requestPermissions(ListMenuAdminActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });

        //klik tambah
        Button buttonUpdatePesanan = view.findViewById(R.id.buttonTambahPesanan);
        Button buttonBatal = view.findViewById(R.id.buttonBatal);
        buttonUpdatePesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaMenu = etNamaMenu.getText().toString().trim();
                String kategoriMenu = autoCompleteTextViewAdminKategoriMenu.getText().toString().trim();
                String jenisMenu = autoCompleteTextViewAdminJenisMenu.getText().toString();
                String hargaMenu = etHargaMenu.getText().toString().trim();
                String kapasitasMenu = autoCompleteTextViewAdminKapasitasMenu.getText().toString().trim();

                boolean cek = false;

                if(namaMenu.length()==0){
                    etNamaMenu.setError("Tidak Boleh Kosong");
                    cek = true;
                }else{
                    cek = false;
                }

                if(hargaMenu.length()==0){
                    etHargaMenu.setError("Tidak Boleh Kosong");
                    cek = true;
                }else{
                    cek = false;

                }

                if(cek == false){
                    loading.alertDialogLoading(ListMenuAdminActivity.this);
                    updateMenuBaruAdmin(dialog, idMenu, namaMenu, kategoriMenu, jenisMenu, hargaMenu, kapasitasMenu);
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

    private void tambahMenuBaruAdmin(AlertDialog dialog, String nama_menu1, String kategori1, String jenis_menu1, String harga1, String kapasitas1){

        MultipartBody.Part body = null;
        try{
            File file = new File(path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("gambar", file.getName(), requestFile);
        }
        catch (Exception ex){

        }

        RequestBody fu = RequestBody.create(MediaType.parse("multipart/form-data"), "kosong");
        RequestBody nama_menu = RequestBody.create(MediaType.parse("multipart/form-data"), nama_menu1);
        RequestBody kategori = RequestBody.create(MediaType.parse("multipart/form-data"), kategori1);
        RequestBody jenis_menu = RequestBody.create(MediaType.parse("multipart/form-data"), jenis_menu1);
        RequestBody harga = RequestBody.create(MediaType.parse("multipart/form-data"), harga1);
        RequestBody kapasitas = RequestBody.create(MediaType.parse("multipart/form-data"), kapasitas1);

        ApiService.getApiConfig().tambahMenuBaruAdmin(fu, body, nama_menu, kategori, jenis_menu, harga, kapasitas)
                .enqueue(new Callback<PesananModel>() {
            @Override
            public void onResponse(Call<PesananModel> call, Response<PesananModel> response) {
                loading.alertDialogCancel();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<PesananModel> call, Throwable t) {
                Toast.makeText(ListMenuAdminActivity.this, "Berhasil Menambah", Toast.LENGTH_SHORT).show();
                loading.alertDialogCancel();
                dialog.dismiss();
            }
        });
    }

    private void deleteMenuAdmin(String id_menu){
        ApiService.getApiConfig().deleteMenuAdmin("", id_menu)
                .enqueue(new Callback<PesananModel>() {
                    @Override
                    public void onResponse(Call<PesananModel> call, Response<PesananModel> response) {
                        Toast.makeText(ListMenuAdminActivity.this, "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                        loading.alertDialogCancel();
                    }

                    @Override
                    public void onFailure(Call<PesananModel> call, Throwable t) {
                        Toast.makeText(ListMenuAdminActivity.this, "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                        loading.alertDialogCancel();
                    }
                });
    }

    private void updateMenuBaruAdmin(AlertDialog dialog, String id_menu1,String nama_menu1, String kategori1, String jenis_menu1, String harga1, String kapasitas1){
        MultipartBody.Part body = null;
        try{
            File file = new File(path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("gambar", file.getName(), requestFile);
        }
        catch (Exception ex){
            body = null;
        }

        if(body != null){
            RequestBody fu = RequestBody.create(MediaType.parse("multipart/form-data"), "kosong");
            RequestBody id_menu = RequestBody.create(MediaType.parse("multipart/form-data"), id_menu1);
            RequestBody nama_menu = RequestBody.create(MediaType.parse("multipart/form-data"), nama_menu1);
            RequestBody kategori = RequestBody.create(MediaType.parse("multipart/form-data"), kategori1);
            RequestBody jenis_menu = RequestBody.create(MediaType.parse("multipart/form-data"), jenis_menu1);
            RequestBody harga = RequestBody.create(MediaType.parse("multipart/form-data"), harga1);
            RequestBody kapasitas = RequestBody.create(MediaType.parse("multipart/form-data"), kapasitas1);

            ApiService.getApiConfig().updateMenuAdmin(fu, body, id_menu, nama_menu, kategori, jenis_menu, harga, kapasitas)
                    .enqueue(new Callback<PesananModel>() {
                        @Override
                        public void onResponse(Call<PesananModel> call, Response<PesananModel> response) {
                            Log.d(TAG, "onResponse: "+response.body());
                            Toast.makeText(ListMenuAdminActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<PesananModel> call, Throwable t) {
                            Log.d(TAG, "onFailure: "+t.getMessage().toString());
                            Toast.makeText(ListMenuAdminActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();
                            loading.alertDialogCancel();
                            dialog.dismiss();
                        }
                    });
        }
        else{
            ApiService.getApiConfig().updateMenuAdminNoImage("updateMenuAdminNoImage", id_menu1, nama_menu1, kategori1, jenis_menu1, harga1, kapasitas1)
                    .enqueue(new Callback<PesananModel>() {
                        @Override
                        public void onResponse(Call<PesananModel> call, Response<PesananModel> response) {
                            Log.d(TAG, "onResponse: "+response.body());
                            Toast.makeText(ListMenuAdminActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<PesananModel> call, Throwable t) {
                            Log.d(TAG, "onFailure: "+t.getMessage().toString());
                            Toast.makeText(ListMenuAdminActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();
                            loading.alertDialogCancel();
                            dialog.dismiss();
                        }
                    });
        }

    }
}