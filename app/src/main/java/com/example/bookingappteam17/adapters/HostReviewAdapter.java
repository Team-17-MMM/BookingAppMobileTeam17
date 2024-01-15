// HostReviewAdapter.java
package com.example.bookingappteam17.adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.review.HostReviewDTO;
import com.example.bookingappteam17.dto.review.ReportedReviewDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostReviewAdapter extends RecyclerView.Adapter<HostReviewAdapter.HostReviewViewHolder> {
    private Context context;
    private List<HostReviewDTO> reviews;
    private final Long userID;
    private final Long hostID;

    public HostReviewAdapter(Context context, Long userID, Long hostID) {
        this.context = context;
        this.reviews = new ArrayList<>();
        this.userID = userID;
        this.hostID = hostID;
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

        if (this.userID.equals(this.hostID)){
            holder.reportButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            holder.reportButton.setVisibility(View.GONE);
            if (this.userID.equals(review.getReviewer())){
                holder.deleteButton.setVisibility(View.VISIBLE);
            } else {
                holder.deleteButton.setVisibility(View.GONE);
            }
        }

        holder.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportReview(review);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReview(review.getReviewID());
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

    private void deleteReview(Long reviewID) {
        String path = "review/host/" + reviewID;
        Call<Void> call = ClientUtils.reviewService.deleteReview(path);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    reviews.removeIf(review -> review.getReviewID().equals(reviewID));
                    notifyDataSetChanged();
                } else {
                    Log.e("Error deleting review", Objects.requireNonNull(response.errorBody()).toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error deleting review", Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    private void reportReview(HostReviewDTO review) {
        ReportedReviewDTO reportedReviewDTO = new ReportedReviewDTO();
        reportedReviewDTO.setReviewID(review.getReviewID());
        reportedReviewDTO.setHostReview(true);
        reportedReviewDTO.setApproved(true);

        Call<ReportedReviewDTO> call = ClientUtils.reviewService.reportHostReview(reportedReviewDTO);
        call.enqueue(new Callback<ReportedReviewDTO>() {
            @Override
            public void onResponse(Call<ReportedReviewDTO> call, Response<ReportedReviewDTO> response) {
                if (response.isSuccessful()) {
//                    reviews.removeIf(review1 -> review1.getReviewID().equals(review.getReviewID()));
//                    notifyDataSetChanged();
                    Toast.makeText(context, "Reported review", Toast.LENGTH_LONG).show();
                } else {
                    if (response.code() == 409) {
                        Toast.makeText(context, "You already reported this review", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                    }
            }

        }
            @Override
            public void onFailure(Call<ReportedReviewDTO> call, Throwable t) {

            }
        });
    }

}
