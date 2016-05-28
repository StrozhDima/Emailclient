package com.strozh.emailclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.woods.emailclient.R;
import com.strozh.emailclient.fragments.*;

/**
 * Created by Woods on 21.05.2016.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    public void setTheme(int resid) {
        super.setTheme(R.style.AppDefault);
    }

    private static final String SETTINGS = "settings";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private static final int LAYOUT = R.layout.activity_main;
    //инициализируем фрагменты каждого списка
    private FragmentInbox fInbox = new FragmentInbox();
    private FragmentSent fSent = new FragmentSent();
    private FragmentSpam fSpam = new FragmentSpam();
    private FragmentNewMail fNewMail = new FragmentNewMail();
    private FragmentDrafts fDrafts = new FragmentDrafts();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        initToolbar();
        initNavigationView();
    }

    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_item_login:

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                        return true;
                }
                return false;
            }
        });
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                switch (menuItem.getItemId()) {
                    case R.id.action_item_inbox:
                        fragmentTransaction.replace(R.id.general_layout, fInbox);
                        break;
                    case R.id.action_item_sent:
                        fragmentTransaction.replace(R.id.general_layout, fSent);
                        break;
                    case R.id.action_item_spam:
                        fragmentTransaction.replace(R.id.general_layout, fSpam);
                        break;
                    case R.id.action_item_new_mail:
                        fragmentTransaction.replace(R.id.general_layout, fNewMail);
                        break;
                    case R.id.action_item_drafts:
                        fragmentTransaction.replace(R.id.general_layout, fDrafts);
                        break;
                    case R.id.action_item_exit:
                        //Close program
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                }
                fragmentTransaction.commit();
                return true;
            }
        });
    }
}
