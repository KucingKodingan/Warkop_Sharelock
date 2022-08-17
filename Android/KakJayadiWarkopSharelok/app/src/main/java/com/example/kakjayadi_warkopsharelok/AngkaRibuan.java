package com.example.kakjayadi_warkopsharelok;

import android.util.Log;

public class AngkaRibuan {
    String angkaFinal;
    public void setAngkaRibuan(int panjang, String angka){
        String arrayAngka[] = new String[panjang];

        String hargaSementara;
        int angkaAwal = 0;
        int angkaAkhir = 0;
        for(int i=0; i<panjang; i++){
            angkaAwal = panjang-(i+1);
            angkaAkhir = panjang-(i);
            hargaSementara = angka.substring(angkaAwal, angkaAkhir);

            arrayAngka[i] = hargaSementara;
        }

        String hargaRupiah = "Rp.";
        double hitung = arrayAngka.length;
        for (int i = arrayAngka.length-1; i >= 0; i--){
            hargaRupiah+=arrayAngka[i];

            if(hitung/4 == 1.0){
                hargaRupiah+=".";
            }
            hitung--;
        }
        angkaFinal = hargaRupiah;
    }
    public String getAngkaRibuan(){
        return angkaFinal;
    }
}
