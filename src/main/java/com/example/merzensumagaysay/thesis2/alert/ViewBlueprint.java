package com.example.merzensumagaysay.thesis2.alert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merzensumagaysay.thesis2.api.APIUtils;
import com.example.merzensumagaysay.thesis2.api.InterfaceAPI;
import com.example.merzensumagaysay.thesis2.model.SafeExits;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.merzensumagaysay.thesis2.R;


public class ViewBlueprint extends AppCompatActivity {

    InterfaceAPI interfaceAPI;

    CheckBox MFCExit2, backgateExit2, mainExit2, mainGateExit2, LRTExit2;

    TextView ins1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blueprint);

        MFCExit2 = (CheckBox)findViewById(R.id.MFCExit2);
        backgateExit2 = (CheckBox)findViewById(R.id.backgateExit2);
        mainExit2 = (CheckBox)findViewById(R.id.mainExit2);
        mainGateExit2 = (CheckBox)findViewById(R.id.mainGateExit2);
        LRTExit2 = (CheckBox)findViewById(R.id.LRTExit2);
        viewSafeExit();

        ins1 = (TextView)findViewById(R.id.ins1);
        fetchData();

    }

    private void fetchData() {
        interfaceAPI = APIUtils.getInterfaceAPI();

        Call<List<SafeExits>> call = interfaceAPI.getMessage();
        call.enqueue(new Callback<List<SafeExits>>() {
            @Override
            public void onResponse(Call<List<SafeExits>> call, Response<List<SafeExits>> response) {
                List<SafeExits> adslist = response.body();

                String instruction = adslist.get(0).getInstruction();
                ins1.setText(instruction);

            }

            @Override
            public void onFailure(Call<List<SafeExits>> call, Throwable t) {

                Toast.makeText(ViewBlueprint.this, ""+t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void viewSafeExit(){
        interfaceAPI = APIUtils.getInterfaceAPI();

        Call<List<SafeExits>> call = interfaceAPI.getExit();
        call.enqueue(new Callback<List<SafeExits>>() {
            @Override
            public void onResponse(Call<List<SafeExits>> call, Response<List<SafeExits>> response) {
                List<SafeExits> sl = response.body();

                for (SafeExits value : sl)
                {
                    if(value.getExitID() == 1)
                    {
                        if(value.getiStatus() == 1)

                        {
                            MFCExit2.setChecked(true);
                        }
                        else
                        {
                            MFCExit2.setChecked(false);
                        }
                    }

                    else if(value.getExitID() == 2)
                    {
                        if(value.getiStatus() == 1)
                        {
                            backgateExit2.setChecked(true);
                        }
                        else
                        {
                            backgateExit2.setChecked(false);
                        }
                    }

                    else if(value.getExitID() == 3)
                    {
                        if(value.getiStatus() == 1)
                        {
                            mainExit2.setChecked(true);
                        }
                        else
                        {
                            mainExit2.setChecked(false);
                        }
                    }

                    else if(value.getExitID() == 4)
                    {
                        if(value.getiStatus() == 1)
                        {
                            mainGateExit2.setChecked(true);
                        }
                        else
                        {
                            mainGateExit2.setChecked(false);
                        }
                    }

                    else if(value.getExitID() == 5)
                    {
                        if(value.getiStatus() == 1)
                        {
                            LRTExit2.setChecked(true);
                        }
                        else
                        {
                            LRTExit2.setChecked(false);
                        }
                    }

                    Log.d("responsebody ",String.valueOf(value.getiStatus()));
                }
            }

            @Override
            public void onFailure(Call<List<SafeExits>> call, Throwable t) {
                Log.d("responsebody", "onFailure: " + t.getMessage());
            }
        });
    }
}
