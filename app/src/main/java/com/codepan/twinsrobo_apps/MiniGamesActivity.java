package com.codepan.twinsrobo_apps;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepan.twinsrobo_apps.Adapters.AdapterChampionship;
import com.codepan.twinsrobo_apps.Models.DataModelChampion;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class MiniGamesActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaController mediaController;

    private RecyclerView rvListCatChampion;
    private RecyclerView.Adapter adDataCatChampion;
    private RecyclerView.LayoutManager lmDataCatChampion;

    private Spinner spProgramSelectorMG;
    private Button btnBrowsePathMG;
    private FrameLayout flContainerVideoMG;
    private VideoView vvVideoMG;

    private ImageView ivBackArrowMG;
    private EditText etNamaMG, etJenisRobotMG, etKecMotorMG;
    private TextView tvLineNumberProgramPreviewMG, tvProgramPreviewMG, tvPathUploadVideoMG, tvDurasiVideoMG;

    private String[] myProgram;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_games);

        ivBackArrowMG = findViewById(R.id.ivBackArrowMG);
        ivBackArrowMG.setOnClickListener(this);

        flContainerVideoMG = findViewById(R.id.flContainerVideoMG);
        vvVideoMG = findViewById(R.id.vvVideoMG);
        spProgramSelectorMG = findViewById(R.id.spProgramSelectorMG);
        rvListCatChampion = findViewById(R.id.rvCatChampion);
        etNamaMG = findViewById(R.id.etNamaMG);
        etJenisRobotMG = findViewById(R.id.etJenisRobotMG);
        etKecMotorMG = findViewById(R.id.etKecMotorMG);
        tvPathUploadVideoMG = findViewById(R.id.tvPathUploadVideoMG);
        tvProgramPreviewMG = findViewById(R.id.tvProgramPreviewMG);
        tvLineNumberProgramPreviewMG = findViewById(R.id.tvLineNumberProgramPreviewMG);
        tvDurasiVideoMG = findViewById(R.id.tvDurasiVideoMG);
        btnBrowsePathMG = findViewById(R.id.btnBrowsePathMG);

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

        showListCatChampion();
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

        adDataCatChampion = new AdapterChampionship(MiniGamesActivity.this, dataChampion);
        lmDataCatChampion = new LinearLayoutManager(MiniGamesActivity.this, LinearLayoutManager.VERTICAL, false);
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

            tvPathUploadVideoMG.setText(videoUri.toString());
            flContainerVideoMG.setVisibility(View.VISIBLE);
            tvDurasiVideoMG.setVisibility(View.VISIBLE);

            vvVideoMG.setVideoURI(videoUri);

            vvVideoMG.setMediaController(mediaController);
            mediaController.setAnchorView(vvVideoMG);
            vvVideoMG.start();

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(this, videoUri);
            long videoDuration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            retriever.release();

            tvDurasiVideoMG.setText("Duration : " + videoDuration/1000 + "," + videoDuration%1000 + " Secon");
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
}