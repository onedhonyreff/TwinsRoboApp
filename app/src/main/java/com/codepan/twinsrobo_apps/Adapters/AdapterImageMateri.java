package com.codepan.twinsrobo_apps.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepan.twinsrobo_apps.LearningListActivity;
import com.codepan.twinsrobo_apps.MateriActivity;
import com.codepan.twinsrobo_apps.Models.DataModelImageMateri;
import com.codepan.twinsrobo_apps.Models.DataModelMateri;
import com.codepan.twinsrobo_apps.R;

import java.util.List;

public class AdapterImageMateri extends RecyclerView.Adapter<AdapterImageMateri.HolderData>{

    private Context ctx;
    private List<DataModelImageMateri> listDataImageMateri;

    public AdapterImageMateri(Context ctx, List<DataModelImageMateri> listDataImageMateri){
        this.ctx = ctx;
        this.listDataImageMateri = listDataImageMateri;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.materi_image_item, parent, false);
        AdapterImageMateri.HolderData holder = new AdapterImageMateri.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModelImageMateri dm = listDataImageMateri.get(position);

        holder.tvTitleImageMateri.setText(dm.getTitleImageMateri());
        holder.ivImageMateri.setImageResource(dm.getImageMateri());

        holder.llFrameImageMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MateriActivity) ctx).showImageMateriZoom(dm.getImageMateri());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataImageMateri.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        ImageView ivImageMateri;
        TextView tvTitleImageMateri;
        LinearLayout llFrameImageMateri;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            ivImageMateri = itemView.findViewById(R.id.ivImageMateri);
            tvTitleImageMateri = itemView.findViewById(R.id.tvTitleImageMateri);
            llFrameImageMateri = itemView.findViewById(R.id.llFrameImageMateri);
        }
    }
}
