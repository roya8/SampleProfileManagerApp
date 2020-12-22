package com.communere.testapplication.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.communere.testapplication.R;

import java.io.File;

import androidx.annotation.Nullable;

public class GlideUtil {

    private static final String TAG = "GlideUtil";
    private static final int timeout = 10000 ;

//    public static void loadLocalImage(final String path, com.mikhaellopez.circularimageview.CircularImageView imv, Context context){
    public static void loadLocalImage(final String path, ImageView imv, Context context){
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_icon_user).timeout(timeout).error(R.drawable.ic_icon_user).dontAnimate();

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(new File(path))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d(TAG, "loadLocalImage_excep for uri: "+ path+ "   --> "+e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d(TAG, "onResourceReady");
                        return false;
                    }
                })

                .into(imv);
    }
    public static void loadLocalImage(final File file, com.mikhaellopez.circularimageview.CircularImageView imv, Context context){

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_icon_user).error(R.drawable.ic_icon_user).dontAnimate();

        //Loading image from url into imageView
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(file)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imv);
    }


}
