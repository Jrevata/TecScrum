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
import com.jordanrevata.tecscrum.activities.MainActivity;
import com.jordanrevata.tecscrum.adapters.ProjectAdapter;
import com.jordanrevata.tecscrum.adapters.SprintAdapter;
import com.jordanrevata.tecscrum.models.Project;
import com.jordanrevata.tecscrum.models.Sprint;
import com.jordanrevata.tecscrum.repositories.ProjectRepository;
import com.jordanrevata.tecscrum.repositories.SprintRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SprintListFragment extends Fragment {

    RecyclerView recyclerView_sprints;
    Integer idproject;

    private static final String TAG = SprintListFragment.class.getSimpleName();

    // Nombre de parámetro declarado como constante
    private static final String ARG_PARAM1 = "param1";

    // Atributo param1
    private String param1;



    // Como buena práctica definimos un método fáctory 'newInstance' que reciba los parámetros requeridos 'param1'
    public static SprintListFragment newInstance(String param1) {

        SprintListFragment fragment = new SprintListFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
        }
    }




    public SprintListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sprint_list, container, false);

        idproject = getArguments().getInt("idprojects");

        recyclerView_sprints = view.findViewById(R.id.recyclerview_sprints);
        recyclerView_sprints.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_sprints.setAdapter(new SprintAdapter(this));

        initialize();

        return view;

    }



    public void initialize(){

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Sprint>> sprints = api.getSprintsByProject(idproject);

        sprints.enqueue(new Callback<List<Sprint>>() {
            @Override
            public void onResponse(Call<List<Sprint>> call, Response<List<Sprint>> response) {

                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode );

                    if (response.isSuccessful()) {


                        List<Sprint> sprintList = response.body();

                        SprintAdapter sprintAdapter = (SprintAdapter) recyclerView_sprints.getAdapter();
                        sprintAdapter.setProjects(sprintList);
                        sprintAdapter.notifyDataSetChanged();




                        Log.d(TAG, sprintList.toString());


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(SprintListFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }


            }

            @Override
            public void onFailure(Call<List<Sprint>> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(SprintListFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


}
