package com.codepan.twinsrobo_apps.Fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepan.twinsrobo_apps.AboutUsActivity;
import com.codepan.twinsrobo_apps.Adapters.AdapterChampionship;
import com.codepan.twinsrobo_apps.Adapters.AdapterModul;
import com.codepan.twinsrobo_apps.InfoLombaRobotActivity;
import com.codepan.twinsrobo_apps.LearnRobotActivity;
import com.codepan.twinsrobo_apps.MenuCheckMyRobotActivity;
import com.codepan.twinsrobo_apps.MenuProgramRobotActivity;
import com.codepan.twinsrobo_apps.MiniGamesActivity;
import com.codepan.twinsrobo_apps.Models.DataModelChampion;
import com.codepan.twinsrobo_apps.Models.DataModelModul;
import com.codepan.twinsrobo_apps.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView rvListChampion;
    private RecyclerView.Adapter adDataChampion;
    private RecyclerView.LayoutManager lmDataChampion;

    private CardView cvMiniGames, cvCheckMyRobot, cvProgramRobot, cvAboutUs, cvLearnRobot, cvInfoLombaRobot;
    private TextView tvUserNamaDepan;

    private String sapa_user, id_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisFragment = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = getArguments();
        id_user = bundle.getString("id_user");
        sapa_user = bundle.getString("nama_depan_home");

        cvMiniGames = thisFragment.findViewById(R.id.cvMiniGames);
        cvCheckMyRobot = thisFragment.findViewById(R.id.cvCheckMyRobot);
        cvProgramRobot = thisFragment.findViewById(R.id.cvProgramRobot);
        cvAboutUs = thisFragment.findViewById(R.id.cvAboutUs);
        cvLearnRobot = thisFragment.findViewById(R.id.cvLearnRobot);
        cvInfoLombaRobot = thisFragment.findViewById(R.id.cvInfoLombaRobot);
        tvUserNamaDepan = thisFragment.findViewById(R.id.tvUserNamaDepan);
        rvListChampion = thisFragment.findViewById(R.id.rvListChampion);

        tvUserNamaDepan.setText(sapa_user);

        cvMiniGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MiniGamesActivity.class));
            }
        });

        cvCheckMyRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MenuCheckMyRobotActivity.class));
            }
        });

        cvProgramRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MenuProgramRobotActivity.class));
            }
        });

        cvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
            }
        });

        cvLearnRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LearnRobotActivity.class));
            }
        });

        cvInfoLombaRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfoLombaRobotActivity.class);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });

        showListChampion();

        return thisFragment;
    }

    private void showListChampion() {

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

        adDataChampion = new AdapterChampionship(getActivity(), dataChampion);
        lmDataChampion = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvListChampion.setLayoutManager(lmDataChampion);
        rvListChampion.setAdapter(adDataChampion);
        adDataChampion.notifyDataSetChanged();
    }
}