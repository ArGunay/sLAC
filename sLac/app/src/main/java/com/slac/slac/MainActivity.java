package com.slac.slac;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /***
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ***/


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

        switch (item.getItemId()) {


            /*case R.id.action_settings:
                Intent intentSettings=new Intent(this,Settings.class);
                if (id == R.id.action_settings) {
                    startActivity(intentSettings);
                }*/

            case R.id.info_bar:
                Intent intentInformation=new Intent(this,Information.class);
                if (id == R.id.info_bar) {
                    startActivity(intentInformation);
                }

            /*case R.id.account_bar:
                Intent intentAccount=new Intent(this,SetAccount.class);
                if (id == R.id.account_bar) {
                    startActivity(intentAccount);
                }*/

            case R.id.share_bar:
                if (id == R.id.share_bar) {

                    /***
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Join the sLAC community and have fun!");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    ***/

                    Intent intentShare = new Intent(Intent.ACTION_SEND);
                    intentShare.setType("text/plain");
                    intentShare.putExtra(Intent.EXTRA_SUBJECT, "Join the sLAC community!!");
                    intentShare.putExtra(Intent.EXTRA_TEXT, "https://github.com/ArGunay/sLac");
                    startActivity(intentShare);

                    /***
                    new AlertDialog.Builder(this)

                            .setTitle("Share Your Score!!!")
                            .setMessage("Select your social app.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })

                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })

                            .setMultiChoiceItems (CharSequence[] "ciao", boolean[] checkedItems,DialogInterface.OnMultiChoiceClickListener listener)

                            .setIcon(android.R.drawable.ic_dialog_email)
                            .show();
                     ***/

                }




        }

        return super.onOptionsItemSelected(item);
    }

    //deleted PlaceHolderFragment from here

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TabEvents tabEvents = new TabEvents();
                    return tabEvents;
                case 1:
                    TabMap tabMap = new TabMap();
                    return tabMap;
                case 2:
                    TabAccount tabAccount = new TabAccount();
                    return tabAccount;


                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "EVENTS";
                case 1:
                    return "MAP";
                case 2:
                    return "ACCOUNT";

            }
            return null;
        }
    }




}
