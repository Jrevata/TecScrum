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
import com.jordanrevata.tecscrum.services.DailyJobService;
import com.jordanrevata.tecscrum.utilities.Function;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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


        if(daily.getIddailies()==null){

            Calendar nowCalendar = Calendar.getInstance();
            String now = Function.convertToString(nowCalendar);

            if(now.equals(daily.getDate_daily())){

                viewHolder.imageview_daily.setBackgroundResource(R.drawable.img_check_pending);
                viewHolder.textview_dailycheckname.setTextColor(viewHolder.textview_dailycheckname.getResources().getColor(R.color.colorPrimary));
                viewHolder.textview_dailycheckname.setTypeface(Typeface.DEFAULT_BOLD);
                viewHolder.textview_dailycheckname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentDaily = new Intent(fragment.getContext(), DailyActivity.class);
                        intentDaily.putExtra("idsprint", daily.getSprints_idsprints());
                        intentDaily.putExtra("iduser", daily.getUsers_idusers());
                        intentDaily.putExtra("date", daily.getDate_daily());
                        intentDaily.putExtra("name", daily.getDailyname());
                        intentDaily.putExtra("action" , "Edit");
                        fragment.startActivity(intentDaily);
                    }
                });

            }else{

                viewHolder.imageview_daily.setBackgroundResource(R.drawable.img_check_bad);
                viewHolder.textview_dailycheckname.setTextColor(viewHolder.textview_dailycheckname.getResources().getColor(R.color.Black_Eel));
                viewHolder.textview_dailycheckname.setTypeface(Typeface.DEFAULT);
                viewHolder.textview_dailycheckname.setClickable(false);
            }

        }else{
            viewHolder.imageview_daily.setBackgroundResource(R.drawable.img_check_finish);
            viewHolder.textview_dailycheckname.setTextColor(viewHolder.textview_dailycheckname.getResources().getColor(R.color.Black_Eel));
            viewHolder.textview_dailycheckname.setTypeface(Typeface.DEFAULT_BOLD);


            viewHolder.textview_dailycheckname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDaily = new Intent(fragment.getContext(), DailyActivity.class);
                    intentDaily.putExtra("iddaily", daily.getIddailies());
                    intentDaily.putExtra("name", daily.getDailyname());
                    intentDaily.putExtra("date", daily.getDate_daily());
                    intentDaily.putExtra("action", "NoEdit");
                    fragment.startActivity(intentDaily);
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return this.dailies.size();
    }
}
