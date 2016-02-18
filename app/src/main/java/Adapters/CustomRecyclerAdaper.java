package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanch on 2016/2/18.
 */
public class CustomRecyclerAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //头部标识
    private int Type_Head=1;

    //头部集合
    private List<RecyclerView.ViewHolder> HeadHolderList=new ArrayList<RecyclerView.ViewHolder>();
    //头部数量
    private int HeadCount=0;
    private int HeadPosition=0;

   //添加头部
    public void AddHead(RecyclerView.ViewHolder holder)
    {
        HeadHolderList.add(holder);
        HeadCount++;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        //判断是否存在Head,存在则返回
        if (HeadCount>0&&position<HeadCount)
        {
           // IsHead=true;
            HeadPosition=position;
            return Type_Head;
        }
        else
        {
           // IsHead=false;
        }
        if (position==0)
            return Type_Head;
        return super.getItemViewType(position);
    }
}
