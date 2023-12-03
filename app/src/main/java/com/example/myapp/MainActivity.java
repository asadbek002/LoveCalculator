package com.example.myapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private EditText in1,in2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        in1 = findViewById(R.id.yourname);
        in2 = findViewById(R.id.partnername);
        Button button = findViewById(R.id.button);
        textView = findViewById(R.id.result);

        button.setOnClickListener(view -> {
            String stringy = in1.getText().toString();
            String stringp = in2.getText().toString();
            fetchData(stringy,stringp);

        });

    }
    private void fetchData(String stringy, String stringp) {

        String url ="https://love-calculator.p.rapidapi.com/getPercentage?fname="+stringy+"&sname="+stringp;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", "f51f9ceac5msh857ad14ffc6346ap1e2015jsn49df0f0d82b1")
                .addHeader("X-RapidAPI-Host", "love-calculator.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MainActivity.this,"Error +",Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
if(response.isSuccessful())
{
    assert response.body() != null;
    String resp = response.body().string();
    MainActivity.this.runOnUiThread(() -> {
        try{
            JSONObject jsonObject = new JSONObject(resp);
            String val1 = jsonObject.getString("percentage");
            String val2 = jsonObject.getString("result");
            textView.setText(val1+"%나왔습니다\n"+val2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    });
}
            }
        });
    }
}