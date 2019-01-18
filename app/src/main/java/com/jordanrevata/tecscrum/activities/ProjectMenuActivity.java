package com.jordanrevata.tecscrum.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.fragments.SprintListFragment;
import com.jordanrevata.tecscrum.fragments.TeamFragment;

public class ProjectMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_project_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_sprints:
                        Toast.makeText(ProjectMenuActivity.this, R.string.go_sprints, Toast.LENGTH_SHORT).show();
                        // Get FragmentManager
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        // Create FirstFragment
                        Fragment fragment = new SprintListFragment();

                        // Replace content
                        fragmentManager.beginTransaction().replace(R.id.content_menu_project, fragment).addToBackStack("tag").commit();


                        break;
                    case R.id.menu_team:
                        Toast.makeText(ProjectMenuActivity.this, R.string.go_team, Toast.LENGTH_SHORT).show();

                        // Get FragmentManager
                        FragmentManager fragmentManager2 = getSupportFragmentManager();

                        // Create FirstFragment
                        Fragment fragment2 = new TeamFragment();

                        // Replace content
                        fragmentManager2.beginTransaction().replace(R.id.content_menu_project, fragment2).addToBackStack("tag").commit();

                        break;
                }
                return true;
            }
        });

    }


}



