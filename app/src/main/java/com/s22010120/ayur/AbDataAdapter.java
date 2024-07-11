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

    // Constructor for AbDataAdapter class.
    // Initializes the context and the list of AthBehethDataClass objects
    AbDataAdapter(Context context, List<AthBehethDataClass> abDataList){
        this.context = context;
        this.abDataList = abDataList;
    }

    // Creates and returns a new AbViewHolder by inflating the recycle_osu_item layout for each item in the RecyclerView
    @NonNull
    @Override
    public AbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_osu_item, parent,false);
        return new AbViewHolder(view);
    }

    // Binds the data from abDataList to the views in the ViewHolder for the item at the specified position
    @Override
    public void onBindViewHolder(@NonNull AbViewHolder holder, int position) {
        // Loads the image from the URL in abDataList using Glide and sets it into the abRecImage ImageView.
        Glide.with(context).load(abDataList.get(position).getAthBehethImage()).into(holder.abRecImage);
        //Sets the topic, Description and Date text from abDataList into the abRecTopic, abRecDesc and abRecDate TextView
        holder.abRecTopic.setText(abDataList.get(position).getAthBehethTopic());
        holder.abRecDesc.setText(abDataList.get(position).getAthBehethDesc());
        holder.abRecDate.setText(abDataList.get(position).getAbDataDate());

        holder.abRecCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates a new Intent to start the AbViewsActivity
                Intent intent = new Intent(context, AbViewsActivity.class);
                // Adds the image URL,description, topic, key, and date from the current item in abDataList to the intent extras
                intent.putExtra("Image",abDataList.get(holder.getAdapterPosition()).getAthBehethImage());
                intent.putExtra("Description", abDataList.get(holder.getAdapterPosition()).getAthBehethDesc());
                intent.putExtra("Topic", abDataList.get(holder.getAdapterPosition()).getAthBehethTopic());
                intent.putExtra("Key", abDataList.get(holder.getAdapterPosition()).getAbKey());
                intent.putExtra("Date", abDataList.get(holder.getAdapterPosition()).getAbDataDate());

                context.startActivity(intent);
            }
        });

    }

    // Returns the number of items in the abDataList, which represents the total number of items to be displayed in the RecyclerView
    @Override
    public int getItemCount() {
        return abDataList.size();
    }
}
class AbViewHolder extends RecyclerView.ViewHolder{

    ImageView abRecImage;
    TextView abRecTopic, abRecDesc, abRecDate;
    FrameLayout abRecCard;

    public AbViewHolder(@NonNull View itemView) {
        super(itemView);

        //initialize id
        abRecTopic = itemView.findViewById(R.id.osuRecTitle);
        abRecImage = itemView.findViewById(R.id.osuRecImage);
        abRecDesc = itemView.findViewById(R.id.osuRecDesc);
        abRecDate = itemView.findViewById(R.id.osuRecDate);
        abRecCard = itemView.findViewById(R.id.osuRecCard);
    }
}

