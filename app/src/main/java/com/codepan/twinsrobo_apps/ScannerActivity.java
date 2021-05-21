package com.codepan.twinsrobo_apps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.util.Scanner;

public class ScannerActivity extends AppCompatActivity {

    private CodeScanner codeScanner;
    private CodeScannerView codeScannerView;
    private TextView tvScannerResult;
    private Button btnTryScannAgain, btnTakeScannResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        codeScannerView = findViewById(R.id.scanner_view);
        tvScannerResult = findViewById(R.id.tvScannerResult);
        btnTryScannAgain = findViewById(R.id.btnTryScannAgain);
        btnTakeScannResult = findViewById(R.id.btnTakeScannResult);
        codeScanner = new CodeScanner(this, codeScannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvScannerResult.setText(result.getText());
                    }
                });
            }
        });

        btnTryScannAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
                tvScannerResult.setText("Scanning...");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvScannerResult.setText("Scanning...");
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}