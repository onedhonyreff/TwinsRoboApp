package com.codepan.twinsrobo_apps;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.codepan.twinsrobo_apps.APIs.APIRequestData;
import com.codepan.twinsrobo_apps.APIs.EmailAPI;
import com.codepan.twinsrobo_apps.APIs.RetroServer;
import com.codepan.twinsrobo_apps.Models.DataModelLogin;
import com.codepan.twinsrobo_apps.Models.ResponseModelLogin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

//    private FirebaseAuth mAuth;

    EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private Dialog popUpVerifikasiOTP;
    private ProgressDialog pgEmailCheck;
    private Snackbar sbToast;

    private LinearLayout toLogin;
    private EditText etRegEmail, etRegPass, etRegRePass, etOtpBox1, etOtpBox2, etOtpBox3, etOtpBox4, etOtpBox5;
    private Button btnSignUp;
    private ProgressBar pbSignIn;

    private String kodeVerifikasi, progressDialogTitle, progressDialogMessage;
    private String[] inputKodeUserArray = {"","","","",""};
    private boolean isFillingOtp = false, onProgressDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        StatusBarUtil.setTransparent(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        mAuth = FirebaseAuth.getInstance();

//        mFirebaseAuth = FirebaseAuth.getInstance();
//        emailId = findViewById(R.id.editText);
//        password = findViewById(R.id.editText2);
//        btnSignIn = findViewById(R.id.button2);
//        tvSignUp = findViewById(R.id.textView);

        toLogin = findViewById(R.id.toLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPass = findViewById(R.id.etRegPass);
        etRegRePass = findViewById(R.id.etRegRePass);
        pbSignIn = findViewById(R.id.pbSignIn);

        sbToast = Snackbar.make(findViewById(R.id.btnSignUp), "Email verifikasi terkirim. Cek email sekarang?", Snackbar.LENGTH_INDEFINITE)
                .setAction("Cek Gmail", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com")));
                        insertCodeOTP();
                    }
                })
                .setActionTextColor(Color.parseColor("#0094EF"));
        if (getHardwareNavbarHeight() > 0 && findViewById(R.id.rlLayoutSignIn_potrait) != null){
            Snackbar.SnackbarLayout sbParams = (Snackbar.SnackbarLayout) sbToast.getView();
            sbParams.setPadding(50, 0, 50, getHardwareNavbarHeight());
        }

        if(savedInstanceState != null){
            isFillingOtp = savedInstanceState.getBoolean("is_filling_otp");
            onProgressDialog = savedInstanceState.getBoolean("on_progress_dialog");
            kodeVerifikasi = savedInstanceState.getString("kode_verifikasi");
            progressDialogTitle = savedInstanceState.getString("progress_dialog_title");
            progressDialogMessage = savedInstanceState.getString("progress_dialog_message");
            inputKodeUserArray = savedInstanceState.getStringArray("input_kode_user_array");

            if(savedInstanceState.getBoolean("sbToastIsShown")){
                sbToast.show();
            }
            if(isFillingOtp){
                insertCodeOTP();
            }
        }

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_up_out_anim);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isIncompleteForm()) {
                    if (etRegRePass.getText().toString().equals(etRegPass.getText().toString())) {
                        checkingEmailIsNotRegistered();
                    }
                    else {
                        Toast.makeText(SigninActivity.this, "Password not match!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SigninActivity.this, "Form tidak lengkap", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignUp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(SigninActivity.this, TryDayNightActivity.class));
                return false;
            }
        });

        if (findViewById(R.id.llSignUp_landscape) != null) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.error(R.drawable.hello);

            ImageView ivRegIconHello = findViewById(R.id.ivRegIconHello);
            Glide.with(SigninActivity.this)
                    // LOAD URL DARI LOKAL DRAWABLE
                    .load(R.drawable.giphy7)
                    .apply(requestOptions)
                    .into(ivRegIconHello);
        }

        // Sign in Firebase
//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
//                if ( mFirebaseUser != null ){
//                    Toast.makeText(SigninActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(SigninActivity.this, HomeActivity.class);
//                    startActivity(i);
//                } else {
//                    Toast.makeText(SigninActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
        //btn sign in
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = emailId.getText().toString();
//                String pwd = password.getText().toString();
//                if (email.isEmpty()) {
//                    emailId.setError("Please enter email id");
//                    emailId.requestFocus();
//                } else if (pwd.isEmpty()) {
//                    password.setError("Please enter your password");
//                    password.requestFocus();
//                } else if (email.isEmpty() && pwd.isEmpty()) {
//                    Toast.makeText(SigninActivity.this,"Field Are Empty!",Toast.LENGTH_SHORT).show();
//                } else if ((!email.isEmpty() && !pwd.isEmpty())) {
////                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
////                        @Override
////                        public void onComplete(@NonNull Task<AuthResult> task) {
////                            if (!task.isSuccessful()) {
////                                Toast.makeText(SigninActivity.this,"Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
////                            } else {
////                                Intent intToHome = new Intent(SigninActivity.this,HomeActivity.class);
////                                startActivity(intToHome);
////                            }
////                        }
////                    });
//                }
//                else {
//                    Toast.makeText(SigninActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        tvSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intSignUp = new Intent(SigninActivity.this, login.class);
//                startActivity(intSignUp);
//            }
//        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
////        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    private void isProcess(boolean process){
        if(process){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            pbSignIn.setVisibility(View.VISIBLE);
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            pbSignIn.setVisibility(View.GONE);
        }
    }

    private boolean isIncompleteForm() {
        if (etRegEmail.getText().toString().trim().isEmpty()) return true;
        if (etRegPass.getText().toString().trim().isEmpty()) return true;
        if (etRegEmail.getText().toString().split("\\@").length != 2 || etRegEmail.getText().toString().split(" ").length > 1){
            etRegEmail.setError("Gunakan Format Email");
            return true;
        }
        return false;
    }

    private void showProgressDialog(String title, String message){
        pgEmailCheck = ProgressDialog.show(SigninActivity.this, title, message,false,false);
    }

    public void postSendingEmailResult(String pesan){

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        if(onProgressDialog){
            pgEmailCheck.dismiss();
            onProgressDialog = false;
        }

        if(pesan.equals("Success")){
            sbToast.show();
        }
        else {
            Toast.makeText(this, "Email verifikasi tidak terkirim", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertCodeOTP(){
        isFillingOtp = true;
        popUpVerifikasiOTP = new Dialog(this);
        popUpVerifikasiOTP.setContentView(R.layout.otp_code_field);
        ImageView ivClosePopUpOtpVerification = (ImageView) popUpVerifikasiOTP.findViewById(R.id.ivClosePopUpOtpVerification);
        etOtpBox1 = (EditText) popUpVerifikasiOTP.findViewById(R.id.etOtpBox1);
        etOtpBox2 = (EditText) popUpVerifikasiOTP.findViewById(R.id.etOtpBox2);
        etOtpBox3 = (EditText) popUpVerifikasiOTP.findViewById(R.id.etOtpBox3);
        etOtpBox4 = (EditText) popUpVerifikasiOTP.findViewById(R.id.etOtpBox4);
        etOtpBox5 = (EditText) popUpVerifikasiOTP.findViewById(R.id.etOtpBox5);
        FloatingActionButton fabSubmitOtp = (FloatingActionButton) popUpVerifikasiOTP.findViewById(R.id.fabSubmitOtp);

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

                    Intent intent = new Intent(SigninActivity.this, RegistrasiProfilActivity.class);
                    intent.putExtra("regEmail", etRegEmail.getText().toString().trim());
                    intent.putExtra("regPass", etRegPass.getText().toString());
                    startActivityForResult(intent, 386);
                }
                else {
                    Toast.makeText(SigninActivity.this, "Kode OTP salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivClosePopUpOtpVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Arrays.fill(inputKodeUserArray, 0, kodeVerifikasi.length(), "");
                popUpVerifikasiOTP.dismiss();
                isFillingOtp = false;
            }
        });

        popUpVerifikasiOTP.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpVerifikasiOTP.setCancelable(false);
        popUpVerifikasiOTP.show();
    }

    private void autoFillOtp(){
        etOtpBox1.setText(inputKodeUserArray[0]);
        etOtpBox2.setText(inputKodeUserArray[1]);
        etOtpBox3.setText(inputKodeUserArray[2]);
        etOtpBox4.setText(inputKodeUserArray[3]);
        etOtpBox5.setText(inputKodeUserArray[4]);
    }

    private void checkingEmailIsNotRegistered(){

        progressDialogTitle = "Mengecek Email";
        progressDialogMessage = "Mohon tunggu...";
        showProgressDialog(progressDialogTitle, progressDialogMessage);
        onProgressDialog = true;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelLogin> requestData = ardData.ardCekRegister(etRegEmail.getText().toString().trim(), "cek-duplikat");

        requestData.enqueue(new Callback<ResponseModelLogin>() {
            @Override
            public void onResponse(Call<ResponseModelLogin> call, Response<ResponseModelLogin> response) {
                List<DataModelLogin> listData = response.body().getDataLogin(0);
                DataModelLogin dml = listData.get(0);

                if(dml.getUsercount().equals("0")){
                    kodeVerifikasi = String.valueOf(new Random().nextInt(89999) + 10000);
                    String emailReceiver = etRegEmail.getText().toString();
                    String emailSubject = "Verifikasi Email Akun TwinsRoboApp - " + (new Random().nextInt(89999 + 10000 ));
                    String emailText = "Selamat datang di Twins Robo Apps...!" +
                            "\nKami senang dengan bergabungnya anda untuk menjadi bagian dari komunitas kami." +
                            " Untuk melanjutkan ke tahap berikutnya masukkan kode varifikasi anda dibawah ini." +
                            "\n\n Kode : " + kodeVerifikasi +
                            "\n\n Atau klik link dibawah ini. (*Dibuka dengan smartphone)" +
                            "\n https://codepan.twinsrobo.com/verification/" + kodeVerifikasi +
                            "\n\nSahabatmu,\nTwinsRobo Team.";
                    EmailAPI kirimEmailVerifikasi = new EmailAPI(SigninActivity.this, emailReceiver, emailSubject, emailText);
                    kirimEmailVerifikasi.execute();

                    progressDialogTitle = "Mengirim Kode OTP";
                    progressDialogMessage = "Mengirim via email, mohon tunggu...";
                    pgEmailCheck.setTitle(progressDialogTitle);
                    pgEmailCheck.setMessage(progressDialogMessage);
                }
                else {
                    Toast.makeText(SigninActivity.this, "Email sudah terdaftar", Toast.LENGTH_SHORT).show();
                    pgEmailCheck.dismiss();
                    onProgressDialog = false;
                }
//                pgEmailCheck.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseModelLogin> call, Throwable t) {
                Toast.makeText(SigninActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pgEmailCheck.dismiss();
                onProgressDialog = false;
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 386) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_up_out_anim);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(isFillingOtp){
            inputKodeUserArray[0] = etOtpBox1.getText().toString().equals("") ? "" : etOtpBox1.getText().toString();
            inputKodeUserArray[1] = etOtpBox2.getText().toString().equals("") ? "" : etOtpBox2.getText().toString();
            inputKodeUserArray[2] = etOtpBox3.getText().toString().equals("") ? "" : etOtpBox3.getText().toString();
            inputKodeUserArray[3] = etOtpBox4.getText().toString().equals("") ? "" : etOtpBox4.getText().toString();
            inputKodeUserArray[4] = etOtpBox5.getText().toString().equals("") ? "" : etOtpBox5.getText().toString();
        }

        outState.putStringArray("input_kode_user_array", inputKodeUserArray);
        outState.putString("kode_verifikasi", kodeVerifikasi);
        outState.putString("progress_dialog_title", progressDialogTitle);
        outState.putString("progress_dialog_message", progressDialogMessage);
        outState.putBoolean("is_filling_otp", isFillingOtp);
        outState.putBoolean("on_progress_dialog", onProgressDialog);
        outState.putBoolean("sbToastIsShown", sbToast.isShown());
    }

    //    private class SendMail extends AsyncTask<Message, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            isProcess(true);
//        }
//
//        @Override
//        protected String doInBackground(Message... messages) {
//            try {
//                Transport.send(messages[0]);
//                isProcess(false);
//                return "Sukses";
//            }
//            catch (Exception e){
//                isProcess(false);
//                e.printStackTrace();
//                return "Gagal";
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            if(s.equals("Sukses")){
//                Toast.makeText(SigninActivity.this, "Email Verifikasi Terkirim", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                Toast.makeText(SigninActivity.this, "Email Verifikasi Tidak Terkirim", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
