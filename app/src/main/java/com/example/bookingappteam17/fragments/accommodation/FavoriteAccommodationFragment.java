    package com.example.bookingappteam17.fragments.accommodation;

    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProvider;

    import com.example.bookingappteam17.R;
    import com.example.bookingappteam17.clients.ClientUtils;
    import com.example.bookingappteam17.databinding.FragmentAccommodationsPageBinding;
    import com.example.bookingappteam17.databinding.FragmentFavoriteAccommodationsBinding;
    import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
    import com.example.bookingappteam17.fragments.FragmentTransition;
    import com.example.bookingappteam17.services.accommodation.IAccommodationService;

    import java.util.ArrayList;
    import java.util.List;

    public class FavoriteAccommodationFragment extends Fragment {

        private ArrayList<AccommodationCardDTO> accommodations = new ArrayList<>();
        private FavoriteAccommodationPageViewModel accommodationsPageViewModel;
        private FavoriteAccommodationListFragment accommodationListFragment;
        private FragmentFavoriteAccommodationsBinding binding;
        private SharedPreferences sharedPreferences;
        private IAccommodationService accommodationService = ClientUtils.accommodationService;

        public static FavoriteAccommodationFragment newInstance() {
            return new FavoriteAccommodationFragment();
        }
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences("user_prefs", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String role = sharedPreferences.getString("role","");
        Long id = sharedPreferences.getLong("userId", 1);
        binding = FragmentFavoriteAccommodationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        accommodationsPageViewModel = new ViewModelProvider(this).get(FavoriteAccommodationPageViewModel.class);

        // Initialize the list fragment
        accommodationListFragment = new FavoriteAccommodationListFragment();

        // Set up the adapter
        AccommodationListAdapter adapter = new AccommodationListAdapter(getActivity(), accommodations);
            accommodationListFragment.setListAdapter(adapter);

        // Load data
            accommodationsPageViewModel.loadAccommodations(username,role, id);

        // Observe LiveData and update UI
            accommodationsPageViewModel.getAccommodationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<AccommodationCardDTO>>() {
            @Override
            public void onChanged(List<AccommodationCardDTO> accommodationCardDTOS) {
                adapter.setAccommodations(accommodationCardDTOS);
            }
        });


            FragmentTransition.to(accommodationListFragment, getActivity(), false, R.id.scroll_products_list1);

            return root;
    }

    }
