package com.example.merzensumagaysay.thesis2.alert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.merzensumagaysay.thesis2.api.APIUtils;
import com.example.merzensumagaysay.thesis2.adapter.AlertAdapter;
import com.example.merzensumagaysay.thesis2.api.InterfaceAPI;
import com.example.merzensumagaysay.thesis2.model.Crud;
import com.example.merzensumagaysay.thesis2.model.Value;
import com.example.merzensumagaysay.thesis2.R;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAlertActivity extends AppCompatActivity {

    InterfaceAPI interfaceAPI;

    private RecyclerView recyclerView;
    private AlertAdapter adapter;

    private CheckBox CBearthquake, CBfire, CBflood, CBbomb, CBshooter;
    private Button buttonOK;
    private ImageView image;

    private List<Crud> crud = new ArrayList<>();
    ArrayList<String> selection = new ArrayList<String>();
    public static List<Crud> alertitems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alert);

        image = (ImageView)findViewById(R.id.imgCalamity);
        CBearthquake = (CheckBox) findViewById(R.id.cbEartquake);
        CBfire = (CheckBox) findViewById(R.id.cbFire);
        CBflood = (CheckBox) findViewById(R.id.cbFlood);
        CBbomb = (CheckBox) findViewById(R.id.cbBomb);
        CBshooter = (CheckBox)findViewById(R.id.cbShooter);


        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        loadDataCrud();

        buttonOK = (Button) findViewById(R.id.btnOk);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewAlertActivity.this, ViewBlueprint.class);
                startActivity(i);
            }
        });
    }
    private void loadDataCrud() {

        interfaceAPI = APIUtils.getInterfaceAPI();

        Call<Value> call = interfaceAPI.sendAlert();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();

                if (response.isSuccessful()) {
                    crud = response.body().getResult();
                    adapter = new AlertAdapter(crud, ViewAlertActivity.this);
                    recyclerView.setAdapter(adapter);

                    if (response.body().getValue().equals("1")) {
                        image.setImageResource(R.drawable.earthquake_img);
                        System.out.println("PAT " + response.body().getValue());
                    } else if (value.equals("2")) {
                        image.setImageResource(R.drawable.active_shooter);
                        System.out.println("PAT2 " + response.body().getValue());
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });
    }
}
