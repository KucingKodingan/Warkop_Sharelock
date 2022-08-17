package com.example.kakjayadi_warkopsharelok.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kakjayadi_warkopsharelok.AngkaRibuan;
import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;

import java.util.ArrayList;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.PesananViewHolder> {
    ArrayList<PesananModel> pesananModel;

    public PesananAdapter(ArrayList<PesananModel> pesananModel) {
        this.pesananModel = pesananModel;
    }

    @NonNull
    @Override
    public PesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_profile_pesanan, parent, false);

        return new PesananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananViewHolder holder, int position) {
        AngkaRibuan obj = new AngkaRibuan();
        holder.list_profile_nama_menu.setText(pesananModel.get(position).getMenu());
        holder.list_profile_jumlah_menu.setText(pesananModel.get(position).getJumlah()+"x");
        obj.setAngkaRibuan(pesananModel.get(position).getHarga().length(), pesananModel.get(position).getHarga());
        holder.list_profile_harga_menu.setText("@"+obj.getAngkaRibuan());
        obj.setAngkaRibuan(pesananModel.get(position).getTotal().length(), pesananModel.get(position).getTotal());
        holder.list_profile_total_harga_menu.setText(obj.getAngkaRibuan());
    }

    @Override
    public int getItemCount() {
        return pesananModel.size();
    }

    public class PesananViewHolder extends RecyclerView.ViewHolder {
        TextView list_profile_nama_menu, list_profile_jumlah_menu, list_profile_harga_menu, list_profile_total_harga_menu;
        public PesananViewHolder(@NonNull View v) {
            super(v);

            list_profile_nama_menu = v.findViewById(R.id.list_profile_nama_menu);
            list_profile_jumlah_menu = v.findViewById(R.id.list_profile_jumlah_menu);
            list_profile_harga_menu = v.findViewById(R.id.list_profile_harga_menu);
            list_profile_total_harga_menu = v.findViewById(R.id.list_profile_total_harga_menu);
        }
    }
}
