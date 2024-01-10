package com.example.bookingappteam17.fragments.review;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.model.review.ReportedReviewCardDTO;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageReviewListAdapter extends ArrayAdapter<ReportedReviewCardDTO> {
    private List<ReportedReviewCardDTO> aReviews;
    private Context context;

    public ManageReviewListAdapter(Context context, List<ReportedReviewCardDTO> reviews) {
        super(context, R.layout.manage_review_card, reviews);
        this.context = context;
        this.aReviews = reviews;
    }

    public void setReviews(List<ReportedReviewCardDTO> reviews) {
        aReviews.clear();
        aReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReportedReviewCardDTO review = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.manage_review_card, parent, false);
        }

        LinearLayout reviewCard = convertView.findViewById(R.id.manage_review_card_item);
        TextInputEditText accommodationHostName = convertView.findViewById(R.id.accommodation_host_name);
        TextInputEditText comment = convertView.findViewById(R.id.comment);
        TextInputEditText grade = convertView.findViewById(R.id.grade);
        TextInputEditText username = convertView.findViewById(R.id.username);

        if (review != null) {
            accommodationHostName.setText(review.getAccommodationName());
            comment.setText(review.getComment());
            grade.setText(review.getGrade().toString());
            username.setText(review.getUsername());
        }

        Button deleteReportButton = convertView.findViewById(R.id.button_delete_report);
        deleteReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReport(Objects.requireNonNull(review).getReportedAccommodationReviewDetailsID());
                Snackbar.make(v, "Report deleted", Snackbar.LENGTH_LONG).show();
            }
        });


        Button deleteReviewButton = convertView.findViewById(R.id.button_delete_review);
        deleteReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReport(Objects.requireNonNull(review).getReportedAccommodationReviewDetailsID());
                deleteReview(Objects.requireNonNull(review).getReviewID(), Objects.requireNonNull(review).getHostPath());
                Snackbar.make(v, "Review deleted", Snackbar.LENGTH_LONG).show();
            }
        });

        return convertView;
    }


    private void deleteReport(Long reportedAccommodationReviewDetailsID) {
        Call<Void> call = ClientUtils.reviewService.deleteReport(reportedAccommodationReviewDetailsID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    aReviews.removeIf(review -> review.getReportedAccommodationReviewDetailsID().equals(reportedAccommodationReviewDetailsID));
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error deleting report", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void deleteReview(Long reviewID, String hostPath) {
        String path = "review/" + hostPath + reviewID;
        Call<Void> call = ClientUtils.reviewService.deleteReview(path);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    aReviews.removeIf(review -> review.getReviewID().equals(reviewID));
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error deleting review", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

}
