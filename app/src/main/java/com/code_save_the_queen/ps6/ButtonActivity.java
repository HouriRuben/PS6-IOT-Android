package com.code_save_the_queen.ps6;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        long id = getIntent().getLongExtra("ID",-1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sylvainlangler.alwaysdata.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);

        final Post post = new Post(id);

        Button Valider = (Button) findViewById(R.id.buttonValider);
        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost(post);
            }
        }); // calling onClick() method
        Button Refuser = (Button) findViewById(R.id.buttonRefuser);
        Refuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button Refuser", Toast.LENGTH_SHORT).show();
            }
        });
        Button Retard = (Button) findViewById(R.id.buttonRetard);
        Retard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button Retard", Toast.LENGTH_SHORT).show();
            }
        });
        Button Incomplet = (Button) findViewById(R.id.buttonIncomplet);
        Incomplet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button Incomplet", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void createPost(Post post) {
        Call<Integer> call = jsonApi.createPost(post);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                int postResponse = response.body();

                String content = "";
                content += "Text: " + postResponse + "\n\n";

                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
