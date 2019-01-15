package com.fx.android.watercamerademo.Camera;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import com.fx.android.watercamerademo.ImagesStitchUtil;
import com.fx.android.watercamerademo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrdinaryHandlePicActivity extends AppCompatActivity {

    private ImageView iv_cancle, iv_commit;
    private ImageView iv_pic;
    private final  static  String PanoCameraKey="IMAGEKEY";
    private  List<String>mList=new ArrayList<>();
    private String[] strs= new String[30];//按照每2秒取一帧，那就是一分钟。


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ordinary_handle_pic);
        //dialog= DialogUtil.showDialog(OrdinaryHandlePicActivity.this,"正在生成全景图...",false,true);
        initView();
        initEvent();
    }

    @SuppressLint("NewApi")
    private void initView() {
        iv_cancle = (ImageView) findViewById(R.id.iv_cancle);
        iv_pic  = (ImageView) findViewById(R.id.iv_pic);
        iv_commit = (ImageView) findViewById(R.id.iv_commit);
        mList=getIntent().getStringArrayListExtra(PanoCameraKey);
        strs = (String[]) mList.toArray(new String[0]);
        //ImagesStitchUtil.StitchImages(strs,listener);
    }

    private ImagesStitchUtil.onStitchResultListener listener =new ImagesStitchUtil.onStitchResultListener(){
        @Override
        public void onSuccess(final Bitmap bitmap) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    if(dialog!=null&&dialog.isShowing()){
//                        DialogUtil.closeDialog(dialog);
//                        Toast.makeText(OrdinaryHandlePicActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                    }
                    Log.i("PanoCamera1",bitmap.getWidth()+","+bitmap.getHeight());
                    savePanoBitmap(bitmap);
                    iv_pic.setImageBitmap(bitmap);
                }
            });
        }

        @Override
        public void onError(String errorMsg) {
            Log.i("PanoCamera1",errorMsg);
            //Toast.makeText(OrdinaryHandlePicActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    private void initEvent() {
        iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //保存处理后的图并且回传
    public void savePanoBitmap(Bitmap bitmap) {
// 首先保存图片
        File appDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = getPhotoFilename();
        File file = new File(appDir, fileName);
        Boolean is = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            is = bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (is) {
                Toast.makeText(OrdinaryHandlePicActivity.this, file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(OrdinaryHandlePicActivity.this, "失败", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
// 通知图库更新
        MediaScannerConnection.scanFile(this,
                new String[]{file.getPath()},
                new String[]{"image/jpeg"}, null);
    }
    //文件名字
    protected static String getPhotoFilename() {
        String ts=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        return("Photo_" + ts + ".jpg");
    }

}
