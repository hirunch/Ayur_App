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

public class OHelpsAdapter extends RecyclerView.Adapter<OHelpsViewHolder> {

    private Context context;
    private List<OHelpsDataClass> ohDataList;

    OHelpsAdapter(Context context, List<OHelpsDataClass> ohDataList){
        this.context = context;
        this.ohDataList = ohDataList;

    }

    @NonNull
    @Override
    public OHelpsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_oh_item,parent,false);
        return new OHelpsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OHelpsViewHolder holder, int position) {
        Glide.with(context).load(ohDataList.get(position).getOtherHImage()).into(holder.ohRecImage);
        holder.ohRecTopic.setText(ohDataList.get(position).getOtherHTopic());
        holder.ohRecDesc.setText(ohDataList.get(position).getOtherHDesc());

        holder.ohRecCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherHelpsViewsActivity.class);
                intent.putExtra("Image",ohDataList.get(holder.getAdapterPosition()).getOtherHImage());
                intent.putExtra("Description", ohDataList.get(holder.getAdapterPosition()).getOtherHDesc());
                intent.putExtra("Topic", ohDataList.get(holder.getAdapterPosition()).getOtherHTopic());
                intent.putExtra("Key", ohDataList.get(holder.getAdapterPosition()).getOhKey());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ohDataList.size();
    }
}

class OHelpsViewHolder extends RecyclerView.ViewHolder{
    ImageView ohRecImage;
    TextView ohRecTopic, ohRecDesc;
    FrameLayout ohRecCard;

    public OHelpsViewHolder(@NonNull View itemView) {
        super(itemView);

        ohRecImage = itemView.findViewById(R.id.ohRecImage);
        ohRecTopic = itemView.findViewById(R.id.ohRecTitle);
        ohRecDesc = itemView.findViewById(R.id.ohRecDesc);
        ohRecCard = itemView.findViewById(R.id.ohRecCard);


    }
}
