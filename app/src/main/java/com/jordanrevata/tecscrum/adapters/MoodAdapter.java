package com.jordanrevata.tecscrum.adapters;

import android.content.Intent;
import android.graphics.Typeface;
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
import com.jordanrevata.tecscrum.activities.MoodTodayActivity;
import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.models.MoodToday;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.utilities.Function;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder>  {


    private List<MoodToday> moodTodays;
    private Fragment fragment;

    public MoodAdapter(Fragment fragment){
        this.fragment = fragment;
        moodTodays = new ArrayList<>();
    }

    public void setMoodTodays(List<MoodToday> moodTodays){
        this.moodTodays = moodTodays;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textview_moodcheckname;
        public TextView textview_moodcheckdate;
        public ImageView imageview_moodtoday;

        public CardView cardview_moodtoday;

        public ViewHolder(View itemView){
            super(itemView);

            textview_moodcheckname = itemView.findViewById(R.id.textview_moodcheckname);
            textview_moodcheckdate = itemView.findViewById(R.id.textview_moodcheckdate);
            imageview_moodtoday = itemView.findViewById(R.id.imageview_moodtoday);
            cardview_moodtoday     = itemView.findViewById(R.id.cardview_moodtoday);

        }

    }


    @Override
    public MoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moodtoday, parent, false);
        return  new MoodAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MoodAdapter.ViewHolder viewHolder, int position) {

        final MoodToday moodToday = this.moodTodays.get(position);

        viewHolder.textview_moodcheckname.setText(moodToday.getMoodname());
        viewHolder.textview_moodcheckdate.setText(moodToday.getDate_mood());

        if(moodToday.getIdmoodtoday()==null){

            Calendar nowCalendar = Calendar.getInstance();
            String now = Function.convertToString(nowCalendar);

            if(now.equals(moodToday.getDate_mood())){

                viewHolder.imageview_moodtoday.setBackgroundResource(R.drawable.img_check_pending);
                viewHolder.textview_moodcheckname.setTextColor(viewHolder.textview_moodcheckname.getResources().getColor(R.color.colorPrimary));
                viewHolder.textview_moodcheckname.setTypeface(Typeface.DEFAULT_BOLD);
                viewHolder.textview_moodcheckname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intentMood = new Intent(fragment.getContext(), MoodTodayActivity.class);
                        intentMood.putExtra("idsprint", moodToday.getSprints_idsprints());
                        intentMood.putExtra("iduser", UserRepository.getUser().getIdusers());
                        intentMood.putExtra("date", moodToday.getDate_mood());
                        intentMood.putExtra("name", moodToday.getMoodname());
                        intentMood.putExtra("action", "Edit");
                        fragment.startActivity(intentMood);
                    }
                });


            }else{
                viewHolder.imageview_moodtoday.setBackgroundResource(R.drawable.img_check_bad);
                viewHolder.textview_moodcheckname.setTextColor(viewHolder.textview_moodcheckname.getResources().getColor(R.color.Black_Eel));
                viewHolder.textview_moodcheckname.setTypeface(Typeface.DEFAULT);
                viewHolder.textview_moodcheckname.setClickable(false);

            }


        } else {

            viewHolder.imageview_moodtoday.setBackgroundResource(R.drawable.img_check_finish);
            viewHolder.textview_moodcheckname.setTextColor(viewHolder.textview_moodcheckname.getResources().getColor(R.color.Black_Eel));
            viewHolder.textview_moodcheckname.setTypeface(Typeface.DEFAULT_BOLD);


            viewHolder.textview_moodcheckname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentMood = new Intent(fragment.getContext(), MoodTodayActivity.class);
                    intentMood.putExtra("idmood", moodToday.getIdmoodtoday());
                    intentMood.putExtra("name", moodToday.getMoodname());
                    intentMood.putExtra("date", moodToday.getDate_mood());
                    intentMood.putExtra("action", "NoEdit");
                    fragment.startActivity(intentMood);
                }
            });

        }



    }


    @Override
    public int getItemCount() {
        return this.moodTodays.size();
    }
}
