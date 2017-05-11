package com.example.sun0002.trainproject.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.activity.NextActivity;
import com.example.sun0002.trainproject.adapter.GoodsUpLoadAdatper;
import com.example.sun0002.trainproject.commons.ImageUtils;
import com.example.sun0002.trainproject.commons.MyFileUtils;
import com.example.sun0002.trainproject.databinding.FragmentJobExecutionBinding;
import com.example.sun0002.trainproject.model.ImageItem;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by yq895943339 on 2017/5/9.
 */

public class JobExecutionFragment extends BasePageFragment implements View.OnClickListener {
    private static final String TAG = JobExecutionFragment.class.getName();

    private Bundle bundle;
    private String title;
    private boolean isSuccess = true;
    //模式：普通=1
    public static final int MODE_DONE = 1;
    //模式：删除=2
    public static final int MODE_DELETE = 2;
    private int title_mode = MODE_DONE;
    private ArrayList<ImageItem> list = new ArrayList<>();
    private GoodsUpLoadAdatper adatper;
    private RxPermissions rxPermissions;
    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;
    private static final int CROP_CODE = 3;
    private File img;
    private boolean isSelectPic;
    private Uri imageUriData;
    private Uri photoURI;
    private int typeNum;

    public static JobExecutionFragment newInstance(Bundle bundle) {
        JobExecutionFragment fragment = new JobExecutionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        rxPermissions = new RxPermissions(getActivity());
    }

    private FragmentJobExecutionBinding binding;

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_execution, container, false);

        initData();
        return binding.getRoot();
    }

    private void initData() {
//        Log.e(TAG, "initData: -------");
        typeNum = bundle.getInt("typeNum");
        title = bundle.getString("title");
//        Log.e(TAG, "initData: " + title);
        if (title != null && title != "") {
//            Log.e(TAG, "initData: -------" + title);
            setTitle(title);
        }
//单选按钮设置图片
        binding.rbNotUnusual.setButtonDrawable(R.drawable.select_radio_b);
        binding.rbUnusual.setButtonDrawable(R.drawable.select_radio_b);
        binding.rbNotUnusual.setChecked(true);
        binding.bNfcScan.setOnClickListener(this);
        binding.tvBSave.setOnClickListener(this);
        binding.tvBUploading.setOnClickListener(this);
        binding.etDescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeScrollView();
                return false;
            }
        });
        initAdapter();
    }

    private void initAdapter() {
        binding.rvJe.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //设置默认动画（item增删动画）
        binding.rvJe.setItemAnimator(new DefaultItemAnimator());
        //设置固定大小
        binding.rvJe.setHasFixedSize(true);


        //获取缓存文件夹中的文件
        list = getFilePhoto();
        adatper = new GoodsUpLoadAdatper(getActivity(), list);
        adatper.setListener(itemClickedListener);
        binding.rvJe.setAdapter(adatper);

    }

    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: " + title);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_nfc_scan://NFC扫描

                if (isSuccess) {
                    binding.bNfcScan.setBackgroundResource(R.drawable.b_un_scan_bg);
                    binding.ivSuccessIcon.setImageResource(R.drawable.finish_job);
                    isSuccess = false;
                } else {
                    binding.bNfcScan.setBackgroundResource(R.drawable.b_confim_bg);
                    binding.ivSuccessIcon.setImageResource(R.drawable.unfinish_job);
                    isSuccess = true;
                }
                break;
            case R.id.tv_b_save://保存按钮
                Log.e(TAG, "onClick: " + "保存");
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                ((NextActivity) getActivity()).finish();
                break;
            case R.id.tv_b_uploading://上传按钮
                deleteCache();
                Log.e(TAG, "onClick: " + "上传");
                Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                ((NextActivity) getActivity()).finish();
                break;
        }
    }

    //适配器中自定义的监听事件
    private GoodsUpLoadAdatper.OnItemClickedListener itemClickedListener = new GoodsUpLoadAdatper.OnItemClickedListener() {
        @Override
        public void onAddClicked() {
            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new io.reactivex.functions.Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                chooseFromCamera();
                            } else {
                                Toast.makeText(getActivity(), "请授予相机和存储权限", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            //单击，添加图片
//            if (picWindow != null && picWindow.isShowing()){
//                picWindow.dismiss();
//            }else if (picWindow != null){
//                picWindow.show();
//            }
        }

        @Override
        public void onPhotoClicked(ImageItem photo, ImageView imageView) {
            //单机，跳转到图片展示页
//            Intent intent = new Intent(GoodsUpLoadActivity.this,GoodsUpLoadImageShowActivity.class);
//            intent.putExtra("images",photo.getBitmap());
//            intent.putExtra("width",imageView.getWidth());
//            intent.putExtra("height",imageView.getHeight());
//            startActivity(intent);
        }

        @Override
        public void onLongClicked(int position) {
            Log.e(TAG, "onLongClicked: " + position);
            ArrayList<ImageItem> del_list = adatper.getList();
            int num = del_list.size();
//                if (del_list.get(position).isCheck()){
//                    Log.e(TAG, "onLongClicked: "+del_list.get(position).isCheck() );
//                    //删除缓存文件夹中的文件
            MyFileUtils.delFile(typeNum,del_list.get(position).getImagePath());
            del_list.remove(position);
            Log.e(TAG, "onLongClicked: " + del_list.size());
//                }

            list = del_list;
            Log.e(TAG, "onLongClicked: " + list.size());
            adatper.notifyData();
            //长按，执行删除相关
            //模式改为可删除模式
//            title_mode = MODE_DELETE;
//            //删除的tv可见
//            tv_goods_delete.setVisibility(View.VISIBLE);
        }
    };

    /**
     * 拍照选择图片
     */
    private void chooseFromCamera() {
        //构建隐式Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //调用系统相机
        startActivityForResult(intent, CAMERA_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("HandleFragment", "requestCode:" + requestCode);
        Uri uri;
        switch (requestCode) {
            case CAMERA_CODE:
                //用户点击了取消
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        //获得拍的照片
                        Bitmap bm = extras.getParcelable("data");
                        //将Bitmap转化为uri
                        uri = saveBitmap(bm, "temp");
                        //启动图像裁剪
                        startImageZoom(uri);
//                        UCrop.of(uri,destinationUri)
//                                .withAspectRatio(16, 9)
//                                .withMaxResultSize(maxWidth, maxHeight)
//                                .start(getActivity());
                    }
                }
                break;
            case CROP_CODE:
                if (data == null) {
                    return;
                } else {
//                    final Uri resultUri = UCrop.getOutput(data);
//                    //设置裁剪完成后的图片显示
//                    imgPic.setImageURI(resultUri);
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        //获取到裁剪后的图像
                        Bitmap bm = extras.getParcelable("data");
                        //需求：裁剪完成后，把图片保存为bitmap，并且保存到SD中，并且展示出来
                        //文件名：就是用系统当前时间，不重名
                        Uri uri1 = saveBitmap(bm, "temp");
                        String fileName = String.valueOf(System.currentTimeMillis());
                        Log.e(TAG, "onActivityResult: " + uri1);
                        //拿到bitmap（通过ImageUtils）
                        Bitmap bitmap = ImageUtils.readDownsampledImage(uri1.getPath(), 1080, 1920);
                        Log.e(TAG, "onActivityResult: " + bitmap);
                        //保存到SD中
                        MyFileUtils.saveBitmap(bitmap, typeNum,fileName);
                        //展示出来
                        ImageItem photo = new ImageItem();
                        photo.setImagePath(fileName + ".JPEG");
                        photo.setBitmap(bitmap);
                        adatper.add(photo);
                        adatper.notifyData();
//
                    }
                }
                break;
            default:
                break;
        }
    }

    public String getAbsolutePath(final Context context,
                                  final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA},
                    null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(
                            MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 将Bitmap写入SD卡中的一个文件中,并返回写入文件的Uri
     *
     * @param bm
     * @param dirPath
     * @return
     */
    private Uri saveBitmap(Bitmap bm, String dirPath) {
        //新建文件夹用于存放裁剪后的图片
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/" + dirPath);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }

        int i = 0;
        i++;
        //新建文件存储裁剪后的图片
        img = new File(tmpDir.getAbsolutePath() + "/avator" + i + ".png");
        Log.e(TAG, "saveBitmap: " + img);
        try {
            //打开文件输出流
            FileOutputStream fos = new FileOutputStream(img);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            //刷新输出流
            fos.flush();
            //关闭输出流
            fos.close();
            //返回File类型的Uri
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 通过Uri传递图像信息以供裁剪
     *
     * @param uri
     */
    private void startImageZoom(Uri uri) {
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        // TODO: 2017/3/14 修改android 7.0 无法裁剪
        Log.e(TAG, "startImageZoom: " + img);

        photoURI = FileProvider.getUriForFile(getContext(), "com.example.sun0002.trainproject.fileprovider", img);
        Log.e(TAG, "startImageZoom: " + photoURI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(photoURI, "image/*");
        //设置数据uri和类型为图片类型
//        intent.setDataAndType(uri, "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出图片的宽高均为150
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        //裁剪之后的数据是通过Intent返回
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_CODE);
    }

    //删除缓存（删除缓存文件夹中的文件）
    private void deleteCache() {
        Log.e(TAG, "deleteCache: ................" );
        for (int i = 0; i < adatper.getList().size(); i++) {
            MyFileUtils.delFile(typeNum,adatper.getList().get(i).getImagePath());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        deleteCache();
    }

    //获取缓存文件夹中的文件
    public ArrayList<ImageItem> getFilePhoto() {
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        //拿到所有图片文件
        File[] files = new File(MyFileUtils.SD_PATH+typeNum+File.separator).listFiles();
        Log.e(TAG, "getFilePhoto: "+files );

        if (files != null) {
            Log.e(TAG, "getFilePhoto: "+files.length);
            Log.e(TAG, "getFilePhoto: "+files.toString() );
            for (File file : files) {
                //解码file拿到bitmap
                Bitmap bitmap =
                        BitmapFactory.decodeFile(MyFileUtils.SD_PATH + typeNum+File.separator+file.getName());
                ImageItem item = new ImageItem();
                item.setImagePath(file.getName());
                item.setBitmap(bitmap);
                imageItems.add(item);
            }
        }
        return imageItems;
    }

    /**
     * 使ScrollView指向底部
     */
    private void changeScrollView() {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                binding.nsJe.scrollTo(0, binding.nsJe.getHeight());
            }
        }, 300);
    }
}

