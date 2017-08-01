package text.workingmusic;

import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    final int locationb1[] = new int[2];
    final int locationb2[] = new int[2];
    MediaPlayer s1,s2,s3,s4;
    boolean playon = false;
    RemoteViews mRemoteViews;
    NotificationManager nm;
    Notification nf;
    NotificationCompat.Builder mBuilder;
    /** 通知栏按钮广播 */
    public NotificationBroadcastReceiver bReceiver;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int sinit1,sinit2,sinit3,sinit4;


    View button1;
    View button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sinit1 = sharedPreferences.getInt("s1",0);
        sinit2 = sharedPreferences.getInt("s2",0);
        sinit3 = sharedPreferences.getInt("s3",0);
        sinit4 = sharedPreferences.getInt("s4",0);




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



        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);



        /*
            关闭按键
         */
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Allstop();
                nm.cancel(1417);
                playon = false;
                mRemoteViews.setImageViewResource(R.id.btn_custom_playstop,R.mipmap.ic_notification_start);
                refleshnotifi();
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
                mRemoteViews.setImageViewResource(R.id.btn_custom_playstop,R.mipmap.ic_notification_pause);
                refleshnotifi();
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
        seekBar1.setProgress(sinit1);
        seekBar2.setProgress(sinit2);
        seekBar3.setProgress(sinit3);
        seekBar4.setProgress(sinit4);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {
                editor.putInt("s1",seekBar1.getProgress());
                editor.commit();

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
                editor.putInt("s2",seekBar2.getProgress());
                editor.commit();
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
                editor.putInt("s3",seekBar3.getProgress());
                editor.commit();
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
                editor.putInt("s4",seekBar4.getProgress());
                editor.commit();

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
                                                        s1.setVolume(sinit1/10.0f, sinit1/10.0f);
                                                        s2.setVolume(sinit2/10.0f, sinit2/10.0f);
                                                        s3.setVolume(sinit3/10.0f, sinit3/10.0f);
                                                        s4.setVolume(sinit4/10.0f, sinit4/10.0f);
                                                        s1.setLooping(true);
                                                        s2.setLooping(true);
                                                        s3.setLooping(true);
                                                        s4.setLooping(true);

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

        /*
            通知栏设置
         */

        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //此Builder为android.support.v4.app.NotificationCompat.Builder中的，下同。
        mBuilder = new NotificationCompat.Builder(this);
        //系统收到通知时，通知栏上面显示的文字。
        mBuilder.setTicker("点击可以返回设置");
        //显示在通知栏上的小图标
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round,2);

        mBuilder.setVisibility(Notification.VISIBILITY_SECRET);
        //通知标题
        mBuilder.setContentTitle("Workingmusic");
        //通知内容
        mBuilder.setContentText("点击可以返回设置");
        //设置大图标，即通知条上左侧的图片（如果只设置了小图标，则此处会显示小图标）
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        //设置点击一次后消失（如果没有点击事件，则该方法无效。）
        mBuilder.setAutoCancel(true);
        //设置为不可清除模式
        mBuilder.setOngoing(false);


        mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_custom_button);


        Intent intent_btn = new Intent("text.notifications.intent.action.ButtonClick");
        intent_btn.putExtra("notificationbtn", 1);
        PendingIntent intentp = PendingIntent.getBroadcast(this, 1, intent_btn, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_playstop, intentp);
        intent_btn.putExtra("notificationbtn", 2);
        PendingIntent intents = PendingIntent.getBroadcast(this, 2, intent_btn, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_stopleave, intents);

        mBuilder.setContent(mRemoteViews);
        nf = mBuilder.build();
        //设置点击返回页面
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(this, MainActivity.class));//用ComponentName得到class对象
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式，两种情况

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);//将经过设置了的Intent绑定给PendingIntent
        nf.contentIntent = contentIntent;// 通知绑定 PendingIntent
        nf.flags= Notification.FLAG_ONGOING_EVENT | Notification. FLAG_AUTO_CANCEL | Notification.FLAG_NO_CLEAR;//设置自动取消
        nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        initButtonReceiver();


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
        //int id = item.getItemId();

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
            if(playon)
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
        else if(id == R.id.nav_exit)
        {
            nm.cancel(1417);
                System.exit(0);

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
                ObjectAnimator.ofFloat(button1, "translationY", 0 , 0).setDuration(500).start();
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.VISIBLE);
            }
        });
        view.startAnimation(mHideAnimation);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && playon == true) {
            moveTaskToBack(false);
            nm.notify(1417, nf);

            Toast.makeText(this, "你把我放在后台啦~", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(keyCode == KeyEvent.KEYCODE_BACK && playon != true)
        {
            nm.cancel(1417);
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void refleshnotifi()
    {
        mBuilder.setContent(mRemoteViews);
        nf = mBuilder.build();
        //设置点击返回页面
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(this, MainActivity.class));//用ComponentName得到class对象
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式，两种情况

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);//将经过设置了的Intent绑定给PendingIntent
        nf.contentIntent = contentIntent;// 通知绑定 PendingIntent
        nf.flags= Notification.FLAG_ONGOING_EVENT | Notification. FLAG_AUTO_CANCEL | Notification.FLAG_NO_CLEAR;//设置自动取消
        nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

    }

    /** 带按钮的通知栏点击广播接收 */
    public void initButtonReceiver(){
        bReceiver = new NotificationBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("text.notifications.intent.action.ButtonClick");
        registerReceiver(bReceiver, intentFilter);
    }

    class NotificationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (action.equals("text.notifications.intent.action.ButtonClick"))
            {
                View v = View.inflate(MainActivity.this, R.layout.view_custom_button, null);
                int btn_id = intent.getIntExtra("notificationbtn", 0);
                switch (btn_id)
                {
                    case 1:
                        if(playon)
                        {
                            Allstop();
                            playon = false;


                            mRemoteViews.setImageViewResource(R.id.btn_custom_playstop,R.mipmap.ic_notification_start);
                            refleshnotifi();
                            nm.notify(1417, nf);
                            button1.setVisibility(View.INVISIBLE);
                            button2.setVisibility(View.VISIBLE);
                            View view;
                            view=findViewById(R.id.workingmusicnow);
                            setHideAnimation(view,1);


                        }
                        else
                        {
                            Allplay();
                            playon = true;


                            mRemoteViews.setImageViewResource(R.id.btn_custom_playstop,R.mipmap.ic_notification_pause);
                            refleshnotifi();
                            nm.notify(1417, nf);
                            button1.setVisibility(View.VISIBLE);
                            button2.setVisibility(View.INVISIBLE);
                            View view;
                            view=findViewById(R.id.workingmusicnow);
                            setShowAnimation(view,1);

                        }
                        break;
                    case 2:
                        nm.cancel(1417);
                        System.exit(0);
                        break;
                }
            }
        }
    }
}
