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
import com.jordanrevata.tecscrum.adapters.SprintAdapter;
import com.jordanrevata.tecscrum.adapters.TeamAdapter;
import com.jordanrevata.tecscrum.models.Sprint;
import com.jordanrevata.tecscrum.models.User;
import com.jordanrevata.tecscrum.repositories.SprintRepository;
import com.jordanrevata.tecscrum.repositories.TeamRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {

    private final static String TAG = TeamFragment.class.getSimpleName();

    RecyclerView recyclerview_team;
    Integer idproject;
    // Nombre de parámetro declarado como constante
    private static final String ARG_PARAM1 = "param1";

    // Atributo param1
    private String param1;



    // Como buena práctica definimos un método fáctory 'newInstance' que reciba los parámetros requeridos 'param1'
    public static TeamFragment newInstance(String param1) {

        TeamFragment fragment = new TeamFragment();

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


    public TeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        idproject = getArguments().getInt("idprojects");

        recyclerview_team = view.findViewById(R.id.recyclerview_team);
        recyclerview_team.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_team.setAdapter(new TeamAdapter(this));

        initialize();

        return view;
    }

    public void initialize(){

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call<List<User>> users = api.getUsersByProject(idproject);

        users.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode );

                    if (response.isSuccessful()) {


                        List<User> userList = response.body();

                        TeamAdapter teamAdapter = (TeamAdapter) recyclerview_team.getAdapter();
                        teamAdapter.setTeam(userList);
                        teamAdapter.notifyDataSetChanged();




                        Log.d(TAG, userList.toString());


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(TeamFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(TeamFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}
