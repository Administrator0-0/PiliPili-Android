package com.example.pilipili_android.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pilipili_android.R;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.constant.UrlConstant;
import com.example.pilipili_android.databinding.ActivitySpaceBinding;
import com.example.pilipili_android.fragment.SaveImageFragment;
import com.example.pilipili_android.util.UCropUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class SpaceActivity extends AppCompatActivity {

    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.gender_img)
    ImageView genderImg;
    @BindView(R.id.big_vip)
    ImageView bigVip;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.sign)
    TextView signTv;

    @BindView(R.id.background)
    ImageView background;

    @BindView(R.id.avatar)
    QMUIRadiusImageView avatar;
    @BindView(R.id.uid)
    TextView uid;

    private int UID;
    private boolean isDetail = false;
    private String signSingle = "";
    private String signOrigin = "";
    private static final int REQUEST_CODE_CHOOSE_BG = 10086;
    private static final int REQUEST_CODE_CHOOSE_AVATAR = 10010;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int UCROP_CODE_CHOOSE_BG = 2333;
    private static final int UCROP_CODE_CHOOSE_AVATAR = 2334;
    private static final int GALLERY_CODE = 2;
    ActivitySpaceBinding activitySpaceBinding;
    FragmentManager fragmentManager;
    SaveImageFragment saveImageFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initBind();
        initView();
        initFragment();

        activitySpaceBinding.getUserViewModel().getSpaceActivityBean().observe(this, spaceActivityBean -> {
            activitySpaceBinding.setSpaceActivityBean(spaceActivityBean);
            if (spaceActivityBean.isGender()) {
                genderImg.setImageResource(R.drawable.cyx);
            } else {
                genderImg.setImageResource(R.drawable.cyz);
            }

            if (spaceActivityBean.isVip()) {
                bigVip.setImageResource(R.drawable.bxrb);
                usernameTv.setTextColor(getColor(R.color.colorPrimary));
            } else {
                bigVip.setVisibility(View.GONE);
            }
            signOrigin = spaceActivityBean.getSign();
            if(signOrigin.equals("")) {
                signOrigin = "这个人很神秘，什么都没有留下";
                signSingle = "这个人很神秘，什么都没有留下";
            } else {
                signTv.setText(signOrigin);
                signSingle = signTv.getText().toString().substring(0, signTv.getLayout().getLineEnd(0));
            }
            signTv.setText(signSingle);
            detail.setText("详情");
            uid.setVisibility(View.GONE);
        });

        activitySpaceBinding.getUserViewModel().getSpaceBackgroundUrl().observe(this, spaceBackgroundUrl -> {
            if (spaceBackgroundUrl.equals("")) {
                background.setImageResource(DefaultConstant.BACKGROUND_IMAGE_DEFAULT);
            } else {
                Glide.with(this).load(spaceBackgroundUrl).into(background);
            }
        });

        activitySpaceBinding.getUserViewModel().getSpaceAvatarUrl().observe(this, spaceAvatarUrl -> {
            if (spaceAvatarUrl.equals("")) {
                avatar.setImageDrawable(getDrawable(DefaultConstant.AVATAR_IMAGE_DEFAULT));
            } else {
                Glide.with(this).load(spaceAvatarUrl).into(avatar);
            }
        });

    }

    private void initData() {
        Intent intent = getIntent();
        UID = intent.getIntExtra("UID", -1);
    }

    private void initBind() {
        activitySpaceBinding = DataBindingUtil.setContentView(this, R.layout.activity_space);
        activitySpaceBinding.setUserViewModel(new ViewModelProvider(this).get(UserViewModel.class));
        ButterKnife.bind(this);
    }

    private void initView() {
        activitySpaceBinding.getUserViewModel().getUserSpaceData(UID);
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        saveImageFragment = new SaveImageFragment();
    }

    @OnClick(R.id.change_bg)
    public void onChangeBackgroundClicked() {
        showBottomDialog();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        View view = View.inflate(this, R.layout.user_space_bg_dialog, null);
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

        TextView defaultBackground = view.findViewById(R.id.default_bg);
        defaultBackground.setOnClickListener(view1 -> {
            background.setImageResource(DefaultConstant.BACKGROUND_IMAGE_DEFAULT);
            dialog.dismiss();
        });

        TextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        QMUIRoundButton selfDef = view.findViewById(R.id.self_def);
        selfDef.setOnClickListener(view1 -> {
            dialog.dismiss();
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                openAlbum();
            }
        });
    }

    //获取权限的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if(grantResults.length > 0) {
                for(int result : grantResults) {
                    if(result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "啊啦~被残忍拒绝了呢~", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                openAlbum();
            }
        }
    }

    //启动相册
    private void openAlbum() {
        Matisse.from(this).choose(MimeType.ofImage()).showSingleMediaType(true).maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).thumbnailScale(0.8f)
                .theme(R.style.Matisse_Dracula).forResult(REQUEST_CODE_CHOOSE_BG);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CODE_CHOOSE_BG || requestCode == REQUEST_CODE_CHOOSE_AVATAR) && resultCode == RESULT_OK) {
            //图片路径 同样视频地址也是这个
            List<String> pathList = Matisse.obtainPathResult(data);
           // String path = UriUtil.getPath(this, pathList.get(0));
            File dir = new File(UrlConstant.COMPRESS_CACHE);
            if(!dir.exists()) dir.mkdir();
            Luban.with(this)
                    .load(pathList.get(0))
                    .ignoreBy(300)
                    .setTargetDir(UrlConstant.COMPRESS_CACHE)
                    .filter(path1 -> !(TextUtils.isEmpty(path1) || path1.toLowerCase().endsWith(".gif")))
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            if (requestCode == REQUEST_CODE_CHOOSE_BG) {
                                int height = background.getHeight();
                                int width = background.getWidth();
                                UCropUtil.startUCrop(SpaceActivity.this, file.getPath(), UCROP_CODE_CHOOSE_BG, width, height);
                            } else {
                                int height = background.getWidth();
                                int width = background.getWidth();
                                UCropUtil.startUCrop(SpaceActivity.this, file.getPath(), UCROP_CODE_CHOOSE_AVATAR, width, height);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                        }
                    }).launch();

        } else if (requestCode == UCROP_CODE_CHOOSE_BG || requestCode == UCROP_CODE_CHOOSE_AVATAR) {
            final Uri croppedUri;
            if (data != null) {
                croppedUri = UCrop.getOutput(data);
                try {
                    if (croppedUri != null) {
                        if (requestCode == UCROP_CODE_CHOOSE_BG) {
                            activitySpaceBinding.getUserViewModel().uploadUserBackground(croppedUri.getPath());
                        } else {
                            activitySpaceBinding.getUserViewModel().uploadUserAvatar(croppedUri.getPath());
                        }
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

    @OnClick(R.id.avatar)
    public void onAvatarClicked() {
        saveImageFragment.setAvatarPath(UserBaseDetail.getAvatarPath(this));
        fragmentManager.beginTransaction().add(R.id.save_avatar_box, saveImageFragment).commit();
    }

    @OnClick(R.id.button_return_inspace)
    public void onBackClicked() {
        this.finish();
    }

    @OnClick(R.id.edit)
    public void onEditClicked() {

    }

    @OnClick(R.id.relativelayout_6)
    public void onRelativelayout6Clicked() {
        if (!isDetail) {
            signTv.setText(signOrigin);
            uid.setVisibility(View.VISIBLE);
            detail.setText("收起");
        } else {
            signTv.setText(signSingle);
            uid.setVisibility(View.GONE);
            detail.setText("详情");
        }
        isDetail = !isDetail;
    }

    @OnClick(R.id.uid)
    public void onUidClicked() {

    }
}