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
import com.jordanrevata.tecscrum.repositories.DailyRepository;


public class DailyListFragment extends Fragment {

    RecyclerView recyclerview_dailies;

    public DailyListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily_list, container, false);

        recyclerview_dailies = view.findViewById(R.id.recyclerview_daily_list);
        recyclerview_dailies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_dailies.setAdapter(new DailyAdapter(this));

        DailyAdapter dailyAdapter = (DailyAdapter) recyclerview_dailies.getAdapter();
        dailyAdapter.setDailies(DailyRepository.getList());
        dailyAdapter.notifyDataSetChanged();

        return view;
    }

}
