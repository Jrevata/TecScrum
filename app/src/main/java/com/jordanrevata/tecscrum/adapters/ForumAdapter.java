package com.jordanrevata.tecscrum.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;

import com.jordanrevata.tecscrum.models.Forum;

import java.util.ArrayList;
import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder>{

    private List<Forum> comments;
    private Fragment fragment;

    public ForumAdapter(Fragment fragment){
        this.fragment = fragment;
        this.comments = new ArrayList<>();
    }

    public void setForum(List<Forum> comments){
        this.comments = comments;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textview_fullname_comment;
        public TextView textview_date_comment;
        public TextView textview_message_comment;
        public ImageView imageview_photo_comment;

        public ViewHolder(View itemView){
            super(itemView);

            textview_fullname_comment = itemView.findViewById(R.id.textview_fullname_comment);
            textview_date_comment     = itemView.findViewById(R.id.textview_date_comment);
            textview_message_comment  = itemView.findViewById(R.id.textview_message_comment);
            imageview_photo_comment   = itemView.findViewById(R.id.imageview_photo_comment);


        }

    }

    @Override
    public ForumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ForumAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ForumAdapter.ViewHolder viewHolder, int position) {

        final Forum comment = this.comments.get(position);

        viewHolder.textview_fullname_comment.setText(comment.getFullname());
        viewHolder.textview_date_comment.setText(comment.getDatetime_comment());
        viewHolder.textview_message_comment.setText(comment.getMessage());




    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }



}
