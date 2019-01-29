package com.jordanrevata.tecscrum.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jordanrevata.tecscrum.R;
import com.jordanrevata.tecscrum.activities.MainActivity;
import com.jordanrevata.tecscrum.adapters.ForumAdapter;
import com.jordanrevata.tecscrum.models.Forum;
import com.jordanrevata.tecscrum.models.ResponseMessage;
import com.jordanrevata.tecscrum.repositories.UserRepository;
import com.jordanrevata.tecscrum.services.ApiService;
import com.jordanrevata.tecscrum.services.ApiServiceGenerator;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForumFragment extends Fragment {


    private final static String TAG = ForumFragment.class.getSimpleName();

    RecyclerView recyclerview_comments;
    Integer idsprint;
    Button button_sendcomment;
    EditText editText_comment;

    public ForumFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        idsprint = getArguments().getInt("idsprint");

        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        editText_comment = view.findViewById(R.id.editText_comment);
        button_sendcomment = view.findViewById(R.id.button_sendcomment);
        recyclerview_comments = view.findViewById(R.id.recyclerview_comments);
        recyclerview_comments.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_comments.setAdapter(new ForumAdapter(this));

        button_sendcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(editText_comment.getText().toString().isEmpty()){

                    Toast.makeText(ForumFragment.this.getContext(), "Complete el cuadro de texto", Toast.LENGTH_LONG).show();

                }else {
                    //Toast.makeText(ForumFragment.this.getContext(), message, Toast.LENGTH_LONG).show();
                    sendMessage();

                }
            }
        });

        initialize();



        return view;

    }


    public void initialize(){

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Forum>> comments = api.getCommentsBySprint(idsprint);

        comments.enqueue(new Callback<List<Forum>>() {
            @Override
            public void onResponse(Call<List<Forum>> call, Response<List<Forum>> response) {

                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode );

                    if (response.isSuccessful()) {


                        List<Forum> comments = response.body();
                        Collections.reverse(comments);

                        ForumAdapter forumAdapter = (ForumAdapter) recyclerview_comments.getAdapter();
                        forumAdapter.setForum(comments);
                        forumAdapter.notifyDataSetChanged();




                        Log.d(TAG, comments.toString());


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(ForumFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }



            }

            @Override
            public void onFailure(Call<List<Forum>> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(ForumFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    public void sendMessage(){

        String message = editText_comment.getText().toString();

        ApiService api = ApiServiceGenerator.createService(ApiService.class);

        Forum forum = new Forum();
        forum.setSprints_idsprints(idsprint);
        forum.setMessage(message);
        forum.setUsers_idusers(UserRepository.getUser().getIdusers());

        Call<ResponseMessage> responseMessageCall = api.createComment(forum);

        responseMessageCall.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode );

                    if (response.isSuccessful()) {

                        ResponseMessage responseMessage = response.body();

                        Toast.makeText(ForumFragment.this.getContext(), responseMessage.getMessage(), Toast.LENGTH_LONG).show();

                        Log.d(TAG, responseMessage.toString());

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(ForumFragment.this).attach(ForumFragment.this).commit();

                        editText_comment.setText("");
                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(ForumFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }


            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(ForumFragment.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

}
