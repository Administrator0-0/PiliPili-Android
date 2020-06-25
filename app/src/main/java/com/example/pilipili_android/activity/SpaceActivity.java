package com.example.pilipili_android.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.pilipili_android.databinding.ActivitySpaceBinding;
import com.example.pilipili_android.fragment.SaveImageFragment;
import com.example.pilipili_android.util.UCropUtil;
import com.example.pilipili_android.util.UriUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
//        startActivityForResult(intent, GALLERY_CODE);
        Matisse.from(this)
                //选择视频和图片
//                .choose(MimeType.ofAll())
                //选择图片
                .choose(MimeType.ofImage())
                //选择视频
//                .choose(MimeType.ofVideo())
                //自定义选择选择的类型
//                .choose(MimeType.of(MimeType.JPEG,MimeType.AVI))
                //是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                .showSingleMediaType(true)
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .capture(true)
                .captureStrategy(new CaptureStrategy(true,"com.pilipili.fuckthisshit"))
                //有序选择图片 123456...
                .countable(true)
                //最大选择数量为9
                .maxSelectable(1)
                //选择方向
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //界面中缩略图的质量
                .thumbnailScale(0.8f)
                //蓝色主题
//                .theme(R.style.Matisse_Zhihu)
                //黑色主题
                .theme(R.style.Matisse_Dracula)
                //Glide加载方式
//                .imageEngine(new GlideEngine())
                //Picasso加载方式
//                .imageEngine(new PicassoEngine())
                //请求码
                .forResult(REQUEST_CODE_CHOOSE_BG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GALLERY_CODE || requestCode == 3) {
//            String path = null;
//            Uri uri;
//            if (data == null) {
//                return;
//            }
//            uri = data.getData();
//
//
//        }
        if (requestCode == REQUEST_CODE_CHOOSE_BG || requestCode == REQUEST_CODE_CHOOSE_AVATAR) {
            //图片路径 同样视频地址也是这个
            List<Uri> pathList = Matisse.obtainResult(data);
            String path = UriUtil.getPath(this, pathList.get(0));
            if (requestCode == REQUEST_CODE_CHOOSE_BG) {
                int height = background.getHeight();
                int width = background.getWidth();
                UCropUtil.startUCrop(this, path, UCROP_CODE_CHOOSE_BG, width, height);
            } else {
                int height = background.getWidth();
                int width = background.getWidth();
                UCropUtil.startUCrop(this, path, UCROP_CODE_CHOOSE_AVATAR, width, height);
            }
        } else if (requestCode == UCROP_CODE_CHOOSE_BG || requestCode == UCROP_CODE_CHOOSE_AVATAR) {
            final Uri croppedUri;
            if (data != null) {
                croppedUri = UCrop.getOutput(data);
                try {
                    if (croppedUri != null) {
                        if (requestCode == UCROP_CODE_CHOOSE_BG) {
                            //TODO:图片尺寸过大将上传失败
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