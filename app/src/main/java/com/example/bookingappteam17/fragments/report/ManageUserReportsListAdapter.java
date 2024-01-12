package com.example.bookingappteam17.fragments.report;

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
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardRDTO;
import com.example.bookingappteam17.dto.user.UserReportDTO;
import com.example.bookingappteam17.model.review.ReportedReviewCardDTO;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageUserReportsListAdapter extends ArrayAdapter<UserReportDTO> {
    private List<UserReportDTO> aReviews;
    private Context context;

    public ManageUserReportsListAdapter(Context context, List<UserReportDTO> reviews) {
        super(context, R.layout.manage_review_card, reviews);
        this.context = context;
        this.aReviews = reviews;
    }

    public void setReviews(List<UserReportDTO> reviews) {
        aReviews.clear();
        aReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserReportDTO review = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.manage_user_report_card, parent, false);
        }

        LinearLayout reviewCard = convertView.findViewById(R.id.manage_user_reports_card);
        TextInputEditText accommodationHostName = convertView.findViewById(R.id.accommodation_host_name);
        TextInputEditText comment = convertView.findViewById(R.id.comment);
        TextInputEditText banned = convertView.findViewById(R.id.grade);

        if (review != null) {
            accommodationHostName.setText(review.getReportedUser());
            comment.setText(review.getDescription());
            if(review.isBanned()){
                banned.setText("True");
            }
            else {
                banned.setText("False");
            }

        }

        Button deleteReportButton = convertView.findViewById(R.id.button_delete_report);
        deleteReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  deleteReport(Objects.requireNonNull(review).getReportedAccommodationReviewDetailsID());
                approve(review);
                Snackbar.make(v, "Report deleted", Snackbar.LENGTH_LONG).show();
            }
        });


        Button deleteReviewButton = convertView.findViewById(R.id.button_delete_review);
        deleteReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  deleteReport(Objects.requireNonNull(review).getReportedAccommodationReviewDetailsID());
               // deleteReview(Objects.requireNonNull(review).getReviewID(), Objects.requireNonNull(review).getHostPath());
                reject(review);
                Snackbar.make(v, "Review deleted", Snackbar.LENGTH_LONG).show();
            }
        });

        return convertView;
    }


    private void approve(UserReportDTO review){
        review.setBanned(true);
        Call<UserReportDTO> call = ClientUtils.accommodationService.updateUserReport(review, review.getId());
        call.enqueue(new Callback<UserReportDTO>() {
            @Override
            public void onResponse(Call<UserReportDTO> call, Response<UserReportDTO> response) {
                if (response.isSuccessful()) {
                    UserReportDTO accommodationCardRDTOS = response.body();
                    aReviews.removeIf(report -> report.getId().equals(review.getId()));
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UserReportDTO> call, Throwable t) {
                // Handle failure
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private void reject(UserReportDTO review){
        review.setBanned(true);
        Call<Void> call = ClientUtils.accommodationService.deleteUserReport(review.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    aReviews.removeIf(report -> report.getId().equals(review.getId()));
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Log.e("ERROR", t.getMessage());
            }
        });
    }
}
