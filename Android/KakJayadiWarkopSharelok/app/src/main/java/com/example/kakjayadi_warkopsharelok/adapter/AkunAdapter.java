package com.example.kakjayadi_warkopsharelok.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;
import com.example.kakjayadi_warkopsharelok.data.model.UserModel;

import java.util.ArrayList;

public class AkunAdapter extends RecyclerView.Adapter<AkunAdapter.AkunViewHolder> {

    ArrayList<UserModel> userModels;
    AdapterListenerListAkun listener;
    Context context;
    View rootView;

    public AkunAdapter(ArrayList<UserModel> userModels, AdapterListenerListAkun listener) {
        this.userModels = userModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AkunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_akun, parent, false);

        context = parent.getContext();
        rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        return new AkunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AkunViewHolder holder, int position) {

        holder.tvNamaAkun.setText(userModels.get(position).getNama());
        holder.tvEmailAkun.setText(userModels.get(position).getEmail());
        holder.tvNomorAkun.setText(userModels.get(position).getNomor_wa());
        holder.tvSebagaiAkun.setText(userModels.get(position).getSebagai());

        UserModel userModel = userModels.get(position);
        holder.clAkunItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(userModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class AkunViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout clAkunItemList;
        TextView tvNamaAkun,tvEmailAkun,tvNomorAkun,tvSebagaiAkun;

        public AkunViewHolder(@NonNull View v) {
            super(v);

            clAkunItemList = v.findViewById(R.id.clAkunItemList);
            tvNamaAkun = v.findViewById(R.id.tvNamaAkun);
            tvEmailAkun = v.findViewById(R.id.tvEmailAkun);
            tvNomorAkun = v.findViewById(R.id.tvNomorAkun);
            tvSebagaiAkun = v.findViewById(R.id.tvSebagaiAkun);
        }
    }

    public interface AdapterListenerListAkun{
        void onClick(UserModel userModel);
    }
}
