package com.example.kakjayadi_warkopsharelok.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kakjayadi_warkopsharelok.AngkaRibuan;
import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.data.model.MenuModel;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;

import java.util.ArrayList;

public class ListPesananAdapter extends RecyclerView.Adapter<ListPesananAdapter.ListPesananViewHolder> {
    ArrayList<PesananModel> pesananModels;
    AdapterListenerListPesanan listener;
    Context context;
    View rootView;

    public ListPesananAdapter(ArrayList<PesananModel> pesananModels, AdapterListenerListPesanan listener) {
        this.pesananModels = pesananModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListPesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_pesanan, parent, false);

        context = parent.getContext();
        rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        return new ListPesananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPesananViewHolder holder, int position) {
        holder.tvNamaPemesan.setText(pesananModels.get(position).getNama());
        holder.tvJumlahPesanan.setText(pesananModels.get(position).getJumlah()+" pesanan");
        AngkaRibuan obj = new AngkaRibuan();
        obj.setAngkaRibuan(pesananModels.get(position).getTotal().length(), pesananModels.get(position).getTotal());
        holder.tvTotalHargaPesanan.setText(obj.getAngkaRibuan());
        holder.tvWaktuPesanan.setText(pesananModels.get(position).getTgl_pesanan());
        PesananModel pesananModel = pesananModels.get(position);

        holder.clPesananItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(pesananModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pesananModels.size();
    }

    public class ListPesananViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout clPesananItemList;
        TextView tvNamaPemesan,tvJumlahPesanan,tvTotalHargaPesanan,tvWaktuPesanan;

        public ListPesananViewHolder(@NonNull View v) {
            super(v);

            clPesananItemList = v.findViewById(R.id.clPesananItemList);
            tvNamaPemesan = v.findViewById(R.id.tvNamaPemesan);
            tvJumlahPesanan = v.findViewById(R.id.tvJumlahPesanan);
            tvTotalHargaPesanan = v.findViewById(R.id.tvTotalHargaPesanan);
            tvWaktuPesanan = v.findViewById(R.id.tvWaktuPesanan);

        }
    }

    public interface AdapterListenerListPesanan{
        void onClick(PesananModel pesananModel);
    }
}
