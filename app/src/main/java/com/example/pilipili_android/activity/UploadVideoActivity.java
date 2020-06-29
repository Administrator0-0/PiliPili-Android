package com.example.pilipili_android.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pilipili_android.R;
import com.example.pilipili_android.bean.netbean.UploadVideoOrCoverReturn;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.constant.UrlConstant;
import com.example.pilipili_android.fragment.FragmentMsg;
import com.example.pilipili_android.util.AliyunOSSUtil;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.util.TimeUtil;
import com.example.pilipili_android.util.UCropUtil;
import com.example.pilipili_android.view_model.VideoViewModel;
import com.example.pilipili_android.widget.CommonDialog;
import com.example.pilipili_android.widget.PiliPiliColorTextView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.example.pilipili_android.constant.UrlConstant.CROP_CACHE;
import static com.example.pilipili_android.constant.UrlConstant.PILIPILI_BUCKET;
import static com.example.pilipili_android.util.AliyunOSSUtil.getOSS;

public class UploadVideoActivity extends AppCompatActivity {

    @BindView(R.id.is_uploading)
    TextView isUploadingTv;
    @BindView(R.id.is_uploading_img)
    ImageView isUploadingImg;
    @BindView(R.id.image_cover)
    ImageView frontCoverImg;
    @BindView(R.id.select_part)
    RelativeLayout selectPart;
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.edit_title)
    EditText editTitle;
    @BindView(R.id.number_of_title)
    TextView numberOfTitle;
    @BindView(R.id.title_intro)
    TextView titleIntro;
    @BindView(R.id.edit_intro)
    EditText editIntro;
    @BindView(R.id.number_of_intro)
    TextView numberOfIntro;
    @BindView(R.id.part_title)
    TextView partTitle;
    @BindView(R.id.select_part_tv)
    TextView selectPartTv;
    @BindView(R.id.publish)
    QMUIRoundButton publish;

    private static final int REQUEST_CODE_CHOOSE_COVER = 10086;
    private static final int UCROP_CODE_CHOOSE_COVER = 23333;

    private int type = -1;
    private File video;
    private String coverPath;
    private VideoViewModel videoViewModel;
    private boolean isUploadFinish = false;
    private boolean isUploadError = false;
    private boolean isDialogAppear = false;
    private boolean isUploadVideoCoverFinish = false;
    private OSSAsyncTask uploadVideoToBucketTask;
    private OSSAsyncTask uploadVideoCoverToBucketTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        ButterKnife.bind(this);
        String originPath = getIntent().getStringExtra("path");
        assert originPath != null;
        video = new File(originPath);

        uploadVideo();

        setAsteriskRed(partTitle);
        setAsteriskRed(titleTitle);
        numberOfTitle.setText("80");
        numberOfIntro.setText("233");
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                numberOfTitle.setText(80 - editTitle.getText().toString().length() + "");
            }
        });
        editIntro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                numberOfIntro.setText(233 - editIntro.getText().toString().length() + "");
            }
        });

        Glide.with(this)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(500000)
                                .centerCrop()
                                .error(R.drawable.ic_movie_pay_order_error)
                ).asBitmap().override(412, 258).load(originPath).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        File file = new File(CROP_CACHE);
                        if(!file.exists()) //noinspection ResultOfMethodCallIgnored
                            file.mkdir();
                        String fileName = System.currentTimeMillis() + ".png";
                        coverPath = CROP_CACHE + File.separator + fileName;
                        try{
                            OutputStream outputStream = new FileOutputStream(coverPath);
                            resource.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                            outputStream.close();
                            videoViewModel.uploadVideoCover(fileName);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        videoViewModel.getUploadVideoKeyInfo().observe(this, uploadVideoOrCoverReturn -> {
            UploadVideoOrCoverReturn.DataBean dataBean = uploadVideoOrCoverReturn.getData();
            uploadVideoToBucketTask = uploadVideoToBucket(getApplicationContext(), dataBean.getGuest_key(),
                    dataBean.getGuest_secret(), dataBean.getSecurity_token(),
                    dataBean.getFile(), video.getAbsolutePath());
        });

        videoViewModel.getUploadVideoCoverKeyInfo().observe(this, uploadVideoOrCoverReturn -> {
            UploadVideoOrCoverReturn.DataBean dataBean = uploadVideoOrCoverReturn.getData();
            uploadVideoCoverToBucketTask = uploadVideoCoverToBucket(getApplicationContext(), dataBean.getGuest_key(),
                    dataBean.getGuest_secret(), dataBean.getSecurity_token(),
                    dataBean.getFile(), coverPath);
        });
    }

    public OSSAsyncTask uploadVideoToBucket(Context context, String accessKey, String secretKey, String securityToken, String fileName, String path) {
        OSS oss = getOSS(context, accessKey, secretKey, securityToken);

        PutObjectRequest put = new PutObjectRequest(PILIPILI_BUCKET, fileName, path);

        put.setProgressCallback((request, currentSize, totalSize) -> {
            UploadVideoActivity.this.runOnUiThread(() -> {
                double f = (100.0 * currentSize) / totalSize;
                String s = f + "";
                isUploadingTv.setText("已上传" + s.substring(0, s.indexOf(".") + 2) + "%");
            });
        });

        return oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                UploadVideoActivity.this.runOnUiThread(() -> {
                    isUploadError = false;
                    isUploadingTv.setText("上传完成");
                    isUploadingImg.setVisibility(View.GONE);
                    isUploadFinish = true;
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    UploadVideoActivity.this.runOnUiThread(() -> {
                        CommonDialog commonDialog = new CommonDialog(UploadVideoActivity.this);
                        commonDialog.setCancelable(false);
                        commonDialog.setTitle("哦豁~").setNegative("不要").setPositive("要")
                                .setMessage("视频上传出错了哦~\n(请确保网络通畅哦)\n需要重新上传吗")
                                .setOnCancelDialogClickListener(() -> {
                                    isUploadingImg.setVisibility(View.VISIBLE);
                                    isUploadingImg.setImageResource(R.drawable.cm8);
                                    isUploadingTv.setText("点击右侧重新上传");
                                    isUploadError = true;
                                    commonDialog.dismiss();
                                })
                                .setOnConfirmDialogClickListener(() -> {
                                    uploadVideo();
                                    commonDialog.dismiss();
                                }).setImageResId(R.drawable.bh3).show();
                    });
                }
                if (serviceException != null) {
                    //服务器异常
                    UploadVideoActivity.this.runOnUiThread(() -> {
                        CommonDialog commonDialog = new CommonDialog(UploadVideoActivity.this);
                        commonDialog.setCancelable(false);
                        commonDialog.setTitle("哦豁~").setNegative("不要").setPositive("要")
                                .setMessage("视频上传出错了哦~\n(也许是服务器炸了...)\n需要重新上传吗")
                                .setOnCancelDialogClickListener(() -> {
                                    isUploadingImg.setVisibility(View.VISIBLE);
                                    isUploadingImg.setImageResource(R.drawable.cm8);
                                    isUploadingTv.setText("点击右侧重新上传");
                                    isUploadError = true;
                                    commonDialog.dismiss();
                                })
                                .setOnConfirmDialogClickListener(() -> {
                                    uploadVideo();
                                    commonDialog.dismiss();
                                }).setImageResId(R.drawable.bh3).show();
                    });
                }
            }
        });
    }

    public OSSAsyncTask uploadVideoCoverToBucket(Context context, String accessKey, String secretKey, String securityToken, String fileName, String path) {
        OSS oss = getOSS(context, accessKey, secretKey, securityToken);
        PutObjectRequest put = new PutObjectRequest(PILIPILI_BUCKET, fileName, path);

        return oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                UploadVideoActivity.this.runOnUiThread(() -> {
                    Glide.with(UploadVideoActivity.this).load(coverPath).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(frontCoverImg);
                    isUploadVideoCoverFinish = true;
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                UploadVideoActivity.this.runOnUiThread(() -> {
                    Toast.makeText(UploadVideoActivity.this, "封面上传异常", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void setAsteriskRed(TextView textView){
        SpannableString spannableString = new SpannableString(textView.getText());
        int end = textView.getText().toString().indexOf("*");
        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.red)), end, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setCancelable(false);
        commonDialog.setTitle("取消发布").setNegative("不想发了").setPositive("我再想想").setMessage("真的要取消发布视频吗？")
                .setOnCancelDialogClickListener(() -> {
                    commonDialog.dismiss();
                    if(!isUploadFinish && !isUploadError) uploadVideoToBucketTask.cancel();
                    if(!isUploadVideoCoverFinish && !isUploadError) uploadVideoCoverToBucketTask.cancel();
                    videoViewModel.cancelUploadVideo(isUploadFinish || isUploadVideoCoverFinish);
                    UploadVideoActivity.this.finish();
                    EventBus.getDefault().post(FragmentMsg.getInstance("UploadVideoActivity", "openAlbum"));
                })
                .setOnConfirmDialogClickListener(commonDialog::dismiss).setImageResId(R.drawable.d36).show();
    }

    @Override
    public void onBackPressed() {
        if(!isDialogAppear) {
            CommonDialog commonDialog = new CommonDialog(this);
            commonDialog.setCancelable(false);
            commonDialog.setTitle("取消发布").setNegative("不想发了").setPositive("我再想想").setMessage("真的要取消发布视频吗？")
                    .setOnCancelDialogClickListener(() -> {
                        commonDialog.dismiss();
                        if(!isUploadFinish && !isUploadError) uploadVideoToBucketTask.cancel();
                        if(!isUploadVideoCoverFinish && !isUploadError) uploadVideoCoverToBucketTask.cancel();
                        videoViewModel.cancelUploadVideo(isUploadFinish || isUploadVideoCoverFinish);
                        super.onBackPressed();
                        EventBus.getDefault().post(FragmentMsg.getInstance("UploadVideoActivity", "openAlbum"));
                    })
                    .setOnConfirmDialogClickListener(commonDialog::dismiss).setImageResId(R.drawable.d36).show();
        }
    }

    @OnClick(R.id.publish)
    public void onPublishClicked() {
        if(!isUploadFinish) {
            Toast.makeText(this, "视频还未上传完哦~", Toast.LENGTH_SHORT).show();
        } else if (!isUploadVideoCoverFinish) {
            Toast.makeText(this, "封面还未上传完哦~", Toast.LENGTH_SHORT).show();
        } else if(type < 0) {
            Toast.makeText(this, "必须要选择分区哦~", Toast.LENGTH_SHORT).show();
        } else if(editTitle.getText().toString().equals("")) {
            Toast.makeText(this, "必须要填写标题哦~", Toast.LENGTH_SHORT).show();
        } else {
            publish.setEnabled(false);
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(video.getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(mediaPlayer1 -> {
                    videoViewModel.confirmUploadVideo(editTitle.getText().toString(), editIntro.getText().toString(),
                            type, TimeUtil.getTimeStrFromMilliSeconds(mediaPlayer1.getDuration()));
                    mediaPlayer.release();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @OnClick(R.id.button_setcover)
    public void onButtonSetCoverClicked() {
        openAlbum();
    }

    private void openAlbum() {
        Matisse.from(this).choose(MimeType.ofImage()).showSingleMediaType(true).capture(true)
                .captureStrategy(new CaptureStrategy(true,"com.pilipili.fuckthisshit"))
                .countable(false).maxSelectable(1).restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.8f).theme(R.style.Matisse_Dracula).forResult(REQUEST_CODE_CHOOSE_COVER);
    }

    @OnClick(R.id.select_part)
    public void onSelectPartClicked() {
        showBottomDialog();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        View view = View.inflate(this, R.layout.select_part_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        if (window != null) {
            //设置弹出位置
            window.setGravity(Gravity.BOTTOM);
            //设置弹出动画
            window.setWindowAnimations(R.style.main_menu_animStyle);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.show();
        isDialogAppear = true;

        LinearLayout color = view.findViewById(R.id.color);
        color.setVisibility(View.GONE);
        color.setOnClickListener(view1 -> {
            selectPartTv.setText("颜色区");
            selectPartTv.setTextColor(getColor(R.color.yellow_50));
            type = DefaultConstant.TYPE_COLOR;
            isDialogAppear = false;
            dialog.dismiss();
        });
        View line = view.findViewById(R.id.line1);
        line.setVisibility(View.GONE);

        LinearLayout all = view.findViewById(R.id.all);
        all.setOnClickListener(view1 -> {
            selectPartTv.setText("综合区");
            selectPartTv.setTextColor(getColor(R.color.black));
            type = DefaultConstant.TYPE_ALL;
            isDialogAppear = false;
            dialog.dismiss();
        });

        PiliPiliColorTextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(view1 -> {
            isDialogAppear = false;
            dialog.dismiss();
        });

        cancel.setOnLongClickListener(view12 -> {
            color.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            return false;
        });
    }

    private void uploadVideo() {
        publish.setEnabled(true);
        isUploadError = false;
        isUploadFinish = false;
        isUploadingImg.setVisibility(View.VISIBLE);
        isUploadingImg.setImageResource(R.drawable.ct6);
        isUploadingTv.setText("上传部署中...");
        videoViewModel.uploadVideo(video.getName());
    }

    @OnClick(R.id.is_uploading_img)
    public void onUploadImgClicked() {
        if(!isUploadError)
            return;
        uploadVideo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE_COVER && resultCode == RESULT_OK) {
            assert data != null;
            List<String> pathList = Matisse.obtainPathResult(data);
            File dir = new File(UrlConstant.COMPRESS_CACHE);
            if(!dir.exists()) //noinspection ResultOfMethodCallIgnored
                dir.mkdir();
            Luban.with(this)
                    .load(pathList.get(0))
                    .ignoreBy(300)
                    .setTargetDir(UrlConstant.COMPRESS_CACHE)
                    .filter(path1 -> !(TextUtils.isEmpty(path1) || path1.toLowerCase().endsWith(".gif")))
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }
                        @Override
                        public void onSuccess(File file) {
                            UCropUtil.startUCrop(UploadVideoActivity.this, file.getPath(), UCROP_CODE_CHOOSE_COVER, 412, 258);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    }).launch();
        } else if (requestCode == UCROP_CODE_CHOOSE_COVER) {
            final Uri croppedUri;
            if (data != null) {
                croppedUri = UCrop.getOutput(data);
                try {
                    if (croppedUri != null) {
                        coverPath = croppedUri.getPath();
                        assert coverPath != null;
                        File file = new File(coverPath);
                        videoViewModel.uploadVideoCover(file.getName());
                        isUploadVideoCoverFinish = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                openAlbum();
                overridePendingTransition(0, 0);
            }
        }
    }
}