package com.ats.exhibitionvisitor.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.adapter.EventsAdapter;
import com.ats.exhibitionvisitor.adapter.PortfolioAdapter;
import com.ats.exhibitionvisitor.filterview.ChipView;
import com.ats.exhibitionvisitor.flowlayout.FlowLayout;
import com.ats.exhibitionvisitor.fragment.AboutDeveloperFragment;
import com.ats.exhibitionvisitor.fragment.EventInfoFragment;
import com.ats.exhibitionvisitor.fragment.EventSubscribedFragment;
import com.ats.exhibitionvisitor.fragment.ExhibitorsHomeFragment;
import com.ats.exhibitionvisitor.fragment.ExhibitorsListFragment;
import com.ats.exhibitionvisitor.fragment.FloorMapFragment;
import com.ats.exhibitionvisitor.fragment.HomeFragment;
import com.ats.exhibitionvisitor.fragment.LikedExhibitorFragment;
import com.ats.exhibitionvisitor.fragment.LikedProductFragment;
import com.ats.exhibitionvisitor.fragment.PortfolioFragment;
import com.ats.exhibitionvisitor.fragment.ProductsFragment;
import com.ats.exhibitionvisitor.fragment.SettingsFragment;
import com.ats.exhibitionvisitor.model.EventModel;
import com.ats.exhibitionvisitor.model.Visitor;
import com.ats.exhibitionvisitor.util.CustomSharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.ats.exhibitionvisitor.activity.SelectCityActivity.cityMap;
import static com.ats.exhibitionvisitor.fragment.HomeFragment.industryMap;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rvEvents;
    private EventsAdapter adapter;
    private FlowLayout llFilter;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvEvents = findViewById(R.id.rvEvents);
        llFilter = findViewById(R.id.llFilter);

        String jsonVisitor = CustomSharedPreference.getString(this, CustomSharedPreference.KEY_VISITOR);
        final Visitor visitor = gson.fromJson(jsonVisitor, Visitor.class);
        if (visitor == null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new HomeFragment(), "Home");
        ft.commit();


    }

    @Override
    public void onBackPressed() {
        Fragment home = getSupportFragmentManager().findFragmentByTag("Home");
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag("HomeFragment");
        Fragment eventInfoFragment = getSupportFragmentManager().findFragmentByTag("EventInfoFragment");
        Fragment exhibitorListFragment = getSupportFragmentManager().findFragmentByTag("ExhibitorListFragment");
        Fragment exhibitorHomeFragment = getSupportFragmentManager().findFragmentByTag("ExhibitorHomeFragment");


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (home instanceof HomeFragment && home.isVisible()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
            builder.setTitle("Confirm Action");
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (homeFragment instanceof EventInfoFragment && homeFragment.isVisible() ||
                homeFragment instanceof EventSubscribedFragment && homeFragment.isVisible() ||
                homeFragment instanceof LikedExhibitorFragment && homeFragment.isVisible() ||
                homeFragment instanceof LikedProductFragment && homeFragment.isVisible() ||
                homeFragment instanceof SettingsFragment && homeFragment.isVisible() ||
                homeFragment instanceof AboutDeveloperFragment && homeFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new HomeFragment(), "Home");
            ft.commit();

        } else if (eventInfoFragment instanceof ExhibitorsListFragment && eventInfoFragment.isVisible() ||
                eventInfoFragment instanceof FloorMapFragment && eventInfoFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new EventInfoFragment(), "HomeFragment");
            ft.commit();

        } else if (exhibitorListFragment instanceof ExhibitorsHomeFragment && exhibitorListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new ExhibitorsListFragment(), "EventInfoFragment");
            ft.commit();

        } else if (exhibitorHomeFragment instanceof ProductsFragment && exhibitorHomeFragment.isVisible() ||
                exhibitorHomeFragment instanceof PortfolioFragment && exhibitorHomeFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new ExhibitorsHomeFragment(), "ExhibitorListFragment");
            ft.commit();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new HomeFragment(), "Home");
            ft.commit();
        } else if (id == R.id.nav_event) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new EventSubscribedFragment(), "HomeFragment");
            ft.commit();
        } else if (id == R.id.nav_exhibitor) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new LikedExhibitorFragment(), "HomeFragment");
            ft.commit();
        } else if (id == R.id.nav_product) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new LikedProductFragment(), "HomeFragment");
            ft.commit();
        } else if (id == R.id.nav_settings) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new SettingsFragment(), "HomeFragment");
            ft.commit();
        } else if (id == R.id.nav_aboutdev) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new AboutDeveloperFragment(), "HomeFragment");
            ft.commit();
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // updateUserToken(userId, "");
                    CustomSharedPreference.deletePreference(HomeActivity.this);
                    cityMap.clear();
                    industryMap.clear();

                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    finish();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
