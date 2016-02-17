package Models;

import android.graphics.Bitmap;

/**
 * 主页
 * Created by tanch on 2016/2/17.
 */
public class HomeModel {
    //总标题
    private String AllTitle;
    //子标题
    private String Title;
    //子小标题
    private String SmallTitle;

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    //图片链接
    private String Img;
    //标识
    private String tag;
    //产品或分类链接
    private String Link;
    //其他信息
    private String Other;

    public HomeModel(String allTitle,String title,String smallTitle,String img)
    {
        AllTitle=allTitle;
        Title=title;
        SmallTitle=smallTitle;
        Img=img;
    }


    public String getAllTitle() {
        return AllTitle;
    }

    public void setAllTitle(String allTitle) {
        AllTitle = allTitle;
    }


    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String other) {
        Other = other;
    }

    public String getSmallTitle() {
        return SmallTitle;
    }

    public void setSmallTitle(String smallTitle) {
        SmallTitle = smallTitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


}
