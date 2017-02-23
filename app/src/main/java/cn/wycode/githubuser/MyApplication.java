package cn.wycode.githubuser;

import android.app.Application;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import cn.wycode.githubuser.net.OKhttpUtils;
import cn.wycode.githubuser.utils.DisplayUtils;
import okhttp3.OkHttpClient;


/**
 * 主要用作第三方初始化
 * Created by wy on 2015/10/15.
 */
public class MyApplication extends Application {
    public static ImageLoader imageLoader;
    public static int heightPixels;
    public static int widthPixels;


    @Override
    public void onCreate() {
        super.onCreate();
        // 获取屏幕高度
        heightPixels = DisplayUtils.getScreenHeight(this);
        // 获取屏幕宽度
        widthPixels = DisplayUtils.getScreenWidth(this);
        //初始化ImageLoader
        initImageLoader();

        OKhttpUtils.init(this);

    }

    private void initImageLoader() {
        // Create default options which will be used for every
        // displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_about) // resource
                .showImageForEmptyUri(R.drawable.ic_about) // resource or
                // drawable
                .showImageOnFail(R.drawable.ic_about) // resource or
//                         drawable
                .displayer(new FadeInBitmapDisplayer(200, true, false, false)).cacheInMemory(true).cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(widthPixels, heightPixels) // default
                .diskCacheExtraOptions(widthPixels, heightPixels, null).defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config); // Do it on Application start
        imageLoader = ImageLoader.getInstance();
//     Then later, when you want to display image
//     DiningMasterApplication.imageLoader.displayImage( String imageUrl,
//     ImageView imageView);
    }

}
