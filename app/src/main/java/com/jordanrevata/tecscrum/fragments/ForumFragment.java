package com.jordanrevata.tecscrum.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.adapters.ForumAdapter;
import com.jordanrevata.tecscrum.repositories.ForumRepository;



public class ForumFragment extends Fragment {


    RecyclerView recyclerview_comments;

    public ForumFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        recyclerview_comments = view.findViewById(R.id.recyclerview_comments);
        recyclerview_comments.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_comments.setAdapter(new ForumAdapter(this));

        ForumAdapter forumAdapter = (ForumAdapter) recyclerview_comments.getAdapter();
        forumAdapter.setForum(ForumRepository.getList());
        forumAdapter.notifyDataSetChanged();

        return view;

    }

}
