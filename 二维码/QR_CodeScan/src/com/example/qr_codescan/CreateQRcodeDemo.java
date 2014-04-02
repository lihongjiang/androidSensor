package com.example.qr_codescan;


	import android.app.Activity;
	import android.graphics.Bitmap;
	import android.os.Bundle;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.EditText;
	import android.widget.ImageView;
	import android.widget.Toast;


	import com.google.zxing.WriterException;

import com.minging.app.zxing.activity.QRcodeUtils;

	/**
	 * 用于创建和扫描二维码
	 */
	public class CreateQRcodeDemo extends Activity {

	   
	    /**
	     * 将要生成二维码的内容
	     */
	    private EditText codeEdit;

	    /**
	     * 生成二维码代码
	     */
	    private Button twoCodeBtn;
	    /**
	     * 用于展示生成二维码的imageView
	     */
	    private ImageView codeImg;
	   
	    /**
	     * 生成一维码按钮
	     */
	    private Button oneCodeBtn;

	    /**
	     * 界面的初始化事件
	     *
	     * @param savedInstanceState Bundle对象
	     */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        initView();
	        setListener();

	    }

	    /**
	     * 用于初始化界面展示的view
	     */
	    private void initView() {
	        codeEdit = (EditText) findViewById(R.id.code_edittext);
	        twoCodeBtn = (Button) findViewById(R.id.code_btn);
	        oneCodeBtn = (Button) findViewById(R.id.btn_code);
	        codeImg = (ImageView) findViewById(R.id.code_img);
	    }

	    /**
	     * 设置生成二维码和扫描二维码的事件
	     */
	    private void setListener() {
	        twoCodeBtn.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                String str = codeEdit.getText().toString().trim();
	                Bitmap bmp = null;
	                try {
	                    if (str != null && !"".equals(str)) {
	                        bmp = QRcodeUtils.CreateTwoDCode(str);
	                    }
	                } catch (WriterException e) {
	                    e.printStackTrace();
	                }
	                if (bmp != null) {
	                    codeImg.setImageBitmap(bmp);
	                }

	            }
	        });


	        oneCodeBtn.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                String str = codeEdit.getText().toString().trim();
	                int size = str.length();
	                for (int i = 0; i < size; i++) {
	                    int c = str.charAt(i);
	                    if ((19968 <= c && c < 40623)) {
	                        Toast.makeText(CreateQRcodeDemo.this, "生成条形码的时刻不能是中文", Toast.LENGTH_SHORT).show();
	                        return;
	                    }

	                }
	                Bitmap bmp = null;
	                try {
	                    if (str != null && !"".equals(str)) {
	                        bmp =QRcodeUtils.CreateOneDCode(str);
	                    }
	                } catch (WriterException e) {
	                    e.printStackTrace();
	                }
	                if (bmp != null) {
	                    codeImg.setImageBitmap(bmp);
	                }
	            }
	        });
	    }


	}
