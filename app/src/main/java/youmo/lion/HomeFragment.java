package youmo.lion;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Adapters.HomeRecyclerAdapter;
import Core.BaseFragment;
import Models.HomeModel;

/**
 * Created by tanch on 2016/2/16.
 */
public class HomeFragment extends BaseFragment {
    private Activity act;
    //private ViewPager ViewPager_Main_Banner;
    private RecyclerView RecyclerView_Main_Content;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act=getActivity();
    }

    /**
     * 初始化Views
     */
    private void InitViews(View v)
    {
        //ViewPager_Main_Banner=(ViewPager)v.findViewById(R.id.ViewPager_Main_Banner);
        RecyclerView_Main_Content=(RecyclerView)v.findViewById(R.id.RecyclerView_Main_Content);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main,container,false);
        InitViews(v);
        RecyclerView_Main_Content.setHasFixedSize(true);
        RecyclerView_Main_Content.setLayoutManager(new GridLayoutManager(act,2));

        List<HomeModel> data=new ArrayList<HomeModel>();
        data.add(new HomeModel("","","",""));
        data.add(new HomeModel("数码","平板","suface book","http://img4.duitang.com/uploads/item/201206/06/20120606175201_WZ2F3.jpeg"));
        data.add(new HomeModel("数码","平板","suface book","http://d.3987.com/tfmnx_1309014/003.jpg"));
        data.add(new HomeModel("数码","平板","suface book","http://imgsrc.baidu.com/forum/pic/item/e684e7f81a4c510f33b120e66059252dd52aa512.jpg"));
        data.add(new HomeModel("数码","平板","suface book","http://p.qq181.com/cms/1304/2013040607430286882.jpg"));
        data.add(new HomeModel("数码","平板","suface book","http://img4.duitang.com/uploads/item/201206/06/20120606175201_WZ2F3.jpeg"));
        data.add(new HomeModel("数码","平板","suface book","http://d.3987.com/tfmnx_1309014/003.jpg"));
        data.add(new HomeModel("数码","平板","suface book","http://imgsrc.baidu.com/forum/pic/item/e684e7f81a4c510f33b120e66059252dd52aa512.jpg"));
        data.add(new HomeModel("数码","平板","suface book","http://p.qq181.com/cms/1304/2013040607430286882.jpg"));
        data.add(new HomeModel("数码","平板","suface book","http://img4.duitang.com/uploads/item/201206/06/20120606175201_WZ2F3.jpeg"));
        data.add(new HomeModel("数码","平板","suface book","http://d.3987.com/tfmnx_1309014/003.jpg"));
        data.add(new HomeModel("数码","平板","suface book","http://imgsrc.baidu.com/forum/pic/item/e684e7f81a4c510f33b120e66059252dd52aa512.jpg"));
        data.add(new HomeModel("数码","平板","suface book","http://p.qq181.com/cms/1304/2013040607430286882.jpg"));


        String[] BannerData={   "http://pic2.ooopic.com/10/58/62/79b1OOOPIC11.jpg",
                "http://pic2.ooopic.com/10/55/95/26b1OOOPICf8.jpg",
                "http://pic2.ooopic.com/10/93/68/93b1OOOPIC0f.jpg",
                "http://pic2.ooopic.com/10/75/04/43b1OOOPICc1.jpg"
        };
        HomeRecyclerAdapter hra=new HomeRecyclerAdapter(act,data, BannerData);
        hra.SetSubCilck(new HomeRecyclerAdapter.ISubCilck() {
            @Override
            public void OnClick(View v, int i) {
                Toast.makeText(act,"第"+i,Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView_Main_Content.setAdapter(hra);


        //InitBanner(ViewPager_Main_Banner,BannerData);
        return v;
    }

    /**
     * 初始化广告栏
     * @param viewPager
     * @param urls
     */
    private void InitBanner(Activity activity,final ViewPager viewPager, String[] urls)
    {
        final List<ImageView> viewList=new ArrayList<ImageView>();
        //添加头部切换页
        ImageView head=new ImageView(act);
        x.image().bind(head,urls[urls.length-1]);
        viewList.add(head);
        //加载主要切换页
        for (int i=0;i<urls.length;i++)
        {
            ImageView image=new ImageView(act);
            x.image().bind(image,urls[i]);
            viewList.add(image);
        }
        //添加尾部切换页
        ImageView foot=new ImageView(act);
        x.image().bind(foot,urls[0]);
        viewList.add(foot);

        final int end=viewList.size()-2;
        final int[] cur = {1};
        PagerAdapter pAdater=new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if ( position < 1) { //首位之前，跳转到末尾（N）
                    position = end; //跳到尾部切换页
                    viewPager.setCurrentItem(position, false);
                } else if ( position > end) { //末位之后，跳转到首位（1）
                    viewPager.setCurrentItem(1, false); //false:不显示跳转过程的动画
                    position = 1;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(pAdater);
        viewPager.setCurrentItem(1);

        new ScheduledThreadPoolExecutor(1).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                new Handler(act.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        //这里可以直接访问UI
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    }
                });
            }
        },5,5, TimeUnit.SECONDS);
    }
}
