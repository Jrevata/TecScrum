package com.jordanrevata.tecscrum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.activities.ProjectMenuActivity;
import com.jordanrevata.tecscrum.activities.SprintMenuActivity;
import com.jordanrevata.tecscrum.models.Project;

import java.util.ArrayList;
import java.util.List;



public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private List<Project> projects;
    private Activity activity;

    public ProjectAdapter(Activity activity){
        this.activity = activity;
        this.projects = new ArrayList<>();
    }

    public void setProjects(List<Project> projects){
        this.projects = projects;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textview_projectname;
        public TextView textview_sprints;
        public TextView textview_members;
        public TextView textview_projectstart;
        public TextView textview_projectend;
        public CardView cardview_project;


        public ViewHolder(View itemView){
            super(itemView);

            textview_projectname  = itemView.findViewById(R.id.textview_projectname);
            textview_sprints      = itemView.findViewById(R.id.textview_sprints);
            textview_members      = itemView.findViewById(R.id.textview_members);
            textview_projectstart = itemView.findViewById(R.id.textview_projectstart);
            textview_projectend   = itemView.findViewById(R.id.textview_projectend);
            cardview_project      = itemView.findViewById(R.id.cardview_project);

        }

    }

    @Override
    public ProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProjectAdapter.ViewHolder viewHolder, int position) {

        final Project project = this.projects.get(position);

        viewHolder.textview_projectname.setText(project.getProject_name());
        viewHolder.textview_sprints.setText("Sprints : " + String.valueOf(project.getNumber_sprints()));
        viewHolder.textview_members.setText("Members : " + String.valueOf(project.getNumber_members()));
        viewHolder.textview_projectstart.setText(project.getStart_date());
        viewHolder.textview_projectend.setText(project.getEnd_date());

        viewHolder.cardview_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(viewHolder.cardview_project.getContext(), project.getProject_name(), Toast.LENGTH_SHORT).show();
                Intent intentProject = new Intent(activity.getBaseContext(), ProjectMenuActivity.class);
                intentProject.putExtra("idprojects", project.getIdprojects());
                activity.startActivity(intentProject);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.projects.size();
    }
}
