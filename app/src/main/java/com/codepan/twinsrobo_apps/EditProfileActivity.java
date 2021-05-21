package com.codepan.twinsrobo_apps;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.codepan.twinsrobo_apps.APIs.APIRequestData;
import com.codepan.twinsrobo_apps.APIs.RetroServer;
import com.codepan.twinsrobo_apps.Adapters.AdapterAvatarEdit;
import com.codepan.twinsrobo_apps.Models.ResponseModelUser;
import com.codepan.twinsrobo_apps.OtherClass.EncodeDecodeImage;
import com.codepan.twinsrobo_apps.OtherClass.ListAvatar;
import com.codepan.twinsrobo_apps.OtherClass.StringDateFormat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class EditProfileActivity extends AppCompatActivity {

    private Random randomAvatar;
    private int indexAvatarImage = -1;
    private final ListAvatar listAvatar = new ListAvatar();

    private static final String PATH_FILE_IMAGE_STATE = "/Android/data/com.codepan.twinsrobo_apps/photo_cache/";
//    private static final int CAMERA_PERM_CODE = 101;
//    private static final int CAMERA_REQ_CODE = 102;

    private Dialog popUpDialog, popUpAvatarDialog;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private ProgressBar pbProfileEdit;

    private RelativeLayout rlDeletePhotoOnFrame, rlOpenGallery, rlOpenAvatar, rlEditMaleButton, rlEditFemaleButton;
    private FloatingActionButton fabEditFotoPicker;
    private ImageView ivBackArrowPL, ivEditDatePicker, ivEditPhotoFrame, ivClosePopUpPhotoPicker, ivClosePopUpAvatarPicker, ivEditPhotoFrameInvisible;
    private EditText etEditTanggalLahir, etEditEmail, etEditUsername, etEditNamaDepan, etEditNamaBelakang, etEditNamaSekolah, etPassEdit;
    private TextView tvEditMaleButton, tvEditFemaleButton;
    private Button btnSaveEdit;
    private CheckBox cbShowPasswordEdit;

    private RecyclerView rvAvatarPicker;
    private List<Integer> imageAvatar;

    private BitmapDrawable drawableState;
    private Bitmap bitmapState;

    private boolean showingDialog = false, showingAvatarDialog = false, photoWasModified = false, isFirstOpened = true;
    private String fotoFrameMode = "Foto", gender = "Male", idUser, linkFoto, uriFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        pbProfileEdit = findViewById(R.id.pbProfileEdit);
        ivBackArrowPL = findViewById(R.id.ivBackArrowPL);
        ivEditDatePicker = findViewById(R.id.ivEditDatePicker);
        etEditTanggalLahir = findViewById(R.id.etEditTanggalLahir);
        ivEditPhotoFrame = findViewById(R.id.ivEditPhotoFrame);
        ivEditPhotoFrameInvisible = findViewById(R.id.ivEditPhotoFrameInvisible);
        fabEditFotoPicker = findViewById(R.id.fabEditFotoPicker);
        etEditEmail = findViewById(R.id.etEditEmail);
        etEditUsername = findViewById(R.id.etEditUsername);
        etEditNamaDepan = findViewById(R.id.etEditNamaDepan);
        etEditNamaBelakang = findViewById(R.id.etEditNamaBelakang);
        etEditNamaSekolah = findViewById(R.id.etEditNamaSekolah);
        tvEditMaleButton = findViewById(R.id.tvEditMaleButton);
        tvEditFemaleButton = findViewById(R.id.tvEditFemaleButton);
        rlEditMaleButton = findViewById(R.id.rlEditMaleButton);
        rlEditFemaleButton = findViewById(R.id.rlEditFemaleButton);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);
        etPassEdit = findViewById(R.id.etPassEdit);
        cbShowPasswordEdit = findViewById(R.id.cbShowPasswordEdit);
        myCalendar = Calendar.getInstance();

        if (savedInstanceState != null) {
            isFirstOpened = savedInstanceState.getBoolean("isFirstOpened");
            showingDialog = savedInstanceState.getBoolean("showingDialogEditPhoto");
            showingAvatarDialog = savedInstanceState.getBoolean("showingAvatarDialogEdit");
            photoWasModified = savedInstanceState.getBoolean("pengubahanFoto");
            indexAvatarImage = savedInstanceState.getInt("indexEditAvatarImage");
            fotoFrameMode = savedInstanceState.getString("fotoEditFrameMode");
            gender = savedInstanceState.getString("jenisKelaminEdit");
            linkFoto = savedInstanceState.getString("linkFoto");
            uriFoto = savedInstanceState.getString("uriFoto");
            idUser = savedInstanceState.getString("idUser");

            etEditUsername.setText(savedInstanceState.getString("editUsername"));
            etEditNamaDepan.setText(savedInstanceState.getString("editNamaDepan"));
            etEditNamaBelakang.setText(savedInstanceState.getString("editNamaBelakang"));
            etEditTanggalLahir.setText(savedInstanceState.getString("editTanggalLahir"));
            etEditNamaSekolah.setText(savedInstanceState.getString("editNamaSekolah"));
            etEditEmail.setText(savedInstanceState.getString("editEmail"));
            etPassEdit.setText(savedInstanceState.getString("editPasswordCek"));
        }
        else {
            Bundle extras = getIntent().getExtras();
            idUser = extras.getString("id");
            etEditUsername.setText(extras.getString("username"));
            etEditNamaDepan.setText(extras.getString("namaDepan"));
            etEditNamaBelakang.setText(extras.getString("namaBelakang"));
            etEditNamaSekolah.setText(extras.getString("namaSekolah"));
            etEditEmail.setText(extras.getString("email"));
            gender = extras.getString("gender");
            etEditTanggalLahir.setText(new StringDateFormat().getStringDate2(extras.getString("tglLahir")));
            linkFoto = extras.getString("linkFoto");

            if(linkFoto.length() > 15){
                if(!linkFoto.equals("/twinsrobo/image/image-user/default/none.png")){
                    fotoFrameMode = "Foto";
                }
                else fotoFrameMode = "Kosong";
            }
            else {
                fotoFrameMode = "Avatar";
                indexAvatarImage = Integer.parseInt(linkFoto);
            }
        }

///////////////////////////// Membuat Kalender Biasa ///////////////////////////
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int tahun, int bulan, int tanggal) {
                myCalendar.set(Calendar.YEAR, tahun);
                myCalendar.set(Calendar.MONTH, bulan);
                myCalendar.set(Calendar.DAY_OF_MONTH, tanggal);

                String formatTanggal = "dd-MMMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal, Locale.US);

                etEditTanggalLahir.setText(sdf.format(myCalendar.getTime()));
            }
        };

        ivEditDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditProfileActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////// Membuat Kalender Spinner /////////////////////////
//        ivDatePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Calendar kalender = Calendar.getInstance();
//                int tahun = kalender.get(Calendar.YEAR);
//                int bulan = kalender.get(Calendar.MONTH);
//                int tanggal = kalender.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialogKalender = new DatePickerDialog(
//                        UserProfileActivity.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        date,
//                        tahun, bulan, tanggal);
//                dialogKalender.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialogKalender.show();
//            }
//        });
//
//        date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int tahun, int bulan, int tanggal) {
//                bulan += 1;
//                String selectedDate = tanggal + "/" + bulan + "/" + tahun;
//                etDateResult.setText(selectedDate);
//            }
//        };
//////////////////////////////////////////////////////////////////////////////////////////////

        ivBackArrowPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fabEditFotoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoPickerDialog();
            }
        });

        rlEditMaleButton.setOnClickListener(new View.OnClickListener() {
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

        rlEditFemaleButton.setOnClickListener(new View.OnClickListener() {
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

        cbShowPasswordEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbShowPasswordEdit.isChecked()) {
                    etPassEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else if (!cbShowPasswordEdit.isChecked()) {
                    etPassEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                Typeface typeFace = ResourcesCompat.getFont(EditProfileActivity.this, R.font.msmedium);
                etPassEdit.setTypeface(typeFace);
            }
        });

        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isIncompleteForm()) {
                    isProcess(true);
                    APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
                    Call<ResponseModelUser> requestUpdate = ardData.ardUpdateUser(idUser,
                                                                                    etEditUsername.getText().toString(),
                                                                                    etEditEmail.getText().toString(),
                                                                                    etEditNamaDepan.getText().toString(),
                                                                                    etEditNamaBelakang.getText().toString(),
                                                                                    gender,
                                                                                    new StringDateFormat().getStringDate(etEditTanggalLahir.getText().toString()),
                                                                                    etEditNamaSekolah.getText().toString(),
                                                                                    howUpdatePhoto(),
                                                                                    etPassEdit.getText().toString());
                    requestUpdate.enqueue(new Callback<ResponseModelUser>() {
                        @Override
                        public void onResponse(Call<ResponseModelUser> call, Response<ResponseModelUser> response) {
                            if(response.body().getKodeUser() == 0){
                                Toast.makeText(EditProfileActivity.this, "Update gagal", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.body().getKodeUser() == 1){
//                                Toast.makeText(EditProfileActivity.this, "Update Berhasil", Toast.LENGTH_SHORT).show();

//                                Intent resultIntent = new Intent();
                                setResult(RESULT_OK);
                                finish();
                            }
                            else if(response.body().getKodeUser() == 2){
                                Toast.makeText(EditProfileActivity.this, "Password salah / data tidak ada", Toast.LENGTH_SHORT).show();
                            }
                            isProcess(false);
                        }

                        @Override
                        public void onFailure(Call<ResponseModelUser> call, Throwable t) {
                            Toast.makeText(EditProfileActivity.this, "Gagal menghubungi server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            isProcess(false);
                        }
                    });
                } else {
                    Toast.makeText(EditProfileActivity.this, "Form Tidak Lengkap", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showPhotoPickerDialog() {
        showingDialog = true;
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
                changeEditPhoto();
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
                deleteEditPhoto();
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
        AdapterAvatarEdit adapterAvatarEdit;
        popUpAvatarDialog = new Dialog(this);
        popUpAvatarDialog.setContentView(R.layout.avatar_picker_popup);
        ivClosePopUpAvatarPicker = popUpAvatarDialog.findViewById(R.id.ivClosePopUpAvatarPicker);
        rvAvatarPicker = popUpAvatarDialog.findViewById(R.id.rvAvatarPicker);

        imageAvatar = new ArrayList<>();

        imageAvatar = listAvatar.takeAvatar(gender);

        adapterAvatarEdit = new AdapterAvatarEdit(this, imageAvatar);

        if (popUpAvatarDialog.findViewById(R.id.llAvatarPallete_Potrait) != null) {
            columnCount = 3;
        } else {
            columnCount = 4;
        }
        GridLayoutManager gridLayoutManagerAvatar = new GridLayoutManager(this, columnCount, GridLayoutManager.VERTICAL, false);
        rvAvatarPicker.setLayoutManager(gridLayoutManagerAvatar);
        rvAvatarPicker.setAdapter(adapterAvatarEdit);

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

    public void applyingAvatarPickerDialogFromEPA(int idOfAvatar) {
        popUpAvatarDialog.dismiss();
        showingAvatarDialog = false;
        indexAvatarImage = idOfAvatar;
        setAvatar();
    }

    private void deleteEditPhoto() {
        Glide.with(this)
                .load(R.drawable.ic_account_circle2)
                .apply(RequestOptions.circleCropTransform())
                .into(ivEditPhotoFrame);
        fotoFrameMode = "Kosong";
        genderSelected();
    }

    private void changeEditPhoto() {
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
                .into(ivEditPhotoFrame);

        genderSelected();
        fotoFrameMode = "Avatar";
        photoWasModified = true;
    }

    private void genderSelected() {
        if (gender.equals("Male")) {
            rlEditMaleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_selected));
            tvEditMaleButton.setTextColor(ContextCompat.getColor(this, R.color.colorwhite));
            rlEditFemaleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.input_field));
            tvEditFemaleButton.setTextColor(ContextCompat.getColor(this, R.color.colornormaltext));
        } else {
            rlEditFemaleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_selected));
            tvEditFemaleButton.setTextColor(ContextCompat.getColor(this, R.color.colorwhite));
            rlEditMaleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.input_field));
            tvEditMaleButton.setTextColor(ContextCompat.getColor(this, R.color.colornormaltext));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                uriFoto = resultUri.toString();
                ivEditPhotoFrame.setImageURI(resultUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    fotoFrameMode = "Foto";
//                    saveImage("updating");
                    photoWasModified = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void saveImage(String withUpdating) throws IOException {
        drawableState = (BitmapDrawable) ivEditPhotoFrame.getDrawable();
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
        if(withUpdating.equals("updating")){
            photoWasModified = true;
        }
    }

    private boolean isIncompleteForm() {
        if (etEditUsername.getText().toString().trim().isEmpty()) etEditUsername.setError("Wajib Diisi");
        if (etEditEmail.getText().toString().trim().isEmpty()) etEditEmail.setError("Wajib Diisi");
        if (etEditNamaDepan.getText().toString().trim().isEmpty()) etEditNamaDepan.setError("Wajib Diisi");
        if (etEditNamaBelakang.getText().toString().trim().isEmpty()) etEditNamaBelakang.setError("Wajib Diisi");
        if (etEditNamaSekolah.getText().toString().trim().isEmpty()) etEditNamaSekolah.setError("Wajib Diisi");
        if (etPassEdit.getText().toString().trim().isEmpty()) etPassEdit.setError("Wajib Diisi");

        if (etEditUsername.getText().toString().trim().isEmpty()) return true;
        if (etEditEmail.getText().toString().trim().isEmpty()) return true;
        if (etEditNamaDepan.getText().toString().trim().isEmpty()) return true;
        if (etEditNamaBelakang.getText().toString().trim().isEmpty()) return true;
        if (etEditNamaSekolah.getText().toString().trim().isEmpty()) return true;
        if (etPassEdit.getText().toString().trim().isEmpty()) return true;
        return false;
    }

    private String howUpdatePhoto(){
        String strFoto = "";

        if(photoWasModified){
            if(fotoFrameMode.equals("Foto")){
                drawableState = (BitmapDrawable) ivEditPhotoFrameInvisible.getDrawable();
                bitmapState = drawableState.getBitmap();
                strFoto = new EncodeDecodeImage().getStringImage(bitmapState);
            }
            else if(fotoFrameMode.equals("Avatar")){
                strFoto = String.valueOf(indexAvatarImage);
            }
            else if(fotoFrameMode.equals("Kosong")){
                strFoto = "hapusfoto";
            }
        }

        return strFoto;
    }

    private void isProcess(boolean process){
        if(process){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            pbProfileEdit.setVisibility(View.VISIBLE);
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            pbProfileEdit.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        Glide.with(this)
//                .load(R.drawable.sample_foto_profil)
//                .apply(RequestOptions.circleCropTransform())
//                .into(ivEditPhotoFrame);

        if (showingDialog) {
            showPhotoPickerDialog();
        }

        if (showingAvatarDialog) {
            showAvatarPickerDialog();
        }

        if (fotoFrameMode == "Foto") {
            if(isFirstOpened){
                Glide.get(EditProfileActivity.this).clearMemory();
            }
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.drawable.broken_image);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);

            if (photoWasModified) {
                requestOptions.skipMemoryCache(true);
                Glide.with(this)
                        .load(Uri.parse(uriFoto))
                        .apply(RequestOptions.circleCropTransform())
                        .apply(requestOptions)
                        .into(ivEditPhotoFrame);
                ivEditPhotoFrameInvisible.setImageURI(Uri.parse(uriFoto));
//                Glide.with(this)
//                        .load(new File(Environment.getExternalStorageDirectory(), PATH_FILE_IMAGE_STATE + "ImageState.jpg"))
//                        .apply(RequestOptions.circleCropTransform())
//                        .apply(requestOptions)
//                        .into(ivEditPhotoFrame);
//                Glide.with(this)
//                        .load(new File(Environment.getExternalStorageDirectory(), PATH_FILE_IMAGE_STATE + "ImageState.jpg"))
//                        .apply(requestOptions)
//                        .into(ivEditPhotoFrameInvisible);
            } else {
                requestOptions.skipMemoryCache(false);
                requestOptions.placeholder(R.drawable.anim_loading);
                Glide.with(this)
                        .load(linkFoto)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(requestOptions)
                        .into(ivEditPhotoFrame);
            }

            genderSelected();
        } else if (fotoFrameMode == "Avatar") {
            setAvatar();
        } else if (fotoFrameMode == "AvatarRandom") {
            setRandomAvatar();
        } else if (fotoFrameMode == "Kosong") {
            deleteEditPhoto();
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("isFirstOpened", false);
        outState.putBoolean("showingDialogEditPhoto", showingDialog);
        outState.putBoolean("showingAvatarDialogEdit", showingAvatarDialog);
        outState.putBoolean("pengubahanFoto", photoWasModified);
        outState.putInt("indexEditAvatarImage", indexAvatarImage);
        outState.putString("fotoEditFrameMode", fotoFrameMode);
        outState.putString("jenisKelaminEdit", gender);
        outState.putString("linkFoto", linkFoto);
        outState.putString("uriFoto", uriFoto);

        outState.putString("idUser", idUser);
        outState.putString("editUsername", etEditUsername.getText().toString());
        outState.putString("editNamaDepan", String.valueOf(etEditNamaDepan));
        outState.putString("editNamaBelakang", String.valueOf(etEditNamaBelakang));
        outState.putString("editTanggalLahir", String.valueOf(etEditTanggalLahir));
        outState.putString("editNamaSekolah", String.valueOf(etEditNamaSekolah));
        outState.putString("editEmail", String.valueOf(etEditEmail));
        outState.putString("editPasswordCek", String.valueOf(etPassEdit));
    }
}