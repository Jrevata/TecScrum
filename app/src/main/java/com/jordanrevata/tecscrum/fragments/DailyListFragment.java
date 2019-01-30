package com.jordanrevata.tecscrum.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.adapters.DailyAdapter;
import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.repositories.DailyRepository;
import com.jordanrevata.tecscrum.services.DailyJobService;

import java.util.ArrayList;
import java.util.List;


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


        List<Daily> asd = new ArrayList<>();

        DailyAdapter dailyAdapter = (DailyAdapter) recyclerview_dailies.getAdapter();
        dailyAdapter.setDailies(DailyJobService.objetos(asd, "01-01-2019", "2019-01-22"));
        dailyAdapter.notifyDataSetChanged();

        Integer prueba = DailyJobService.calculateDays("2019-01-01" , "2019-01-03");
        Toast.makeText(DailyListFragment.this.getContext(), prueba.toString(),Toast.LENGTH_LONG).show();

        return view;
    }



}
