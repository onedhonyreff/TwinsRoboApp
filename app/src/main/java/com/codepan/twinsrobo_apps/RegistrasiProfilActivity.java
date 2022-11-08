package com.codepan.twinsrobo_apps;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.codepan.twinsrobo_apps.APIs.APIRequestData;
import com.codepan.twinsrobo_apps.APIs.RetroServer;
import com.codepan.twinsrobo_apps.Adapters.AdapterAvatar;
import com.codepan.twinsrobo_apps.Models.ResponseModelUser;
import com.codepan.twinsrobo_apps.OtherClass.EncodeDecodeImage;
import com.codepan.twinsrobo_apps.OtherClass.ListAvatar;
import com.codepan.twinsrobo_apps.OtherClass.StringDateFormat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaeger.library.StatusBarUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrasiProfilActivity extends AppCompatActivity {

    private Random randomAvatar;
    private int indexAvatarImage = -1;
    private ListAvatar listAvatar = new ListAvatar();

    private static final String PATH_FILE_IMAGE_STATE = "/Android/data/com.codepan.twinsrobo_apps/photo_cache/";
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQ_CODE = 102;
    private Dialog popUpDialog, popUpAvatarDialog;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private FloatingActionButton fabRegFotoPicker;
    private LinearLayout changeEmailOrPassword;
    private RelativeLayout rlDeletePhotoOnFrame, rlOpenGallery, rlOpenAvatar, rlMaleButton, rlFemaleButton;
    private ImageView ivRegPhotoFrame, ivRegDatePicker, ivClosePopUpPhotoPicker, ivClosePopUpAvatarPicker;
    private EditText etRegTanggalLahir, etRegUsername, etRegNamaDepan, etRegNamaBelakang, etRegNamaSekolah;
    private TextView tvMaleButton, tvFemaleButton;
    private Button btnSignUp;
    private ProgressBar pbRegister;

    private RecyclerView rvAvatarPicker;
    private List<Integer> imageAvatar;

//    private GenderChooser crbLaki, crbPerempuan;

    private final int REQUEST_GALLERY = 9544;
    private Uri imageUri;
    private BitmapDrawable drawableState;
    private Bitmap bitmapState;

    private boolean showingDialog = false, showingAvatarDialog = false;
    private String fotoFrameMode = "AvatarRandom", gender, regEmail = "N/A", regPass = "N/A", strFoto, uriFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_profil);

        StatusBarUtil.setTransparent(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        /////////////////////////////////// terima passing data //////////////////////////////

        Intent receiverIntenData = getIntent();
        regEmail = receiverIntenData.getStringExtra("regEmail");
        regPass = receiverIntenData.getStringExtra("regPass");

        /////////////////////////////////// finding value ////////////////////////////////////

//        crbLaki = findViewById(R.id.crbLaki);
//        crbPerempuan = findViewById(R.id.crbPerempuan);

        ivRegPhotoFrame = findViewById(R.id.ivRegPhotoFrame);
        ivRegDatePicker = findViewById(R.id.ivRegDatePicker);
        fabRegFotoPicker = findViewById(R.id.fabRegFotoPicker);
        changeEmailOrPassword = findViewById(R.id.changeEmailOrPassword);
        rlMaleButton = findViewById(R.id.rlMaleButton);
        rlFemaleButton = findViewById(R.id.rlFemaleButton);
        tvMaleButton = findViewById(R.id.tvMaleButton);
        tvFemaleButton = findViewById(R.id.tvFemaleButton);
        btnSignUp = findViewById(R.id.btnSignUp);
        etRegUsername = findViewById(R.id.etRegUsername);
        etRegNamaDepan = findViewById(R.id.etRegNamaDepan);
        etRegNamaBelakang = findViewById(R.id.etRegNamaBelakang);
        etRegTanggalLahir = findViewById(R.id.etRegTanggalLahir);
        etRegNamaSekolah = findViewById(R.id.etRegNamaSekolah);
        pbRegister = findViewById(R.id.pbRegister);
        myCalendar = Calendar.getInstance();

        if (savedInstanceState != null) {
            showingDialog = savedInstanceState.getBoolean("popUp");
            showingAvatarDialog = savedInstanceState.getBoolean("popUpAvatar");
            indexAvatarImage = savedInstanceState.getInt("recentAvatar");
            fotoFrameMode = savedInstanceState.getString("modeFoto");
            gender = savedInstanceState.getString("jenisKelamin");
            regEmail = savedInstanceState.getString("regEmail");
            regPass = savedInstanceState.getString("regPass");
            uriFoto = savedInstanceState.getString("uriFoto");

            etRegUsername.setText(savedInstanceState.getString("regUsername"));
            etRegNamaDepan.setText(savedInstanceState.getString("regNamaDepan"));
            etRegNamaBelakang.setText(savedInstanceState.getString("regNamaBelakang"));
            etRegTanggalLahir.setText(savedInstanceState.getString("regTanggalLahir"));
            etRegNamaSekolah.setText(savedInstanceState.getString("regNamaSekolah"));
        }

        ///////////////////////////////////////////////////////////////////////////////////////////

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int tahun, int bulan, int tanggal) {
                myCalendar.set(Calendar.YEAR, tahun);
                myCalendar.set(Calendar.MONTH, bulan);
                myCalendar.set(Calendar.DAY_OF_MONTH, tanggal);

                String formatTanggal = "dd-MMMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal, Locale.US);
                etRegTanggalLahir.setText(sdf.format(myCalendar.getTime()));
                etRegTanggalLahir.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        };

//        setCustomRadioButtonListener();

        ivRegDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(
                        RegistrasiProfilActivity.this,
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        changeEmailOrPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_up_out_anim);
            }
        });

        fabRegFotoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!fotoFrameIsNull){
//                    showPhotoPickerDialog();
//                }
//                else {
//                    changeRegisterPhoto();
//                }
                showPhotoPickerDialog();
            }
        });

        rlMaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gender != "Male") {
                    gender = "Male";
                    genderSelected();
                    if (fotoFrameMode == "Avatar") {
                        setRandomAvatarMaleOrFemale();
                    }
                }
            }
        });

        rlFemaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gender != "Female") {
                    gender = "Female";
                    genderSelected();
                    if (fotoFrameMode == "Avatar") {
                        setRandomAvatarMaleOrFemale();
                    }
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isIncompleteForm()) {

                    isProcess(true);

                    String username = etRegUsername.getText().toString().trim();
                    String nama_depan = etRegNamaDepan.getText().toString().trim();
                    String nama_belakang = etRegNamaBelakang.getText().toString().trim();
                    String nama_sekolah = etRegNamaSekolah.getText().toString().trim();
                    String tanggal_lahir = new StringDateFormat().getStringDate(etRegTanggalLahir.getText().toString());

                    if(fotoFrameMode == "Foto"){
                        drawableState = (BitmapDrawable) ivRegPhotoFrame.getDrawable();
                        bitmapState = drawableState.getBitmap();
                        strFoto = new EncodeDecodeImage().getStringImage(bitmapState);
                    }
                    else if(fotoFrameMode == "Avatar"){
//                        strFoto = String.valueOf(listAvatar.getAvatar(gender, indexAvatarImage));
                        strFoto = String.valueOf(indexAvatarImage);
                    }

                    APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
                    Call<ResponseModelUser> requestDataRegister = ardData.ardRegister(regEmail, regPass, username, nama_depan, nama_belakang, gender, tanggal_lahir, nama_sekolah, strFoto);
                    requestDataRegister.enqueue(new Callback<ResponseModelUser>() {
                        @Override
                        public void onResponse(Call<ResponseModelUser> call, Response<ResponseModelUser> response) {
                            Toast.makeText(RegistrasiProfilActivity.this, "You are registered", Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent();
                            setResult(RESULT_OK);
                            finish();
                            isProcess(false);
                        }

                        @Override
                        public void onFailure(Call<ResponseModelUser> call, Throwable t) {
                            Toast.makeText(RegistrasiProfilActivity.this, "Gagal Menghubungi Server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            isProcess(false);
                        }
                    });
                } else {
                    Toast.makeText(RegistrasiProfilActivity.this, "Data Tidak Lengkap", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void setCustomRadioButtonListener() {
//        crbLaki.setOwnonCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Toast.makeText(RegistrasiProfilActivity.this, "Laki - laki", Toast.LENGTH_SHORT).show();
//            }
//        });
//        crbPerempuan.setOwnonCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Toast.makeText(RegistrasiProfilActivity.this, "Perempuan", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void showPhotoPickerDialog() {
        popUpDialog = new Dialog(this);
        popUpDialog.setContentView(R.layout.photo_picker_popup);
        ivClosePopUpPhotoPicker = popUpDialog.findViewById(R.id.ivClosePopUpPhotoPicker);
        rlDeletePhotoOnFrame = popUpDialog.findViewById(R.id.rlDeletePhotoOnFrame);
        rlOpenGallery = popUpDialog.findViewById(R.id.rlOpenGallery);
        rlOpenAvatar = popUpDialog.findViewById(R.id.rlOpenAvatar);

        ivClosePopUpPhotoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialog.dismiss();
                showingDialog = false;
            }
        });

        rlOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialog.dismiss();
                changeRegisterPhoto();
                showingDialog = false;
            }
        });

        rlOpenAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialog.dismiss();
                showAvatarPickerDialog();
                showingDialog = false;
            }
        });

        rlDeletePhotoOnFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialog.dismiss();
                deleteRegisterPhoto();
                showingDialog = false;
            }
        });

        popUpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpDialog.setCancelable(false);
        popUpDialog.show();
        showingDialog = true;
    }

    private void showAvatarPickerDialog() {
        showingAvatarDialog = true;
        int columnCount;
        AdapterAvatar adapterAvatar;
        popUpAvatarDialog = new Dialog(this);
        popUpAvatarDialog.setContentView(R.layout.avatar_picker_popup);
        ivClosePopUpAvatarPicker = popUpAvatarDialog.findViewById(R.id.ivClosePopUpAvatarPicker);
        rvAvatarPicker = popUpAvatarDialog.findViewById(R.id.rvAvatarPicker);

        imageAvatar = new ArrayList<>();

        imageAvatar = listAvatar.takeAvatar(gender);

        adapterAvatar = new AdapterAvatar(this, imageAvatar);

        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            columnCount = 3;
        } else {
            columnCount = 4;
        }
        GridLayoutManager gridLayoutManagerAvatar = new GridLayoutManager(this, columnCount, GridLayoutManager.VERTICAL, false);
        rvAvatarPicker.setLayoutManager(gridLayoutManagerAvatar);
        rvAvatarPicker.setAdapter(adapterAvatar);

        ivClosePopUpAvatarPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpAvatarDialog.dismiss();
                showingAvatarDialog = false;
            }
        });

        popUpAvatarDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpAvatarDialog.setCancelable(false);
        popUpAvatarDialog.show();
    }

    public void applyingAvatarPickerDialogFromRPA(int idOfAvatar) {
        popUpAvatarDialog.dismiss();
        showingAvatarDialog = false;
        indexAvatarImage = idOfAvatar;
        setAvatar();
    }

//    private void askCameraPermission() {
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
//        }
//        else {
//            openCamera();
//        }
//    }

    private void openCamera() {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQ_CODE);
    }

    private void deleteRegisterPhoto() {
        Glide.with(this)
                .load(R.drawable.ic_account_circle)
                .apply(RequestOptions.circleCropTransform())
                .into(ivRegPhotoFrame);
        fotoFrameMode = "Kosong";
        genderSelected();
        strFoto = "";
    }

    private void changeRegisterPhoto() {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Open Galery"), REQUEST_GALLERY);
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    private void setRandomAvatarMaleOrFemale() {
        randomAvatar = new Random();
        indexAvatarImage = randomAvatar.nextInt(listAvatar.getAvatarLenght(gender) - 1);
        setAvatar();
    }

    private void setRandomAvatar() {
        if (indexAvatarImage == -1) {
            randomAvatar = new Random();
            indexAvatarImage = randomAvatar.nextInt(listAvatar.getAvatarLenght() - 1);
            if (indexAvatarImage < listAvatar.getAvatarLenght("Male")) {
                gender = "Male";
            } else {
                gender = "Female";
                indexAvatarImage -= listAvatar.getAvatarLenght("Male");
            }
            setAvatar();
        }
    }

    private void setAvatar() {
        Glide.with(this)
                .load(listAvatar.getAvatar(gender, indexAvatarImage))
                .apply(RequestOptions.circleCropTransform())
                .into(ivRegPhotoFrame);

        genderSelected();
        fotoFrameMode = "Avatar";
    }

    private void genderSelected() {
        if (gender == "Male") {
//            tvMaleButton.setTextColor(ContextCompat.getColor(this, R.color.colorwhite));
//            tvFemaleButton.setTextColor(ContextCompat.getColor(this, R.color.colornormaltext));

            rlMaleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_selected));
            tvMaleButton.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.ic_checklist), null);
            rlFemaleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.gender_selector));
            tvFemaleButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
//            tvFemaleButton.setTextColor(ContextCompat.getColor(this, R.color.colorwhite));
//            tvMaleButton.setTextColor(ContextCompat.getColor(this, R.color.colornormaltext));

            rlFemaleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_selected));
            tvMaleButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            rlMaleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.gender_selector));
            tvFemaleButton.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.ic_checklist), null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Aplikasi tidak diizinkan untuk mengakses kamera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            imageUri = data.getData();
//            try {
//
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//
////                ivRegPhotoFrame.setImageBitmap(bitmap);
//
//                strRegFoto = new EncodeDecodeImage().getStringImage(bitmap);
//
//                Glide.with(this)
//                        .load(strRegFoto)
//                        .apply(RequestOptions.circleCropTransform())
//                        .into(ivRegPhotoFrame);
//
//                fotoFrameIsNull = false;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        if (requestCode == CAMERA_REQ_CODE && resultCode == RESULT_OK){
//            Bitmap bmtImage = (Bitmap) data.getExtras().get("data");
////            imageUri = data.getData();
////            CropImage.activity(imageUri)
////                    .start(this);
//
//            strRegFoto = new EncodeDecodeImage().getStringImage(bmtImage);
//
//            Glide.with(this)
//                    .load(strRegFoto)
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(ivRegPhotoFrame);
//
//            fotoFrameIsNull = false;
//        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                uriFoto = resultUri.toString();
                ivRegPhotoFrame.setImageURI(resultUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    fotoFrameMode = "Foto";
//                    saveImage();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void saveImage() throws IOException {
        drawableState = (BitmapDrawable) ivRegPhotoFrame.getDrawable();
        bitmapState = drawableState.getBitmap();

        FileOutputStream outputStream = null;

        File sdCardKu = Environment.getExternalStorageDirectory();
        File directoryKu = new File(sdCardKu.getAbsolutePath() + PATH_FILE_IMAGE_STATE);
        directoryKu.delete();
        directoryKu.mkdir();
        // String fileName = String.format("%d.jpg", System.currentTimeMillis());
        String fileName = String.format("%s.jpg", "ImageState");
        File outFileKu = new File(directoryKu, fileName);

        outputStream = new FileOutputStream(outFileKu);
        bitmapState.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.flush();
        outputStream.close();

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(outFileKu));
        sendBroadcast(intent);
    }

    private boolean isIncompleteForm() {
        if (etRegUsername.getText().toString().trim().isEmpty())
            etRegUsername.setError("Wajib Diisi");
        if (etRegNamaDepan.getText().toString().trim().isEmpty())
            etRegNamaDepan.setError("Wajib Diisi");
        if (etRegNamaBelakang.getText().toString().trim().isEmpty())
            etRegNamaBelakang.setError("Wajib Diisi");
        if (etRegTanggalLahir.getText().toString().trim().isEmpty()) {
            etRegTanggalLahir.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ContextCompat.getDrawable(RegistrasiProfilActivity.this, R.drawable.ic_error),
                    null);
        }
        if (etRegNamaSekolah.getText().toString().trim().isEmpty())
            etRegNamaSekolah.setError("Wajib Diisi");

        if (etRegUsername.getText().toString().trim().isEmpty()) return true;
        if (etRegNamaDepan.getText().toString().trim().isEmpty()) return true;
        if (etRegNamaBelakang.getText().toString().trim().isEmpty()) return true;
        if (etRegTanggalLahir.getText().toString().trim().isEmpty()) return true;
        if (etRegNamaSekolah.getText().toString().trim().isEmpty()) return true;
        return false;
    }

    private void isProcess(boolean process){
        if(process){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            pbRegister.setVisibility(View.VISIBLE);
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            pbRegister.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (showingDialog) {
//            if(!fotoFrameIsNull){
            showPhotoPickerDialog();
//            }
        }

        if (showingAvatarDialog) {
            showAvatarPickerDialog();
        }

        if (fotoFrameMode == "Foto") {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.ic_account_circle);
            requestOptions.error(R.drawable.ic_account_circle);

            Glide.with(this)
                    .load(uriFoto)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(requestOptions)
                    .into(ivRegPhotoFrame);
//            Glide.with(this)
//                    .load(new File(Environment.getExternalStorageDirectory(), PATH_FILE_IMAGE_STATE + "ImageState.jpg"))
//                    .apply(RequestOptions.circleCropTransform())
//                    .apply(requestOptions)
//                    .into(ivRegPhotoFrame);
            genderSelected();
        } else if (fotoFrameMode == "Avatar") {
            setAvatar();
        } else if (fotoFrameMode == "AvatarRandom") {
            setRandomAvatar();
        } else if (fotoFrameMode == "Kosong") {
            deleteRegisterPhoto();
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

        outState.putBoolean("popUp", showingDialog);
        outState.putBoolean("popUpAvatar", showingAvatarDialog);
        outState.putInt("recentAvatar", indexAvatarImage);
        outState.putString("modeFoto", fotoFrameMode);
        outState.putString("jenisKelamin", gender);
        outState.putString("regEmail", regEmail);
        outState.putString("regPass", regPass);
        outState.putString("uriFoto", uriFoto);

        outState.putString("regUsername", etRegUsername.getText().toString());
        outState.putString("regNamaDepan", String.valueOf(etRegNamaDepan));
        outState.putString("regNamaBelakang", String.valueOf(etRegNamaBelakang));
        outState.putString("regTanggalLahir", String.valueOf(etRegTanggalLahir));
        outState.putString("regNamaSekolah", String.valueOf(etRegNamaSekolah));
    }
}