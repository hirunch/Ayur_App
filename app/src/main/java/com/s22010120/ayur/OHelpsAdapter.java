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

    // Constructor for AbDataAdapter class.
    // Initializes the context and the list of OHelpsDataClass objects
    OHelpsAdapter(Context context, List<OHelpsDataClass> ohDataList){
        this.context = context;
        this.ohDataList = ohDataList;

    }
    // Creates and returns a new OHelpsViewHolder by inflating the recycle_oh_item layout for each item in the RecyclerView
    @NonNull
    @Override
    public OHelpsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_oh_item,parent,false);
        return new OHelpsViewHolder(view);
    }
    // Binds the data from ohDataList to the views in the ViewHolder for the item at the specified position
    @Override
    public void onBindViewHolder(@NonNull OHelpsViewHolder holder, int position) {
        // Loads the image from the URL in ohDataList using Glide and sets it into the ohRecImage ImageView
        Glide.with(context).load(ohDataList.get(position).getOtherHImage()).into(holder.ohRecImage);
        //Sets the topic, Description and Date text from ohDataList into the ohRecTopic, ohRecDesc and ohRecDate TextView
        holder.ohRecTopic.setText(ohDataList.get(position).getOtherHTopic());
        holder.ohRecDesc.setText(ohDataList.get(position).getOtherHDesc());
        holder.ohRecDate.setText(ohDataList.get(position).getOhDateData());

        holder.ohRecCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates a new Intent to start the OtherHelpsViewsActivity
                Intent intent = new Intent(context, OtherHelpsViewsActivity.class);
                // Adds the image URL,description, topic, key, and date from the current item in ohDataList to the intent extras
                intent.putExtra("Image",ohDataList.get(holder.getAdapterPosition()).getOtherHImage());
                intent.putExtra("Description", ohDataList.get(holder.getAdapterPosition()).getOtherHDesc());
                intent.putExtra("Topic", ohDataList.get(holder.getAdapterPosition()).getOtherHTopic());
                intent.putExtra("Key", ohDataList.get(holder.getAdapterPosition()).getOhKey());
                intent.putExtra("Date", ohDataList.get(holder.getAdapterPosition()).getOhDateData());

                context.startActivity(intent);
            }
        });

    }
    // Returns the number of items in the ohDataList, which represents the total number of items to be displayed in the RecyclerView
    @Override
    public int getItemCount() {
        return ohDataList.size();
    }
}

class OHelpsViewHolder extends RecyclerView.ViewHolder{
    ImageView ohRecImage;
    TextView ohRecTopic, ohRecDesc, ohRecDate;
    FrameLayout ohRecCard;

    public OHelpsViewHolder(@NonNull View itemView) {
        super(itemView);
        //initialize id
        ohRecImage = itemView.findViewById(R.id.ohRecImage);
        ohRecTopic = itemView.findViewById(R.id.ohRecTitle);
        ohRecDesc = itemView.findViewById(R.id.ohRecDesc);
        ohRecDate = itemView.findViewById(R.id.ohRecDate);
        ohRecCard = itemView.findViewById(R.id.ohRecCard);


    }
}
