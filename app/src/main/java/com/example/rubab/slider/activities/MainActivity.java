package com.example.rubab.slider.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rubab.slider.fragments.CartFragment;
import com.example.rubab.slider.fragments.HomeFragment;
import com.example.rubab.slider.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ImageView close;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupHomeFragment(new HomeFragment());
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        close = findViewById(R.id.close);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setElevation(4.0F);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_menu_arrow);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            setupHomeFragment(new CartFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.my_home: {
                setupHomeFragment(new HomeFragment());
                break;
            }
            case R.id.my_cart: {
                setupHomeFragment(new CartFragment());
                break;
            }
            case R.id.settings: {
                setupHomeFragment(new HomeFragment());
                break;
            }
            case R.id.logout: {
                setupHomeFragment(new HomeFragment());
                break;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupHomeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.content_main);

        if(fm.getBackStackEntryCount() > 1){
            fm.popBackStack();
        } else{
            finish();
        }
    }
}