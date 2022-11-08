package com.codepan.twinsrobo_apps.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepan.twinsrobo_apps.Models.DataModelChampion;
import com.codepan.twinsrobo_apps.R;

import java.util.List;

public class AdapterChampionship extends RecyclerView.Adapter<AdapterChampionship.HolderData>{
    private Context ctx;
    private List<DataModelChampion> listDataChampion;

    public AdapterChampionship(Context ctx, List<DataModelChampion> listDataChampion){
        this.ctx = ctx;
        this.listDataChampion = listDataChampion;
    }

    public class HolderData extends RecyclerView.ViewHolder {
        ImageView ivPhotoChampion, ivTrophyChampion;
        TextView tvNameChampion, tvSchoolChampion, tvPointChampion;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            ivPhotoChampion = itemView.findViewById(R.id.ivPhotoChampion);
            ivTrophyChampion = itemView.findViewById(R.id.ivTrophyChampion);
            tvNameChampion = itemView.findViewById(R.id.tvNameChampion);
            tvSchoolChampion = itemView.findViewById(R.id.tvSchoolChampion);
            tvPointChampion = itemView.findViewById(R.id.tvPointChampion);
        }
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.championship_leader_item, parent, false);
        AdapterChampionship.HolderData holder = new AdapterChampionship.HolderData(layout);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterChampionship.HolderData holder, int position) {
        DataModelChampion dm = listDataChampion.get(position);
        Glide.with(ctx)
                .load(dm.getFoto())
                .into(holder.ivPhotoChampion);
        holder.tvNameChampion.setText(dm.getNama());
        holder.tvSchoolChampion.setText(dm.getSekolah());
        holder.tvPointChampion.setText(dm.getPoint());
        if(position <= 2){
            if (position == 0){
                holder.ivTrophyChampion.setImageResource(R.drawable.trophy1);
            }
            else if (position == 1){
                holder.ivTrophyChampion.setImageResource(R.drawable.trophy2);
            }
            else if (position == 2){
                holder.ivTrophyChampion.setImageResource(R.drawable.trophy3);
            }
        }
        else {
            holder.ivTrophyChampion.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return listDataChampion.size();
    }
}
