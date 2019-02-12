package com.jordanrevata.tecscrum.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.models.Credential;
import com.jordanrevata.tecscrum.models.Login;
import com.jordanrevata.tecscrum.models.Saved;
import com.jordanrevata.tecscrum.models.User;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private Button buttonLogin;
    private EditText editText_email;
    private EditText editText_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.button_login);
        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String email = editText_email.getText().toString();
                final String password = editText_password.getText().toString();

                ApiService api = ApiServiceGenerator.createService(ApiService.class);

                Credential credential = new Credential(email, password);

                Call<Login> call = api.login(credential);

                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        try {

                            int statusCode = response.code();
                            Log.d(TAG, "HTTP status code: " + statusCode );

                            if (response.isSuccessful()) {

                                Login usuario = response.body();
                                usuario.getUser().setFullname(usuario.getUser().getGivenName() + " " + usuario.getUser().getFamilyName());
                                usuario.getUser().setToken("Bearer "+usuario.getToken());

                                User user = usuario.getUser();




                                Log.e(TAG, user.toString());


                                UserRepository.create(user);

                                createPersistentData();

                                Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intentMain);
                                finish();

                            } else {
                                Log.e(TAG, "onError: " + response.errorBody().string());
                                throw new Exception("Error en el servicio");
                            }

                        } catch (Throwable t) {
                            try {
                                Log.e(TAG, "onThrowable: " + t.toString(), t);
                                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }catch (Throwable x){}
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {

                        Log.e(TAG, "onFailure: " + t.toString());
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();


                    }
                });



            }
        });

    }

    private void createPersistentData(){

        SharedPreferences.Editor prefContDaily = getSharedPreferences("CONT_DAILY", MODE_PRIVATE).edit();
        prefContDaily.putInt("contador_daily", 0);
        prefContDaily.apply();

        SharedPreferences.Editor prefContMood = getSharedPreferences("CONT_MOOD", MODE_PRIVATE).edit();
        prefContMood.putInt("contador_mood", 0);
        prefContMood.apply();

        SharedPreferences.Editor prefContProject = getSharedPreferences("CONT_UPDATE_PROJECT", MODE_PRIVATE).edit();
        prefContProject.putInt("contador_project", 0);
        prefContProject.apply();

        SharedPreferences.Editor prefContSprint = getSharedPreferences("CONT_UPDATE_SPRINT", MODE_PRIVATE).edit();
        prefContSprint.putInt("contador_sprint", 0);
        prefContSprint.apply();




    }

}
