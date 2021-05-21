package com.codepan.twinsrobo_apps.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codepan.twinsrobo_apps.LearnRobotActivity;
import com.codepan.twinsrobo_apps.Models.DataModelMateri;
import com.codepan.twinsrobo_apps.Models.DataModelModul;
import com.codepan.twinsrobo_apps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterModul extends RecyclerView.Adapter<AdapterModul.HolderData>{

    private Context ctx;
    private List<DataModelModul> listDataModul;

    public AdapterModul(Context ctx, List<DataModelModul> listDataModul){
        this.ctx = ctx;
        this.listDataModul = listDataModul;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.modul_item, parent, false);
        AdapterModul.HolderData holder = new AdapterModul.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModelModul dm = listDataModul.get(position);

        holder.ivModulItem.setImageResource(dm.getModul_image());
        holder.tvTitleModul.setText(dm.getModul_title());
        if(dm.getModul_status().equals("Unlock")){
            holder.llLockModul.setVisibility(View.GONE);
        }
        else {
            holder.llLockModul.setVisibility(View.VISIBLE);
        }

        holder.cvModulItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dm.getModul_status().equals("Unlock")){
                    ((LearnRobotActivity) ctx).openModul(position);
                }
                else if (dm.getModul_status().equals("Lock")){
                    ((LearnRobotActivity) ctx).showBuyModulDialog(dm.getModul_image(), dm.getModul_title(), dm.getModul_price());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataModul.size();
    }

    public void filterList(ArrayList<DataModelModul> filteredModul){
        this.listDataModul = filteredModul;
        notifyDataSetChanged();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        LinearLayout llLockModul;
        CardView cvModulItem;
        ImageView ivModulItem;
        TextView tvTitleModul;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            llLockModul = itemView.findViewById(R.id.llLockModul);
            cvModulItem = itemView.findViewById(R.id.cvModulItem);
            ivModulItem = itemView.findViewById(R.id.ivModulItem);
            tvTitleModul = itemView.findViewById(R.id.tvTitleModul);
        }
    }
}
