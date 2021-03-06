package RecyclerHolders;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by tanch on 2016/2/18.
 */
//头部广告
public class BannerHolder extends RecyclerView.ViewHolder
{
    public BannerHolder(Activity activity, View itemView, String[] urls) {
        super(itemView);
        //设置宽高度
        ((ViewPager) itemView).setLayoutParams(new ViewGroup.LayoutParams(-1, DensityUtil.dip2px(150)));
        //加载
        InitBanner(activity,(ViewPager) itemView,urls);
    }
    private void InitBanner(final Activity activity, final ViewPager viewPager, String[] urls)
    {
        final List<ImageView> viewList=new ArrayList<ImageView>();
        //添加头部切换页
        ImageView head=new ImageView(activity);
        x.image().bind(head,urls[urls.length-1]);
        viewList.add(head);
        //加载主要切换页
        for (int i=0;i<urls.length;i++)
        {
            ImageView image=new ImageView(activity);
            x.image().bind(image,urls[i]);
            viewList.add(image);
        }
        //添加尾部切换页
        ImageView foot=new ImageView(activity);
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
                new Handler(activity.getMainLooper()).post(new Runnable() {
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

