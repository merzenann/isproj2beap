package com.example.merzensumagaysay.thesis2.alert;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.merzensumagaysay.thesis2.api.APIUtils;
import com.example.merzensumagaysay.thesis2.api.InterfaceAPI;
import com.example.merzensumagaysay.thesis2.model.SafeExits;
import com.example.merzensumagaysay.thesis2.notif.Notification;
import com.example.merzensumagaysay.thesis2.notif.NotificationReceiver;

import com.example.merzensumagaysay.thesis2.R;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertBlueprint extends AppCompatActivity {

    InterfaceAPI interfaceAPI;

    private NotificationManagerCompat notificationManager;
    private static final String TAG = "AdminBlueprint";
    String check;
    private int userID;
    CheckBox MFCExit, backgateExit, mainExit, mainGateExit, LRTExit;

    EditText instruction;


    Button btnSendToUsers, btnUncheck;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_blueprint);

       /* SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AnotherAlertActivity.this);
        userID = prefs.getInt("ID", 0);
        */

        notificationManager = NotificationManagerCompat.from(this);
        setTitle("Safe Exits - Blueprint");

        MFCExit = (CheckBox) findViewById(R.id.MFCExit);
        backgateExit = (CheckBox) findViewById(R.id.backgateExit);
        mainExit = (CheckBox) findViewById(R.id.mainExit);
        mainGateExit = (CheckBox) findViewById(R.id.mainGateExit);
        LRTExit = (CheckBox) findViewById(R.id.LRTExit);

        instruction = (EditText)findViewById(R.id.instruc1);


        btnUncheck = (Button) findViewById(R.id.btnUncheck);
        btnUncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUncheck();
            }
        });

        btnSendToUsers = (Button) findViewById(R.id.btnSendToUsers);
        btnSendToUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AlertBlueprint.this);
                alertDialogBuilder.setTitle("Warning");
                alertDialogBuilder
                        .setMessage("Do you wish to continue?")
                        .setCancelable(false)
                        .setPositiveButton("Alert", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendNotif();

                                String ins = instruction.getText().toString();
                                try {
                                    setInstruction(1, ins);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(AlertBlueprint.this, "Alert sent!", Toast.LENGTH_SHORT).show();
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
        instruct();

        getExits();

    }

    public void btnUncheck() {
        MFCExit.setChecked(false);
        backgateExit.setChecked(false);
        mainExit.setChecked(false);
        mainGateExit.setChecked(false);
        LRTExit.setChecked(false);

    }

    public void instruct(){
        interfaceAPI = APIUtils.getInterfaceAPI();

        Call<List<SafeExits>> call = interfaceAPI.getMessage();
        call.enqueue(new Callback<List<SafeExits>>() {
            @Override
            public void onResponse(Call<List<SafeExits>> call, Response<List<SafeExits>> response) {
                final List<SafeExits> ins = response.body();
                for (final  SafeExits value : ins)
                {
                    if(value.getExitID() == 1)
                    {
                        instruction.setText(value.getInstruction());

                    }
                }
            }

            @Override
            public void onFailure(Call<List<SafeExits>> call, Throwable t) {

            }
        });
    }

    public void setInstruction(int exitID, String instruction) throws IOException {

        Call<String> call = interfaceAPI.sendMessage(exitID, instruction);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());

            }
        });

    }

    public void getExits() {

        interfaceAPI = APIUtils.getInterfaceAPI();

        Call<List<SafeExits>> call = interfaceAPI.getExit();
        call.enqueue(new Callback<List<SafeExits>>() {
            @Override
            public void onResponse(Call<List<SafeExits>> call, Response<List<SafeExits>> response) {
                final List<SafeExits> sl = response.body();

                for (final SafeExits value : sl) {
                    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            Log.d(TAG, "onCheckedChanged: " + compoundButton.isChecked());
                            if (compoundButton.isChecked()) {
                                setExits(value.getExitID(), 1);
                            } else {
                                setExits(value.getExitID(), 0);
                            }
                        }
                    };

                    if (value.getExitID() == 1) {
                        if (value.getiStatus() == 1) {
                            MFCExit.setChecked(true);
                        }
                        MFCExit.setOnCheckedChangeListener(checkListener);

                    } else if (value.getExitID() == 2) {
                        if (value.getiStatus() == 1) {
                            backgateExit.setChecked(true);
                        }
                        backgateExit.setOnCheckedChangeListener(checkListener);

                    } else if (value.getExitID() == 3) {
                        if (value.getiStatus() == 1) {
                            mainExit.setChecked(true);
                        }
                        mainExit.setOnCheckedChangeListener(checkListener);

                    } else if (value.getExitID() == 4) {
                        if (value.getiStatus() == 1) {
                            mainGateExit.setChecked(true);
                        }
                        mainGateExit.setOnCheckedChangeListener(checkListener);

                    } else if (value.getExitID() == 5) {
                        if (value.getiStatus() == 1) {
                            LRTExit.setChecked(true);
                        }
                        LRTExit.setOnCheckedChangeListener(checkListener);
                    }

                    Log.d("responsebody ", String.valueOf(value.getiStatus()));
                }
            }

            @Override
            public void onFailure(Call<List<SafeExits>> call, Throwable t) {
                Log.d("responsebody", "onFailure: " + t.getMessage());
            }
        });
    }

    public void setExits(int exitID, int iStatus) {

        interfaceAPI = APIUtils.getInterfaceAPI();

        Call<ResponseBody> call = interfaceAPI.updateExit(exitID, iStatus);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());
                } else {
                    Toast.makeText(AlertBlueprint.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(AlertBlueprint.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void sendNotif() {
        long[] v = {500,1000};

        Intent activityIntent = new Intent(this, ViewAlertActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Intent broadcastIntent = new Intent (this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", "There is a calamity happening");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0,
                broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification notification =  new NotificationCompat.Builder(this, Notification.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_looks_one_black_24dp)
                .setContentTitle("Alert")
                .setContentText("There is a calamity happening!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setVibrate(v)
                .setLights(0xff00ff00, 300, 100)
                .addAction(R.mipmap.ic_launcher, "Toast",  actionIntent)
                .build();

        notificationManager.notify(1, notification);
    }
}