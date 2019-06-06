package com.code_save_the_queen.ps6;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
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
import android.os.Vibrator;




public class ButtonActivity extends AppCompatActivity {
    private JsonApi jsonApi;
    Context context = this;
    String orchestratorIp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        long id = getIntent().getLongExtra("ID",-1);
        orchestratorIp = getIntent().getStringExtra("IP");
        orchestratorIp = orchestratorIp.split(":")[0];
        Toast.makeText(context, orchestratorIp, Toast.LENGTH_SHORT).show();
        //https://sylvainlangler.alwaysdata.net/api/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:/"+orchestratorIp+":1880")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);

        final Post validate = new Post(id, false,false);
        final Post invalidate = new Post(id, true,false);
        final Post delay = new Post(id, false,true);


        Button Valider = (Button) findViewById(R.id.buttonValider);
        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPost(validate);
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vib.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vib.vibrate(100);
                }
            }
        }); // calling onClick() method
        Button Refuser = (Button) findViewById(R.id.buttonRefuser);
        Refuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPost(invalidate);
                // Vibrate for 500 milliseconds
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vib.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vib.vibrate(100);
                }
            }
        });
        Button Retard = (Button) findViewById(R.id.buttonRetard);
        Retard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost(delay);
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vib.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vib.vibrate(100);
                }
            }
        });
    }


    private void createPost(Post post) {
        Call<String> call = jsonApi.createPost(post);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                String postResponse = response.body();

                String content = "";
                content += "Text: " + postResponse + "\n\n";

                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletePost(Delete delete) {
        Call<String> call = jsonApi.deletepost(delete);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                String postResponse = response.body();

                String content = "";
                content += "Text: " + postResponse + "\n\n";

                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void delayPost(Delete delete) {
        Call<String> call = jsonApi.delaypost(delete);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                String postResponse = response.body();

                String content = "";
                content += "Text: " + postResponse + "\n\n";

                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
