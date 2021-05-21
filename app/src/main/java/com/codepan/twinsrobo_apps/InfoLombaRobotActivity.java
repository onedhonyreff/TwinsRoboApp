package com.codepan.twinsrobo_apps;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepan.twinsrobo_apps.APIs.APIRequestData;
import com.codepan.twinsrobo_apps.APIs.RetroServer;
import com.codepan.twinsrobo_apps.Adapters.AdapterInfoLomba;
import com.codepan.twinsrobo_apps.Models.DataModelInfoLomba;
import com.codepan.twinsrobo_apps.Models.ResponseModelInfoLomba;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoLombaRobotActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog pamfletZoom;
    private RecyclerView rvListInfoLomba;
    private RecyclerView.Adapter adDataInfoLomba;
    private RecyclerView.LayoutManager lmDataInfoLomba;
//    private List<DataModelInfoLomba> listData = new ArrayList<>();
    private Call<ResponseModelInfoLomba> requestInfoLomba;

    private ImageView ivBackArrowILR;
    private TextView tvNullInfoLomba, tvCatAll;
    private ImageView ivImageZoom;
    private LinearLayout llSumoRc, llMazeSoving, llDroneRc, llTransporter, llUnderWater;
    private RelativeLayout rlSumoRc, rlMazeSoving, rlDroneRc, rlTransporter, rlUnderWater;

    private boolean isZoomingPamflet = false;
    private String linkPamflet, lastestCategory, id_user;
//    CardView cvEvent1, cvEvent2, cvEvent3, cvEvent4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_lomba_robot);

        ivBackArrowILR = findViewById(R.id.ivBackArrowILR);
        rvListInfoLomba = findViewById(R.id.rvListInfoLomba);
        tvNullInfoLomba = findViewById(R.id.tvNullInfoLomba);
        llSumoRc = findViewById(R.id.llCatSumoRc);
        llMazeSoving = findViewById(R.id.llCatMazeSolving);
        llDroneRc = findViewById(R.id.llCatDroneRace);
        llTransporter = findViewById(R.id.llCatTransporter);
        llUnderWater = findViewById(R.id.llCatUnderWater);
        rlSumoRc = findViewById(R.id.rlCatSumoRc);
        rlMazeSoving = findViewById(R.id.rlCatMazeSolving);
        rlDroneRc = findViewById(R.id.rlCatDroneRace);
        rlTransporter = findViewById(R.id.rlCatTransporter);
        rlUnderWater = findViewById(R.id.rlCatUnderWater);
        tvCatAll = findViewById(R.id.tvCatAll);

        Bundle bundle = getIntent().getExtras();
        id_user = bundle.getString("id_user");

        if(savedInstanceState != null){
            isZoomingPamflet = savedInstanceState.getBoolean("is_zooming_pamflet");
            linkPamflet = savedInstanceState.getString("link_pamflet");
            lastestCategory = savedInstanceState.getString("lastest_category");

            selectCategory(lastestCategory);
        }
        else {
            selectCategory("all");
        }

        ivBackArrowILR.setOnClickListener(this);
        llSumoRc.setOnClickListener(this);
        llMazeSoving.setOnClickListener(this);
        llDroneRc.setOnClickListener(this);
        llTransporter.setOnClickListener(this);
        llUnderWater.setOnClickListener(this);
        tvCatAll.setOnClickListener(this);
    }

    private void retrieveInfoLomba(String category){

        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        if(category.equals("all")){
            requestInfoLomba = ardData.ardRetrieveDataInfoLomba();
        }
        else {
            requestInfoLomba = ardData.ardRetrieveDataInfoLombaSpesifik(category);
        }

        requestInfoLomba.enqueue(new Callback<ResponseModelInfoLomba>() {
            @Override
            public void onResponse(Call<ResponseModelInfoLomba> call, Response<ResponseModelInfoLomba> response) {
                if(response.body().getKodeInfoLomba() == 1){
                    if(Integer.parseInt(response.body().getJumlahInfoLomba()) > 0){

//                        Toast.makeText(InfoLombaRobotActivity.this, "x = " + response.body().getJumlahInfoLomba(), Toast.LENGTH_SHORT).show();

                        tvNullInfoLomba.setVisibility(View.GONE);
                        rvListInfoLomba.setVisibility(View.VISIBLE);

                        List<DataModelInfoLomba> listInfoLomba = response.body().getDataInfoLomba();
                        adDataInfoLomba = new AdapterInfoLomba(InfoLombaRobotActivity.this, listInfoLomba);
                        lmDataInfoLomba = new LinearLayoutManager(InfoLombaRobotActivity.this, LinearLayoutManager.VERTICAL, false);
                        rvListInfoLomba.setLayoutManager(lmDataInfoLomba);
                        rvListInfoLomba.setAdapter(adDataInfoLomba);
                        adDataInfoLomba.notifyDataSetChanged();
                        if(findViewById(R.id.llInfoLomba_Potrait) == null){
                            lmDataInfoLomba = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                            rvListInfoLomba.setLayoutManager(lmDataInfoLomba);
                            rvListInfoLomba.setAdapter(adDataInfoLomba);
                            adDataInfoLomba.notifyDataSetChanged();
                        }

                    }
                    else {
                        emptyInfoLomba("Tidak ada info lomba");
                    }
                }
                else {
                    Toast.makeText(InfoLombaRobotActivity.this, "Info lomba tidak Tersedia", Toast.LENGTH_SHORT).show();
                    emptyInfoLomba("Info lomba tidak Tersedia");
                }
            }

            @Override
            public void onFailure(Call<ResponseModelInfoLomba> call, Throwable t) {
                emptyInfoLomba("Gagal menghubungi server");
                Toast.makeText(InfoLombaRobotActivity.this, "Gagal menghubungi server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void emptyInfoLomba(String pesan){
        tvNullInfoLomba.setVisibility(View.VISIBLE);
        rvListInfoLomba.setVisibility(View.GONE);
        tvNullInfoLomba.setText(pesan);
    }

    public void showPamfletZoom(String linkPhoto){
        linkPamflet = linkPhoto;
        isZoomingPamflet = true;

        pamfletZoom = new Dialog(this);
        pamfletZoom.setContentView(R.layout.photo_zoom);
        ivImageZoom = pamfletZoom.findViewById(R.id.ivImageZoom);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        requestOptions.error(R.drawable.broken_image);

        Glide.with(this)
                .load(linkPamflet)
                .apply(requestOptions)
                .into(ivImageZoom);

        pamfletZoom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pamfletZoom.setCancelable(true);
        pamfletZoom.show();

        pamfletZoom.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
//                Toast.makeText(InfoLombaRobotActivity.this, "Popup Closed", Toast.LENGTH_SHORT).show();
                isZoomingPamflet = false;
            }
        });
    }

    private void clearActiveCategory(){
        rlSumoRc.setVisibility(View.INVISIBLE);
        rlMazeSoving.setVisibility(View.INVISIBLE);
        rlDroneRc.setVisibility(View.INVISIBLE);
        rlTransporter.setVisibility(View.INVISIBLE);
        rlUnderWater.setVisibility(View.INVISIBLE);
    }

    private void selectCategory(String category){

            clearActiveCategory();
            retrieveInfoLomba(category);

            if (category.equals("cat_sumo_rc")) rlSumoRc.setVisibility(View.VISIBLE);
            else if (category.equals("cat_maze_solving")) rlMazeSoving.setVisibility(View.VISIBLE);
            else if (category.equals("cat_drone_race")) rlDroneRc.setVisibility(View.VISIBLE);
            else if (category.equals("cat_transporter")) rlTransporter.setVisibility(View.VISIBLE);
            else if (category.equals("cat_under_water")) rlUnderWater.setVisibility(View.VISIBLE);

            lastestCategory = category;
    }

    public void showDetailEvent(String id,
                                String judul,
                                String deskripsi,
                                String durasi,
                                String jumlahShare,
                                String jumlahView,
                                String catSumoRc,
                                String catMazeSolving,
                                String catTransporter,
                                String catDroneRace,
                                String catUnderWater,
                                String linkPamflet){
        Intent intent = new Intent(InfoLombaRobotActivity.this, SelectedEventActivity.class);
        intent.putExtra("id_user", id_user);
        intent.putExtra("id", id);
        intent.putExtra("judul", judul);
        intent.putExtra("deskripsi", deskripsi);
        intent.putExtra("durasi", durasi);
        intent.putExtra("jumlahShare", jumlahShare);
        intent.putExtra("jumlahView", jumlahView);
        intent.putExtra("catSumoRc", catSumoRc);
        intent.putExtra("catMazeSolving", catMazeSolving);
        intent.putExtra("catTransporter", catTransporter);
        intent.putExtra("catDroneRace", catDroneRace);
        intent.putExtra("catUnderWater", catUnderWater);
        intent.putExtra("linkPamflet", linkPamflet);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ivBackArrowILR) finish();
        else {
            String kategoriDipilih = null;
            if(view.getId() == R.id.llCatSumoRc) kategoriDipilih = "cat_sumo_rc";
            else if(view.getId() == R.id.llCatMazeSolving) kategoriDipilih = "cat_maze_solving";
            else if(view.getId() == R.id.llCatDroneRace) kategoriDipilih = "cat_drone_race";
            else if(view.getId() == R.id.llCatTransporter) kategoriDipilih = "cat_transporter";
            else if(view.getId() == R.id.llCatUnderWater) kategoriDipilih = "cat_under_water";
            else if(view.getId() == R.id.tvCatAll) kategoriDipilih = "all";

            if(!kategoriDipilih.equals(lastestCategory)){
                selectCategory(kategoriDipilih);
            }
        }
//        llUnderWater.setBackgroundColor(Color.parseColor("#440094EF"));
//        tvUnderWater.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isZoomingPamflet){
            showPamfletZoom(linkPamflet);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("link_pamflet", linkPamflet);
        outState.putString("lastest_category", lastestCategory);
        outState.putBoolean("is_zooming_pamflet", isZoomingPamflet);
        outState.putBoolean("is_recreated_activity", true);
    }
}