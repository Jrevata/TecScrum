package com.jordanrevata.tecscrum.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.adapters.ProjectAdapter;
import com.jordanrevata.tecscrum.adapters.SprintAdapter;
import com.jordanrevata.tecscrum.repositories.ProjectRepository;
import com.jordanrevata.tecscrum.repositories.SprintRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class SprintListFragment extends Fragment {

    RecyclerView recyclerView_sprints;

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


        recyclerView_sprints = view.findViewById(R.id.recyclerview_sprints);
        recyclerView_sprints.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_sprints.setAdapter(new SprintAdapter(this));

        SprintAdapter sprintAdapter = (SprintAdapter) recyclerView_sprints.getAdapter();
        sprintAdapter.setProjects(SprintRepository.getList());
        sprintAdapter.notifyDataSetChanged();

        return view;

    }



}
