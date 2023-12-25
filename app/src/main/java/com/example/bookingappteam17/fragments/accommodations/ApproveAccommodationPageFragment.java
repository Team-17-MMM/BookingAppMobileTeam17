package com.example.bookingappteam17.fragments.accommodations;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.FragmentAccommodationsPageBinding;
import com.example.bookingappteam17.databinding.FragmentApproveAccommodationPageBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardRDTO;
import com.example.bookingappteam17.fragments.FragmentTransition;
import com.example.bookingappteam17.services.IAccommodationService;

import java.util.ArrayList;
import java.util.HashSet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveAccommodationPageFragment extends Fragment {
    public static ArrayList<AccommodationCardDTO> accommodations = new ArrayList<AccommodationCardDTO>();
    private ApproveAccommodationPageViewModel approveAccommodationsPageViewModel;
    private ApproveAccommodationListFragment accommodationListFragment;
    private FragmentApproveAccommodationPageBinding binding;
    private SharedPreferences sharedPreferences;
    private IAccommodationService accommodationService = ClientUtils.accommodationService;
    public static AccommodationPageFragment newInstance() {
        return new AccommodationPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        approveAccommodationsPageViewModel = new ViewModelProvider(this).get(ApproveAccommodationPageViewModel.class);

        binding = FragmentApproveAccommodationPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareAccommodationList(accommodations);

        // Calls FragmentTransition.to to replace the layout with a ApproveAccommodationsListFragment.
        FragmentTransition.to(ApproveAccommodationListFragment.newInstance(accommodations), getActivity(), false, R.id.scroll_approve_accommodation_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareAccommodationList(ArrayList<AccommodationCardDTO> accommodationsList) {

        Call<HashSet<AccommodationCardRDTO>> call = accommodationService.getNotApprovedAccommodations();
        call.enqueue(new Callback<HashSet<AccommodationCardRDTO>>() {
            @Override
            public void onResponse(Call<HashSet<AccommodationCardRDTO>> call, Response<HashSet<AccommodationCardRDTO>> response) {
             if (response.isSuccessful()) {
                 accommodations.clear();
                 HashSet<AccommodationCardRDTO> accommodations = response.body();
                 for (AccommodationCardRDTO accommodationCardDTO : accommodations) {
                     loadImage(new AccommodationCardDTO(accommodationCardDTO), accommodationsList);
                 }
             }
            }

            @Override
            public void onFailure(Call<HashSet<AccommodationCardRDTO>> call, Throwable t) {
             Log.d("Error", t.getMessage());
            }
            }
        );
    }

    private void loadImage(AccommodationCardDTO accommodationCardDTO, ArrayList<AccommodationCardDTO> products) {
        Call<ResponseBody> callImage = accommodationService.getAccommodationImage(accommodationCardDTO.getAccommodationID());
        callImage.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> callImage, Response<ResponseBody> responseImage) {
                if (responseImage.isSuccessful()) {
                    Bitmap bmp = BitmapFactory.decodeStream(responseImage.body().byteStream());
                    // Use the bitmap as needed, for example set it to an ImageView
                    accommodationCardDTO.setImage(bmp);
                    products.add(accommodationCardDTO);
                } else {
                    Log.d("Error", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Error", "Failed to get image", t);
            }
        });
    }
}
