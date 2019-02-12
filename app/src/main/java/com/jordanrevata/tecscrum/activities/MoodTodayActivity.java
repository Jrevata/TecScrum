package com.jordanrevata.tecscrum.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.models.MoodToday;
import com.jordanrevata.tecscrum.models.ResponseMessage;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoodTodayActivity extends AppCompatActivity {

    private static final String TAG = MoodTodayActivity.class.getSimpleName();

    private String nameMood;
    private String dateMood;
    private Integer iduser;
    private Integer idsprint;
    private Integer idmood;

    private Integer idemotion;
    private Integer iddedicated;

    private Button button_sendmoodtoday;
    private EditText editText_difficultiestoday;

    private TextView textview_moodname;
    private TextView textview_mooddate;

    private RadioButton radio_angry;
    private RadioButton radio_bad;
    private RadioButton radio_neutral;
    private RadioButton radio_happy;
    private RadioButton radio_excellent;

    private RadioButton radio_dedicated_0;
    private RadioButton radio_dedicated_40;
    private RadioButton radio_dedicated_40_70;
    private RadioButton radio_dedicated_70;
    private RadioButton radio_dedicated_100;


    @Override
    protected void onStart() {
        super.onStart();

        if(!isNetworkAvailable()){

            Toast.makeText(MoodTodayActivity.this,getString(R.string.connect_network), Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_today);

        if(!isNetworkAvailable()){

            Toast.makeText(MoodTodayActivity.this,getString(R.string.connect_network), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        idemotion = 0;
        iddedicated = 0;

        button_sendmoodtoday        = findViewById(R.id.button_sendmoodtoday);
        editText_difficultiestoday  = findViewById(R.id.editText_difficultiestoday);
        textview_moodname           = findViewById(R.id.textview_moodname);
        textview_mooddate           = findViewById(R.id.textview_mooddate);

        initializeRadios();

        String action = getIntent().getExtras().getString("action");
        dateMood = getIntent().getExtras().getString("date");
        nameMood = getIntent().getExtras().getString("name");


        if(nameMood!=null) {
            textview_moodname.setText(nameMood);
        }else{
            textview_moodname.setText(R.string.mood_today);
        }
        textview_mooddate.setText(dateMood);

        if(action.equals("Edit")){



            iduser = getIntent().getExtras().getInt("iduser");
            idsprint = getIntent().getExtras().getInt("idsprint");
            textview_mooddate.setText(dateMood);
            button_sendmoodtoday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMoodToday();
                }
            });

        }else if(action.equals("NoEdit")){

            idmood = getIntent().getExtras().getInt("idmood");

            showMoodToday();

            button_sendmoodtoday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


        }

    }

    private void sendMoodToday() {

        if(idemotion == 0 || iddedicated == 0 || editText_difficultiestoday.getText().toString().isEmpty()){

            Toast.makeText(MoodTodayActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
            return;

        }

        MoodToday moodToday = new MoodToday();
        moodToday.setUsers_idusers(iduser);
        moodToday.setSprints_idsprints(idsprint);
        moodToday.setMood_idmood(idemotion);
        moodToday.setDedicated_iddedicated(iddedicated);
        moodToday.setDifficulties(editText_difficultiestoday.getText().toString());

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call<ResponseMessage> responseMessageCall = api.createMoodToday(moodToday);

        responseMessageCall.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ResponseMessage responseMessage = response.body();

                        Toast.makeText(MoodTodayActivity.this, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MoodTodayActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MoodTodayActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void showMoodToday() {

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call<MoodToday> moodTodayCall = api.getMoodToday(idmood);

        moodTodayCall.enqueue(new Callback<MoodToday>() {
            @Override
            public void onResponse(Call<MoodToday> call, Response<MoodToday> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        MoodToday moodToday = response.body();

                        checkEmotions(moodToday.getMood_idmood());
                        checkTimeDedicated(moodToday.getDedicated_iddedicated());

                        editText_difficultiestoday.setText(moodToday.getDifficulties());
                        editText_difficultiestoday.setFocusable(false);
                        button_sendmoodtoday.setText(getString(R.string.confirm));

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MoodTodayActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<MoodToday> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MoodTodayActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });

    }

    public void onRadioEmotionClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.radio_angry:
                if(checked){
                    idemotion = 1;
                    radio_bad.setChecked(false);
                    radio_neutral.setChecked(false);
                    radio_happy.setChecked(false);
                    radio_excellent.setChecked(false);
                }
                break;

            case R.id.radio_bad:
                if(checked){
                    idemotion = 2;
                    radio_angry.setChecked(false);
                    radio_neutral.setChecked(false);
                    radio_happy.setChecked(false);
                    radio_excellent.setChecked(false);
                }
                break;

            case R.id.radio_neutral:
                if(checked){
                    idemotion = 3;
                    radio_angry.setChecked(false);
                    radio_bad.setChecked(false);
                    radio_happy.setChecked(false);
                    radio_excellent.setChecked(false);
                }
                break;

            case R.id.radio_happy:
                if(checked){
                    idemotion = 4;
                    radio_angry.setChecked(false);
                    radio_bad.setChecked(false);
                    radio_neutral.setChecked(false);
                    radio_excellent.setChecked(false);
                }
                break;

            case R.id.radio_excellent:
                if(checked){
                    idemotion = 5;
                    radio_angry.setChecked(false);
                    radio_bad.setChecked(false);
                    radio_neutral.setChecked(false);
                    radio_happy.setChecked(false);
                }
                break;
        }

    }

    public void onRadioDedicatedClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.radio_dedicated_0:
                if(checked){
                    iddedicated = 1;
                    radio_dedicated_40.setChecked(false);
                    radio_dedicated_40_70.setChecked(false);
                    radio_dedicated_70.setChecked(false);
                    radio_dedicated_100.setChecked(false);
                }
                break;
            case R.id.radio_dedicated_40:
                if(checked){
                    iddedicated = 2;
                    radio_dedicated_0.setChecked(false);
                    radio_dedicated_40_70.setChecked(false);
                    radio_dedicated_70.setChecked(false);
                    radio_dedicated_100.setChecked(false);
                }
                break;
            case R.id.radio_dedicated_40_70:
                if(checked){
                    iddedicated = 3;
                    radio_dedicated_0.setChecked(false);
                    radio_dedicated_40.setChecked(false);
                    radio_dedicated_70.setChecked(false);
                    radio_dedicated_100.setChecked(false);
                }
                break;
            case R.id.radio_dedicated_70:
                if(checked){
                    iddedicated = 4;
                    radio_dedicated_0.setChecked(false);
                    radio_dedicated_40.setChecked(false);
                    radio_dedicated_40_70.setChecked(false);
                    radio_dedicated_100.setChecked(false);
                }
                break;
            case R.id.radio_dedicated_100:
                if(checked){
                    iddedicated = 5;
                    radio_dedicated_0.setChecked(false);
                    radio_dedicated_40.setChecked(false);
                    radio_dedicated_40_70.setChecked(false);
                    radio_dedicated_70.setChecked(false);
                }
                break;

        }

    }

    private void checkEmotions(Integer id){

        switch (id){

            case 1:
                radio_angry.setChecked(true);
                break;

            case 2:
                radio_bad.setChecked(true);
                break;

            case 3:
                radio_neutral.setChecked(true);
                break;

            case 4:
                radio_happy.setChecked(true);
                break;

            case 5:
                radio_excellent.setChecked(true);
                break;
        }

        radio_angry.setClickable(false);
        radio_neutral.setClickable(false);
        radio_bad.setClickable(false);
        radio_excellent.setClickable(false);
        radio_happy.setClickable(false);

    }

    private void checkTimeDedicated(Integer id){

        switch (id){

            case 1:
                radio_dedicated_0.setChecked(true);
                break;

            case 2:
                radio_dedicated_40.setChecked(true);
                break;

            case 3:
                radio_dedicated_40_70.setChecked(true);
                break;

            case 4:
                radio_dedicated_70.setChecked(true);
                break;

            case 5:
                radio_dedicated_100.setChecked(true);
                break;
        }

        radio_dedicated_0.setClickable(false);
        radio_dedicated_40.setClickable(false);
        radio_dedicated_40_70.setClickable(false);
        radio_dedicated_70.setClickable(false);
        radio_dedicated_100.setClickable(false);

    }

    private void initializeRadios(){

        radio_angry     = findViewById(R.id.radio_angry);
        radio_bad       = findViewById(R.id.radio_bad);
        radio_neutral   = findViewById(R.id.radio_neutral);
        radio_happy     = findViewById(R.id.radio_happy);
        radio_excellent = findViewById(R.id.radio_excellent);


        radio_dedicated_0 = findViewById(R.id.radio_dedicated_0);
        radio_dedicated_40 = findViewById(R.id.radio_dedicated_40);
        radio_dedicated_40_70 = findViewById(R.id.radio_dedicated_40_70);
        radio_dedicated_70 = findViewById(R.id.radio_dedicated_70);
        radio_dedicated_100 = findViewById(R.id.radio_dedicated_100);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
