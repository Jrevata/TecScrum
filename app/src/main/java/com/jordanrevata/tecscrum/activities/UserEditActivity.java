package com.jordanrevata.tecscrum.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jordanrevata.tecscrum.R;



import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.models.ResponseMessage;
import com.jordanrevata.tecscrum.models.User;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserEditActivity extends AppCompatActivity {

    private static final  String TAG = UserEditActivity.class.getSimpleName();

    public static final int REQUEST_CODE_CAMERA     = 0012;
    public static final int REQUEST_CODE_GALLERY    = 0013;

    private CircleImageView profile_image;
    private EditText editText_name_edit;
    private  EditText editText_lastname_edit;
    private EditText editText_phone_edit;
    private FloatingActionButton floating_button_save;

    private Button button_openimage;

    private String[] items = {"Camera" , "Gallery"};

    File mediaFile;

    private static final int PERMISSIONS_REQUEST = 100;

    private static String[] PERMISSIONS_LIST = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };



    @Override
    protected void onStart() {
        super.onStart();

        if(!isNetworkAvailable()){

            Toast.makeText(UserEditActivity.this,getString(R.string.connect_network), Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        if(!isNetworkAvailable()){

            Toast.makeText(UserEditActivity.this,getString(R.string.connect_network), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        mediaFile = null;
        User user = UserRepository.getUser();

        profile_image = findViewById(R.id.profile_image_edit);
        button_openimage = findViewById(R.id.button_openimage);
        editText_name_edit = findViewById(R.id.editText_name_edit);
        editText_lastname_edit = findViewById(R.id.editText_lastname_edit);
        editText_phone_edit = findViewById(R.id.editText_phone_edit);
        floating_button_save = findViewById(R.id.floating_button_save);

        String url = ApiService.API_BASE_URL + "/images/" + user.getImage();


        editText_name_edit.setText(user.getGivenName());
        editText_lastname_edit.setText(user.getFamilyName());
        editText_phone_edit.setText(user.getPhone());
        if(user.getImage() != null){

            Picasso.with(this).load(url).into(profile_image);

        }


        button_openimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        floating_button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        checkAllPermissions();
    }

    private void save() {

        final String givenName = editText_name_edit.getText().toString();
        final String familyName = editText_lastname_edit.getText().toString();
        final String phone = editText_phone_edit.getText().toString();

        if(givenName.isEmpty() || familyName.isEmpty() || phone.isEmpty()){

            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;

        }

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call<ResponseMessage> call = null;

        if(mediaFile == null){

            call = api.updateUserWithoutImage(UserRepository.getUser().getIdusers(),givenName,familyName,phone);

        }else{

            File file = mediaFile;

            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());

            bitmap = scaleBitmapDown(bitmap, 290);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            RequestBody givenNamePart = RequestBody.create(MultipartBody.FORM, givenName);
            RequestBody familyNamePart = RequestBody.create(MultipartBody.FORM, familyName);
            RequestBody phonePart = RequestBody.create(MultipartBody.FORM, phone);

            call = api.updateUserWithImage(UserRepository.getUser().getIdusers(), givenNamePart, familyNamePart, phonePart, imagePart);


        }

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                try{
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ResponseMessage responseMessage = response.body();

                        User user = UserRepository.getUser();
                        user.setGivenName(givenName);
                        user.setPhone(phone);
                        user.setFamilyName(familyName);
                        user.setImage(responseMessage.getMessage());
                        user.setFullname(givenName+" "+familyName);


                        Log.d(TAG, "responseMessage: " + responseMessage);

                        UserRepository.updateUser(user);
                        Log.d(TAG, "User update: " + UserRepository.getUser());

                        Toast.makeText(UserEditActivity.this, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(UserEditActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) { }
                }

            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(UserEditActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    private void checkAllPermissions() {
        boolean permissionRequired = false;
        for (String permission : PERMISSIONS_LIST){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                permissionRequired = true;
        }
        if(permissionRequired){
            ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, PERMISSIONS_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSIONS_REQUEST: {
                for (int i=0; i<grantResults.length; i++){
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, PERMISSIONS_LIST[i] + " permissions declined!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                finishAffinity();
                            }
                        }, Toast.LENGTH_LONG);
                    }
                }
            }
        }
    }


    private void openImage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(items[i].equals("Camera")){
                    EasyImage.openCamera(UserEditActivity.this,REQUEST_CODE_CAMERA);
                }else if(items[i].equals("Gallery")){
                    EasyImage.openGallery(UserEditActivity.this,REQUEST_CODE_GALLERY);
                }
            }
        });

        AlertDialog dialog= builder.create();
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles,type);
            }



            private void onPhotosReturned(List<File> imageFile,int type) {
                switch (type){
                    case REQUEST_CODE_CAMERA:
                        Picasso.with(UserEditActivity.this).load(imageFile.get(0)).into(profile_image);
                        mediaFile = imageFile.get(0);

                        break;
                    case REQUEST_CODE_GALLERY:
                        Picasso.with(UserEditActivity.this).load(imageFile.get(0)).into(profile_image);
                        mediaFile = imageFile.get(0);

                        break;
                }

            }


            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(UserEditActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });


    }


    // Redimensionar una imagen bitmap
    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
