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
    // Constructor for AbDataAdapter class.
    // Initializes the context and the list of OsuInfoDataClass objects
    OsuInfoAdapter(Context context, List<OsuInfoDataClass> osuDataList){
        this.context = context;
        this.osuDataList = osuDataList;
    }

    // Creates and returns a new OsuInfoViewHolder by inflating the recycle_osu_item layout for each item in the RecyclerView
    @NonNull
    @Override
    public OsuInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_osu_item,parent,false);
        return new OsuInfoViewHolder(view);
    }
    // Binds the data from osuDataList to the views in the ViewHolder for the item at the specified position
    @Override
    public void onBindViewHolder(@NonNull OsuInfoViewHolder holder, int position) {
        // Loads the image from the URL in osuDataList using Glide and sets it into the osuRecImage ImageView
        Glide.with(context).load(osuDataList.get(position).getOsuDataImage()).into(holder.osuRecImage);
        //Sets the topic, Description and Date text from osuDataList into the osuRecTopic, osuRecDesc and osuRecDate TextView
        holder.osuRecTopic.setText(osuDataList.get(position).getOsuDataTopic());
        holder.osuRecDesc.setText(osuDataList.get(position).getOsuDataDesc());
        holder.osuRecDate.setText(osuDataList.get(position).getOsuDataDate());

        holder.osuRecCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates a new Intent to start the OsuInfoViewsActivity
                Intent intent = new Intent(context, OsuInfoViewsActivity.class);
                // Adds the image URL,description, topic, key, and date from the current item in osuDataList to the intent extras
                intent.putExtra("Image", osuDataList.get(holder.getAdapterPosition()).getOsuDataImage());
                intent.putExtra("Description", osuDataList.get(holder.getAdapterPosition()).getOsuDataDesc());
                intent.putExtra("Topic", osuDataList.get(holder.getAdapterPosition()).getOsuDataTopic());
                intent.putExtra("Date", osuDataList.get(holder.getAdapterPosition()).getOsuDataDate());
                intent.putExtra("Key", osuDataList.get(holder.getAdapterPosition()).getOsuKey());

                context.startActivity(intent);
            }
        });

    }
    // Returns the number of items in the osuDataList, which represents the total number of items to be displayed in the RecyclerView
    @Override
    public int getItemCount() {

        return osuDataList.size();
    }
}

class OsuInfoViewHolder extends RecyclerView.ViewHolder{

    ImageView osuRecImage;
    TextView osuRecTopic, osuRecDesc, osuRecDate;
    FrameLayout osuRecCard;

    public OsuInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        //initialize id
        osuRecTopic = itemView.findViewById(R.id.osuRecTitle);
        osuRecImage = itemView.findViewById(R.id.osuRecImage);
        osuRecDesc = itemView.findViewById(R.id.osuRecDesc);
        osuRecDate = itemView.findViewById(R.id.osuRecDate);
        osuRecCard = itemView.findViewById(R.id.osuRecCard);


    }
}
