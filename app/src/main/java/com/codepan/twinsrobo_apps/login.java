package com.codepan.twinsrobo_apps;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.codepan.twinsrobo_apps.APIs.APIRequestData;
import com.codepan.twinsrobo_apps.APIs.EmailAPI;
import com.codepan.twinsrobo_apps.APIs.RetroServer;
import com.codepan.twinsrobo_apps.Models.DataModelLogin;
import com.codepan.twinsrobo_apps.Models.ResponseModelLogin;
import com.codepan.twinsrobo_apps.Models.ResponseModelUser;
import com.codepan.twinsrobo_apps.OtherClass.DataHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jaeger.library.StatusBarUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

//    private EditText emailid, password;
//    private Button btnSignup;
//    private TextView tvSignin;
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private Button toSignUp, btnLogin;
    private EditText etLoginEmail, etLoginPass, etOtpBox1, etOtpBox2, etOtpBox3, etOtpBox4, etOtpBox5, etUpdatePassword;
    private TextView toSolvePassword;
    private ProgressBar pbLogin;
    private ProgressDialog progressDialog;
    private Dialog popUpVerifikasiOTP, popUpInputNewPassword;

    private String kodeVerifikasi, newPassword;
    private String[] inputKodeUserArray = {"","","","",""};
    private boolean isFillingOtp = false, isFillingNewPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StatusBarUtil.setTransparent(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        toSignUp = findViewById(R.id.toSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPass = findViewById(R.id.etLoginPass);
        pbLogin = findViewById(R.id.pbLogin);
        toSolvePassword = findViewById(R.id.toSolvePassword);

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, SigninActivity.class));
                overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_up_out_anim);
                etLoginEmail.setText("");
                etLoginPass.setText("");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isIncompleteForm()) {
                    masuk();
                } else {
                    Toast.makeText(login.this, "Masukkan Email dan Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toSolvePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(etLoginEmail.getText().toString().split("\\@").length != 2) && !(etLoginEmail.getText().toString().split(" ").length > 1)){
                    Snackbar sb = Snackbar.make(view, "Try to change the password", Snackbar.LENGTH_SHORT)
                            .setAction("Do it", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    checkingEmailIsRegistered();
                                }
                            })
                            .setActionTextColor(Color.parseColor("#0094EF"));
                    if (getHardwareNavbarHeight() > 0 && findViewById(R.id.rlLoginLayout_potrait) != null){
                        Snackbar.SnackbarLayout sbParams = (Snackbar.SnackbarLayout) sb.getView();
                        sbParams.setPadding(50, 0, 50, getHardwareNavbarHeight());
                    }
                    sb.show();
                }
                else {
                    Snackbar sb = Snackbar.make(view, "Please fill in the username or email field with your email address", Snackbar.LENGTH_SHORT)
                            .setAction("Complete\nit", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    etLoginEmail.requestFocus();
                                    etLoginEmail.setSelection(etLoginEmail.getText().toString().length());
                                }
                            })
                            .setActionTextColor(Color.parseColor("#AAA000"));
                    if (getHardwareNavbarHeight() > 0 && findViewById(R.id.rlLoginLayout_potrait) != null){
                        Snackbar.SnackbarLayout sbParams = (Snackbar.SnackbarLayout) sb.getView();
                        sbParams.setPadding(50, 0, 50, getHardwareNavbarHeight());
                    }
                    sb.show();
                }
            }
        });

////        mFirebaseAuth = FirebaseAuth.getInstance();
//        emailid = findViewById(R.id.editText);
//        password = findViewById(R.id.editText2);
//        btnSignup = findViewById(R.id.button2);
//        tvSignin = findViewById(R.id.textView);
//
//        //sign up button
//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = emailid.getText().toString();
//                String pwd = password.getText().toString();
//                if (email.isEmpty() && pwd.isEmpty()) {
//                    Toast.makeText(login.this,"Field Are Empty!", Toast.LENGTH_SHORT).show();
//                }
//                else if (email.isEmpty()) {
//                    emailid.setError("Please enter email id");
//                    emailid.requestFocus();
//                }  else if (pwd.isEmpty()) {
//                    password.setError("Please enter your password");
//                    password.requestFocus();
//                }   else if ((!email.isEmpty() && !pwd.isEmpty())) {
////                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
////                        @Override
////                        public void onComplete(@NonNull Task<AuthResult> task) {
////                            if (!task.isSuccessful()){
////                                Toast.makeText(login.this,"SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
////                            } else {
////                                startActivity(new Intent(login.this,HomeActivity.class));
////                            }
////                        }
////                    });
//                }else {
//                    Toast.makeText(login.this,"Error Occured!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        tvSignin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(login.this,SigninActivity.class);
//                startActivity(i);
//            }
//        });
//
    }

    private void masuk() {
        isProcess(true);
        String usernameOrEmail = etLoginEmail.getText().toString();
        String password = etLoginPass.getText().toString();

        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelLogin> requestLogin = ardData.ardRetrieveLogin(usernameOrEmail, password, "cek-login");

        requestLogin.enqueue(new Callback<ResponseModelLogin>() {
            @Override
            public void onResponse(Call<ResponseModelLogin> call, Response<ResponseModelLogin> response) {
                List<DataModelLogin> listDataLogin = response.body().getDataLogin(0);
                DataModelLogin dml = listDataLogin.get(0);

                if (Integer.parseInt(dml.getUsercount()) > 0) {
                    try {
                        Intent intent = new Intent(login.this, HomeActivity.class);
                        intent.putExtra("id_user_login", dml.getIdUser());
                        intent.putExtra("nama_depan_login", dml.getNamaDepanUser());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up_in_anim, R.anim.slide_up_out_anim);
//                        Toast.makeText(login.this, "Login Sukses", Toast.LENGTH_SHORT).show();

                        saveLoginSession(dml.getIdUser(), dml.getNamaDepanUser());
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(login.this, "Error, coba lagi...", Toast.LENGTH_SHORT).show();
                    }
                    etLoginEmail.setText("");
                    etLoginPass.setText("");
                } else {
                    Toast.makeText(login.this, "Username / password salah", Toast.LENGTH_SHORT).show();
                }
                isProcess(false);
            }

            @Override
            public void onFailure(Call<ResponseModelLogin> call, Throwable t) {
                Toast.makeText(login.this, "Gagal Menghubungi Server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
                isProcess(false);
            }
        });
    }

    private void autoFillOtp(){
        etOtpBox1.setText(inputKodeUserArray[0]);
        etOtpBox2.setText(inputKodeUserArray[1]);
        etOtpBox3.setText(inputKodeUserArray[2]);
        etOtpBox4.setText(inputKodeUserArray[3]);
        etOtpBox5.setText(inputKodeUserArray[4]);
    }

    private void checkingEmailIsRegistered(){

        showProgressDialog("Mengecek Email", "Mohon tunggu...");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelLogin> requestData = ardData.ardCekRegister(etLoginEmail.getText().toString().trim(), "cek-duplikat");

        requestData.enqueue(new Callback<ResponseModelLogin>() {
            @Override
            public void onResponse(Call<ResponseModelLogin> call, Response<ResponseModelLogin> response) {
                List<DataModelLogin> listData = response.body().getDataLogin(0);
                DataModelLogin dml = listData.get(0);

                if(!dml.getUsercount().equals("0")){
                    kodeVerifikasi = String.valueOf(new Random().nextInt(89999) + 10000);
                    String emailReceiver = etLoginEmail.getText().toString();
                    String emailSubject = "Verifikasi Perubahan Password TwinsRoboApp - " + (new Random().nextInt(89999 + 10000 ));
                    String emailText = "Hallo Sobat Twins Robo...!" +
                            "\nTerimakasih atas kesetiaan anda dalam menggunakan aplikasi Twins Robo Apps." +
                            " Berikut ini adalah kode verifikasi untuk melanjutkan proses penggantian password akun anda." +
                            "\n\n Kode : " + kodeVerifikasi +
                            "\n\n Atau klik link dibawah ini. (*Dibuka dengan smartphone)" +
                            "\n https://codepan.twinsrobo.com/change_password/" + kodeVerifikasi +
                            "\n\nSahabatmu,\nTwinsRobo Team.";
                    EmailAPI kirimEmailVerifikasi = new EmailAPI(login.this, emailReceiver, emailSubject, emailText);
                    kirimEmailVerifikasi.execute();

                    progressDialog.setTitle("Mengirim Kode OTP");
                    progressDialog.setMessage("Mengirim via email, mohon tunggu...");
                }
                else {
                    Toast.makeText(login.this, "Email tidak terdaftar", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
//                pgEmailCheck.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseModelLogin> call, Throwable t) {
                Toast.makeText(login.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void postSendingEmailResult(String pesan){

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        progressDialog.dismiss();

        if(pesan.equals("Success")){
            insertCodeOTP();
        }
        else {
            Toast.makeText(this, "Email verifikasi tidak terkirim", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertNewPassword(String lastInputText){

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        popUpInputNewPassword = new Dialog(this);
        popUpInputNewPassword.setContentView(R.layout.update_password_popup);
        ImageView ivClosePopUpPasswordUpdate = popUpInputNewPassword.findViewById(R.id.ivClosePopUpPasswordUpdate);
        etUpdatePassword = popUpInputNewPassword.findViewById(R.id.etUpdatePassword);
        FloatingActionButton fabSubmitPasswordUpdate = popUpInputNewPassword.findViewById(R.id.fabSubmitPasswordUpdate);

        etUpdatePassword.setText(lastInputText);

        ivClosePopUpPasswordUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPassword = "";
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                popUpInputNewPassword.dismiss();
            }
        });

        fabSubmitPasswordUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
                progressDialog.setTitle("Memperbarui Password");
                progressDialog.setMessage("Mohon tunggu...");
                progressDialog.show();

                APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
                Call<ResponseModelUser> requestUpdatePassword = ardData.ardUpdatePassword(etLoginEmail.getText().toString(), etUpdatePassword.getText().toString());

                requestUpdatePassword.enqueue(new Callback<ResponseModelUser>() {
                    @Override
                    public void onResponse(Call<ResponseModelUser> call, Response<ResponseModelUser> response) {
                        progressDialog.dismiss();
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                        if(response.body().getKodeUser() == 1){
                            popUpInputNewPassword.dismiss();
                            Snackbar sb = Snackbar.make(findViewById(R.id.btnLogin), "Password updated successfully, rewrite password?", Snackbar.LENGTH_SHORT)
                                    .setAction("Do it", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            etLoginPass.setText("");
                                            etLoginPass.requestFocus();
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.showSoftInput(etLoginPass, InputMethodManager.SHOW_IMPLICIT);
                                        }
                                    })
                                    .setActionTextColor(Color.parseColor("#AAA000"));
                            if (getHardwareNavbarHeight() > 0 && findViewById(R.id.rlLoginLayout_potrait) != null){
                                Snackbar.SnackbarLayout sbParams = (Snackbar.SnackbarLayout) sb.getView();
                                sbParams.setPadding(50, 0, 50, getHardwareNavbarHeight());
                            }
                            sb.show();
                        }
                        else {
                            progressDialog.dismiss();
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                            Toast.makeText(login.this, response.body().getPesanUser(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModelUser> call, Throwable t) {
                        Toast.makeText(login.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        popUpInputNewPassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpInputNewPassword.setCancelable(false);
        popUpInputNewPassword.show();
    }

    private void insertCodeOTP(){

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        isFillingOtp = true;
        popUpVerifikasiOTP = new Dialog(this);
        popUpVerifikasiOTP.setContentView(R.layout.otp_code_field);
        ImageView ivClosePopUpOtpVerification = popUpVerifikasiOTP.findViewById(R.id.ivClosePopUpOtpVerification);
        etOtpBox1 = popUpVerifikasiOTP.findViewById(R.id.etOtpBox1);
        etOtpBox2 = popUpVerifikasiOTP.findViewById(R.id.etOtpBox2);
        etOtpBox3 = popUpVerifikasiOTP.findViewById(R.id.etOtpBox3);
        etOtpBox4 = popUpVerifikasiOTP.findViewById(R.id.etOtpBox4);
        etOtpBox5 = popUpVerifikasiOTP.findViewById(R.id.etOtpBox5);
        FloatingActionButton fabSubmitOtp = popUpVerifikasiOTP.findViewById(R.id.fabSubmitOtp);

        autoFillOtp();
//            etOtpBox1.requestFocus();

        etOtpBox1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0) {
                    etOtpBox2.requestFocus();
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {                }
        });

        etOtpBox2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0) etOtpBox3.requestFocus();
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        });

        etOtpBox3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0) etOtpBox4.requestFocus();
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        });

        etOtpBox4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0) etOtpBox5.requestFocus();
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        });

        etOtpBox5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0) fabSubmitOtp.requestFocus();
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        });

        // konfigurasi key listener termasuk yang didalam coment hanya berlaku apabila menggunakan hard keyboard
        etOtpBox1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                    boolean result = false;
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL && etOtpBox1.getText().toString().length() == 0){
                    etOtpBox1.clearFocus();
                }
//                    if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i != KeyEvent.KEYCODE_DEL){
//                        etOtpBox2.requestFocus();
//                        if(etOtpBox1.getText().toString().length() > 0) result = true;
//                    }
//                    return result;
                return false;
            }
        });

        etOtpBox2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL && etOtpBox2.getText().toString().length() == 0){
                    etOtpBox1.requestFocus();
                    etOtpBox1.setSelection(etOtpBox1.getText().length());
                }
                return false;
            }
        });

        etOtpBox3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL && etOtpBox3.getText().toString().length() == 0){
                    etOtpBox2.requestFocus();
                    etOtpBox2.setSelection(etOtpBox2.getText().length());
                }
                return false;
            }
        });

        etOtpBox4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL && etOtpBox4.getText().toString().length() == 0){
                    etOtpBox3.requestFocus();
                    etOtpBox3.setSelection(etOtpBox3.getText().length());
                }
                return false;
            }
        });

        etOtpBox5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL && etOtpBox5.getText().toString().length() == 0){
                    etOtpBox4.requestFocus();
                    etOtpBox4.setSelection(etOtpBox4.getText().length());
                }
                return false;
            }
        });

        fabSubmitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((etOtpBox1.getText().toString()
                        + etOtpBox2.getText().toString()
                        + etOtpBox3.getText().toString()
                        + etOtpBox4.getText().toString()
                        + etOtpBox5.getText().toString())
                        .equals(kodeVerifikasi)){

                    isFillingOtp = false;
                    popUpVerifikasiOTP.dismiss();

                    insertNewPassword(newPassword);
                }
                else {
                    Toast.makeText(login.this, "Kode OTP salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivClosePopUpOtpVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Arrays.fill(inputKodeUserArray, 0, kodeVerifikasi.length(), "");
                popUpVerifikasiOTP.dismiss();
                isFillingOtp = false;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            }
        });

        popUpVerifikasiOTP.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpVerifikasiOTP.setCancelable(false);
        popUpVerifikasiOTP.show();
    }

    private boolean isIncompleteForm() {
        if (etLoginEmail.getText().toString().trim().isEmpty()) {
            etLoginEmail.setError("Wajib Diisi");
            return true;
        }
        if (etLoginPass.getText().toString().isEmpty()) return true;
        return false;
    }

    private void showProgressDialog(String title, String message){
        progressDialog = ProgressDialog.show(login.this, title, message,false,false);
    }

    private void isProcess(boolean process){
        if(process){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            pbLogin.setVisibility(View.VISIBLE);
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            pbLogin.setVisibility(View.GONE);
        }
    }

    private void saveLoginSession(String idUser, String firstNameUser){
        DataHelper mySessionDb = new DataHelper(login.this);
        boolean isRenewedSession = mySessionDb.renewSession(idUser, firstNameUser);
        if(!isRenewedSession){
            Toast.makeText(this, "Failed to logging session", Toast.LENGTH_SHORT).show();
        }
    }

    private int getHardwareNavbarHeight(){
        int navigationBarHeight = 0;
        int idNavbar = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (getResources().getBoolean(idNavbar) && idNavbar > 0){
            int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
        }
        return navigationBarHeight;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//    }
}
