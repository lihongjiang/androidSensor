package com.lidroid.xutils.sample.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.RequestParams;
import com.lidroid.xutils.sample.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

/**
 * Author: wyouflf
 * Date: 13-9-14
 * Time: 下午3:35
 */
public class HttpFragment extends Fragment {

    private HttpHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.http_fragment, container, false);
        ViewUtils.inject(this, view);
        return view;
    }

    @ViewInject(R.id.download_addr_edit)
    private EditText downloadAddrEdit;

    @ViewInject(R.id.download_btn)
    private Button downloadBtn;

    @ViewInject(R.id.stop_btn)
    private Button stopBtn;

    @ViewInject(R.id.result_txt)
    private TextView resultText;

    @OnClick(R.id.download_btn)
    public void download(View view) {
        HttpUtils http = new HttpUtils();
        handler = http.download(
                downloadAddrEdit.getText().toString(),
                "/sdcard/fileexplorer.apk",
                true, // 如果目标文件存在，接着未完成的部分继续下载。
                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {

                    @Override
                    public void onStart() {
                        resultText.setText("conn...");
                    }

                    @Override
                    public void onLoading(long total, long current) {
                        resultText.setText(current + "/" + total);
                    }

                    @Override
                    public void onSuccess(File result) {
                        resultText.setText("downloaded:" + result.getPath());
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        resultText.setText(error.getExceptionCode() + ":" + msg);
                    }
                });
    }

    @OnClick(R.id.stop_btn)
    public void stop(View view) {
        if (handler != null) {
            handler.stop();
            resultText.setText("stopped");
        }
    }

    /////////////////////////////////////// other ////////////////////////////////////////////////////////////////

    private void testUpload() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method", "upload");
        params.addQueryStringParameter("path", "/apps/测试应用/test.zip");
        // 请在百度的开放access_tokenapi测试页面找到自己的access_token
        params.addQueryStringParameter("access_token", "3.1042851f652496c9362b1cd77d4f849b.2592000.1377530363.3590808424-248414");
        params.addBodyParameter("file", new File("/sdcard/test.zip"));

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                "https://pcs.baidu.com/rest/2.0/pcs/file",
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        resultText.setText("conn...");
                    }

                    @Override
                    public void onLoading(long total, long current) {
                        resultText.setText(current + "/" + total);
                    }

                    @Override
                    public void onSuccess(String result) {
                        resultText.setText("upload response:" + result);
                    }


                    @Override
                    public void onFailure(HttpException error, String msg) {
                        resultText.setText(msg);
                    }
                });
    }

    private void testGet() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method", "info");
        params.addQueryStringParameter("access_token",
                "3.1042851f652496c9362b1cd77d4f849b.2592000.1377530363.3590808424-248414");

        HttpUtils http = new HttpUtils();
        http.configCurrRequestExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.GET,
                "https://pcs.baidu.com/rest/2.0/pcs/quota",
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        resultText.setText("conn...");
                    }

                    @Override
                    public void onLoading(long total, long current) {
                        resultText.setText(current + "/" + total);
                    }

                    @Override
                    public void onSuccess(String result) {
                        resultText.setText("response:" + result);
                    }


                    @Override
                    public void onFailure(HttpException error, String msg) {
                        resultText.setText(msg);
                    }
                });
    }

    private void testPost() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method", "mkdir");
        params.addQueryStringParameter("access_token", "3.1042851f652496c9362b1cd77d4f849b.2592000.1377530363.3590808424-248414");
        params.addBodyParameter("path", "/apps/测试应用/test文件夹");

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                "https://pcs.baidu.com/rest/2.0/pcs/file",
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        resultText.setText("conn...");
                    }

                    @Override
                    public void onLoading(long total, long current) {
                        resultText.setText(current + "/" + total);
                    }

                    @Override
                    public void onSuccess(String result) {
                        resultText.setText("upload response:" + result);
                    }


                    @Override
                    public void onFailure(HttpException error, String msg) {
                        resultText.setText(msg);
                    }
                });
    }

}
