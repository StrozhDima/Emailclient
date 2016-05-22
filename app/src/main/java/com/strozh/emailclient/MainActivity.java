package com.strozh.emailclient;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.woods.emailclient.R;
import com.strozh.emailclient.fragments.FragmentInbox;

/**
 * Created by Woods on 21.05.2016.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    public void setTheme(int resid) {
        super.setTheme(R.style.AppDefault);
    }

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private static final int LAYOUT = R.layout.activity_main;
    //инициализируем фрагменты каждого списка
    private FragmentInbox fInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        initToolbar();
        initNavigationView();
        fInbox = new FragmentInbox();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);

    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                switch (menuItem.getItemId()) {
                    case R.id.action_item_inbox:
                        fragmentTransaction.replace(R.id.inbox_frame, fInbox);
                        break;
                }

                fragmentTransaction.commit();

                return true;
            }
        });
    }

}
