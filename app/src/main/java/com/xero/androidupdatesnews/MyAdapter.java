package com.xero.androidupdatesnews;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    // Variables
    private Context context;
    private List<Journal> journalList;

    public MyAdapter(Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.journal_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Journal currentJournal = journalList.get(position);

        holder.title.setText(currentJournal.getTitle());
        holder.thoughts.setText(currentJournal.getThoughts());
        holder.name.setText(currentJournal.getUserName());

        String imageUrl = currentJournal.getImageUrl();

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                currentJournal.getTimeAdded().getSeconds()*1000
        );
        holder.dateAdded.setText(timeAgo);


        // Glide Library to display the image
        Glide.with(context)
                .load(imageUrl)
                .fitCenter()
                .into(holder.image);

        // Set OnClickListener for share button
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareJournal(currentJournal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }


    // View Holder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, thoughts, dateAdded, name;
        public ImageView image, shareButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.journal_title_list);
            thoughts = itemView.findViewById(R.id.journal_thought_list);
            dateAdded = itemView.findViewById(R.id.journal_timestamp_list);

            image = itemView.findViewById(R.id.journal_image_list);
            name = itemView.findViewById(R.id.journal_row_username);

            shareButton = itemView.findViewById(R.id.journal_row_share_button);
        }
    }

    // Share journal method
    private void shareJournal(Journal journal) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "Title: " + journal.getTitle() + "\n\n"
                + "Thoughts: " + journal.getThoughts() + "\n\n";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(shareIntent, "Share Journal"));
    }
}
