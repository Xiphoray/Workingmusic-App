package text.workingmusic;

import android.animation.ObjectAnimator;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.SeekBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    final int locationb1[] = new int[2];
    final int locationb2[] = new int[2];
    MediaPlayer s1,s2,s3,s4;
    boolean playon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        final View button1 = findViewById(R.id.button);
        final View button2 = findViewById(R.id.button2);

        /*
            关闭按键
         */
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Allstop();

                playon = false;
                button1.getLocationOnScreen(locationb1);
                button2.getLocationOnScreen(locationb2);
                ObjectAnimator.ofFloat(button1, "translationY", 0 , locationb2[1]-locationb1[1]).setDuration(500).start();
                View view;
                view=findViewById(R.id.workingmusicnow);
                setHideAnimation(view,450);
            }
        });

        /*
            开始按键
         */
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Allplay();
                playon = true;
                button1.getLocationOnScreen(locationb1);
                button2.getLocationOnScreen(locationb2);
                ObjectAnimator.ofFloat(button2, "translationY", 0 , locationb1[1]-locationb2[1]).setDuration(500).start();
                View view;
                view=findViewById(R.id.workingmusicnow);
                setShowAnimation(view,450);
            }
        });

        /*
            seekbar监听设置
         */
        SeekBar seekBar1 =(SeekBar)findViewById(R.id.seekBar1);
        SeekBar seekBar2 =(SeekBar)findViewById(R.id.seekBar2);
        SeekBar seekBar3 =(SeekBar)findViewById(R.id.seekBar3);
        SeekBar seekBar4 =(SeekBar)findViewById(R.id.seekBar4);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {
            }
            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar1) {
            }
            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar1, int progress,
                                          boolean fromUser) {
                s1.setVolume(progress/10.0f, progress/10.0f);

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }
            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }
            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar2, int progress,
                                          boolean fromUser) {
                s2.setVolume(progress/10.0f, progress/10.0f);

            }
        });
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar3) {
            }
            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar3) {
            }
            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar3, int progress,
                                          boolean fromUser) {
                s3.setVolume(progress/10.0f, progress/10.0f);
            }
        });
        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar4) {
            }
            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar4) {
            }
            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar4, int progress,
                                          boolean fromUser) {
                s4.setVolume(progress/10.0f, progress/10.0f);
            }
        });

        /*
            播放器设置
         */
        s1 = new MediaPlayer();
        s2 = new MediaPlayer();
        s3 = new MediaPlayer();
        s4 = new MediaPlayer();

        try
        {
            AssetManager g1 = getAssets();
            AssetFileDescriptor fire = g1.openFd("1/fire.wav");
            s1.setDataSource(fire.getFileDescriptor(),fire.getStartOffset(), fire.getLength());
            s1.prepareAsync();
            s1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp)
                {
                    try
                    {
                        AssetManager g1 = getAssets();
                        AssetFileDescriptor rain = g1.openFd("2/rain.wav");
                        s2.setDataSource(rain.getFileDescriptor(),rain.getStartOffset(), rain.getLength());
                        s2.prepareAsync();
                        s2.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                        {
                            @Override
                            public void onPrepared(MediaPlayer mp)
                            {
                                try
                                {
                                    AssetManager g1 = getAssets();
                                    AssetFileDescriptor thunder = g1.openFd("3/thunder.wav");
                                    s3.setDataSource(thunder.getFileDescriptor(),thunder.getStartOffset(), thunder.getLength());
                                    s3.prepareAsync();
                                    s3.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                                    {
                                        @Override
                                        public void onPrepared(MediaPlayer mp)
                                        {
                                            try
                                            {
                                                AssetManager g1 = getAssets();
                                                AssetFileDescriptor people = g1.openFd("4/people.wav");
                                                s4.setDataSource(people.getFileDescriptor(),people.getStartOffset(), people.getLength());
                                                s4.prepareAsync();
                                                s4.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                                                {
                                                    @Override
                                                    public void onPrepared(MediaPlayer mp)
                                                    {
                                                        s1.setVolume(0, 0);
                                                        s2.setVolume(0, 0);
                                                        s3.setVolume(0, 0);
                                                        s4.setVolume(0, 0);

                                                    }
                                                });
                                            }
                                            catch (IOException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                                catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    /*
        停止播放
     */
    public  void Allstop()
    {
        s1.pause();
        s2.pause();
        s3.pause();
        s4.pause();
    }

    /*
        开始播放
     */
    public void Allplay()
    {
        s1.start();
        s2.start();
        s3.start();
        s4.start();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        View view;
        if (id == R.id.nav_workingmusic) {
            if(playon == true)
            {
                view=findViewById(R.id.workingmusicnow);
                view.setVisibility(View.VISIBLE);
            }
            else
            {
                view=findViewById(R.id.workingmusicnow);
                view.setVisibility(View.INVISIBLE);
            }
            view=findViewById(R.id.workingmusicstop);
            view.setVisibility(View.VISIBLE);
            view=findViewById(R.id.informationnow);
            view.setVisibility(View.INVISIBLE);

        } else if (id == R.id.nav_information) {
            view=findViewById(R.id.workingmusicnow);
            view.setVisibility(View.INVISIBLE);
            view=findViewById(R.id.workingmusicstop);
            view.setVisibility(View.INVISIBLE);
            view=findViewById(R.id.informationnow);
            view.setVisibility(View.VISIBLE);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private  AlphaAnimation mHideAnimation= null;

    private  AlphaAnimation mShowAnimation= null;
    /**
     * View渐现动画效果
     */
    public  void setShowAnimation( final View view, int duration)
    {
        if (null == view || duration < 0)
        {
            return;
        }
        if (null != mShowAnimation)
        {
            mShowAnimation.cancel();
        }
        mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
        mShowAnimation.setDuration(duration);
        mShowAnimation.setFillAfter(false);
        mShowAnimation.setAnimationListener(new AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation arg0)
            {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation arg0)
            {

            }

            @Override
            public void onAnimationEnd(Animation arg0)
            {


                View button1=findViewById(R.id.button);
                View button2=findViewById(R.id.button2);
//                button1.getLocationOnScreen(locationb1);
//                button2.getLocationOnScreen(locationb2);
                ObjectAnimator.ofFloat(button2, "translationY", 0 , 0).setDuration(500).start();
                button2.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.VISIBLE);
            }
        });
        view.startAnimation(mShowAnimation);
    }

    /**
     * View渐隐动画效果
     */
    public  void setHideAnimation( final View view, int duration)
    {
        if (null == view || duration < 0)
        {
            return;
        }

        if (null != mHideAnimation)
        {
            mHideAnimation.cancel();
        }
        // 监听动画结束的操作
        mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mHideAnimation.setDuration(duration);
        mHideAnimation.setFillAfter(true);
        mHideAnimation.setAnimationListener(new AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation arg0)
            {

            }

            @Override
            public void onAnimationRepeat(Animation arg0)
            {

            }

            @Override
            public void onAnimationEnd(Animation arg0)
            {
                view.setVisibility(View.INVISIBLE);
                View button1=findViewById(R.id.button);
                View button2=findViewById(R.id.button2);
//                button1.getLocationOnScreen(locationb1);
//                button2.getLocationOnScreen(locationb2);
                ObjectAnimator.ofFloat(button1, "translationY", 0 , 0).setDuration(500).start();
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.VISIBLE);
            }
        });
        view.startAnimation(mHideAnimation);
    }
}
