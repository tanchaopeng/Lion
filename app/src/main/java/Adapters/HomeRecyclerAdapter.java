package Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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
    private ISubCilck sc;
    //头部
    private int TYPE_HEADER=1;
    private int HeadCount=0;
    private int HeadPosition=0;
    private boolean IsHead=false;

    //头部集合
    private List<RecyclerView.ViewHolder> HeadHolderList=new ArrayList<RecyclerView.ViewHolder>();

    public interface ISubCilck
    {
        void OnClick(View v, int i);
    }
    public List<HomeModel> GetData()
    {
        return Data;
    }
    public HomeModel GetDataForPos(int i)
    {
        return Data.get(i-HeadCount);
    }
    public void SetSubCilck(ISubCilck sc)
    {
        this.sc=sc;
    }

    public void AddHead(RecyclerView.ViewHolder viewHolder)
    {
        HeadHolderList.add(viewHolder);
        HeadCount++;
    }

    public HomeRecyclerAdapter(Activity activity, List<HomeModel> data)
    {
        this.activity=activity;
        Data = data;
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

            Img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(250)));
            Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sc.OnClick(v,getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Data.size()+HeadCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_HEADER)
            return HeadHolderList.get(HeadPosition);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_item, parent, false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (IsHead)
            return;
        HomeModel p=Data.get(position-HeadCount);
        ((HomeViewHolder)holder).AllTitle.setText(p.getAllTitle());
        ((HomeViewHolder)holder).Title.setText(p.getTitle());
        ((HomeViewHolder)holder).SmallTitle.setText(p.getSmallTitle());
        x.image().bind(((HomeViewHolder)holder).Img,p.getImgLink());
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
                    if (getItemViewType(position)==2)
                        return 2;
                    return getItemViewType(position) == TYPE_HEADER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        //判断是否存在Head,存在则返回
        if (HeadCount>0&&position<HeadCount)
        {
            IsHead=true;
            HeadPosition=position;
            return TYPE_HEADER;
        }
        else
        {
            IsHead=false;
        }
        if (position==0)
            return TYPE_HEADER;
        if (position==1)
            return 2;
        return super.getItemViewType(position);
    }
}
