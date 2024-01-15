    package com.example.bookingappteam17.fragments;
    
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentPagerAdapter;
    import androidx.viewpager.widget.ViewPager;
    
    import com.example.bookingappteam17.R;
    import com.example.bookingappteam17.fragments.accommodation.ApproveAccommodationPageFragment;
    import com.example.bookingappteam17.fragments.report.ManageUserReportsFragment;
    import com.example.bookingappteam17.fragments.review.ApproveReviewListFragment;
    import com.example.bookingappteam17.fragments.review.ManageReviewPageFragment;
    import com.google.android.material.tabs.TabLayout;
    
    public class TabApproveAndManageReviewFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_tab_approve_and_manage_review, container, false);
    
            ViewPager viewPager = view.findViewById(R.id.approve_manage_review_view_pager);
            TabLayout tabs = view.findViewById(R.id.approve_manage_review_tab_layout);
    
            viewPager.setAdapter(new SectionPagerAdapter(getChildFragmentManager()));
            tabs.setupWithViewPager(viewPager);
    
            return view;
        }
    
        public static class SectionPagerAdapter extends FragmentPagerAdapter {
    
            public SectionPagerAdapter(FragmentManager fm) {
                super(fm);
            }
    
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ApproveAccommodationPageFragment();
                    case 1:
                        return new ManageReviewPageFragment();
                    case 2:
                        return new ManageUserReportsFragment();
                    case 3:
                        return new ApproveReviewListFragment();
                    default:
                        return null;
                }
            }
    
            @Override
            public int getCount() {
                return 4;
            }
    
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Approve Accommodation";
                    case 1:
                        return "Manage Review";
                    case 2:
                        return "Manage Reports";
                    case 3:
                        return "Approve Review";
                    default:
                        return null;
                }
            }
        }
    }
