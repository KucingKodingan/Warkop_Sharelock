package com.example.kakjayadi_warkopsharelok.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kakjayadi_warkopsharelok.AngkaRibuan;
import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.MenuModel;
import com.example.kakjayadi_warkopsharelok.data.model.MenuRekomendasiModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    ArrayList<MenuModel> arrayListMenu;
    AdapterListener listener;
    Context context;
    View rootView;

    public MenuAdapter(ArrayList<MenuModel> arrayListMenu, AdapterListener listener) {
        this.arrayListMenu = arrayListMenu;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_menu, parent, false);

        context = parent.getContext();
        rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        return new MenuViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MenuViewHolder holder, int position) {
        holder.item_list_gambar_menu.setImageResource(R.drawable.cappucino_ice);
        holder.item_list_nama.setText(arrayListMenu.get(position).getNama_menu());
        AngkaRibuan obj = new AngkaRibuan();

        obj.setAngkaRibuan(arrayListMenu.get(position).getHarga().length(), arrayListMenu.get(position).getHarga());
        holder.item_list_harga.setText(obj.getAngkaRibuan());

        String gambar = arrayListMenu.get(position).getGambar();
        Picasso.get()
                .load(ApiService.URL+"/warkop_sharelock/image/menu/"+gambar)
                .error(R.drawable.no_image)
                .into(holder.item_list_gambar_menu);

        if(arrayListMenu.get(position).getKapasitas().equals("ada")){
            holder.item_list_btn_menu_habis.setVisibility(View.GONE);
        }

        MenuModel menu = arrayListMenu.get(position);

        holder.item_list_btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(menu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListMenu.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout item_list_btn_menu, item_list_btn_menu_habis;
        ImageView item_list_gambar_menu;
        TextView item_list_nama, item_list_harga;
        public MenuViewHolder(@NonNull View v) {
            super(v);

            item_list_btn_menu = v.findViewById(R.id.item_list_btn_menu);
            item_list_btn_menu_habis = v.findViewById(R.id.item_list_btn_menu_habis);
            item_list_gambar_menu = v.findViewById(R.id.item_list_gambar_menu);
            item_list_nama = v.findViewById(R.id.item_list_nama);
            item_list_harga = v.findViewById(R.id.item_list_harga);

        }
    }

    public interface AdapterListener{
        void onClick(MenuModel menuModel);
    }
}
