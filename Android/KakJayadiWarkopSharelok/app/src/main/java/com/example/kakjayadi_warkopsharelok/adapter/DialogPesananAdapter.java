package com.example.kakjayadi_warkopsharelok.adapter;

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

public class DialogPesananAdapter extends RecyclerView.Adapter<DialogPesananAdapter.DialogPesananViewHolder> {

    ArrayList<PesananModel> pesananModelArrayList;

    public DialogPesananAdapter(ArrayList<PesananModel> pesananModelArrayList) {
        this.pesananModelArrayList = pesananModelArrayList;
    }

    @NonNull
    @Override
    public DialogPesananAdapter.DialogPesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_alert_dialog_list_pesanan, parent, false);
        return new DialogPesananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogPesananAdapter.DialogPesananViewHolder holder, int position) {
        holder.list_alert_dialog_nama_menu.setText(pesananModelArrayList.get(position).getMenu());
        holder.list_alert_dialog_jumlah_menu.setText(pesananModelArrayList.get(position).getJumlah()+"x");
        AngkaRibuan obj = new AngkaRibuan();
        obj.setAngkaRibuan(pesananModelArrayList.get(position).getHarga().length(), pesananModelArrayList.get(position).getHarga().toString());
        holder.list_alert_dialog_harga_menu.setText("@"+obj.getAngkaRibuan());
        obj.setAngkaRibuan(pesananModelArrayList.get(position).getTotal().length(),pesananModelArrayList.get(position).getTotal());
        holder.list_alert_dialog_total_harga_menu.setText(obj.getAngkaRibuan());
    }

    @Override
    public int getItemCount() {
        return pesananModelArrayList.size();
    }

    public class DialogPesananViewHolder extends RecyclerView.ViewHolder{
        TextView list_alert_dialog_nama_menu, list_alert_dialog_jumlah_menu, list_alert_dialog_harga_menu, list_alert_dialog_total_harga_menu;
        public DialogPesananViewHolder(@NonNull View v) {
            super(v);

            list_alert_dialog_nama_menu = v.findViewById(R.id.list_alert_dialog_nama_menu);
            list_alert_dialog_jumlah_menu = v.findViewById(R.id.list_alert_dialog_jumlah_menu);
            list_alert_dialog_harga_menu = v.findViewById(R.id.list_alert_dialog_harga_menu);
            list_alert_dialog_total_harga_menu = v.findViewById(R.id.list_alert_dialog_total_harga_menu);
        }
    }
}
