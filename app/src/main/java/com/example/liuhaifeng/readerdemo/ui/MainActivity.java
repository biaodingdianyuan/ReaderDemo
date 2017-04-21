package com.example.liuhaifeng.readerdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.liuhaifeng.readerdemo.tool.MyActionBarDrawerToggle;
import com.example.liuhaifeng.readerdemo.R;
import com.example.liuhaifeng.readerdemo.ui.Music.MusicFragment;
import com.example.liuhaifeng.readerdemo.ui.Picture.PictureFragment;
import com.example.liuhaifeng.readerdemo.ui.Video.VideoFragment;
import com.example.liuhaifeng.readerdemo.ui.news.NewsFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        toolbar.inflateMenu(R.menu.other);
        setSupportActionBar(toolbar);
        drawertoggle();

        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setOnMenuItemClickListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,NewsFragment.newInstance(0)).commit();

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void drawertoggle() {
        mDrawerToggle = new MyActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.music:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MusicFragment()).commit();
//                Toast.makeText(MainActivity.this, "music", Toast.LENGTH_SHORT).show();
                break;
            case R.id.news:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, NewsFragment.newInstance(0)).commit();
                break;
            case R.id.picture:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new PictureFragment()).commit();
//                Toast.makeText(MainActivity.this, "picture", Toast.LENGTH_SHORT).show();
                break;
            case R.id.video:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new VideoFragment()).commit();
//                Toast.makeText(MainActivity.this, "video", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawerLayout.closeDrawers();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.other, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saomiao:
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
                break;
            case R.id.about:
                break;
            case R.id.feedback:
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            String result = scanResult.getContents();
            startActivity(new Intent(MainActivity.this, WebViewActivity.class).putExtra("url", result));
        }
    }
}
