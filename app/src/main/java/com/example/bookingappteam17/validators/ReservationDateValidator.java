package com.example.bookingappteam17.validators;

import android.os.Parcel;

import com.google.android.material.datepicker.CalendarConstraints.DateValidator;

import java.util.ArrayList;
import java.util.List;

    public class ReservationDateValidator implements DateValidator {

        private final List<Long> enabledDates;

        public ReservationDateValidator(List<Long> enabledDates) {
            this.enabledDates = enabledDates;
        }

        @Override
        public boolean isValid(long date) {
            return enabledDates.contains(date);
        }

        protected ReservationDateValidator(Parcel in) {
            enabledDates = new ArrayList<>();
            in.readList(enabledDates, Long.class.getClassLoader());
        }

        public static final Creator<ReservationDateValidator> CREATOR = new Creator<ReservationDateValidator>() {
            @Override
            public ReservationDateValidator createFromParcel(Parcel in) {
                return new ReservationDateValidator(in);
            }

            @Override
            public ReservationDateValidator[] newArray(int size) {
                return new ReservationDateValidator[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(enabledDates);
        }
    }
