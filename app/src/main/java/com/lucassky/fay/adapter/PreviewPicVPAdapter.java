package com.lucassky.fay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.lucassky.fay.R;
import com.lucassky.fay.model.base.ThumbnailPic;
import com.lucassky.fay.view.zoomable.DefaultZoomableController;
import com.lucassky.fay.view.zoomable.ZoomableController;
import com.lucassky.fay.view.zoomable.ZoomableDraweeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/9.
 */
public class PreviewPicVPAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<ThumbnailPic> mThumbnailPics;
    private LayoutInflater mLayoutInflater;
    public PreviewPicVPAdapter(Context mContext, ArrayList<ThumbnailPic> mThumbnailPics) {
        this.mContext = mContext;
        this.mThumbnailPics = mThumbnailPics;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mThumbnailPics.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = (View) mLayoutInflater.inflate(R.layout.item_preview_pic, null);

        ZoomableDraweeView  simpleDraweeView = (ZoomableDraweeView) view.findViewById(R.id.my_image_view);
        String url = mThumbnailPics.get(position).getThumbnail_pic();
        url = url.replace("thumbnail","large");
//        simpleDraweeView.setImageURI(Uri.parse(url));
//        ZoomableDraweeView d;
        GenericDraweeHierarchyBuilder builder =  new GenericDraweeHierarchyBuilder(mContext.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(300)
                .build();

//        Postprocessor redMeshPostprocessor = new BasePostprocessor() {
//            @Override
//            public String getName() {
//                return "redMeshPostprocessor";
//            }
//
//            @Override
//            public void process(Bitmap bitmap) {
//                for (int x = 0; x < bitmap.getWidth(); x+=2) {
//                    for (int y = 0; y < bitmap.getHeight(); y+=2) {
//                        bitmap.setPixel(x, y, Color.RED);
//                    }
//                }
//            }
//        };



        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                .setResizeOptions(new ResizeOptions())
//                .setPostprocessor(redMeshPostprocessor)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setHierarchy(hierarchy);
        simpleDraweeView.setController(controller);

        DefaultZoomableController ZoomableController =DefaultZoomableController.newInstance();
        RectF RectF = new RectF(1,1,1,1);
        ZoomableController.setImageBounds(RectF);
        simpleDraweeView.setZoomableController(ZoomableController);


//        int width = 50, height = 50;
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                .setResizeOptions(new ResizeOptions(width, height))
//                .build();
//        PipelineDraweeController  controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
//                .setOldController(null)
//                .setImageRequest(request)
//                .build();
//        simpleDraweeView.setController(controller);

        ((ViewPager) container).addView(view);

//        ImageView imageView = new ImageView(mContext);
//        Picasso.with(mContext).load(url).into(imageView);
//        ((ViewPager) container).addView(imageView);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        (ViewPager) container).RemoveView((View)object);
//        super.destroyItem(container, position, object);
//        destroyItem((View) container, position, object);
        ((ViewPager) container).removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
