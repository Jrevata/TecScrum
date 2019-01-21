package com.jordanrevata.tecscrum.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.models.User;

public class UserDetailActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButtonEdit;
    private TextView textview_profile_fullname;
    private TextView textview_profile_email;
    private TextView textview_profile_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);


        User user = new User();

        user.setPhone(getIntent().getStringExtra("phone"));
        user.setEmail(getIntent().getStringExtra("email"));
        user.setFullname(getIntent().getStringExtra("fullname"));

        setProfile(user);

        String code = getIntent().getStringExtra("code");


        floatingActionButtonEdit = findViewById(R.id.floating_button_edit);

        if(code.equals("Edit")){
            floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentEdit = new Intent(UserDetailActivity.this, UserEditActivity.class);
                    startActivity(intentEdit);
                }
            });
        }else{
            floatingActionButtonEdit.hide();
        }

    }

    private void setProfile(User user){

        textview_profile_email = findViewById(R.id.textview_profile_email);
        textview_profile_phone = findViewById(R.id.textview_profile_phone);
        textview_profile_fullname = findViewById(R.id.textview_profile_fullname);

        textview_profile_fullname.setText(user.getFullname());
        textview_profile_email.setText(user.getEmail());
        textview_profile_phone.setText(user.getPhone());

    }


}
