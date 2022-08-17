package com.example.kakjayadi_warkopsharelok.data.api;

import com.example.kakjayadi_warkopsharelok.data.model.JenisMenuModel;
import com.example.kakjayadi_warkopsharelok.data.model.MenuModel;
import com.example.kakjayadi_warkopsharelok.data.model.MenuRekomendasiModel;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;
import com.example.kakjayadi_warkopsharelok.data.model.PostUserModel;
import com.example.kakjayadi_warkopsharelok.data.model.UserModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiConfig {

    //GET
    @GET("menu.php")
    Call<ArrayList<MenuModel>> getDataMenu();

    @GET("user.php")
    Call<ArrayList<UserModel>> getDataUserLogin(@Query("login") String login,
                                                @Query("email") String email,
                                                @Query("password") String password);

    @GET("user.php")
    Call<ArrayList<UserModel>> getDataUserCekRegistrasi(@Query("cek_registrasi") String cek_registrasi,
                                                        @Query("email") String email,
                                                        @Query("sebagai") String sebagai);

    //Get Jenis Menu
    @GET("jenins_menu.php")
    Call<ArrayList<MenuRekomendasiModel>> getJenisMenuMinumanIce(@Query("minuman_ice") String minuman_ice);

    @GET("jenins_menu.php")
    Call<ArrayList<MenuRekomendasiModel>> getJenisMenuMinumanHot(@Query("minuman_hot") String minuman_hot);

    @GET("jenins_menu.php")
    Call<ArrayList<MenuRekomendasiModel>> getJenisMenuMakanan(@Query("makanan") String makanan);

    //Get Rekomendasi
    @GET("rekomendasi.php")
    Call<ArrayList<MenuRekomendasiModel>> getMenuRekomendasi(@Query("kategori") String kategori,
                                                             @Query("jenis_menu") String jenis_menu,
                                                             @Query("nominal_awal") String nominal_awal,
                                                             @Query("nominal_batas") String nominal_batas);

    @GET("user.php")
    Call<ArrayList<UserModel>> getDataUser();

    //Get pesanan
    @GET("pesanan.php")
    Call<ArrayList<PesananModel>> getMemesan(@Query("email") String email,
                                             @Query("keterangan") String keterangan);

    //Get jumlah pesanan
    @GET("pesanan.php")
    Call<ArrayList<PesananModel>> getJumlahPesananMenu(@Query("jumlah_menu") String jumlah_menu,
                                                       @Query("email") String email,
                                                       @Query("id_menu") String id_menu);

    //Get list pesanan pelanggan
    @GET("pesanan.php")
    Call<ArrayList<PesananModel>> getListPesananPelanggan(@Query("list_pesanan_pelanggan") String list_pesanan_pelanggan,
                                                 @Query("email") String email);

    @GET("pesanan.php")
    Call<ArrayList<PesananModel>> getListPesananPelangganSudahBayar(@Query("list_pesanan_pelanggan_sudah_bayar") String list_pesanan_pelanggan_sudah_bayar,
                                                          @Query("email") String email);

    //Get list pesanan
    @GET("pesanan.php")
    Call<ArrayList<PesananModel>> getListPesananKasir(@Query("list_pesanan_pelanggan_kasir") String list_pesanan_pelanggan_kasir);

    //Get list pesanan
    @GET("pesanan.php")
    Call<ArrayList<PesananModel>> getListPesananKasirSudahBayar(@Query("list_pesanan_pelanggan_kasir_sudah_bayar") String list_pesanan_pelanggan_kasir_sudah_bayar);

    //Get profile pesanan
    @GET("pesanan.php")
    Call<ArrayList<PesananModel>> getProfilePesanan(@Query("profile_pesanan") String profile_pesanan,
                                                    @Query("id_pesanan") String id_pesanan,
                                                    @Query("email") String email);


    //POST
    @FormUrlEncoded
    @POST("user.php")
    Call<PostUserModel> postDataUser(@Field("tambahUser") String tambahUser,
                                     @Field("nama") String nama,
                                     @Field("nomor_wa") String nomor_wa,
                                     @Field("email") String email,
                                     @Field("password") String password,
                                     @Field("sebagai") String sebagai,
                                     @Field("shift") String shift);

    @FormUrlEncoded
    @POST("user.php")
    Call<PostUserModel> updateDataUser(@Field("updateUser") String updateUser,
                                       @Field("id_user") String id_user,
                                       @Field("nama") String nama,
                                       @Field("nomor_wa") String nomor_wa,
                                       @Field("email") String email,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("pesanan.php")
    Call<PesananModel> postPesanan(@Field("postPesanan") String postPesanan,
                                   @Field("email") String email,
                                   @Field("nama") String nama,
                                   @Field("id_menu") String id_menu,
                                   @Field("menu") String menu,
                                   @Field("kategori") String kategori,
                                   @Field("jenis_menu") String jenis_menu,
                                   @Field("harga") String harga,
                                   @Field("jumlah") String jumlah,
                                   @Field("total") String total,
                                   @Field("kasir") String kasir,
                                   @Field("keterangan") String keterangan);

    //update pesanan
    @FormUrlEncoded
    @POST("pesanan.php")
    Call<PesananModel> memesan(@Field("memesan") String memesan,
                               @Field("id_menu") String id_menu,
                               @Field("email") String email);

    //update pesanan
    @FormUrlEncoded
    @POST("pesanan.php")
    Call<PesananModel> deletePesanan(@Field("deletePesanan") String deletePesanan,
                                     @Field("email") String email,
                                     @Field("id_menu") String id_menu);

    @FormUrlEncoded
    @POST("pesanan.php")
    Call<PesananModel> updatePesanan(@Field("updatePesanan") String updatePesanan,
                                   @Field("jumlah") String jumlah,
                                   @Field("total") String total,
                                   @Field("id_menu") String id_menu,
                                   @Field("email") String email);

    @FormUrlEncoded
    @POST("pesanan.php")
    Call<PesananModel> updateBayarPesanan(@Field("updateBayarPesanan") String updateBayarPesanan,
                                          @Field("id_pesanan") String id_pesanan,
                                          @Field("email") String email,
                                          @Field("nama_kasir") String nama_kasir);

    //Admin
    //Get
    @GET("admin.php")
    Call<ArrayList<MenuModel>> getAllMenu();

    //Get list pesanan Belum Bayar
    @GET("admin.php")
    Call<ArrayList<PesananModel>> getAdminListPesananKasirBelumPesan(@Query("admin_list_pesanan_pelanggan_belum_pesan") String admin_list_pesanan_pelanggan_belum_pesan);


    //Get list pesanan Belum Bayar
    @GET("admin.php")
    Call<ArrayList<PesananModel>> getAdminListPesananKasirBelumBayar(@Query("admin_list_pesanan_pelanggan_belum_bayar") String admin_list_pesanan_pelanggan_belum_bayar);

    //Get list pesanan Sudah Bayar
    @GET("admin.php")
    Call<ArrayList<PesananModel>> getAdminListPesananKasirSudahBayar(@Query("admin_list_pesanan_pelanggan_sudah_bayar") String admin_list_pesanan_pelanggan_sudah_bayar);

    @GET("admin.php")
    Call<ArrayList<UserModel>> getAdminListAkunKasir(@Query("admin_list_akun_kasir") String admin_list_akun_kasir);

    @GET("admin.php")
    Call<ArrayList<UserModel>> getAdminListAkunPelanggan(@Query("admin_list_akun_pelanggan") String admin_list_akun_pelanggan);

    @GET("admin.php")
    Call<ArrayList<UserModel>> cekEmail(@Query("cek_email") String cek_email,
                                        @Query("email") String email);

    //Post
    //tambah menu
    @Multipart
    @POST("admin.php")
    Call<PesananModel> tambahMenuBaruAdmin(@Part("tambahMenuBaruAdmin") RequestBody tambahMenuBaruAdmin,
                                           @Part MultipartBody.Part gambar,
                                           @Part("nama_menu") RequestBody nama_menu,
                                           @Part("kategori") RequestBody kategori,
                                           @Part("jenis_menu") RequestBody jenis_menu,
                                           @Part("harga") RequestBody harga,
                                           @Part("kapasitas") RequestBody kapasitas);

    //update menu
    @Multipart
    @POST("admin.php")
    Call<PesananModel> updateMenuAdmin(@Part("updateMenuAdmin") RequestBody updateMenuAdmin,
                                       @Part MultipartBody.Part gambar,
                                       @Part("id_menu") RequestBody id_menu,
                                       @Part("nama_menu") RequestBody nama_menu,
                                       @Part("kategori") RequestBody kategori,
                                       @Part("jenis_menu") RequestBody jenis_menu,
                                       @Part("harga") RequestBody harga,
                                       @Part("kapasitas") RequestBody kapasitas);

    @FormUrlEncoded
    @POST("admin.php")
    Call<PesananModel> updateMenuAdminNoImage(@Field("updateMenuAdminNoImage") String updateMenuAdminNoImage,
                                              @Field("id_menu") String id_menu,
                                              @Field("nama_menu") String nama_menu,
                                              @Field("kategori") String kategori,
                                              @Field("jenis_menu") String jenis_menu,
                                              @Field("harga") String harga,
                                              @Field("kapasitas") String kapasitas);

    @FormUrlEncoded
    @POST("admin.php")
    Call<PesananModel> deleteMenuAdmin(@Field("deleteMenuAdmin") String deleteMenuAdmin,
                                       @Field("id_menu") String id_menu);

    @FormUrlEncoded
    @POST("admin.php")
    Call<PostUserModel> postTambahAkun(@Field("tambahAkun") String tambahAkun,
                                     @Field("nama") String nama,
                                     @Field("nomor_wa") String nomor_wa,
                                     @Field("email") String email,
                                     @Field("password") String password,
                                     @Field("sebagai") String sebagai,
                                     @Field("shift") String shift);

    @FormUrlEncoded
    @POST("admin.php")
    Call<PostUserModel> postUpdateAkun(@Field("updateAkun") String updateAkun,
                                       @Field("id_user") String id_user,
                                       @Field("nama") String nama,
                                       @Field("nomor_wa") String nomor_wa,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("sebagai") String sebagai,
                                       @Field("shift") String shift);

}

