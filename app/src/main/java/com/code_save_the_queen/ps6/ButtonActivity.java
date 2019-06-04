package com.code_save_the_queen.ps6;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ButtonActivity extends AppCompatActivity {
    private JsonApi jsonApi;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sylvainlangler.alwaysdata.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);


    }


    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.buttonValider:
                createPost();
                break;
            case R.id.buttonIncomplet:
                Toast.makeText(this, "Button Incomplet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonRefuser:
                Toast.makeText(this, "Button Refuser", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonRetard:
                Toast.makeText(this, "Button Retard", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void createPost() {
        Post post = new Post();

        Call<Post> call = jsonApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Text: " + postResponse.getPost() + "\n\n";

                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
