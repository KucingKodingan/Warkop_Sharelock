package com.example.kakjayadi_warkopsharelok.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.PostUserModel;
import com.example.kakjayadi_warkopsharelok.data.model.UserModel;
import com.example.kakjayadi_warkopsharelok.util.LoadingAlertDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileAkunActivity extends Activity {

    Bundle bundle;

    TextInputEditText et_admin_nama,et_admin_nomor_wa, et_admin_email, et_admin_password;
    AutoCompleteTextView autoCompleteTextViewSebagai, autoCompleteTextViewShift;
    Button btn_simpan, btn_batal;
    String aksi,id_user, nama, nomor_wa, email, password, sebagai, shift;

    String[] arraySebagai, arrayShift;

    TextInputLayout textLayoutShift;

    //loading
    LoadingAlertDialog loading = new LoadingAlertDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_akun);

        et_admin_nama = findViewById(R.id.et_admin_nama);
        et_admin_nomor_wa = findViewById(R.id.et_admin_nomor_wa);
        et_admin_email = findViewById(R.id.et_admin_email);
        et_admin_password = findViewById(R.id.et_admin_password);
        autoCompleteTextViewSebagai = findViewById(R.id.autoCompleteTextViewSebagai);
        autoCompleteTextViewShift = findViewById(R.id.autoCompleteTextViewShift);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_batal = findViewById(R.id.btn_batal);

        textLayoutShift = findViewById(R.id.textLayoutShift);

        bundle = getIntent().getExtras();
        aksi = bundle.getString("aksi");

        if(aksi.equals("update")){
            id_user = bundle.getString("id_user");
            nama = bundle.getString("nama");
            nomor_wa = bundle.getString("nomor_wa");
            email = bundle.getString("email");
            password = bundle.getString("password");
            sebagai = bundle.getString("sebagai");
            shift = bundle.getString("shift");

            et_admin_nama.setText(nama);
            et_admin_nomor_wa.setText(nomor_wa);
            et_admin_email.setText(email);
            et_admin_password.setText(password);
            autoCompleteTextViewSebagai.setText(sebagai);
            autoCompleteTextViewShift.setText(shift);

            if(sebagai.equals("pelanggan")){
                autoCompleteTextViewShift.setText("none");
                textLayoutShift.setEnabled(false);
            }
        }
        else if(aksi.equals("tambah")){

        }

        arraySebagai = getResources().getStringArray(R.array.sebagai);
        arrayShift = getResources().getStringArray(R.array.shift);

        autoCompleteTextViewSebagai.setInputType(View.AUTOFILL_TYPE_NONE);
        autoCompleteTextViewShift.setInputType(View.AUTOFILL_TYPE_NONE);

        ArrayAdapter arrayAdapterSebagai = new ArrayAdapter(ProfileAkunActivity.this, R.layout.item_dropdown, arraySebagai);
        ArrayAdapter arrayAdapterShift = new ArrayAdapter(ProfileAkunActivity.this, R.layout.item_dropdown, arrayShift);

        autoCompleteTextViewSebagai.setAdapter(arrayAdapterSebagai);
        autoCompleteTextViewShift.setAdapter(arrayAdapterShift);

        autoCompleteTextViewSebagai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String sebagai = autoCompleteTextViewSebagai.getText().toString().trim();

                if(sebagai.equals("pelanggan")){
                    autoCompleteTextViewShift.setText("none");
                    textLayoutShift.setEnabled(false);
                }
                else if(sebagai.equals("kasir")){
                    autoCompleteTextViewShift.setText("siang");
                    ArrayAdapter arrayAdapterShift = new ArrayAdapter(ProfileAkunActivity.this, R.layout.item_dropdown, arrayShift);
                    autoCompleteTextViewShift.setAdapter(arrayAdapterShift);
                    textLayoutShift.setEnabled(true);
                }
            }
        });


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.alertDialogLoading(ProfileAkunActivity.this);
                cekEmail(et_admin_email.getText().toString().trim(), email, aksi);
            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void cekEmail(String email, String emailSebelumnya, String aksi){
        ApiService.getApiConfig().cekEmail("cek_email", email)
                .enqueue(new Callback<ArrayList<UserModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                        if(aksi.equals("tambah")){
                            if(response.body().size() <= 0){
                                tambahAkun();
                            }
                            else{
                                et_admin_email.setError("Email Sudah Ada");
                                Toast.makeText(ProfileAkunActivity.this, "Email sudah ada", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(aksi.equals("update")){
                            if(response.body().get(0).getEmail().equals(emailSebelumnya)){
                                updateAkun();
                            }
                            else{
                                Toast.makeText(ProfileAkunActivity.this, "Akun sudah ada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {

                    }
                });
    }

    private void tambahAkun(){
        ApiService.getApiConfig().postTambahAkun("tambahAakun", et_admin_nama.getText().toString().trim(), et_admin_nomor_wa.getText().toString().trim(), et_admin_email.getText().toString().trim(),
                        et_admin_password.getText().toString().trim(), autoCompleteTextViewSebagai.getText().toString().trim(),autoCompleteTextViewShift.getText().toString().trim())
                .enqueue(new Callback<PostUserModel>() {
                    @Override
                    public void onResponse(Call<PostUserModel> call, Response<PostUserModel> response) {

                    }

                    @Override
                    public void onFailure(Call<PostUserModel> call, Throwable t) {
                        Toast.makeText(ProfileAkunActivity.this, "Berhasil Tambah", Toast.LENGTH_SHORT).show();
                        loading.alertDialogCancel();
                        finish();
                    }
                });
    }

    private void updateAkun(){
        ApiService.getApiConfig().postUpdateAkun("updateAkun", id_user, et_admin_nama.getText().toString().trim(), et_admin_nomor_wa.getText().toString().trim(), et_admin_email.getText().toString().trim(),
                        et_admin_password.getText().toString().trim(), autoCompleteTextViewSebagai.getText().toString().trim(),autoCompleteTextViewShift.getText().toString().trim())
                .enqueue(new Callback<PostUserModel>() {
                    @Override
                    public void onResponse(Call<PostUserModel> call, Response<PostUserModel> response) {

                    }

                    @Override
                    public void onFailure(Call<PostUserModel> call, Throwable t) {
                        Toast.makeText(ProfileAkunActivity.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                        loading.alertDialogCancel();
                        finish();
                    }
                });
    }
}