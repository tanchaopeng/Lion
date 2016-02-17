package Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Models.CategoryModel;
import Models.HomeModel;
import youmo.lion.R;

/**
 * Created by tanch on 2016/2/17.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Activity activity;
    private final List<HomeModel> Data;
    private final String[] HeadUrls;
    private ISubCilck sc;
    private int TYPE_HEADER=1;
    public interface ISubCilck
    {
        void OnClick(View v, int i);
    }

    public void SetSubCilck(ISubCilck sc)
    {
        this.sc=sc;
    }

    public HomeRecyclerAdapter(Activity activity, List<HomeModel> data, String[] headUrls)
    {
        this.activity=activity;
        Data = data;
        HeadUrls = headUrls;
    }

    //主页
    public class HomeViewHolder extends RecyclerView.ViewHolder
    {
        public TextView AllTitle;
        public TextView Title;
        public TextView SmallTitle;
        public ImageView Img;

        public String Tag;
        public String Link;

        public HomeViewHolder(View v) {
            super(v);
            AllTitle=(TextView)v.findViewById(R.id.TextView_Home_AllTitle);
            Title=(TextView)v.findViewById(R.id.TextView_Home_Title);
            SmallTitle=(TextView)v.findViewById(R.id.TextView_Home_SmallTitle);
            Img=(ImageView)v.findViewById(R.id.ImageView_Home_Img);
            Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sc.OnClick(v,getAdapterPosition());
                }
            });
        }
    }
    //头部广告
    public class HeadViewHolder extends RecyclerView.ViewHolder
    {
        public HeadViewHolder(View itemView,String[] urls) {
            super(itemView);
            //设置宽高度
            ((ViewPager) itemView).setLayoutParams(new ViewGroup.LayoutParams(-1, DensityUtil.dip2px(150)));
            //加载
            InitBanner(activity,(ViewPager) itemView,urls);
        }
        private void InitBanner(final Activity activity,final ViewPager viewPager, String[] urls)
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
    //分类
    public class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        public CategoryViewHolder(View itemView) {
            super(itemView);
        }
    }
    //产品
    public class ProductViewHolder extends RecyclerView.ViewHolder
    {
        public ProductViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public int getItemCount() {
        return Data.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_item, parent, false);
        if (viewType==TYPE_HEADER)
        {
            v = new ViewPager(parent.getContext());
            return new HeadViewHolder(v,HeadUrls);
        }

        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder)
            return;
        HomeModel p=Data.get(position);
        ((HomeViewHolder)holder).AllTitle.setText(p.getAllTitle());
        ((HomeViewHolder)holder).Title.setText(p.getTitle());
        ((HomeViewHolder)holder).SmallTitle.setText(p.getSmallTitle());

        Glide.with(activity).load(p.getImg()).into(((HomeViewHolder)holder).Img);
//        if (p.getImg()!=null)
//            ((HomeViewHolder)holder).Img.setImageBitmap(null);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return TYPE_HEADER;
        return super.getItemViewType(position);
    }
}
