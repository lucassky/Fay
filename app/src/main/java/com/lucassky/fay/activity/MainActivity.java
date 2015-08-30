package com.lucassky.fay.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lucassky.fay.R;
import com.lucassky.fay.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private MainFragment mMainFragment;

    private FragmentTransaction mFragTransaction;

    private FragmentManager mFragManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.mytoolbar);
        mToolbar.inflateMenu(R.menu.main);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mydrawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mFragManager = getSupportFragmentManager();
        mFragTransaction = mFragManager.beginTransaction();
        mMainFragment = MainFragment.newInstance("1","2");
//        mFragTransaction.add(mMainFragment,"mainfrag").commit();
        setmMainFragment();
    }



    private void setmMainFragment(){
        mFragTransaction.replace(R.id.container, mMainFragment);
        mFragTransaction.addToBackStack(null);
        // Commit the transaction
        mFragTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

//    HttpManager.getStattuesFriends(getActivity(),0L, 0L, 10, 1, 0, 0, 0);
}
