// HostReviewAdapter.java
package com.example.bookingappteam17.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.dto.review.HostReviewDTO;

import java.util.ArrayList;
import java.util.List;

public class HostReviewAdapter extends RecyclerView.Adapter<HostReviewAdapter.HostReviewViewHolder> {
    private Context context;
    private List<HostReviewDTO> reviews;

    public HostReviewAdapter(Context context) {
        this.context = context;
        this.reviews = new ArrayList<>();
    }

    public void setReviews(List<HostReviewDTO> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HostReviewAdapter.HostReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_card, parent, false);
        return new HostReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostReviewAdapter.HostReviewViewHolder holder, int position) {
        HostReviewDTO review = reviews.get(position);

        holder.reviewDescription.setText(review.getComment());
        holder.reviewDate.setText(review.getReviewDate());
        holder.reviewRating.setRating(review.getRating());

        holder.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Report review
                Toast.makeText(context, "Reported review", Toast.LENGTH_SHORT).show();
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Delete review
                Toast.makeText(context, "Deleted review", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class HostReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewDescription;
        TextView reviewDate;
        RatingBar reviewRating;
        Button reportButton;
        Button deleteButton;

        public HostReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewDescription = itemView.findViewById(R.id.review_description);
            reviewDate = itemView.findViewById(R.id.review_date);
            reviewRating = itemView.findViewById(R.id.review_rating);
            reportButton = itemView.findViewById(R.id.report_button);
            deleteButton = itemView.findViewById(R.id.delete_review_button);
        }
    }
}
