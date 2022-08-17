package com.example.kakjayadi_warkopsharelok.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.MenuModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdminAdapter extends RecyclerView.Adapter<MenuAdminAdapter.MenuAdminViewHolder> {

    ArrayList<MenuModel> menuModels;
    MenuAdminAdapter.AdminAdapterListener listener;
    Context context;
    View rootView;

    public MenuAdminAdapter(ArrayList<MenuModel> menuModels, AdminAdapterListener listener) {
        this.menuModels = menuModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_menu_admin, parent, false);

        context = parent.getContext();
        rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        return new MenuAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdminViewHolder holder, int position) {
        holder.tvMenuAdminNoMenu.setText(String.valueOf(Integer.valueOf(position)+1));
        holder.tvMenuAdminNamaMenu.setText(menuModels.get(position).getNama_menu());
        holder.tvMenuAdminKategoriMenu.setText(menuModels.get(position).getKategori());
        holder.tvMenuAdminJenisMenu.setText(menuModels.get(position).getJenis_menu());
        holder.tvMenuAdminHargaMenu.setText(menuModels.get(position).getHarga());
        holder.tvMenuAdminKapasitasMenu.setText(menuModels.get(position).getKapasitas());

        //gambar
        String gambar = menuModels.get(position).getGambar();
        Picasso.get()
                .load(ApiService.URL+"/warkop_sharelock/image/menu/"+gambar)
                .error(R.drawable.no_image)
                .into(holder.imageMenuAdminGambarMenu);

        //Klik gambar
        String namaMenu = menuModels.get(position).getNama_menu();
        holder.imageMenuAdminGambarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickGambar(namaMenu, gambar);
            }
        });

        //edit
        MenuModel menu = menuModels.get(position);
        holder.buttonEditMenuAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickEdit(menu);
            }
        });

        //hapus
        String idMenu = menuModels.get(position).getIdMenu();
        holder.buttonHapusMenuAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickHapus(idMenu, namaMenu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuModels.size();
    }


    public class MenuAdminViewHolder extends RecyclerView.ViewHolder{
        TextView tvMenuAdminNoMenu, tvMenuAdminNamaMenu, tvMenuAdminKategoriMenu, tvMenuAdminJenisMenu, tvMenuAdminHargaMenu, tvMenuAdminKapasitasMenu;
        ImageView imageMenuAdminGambarMenu, buttonEditMenuAdmin, buttonHapusMenuAdmin;
        public MenuAdminViewHolder(@NonNull View v) {
            super(v);

            tvMenuAdminNoMenu = v.findViewById(R.id.tvMenuAdminNoMenu);
            tvMenuAdminNamaMenu = v.findViewById(R.id.tvMenuAdminNamaMenu);
            tvMenuAdminKategoriMenu = v.findViewById(R.id.tvMenuAdminKategoriMenu);
            tvMenuAdminJenisMenu = v.findViewById(R.id.tvMenuAdminJenisMenu);
            tvMenuAdminHargaMenu = v.findViewById(R.id.tvMenuAdminHargaMenu);
            imageMenuAdminGambarMenu = v.findViewById(R.id.imageMenuAdminGambarMenu);
            tvMenuAdminKapasitasMenu = v.findViewById(R.id.tvMenuAdminKapasitasMenu);
            buttonEditMenuAdmin = v.findViewById(R.id.buttonEditMenuAdmin);
            buttonHapusMenuAdmin = v.findViewById(R.id.buttonHapusMenuAdmin);
        }
    }

    public interface AdminAdapterListener{
        void onClickEdit(MenuModel menuModel);
        void onClickGambar(String nama, String gambar);
        void onClickHapus(String idMenu, String namaMenu);
    }
}
