package com.jordanrevata.tecscrum.activities;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.adapters.ProjectAdapter;
import com.jordanrevata.tecscrum.repositories.ProjectRepository;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerview_projects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_projects);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_projects);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, android.R.string.ok, android.R.string.cancel);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Change navigation header information
        ImageView photoImage = navigationView.getHeaderView(0).findViewById(R.id.menu_photo);
        photoImage.setBackgroundResource(R.drawable.ic_profile);

        TextView fullnameText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_fullname);
        fullnameText.setText("Jordan Revata");

        TextView emailText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_email);
        emailText.setText("jordan.revata@tecsup.edu.pe");



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Do action by menu item id
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "Go home...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_calendar:
                        Toast.makeText(MainActivity.this, "Go calendar...", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_settings:
                        Toast.makeText(MainActivity.this, "Go settings...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(MainActivity.this, "Do logout...", Toast.LENGTH_SHORT).show();
                        break;
                }

                // Close drawer
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


        initProjectList();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Option open drawer
                if(!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(GravityCompat.START);   // Open drawer
                else
                    drawerLayout.closeDrawer(GravityCompat.START);    // Close drawer
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void initProjectList(){

        recyclerview_projects = findViewById(R.id.recyclerview_projects);
        recyclerview_projects.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_projects.setAdapter(new ProjectAdapter(this));

        ProjectAdapter projectAdapter = (ProjectAdapter) recyclerview_projects.getAdapter();
        projectAdapter.setProjects(ProjectRepository.getList());
        projectAdapter.notifyDataSetChanged();


    }


}
