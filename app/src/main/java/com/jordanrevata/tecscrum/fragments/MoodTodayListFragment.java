package com.jordanrevata.tecscrum.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.adapters.DailyAdapter;
import com.jordanrevata.tecscrum.adapters.MoodAdapter;
import com.jordanrevata.tecscrum.repositories.DailyRepository;
import com.jordanrevata.tecscrum.repositories.MoodRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoodTodayListFragment extends Fragment {

    RecyclerView recyclerview_mood;

    public MoodTodayListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood_today_list, container, false);

        recyclerview_mood = view.findViewById(R.id.recyclerview_mood_list);
        recyclerview_mood.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_mood.setAdapter(new MoodAdapter(this));

        MoodAdapter moodAdapter = (MoodAdapter) recyclerview_mood.getAdapter();
        moodAdapter.setMoodTodays(MoodRepository.getList());
        moodAdapter.notifyDataSetChanged();

        return view;
    }

}
