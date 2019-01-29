package com.jordanrevata.tecscrum.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.activities.SprintMenuActivity;
import com.jordanrevata.tecscrum.models.Project;
import com.jordanrevata.tecscrum.models.Sprint;

import java.util.ArrayList;
import java.util.List;

public class SprintAdapter extends RecyclerView.Adapter<SprintAdapter.ViewHolder>{

    private List<Sprint> sprints;
    private Fragment fragment;

    public SprintAdapter(Fragment fragment){
            this.fragment = fragment;
            this.sprints = new ArrayList<>();
            }

    public void setProjects(List<Sprint> sprints){

            this.sprints = sprints;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textview_sprintname;
        public TextView textview_sprintgoal;
        public TextView textview_sprintstart;
        public TextView textview_sprintend;
        public CardView cardview_sprint;

        public ViewHolder(View itemView){
            super(itemView);

            textview_sprintname  = itemView.findViewById(R.id.textview_sprintname);
            textview_sprintgoal  = itemView.findViewById(R.id.textview_sprintgoal);
            textview_sprintstart = itemView.findViewById(R.id.textview_sprintstart);
            textview_sprintend   = itemView.findViewById(R.id.textview_sprintend);
            cardview_sprint      = itemView.findViewById(R.id.cardview_sprint);

        }

    }

    @Override
    public SprintAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sprint, parent, false);
        return new SprintAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SprintAdapter.ViewHolder viewHolder, int position) {

        final Sprint sprint = this.sprints.get(position);

        viewHolder.textview_sprintname.setText(sprint.getSprint_name());
        viewHolder.textview_sprintgoal.setText("Spring Goal: " + sprint.getSprint_goal());
        viewHolder.textview_sprintstart.setText(sprint.getStart_date());
        viewHolder.textview_sprintend.setText(sprint.getEnd_date());


        viewHolder.cardview_sprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(viewHolder.cardview_sprint.getContext(), sprint.getSprint_name(), Toast.LENGTH_SHORT).show();
                Intent intentSprint = new Intent(fragment.getContext(), SprintMenuActivity.class);
                intentSprint.putExtra("idsprint", sprint.getIdsprints());
                fragment.startActivity(intentSprint);


            }
        });

    }

    @Override
    public int getItemCount() {
        return this.sprints.size();
    }

}
