package com.example.bookingappteam17.fragments.review;


import static com.example.bookingappteam17.clients.ClientUtils.accommodationService;
import static com.example.bookingappteam17.clients.ClientUtils.userService;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.review.AccommodationReviewDTO;
import com.example.bookingappteam17.dto.user.UserUpdateDTO;
import com.example.bookingappteam17.model.user.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveReviewListAdapter extends ArrayAdapter<AccommodationReviewDTO> {
    private List<AccommodationReviewDTO> aReviews;
    private Context context;

    public ApproveReviewListAdapter(Context context, List<AccommodationReviewDTO> reviews) {
        super(context, R.layout.manage_review_card, reviews);
        this.context = context;
        this.aReviews = reviews;
    }

    public void setReviews(List<AccommodationReviewDTO> reviews) {
        aReviews.clear();
        aReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationReviewDTO review = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.approve_review_card, parent, false);
        }

        LinearLayout reviewCard = convertView.findViewById(R.id.manage_review_card_item);
        TextInputEditText accommodationHostName = convertView.findViewById(R.id.accommodation_name);
        TextInputEditText comment = convertView.findViewById(R.id.comment);
        TextInputEditText grade = convertView.findViewById(R.id.grade);
        TextInputEditText username = convertView.findViewById(R.id.reviewer);

        if (review != null) {
            setAccommodationName(review.getAccommodationID(), accommodationHostName);
            comment.setText(review.getComment());
            grade.setText(String.valueOf(review.getGrade()));
            setGuestName(review.getReviewer(), username);
        }

        Button deleteReportButton = convertView.findViewById(R.id.button_approve_review);
        deleteReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveReview(Objects.requireNonNull(review));
            }
        });


        Button deleteReviewButton = convertView.findViewById(R.id.button_delete_review);
        deleteReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReview(Objects.requireNonNull(review).getReviewID());
                Snackbar.make(v, "Review deleted", Snackbar.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    private void setGuestName(Long reviewerID, TextInputEditText username) {
        Call<User> call = userService.getById(reviewerID);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    String nameAndLastname = response.body().getName() + " " + response.body().getLastname();
                    username.setText(nameAndLastname);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error getting accommodation", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void setAccommodationName(Long accommodationID, TextInputEditText accommodationHostName) {
        Call<AccommodationDTO> call = accommodationService.getAccommodation(accommodationID);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    accommodationHostName.setText(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                Log.e("Error getting accommodation", Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    private void approveReview(AccommodationReviewDTO review) {
        review.setApproved(true);
        Call<AccommodationReviewDTO> call = ClientUtils.reviewService.updateReview(review.getReviewID(), review);
        call.enqueue(new Callback<AccommodationReviewDTO>() {
            @Override
            public void onResponse(Call<AccommodationReviewDTO> call, Response<AccommodationReviewDTO> response) {
                if (response.isSuccessful()) {
                    aReviews.removeIf(review -> review.getReviewID().equals(response.body().getReviewID()));
                    notifyDataSetChanged();
                    Toast.makeText(context, "Approved review!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccommodationReviewDTO> call, Throwable t) {
                Log.e("Error deleting report", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void deleteReview(Long reviewID) {
        String path = "review/"  + reviewID;
        Call<Void> call = ClientUtils.reviewService.deleteReview(path);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    aReviews.removeIf(review -> review.getReviewID().equals(reviewID));
                    notifyDataSetChanged();
                    Toast.makeText(context, "Deleted review!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error deleting review", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

}

