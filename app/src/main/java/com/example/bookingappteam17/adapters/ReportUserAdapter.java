package com.example.bookingappteam17.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.review.HostReviewDTO;
import com.example.bookingappteam17.dto.review.ReportedReviewDTO;
import com.example.bookingappteam17.dto.user.UserInfoDTO;
import com.example.bookingappteam17.dto.user.UserReportDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportUserAdapter extends RecyclerView.Adapter<ReportUserAdapter.UserReportViewHolder> {

    private Context context;
    private List<UserInfoDTO> reviews;
    private final Long userID;

    public ReportUserAdapter(Context context, Long id) {
        this.context = context;
        this.reviews = new ArrayList<>();
        this.userID = id;
    }

    public void setReviews(List<UserInfoDTO> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReportUserAdapter.UserReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_user_card, parent, false);
        return new ReportUserAdapter.UserReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportUserAdapter.UserReportViewHolder holder, int position) {
        UserInfoDTO review = reviews.get(position);

        holder.reviewDescription.setText(review.getUsername());
        holder.reviewName.setText(review.getName());
        holder.reviewSurname.setText(review.getLastname());
        holder.reportButton.setVisibility(View.VISIBLE);
       /* if (this.userID.equals(0L)){
            holder.reportButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            holder.reportButton.setVisibility(View.GONE);
            if (this.userID.equals(review.getReviewer())){
                holder.deleteButton.setVisibility(View.VISIBLE);
            } else {
                holder.deleteButton.setVisibility(View.GONE);
            }
        }*/


        holder.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reportDescription = holder.editTextReportDescription.getText().toString();
                reportReview(review, reportDescription);
                Toast.makeText(context, "Reported review", Toast.LENGTH_LONG).show();
            }
        });

      /*  holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReview(review.getReviewID());
                Toast.makeText(context, "Deleted review", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }



    static class UserReportViewHolder extends RecyclerView.ViewHolder {
        TextView reviewDescription;
        TextView reviewName;
        EditText editTextReportDescription;

        TextView reviewSurname;
        Button reportButton;
        Button deleteButton;

        public UserReportViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewDescription = itemView.findViewById(R.id.review_description);
            reviewName = itemView.findViewById(R.id.review_name);
            reviewSurname = itemView.findViewById(R.id.review_surname);
            reportButton = itemView.findViewById(R.id.report_button);
            deleteButton = itemView.findViewById(R.id.delete_review_button);
            editTextReportDescription = itemView.findViewById(R.id.editTextReportDescription);
        }
    }

    private void reportReview(UserInfoDTO review, String reportDescription) {
        UserReportDTO userReportDTO = new UserReportDTO();
        userReportDTO.setUserID(userID);
        userReportDTO.setReportedUser(review.getUsername());
        userReportDTO.setBanned(false);
        userReportDTO.setDescription(reportDescription);



        Call<UserReportDTO> call = ClientUtils.reviewService.reportUser(userReportDTO);
        call.enqueue(new Callback<UserReportDTO>() {
            @Override
            public void onResponse(Call<UserReportDTO> call, Response<UserReportDTO> response) {
                if (response.isSuccessful()) {
//                    reviews.removeIf(review1 -> review1.getReviewID().equals(review.getReviewID()));
//                    notifyDataSetChanged();
                    Log.e("Reported review", "Reported review");
                } else {
                    Log.e("Error reporting review", Objects.requireNonNull(response.errorBody()).toString());
                }
            }

            @Override
            public void onFailure(Call<UserReportDTO> call, Throwable t) {
                Log.e("Error reporting review", Objects.requireNonNull(t.getMessage()));
            }
        });

    }
}
