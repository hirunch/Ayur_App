package com.s22010120.ayur;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class OsuInfoAdapter extends RecyclerView.Adapter<OsuInfoViewHolder> {

    private Context context;
    private List<OsuInfoDataClass> osuDataList;

    OsuInfoAdapter(Context context, List<OsuInfoDataClass> osuDataList){
        this.context = context;
        this.osuDataList = osuDataList;
    }


    @NonNull
    @Override
    public OsuInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_osu_item,parent,false);
        return new OsuInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OsuInfoViewHolder holder, int position) {
        Glide.with(context).load(osuDataList.get(position).getOsuDataImage()).into(holder.osuRecImage);
        holder.osuRecTopic.setText(osuDataList.get(position).getOsuDataTopic());
        holder.osuRecDesc.setText(osuDataList.get(position).getOsuDataDesc());

        holder.osuRecCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OsuInfoViewsActivity.class);
                intent.putExtra("Image", osuDataList.get(holder.getAdapterPosition()).getOsuDataImage());
                intent.putExtra("Description", osuDataList.get(holder.getAdapterPosition()).getOsuDataDesc());
                intent.putExtra("Topic", osuDataList.get(holder.getAdapterPosition()).getOsuDataTopic());
                intent.putExtra("Key", osuDataList.get(holder.getAdapterPosition()).getOsuKey());

                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {

        return osuDataList.size();
    }


}

class OsuInfoViewHolder extends RecyclerView.ViewHolder{

    ImageView osuRecImage;
    TextView osuRecTopic, osuRecDesc;
    FrameLayout osuRecCard;

    public OsuInfoViewHolder(@NonNull View itemView) {
        super(itemView);

        osuRecTopic = itemView.findViewById(R.id.osuRecTitle);
        osuRecImage = itemView.findViewById(R.id.osuRecImage);
        osuRecDesc = itemView.findViewById(R.id.osuRecDesc);
        osuRecCard = itemView.findViewById(R.id.osuRecCard);


    }
}
