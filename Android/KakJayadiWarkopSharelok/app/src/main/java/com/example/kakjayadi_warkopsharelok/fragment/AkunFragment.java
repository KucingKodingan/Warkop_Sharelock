package com.example.kakjayadi_warkopsharelok.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.activity.EditActivity;
import com.example.kakjayadi_warkopsharelok.activity.MainActivity;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AkunFragment extends Fragment {

    View view;
    String TAG = "AkunFragment";
    SharedPreferences sp;
    String email, nama;
    SharedPreferences.Editor editor;

    TextInputEditText et_nama, et_nomor_wa, et_email, et_password;
    Button btn_edit, btn_logout;

    String id_user, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_akun, container, false);
        et_nama = view.findViewById(R.id.et_nama);
        et_nomor_wa = view.findViewById(R.id.et_nomor_wa);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);

        btn_logout = view.findViewById(R.id.btn_logout);
        btn_edit = view.findViewById(R.id.btn_edit);

        sp = getContext().getSharedPreferences("SHAREDPREFERENCES_LOGIN", Context.MODE_PRIVATE);
        email = sp.getString("EMAIL", null);
        nama = sp.getString("NAMA", null);
        password = sp.getString("PASSWORD", null);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sp.edit();
                editor.putString("EMAIL", null);
                editor.putString("PASSWORD", null);
                editor.putString("NAMA", null);
                editor.putString("SEBAGAI", null);
                editor.apply();

                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });

        ambilDataUser();

        return view;
    }

    private void ambilDataUser() {
        ApiService.getApiConfig().getDataUserLogin("login", email, password).enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                try{
                    String nama = response.body().get(0).getNama();
                    password = response.body().get(0).getPassword();

                    et_nama.setText(response.body().get(0).getNama().toString());
                    et_nomor_wa.setText(response.body().get(0).getNomor_wa().toString());
                    et_email.setText(response.body().get(0).getEmail().toString());
                    et_password.setText(response.body().get(0).getPassword().toString());

                    id_user = response.body().get(0).getId_user();


                }
                catch (Exception ex){
                    Toast.makeText(getContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error: "+ex);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {

            }
        });
    }
}