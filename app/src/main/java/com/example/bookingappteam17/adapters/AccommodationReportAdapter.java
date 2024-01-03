package com.example.bookingappteam17.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingappteam17.R;  // Replace with your actual package name
import com.example.bookingappteam17.dto.accommodation.AccommodationReportDTO;

import java.util.List;

public class AccommodationReportAdapter extends RecyclerView.Adapter<AccommodationReportAdapter.ViewHolder> {

    private List<AccommodationReportDTO> data;

    public AccommodationReportAdapter(List<AccommodationReportDTO> data) {
        this.data = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView profitTextView;
        TextView reservationsTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.accommodationNameTextView);
            profitTextView = itemView.findViewById(R.id.profitTextView);
            reservationsTextView = itemView.findViewById(R.id.reservationsTextView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccommodationReportDTO item = data.get(position);

        holder.nameTextView.setText(item.getName());
        holder.profitTextView.setText(String.valueOf(item.getProfit()));
        holder.reservationsTextView.setText(String.valueOf(item.getNumberOfReservations()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
