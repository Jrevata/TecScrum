package com.jordanrevata.tecscrum.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.fragments.DailyListFragment;
import com.jordanrevata.tecscrum.fragments.ForumFragment;
import com.jordanrevata.tecscrum.fragments.MoodTodayListFragment;

public class SprintMenuActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment dailyFragment;
    Fragment moodFragment;
    Fragment forumFragment;

    Integer idsprint;
    String start_sprint;
    String end_sprint;

    BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayoutSprintMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_menu);

        idsprint = getIntent().getExtras().getInt("idsprint");
        start_sprint = getIntent().getExtras().getString("start_sprint");
        end_sprint = getIntent().getExtras().getString("end_sprint");

        final Bundle bundleSprint = new Bundle();
        bundleSprint.putInt("idsprint", idsprint);
        bundleSprint.putString("start_sprint", start_sprint);
        bundleSprint.putString("end_sprint",end_sprint);

        relativeLayoutSprintMenu = findViewById(R.id.content_menu_sprint);
        bottomNavigationView =  findViewById(R.id.bottom_navigation_sprint_menu);

        fragmentManager = getSupportFragmentManager();
        dailyFragment = new DailyListFragment();
        dailyFragment.setArguments(bundleSprint);
        fragmentManager.beginTransaction().replace(R.id.content_menu_sprint, dailyFragment).addToBackStack("tag").commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()){
                    case R.id.menu_dailies:

                        dailyFragment = new DailyListFragment();
                        dailyFragment.setArguments(bundleSprint);
                        fragmentManager.beginTransaction().replace(R.id.content_menu_sprint, dailyFragment).addToBackStack("tag").commit();
                        break;

                    case R.id.menu_moodtoday:

                        moodFragment = new MoodTodayListFragment();
                        moodFragment.setArguments(bundleSprint);
                        fragmentManager.beginTransaction().replace(R.id.content_menu_sprint, moodFragment).addToBackStack("tag").commit();
                        break;

                    case R.id.menu_forum:

                        forumFragment = new ForumFragment();
                        forumFragment.setArguments(bundleSprint);
                        fragmentManager.beginTransaction().replace(R.id.content_menu_sprint, forumFragment).addToBackStack("tag").commit();
                        break;
                }

                updateNavigationBarState(menuItem.getItemId());

                return true;

            }
        });

    }

    private void updateNavigationBarState(int actionId){
        Menu menu = bottomNavigationView.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == actionId);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
