package com.jordanrevata.tecscrum.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.fragments.SprintListFragment;
import com.jordanrevata.tecscrum.fragments.TeamFragment;

public class ProjectMenuActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment fragmentSprint;
    Fragment fragmentTeam;
    Integer idprojects;


    @Override
    protected void onStart() {
        super.onStart();

        if(!isNetworkAvailable()){

            Toast.makeText(ProjectMenuActivity.this,getString(R.string.connect_network), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu);

        if(!isNetworkAvailable()){

            Toast.makeText(ProjectMenuActivity.this,getString(R.string.connect_network), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        idprojects = getIntent().getExtras().getInt("idprojects");

        final Bundle bundleGeneral = new Bundle();
        bundleGeneral.putInt("idprojects", idprojects);



        fragmentManager = getSupportFragmentManager();
        fragmentSprint  = new SprintListFragment();
        fragmentSprint.setArguments(bundleGeneral);
        fragmentManager.beginTransaction().replace(R.id.content_menu_project, fragmentSprint).addToBackStack("tag").commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_project_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_sprints:

                        Toast.makeText(ProjectMenuActivity.this, R.string.go_sprints, Toast.LENGTH_SHORT).show();

                        // Create FirstFragment
                        fragmentSprint = new SprintListFragment();
                        fragmentSprint.setArguments(bundleGeneral);
                        // Replace content
                        fragmentManager.beginTransaction().replace(R.id.content_menu_project, fragmentSprint).addToBackStack("tag").commit();
                        break;
                    case R.id.menu_team:
                        Toast.makeText(ProjectMenuActivity.this, R.string.go_team, Toast.LENGTH_SHORT).show();
                        fragmentTeam = new TeamFragment();
                        fragmentTeam.setArguments(bundleGeneral);
                        fragmentManager.beginTransaction().replace(R.id.content_menu_project, fragmentTeam).addToBackStack("tag").commit();

                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}



