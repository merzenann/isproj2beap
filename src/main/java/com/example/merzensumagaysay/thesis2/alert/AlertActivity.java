package com.example.merzensumagaysay.thesis2.alert;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.merzensumagaysay.thesis2.R;


import com.example.merzensumagaysay.thesis2.api.APIUtils;
import com.example.merzensumagaysay.thesis2.api.InterfaceAPI;
import com.example.merzensumagaysay.thesis2.adapter.AlertAdapter;
import com.example.merzensumagaysay.thesis2.model.Value;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertActivity extends AppCompatActivity {

    InterfaceAPI interfaceAPI;

    private AlertAdapter viewAlertAdapter;

    private CheckBox CBearthquake, CBfire, CBflood, CBbomb, CBshooter;
    private Button buttonAlert;
    private int userID;

    ArrayList<String> selection = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AlertActivity.this);
        userID = prefs.getInt("ID", 0);

        CBearthquake = (CheckBox) findViewById(R.id.cbEartquake);
        CBfire = (CheckBox) findViewById(R.id.cbFire);
        CBflood = (CheckBox) findViewById(R.id.cbFlood);
        CBbomb = (CheckBox) findViewById(R.id.cbBomb);
        CBshooter = (CheckBox)findViewById(R.id.cbShooter);
        buttonAlert = (Button) findViewById(R.id.btnAlert);
        buttonAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AlertActivity.this);
                alertDialogBuilder.setTitle("Warning");
                alertDialogBuilder
                        .setMessage("Do you wish to continue?")
                        .setCancelable(false)
                        .setPositiveButton("Alert", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                for(int i=0; i<selection.size(); i++) {
                                    interfaceAPI = APIUtils.getInterfaceAPI();


                                    Call<Value> call = interfaceAPI.alertUsers(Integer.parseInt(selection.get(i)), userID);
                                    call.enqueue(new Callback<Value>() {
                                        @Override
                                        public void onResponse(Call<Value> call, Response<Value> response) {


                                            if (response.isSuccessful()){
                                                Toast.makeText(AlertActivity.this, "Report sent.", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(AlertActivity.this, "Report failed to send.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Value> call, Throwable t) {
                                            t.printStackTrace();

                                            System.out.println("NADINE " +  t.getMessage());
                                            Toast.makeText(AlertActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                Intent i = new Intent(AlertActivity.this, AlertBlueprint.class);
                                startActivity(i);


                                CBearthquake.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(CBearthquake.isChecked())
                                        {
                                            selection.add("1");
                                        }else{

                                            selection.remove("1");
                                        }
                                    }
                                });

                                CBfire.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(CBfire.isChecked())
                                        {
                                            selection.add("2");
                                        }else{

                                            selection.remove("2");
                                        }
                                    }
                                });
                                CBflood.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(CBflood.isChecked())
                                        {
                                            selection.add("3");
                                        }else{

                                            selection.remove("3");
                                        }
                                    }
                                });
                                CBbomb.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(CBbomb.isChecked())
                                        {
                                            selection.add("4");
                                        }else{

                                            selection.remove("4");
                                        }
                                    }
                                });
                                CBshooter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(CBshooter.isChecked())
                                        {
                                            selection.add("5");
                                        }else{

                                            selection.remove("5");
                                        }
                                    }
                                });

                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }
}
