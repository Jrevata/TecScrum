package com.jordanrevata.tecscrum.activities;

import android.app.FragmentTransaction;
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
import com.jordanrevata.tecscrum.fragments.DailyListFragment;
import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.models.ResponseMessage;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyActivity extends AppCompatActivity {

    private static final String TAG = DailyActivity.class.getSimpleName();

    private Integer iddaily;
    private Integer idsprint;
    private Integer iduser;
    String dailydate;


    private TextView textview_dailyname;
    private TextView textview_dailydate;
    private EditText editText_didyesterday;
    private EditText editText_dowilltoday;
    private Button button_senddaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        textview_dailyname      = findViewById(R.id.textview_dailyname);
        textview_dailydate      = findViewById(R.id.textview_dailydate);
        editText_didyesterday   = findViewById(R.id.editText_didyesterday);
        editText_dowilltoday    = findViewById(R.id.editText_dowilltoday);
        button_senddaily        = findViewById(R.id.button_senddaily);



        String action = getIntent().getExtras().getString("action");
        String dailyname = getIntent().getExtras().getString("name");

        dailydate = getIntent().getExtras().getString("date");

        textview_dailyname.setText(dailyname);
        textview_dailydate.setText(dailydate);

        if(action.equals("Edit")){

            idsprint = getIntent().getExtras().getInt("idsprint");
            iduser   = getIntent().getExtras().getInt("iduser");

            button_senddaily.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(editText_didyesterday.getText().toString().isEmpty() || editText_dowilltoday.getText().toString().isEmpty()){

                        Toast.makeText(DailyActivity.this, "Complete los campos", Toast.LENGTH_LONG).show();

                    }else {

                        sendDaily();

                    }
                }
            });


        }else if(action.equals("NoEdit")){

            iddaily = getIntent().getExtras().getInt("iddaily");

            showDaily();

            button_senddaily.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }

    }

    private void sendDaily() {

        String willDo = editText_dowilltoday.getText().toString();
        String did    = editText_didyesterday.getText().toString();

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Daily daily = new Daily();
        daily.setSprints_idsprints(idsprint);
        daily.setUsers_idusers(iduser);
        daily.setDate_daily(dailydate);
        daily.setWhat_did(did);
        daily.setWhat_willdo(willDo);


        Call<ResponseMessage> responseMessageCall = api.createDaily(daily);

        responseMessageCall.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ResponseMessage responseMessage = response.body();

                        Toast.makeText(DailyActivity.this, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DailyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DailyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });



    }

    private void showDaily(){

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call<Daily> daily = api.getDaily(iddaily);

        daily.enqueue(new Callback<Daily>() {
            @Override
            public void onResponse(Call<Daily> call, Response<Daily> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Daily daily = response.body();

                        editText_didyesterday.setText(daily.getWhat_did());
                        editText_dowilltoday.setText(daily.getWhat_willdo());
                        button_senddaily.setText(getString(R.string.confirm));

                        editText_dowilltoday.setFocusable(false);
                        editText_didyesterday.setFocusable(false);


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DailyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Daily> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DailyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });


    }



}
