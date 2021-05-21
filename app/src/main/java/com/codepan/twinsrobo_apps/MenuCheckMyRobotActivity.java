package com.codepan.twinsrobo_apps;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuCheckMyRobotActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etBitFS, etBitRS, etLebarGaris, etJenisMotor, etKP, etKD, etKI, etBandingkanKiri, etBandingkanKanan;
    private ImageView ivBackArrowCR, ivDeviceConnectIndicatorCheck;
    private TextView tvDeviceConnectIndicatorCheck;
    private Button btnConnectMyRobotCheck;
    private RadioButton rbMaju, rbMundur, rbPutarKiri, rbPutarKanan, rbDiam;
    private RadioButton rbFS1, rbFS2, rbFS3, rbFS4, rbFS5, rbFS6, rbFS7, rbFS8;
    private RadioButton rbRS1, rbRS2, rbRS3, rbRS4, rbRS5, rbRS6, rbRS7, rbRS8;

    private boolean[] FS = new boolean[8];
    private boolean[] RS = new boolean[8];
    private String cekMotorMode = "Diam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_check_my_robot);

        ivBackArrowCR = findViewById(R.id.ivBackArrowCR);
        ivDeviceConnectIndicatorCheck = findViewById(R.id.ivDeviceConnectIndicatorCheck);
        tvDeviceConnectIndicatorCheck = findViewById(R.id.tvDeviceConnectIndicatorCheck);
        btnConnectMyRobotCheck = findViewById(R.id.btnConnectMyRobotCheck);
        etBitFS = findViewById(R.id.etBitFS);
        etBitRS = findViewById(R.id.etBitRS);
        etLebarGaris = findViewById(R.id.etLebarGaris);
        etJenisMotor = findViewById(R.id.etJenisMotor);
        etKP = findViewById(R.id.etKP);
        etKD = findViewById(R.id.etKD);
        etKI = findViewById(R.id.etKI);
        etBandingkanKiri = findViewById(R.id.etBandingkanKiri);
        etBandingkanKanan = findViewById(R.id.etBandingkanKanan);
        rbMaju = findViewById(R.id.rbMaju); rbMaju.setOnClickListener(this);
        rbMundur = findViewById(R.id.rbMundur); rbMundur.setOnClickListener(this);
        rbPutarKiri = findViewById(R.id.rbPutarKiri); rbPutarKiri.setOnClickListener(this);
        rbPutarKanan = findViewById(R.id.rbPutarKanan); rbPutarKanan.setOnClickListener(this);
        rbDiam = findViewById(R.id.rbDiam); rbDiam.setOnClickListener(this);
        rbFS1 = findViewById(R.id.rbFS1); rbFS1.setOnClickListener(this);
        rbFS2 = findViewById(R.id.rbFS2); rbFS2.setOnClickListener(this);
        rbFS3 = findViewById(R.id.rbFS3); rbFS3.setOnClickListener(this);
        rbFS4 = findViewById(R.id.rbFS4); rbFS4.setOnClickListener(this);
        rbFS5 = findViewById(R.id.rbFS5); rbFS5.setOnClickListener(this);
        rbFS6 = findViewById(R.id.rbFS6); rbFS6.setOnClickListener(this);
        rbFS7 = findViewById(R.id.rbFS7); rbFS7.setOnClickListener(this);
        rbFS8 = findViewById(R.id.rbFS8); rbFS8.setOnClickListener(this);
        rbRS1 = findViewById(R.id.rbRS1); rbRS1.setOnClickListener(this);
        rbRS2 = findViewById(R.id.rbRS2); rbRS2.setOnClickListener(this);
        rbRS3 = findViewById(R.id.rbRS3); rbRS3.setOnClickListener(this);
        rbRS4 = findViewById(R.id.rbRS4); rbRS4.setOnClickListener(this);
        rbRS5 = findViewById(R.id.rbRS5); rbRS5.setOnClickListener(this);
        rbRS6 = findViewById(R.id.rbRS6); rbRS6.setOnClickListener(this);
        rbRS7 = findViewById(R.id.rbRS7); rbRS7.setOnClickListener(this);
        rbRS8 = findViewById(R.id.rbRS8); rbRS8.setOnClickListener(this);

        if (savedInstanceState != null) {
            FS = savedInstanceState.getBooleanArray("valueFS");
            RS = savedInstanceState.getBooleanArray("valueRS");
            cekMotorMode = savedInstanceState.getString("cekMotorMode");

            etBitFS.setText(savedInstanceState.getString("valueBitFS"));
            etBitRS.setText(savedInstanceState.getString("valueBitRS"));
            etLebarGaris.setText(savedInstanceState.getString("valueLebarGaris"));
            etJenisMotor.setText(savedInstanceState.getString("valueJenisMotor"));
            etKP.setText(savedInstanceState.getString("valueKP"));
            etKD.setText(savedInstanceState.getString("valueKD"));
            etKI.setText(savedInstanceState.getString("valueKI"));
            etBandingkanKiri.setText(savedInstanceState.getString("valueBandingkanKiri"));
            etBandingkanKanan.setText(savedInstanceState.getString("alueBandingkanKanan"));
        } else {
            for (int i = 0; i < FS.length; i++) {
                FS[i] = false;
            }
            for (int i = 0; i < RS.length; i++) {
                RS[i] = false;
            }
        }

        ivBackArrowCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnConnectMyRobotCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvDeviceConnectIndicatorCheck.getText().toString().equals("Disconnected")){
                    ivDeviceConnectIndicatorCheck.setImageResource(R.drawable.ic_filled_circle_green);
                    tvDeviceConnectIndicatorCheck.setText("Connected");
                }
                else {
                    ivDeviceConnectIndicatorCheck.setImageResource(R.drawable.ic_filled_circle_red);
                    tvDeviceConnectIndicatorCheck.setText("Disconnected");
                }
            }
        });
    }

    private void reloadCekMotor() {
        rbMaju.setSelected(false);
        rbMaju.setChecked(false);
        rbMundur.setSelected(false);
        rbMundur.setChecked(false);
        rbPutarKiri.setSelected(false);
        rbPutarKiri.setChecked(false);
        rbPutarKanan.setSelected(false);
        rbPutarKanan.setChecked(false);
        rbDiam.setSelected(false);
        rbDiam.setChecked(false);
        switch (cekMotorMode) {
            case "Maju":
                rbMaju.setSelected(true);
                rbMaju.setChecked(true);
                break;
            case "Mundur":
                rbMundur.setSelected(true);
                rbMundur.setChecked(true);
                break;
            case "PutarKiri":
                rbPutarKiri.setSelected(true);
                rbPutarKiri.setChecked(true);
                break;
            case "PutarKanan":
                rbPutarKanan.setSelected(true);
                rbPutarKanan.setChecked(true);
                break;
            case "Diam":
                rbDiam.setSelected(true);
                rbDiam.setChecked(true);
                break;
        }
    }

    private void reloadFrontSensorState() {
        rbFS1.setSelected(FS[0]);
        rbFS1.setChecked(FS[0]);
        rbFS2.setSelected(FS[1]);
        rbFS2.setChecked(FS[1]);
        rbFS3.setSelected(FS[2]);
        rbFS3.setChecked(FS[2]);
        rbFS4.setSelected(FS[3]);
        rbFS4.setChecked(FS[3]);
        rbFS5.setSelected(FS[4]);
        rbFS5.setChecked(FS[4]);
        rbFS6.setSelected(FS[5]);
        rbFS6.setChecked(FS[5]);
        rbFS7.setSelected(FS[6]);
        rbFS7.setChecked(FS[6]);
        rbFS8.setSelected(FS[7]);
        rbFS8.setChecked(FS[7]);
    }

    private void reloadRearSensorState() {
        rbRS1.setSelected(RS[0]);
        rbRS1.setChecked(RS[0]);
        rbRS2.setSelected(RS[1]);
        rbRS2.setChecked(RS[1]);
        rbRS3.setSelected(RS[2]);
        rbRS3.setChecked(RS[2]);
        rbRS4.setSelected(RS[3]);
        rbRS4.setChecked(RS[3]);
        rbRS5.setSelected(RS[4]);
        rbRS5.setChecked(RS[4]);
        rbRS6.setSelected(RS[5]);
        rbRS6.setChecked(RS[5]);
        rbRS7.setSelected(RS[6]);
        rbRS7.setChecked(RS[6]);
        rbRS8.setSelected(RS[7]);
        rbRS8.setChecked(RS[7]);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rbMaju
                || view.getId() == R.id.rbMundur
                || view.getId() == R.id.rbPutarKiri
                || view.getId() == R.id.rbPutarKanan
                || view.getId() == R.id.rbDiam) {
            rbMaju.setSelected(false);
            rbMaju.setChecked(false);
            rbMundur.setSelected(false);
            rbMundur.setChecked(false);
            rbPutarKiri.setSelected(false);
            rbPutarKiri.setChecked(false);
            rbPutarKanan.setSelected(false);
            rbPutarKanan.setChecked(false);
            rbDiam.setSelected(false);
            rbDiam.setChecked(false);
        }
        switch (view.getId()) {
            case R.id.rbMaju:
                rbMaju.setSelected(true);
                rbMaju.setChecked(true);
                cekMotorMode = "Maju";
                break;
            case R.id.rbMundur:
                rbMundur.setSelected(true);
                rbMundur.setChecked(true);
                cekMotorMode = "Mundur";
                break;
            case R.id.rbPutarKiri:
                rbPutarKiri.setSelected(true);
                rbPutarKiri.setChecked(true);
                cekMotorMode = "PutarKiri";
                break;
            case R.id.rbPutarKanan:
                rbPutarKanan.setSelected(true);
                rbPutarKanan.setChecked(true);
                cekMotorMode = "PutarKanan";
                break;
            case R.id.rbDiam:
                rbDiam.setSelected(true);
                rbDiam.setChecked(true);
                cekMotorMode = "Diam";
                break;
        }

        switch (view.getId()) {
            case R.id.rbFS1:
                if (rbFS1.isSelected()) {
                    FS[0] = false;
                    rbFS1.setSelected(false);
                    rbFS1.setChecked(false);
                } else {
                    FS[0] = true;
                    rbFS1.setSelected(true);
                    rbFS1.setChecked(true);
                }
                break;
            case R.id.rbFS2:
                if (rbFS2.isSelected()) {
                    FS[1] = false;
                    rbFS2.setSelected(false);
                    rbFS2.setChecked(false);
                } else {
                    FS[1] = true;
                    rbFS2.setSelected(true);
                    rbFS2.setChecked(true);
                }
                break;
            case R.id.rbFS3:
                if (rbFS3.isSelected()) {
                    FS[2] = false;
                    rbFS3.setSelected(false);
                    rbFS3.setChecked(false);
                } else {
                    FS[2] = true;
                    rbFS3.setSelected(true);
                    rbFS3.setChecked(true);
                }
                break;
            case R.id.rbFS4:
                if (rbFS4.isSelected()) {
                    FS[3] = false;
                    rbFS4.setSelected(false);
                    rbFS4.setChecked(false);
                } else {
                    FS[3] = true;
                    rbFS4.setSelected(true);
                    rbFS4.setChecked(true);
                }
                break;
            case R.id.rbFS5:
                if (rbFS5.isSelected()) {
                    FS[4] = false;
                    rbFS5.setSelected(false);
                    rbFS5.setChecked(false);
                } else {
                    FS[4] = true;
                    rbFS5.setSelected(true);
                    rbFS5.setChecked(true);
                }
                break;
            case R.id.rbFS6:
                if (rbFS6.isSelected()) {
                    FS[5] = false;
                    rbFS6.setSelected(false);
                    rbFS6.setChecked(false);
                } else {
                    FS[5] = true;
                    rbFS6.setSelected(true);
                    rbFS6.setChecked(true);
                }
                break;
            case R.id.rbFS7:
                if (rbFS7.isSelected()) {
                    FS[6] = false;
                    rbFS7.setSelected(false);
                    rbFS7.setChecked(false);
                } else {
                    FS[6] = true;
                    rbFS7.setSelected(true);
                    rbFS7.setChecked(true);
                }
                break;
            case R.id.rbFS8:
                if (rbFS8.isSelected()) {
                    FS[7] = false;
                    rbFS8.setSelected(false);
                    rbFS8.setChecked(false);
                } else {
                    FS[7] = true;
                    rbFS8.setSelected(true);
                    rbFS8.setChecked(true);
                }
                break;
        }

        switch (view.getId()) {
            case R.id.rbRS1:
                if (rbRS1.isSelected()) {
                    RS[0] = false;
                    rbRS1.setSelected(false);
                    rbRS1.setChecked(false);
                } else {
                    RS[0] = true;
                    rbRS1.setSelected(true);
                    rbRS1.setChecked(true);
                }
                break;
            case R.id.rbRS2:
                if (rbRS2.isSelected()) {
                    RS[1] = false;
                    rbRS2.setSelected(false);
                    rbRS2.setChecked(false);
                } else {
                    RS[1] = true;
                    rbRS2.setSelected(true);
                    rbRS2.setChecked(true);
                }
                break;
            case R.id.rbRS3:
                if (rbRS3.isSelected()) {
                    RS[2] = false;
                    rbRS3.setSelected(false);
                    rbRS3.setChecked(false);
                } else {
                    RS[2] = true;
                    rbRS3.setSelected(true);
                    rbRS3.setChecked(true);
                }
                break;
            case R.id.rbRS4:
                if (rbRS4.isSelected()) {
                    RS[3] = false;
                    rbRS4.setSelected(false);
                    rbRS4.setChecked(false);
                } else {
                    RS[3] = true;
                    rbRS4.setSelected(true);
                    rbRS4.setChecked(true);
                }
                break;
            case R.id.rbRS5:
                if (rbRS5.isSelected()) {
                    RS[4] = false;
                    rbRS5.setSelected(false);
                    rbRS5.setChecked(false);
                } else {
                    RS[4] = true;
                    rbRS5.setSelected(true);
                    rbRS5.setChecked(true);
                }
                break;
            case R.id.rbRS6:
                if (rbRS6.isSelected()) {
                    RS[5] = false;
                    rbRS6.setSelected(false);
                    rbRS6.setChecked(false);
                } else {
                    RS[5] = true;
                    rbRS6.setSelected(true);
                    rbRS6.setChecked(true);
                }
                break;
            case R.id.rbRS7:
                if (rbRS7.isSelected()) {
                    RS[6] = false;
                    rbRS7.setSelected(false);
                    rbRS7.setChecked(false);
                } else {
                    RS[6] = true;
                    rbRS7.setSelected(true);
                    rbRS7.setChecked(true);
                }
                break;
            case R.id.rbRS8:
                if (rbRS8.isSelected()) {
                    RS[7] = false;
                    rbRS8.setSelected(false);
                    rbRS8.setChecked(false);
                } else {
                    RS[7] = true;
                    rbRS8.setSelected(true);
                    rbRS8.setChecked(true);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        reloadCekMotor();

        reloadFrontSensorState();

        reloadRearSensorState();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBooleanArray("valueFS", FS);
        outState.putBooleanArray("valueRS", RS);
        outState.putString("cekMotorMode", cekMotorMode);
        outState.putString("valueBitFS", String.valueOf(etBitFS));
        outState.putString("valueBitRS", String.valueOf(etBitRS));
        outState.putString("valueLebarGaris", String.valueOf(etLebarGaris));
        outState.putString("valueJenisMotor", String.valueOf(etJenisMotor));
        outState.putString("valueKP", String.valueOf(etKP));
        outState.putString("valueKD", String.valueOf(etKI));
        outState.putString("valueKI", String.valueOf(etKD));
        outState.putString("valueBandingkanKiri", String.valueOf(etBandingkanKiri));
        outState.putString("valueBandingkanKanan", String.valueOf(etBandingkanKanan));
    }
}