package com.jordanrevata.tecscrum.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.adapters.MoodAdapter;
import com.jordanrevata.tecscrum.models.MoodToday;
import com.jordanrevata.tecscrum.repositories.MoodRepository;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;
import com.jordanrevata.tecscrum.utilities.Function;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoodTodayListFragment extends Fragment {

    private final static String TAG = MoodTodayListFragment.class.getSimpleName();

    RecyclerView recyclerview_mood;
    Integer idsprint;
    String start_sprint;
    String end_sprint;


    public MoodTodayListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        idsprint = getArguments().getInt("idsprint");
        start_sprint = getArguments().getString("start_sprint");
        end_sprint   = getArguments().getString("end_sprint");


        Log.d(TAG, "on create Fragment Dailies" + idsprint.toString() + start_sprint + end_sprint);

        View view = inflater.inflate(R.layout.fragment_mood_today_list, container, false);

        recyclerview_mood = view.findViewById(R.id.recyclerview_mood_list);
        recyclerview_mood.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_mood.setAdapter(new MoodAdapter(this));

        initialize();

        return view;
    }


    private void initialize(){

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call<List<MoodToday>> moodTodayCall = api.getMoodTodays(idsprint, UserRepository.getUser().getIdusers());

        moodTodayCall.enqueue(new Callback<List<MoodToday>>() {
            @Override
            public void onResponse(Call<List<MoodToday>> call, Response<List<MoodToday>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<MoodToday> moodTodayResponse;
                        moodTodayResponse = response.body();



                        List<MoodToday> moodTodayGenerate = Function.generateMoodTodays(moodTodayResponse, start_sprint, end_sprint, idsprint, UserRepository.getUser().getIdusers());

                        Collections.reverse(moodTodayGenerate);

                        MoodAdapter moodAdapter = (MoodAdapter) recyclerview_mood.getAdapter();
                        moodAdapter.setMoodTodays(moodTodayGenerate);
                        moodAdapter.notifyDataSetChanged();


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());

                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MoodTodayListFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MoodToday>> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MoodTodayListFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}
