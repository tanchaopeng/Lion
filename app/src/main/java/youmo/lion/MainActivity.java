package youmo.lion;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Core.BaseActivity;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.Toolbar_Main)
    private Toolbar Toolbar_Main;
    @ViewInject(R.id.DrawerLayout_Main)
    private DrawerLayout DrawerLayout_Main;
    @ViewInject(R.id.ListView_Main_Menu)
    private ListView ListView_Main_Menu;
    private ActionBarDrawerToggle DrawerToggle_Main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        setSupportActionBar(Toolbar_Main);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerToggle_Main=new ActionBarDrawerToggle(this,DrawerLayout_Main,Toolbar_Main,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        DrawerToggle_Main.syncState();
        DrawerLayout_Main.setDrawerListener(DrawerToggle_Main);
        String[] NavData={"List Item 01", "List Item 02", "List Item 03", "List Item 04"};
        ListView_Main_Menu.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, NavData));


        if (findViewById(R.id.FrameLayout_Main_Content)  != null) {
            if (savedInstanceState != null) {
                return;
            }
            HomeFragment home = new HomeFragment();

            home.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.FrameLayout_Main_Content, home).commit();

        }

    }


}
