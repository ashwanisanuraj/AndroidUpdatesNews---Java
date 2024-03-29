package com.xero.androidupdatesnews;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailJournalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_journal);

        // Initialize views
        ImageView imageView = findViewById(R.id.journal_image_list_2);
        TextView titleTextView = findViewById(R.id.journal_title_list_2);
        TextView descriptionTextView = findViewById(R.id.journal_thought_list_2);
        TextView dateTextView = findViewById(R.id.journal_timestamp_list_2);

        // Retrieve data from intent
        if (getIntent().hasExtra("journal")) {
            Journal journal = getIntent().getParcelableExtra("journal");

            // Set data to views
            titleTextView.setText(journal.getTitle());
            descriptionTextView.setText(journal.getThoughts());
            dateTextView.setText(journal.getTimeAdded().toString());

            // Load image using Glide library
            Glide.with(this)
                    .load(journal.getImageUrl())
                    .centerCrop()
                    .into(imageView);
        }
    }
}
