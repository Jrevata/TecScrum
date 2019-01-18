package com.jordanrevata.tecscrum.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.adapters.SprintAdapter;
import com.jordanrevata.tecscrum.adapters.TeamAdapter;
import com.jordanrevata.tecscrum.repositories.SprintRepository;
import com.jordanrevata.tecscrum.repositories.TeamRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {


    RecyclerView recyclerview_team;
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


        recyclerview_team = view.findViewById(R.id.recyclerview_team);
        recyclerview_team.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_team.setAdapter(new TeamAdapter(this));

        TeamAdapter teamAdapter = (TeamAdapter) recyclerview_team.getAdapter();
        teamAdapter.setTeam(TeamRepository.getList());
        teamAdapter.notifyDataSetChanged();

        return view;
    }

}
