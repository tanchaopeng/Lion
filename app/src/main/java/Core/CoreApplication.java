package Core;

import android.app.Application;

import org.xutils.x;

/**
 * Created by tanch on 2016/2/16.
 */
public class CoreApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
    }
}
