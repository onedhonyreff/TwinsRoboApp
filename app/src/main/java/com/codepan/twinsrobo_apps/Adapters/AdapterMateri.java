package com.codepan.twinsrobo_apps.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepan.twinsrobo_apps.LearningListActivity;
import com.codepan.twinsrobo_apps.Models.DataModelMateri;
import com.codepan.twinsrobo_apps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterMateri extends RecyclerView.Adapter<AdapterMateri.HolderData>{

    private Context ctx;
    private List<DataModelMateri> listDataMateri;

    public AdapterMateri(Context ctx, List<DataModelMateri> listDataMateri){
        this.ctx = ctx;
        this.listDataMateri = listDataMateri;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.learning_item, parent, false);
        AdapterMateri.HolderData holder = new AdapterMateri.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModelMateri dm = listDataMateri.get(position);

        holder.tvJudulMateriPreview.setText(dm.getJudulMateri());

        holder.llMateriItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LearningListActivity) ctx).openMateri(dm.getJudulMateri(), dm.getVideoMateri(), dm.getIsiMateri());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataMateri.size();
    }

    public void filterList(ArrayList<DataModelMateri> filteredMateri){
        this.listDataMateri = filteredMateri;
        notifyDataSetChanged();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        LinearLayout llMateriItemList;
        TextView tvJudulMateriPreview;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            llMateriItemList = itemView.findViewById(R.id.llMateriItemList);
            tvJudulMateriPreview = itemView.findViewById(R.id.tvJudulMateriPreview);
        }
    }
}
