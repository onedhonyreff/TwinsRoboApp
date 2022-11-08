package com.codepan.twinsrobo_apps;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepan.twinsrobo_apps.APIs.APIRequestData;
import com.codepan.twinsrobo_apps.APIs.RetroServer;
import com.codepan.twinsrobo_apps.Adapters.AdapterChampionship;
import com.codepan.twinsrobo_apps.Models.DataModelChampion;
import com.codepan.twinsrobo_apps.Models.ServerResponse;
import com.codepan.twinsrobo_apps.OtherClass.UriUtils;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private MediaController mediaController;

    private RecyclerView rvListCatChampion;
    private RecyclerView.Adapter adDataCatChampion;
    private RecyclerView.LayoutManager lmDataCatChampion;

    private Spinner spProgramSelectorMG;
    private Button btnBrowsePathMG, btnSaveMG;
    private FrameLayout flContainerVideoMG;
    private VideoView vvVideoMG;

    private ImageView ivBackArrowMG;
    private EditText etNamaMG, etJenisRobotMG, etKecMotorMG;
    private TextView tvLineNumberProgramPreviewMG, tvProgramPreviewMG, tvPathUploadVideoMG, tvDurasiVideoMG;

    private String[] myProgram;
    private Uri videoUri;

    private String videoPath;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_games);

        ivBackArrowMG = findViewById(R.id.ivBackArrowMG);
        ivBackArrowMG.setOnClickListener(this);

        flContainerVideoMG = findViewById(R.id.flContainerVideoMG);
        vvVideoMG = findViewById(R.id.vvVideoMG);
        spProgramSelectorMG = findViewById(R.id.spProgramSelectorMG);
        btnBrowsePathMG = findViewById(R.id.btnBrowsePathMG);
        rvListCatChampion = findViewById(R.id.rvCatChampion);
        etNamaMG = findViewById(R.id.etNamaMG);
        etJenisRobotMG = findViewById(R.id.etJenisRobotMG);
        etKecMotorMG = findViewById(R.id.etKecMotorMG);
        tvPathUploadVideoMG = findViewById(R.id.tvPathUploadVideoMG);
        tvProgramPreviewMG = findViewById(R.id.tvProgramPreviewMG);
        tvLineNumberProgramPreviewMG = findViewById(R.id.tvLineNumberProgramPreviewMG);
        tvDurasiVideoMG = findViewById(R.id.tvDurasiVideoMG);

        btnSaveMG = findViewById(R.id.btnSaveMG);

        mediaController = new MediaController(this);

        myProgram = getResources().getStringArray(R.array.list_programs);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.plan_name));
        adapterSpinner.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spProgramSelectorMG.setAdapter(adapterSpinner);

        if (savedInstanceState != null) {
            etNamaMG.setText(savedInstanceState.getString("etNamaMG"));
            etJenisRobotMG.setText(savedInstanceState.getString("etJenisRobotMG"));
            etKecMotorMG.setText(savedInstanceState.getString("etKecMotorMG"));
            tvPathUploadVideoMG.setText(savedInstanceState.getString("tvPathUploadVideoMG"));
        }

        btnBrowsePathMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Open Galery"), 6328);
            }
        });

        spProgramSelectorMG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showProgramPreview(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSaveMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoUri != null){
                    uploadFile();
                }else{
                    Toast.makeText(DemoActivity.this, "Please select a video", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showListCatChampion();

        initDialog();

        verifyStoragePermissions(this);
    }

    private void showListCatChampion() {

        List<DataModelChampion> dataChampion = new ArrayList<>();

        TypedArray imageChampion = getResources().obtainTypedArray(R.array.photo_champion);

        for (int i = 0; i < getResources().getStringArray(R.array.photo_champion).length; i++){
            DataModelChampion tempDataChampion = new DataModelChampion();
            tempDataChampion.setFoto(imageChampion.getResourceId(i, 0));
            tempDataChampion.setNama(getResources().getStringArray(R.array.name_champion)[i]);
            tempDataChampion.setSekolah(getResources().getStringArray(R.array.school_champion)[i]);
            tempDataChampion.setPoint(getResources().getStringArray(R.array.point_champion)[i]);

            dataChampion.add(tempDataChampion);
        }

        adDataCatChampion = new AdapterChampionship(DemoActivity.this, dataChampion);
        lmDataCatChampion = new LinearLayoutManager(DemoActivity.this, LinearLayoutManager.VERTICAL, false);
        rvListCatChampion.setLayoutManager(lmDataCatChampion);
        rvListCatChampion.setAdapter(adDataCatChampion);
        adDataCatChampion.notifyDataSetChanged();
    }

    private void showProgramPreview(int spinnerPosition){
        tvProgramPreviewMG.setText(myProgram[spinnerPosition]);

        tvLineNumberProgramPreviewMG.setText("");
        for (int i = 0; i < myProgram[spinnerPosition].split("\n").length; i++){
            if (i != 0 ){
                tvLineNumberProgramPreviewMG.setText(tvLineNumberProgramPreviewMG.getText().toString() + "\n");
            }
            tvLineNumberProgramPreviewMG.setText(tvLineNumberProgramPreviewMG.getText().toString() + (i + 1) + ".");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 6328 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videoUri = data.getData();

            videoPath = UriUtils.getPathFromUri(this, videoUri);
            Toast.makeText(this, videoPath, Toast.LENGTH_SHORT).show();

            tvPathUploadVideoMG.setText(videoUri.toString());
            flContainerVideoMG.setVisibility(View.VISIBLE);
            tvDurasiVideoMG.setVisibility(View.VISIBLE);

            vvVideoMG.setVideoURI(videoUri);

            vvVideoMG.setMediaController(mediaController);
            mediaController.setAnchorView(vvVideoMG);
            vvVideoMG.start();

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(this, videoUri);
            long videoDuration = Long.parseLong(
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            );
            retriever.release();

            tvDurasiVideoMG.setText("Duration : "
                    + videoDuration/1000 + "," +
                    videoDuration%1000 + " Second");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackArrowMG:
                finish();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("etNamaMG", String.valueOf(etNamaMG));
        outState.putString("etJenisRobotMG", String.valueOf(etJenisRobotMG));
        outState.putString("etKecMotorMG", String.valueOf(etKecMotorMG));
        outState.putString("tvPathUploadVideoMG", String.valueOf(tvPathUploadVideoMG));
    }

    private void uploadFile() {
        if (videoUri == null || videoUri.equals("")) {
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show();
            return;
        } else {
            showpDialog();

            // Map is used to multipart the file using okhttp3.RequestBody
            Map<String, RequestBody> map = new HashMap<>();
            File file = new File(videoPath);
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
            APIRequestData getResponse = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ServerResponse> call = getResponse.upload("token", map);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            hidepDialog();
                            ServerResponse serverResponse = response.body();
                            Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        hidepDialog();
                        Toast.makeText(getApplicationContext(), "1 problem uploading image", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    hidepDialog();
                    Log.v("Response gotten is", t.getMessage());
                    Toast.makeText(getApplicationContext(), "2 problem uploading image " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else {
            return null;
        }
    }

    public String convertMediaUriToPath(Uri uri) {
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj,  null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Uploading");
        pDialog.setCancelable(true);
    }


    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }
}