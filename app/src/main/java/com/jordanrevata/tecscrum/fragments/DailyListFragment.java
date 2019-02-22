package com.jordanrevata.tecscrum.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.activities.LoginActivity;
import com.jordanrevata.tecscrum.activities.MainActivity;
import com.jordanrevata.tecscrum.adapters.DailyAdapter;
import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.repositories.DailyRepository;
import com.jordanrevata.tecscrum.repositories.ProjectRepository;
import com.jordanrevata.tecscrum.repositories.SavedRepository;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;
import com.jordanrevata.tecscrum.services.DailyJobService;
import com.jordanrevata.tecscrum.utilities.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DailyListFragment extends Fragment {

    private final  String TAG = DailyListFragment.class.getSimpleName();

    private RecyclerView recyclerview_dailies;
    private Integer idsprint;
    private String start_sprint;
    private String end_sprint;
    DailyAdapter dailyAdapter;
    TextView textview_verify_list_daily;


    @Override
    public void onResume() {
        super.onResume();
        recyclerview_dailies.setAdapter(null);
        initialize();
    }

    public DailyListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        idsprint = getArguments().getInt("idsprint");
        start_sprint = getArguments().getString("start_sprint");
        end_sprint   = getArguments().getString("end_sprint");

        Log.d(TAG, "on create Fragment Dailies" + idsprint.toString() + start_sprint + end_sprint);

        View view = inflater.inflate(R.layout.fragment_daily_list, container, false);

        textview_verify_list_daily = view.findViewById(R.id.textview_verify_list_daily);
        textview_verify_list_daily.setVisibility(View.INVISIBLE);

        recyclerview_dailies = view.findViewById(R.id.recyclerview_daily_list);
        recyclerview_dailies.setAdapter(null);

        initialize();

        return view;

    }

    private void initialize(){


        recyclerview_dailies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_dailies.setAdapter(new DailyAdapter(this));




        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Daily>> dailies = api.getDailies(idsprint, UserRepository.getUser().getIdusers());

        dailies.enqueue(new Callback<List<Daily>>() {
            @Override
            public void onResponse(Call<List<Daily>> call, Response<List<Daily>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Daily> dailiesResponse;
                        dailiesResponse = response.body();



                        List<Daily> dailiesGenerate = Function.generateDailies(dailiesResponse, start_sprint, end_sprint, idsprint, UserRepository.getUser().getIdusers());

                        Collections.reverse(dailiesGenerate);

                        dailyAdapter = (DailyAdapter) recyclerview_dailies.getAdapter();
                        dailyAdapter.setDailies(dailiesGenerate);
                        dailyAdapter.notifyDataSetChanged();

                        if(dailiesGenerate.isEmpty()){
                            textview_verify_list_daily.setVisibility(View.VISIBLE);
                        }


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DailyListFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Daily>> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DailyListFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }




}
