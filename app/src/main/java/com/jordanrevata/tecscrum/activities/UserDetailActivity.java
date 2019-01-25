package com.jordanrevata.tecscrum.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.models.User;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButtonEdit;
    private TextView textview_profile_fullname;
    private TextView textview_profile_email;
    private TextView textview_profile_phone;
    private CircleImageView imageview_photo_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);


        String code = getIntent().getStringExtra("code");


        floatingActionButtonEdit = findViewById(R.id.floating_button_edit);

        if(code.equals("Edit")){
            User user = UserRepository.getUser();
            setProfile(user);
            floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentEdit = new Intent(UserDetailActivity.this, UserEditActivity.class);
                    startActivity(intentEdit);
                }
            });
        }else{

            User userMember = new User();
            userMember.setFullname(getIntent().getExtras().getString("fullname"));
            userMember.setEmail(getIntent().getExtras().getString("email"));
            userMember.setPhone(getIntent().getExtras().getString("phone"));
            userMember.setImage(getIntent().getExtras().getString("image"));

            setProfile(userMember);

            floatingActionButtonEdit.hide();
        }

    }

    private void setProfile(User user){

        textview_profile_email = findViewById(R.id.textview_profile_email);
        textview_profile_phone = findViewById(R.id.textview_profile_phone);
        textview_profile_fullname = findViewById(R.id.textview_profile_fullname);
        imageview_photo_profile = findViewById(R.id.imageview_photo_profile);

        String url = ApiService.API_BASE_URL + "/images/" + user.getImage();

        textview_profile_fullname.setText(user.getFullname());
        textview_profile_email.setText(user.getEmail());
        textview_profile_phone.setText(user.getPhone());
        Picasso.with(this).load(url).into(imageview_photo_profile);


    }


}
