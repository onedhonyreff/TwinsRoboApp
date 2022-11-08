package com.codepan.twinsrobo_apps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.codepan.twinsrobo_apps.OtherClass.DataHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MenuProgramRobotActivity extends AppCompatActivity implements View.OnClickListener{

    public int REQUEST_CODE = 838;

    private InputMethodManager imm;

    private ImageView ivBackArrowPR, ivDeviceConnectIndicator;
    private TextView tvDeviceConnectIndicator, tvProgramPreview, tvLineNumberProgramPreview;
    private EditText etKecepatanJalan, etBreakJalan, etKecepatanBelok, etBreakBelok, etLineDelay, etSline, etMotorSpeed, etLineP, etDelay, etAutoDelay, etLeft, etRight, etGripper, etSetUp, etSetDown, etInsertAboveLine, etRemoveLineProgram;
    private Button btnPlanPage, btnConnectRobot, btnRemoveProgramScript, btnInsertProgramAppend, btnUploadProgramScript, btnRemoveLineProgram;
    private CheckBox cbLineColor, cbAutoDelay, cbInsertAboveLine;
    private RadioButton rbLineFL, rbLineFR, rbLineFF, rbLineSL, rbLineSR, rbLineSF, rbFindLine, rbLossLine, rbSS, rbLineDelay,
            rbSline, rbMotorSpeed, rbLineColor, rbLineP, rbDelay, rbRightWay, rbLeftWay, rbMidWay, rbSensorFront, rbSensorRear,
            rbLeft, rbRight, rbNext, rbGripDown, rbGripUp, rbGripper, rbSetUp, rbSetDown, rbSetJump, rbJump;

    private int selectedRadioButton = -1;
    private int activeRadioButton = R.id.rbLineFL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_program_robot);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        ivBackArrowPR = findViewById(R.id.ivBackArrowPR);
        ivDeviceConnectIndicator = findViewById(R.id.ivDeviceConnectIndicator);
        tvDeviceConnectIndicator = findViewById(R.id.tvDeviceConnectIndicator);
        tvProgramPreview = findViewById(R.id.tvProgramPreview);
        tvLineNumberProgramPreview = findViewById(R.id.tvLineNumberProgramPreview);
        btnPlanPage = findViewById(R.id.btnPlanPage);
        btnConnectRobot = findViewById(R.id.btnConnectRobot);
        btnRemoveProgramScript = findViewById(R.id.btnRemoveProgramScript);
        btnInsertProgramAppend = findViewById(R.id.btnInsertProgramAppend);
        btnUploadProgramScript = findViewById(R.id.btnUploadProgramScript);
        btnRemoveLineProgram = findViewById(R.id.btnRemoveLineProgram);
        etKecepatanJalan = findViewById(R.id.etKecepatanJalan);
        etKecepatanBelok = findViewById(R.id.etKecepatanBelok);
        etBreakJalan = findViewById(R.id.etBreakJalan);
        etBreakBelok = findViewById(R.id.etBreakBelok);
        etLineP = findViewById(R.id.etLineP);
        etLineDelay = findViewById(R.id.etLineDelay);
        etSline = findViewById(R.id.etSline);
        etMotorSpeed = findViewById(R.id.etMotorSpeed);
        etDelay = findViewById(R.id.etDelay);
        etGripper = findViewById(R.id.etGripper);
        etAutoDelay = findViewById(R.id.etAutoDelay);
        etInsertAboveLine = findViewById(R.id.etInsertAboveLine);
        etRemoveLineProgram = findViewById(R.id.etRemoveLineProgram);
        etLeft = findViewById(R.id.etLeft);
        etRight = findViewById(R.id.etRight);
        etSetUp = findViewById(R.id.etSetUp);
        etSetDown = findViewById(R.id.etSetDown);

        cbLineColor = findViewById(R.id.cbLineColor);
        cbAutoDelay = findViewById(R.id.cbAutoDelay);
        cbInsertAboveLine = findViewById(R.id.cbInsertAboveLine);

        rbLineFL = findViewById(R.id.rbLineFL); rbLineFL.setOnClickListener(this);
        rbLineFR = findViewById(R.id.rbLineFR); rbLineFR.setOnClickListener(this);
        rbLineFF = findViewById(R.id.rbLineFF); rbLineFF.setOnClickListener(this);
        rbLineSL = findViewById(R.id.rbLineSL); rbLineSL.setOnClickListener(this);
        rbLineSR = findViewById(R.id.rbLineSR); rbLineSR.setOnClickListener(this);
        rbLineSF = findViewById(R.id.rbLineSF); rbLineSF.setOnClickListener(this);
        rbFindLine = findViewById(R.id.rbFindLine); rbFindLine.setOnClickListener(this);
        rbLossLine = findViewById(R.id.rbLossLine); rbLossLine.setOnClickListener(this);
        rbSS = findViewById(R.id.rbSS); rbSS.setOnClickListener(this);
        rbLineDelay = findViewById(R.id.rbLineDelay); rbLineDelay.setOnClickListener(this);
        rbSline = findViewById(R.id.rbSline); rbSline.setOnClickListener(this);
        rbMotorSpeed = findViewById(R.id.rbMotorSpeed); rbMotorSpeed.setOnClickListener(this);
        rbLineColor = findViewById(R.id.rbLineColor); rbLineColor.setOnClickListener(this);
        rbLineP = findViewById(R.id.rbLineP); rbLineP.setOnClickListener(this);
        rbDelay = findViewById(R.id.rbDelay); rbDelay.setOnClickListener(this);
        rbRightWay = findViewById(R.id.rbRightWay); rbRightWay.setOnClickListener(this);
        rbLeftWay = findViewById(R.id.rbLeftWay); rbLeftWay.setOnClickListener(this);
        rbMidWay = findViewById(R.id.rbMidWay); rbMidWay.setOnClickListener(this);
        rbSensorFront = findViewById(R.id.rbSensorFront); rbSensorFront.setOnClickListener(this);
        rbSensorRear = findViewById(R.id.rbSensorRear); rbSensorRear.setOnClickListener(this);
//        rbAutoDelay = findViewById(R.id.rbAutoDelay); rbAutoDelay.setOnClickListener(this);
        rbLeft = findViewById(R.id.rbLeft); rbLeft.setOnClickListener(this);
        rbRight = findViewById(R.id.rbRight); rbRight.setOnClickListener(this);
        rbNext = findViewById(R.id.rbNext); rbNext.setOnClickListener(this);
        rbGripDown = findViewById(R.id.rbGripDown); rbGripDown.setOnClickListener(this);
        rbGripUp = findViewById(R.id.rbGripUp); rbGripUp.setOnClickListener(this);
        rbGripper = findViewById(R.id.rbGripper); rbGripper.setOnClickListener(this);
        rbSetUp = findViewById(R.id.rbSetUp); rbSetUp.setOnClickListener(this);
        rbSetDown = findViewById(R.id.rbSetDown); rbSetDown.setOnClickListener(this);
        rbSetJump = findViewById(R.id.rbSetJump); rbSetJump.setOnClickListener(this);
        rbJump = findViewById(R.id.rbJump); rbJump.setOnClickListener(this);

        if(savedInstanceState != null){
            activeRadioButton = savedInstanceState.getInt("active_radio_button");
            tvProgramPreview.setText(savedInstanceState.getString("program_preview"));
            tvLineNumberProgramPreview.setText(savedInstanceState.getString("line_number_program_preview"));
            etKecepatanJalan.setText(savedInstanceState.getString("kecepatan_jalan"));
            etBreakJalan.setText(savedInstanceState.getString("break_jalan"));
            etKecepatanBelok.setText(savedInstanceState.getString("kecepatan_belok"));
            etBreakBelok.setText(savedInstanceState.getString("break_belok"));
            etLineDelay.setText(savedInstanceState.getString("line_delay"));
            etSline.setText(savedInstanceState.getString("s_line"));
            etMotorSpeed.setText(savedInstanceState.getString("motor_speed"));
            etLineP.setText(savedInstanceState.getString("line_p"));
            etDelay.setText(savedInstanceState.getString("delay"));
            etLeft.setText(savedInstanceState.getString("left"));
            etRight.setText(savedInstanceState.getString("right"));
            etGripper.setText(savedInstanceState.getString("gripper"));
            etSetUp.setText(savedInstanceState.getString("set_up"));
            etSetDown.setText(savedInstanceState.getString("set_down"));
            etAutoDelay.setText(savedInstanceState.getString("auto_delay_value"));
            etInsertAboveLine.setText(savedInstanceState.getString("insert_above_line_value"));
            etRemoveLineProgram.setText(savedInstanceState.getString("remove_line_program"));
            cbLineColor.setChecked(savedInstanceState.getBoolean("line_color"));
            cbAutoDelay.setChecked(savedInstanceState.getBoolean("auto_delay"));
            cbInsertAboveLine.setChecked(savedInstanceState.getBoolean("insert_above_line"));
        }

        setRadioButtonClear();
        setRadioButtonActive(activeRadioButton);

        ivBackArrowPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPlanPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(
                                MenuProgramRobotActivity.this,
                                RobotPlanActivity.class
                        ), REQUEST_CODE);
            }
        });

        btnConnectRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvDeviceConnectIndicator.getText().toString().equals("Disconnected")){
                    ivDeviceConnectIndicator.setImageResource(R.drawable.ic_filled_circle_green);
                    tvDeviceConnectIndicator.setText("Connected");
                }
                else {
                    ivDeviceConnectIndicator.setImageResource(R.drawable.ic_filled_circle_red);
                    tvDeviceConnectIndicator.setText("Disconnected");
                }
            }
        });

        btnInsertProgramAppend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedRadioButton != -1){
                    appendProgram(selectedRadioButton);
                }
            }
        });

        btnRemoveProgramScript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tvProgramPreview.getText().toString().equals("")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuProgramRobotActivity.this);

                    alertDialogBuilder.setTitle("Ingin Menghapus Script Program?");

                    alertDialogBuilder
                            .setMessage("Jika iya, script akan dihapus dan diset ulang.")
                            .setIcon(R.drawable.logo_apps_twins_robo)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    tvProgramPreview.setText("");
                                    tvLineNumberProgramPreview.setText("");
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    // membuat alert dialog dari builder
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alertDialog.show();
                }
            }
        });

        btnRemoveLineProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasiDeleteLine()){
                    reconstructionWhileDeletLine(Integer.parseInt(etRemoveLineProgram.getText().toString().trim()));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        setRadioButtonClear();
        setRadioButtonActive(view.getId());
        activeRadioButton = view.getId();
    }

    private void setRadioButtonClear(){
        rbLineFL.setSelected(false); rbLineFL.setChecked(false);
        rbLineFR.setSelected(false); rbLineFR.setChecked(false);
        rbLineFF.setSelected(false); rbLineFF.setChecked(false);
        rbLineSL.setSelected(false); rbLineSL.setChecked(false);
        rbLineSR.setSelected(false); rbLineSR.setChecked(false);
        rbLineSF.setSelected(false); rbLineSF.setChecked(false);
        rbFindLine.setSelected(false); rbFindLine.setChecked(false);
        rbLossLine.setSelected(false); rbLossLine.setChecked(false);
        rbSS.setSelected(false); rbSS.setChecked(false);
        rbLineDelay.setSelected(false); rbLineDelay.setChecked(false);
        rbSline.setSelected(false); rbSline.setChecked(false);
        rbMotorSpeed.setSelected(false); rbMotorSpeed.setChecked(false);
        rbLineColor.setSelected(false); rbLineColor.setChecked(false);
        rbLineP.setSelected(false); rbLineP.setChecked(false);
        rbDelay.setSelected(false); rbDelay.setChecked(false);
        rbRightWay.setSelected(false); rbRightWay.setChecked(false);
        rbLeftWay.setSelected(false); rbLeftWay.setChecked(false);
        rbMidWay.setSelected(false); rbMidWay.setChecked(false);
        rbSensorFront.setSelected(false); rbSensorFront.setChecked(false);
        rbSensorRear.setSelected(false); rbSensorRear.setChecked(false);
//        rbAutoDelay.setSelected(false); rbAutoDelay.setChecked(false);
        rbLeft.setSelected(false); rbLeft.setChecked(false);
        rbRight.setSelected(false); rbRight.setChecked(false);
        rbNext.setSelected(false); rbNext.setChecked(false);
        rbGripDown.setSelected(false); rbGripDown.setChecked(false);
        rbGripUp.setSelected(false); rbGripUp.setChecked(false);
        rbGripper.setSelected(false); rbGripper.setChecked(false);
        rbSetUp.setSelected(false); rbSetUp.setChecked(false);
        rbSetDown.setSelected(false); rbSetDown.setChecked(false);
        rbSetJump.setSelected(false); rbSetJump.setChecked(false);
        rbJump.setSelected(false); rbJump.setChecked(false);
    }

    private void setRadioButtonActive(int idRadioButton){
        switch (idRadioButton) {
            case R.id.rbLineFL: rbLineFL.setSelected(true); rbLineFL.setChecked(true); selectedRadioButton = rbLineFL.getId(); break;
            case R.id.rbLineFR: rbLineFR.setSelected(true); rbLineFR.setChecked(true); selectedRadioButton = rbLineFR.getId(); break;
            case R.id.rbLineFF: rbLineFF.setSelected(true); rbLineFF.setChecked(true); selectedRadioButton = rbLineFF.getId(); break;
            case R.id.rbLineSL: rbLineSL.setSelected(true); rbLineSL.setChecked(true); selectedRadioButton = rbLineSL.getId(); break;
            case R.id.rbLineSR: rbLineSR.setSelected(true); rbLineSR.setChecked(true); selectedRadioButton = rbLineSR.getId(); break;
            case R.id.rbLineSF: rbLineSF.setSelected(true); rbLineSF.setChecked(true); selectedRadioButton = rbLineSF.getId(); break;
            case R.id.rbFindLine: rbFindLine.setSelected(true); rbFindLine.setChecked(true); selectedRadioButton = rbFindLine.getId(); break;
            case R.id.rbLossLine: rbLossLine.setSelected(true); rbLossLine.setChecked(true); selectedRadioButton = rbLossLine.getId(); break;
            case R.id.rbSS: rbSS.setSelected(true); rbSS.setChecked(true); selectedRadioButton = rbSS.getId(); break;
            case R.id.rbLineDelay: rbLineDelay.setSelected(true); rbLineDelay.setChecked(true); selectedRadioButton = rbLineDelay.getId(); break;
            case R.id.rbSline: rbSline.setSelected(true); rbSline.setChecked(true); selectedRadioButton = rbSline.getId(); break;
            case R.id.rbMotorSpeed: rbMotorSpeed.setSelected(true); rbMotorSpeed.setChecked(true); selectedRadioButton = rbMotorSpeed.getId(); break;
            case R.id.rbLineColor: rbLineColor.setSelected(true); rbLineColor.setChecked(true); selectedRadioButton = rbLineColor.getId(); break;
            case R.id.rbLineP: rbLineP.setSelected(true); rbLineP.setChecked(true); selectedRadioButton = rbLineP.getId(); break;
            case R.id.rbDelay: rbDelay.setSelected(true); rbDelay.setChecked(true); selectedRadioButton = rbDelay.getId(); break;
            case R.id.rbRightWay: rbRightWay.setSelected(true); rbRightWay.setChecked(true); selectedRadioButton = rbRightWay.getId(); break;
            case R.id.rbLeftWay: rbLeftWay.setSelected(true); rbLeftWay.setChecked(true); selectedRadioButton = rbLeftWay.getId(); break;
            case R.id.rbMidWay: rbMidWay.setSelected(true); rbMidWay.setChecked(true); selectedRadioButton = rbMidWay.getId(); break;
            case R.id.rbSensorFront: rbSensorFront.setSelected(true); rbSensorFront.setChecked(true); selectedRadioButton = rbSensorFront.getId(); break;
            case R.id.rbSensorRear: rbSensorRear.setSelected(true); rbSensorRear.setChecked(true); selectedRadioButton = rbSensorRear.getId(); break;
//            case R.id.rbAutoDelay: rbAutoDelay.setSelected(true); rbAutoDelay.setChecked(true); selectedRadioButton = rbAutoDelay.getId(); break;
            case R.id.rbLeft: rbLeft.setSelected(true); rbLeft.setChecked(true); selectedRadioButton = rbLeft.getId(); break;
            case R.id.rbRight: rbRight.setSelected(true); rbRight.setChecked(true); selectedRadioButton = rbRight.getId(); break;
            case R.id.rbNext:  rbNext.setSelected(true); rbNext.setChecked(true); selectedRadioButton = rbNext.getId(); break;
            case R.id.rbGripDown: rbGripDown.setSelected(true); rbGripDown.setChecked(true); selectedRadioButton = rbGripDown.getId(); break;
            case R.id.rbGripUp: rbGripUp.setSelected(true); rbGripUp.setChecked(true); selectedRadioButton = rbGripUp.getId(); break;
            case R.id.rbGripper: rbGripper.setSelected(true); rbGripper.setChecked(true); selectedRadioButton = rbGripper.getId(); break;
            case R.id.rbSetUp: rbSetUp.setSelected(true); rbSetUp.setChecked(true); selectedRadioButton = rbSetUp.getId(); break;
            case R.id.rbSetDown: rbSetDown.setSelected(true); rbSetDown.setChecked(true); selectedRadioButton = rbSetDown.getId(); break;
            case R.id.rbSetJump: rbSetJump.setSelected(true); rbSetJump.setChecked(true); selectedRadioButton = rbSetJump.getId(); break;
            case R.id.rbJump: rbJump.setSelected(true); rbJump.setChecked(true); selectedRadioButton = rbJump.getId(); break;
        }
    }

    private void appendProgram(int idRadioButton){
        switch (idRadioButton) {
            case R.id.rbLineFL:
                if(validasiJalan()){
                    if(!validasiAutoDelay()) return;
                    writeAppendProgram("line(fl, " + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbLineFR:
                if(validasiJalan()){
                    if(!validasiAutoDelay()) return;
                    writeAppendProgram("line(fr, " + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbLineFF:
                if(validasiJalan()){
                    if(!validasiAutoDelay()) return;
                    writeAppendProgram("line(ff, " + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbLineSL:
                if(validasiJalan()){
                    if(!validasiAutoDelay()) return;
                    writeAppendProgram("line(sl, " + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbLineSR:
                if(validasiJalan()){
                    if(!validasiAutoDelay()) return;
                    writeAppendProgram("line(sr, " + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbLineSF:
                if(validasiJalan()){
                    if(!validasiAutoDelay()) return;
                    writeAppendProgram("line(sf, " + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbFindLine:
                if(validasiJalan()){
                    writeAppendProgram("findline(" + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                }
                break;
            case R.id.rbLossLine:
                if(validasiJalan()){
                    if(!validasiAutoDelay()) return;
                    writeAppendProgram("lossline(" + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbSS:
                if(validasiJalan()){
                    writeAppendProgram("SS();");
                }
                break;
            case R.id.rbLineDelay:
                if(validasiJalan()){
                    if(!etLineDelay.getText().toString().trim().equals("")){
                        writeAppendProgram("linedelay(" + etKecepatanJalan.getText().toString().trim() + ", " + etLineDelay.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                    }
                    else {
                        etLineDelay.requestFocus();
                        etLineDelay.setSelection(0);
                        imm.showSoftInput(etLineDelay, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
                break;
            case R.id.rbSline:
                if(validasiJalan()){
                    if(!validasiAutoDelay()) return;
                    if(!etSline.getText().toString().trim().equals("")){
                        writeAppendProgram("sline(" + etSline.getText().toString().trim() + ", " + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                        if(cbAutoDelay.isChecked()) writeAutoDelay();
                    }
                    else {
                        etSline.requestFocus();
                        etSline.setSelection(0);
                        imm.showSoftInput(etSline, InputMethodManager.SHOW_IMPLICIT);
                    }
//                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbMotorSpeed:
                if(validasiJalan()){
                    if(!etMotorSpeed.getText().toString().trim().equals("")){
                        writeAppendProgram("motor(" + etMotorSpeed.getText().toString().trim() + ");");
                    }
                    else {
                        etMotorSpeed.requestFocus();
                        etMotorSpeed.setSelection(0);
                        imm.showSoftInput(etMotorSpeed, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
                break;
            case R.id.rbLineColor:
                String lineColor = cbLineColor.isChecked() ? "white" : "black";
                if(validasiJalan()){
                    if(!validasiAutoDelay()) return;
                    writeAppendProgram("linecolor(" + lineColor + ", " + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbLineP:
                if(validasiJalan()){
                    if(!validasiAutoDelay()) return;
                    if(!etLineP.getText().toString().trim().equals("")){
                        writeAppendProgram("lineP(" + etLineP.getText().toString().trim() + ", " + etKecepatanJalan.getText().toString().trim() + ", " + etBreakJalan.getText().toString().trim() + ");");
                        if(cbAutoDelay.isChecked()) writeAutoDelay();
                    }
                    else {
                        etLineP.requestFocus();
                        etLineP.setSelection(0);
                        imm.showSoftInput(etLineP, InputMethodManager.SHOW_IMPLICIT);
                    }
//                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbDelay:
                if(validasiJalan()){
                    if(!etDelay.getText().toString().trim().equals("")){
                        writeAppendProgram("delay(" + etDelay.getText().toString().trim() + ");");
                    }
                    else {
                        etDelay.requestFocus();
                        etDelay.setSelection(0);
                        imm.showSoftInput(etDelay, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
                break;
            case R.id.rbRightWay:
                if(validasiJalan()){
                    writeAppendProgram("rightway();");
                }
                break;
            case R.id.rbLeftWay:
                if(validasiJalan()){
                    writeAppendProgram("leftway();");
                }
                break;
            case R.id.rbMidWay:
                if(validasiJalan()){
                    writeAppendProgram("midway();");
                }
                break;
            case R.id.rbSensorFront:
                if(validasiJalan()){
                    writeAppendProgram("sensor(front);");
                }
                break;
            case R.id.rbSensorRear:
                if(validasiJalan()){
                    writeAppendProgram("sensor(rear);");
                }
                break;
//            case R.id.rbAutoDelay:
//                break;
            case R.id.rbLeft:
                if(validasiBelok()){
                    if(!validasiAutoDelay()) return;
                    writeAppendProgram("left(" + (-1 * Integer.parseInt(etKecepatanBelok.getText().toString().trim())) + ", " + etKecepatanBelok.getText().toString().trim() + ", " + etBreakBelok.getText().toString().trim() + ");");
                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbRight:
                if(validasiBelok()){
                    if(!validasiAutoDelay()) return;
                    writeAppendProgram("right(" + etKecepatanBelok.getText().toString().trim() + ", " + (-1 * Integer.parseInt(etKecepatanBelok.getText().toString().trim())) + ", " + etBreakBelok.getText().toString().trim() + ");");
                    if(validasiAutoDelay() && cbAutoDelay.isChecked()) writeAutoDelay();
                }
                break;
            case R.id.rbNext:
                if(validasiJalan()){
                    writeAppendProgram("next();");
                }
                break;
            case R.id.rbGripDown:
                if(validasiJalan()){
                    writeAppendProgram("gripperDown();");
                }
                break;
            case R.id.rbGripUp:
                if(validasiJalan()){
                    writeAppendProgram("gripperUp();");
                }
                break;
            case R.id.rbGripper:
                if(validasiJalan()){
                    if(!etGripper.getText().toString().trim().equals("")){
                        writeAppendProgram("gripper(" + etGripper.getText().toString().trim() + ");");
                    }
                    else {
                        etGripper.requestFocus();
                        etGripper.setSelection(0);
                        imm.showSoftInput(etGripper, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
                break;
            case R.id.rbSetUp:
                if(validasiJalan()){
                    writeAppendProgram("setUp();");
                }
                break;
            case R.id.rbSetDown:
                if(validasiJalan()){
                    writeAppendProgram("setDown();");
                }
                break;
            case R.id.rbSetJump:
                if(validasiJalan()){
                    writeAppendProgram("setJump();");
                }
                break;
            case R.id.rbJump:
                if(validasiJalan()){
                    writeAppendProgram("jump();");
                }
                break;
        }
    }

    private boolean validasiJalan(){
        if(!etKecepatanJalan.getText().toString().trim().equals("") && !etBreakJalan.getText().toString().trim().equals("")){
            return true;
        }
        else {
            Snackbar.make(findViewById(R.id.btnConnectRobot), "Kecepatan jalan / Break jalan belum diset", Snackbar.LENGTH_SHORT)
                    .setAction("Set Value", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(etKecepatanJalan.getText().toString().trim().equals("")){
                                etKecepatanJalan.requestFocus();
                                etKecepatanJalan.setSelection(0);
                                imm.showSoftInput(etKecepatanJalan, InputMethodManager.SHOW_IMPLICIT);
                            }
                            else if(etBreakJalan.getText().toString().trim().equals("")){
                                etBreakJalan.requestFocus();
                                etBreakJalan.setSelection(0);
                                imm.showSoftInput(etBreakJalan, InputMethodManager.SHOW_IMPLICIT);
                            }
                        }
                    })
                    .setActionTextColor(Color.parseColor("#AAA000"))
                    .show();
            return false;
        }
    }

    private boolean validasiBelok(){
        if(!etKecepatanBelok.getText().toString().trim().equals("") && !etBreakBelok.getText().toString().trim().equals("")){
            return true;
        }
        else {
            Snackbar.make(findViewById(R.id.btnConnectRobot), "Kecepatan belok / Break belok belum diset", Snackbar.LENGTH_SHORT)
                    .setAction("Set Value", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(etKecepatanBelok.getText().toString().trim().equals("")){
                                etKecepatanBelok.requestFocus();
                                etKecepatanBelok.setSelection(0);
                                imm.showSoftInput(etKecepatanBelok, InputMethodManager.SHOW_IMPLICIT);
                            }
                            else if(etBreakBelok.getText().toString().trim().equals("")){
                                etBreakBelok.requestFocus();
                                etBreakBelok.setSelection(0);
                                imm.showSoftInput(etBreakBelok, InputMethodManager.SHOW_IMPLICIT);
                            }
                        }
                    })
                    .setActionTextColor(Color.parseColor("#AAA000"))
                    .show();
            return false;
        }
    }

    private boolean validasiAutoDelay(){
        if(cbAutoDelay.isChecked()){
            if(!etAutoDelay.getText().toString().trim().equals("")){
                return true;
            }
            else {
                Snackbar.make(findViewById(R.id.btnConnectRobot), "Nilai auto delay belum diset", Snackbar.LENGTH_SHORT)
                        .setAction("Set Value", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                etAutoDelay.requestFocus();
                                etAutoDelay.setSelection(0);
                                imm.showSoftInput(etAutoDelay, InputMethodManager.SHOW_IMPLICIT);
                            }
                        })
                        .setActionTextColor(Color.parseColor("#AAA000"))
                        .show();
                return false;
            }
        }
        else {
            return true;
        }
    }

    private boolean validasiDeleteLine(){
        if (!etRemoveLineProgram.getText().toString().trim().equals("") && Integer.parseInt(etRemoveLineProgram.getText().toString().trim()) <= tvProgramPreview.getText().toString().trim().split("\n").length && Integer.parseInt(etRemoveLineProgram.getText().toString().trim()) > 0){
            return true;
        }
        else {
            Snackbar.make(findViewById(R.id.btnConnectRobot), "Request baris penghapusan tidak valid", Snackbar.LENGTH_SHORT)
                    .setAction("Set Value", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            etRemoveLineProgram.requestFocus();
                            etRemoveLineProgram.setSelection(etRemoveLineProgram.getText().length());
                            imm.showSoftInput(etRemoveLineProgram, InputMethodManager.SHOW_IMPLICIT);
                        }
                    })
                    .setActionTextColor(Color.parseColor("#AAA000"))
                    .show();
            return false;
        }
    }

    private boolean validasiCustomInsertAbove(){
        if(cbInsertAboveLine.isChecked()){
            if(!etInsertAboveLine.getText().toString().trim().equals("")){
                if (Integer.parseInt(etInsertAboveLine.getText().toString().trim()) > tvProgramPreview.getText().toString().trim().split("\n").length || Integer.parseInt(etInsertAboveLine.getText().toString().trim()) <= 0){
                    Snackbar.make(findViewById(R.id.btnConnectRobot), "Request baris sisipan tidak valid", Snackbar.LENGTH_SHORT)
                            .setAction("Set Value", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    etInsertAboveLine.requestFocus();
                                    etInsertAboveLine.setSelection(etInsertAboveLine.getText().length());
                                    imm.showSoftInput(etInsertAboveLine, InputMethodManager.SHOW_IMPLICIT);
                                }
                            })
                            .setActionTextColor(Color.parseColor("#AAA000"))
                            .show();
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                Snackbar.make(findViewById(R.id.btnConnectRobot), "Letak baris sisipan belum diset", Snackbar.LENGTH_SHORT)
                        .setAction("Set Value", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                etInsertAboveLine.requestFocus();
                                etInsertAboveLine.setSelection(0);
                                imm.showSoftInput(etInsertAboveLine, InputMethodManager.SHOW_IMPLICIT);
                            }
                        })
                        .setActionTextColor(Color.parseColor("#AAA000"))
                        .show();
                return false;
            }
        }
        else {
            return true;
        }
    }

    private void writeAppendProgram(String word){
        if (!validasiCustomInsertAbove()) return;

        if(!tvProgramPreview.getText().toString().trim().equals("")){
            tvProgramPreview.setText(tvProgramPreview.getText().toString() + "\n");
            tvLineNumberProgramPreview.setText(tvLineNumberProgramPreview.getText().toString() + "\n");
        }

        if (cbInsertAboveLine.isChecked()){
            reconstructionWhileInsertLine(false, word);
        }
        else {
            tvProgramPreview.setText(tvProgramPreview.getText().toString() + word);
            tvLineNumberProgramPreview.setText(tvLineNumberProgramPreview.getText().toString() + tvProgramPreview.getText().toString().split("\n").length + ".");
        }
    }

    private void writeAutoDelay(){
        if (!validasiCustomInsertAbove()) return;

        if(cbInsertAboveLine.isChecked()){
            reconstructionWhileInsertLine(true, "delay(" + etAutoDelay.getText().toString().trim() + ");");
        }
        else {
            tvProgramPreview.setText(tvProgramPreview.getText().toString() + "\n" + "delay(" + etAutoDelay.getText().toString().trim() + ");");
            tvLineNumberProgramPreview.setText(tvLineNumberProgramPreview.getText().toString() + "\n" + tvProgramPreview.getText().toString().split("\n").length + ".");
        }
    }

    private void reconstructionWhileInsertLine(boolean isAutoDelay, String word){
        ArrayList<String> insertedValue = new ArrayList<>();
        String[] scriptPerLine = tvProgramPreview.getText().toString().trim().split("\n");
        int requestLine = Integer.parseInt(etInsertAboveLine.getText().toString().trim());
//        int programsLastLineNumber = tvProgramPreview.getText().toString().trim().split("\n").length;
        int programsLastLineNumber = scriptPerLine.length;

        tvProgramPreview.setText("");
        tvLineNumberProgramPreview.setText("");

        if(isAutoDelay){
           requestLine += 1;
        }
        for (int i = 0; i < requestLine - 1; i++){
            insertedValue.add(scriptPerLine[i]);
        }
        insertedValue.add(word);
        for (int i = requestLine - 1; i < programsLastLineNumber; i++){
            insertedValue.add(scriptPerLine[i]);
        }
        for (int i = 0; i < insertedValue.size(); i++){
            if(i != 0){
                tvProgramPreview.setText(tvProgramPreview.getText().toString() + "\n");
                tvLineNumberProgramPreview.setText(tvLineNumberProgramPreview.getText().toString() + "\n");
            }
            tvProgramPreview.setText(tvProgramPreview.getText().toString() + insertedValue.get(i));
            tvLineNumberProgramPreview.setText(tvLineNumberProgramPreview.getText().toString() + tvProgramPreview.getText().toString().split("\n").length + ".");
        }
    }

    private void reconstructionWhileDeletLine(int lineNumber){
        ArrayList<String> afterDeletedValue = new ArrayList<>();
        String[] scriptPerLine = tvProgramPreview.getText().toString().trim().split("\n");
        int programsLastLineNumber = tvProgramPreview.getText().toString().trim().split("\n").length;

        tvProgramPreview.setText("");
        tvLineNumberProgramPreview.setText("");

        for (int i = 0; i < lineNumber - 1; i++){
            afterDeletedValue.add(scriptPerLine[i]);
        }
        for (int i = lineNumber; i < programsLastLineNumber; i++){
            afterDeletedValue.add(scriptPerLine[i]);
        }
        for (int i = 0; i < afterDeletedValue.size(); i++){
            if(i != 0){
                tvProgramPreview.setText(tvProgramPreview.getText().toString() + "\n");
                tvLineNumberProgramPreview.setText(tvLineNumberProgramPreview.getText().toString() + "\n");
            }
            tvProgramPreview.setText(tvProgramPreview.getText().toString() + afterDeletedValue.get(i));
            tvLineNumberProgramPreview.setText(tvLineNumberProgramPreview.getText().toString() + tvProgramPreview.getText().toString().split("\n").length + ".");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            tvLineNumberProgramPreview.setText(data.getStringExtra("number_text"));
            tvProgramPreview.setText(data.getStringExtra("program_text"));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("active_radio_button", activeRadioButton);
        outState.putString("program_preview", tvProgramPreview.getText().toString());
        outState.putString("line_number_program_preview", tvLineNumberProgramPreview.getText().toString());
        outState.putString("kecepatan_jalan", etKecepatanJalan.getText().toString());
        outState.putString("break_jalan", etBreakJalan.getText().toString());
        outState.putString("kecepatan_belok", etKecepatanBelok.getText().toString());
        outState.putString("break_belok", etBreakBelok.getText().toString());
        outState.putString("line_delay", etLineDelay.getText().toString());
        outState.putString("s_line", etSline.getText().toString());
        outState.putString("motor_speed", etMotorSpeed.getText().toString());
        outState.putString("line_p", etLineP.getText().toString());
        outState.putString("delay", etDelay.getText().toString());
        outState.putString("left", etLeft.getText().toString());
        outState.putString("right", etRight.getText().toString());
        outState.putString("gripper", etGripper.getText().toString());
        outState.putString("set_up", etSetUp.getText().toString());
        outState.putString("set_down", etSetDown.getText().toString());
        outState.putString("auto_delay_value", etAutoDelay.getText().toString());
        outState.putString("insert_above_line_value", etInsertAboveLine.getText().toString());
        outState.putString("remove_line_program", etRemoveLineProgram.getText().toString());
        outState.putBoolean("line_color", cbLineColor.isChecked());
        outState.putBoolean("auto_delay", cbAutoDelay.isChecked());
        outState.putBoolean("insert_above_line", cbInsertAboveLine.isChecked());
    }
}