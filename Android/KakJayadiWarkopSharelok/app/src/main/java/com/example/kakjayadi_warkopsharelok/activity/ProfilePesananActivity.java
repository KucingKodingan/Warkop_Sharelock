package com.example.kakjayadi_warkopsharelok.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakjayadi_warkopsharelok.AngkaRibuan;
import com.example.kakjayadi_warkopsharelok.R;
import com.example.kakjayadi_warkopsharelok.adapter.PesananAdapter;
import com.example.kakjayadi_warkopsharelok.data.api.ApiService;
import com.example.kakjayadi_warkopsharelok.data.model.PesananModel;
import com.example.kakjayadi_warkopsharelok.util.LoadingAlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePesananActivity extends Activity {

    static final String TAG = "ProfilePesanan";

    ImageView btn_back_profile_pesanan;
    TextView tvBelumBayar;
    Button btnBayar;
    RecyclerView recyclerViewProfilePesanan;
    String id_pesanan, email, keterangan, nama;
    PesananAdapter pesananAdapter;

    LinearLayout llPdf, llPerhatian;

    Button btnDownloadPdf;
//    Button btnPrintPdf;

    SharedPreferences sp;
    String SHAREDPREFERENCES_CEK_SEBAGAI, SHAREDPREFERENCES_CEK, SHAREDPREFERENCES_CEK_NAMA_KASIR;

    //Loading
    LoadingAlertDialog loading = new LoadingAlertDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pesanan);

        Bundle bundle = getIntent().getExtras();
        id_pesanan = bundle.getString("id_pesanan");
        email = bundle.getString("email");
        keterangan = bundle.getString("keterangan");
        nama = bundle.getString("nama");

        btn_back_profile_pesanan = findViewById(R.id.btn_back_profile_pesanan);
        tvBelumBayar = findViewById(R.id.tvBelumBayar);
        btnBayar = findViewById(R.id.btnBayar);
        recyclerViewProfilePesanan = findViewById(R.id.recyclerViewProfilePesanan);
        btnDownloadPdf = findViewById(R.id.btnDownloadPdf);
//        btnPrintPdf = findViewById(R.id.btnPrintPdf);
        llPdf = findViewById(R.id.llPdf);
        llPerhatian = findViewById(R.id.llPerhatian);

        sp = getSharedPreferences("SHAREDPREFERENCES_LOGIN", MODE_PRIVATE);
        SHAREDPREFERENCES_CEK_SEBAGAI = sp.getString("SEBAGAI", null);
        SHAREDPREFERENCES_CEK = sp.getString("EMAIL", null);
        SHAREDPREFERENCES_CEK_NAMA_KASIR = sp.getString("NAMA_KASIR", null);

        if(SHAREDPREFERENCES_CEK_SEBAGAI.equals("kasir")){
            btnBayar.setVisibility(View.VISIBLE);
            tvBelumBayar.setVisibility(View.GONE);
            llPerhatian.setVisibility(View.GONE);
//            btnPrintPdf.setVisibility(View.VISIBLE);
        }
        else if(SHAREDPREFERENCES_CEK_SEBAGAI.equals("pelanggan")){
            btnBayar.setVisibility(View.GONE);
            tvBelumBayar.setVisibility(View.VISIBLE);
            llPerhatian.setVisibility(View.VISIBLE);
//            btnPrintPdf.setVisibility(View.GONE);
        }
        else if(SHAREDPREFERENCES_CEK_SEBAGAI.equals("admin")){
            btnBayar.setVisibility(View.GONE);
            tvBelumBayar.setVisibility(View.VISIBLE);
            llPerhatian.setVisibility(View.GONE);
//            btnPrintPdf.setVisibility(View.VISIBLE);
        }

        if(keterangan.equals("Belum Bayar")){
            llPdf.setVisibility(View.GONE);
        }
        else if(keterangan.equals("Sudah Bayar")){
            llPdf.setVisibility(View.VISIBLE);
            tvBelumBayar.setText(keterangan);
            tvBelumBayar.setVisibility(View.VISIBLE);
            btnBayar.setVisibility(View.GONE);
            llPerhatian.setVisibility(View.GONE);
        }

        btn_back_profile_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.alertDialogLoading(ProfilePesananActivity.this);
                uppdateBayarPesanan(id_pesanan, SHAREDPREFERENCES_CEK, SHAREDPREFERENCES_CEK_NAMA_KASIR);
            }
        });
        btnDownloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.alertDialogLoading(ProfilePesananActivity.this);
                createPDF();
            }
        });

        tvBelumBayar.setText(keterangan);

        getPesanan(id_pesanan, email);
    }

    ArrayList<PesananModel> pesanan;
    private void getPesanan(String id_pesanan, String email){
        ApiService.getApiConfig().getProfilePesanan("profile_pesanan", id_pesanan, email)
                .enqueue(new Callback<ArrayList<PesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PesananModel>> call, Response<ArrayList<PesananModel>> response) {
                        Log.d(TAG, "onResponse: "+response.body());

                        pesananAdapter = new PesananAdapter(response.body());
                        pesanan = response.body();

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProfilePesananActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewProfilePesanan.setLayoutManager(layoutManager);
                        recyclerViewProfilePesanan.setAdapter(pesananAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PesananModel>> call, Throwable t) {

                    }
                });
    }

    private void uppdateBayarPesanan(String id_pesanan, String email, String NAMA_KASIR){
        ApiService.getApiConfig().updateBayarPesanan("updateBayarPesanan",id_pesanan, email, NAMA_KASIR)
                .enqueue(new Callback<PesananModel>() {
                    @Override
                    public void onResponse(Call<PesananModel> call, Response<PesananModel> response) {
                        finish();
                    }

                    @Override
                    public void onFailure(Call<PesananModel> call, Throwable t) {
                        Toast.makeText(ProfilePesananActivity.this, "Berhasil Bayar", Toast.LENGTH_SHORT).show();
                        loading.alertDialogCancel();
                        finish();
                    }
                });
    }

    int pageWidth;

    private void createPDF(){

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

//        atas 115
//        bawah 100
//
//        batas 50

        int pageHeight = 0;
        for(int a=0; a<pesanan.size(); a++){
            pageHeight+=45;
        }

        pageHeight+=(115+75);

        pageWidth = 300;

        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(300, pageHeight,1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        //Title
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(15f);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Warkop Sharelock", pageWidth/2,35, titlePaint);

        titlePaint.setTextSize(12f);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText("Jl. Muspika Industri Kecil, Parepare", pageWidth/2, 55, titlePaint);
        canvas.drawText("Telp. 085951134830 (wa)", pageWidth/2, 72, titlePaint);
        canvas.drawText(""+pesanan.get(0).getTgl_pesanan(), pageWidth/2, 87, titlePaint);

        canvas.drawLine(40, 93, 260 , 93, titlePaint);
        canvas.drawLine(40, 95, 260 , 95, titlePaint);

        //Body
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
        paint.setColor(Color.BLACK);
        paint.setTextSize(11f);
        int yBodyNamaPesanan = 80;
        int yBodyJumlahPesanan=0;
        int totalHargaPesanan = 0;
        AngkaRibuan obj = new AngkaRibuan();
        for(int a=0; a<pesanan.size(); a++){
            yBodyNamaPesanan+=35;
            yBodyJumlahPesanan = yBodyNamaPesanan+15;

            totalHargaPesanan+=Integer.parseInt(pesanan.get(a).getTotal());

            canvas.drawText(""+pesanan.get(a).getMenu(), 45, yBodyNamaPesanan, paint);
            canvas.drawText(pesanan.get(a).getJumlah()+"x", 45, yBodyJumlahPesanan, paint);
            obj.setAngkaRibuan(pesanan.get(a).getHarga().length(), pesanan.get(a).getHarga());
            canvas.drawText("@"+obj.getAngkaRibuan(), 90, yBodyJumlahPesanan, paint);
            obj.setAngkaRibuan(pesanan.get(a).getTotal().length(), pesanan.get(a).getTotal());
            canvas.drawText(obj.getAngkaRibuan().toString(), 190, yBodyJumlahPesanan, paint);
        }

        canvas.drawLine(40, yBodyJumlahPesanan+15, 260 , yBodyJumlahPesanan+15, paint);
        obj.setAngkaRibuan(String.valueOf(totalHargaPesanan).length(), String.valueOf(totalHargaPesanan));
        canvas.drawText("Total Pesanan ", 45, yBodyJumlahPesanan+35, paint);
        canvas.drawText(""+obj.getAngkaRibuan(), 190, yBodyJumlahPesanan+35, paint);

        canvas.drawLine(40, yBodyJumlahPesanan+48, 260 , yBodyJumlahPesanan+48, paint);
        canvas.drawLine(40, yBodyJumlahPesanan+50, 260 , yBodyJumlahPesanan+50, paint);

        canvas.drawText("Terima Kasih", pageWidth/2, yBodyJumlahPesanan+70, titlePaint);
        canvas.drawText("Kasir : "+pesanan.get(0).getKasir(), pageWidth/2, yBodyJumlahPesanan+88, titlePaint);


        pdfDocument.finishPage(page);

        String tanggal = pesanan.get(0).getTgl_pesanan().replace(":",".");

        File file = new File(Environment.getExternalStorageDirectory(),
                "/Download/"+nama+"-"+tanggal+".pdf");
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            loading.alertDialogCancel();
            Toast.makeText(this, "PDF disimpan di /Download/"+nama+"-"+tanggal+".pdf", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Salah Pada Bagian : "+e, Toast.LENGTH_SHORT).show();
        }

        pdfDocument.close();
    }


//    //
//    BluetoothAdapter mBluetoothAdapter;
//    BluetoothSocket mmSocket;
//    BluetoothDevice mmDevice;
//
//    // needed for communication to bluetooth device / network
//    OutputStream mmOutputStream;
//    InputStream mmInputStream;
//    Thread workerThread;
//
//    byte[] readBuffer;
//    int readBufferPosition;
//    volatile boolean stopWorker;
//    byte FONT_TYPE;
//
//    public static String printer_id;
//
//
//    public void findBT() {
//        System.out.println("Printer ID : "+printer_id);
//        try {
//            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            if(mBluetoothAdapter == null) {
//                Toast.makeText(this, "Device Bluetooth tidak Tersedia", Toast.LENGTH_SHORT).show();
//            }
//            if(!mBluetoothAdapter.isEnabled()) {
//                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBluetooth, 0);
//            }
//            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//            if(pairedDevices.size() > 0) {
//                for (BluetoothDevice device : pairedDevices) {
//                    if (device.getName().equals(printer_id)) {
//                        mmDevice = device;
//                        break;
//                    }
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    // tries to open a connection to the bluetooth printer device
//    public void openBT() throws IOException {
//        try {
//            // Standard SerialPortService ID
//            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
//            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
//            mmSocket.connect();
//            mmOutputStream = mmSocket.getOutputStream();
//            mmInputStream = mmSocket.getInputStream();
//            beginListenForData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void printQrCode(Bitmap qRBit) {
//        try {
//            PrintPic printPic1 = PrintPic.getInstance();
//            printPic1.init(qRBit);
//            byte[] bitmapdata2 = printPic1.printDraw();
//            mmOutputStream.write(bitmapdata2);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void printText(String text1, String text2, String text3){
//        try {
//            String text = "---------------------\n";
//            text += "Title  : "+text1+"\n";
//            text += "Encrypt: "+text2+"\n";
//            text += "Date   : "+text3+"\n";
//            mmOutputStream.write(text.getBytes());
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void printStruk(String nama_produk, String pengiriman, String Date){
//        System.out.println("Nama Produk: "+nama_produk);
//        System.out.println("Barang: "+pengiriman);
//        System.out.println("Date: "+Date);
//        try {
//            String text = "---------------------\n";
//            text += "Produk : "+nama_produk+"\n";
//            text += "Barang : "+pengiriman+"\n";
//            text += "Tanggal: "+Date+"\n";
//            mmOutputStream.write(text.getBytes());
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//
//    /*
//     * after opening a connection to bluetooth printer device,
//     * we have to listen and check if a data were sent to be printed.
//     */
//    public void beginListenForData() {
//        try {
//            final Handler handler = new Handler();
//            // this is the ASCII code for a newline character
//            final byte delimiter = 10;
//            stopWorker = false;
//            readBufferPosition = 0;
//            readBuffer = new byte[1024];
//            workerThread = new Thread(new Runnable() {
//                public void run() {
//                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {
//                        try {
//                            int bytesAvailable = mmInputStream.available();
//                            if (bytesAvailable > 0) {
//                                byte[] packetBytes = new byte[bytesAvailable];
//                                mmInputStream.read(packetBytes);
//                                for (int i = 0; i < bytesAvailable; i++) {
//                                    byte b = packetBytes[i];
//                                    if (b == delimiter) {
//                                        byte[] encodedBytes = new byte[readBufferPosition];
//                                        System.arraycopy(
//                                                readBuffer, 0,
//                                                encodedBytes, 0,
//                                                encodedBytes.length
//                                        );
//                                        // specify US-ASCII encoding
//                                        final String data = new String(encodedBytes, "US-ASCII");
//                                        readBufferPosition = 0;
//                                        // tell the user data were sent to bluetooth printer device
//                                        handler.post(new Runnable() {
//                                            public void run() {
////                                                myLabel.setText(data);
//                                            }
//                                        });
//                                    } else {
//                                        readBuffer[readBufferPosition++] = b;
//                                    }
//                                }
//                            }
//                        } catch (IOException ex) {
//                            stopWorker = true;
//                        }
//                    }
//                }
//            });
//            workerThread.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    //    this will update data printer name in ModelUser
//    // close the connection to bluetooth printer.
//    public void closeBT() throws IOException {
//        try {
//            stopWorker = true;
//            mmOutputStream.close();
//            mmInputStream.close();
//            mmSocket.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



}