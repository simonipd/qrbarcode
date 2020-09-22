package com.example.qrbarcode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnEscaneo;
    private TextView txtQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtQr = findViewById(R.id.txtQr);
        btnEscaneo = findViewById(R.id.btnEscaneo);
        btnEscaneo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        scanCode();
    }


    public void scanCode(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Escaneando código");
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentResult != null){
              if(intentResult.getContents() != null){
                  setImagenView(intentResult);
                  txtQr.setVisibility(View.VISIBLE);
                  final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                  builder.setMessage(intentResult.getContents());
                  builder.setTitle("Aviso");
                  builder.setPositiveButton("Volver a Escanear", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          scanCode();
                      }
                  }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });
                  final AlertDialog alertDialog = builder.create();
                  alertDialog.show();
              }else{

              }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void setImagenView(IntentResult intentResult){
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(intentResult.getContents(), BarcodeFormat.QR_CODE, 400, 400);
            ImageView imageViewQrCode = (ImageView) findViewById(R.id.qrCode);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch(Exception e) {

        }
    }

}