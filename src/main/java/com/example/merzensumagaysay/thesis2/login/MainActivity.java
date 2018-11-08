package com.example.merzensumagaysay.thesis2.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.merzensumagaysay.thesis2.alert.AlertActivity;
import com.example.merzensumagaysay.thesis2.api.APIUtils;
import com.example.merzensumagaysay.thesis2.api.InterfaceAPI;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginFormActivityListener,
        WelcomeFragment.OnLogoutListener {

    private DrawerLayout drawerLayout;

    public static PrefConfig prefConfig;
    public static InterfaceAPI interfaceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.alert:
                        Intent view = new Intent(MainActivity.this, AlertActivity.class);
                        startActivity(view);
                }
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open2, R.string.Close2);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        prefConfig = new PrefConfig(this);
        interfaceAPI = APIUtils.getInterfaceAPI();

        if (findViewById(R.id.fragment_container) != null)
        {
            if (savedInstanceState != null)
            {
                return;
            }

            if (prefConfig.readLoginStatus())
            {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new WelcomeFragment()).commit();
            }
            else

            {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new LoginFragment()).commit();
            }
        }
    }

    @Override
    public void performLogin(String name) {
        prefConfig.writeName(name);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WelcomeFragment()).commit();

    }

    @Override
    public void logoutPerformed() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName("User");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

}
