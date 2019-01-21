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
import com.jordanrevata.tecscrum.activities.UserDetailActivity;
import com.jordanrevata.tecscrum.models.Sprint;
import com.jordanrevata.tecscrum.models.User;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private List<User> users;
    private Fragment fragment;

    public TeamAdapter(Fragment fragment){
        this.fragment = fragment;
        this.users = new ArrayList<>();
    }

    public void setTeam(List<User> users){

        this.users = users;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textview_member_fullname;
        public TextView textview_member_email;
        public CardView cardview_member;

        public ViewHolder(View itemView){
            super(itemView);

            textview_member_fullname  = itemView.findViewById(R.id.textview_member_fullname);
            textview_member_email  = itemView.findViewById(R.id.textview_member_email);

            cardview_member      = itemView.findViewById(R.id.cardview_member);

        }

    }

    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new TeamAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TeamAdapter.ViewHolder viewHolder, int position) {

        final User user = this.users.get(position);

        viewHolder.textview_member_fullname.setText(user.getGivenName() + " " + user.getFamilyName());
        viewHolder.textview_member_email.setText(user.getEmail());



        viewHolder.cardview_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(viewHolder.cardview_member.getContext(), "Hola " + user.getGivenName(), Toast.LENGTH_SHORT).show();
                Intent intentTeam = new Intent(fragment.getContext(), UserDetailActivity.class);
                intentTeam.putExtra("fullname", user.getGivenName() + " " + user.getFamilyName());
                intentTeam.putExtra("email", user.getEmail());
                intentTeam.putExtra("phone", user.getPhone());
                intentTeam.putExtra("code" , "noEdit");
                fragment.startActivity(intentTeam);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }


}
