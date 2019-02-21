package com.jordanrevata.tecscrum.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.adapters.ProjectAdapter;
import com.jordanrevata.tecscrum.fragments.SprintListFragment;
import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.models.Project;
import com.jordanrevata.tecscrum.models.User;
import com.jordanrevata.tecscrum.repositories.ProjectRepository;
import com.jordanrevata.tecscrum.repositories.SavedRepository;
import com.jordanrevata.tecscrum.repositories.SprintRepository;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;
import com.jordanrevata.tecscrum.services.DailyJobService;
import com.jordanrevata.tecscrum.services.NotificationHelper;
import com.jordanrevata.tecscrum.utilities.Function;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerview_projects;
    public List<Project> projectList;
    private TextView textview_verify_list_projects;
    TextView emailText;
    TextView fullnameText;
    CircleImageView photoImage;

    private FirebaseJobDispatcher dispatcher;

    @Override
    protected void onStart() {
        super.onStart();

        if(!isNetworkAvailable()){

            Toast.makeText(MainActivity.this,getString(R.string.connect_network), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        User user = UserRepository.getUser();
        if(user.getImage()!=null) {
            String url = ApiService.API_BASE_URL + "/images/" + user.getImage();
            Picasso.with(this).load(url).into(photoImage);
        }
        fullnameText.setText(user.getFullname());
        emailText.setText(user.getEmail());

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        projectList = new ArrayList<>();

        textview_verify_list_projects = findViewById(R.id.textview_verify_list_projects);
        textview_verify_list_projects.setVisibility(View.INVISIBLE);

        if(!isNetworkAvailable()){

            Toast.makeText(MainActivity.this,getString(R.string.connect_network), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        boolean verifyExistUser = UserRepository.verifyLogeo();

        if(verifyExistUser){
            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivity(intentLogin);
            finish();
        }else {

            Toast.makeText(MainActivity.this,"Bienvenido(a) " + UserRepository.getUser().getGivenName(), Toast.LENGTH_LONG).show();

            Toolbar toolbar = findViewById(R.id.toolbar_projects);
            setSupportActionBar(toolbar);

            drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_projects);


            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, android.R.string.ok, android.R.string.cancel);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            // Change navigation header information
            photoImage = navigationView.getHeaderView(0).findViewById(R.id.menu_photo);
            User user = UserRepository.getUser();
            if(user.getImage()!=null) {
                String url = ApiService.API_BASE_URL + "/images/" + user.getImage();
                Picasso.with(this).load(url).into(photoImage);
            }

            fullnameText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_fullname);
            fullnameText.setText(user.getFullname());

            emailText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_email);
            emailText.setText(user.getEmail());


            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    // Do action by menu item id
                    switch (menuItem.getItemId()) {
                        case R.id.nav_projects:
                            Intent intentProject = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intentProject);
                            finish();
                            break;
                        case R.id.nav_profile:
                            Toast.makeText(MainActivity.this, "Go Profile...", Toast.LENGTH_SHORT).show();
                            Intent intentProfile = new Intent(MainActivity.this, UserDetailActivity.class);

                            intentProfile.putExtra("code", "Edit");
                            startActivity(intentProfile);
                            break;

                        case R.id.nav_about:

                            NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());

                            notificationHelper.createNotification("Hola", "Como estás", new MoodTodayActivity(), 2);


                            break;
                        case R.id.nav_logout:

                            logOut();
                            break;
                    }

                    // Close drawer
                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true;
                }
            });


            initialize();




            dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

            startJob();



        }


    }

    private void startJob(){

        Job myJob = dispatcher.newJobBuilder()
                .setService(DailyJobService.class)
                .setTag(DailyJobService.TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(30, 60))
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setReplaceCurrent(false)
                .build();

        dispatcher.mustSchedule(myJob);




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



    public static String token;

    public void initialize(){


        recyclerview_projects = findViewById(R.id.recyclerview_projects);
        recyclerview_projects.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_projects.setAdapter(new ProjectAdapter(this));


        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Project>> projects = api.getProjectsByUser(UserRepository.getUser().getIdusers());

        projects.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                try {

                    int statusCode = response.code();
                    Log.e(TAG, "HTTP status code: " + statusCode );

                    if (response.isSuccessful()) {


                        projectList = response.body();

                        if(projectList.isEmpty()){
                            textview_verify_list_projects.setVisibility(View.VISIBLE);
                        }

                        ProjectAdapter projectAdapter = (ProjectAdapter) recyclerview_projects.getAdapter();
                        projectAdapter.setProjects(projectList);
                        projectAdapter.notifyDataSetChanged();

                        if(ProjectRepository.verifyProjects()){
                            ProjectRepository.saveProjects(projectList);
                        }

                        Log.e(TAG, projectList.toString());


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });




    }


    public void logOut(){

        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Log Out")
                .setMessage("¿Está seguro de cerrar sesión?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ApiService api = ApiServiceGenerator.createService(ApiService.class);

                        Call<Void> call = api.logout();

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                try {

                                    int statusCode = response.code();
                                    Log.d(TAG, "HTTP status code: " + statusCode);

                                    if (response.isSuccessful()) {

                                        Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                                        UserRepository.logout();
                                        SavedRepository.deleteSave();
                                        ProjectRepository.deleteProjects();
                                        SprintRepository.deleteSprints();
                                        dispatcher.cancelAll();
                                        finish();
                                        startActivity(intentLogin);


                                    } else {
                                        Log.e(TAG, "onError: " + response.errorBody().string());
                                        throw new Exception("Error en el servicio");
                                    }

                                } catch (Throwable t) {
                                    try {
                                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                    } catch (Throwable x) {
                                    }
                                }


                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                                Log.e(TAG, "onFailure: " + t.toString());
                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();


                            }
                        });


                    }

                })
                .setNegativeButton("No", null)
                .show();

    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
