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

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private Context context;
    private List<ArticleDataClass> dataList;

    // Constructor for AbDataAdapter class.
    // Initializes the context and the list of ArticleDataClass objects
    public ArticleAdapter(Context context, List<ArticleDataClass> dataList){

        this.context = context;
        this.dataList = dataList;
    }

    // Creates and returns a new ArticleViewHolder by inflating the recycle_item layout for each item in the RecyclerView
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item,parent, false);
        return new ArticleViewHolder(view);
    }

    // Binds the data from dataList to the views in the ViewHolder for the item at the specified position
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        // Loads the image from the URL in abDataList using Glide and sets it into the abRecImage ImageView
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        //Sets the topic, Description and Date text from abDataList into the recTopic, recDesc and recDate TextView
        holder.recTopic.setText(dataList.get(position).getDataTopic());
        holder.recDesc.setText(dataList.get(position).getDataDesc());
        holder.recDate.setText(dataList.get(position).getDataDate());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates a new Intent to start the ArticlesViewsActivity
                Intent intent = new Intent(context, ArticlesViewsActivity.class);
                // Adds the image URL,description, topic, key, and date from the current item in dataList to the intent extras
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Topic", dataList.get(holder.getAdapterPosition()).getDataTopic());
                intent.putExtra("Key", dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getDataDate());

                context.startActivity(intent);
            }
        });

    }

    // Returns the number of items in the dataList, which represents the total number of items to be displayed in the RecyclerView
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class ArticleViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recTopic, recDesc, recDate;
    FrameLayout recCard;

    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        //initialize id
        recImage = itemView.findViewById(R.id.recImage);
        recTopic = itemView.findViewById(R.id.recTitle);
        recDesc = itemView.findViewById(R.id.recDesc);
        recCard = itemView.findViewById(R.id.recCard);
        recDate = itemView.findViewById(R.id.recDate);


    }
}
