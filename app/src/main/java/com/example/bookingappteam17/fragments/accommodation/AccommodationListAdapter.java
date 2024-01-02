package com.example.bookingappteam17.fragments.accommodation;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.accommodation.AccommodationDetailActivity;
import com.example.bookingappteam17.activities.accommodation.HostAccommodationDetailActivity;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.reservation.ReservationReportDTO;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationListAdapter extends ArrayAdapter<AccommodationCardDTO> {

    private List<AccommodationCardDTO> aAccommodations;
    private Context context;
    private SharedPreferences sharedPreferences;

    public AccommodationListAdapter(Context context, List<AccommodationCardDTO> accommodations) {
        super(context, R.layout.accommodation_card, accommodations);
        this.context = context;
        this.aAccommodations = accommodations;
    }

    public void setAccommodations(List<AccommodationCardDTO> accommodations) {
        aAccommodations.clear();
        aAccommodations.addAll(accommodations);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationCardDTO accommodation = getItem(position);
        sharedPreferences = context.getSharedPreferences("user_prefs", context.MODE_PRIVATE);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_card, parent, false);
        }

        LinearLayout accommodationCard = convertView.findViewById(R.id.accommodation_card_item);
        TextView accommodationName = convertView.findViewById(R.id.accommodation_name);
        TextView accommodationDescription = convertView.findViewById(R.id.accommodation_description);
        ImageView accommodationImage = convertView.findViewById(R.id.accommodation_image);
        TextView accommodationPrice = convertView.findViewById(R.id.accommodation_price);

        if (accommodation != null) {
            accommodationName.setText(accommodation.getName());
            accommodationDescription.setText(accommodation.getDescription());
            accommodationImage.setImageBitmap(accommodation.getImage());
            accommodationPrice.setText(String.valueOf(1000)); // You might want to update this based on the actual data
        }

        Button getReportAccommodation = convertView.findViewById(R.id.get_report_accommodation);
        getReportAccommodation.setVisibility(View.VISIBLE);
        getReportAccommodation.setOnClickListener(v -> {
            getReportData(accommodation.getAccommodationID());
        });

        Button detailsButton = convertView.findViewById(R.id.accommodation_details);
        detailsButton.setOnClickListener(v -> {
            // get id of accommodation which is clicked
            Long id = accommodation.getAccommodationID();
            String role = sharedPreferences.getString("role", "");
            if (role.equals("HOST")) {
                System.out.println("HOST");
                Intent intent = new Intent(getContext(), HostAccommodationDetailActivity.class);
                intent.putExtra("selected_accommodation", id);
                getContext().startActivity(intent);
//                AccommodationPageFragment.accommodations.clear();
            }
            else{
                Intent intent = new Intent(getContext(), AccommodationDetailActivity.class);
                intent.putExtra("selected_accommodation", (CharSequence) accommodation);
//                intent.putExtra("sharedViewModel", (CharSequence) sharedViewModel);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private void updateAccommodation(Long accommodationID) {
        Call<AccommodationDTO> call = ClientUtils.accommodationService.updateByNewAccommodation(accommodationID);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    // Remove the accommodation from the local list
                    aAccommodations.removeIf(a -> a.getAccommodationID().equals(accommodationID));
                    // Notify the adapter that the data set has changed
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                // Handle failure
                Log.d("AccommodationListAdapter", "onFailure: " + t.getMessage());
            }
        });
    }

    private void deleteNewAccommodation(Long accommodationID) {
        Call<AccommodationDTO> call = ClientUtils.accommodationService.deleteAccommodation(accommodationID);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    // Remove the accommodation from the local list
                    aAccommodations.removeIf(a -> a.getAccommodationID().equals(accommodationID));
                    // Notify the adapter that the data set has changed
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                // Handle failure
                Log.d("AccommodationListAdapter", "onFailure: " + t.getMessage());
            }
        });
    }

    private void notifyDataChanged() {
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().getCurrentState();
        }
    }

    private void showReportDialog(ReservationReportDTO reservationReportDTO) {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_report);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        CombinedChart combinedChart = dialog.findViewById(R.id.report_chart);
        CombinedData combinedData = new CombinedData();

        // Add bar data
        BarData barData = getBarChartData(reservationReportDTO.getPricesMap());
        combinedData.setData(barData);

        // Add line data
        LineData lineData = getLineChartData(reservationReportDTO.getNumberOfReservationsMap());
        combinedData.setData(lineData);

        combinedChart.setData(combinedData);

        // Customize the appearance of the chart as needed
        customizeChart(combinedChart);

        Button okButton = dialog.findViewById(R.id.dialog_ok_button);
        okButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private BarData getBarChartData(Map<String, Double> pricesMap) {
        // Extract entries from the pricesMap and sort them by date
        List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(pricesMap.entrySet());
        sortedEntries.sort(Map.Entry.comparingByKey());

        // Create entries for the BarData
        List<BarEntry> entries = new ArrayList<>();

        // Iterate through the sorted entries
        for (int i = 0; i < sortedEntries.size(); i++) {
            Map.Entry<String, Double> entry = sortedEntries.get(i);

            // Extract year and month from the key
            String[] parts = entry.getKey().split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            // Calculate the x-value for the BarEntry
            float xValue = month + (year - Calendar.getInstance().get(Calendar.YEAR)) * 12;

            // Convert the Double value to float
            float yValue = entry.getValue().floatValue();

            // Add a data point for each entry
            entries.add(new BarEntry(xValue, yValue));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Profit");
        return new BarData(dataSet);
    }


    private LineData getLineChartData(Map<String, Integer> numberOfReservations) {
        // Extract entries from the numberOfReservations map and sort them by date
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(numberOfReservations.entrySet());
        sortedEntries.sort(Map.Entry.comparingByKey());

        // Create entries for the LineData
        List<Entry> entries = new ArrayList<>();

        // Iterate through the sorted entries
        for (int i = 0; i < sortedEntries.size(); i++) {
            Map.Entry<String, Integer> entry = sortedEntries.get(i);

            // Extract year and month from the key
            String[] parts = entry.getKey().split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            // Calculate the x-value for the Entry
            float xValue = month + (year - Calendar.getInstance().get(Calendar.YEAR)) * 12;

            // Add a data point for each entry
            entries.add(new Entry(xValue, entry.getValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Number of Reservations");
        return new LineData(dataSet);
    }

    private void customizeChart(CombinedChart combinedChart) {
        // Customize the appearance of the combined chart
        combinedChart.getDescription().setEnabled(false);
        combinedChart.getLegend().setEnabled(true);

        // Customize X-axis
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // Ensure the X-axis labels are spaced evenly
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getMonthLabels()));

        // Customize the line data set
        LineData lineData = combinedChart.getData().getLineData();
        if (lineData != null) {
            LineDataSet lineDataSet = (LineDataSet) lineData.getDataSetByIndex(0);
            if (lineDataSet != null) {
                lineDataSet.setColor(Color.YELLOW);
                lineDataSet.setLineWidth(2.5f);
                lineDataSet.setCircleColor(Color.RED);
                lineDataSet.setCircleRadius(5f);
                lineDataSet.setFillColor(Color.YELLOW);
            }
        }
    }

    private List<String> getMonthLabels() {
        // Add the names of the months
        List<String> monthLabels = new ArrayList<>();
        monthLabels.add("Jan");
        monthLabels.add("Feb");
        monthLabels.add("Mar");
        monthLabels.add("Apr");
        monthLabels.add("May");
        monthLabels.add("Jun");
        monthLabels.add("Jul");
        monthLabels.add("Aug");
        monthLabels.add("Sep");
        monthLabels.add("Oct");
        monthLabels.add("Nov");
        monthLabels.add("Dec");
        return monthLabels;
    }

    private void getReportData(Long accommodationID) {
        Call<ReservationReportDTO> call = ClientUtils.reservationService.getReport(accommodationID);
        call.enqueue(new Callback<ReservationReportDTO>() {
            @Override
            public void onResponse(Call<ReservationReportDTO> call, Response<ReservationReportDTO> response) {
                if (response.isSuccessful()) {
                    ReservationReportDTO reservationReportDTO = response.body();
                    System.out.println(reservationReportDTO);
                    showReportDialog(reservationReportDTO);
                }
            }

            @Override
            public void onFailure(Call<ReservationReportDTO> call, Throwable t) {
                // Handle failure
                Log.e("ERROR", t.getMessage());
            }
        });
    }

}