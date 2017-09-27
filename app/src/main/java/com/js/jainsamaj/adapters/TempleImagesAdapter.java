package com.js.jainsamaj.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.js.jainsamaj.R;

import java.util.ArrayList;

/**
 * Created by arbaz on 4/3/17.
 */

public class TempleImagesAdapter extends PagerAdapter {


    ArrayList<String> stringArrayList;
    Context mContext;
    LayoutInflater mLayoutInflater;
    SimpleDraweeView templeImgs;


    public TempleImagesAdapter(Context context, ArrayList<String> stringArrayList) {
        this.mContext = context;
        this.stringArrayList = stringArrayList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return stringArrayList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.temple_images_slider, container, false);
        templeImgs = (SimpleDraweeView) itemView.findViewById(R.id.temple_image_slider);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mContext.getResources());
            GenericDraweeHierarchy hierarchy = builder
                    .setFadeDuration(200)
                    .setProgressBarImage(new AutoRotateDrawable(mContext.getDrawable(R.drawable.ic_spinner), 1500))
                    .build();
            templeImgs.setHierarchy(hierarchy);
        } else {
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mContext.getResources());
            GenericDraweeHierarchy hierarchy = builder
                    .setFadeDuration(200)
                    .setProgressBarImage(new AutoRotateDrawable(mContext.getResources().getDrawable(R.drawable.ic_spinner), 1500))
                    .build();
            templeImgs.setHierarchy(hierarchy);
        }


        String s;
        s = stringArrayList.get(position);
        if (s != null) {
            try {
                String imageString = s;
                Uri templeURi = Uri.parse(imageString);
                templeImgs.setImageURI(templeURi);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
