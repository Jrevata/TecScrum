package com.jordanrevata.tecscrum.adapters;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.activities.DailyActivity;
import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.models.Forum;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private List<Daily> dailies;
    private Fragment fragment;

    public DailyAdapter(Fragment fragment){
            this.fragment = fragment;
            this.dailies = new ArrayList<>();
    }

    public void setDailies(List<Daily> dailies){
        this.dailies = dailies;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textview_dailycheckname;
        public TextView textview_dailycheckdate;
        public ImageView imageview_daily;
        public CardView cardview_daily;

        public ViewHolder(View itemView){
            super(itemView);

            textview_dailycheckname = itemView.findViewById(R.id.textview_dailycheckname);
            textview_dailycheckdate = itemView.findViewById(R.id.textview_dailycheckdate);
            imageview_daily         = itemView.findViewById(R.id.imageview_daily);
            cardview_daily          = itemView.findViewById(R.id.cardview_daily);
        }

    }


    @Override
    public DailyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent, false);
        return  new DailyAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final DailyAdapter.ViewHolder viewHolder, int position) {

        final Daily daily = this.dailies.get(position);

        viewHolder.textview_dailycheckname.setText(daily.getDailyname());
        viewHolder.textview_dailycheckdate.setText(daily.getDate_daily());



        viewHolder.textview_dailycheckname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDaily = new Intent(fragment.getContext(), DailyActivity.class);
                fragment.startActivity(intentDaily);
            }
        });

        if(daily.getIddailies() == 1){
            viewHolder.imageview_daily.setBackgroundResource(R.drawable.img_check_pending);
            viewHolder.textview_dailycheckname.setTextColor(viewHolder.textview_dailycheckname.getResources().getColor(R.color.colorPrimary));
            viewHolder.textview_dailycheckname.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            viewHolder.textview_dailycheckname.setTextColor(viewHolder.textview_dailycheckname.getResources().getColor(R.color.Black_Eel));
            viewHolder.textview_dailycheckname.setTypeface(Typeface.DEFAULT);

            if (daily.getState())
                viewHolder.imageview_daily.setBackgroundResource(R.drawable.img_check_finish);
            else
                viewHolder.imageview_daily.setBackgroundResource(R.drawable.img_check_bad);
        }

    }


    @Override
    public int getItemCount() {
        return this.dailies.size();
    }
}
