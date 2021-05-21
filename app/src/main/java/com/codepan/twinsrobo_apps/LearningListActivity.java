package com.codepan.twinsrobo_apps;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepan.twinsrobo_apps.Adapters.AdapterMateri;
import com.codepan.twinsrobo_apps.Adapters.AdapterMember;
import com.codepan.twinsrobo_apps.Models.DataModelMateri;
import com.codepan.twinsrobo_apps.Models.DataModelModul;

import java.util.ArrayList;
import java.util.List;

public class LearningListActivity extends AppCompatActivity {

    private RecyclerView rvListMateri;
    private AdapterMateri adDataMateri;
    private RecyclerView.LayoutManager lmDataMateri;

    private List<DataModelMateri> dataMateri = new ArrayList<>();
    
    private ImageView ivBackArrowLL, ivButtonSearchLearnList;
    private EditText etSearchLearnList;

    ////// to future
    private int modulId = 0;
    ////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_list);

        Bundle bundle = getIntent().getExtras();

        ////// to future
        modulId = bundle.getInt("id_modul");
        ////////////////

        ivBackArrowLL = findViewById(R.id.ivBackArrowLL);
        etSearchLearnList = findViewById(R.id.etSearchLearnList);
        ivButtonSearchLearnList = findViewById(R.id.ivButtonSearchLearnList);
        rvListMateri = findViewById(R.id.rvListMateri);

        if (savedInstanceState != null) {
            etSearchLearnList.setText(savedInstanceState.getString("etSearchLearnList"));
        }

        ivBackArrowLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivButtonSearchLearnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etSearchLearnList.getText().toString().equals("")){
                    filterMateri(etSearchLearnList.getText().toString().trim());
                }
            }
        });

        etSearchLearnList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals("")){
                    showListMateri();
                }
            }
        });

        showListMateri();
    }

    private void showListMateri() {

        dataMateri = new ArrayList<>();

        for (int i = 0; i < getResources().getStringArray(R.array.list_judul_materi_coba).length; i++){
            DataModelMateri tempDataMateri = new DataModelMateri();
            tempDataMateri.setJudulMateri(getResources().getStringArray(R.array.list_judul_materi_coba)[i]);
            tempDataMateri.setVideoMateri(getResources().getStringArray(R.array.list_video_materi_coba)[i]);
            tempDataMateri.setIsiMateri(getResources().getStringArray(R.array.list_isi_materi_coba)[i]);

            dataMateri.add(tempDataMateri);
        }

        adDataMateri = new AdapterMateri(LearningListActivity.this, dataMateri);
        lmDataMateri = new LinearLayoutManager(LearningListActivity.this, LinearLayoutManager.VERTICAL, false);
        rvListMateri.setLayoutManager(lmDataMateri);
        rvListMateri.setAdapter(adDataMateri);
        adDataMateri.notifyDataSetChanged();
    }

    public void openMateri(String judulMateri, String videoMateri, String isiMateri){
        Intent intentMateri = new Intent(LearningListActivity.this, MateriActivity.class);
        intentMateri.putExtra("judul_materi", judulMateri);
        intentMateri.putExtra("video_materi", videoMateri);
        intentMateri.putExtra("isi_materi", isiMateri);
        startActivity(intentMateri);
    }

    private void filterMateri(String text){
        ArrayList<DataModelMateri> filteredModul = new ArrayList<>();

        for (DataModelMateri item : dataMateri){
            if (item.getJudulMateri().toLowerCase().contains(text.toLowerCase())){
                filteredModul.add(item);
            }
        }
        adDataMateri.filterList(filteredModul);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("etSearchLearnList", String.valueOf(etSearchLearnList));
    }

    @Override
    protected void onResume() {
        super.onResume();

        filterMateri(etSearchLearnList.getText().toString().trim());
    }
}