package edu.temple.webbrowser;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity implements openPage {

    static int location = 0;
    int count = 1;
    String url;

    MyAdapter adapter;
    ViewPager pager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter = new MyAdapter(getSupportFragmentManager());

        adapter.add();


        if (getIntent().getData() != null ) {
            url = getIntent().getData().toString();
            Log.d("url", url);
        }

        pager = findViewById(R.id.viewPager);
        pager.setAdapter(adapter);



        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if(location > i){
                    if((location == 0)){
                        location = count - 1;
                    }else {
                        location = location - 1;
                    }
                    pager.setCurrentItem(location);

                } else if(location < i){
                    if(location == count - 1){
                        location = 0;
                    } else {
                        location = location + 1;
                    }
                    pager.setCurrentItem(location);

                } else {
                    location = i;
                    pager.setCurrentItem(location);

                }



            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                count++;
                location = count;
                adapter.add();
                pager.setCurrentItem(location);

                break;
            case R.id.back:
                    if((location == 0)){
                        location = count - 1;
                    }else {
                        location = location - 1;
                    }
                pager.setCurrentItem(location);

                break;

            case R.id.forward:

                    if(location == count - 1){
                        location = 0;
                    } else {
                        location = location + 1;
                    }
                pager.setCurrentItem(location);

                break;
        }
        return true;

    }

    @Override
    public String open() {
        if (getIntent().getData() != null ) {
            url = getIntent().getData().toString();
            Log.d("url", url);
        }
        return url;
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {

        int count;

        public MyAdapter(FragmentManager fragmentManager ) {
            super(fragmentManager);

        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int location) {
            return webFragment.init();
            }

        public void add() {
            count++;
            notifyDataSetChanged();
        }
        }
    }



