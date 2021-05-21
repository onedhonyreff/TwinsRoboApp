package com.codepan.twinsrobo_apps.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.codepan.twinsrobo_apps.APIs.APIRequestData;
import com.codepan.twinsrobo_apps.APIs.RetroServer;
import com.codepan.twinsrobo_apps.Adapters.AdapterInfoLomba;
import com.codepan.twinsrobo_apps.Adapters.AdapterMember;
import com.codepan.twinsrobo_apps.InfoLombaRobotActivity;
import com.codepan.twinsrobo_apps.Models.DataModelUser;
import com.codepan.twinsrobo_apps.Models.ResponseModelUser;
import com.codepan.twinsrobo_apps.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberFragment extends Fragment {

    public MemberFragment() {
        // Required empty public constructor
    }

    private RecyclerView rvMemberList;
    private RecyclerView.Adapter adDataMember;
    private RecyclerView.LayoutManager lmDataMember;
    private RelativeLayout rlFailedRetrieveMember;

    private ProgressBar pbMemberFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View thisFragment =  inflater.inflate(R.layout.fragment_member, container, false);

        rvMemberList = thisFragment.findViewById(R.id.rvMemberList);
        rlFailedRetrieveMember = thisFragment.findViewById(R.id.rlFailedRetrieveMember);
        pbMemberFragment = thisFragment.findViewById(R.id.pbMemberFragment);

        pbMemberFragment.setVisibility(View.VISIBLE);

        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelUser> requestMember = ardData.ardRetrieveUser("all");

        requestMember.enqueue(new Callback<ResponseModelUser>() {
            @Override
            public void onResponse(Call<ResponseModelUser> call, Response<ResponseModelUser> response) {
                if(response.body().getKodeUser() == 1){
                    rvMemberList.setVisibility(View.VISIBLE);
                    rlFailedRetrieveMember.setVisibility(View.GONE);

                    List<DataModelUser> dataMember = response.body().getDataUser();
                    adDataMember = new AdapterMember(getActivity(), dataMember);
                    lmDataMember = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvMemberList.setLayoutManager(lmDataMember);
                    rvMemberList.setAdapter(adDataMember);
                    adDataMember.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getActivity(), "Gagal memuat member", Toast.LENGTH_SHORT).show();
                }
                pbMemberFragment.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseModelUser> call, Throwable t) {
                rvMemberList.setVisibility(View.GONE);
                rlFailedRetrieveMember.setVisibility(View.VISIBLE);
                pbMemberFragment.setVisibility(View.GONE);
            }
        });

        return thisFragment;
    }
}