package com.riact.ricart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.riact.ricart.utils.RiactDbHandler;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.app.FragmentManager fm=getFragmentManager();
        fm.beginTransaction().replace(R.id.content_menu,new Dashboard()).commit();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.FragmentManager fm=getFragmentManager();


        if (id == R.id.nav_dashboard) {
            fm.beginTransaction().replace(R.id.content_menu,new Dashboard()).commit();

            // Handle the camera action
        } else if (id == R.id.nav_order) {

            fm.beginTransaction().replace(R.id.content_menu,new Order()).commit();

        } else if (id == R.id.nav_new_order) {

            fm.beginTransaction().replace(R.id.content_menu,new NewOrder()).commit();
        } else if(id == R.id.nav_past_order){

            fm.beginTransaction().replace(R.id.content_menu,new PastOrders()).commit();


        }
        else if(id == R.id.nav_favourites){
            fm.beginTransaction().replace(R.id.content_menu,new Favourites()).commit();
        }

        else if(id==R.id.nav_profile){

            fm.beginTransaction().replace(R.id.content_menu,new Profile()).commit();
        }
        else if(id==R.id.nav_logout)
        {
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Warning");
            alert.setMessage("All the saved orders will be deleted. Do you want to Logout?");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    RiactDbHandler db=new RiactDbHandler(getApplicationContext());
                    db.deleteUser();
                    db.deleteOrder();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
            alert.show();

        }
        /*else if (id == R.id.nav_exit) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Warning");
            alert.setMessage("Do you want to exit?");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    System.exit(1);
                }
            });
            alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });

            alert.show();

        }*/
        //

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
