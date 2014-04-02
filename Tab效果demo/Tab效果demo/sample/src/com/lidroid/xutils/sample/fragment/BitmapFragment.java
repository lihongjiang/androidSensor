package com.lidroid.xutils.sample.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.sample.R;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: wyouflf
 * Date: 13-9-14
 * Time: 下午3:35
 */
public class BitmapFragment extends Fragment {

    private BitmapUtils bitmapUtils;
    private String[] imgSites = {
            "http://image.baidu.com/",
            "http://www.22mm.cc/",
            "http://www.moko.cc/",
            "http://eladies.sina.com.cn/photo/",
            "http://www.youzi4.com/"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bitmap_fragment, container, false); // 加载fragment布局
        ViewUtils.inject(this, view); //注入view和事件

        bitmapUtils = new BitmapUtils(this.getActivity());
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.bitmap);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);

        //bitmapUtils.configDefaultBitmapMaxWidth(200);
        //bitmapUtils.configDefaultBitmapMaxHeight(200);

        ////bitmapUtils.configDefaultShowOriginal(true);// 显示原始图片，不压缩。

        // 滑动时加载图片，快速滑动时不加载图片
        imageListView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        imageListAdapter = new ImageListAdapter(this.getActivity());
        imageListView.setAdapter(imageListAdapter);

        // 加载url请求返回的图片连接给listview
        // 这里只是简单的示例，并非最贱实践，图片较多时，最好上拉加载更多...
        for (String url : imgSites) {
            loadImgList(url);
        }

        return view;
    }

    private void loadImgList(String url) {
        new HttpUtils().send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(String result) {
                        imageListAdapter.addSrc(getImgSrcList(result));
                        imageListAdapter.notifyDataSetChanged();//通知listview更新数据
                    }
                });
    }

    @ViewInject(R.id.img_list)
    private ListView imageListView;

    ImageListAdapter imageListAdapter;

    private class ImageListAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<String> imgSrcList;

        public ImageListAdapter(Context context) {
            super();
            this.context = context;
            imgSrcList = new ArrayList<String>();
        }

        public void addSrc(List<String> imgSrcList) {
            this.imgSrcList.addAll(imgSrcList);
        }

        @Override
        public int getCount() {
            return imgSrcList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null) {
                view = new ImageView(this.context);
            }
            bitmapUtils.display((ImageView) view, imgSrcList.get(position));
            //bitmapUtils.display((ImageView) view, imgSrcList.get(position), displayConfig);
            return view;
        }
    }


    /**
     * 得到网页中图片的地址
     */
    public static List<String> getImgSrcList(String htmlStr) {
        List<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*?src=\"(.*?)\""; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            pics.add(m_image.group(1));
        }
        return pics;
    }

}
