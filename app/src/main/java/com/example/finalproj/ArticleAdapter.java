package com.example.finalproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {
    public ArticleAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_article, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView); // Add this line

        titleTextView.setText(article.getTitle());
        descriptionTextView.setText(article.getExplanation());
        dateTextView.setText(article.getDate()); // Set the date

        // Use Picasso to load images
        Picasso.get().load(article.getUrl()).into(imageView);

        return convertView;
    }
}