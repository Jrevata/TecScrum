package com.jordanrevata.tecscrum.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordanrevata.tecscrum.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoodTodayListFragment extends Fragment {


    public MoodTodayListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mood_today_list, container, false);
    }

}
