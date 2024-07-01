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

public class AbDataAdapter extends RecyclerView.Adapter<AbViewHolder> {

    private Context context;
    private List<AthBehethDataClass> abDataList;

    AbDataAdapter(Context context, List<AthBehethDataClass> abDataList){
        this.context = context;
        this.abDataList = abDataList;
    }

    @NonNull
    @Override
    public AbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_osu_item, parent,false);
        return new AbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbViewHolder holder, int position) {
        Glide.with(context).load(abDataList.get(position).getAthBehethImage()).into(holder.abRecImage);
        holder.abRecTopic.setText(abDataList.get(position).getAthBehethTopic());
        holder.abRecDesc.setText(abDataList.get(position).getAthBehethDesc());

        holder.abRecCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AbViewsActivity.class);
                intent.putExtra("Image",abDataList.get(holder.getAdapterPosition()).getAthBehethImage());
                intent.putExtra("Description", abDataList.get(holder.getAdapterPosition()).getAthBehethDesc());
                intent.putExtra("Topic", abDataList.get(holder.getAdapterPosition()).getAthBehethTopic());
                intent.putExtra("Key", abDataList.get(holder.getAdapterPosition()).getAbKey());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return abDataList.size();
    }
}
class AbViewHolder extends RecyclerView.ViewHolder{

    ImageView abRecImage;
    TextView abRecTopic, abRecDesc;
    FrameLayout abRecCard;

    public AbViewHolder(@NonNull View itemView) {
        super(itemView);

        abRecTopic = itemView.findViewById(R.id.osuRecTitle);
        abRecImage = itemView.findViewById(R.id.osuRecImage);
        abRecDesc = itemView.findViewById(R.id.osuRecDesc);
        abRecCard = itemView.findViewById(R.id.osuRecCard);
    }
}

