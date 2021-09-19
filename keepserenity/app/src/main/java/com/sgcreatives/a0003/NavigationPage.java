package com.sgcreatives.a0003;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class NavigationPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Emergencyy()).commit();
            navigationView.setCheckedItem(R.id.emer);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.nav_message:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.voice:
                startActivity(new Intent(getApplicationContext(), VoiceRecog.class));
                finish();
                break;
            case R.id.nav_profile:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
            case R.id.emer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Emergencyy()).commit();
                break;


            }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}


