package com.jordanrevata.tecscrum.activities;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.models.User;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButtonEdit;
    private TextView textview_profile_fullname;
    private TextView textview_profile_email;
    private TextView textview_profile_phone;
    private CircleImageView imageview_photo_profile;

    private static final int REGISTER_FORM_REQUEST = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REGISTER_FORM_REQUEST) {
            //Refresh del intent
            setProfile(UserRepository.getUser());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(!isNetworkAvailable()){

            Toast.makeText(UserDetailActivity.this,getString(R.string.connect_network), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);


        if(!isNetworkAvailable()){

            Toast.makeText(UserDetailActivity.this,getString(R.string.connect_network), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        String code = getIntent().getStringExtra("code");


        floatingActionButtonEdit = findViewById(R.id.floating_button_edit);

        if(code.equals("Edit")){
            User user = UserRepository.getUser();
            setProfile(user);
            floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentEdit = new Intent(UserDetailActivity.this, UserEditActivity.class);
                    startActivityForResult(intentEdit, REGISTER_FORM_REQUEST);
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



        textview_profile_fullname.setText(user.getFullname());
        textview_profile_email.setText(user.getEmail());
        textview_profile_phone.setText(user.getPhone());

        if(user.getImage()!=null) {
            String url = ApiService.API_BASE_URL + "/images/" + user.getImage();
            Picasso.with(this).load(url).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(imageview_photo_profile);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
