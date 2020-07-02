package com.example.pilipili_android.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pilipili_android.R;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.constant.UrlConstant;
import com.example.pilipili_android.databinding.ActivitySpaceBinding;
import com.example.pilipili_android.fragment.AvatarFragment;
import com.example.pilipili_android.util.UCropUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.example.pilipili_android.constant.UrlConstant.CROP_CACHE;

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
    private String signSingle = "";//指sign单行显示
    private boolean hasSign = false;
    private String signOrigin = "";//指sign展开显示
    private static final int REQUEST_CODE_CHOOSE_BG = 10086;
    private static final int REQUEST_CODE_CHOOSE_AVATAR = 10010;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int UCROP_CODE_CHOOSE_BG = 2333;
    private static final int UCROP_CODE_CHOOSE_AVATAR = 2334;
    private static final int EDIT_USER_INFO = 23333;
    ActivitySpaceBinding activitySpaceBinding;
    FragmentManager fragmentManager;
    AvatarFragment avatarFragment;


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
                hasSign = false;
            } else {
                signTv.setText(signOrigin);
                signSingle = signTv.getText().toString().substring(0, signTv.getLayout().getLineEnd(0));
                hasSign = true;
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activitySpaceBinding.getUserViewModel().getUserSpaceData(UID);
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        avatarFragment = new AvatarFragment();
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
            Bitmap img = ((BitmapDrawable) Objects.requireNonNull(getDrawable(DefaultConstant.BACKGROUND_IMAGE_DEFAULT))).getBitmap();
            File file = new File(CROP_CACHE);
            if(!file.exists()) //noinspection ResultOfMethodCallIgnored
                file.mkdir();
            String fileName = CROP_CACHE + System.currentTimeMillis() + ".png";
            try{
                OutputStream outputStream = new FileOutputStream(fileName);
                img.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
                activitySpaceBinding.getUserViewModel().uploadUserBackground(fileName);
            }catch(Exception e){
                e.printStackTrace();
            }
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
            assert data != null;
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
        } else if (requestCode == EDIT_USER_INFO || resultCode == RESULT_OK) {
            if(data != null) {
                String avatarUrl = data.getStringExtra("avatarUrl");
                assert avatarUrl != null;
                if(!avatarUrl.equals("")){
                    Glide.with(this).load(avatarUrl).diskCacheStrategy(DiskCacheStrategy.NONE).into(avatar);
                }
                usernameTv.setText(data.getStringExtra("username"));
                if(data.getBooleanExtra("gender", false)) {
                    genderImg.setImageResource(R.drawable.cyx);
                } else {
                    genderImg.setImageResource(R.drawable.cyz);
                }
                signOrigin = data.getStringExtra("sign");
                assert signOrigin != null;
                if(signOrigin.equals("")) {
                    signOrigin = "这个人很神秘，什么都没有留下";
                    signSingle = "这个人很神秘，什么都没有留下";
                    hasSign = false;
                } else {
                    signTv.setText(signOrigin);
                    signSingle = signTv.getText().toString().substring(0, signTv.getLayout().getLineEnd(0));
                    hasSign = true;
                }
                signTv.setText(signSingle);
                detail.setText("详情");
                uid.setVisibility(View.GONE);
                isDetail = false;
            }
        }
    }

    @OnClick(R.id.avatar)
    public void onAvatarClicked() {
        avatarFragment.setAvatarPath(UserBaseDetail.getAvatarPath(this));
        fragmentManager.beginTransaction().add(R.id.save_avatar_box, avatarFragment).commit();
    }

    @OnClick(R.id.button_return_inspace)
    public void onBackClicked() {
        this.finish();
    }

    @OnClick(R.id.edit)
    public void onEditClicked() {
        Intent intent = new Intent(SpaceActivity.this, EditUserInfoActivity.class);
        intent.putExtra("signOrigin", signOrigin);
        intent.putExtra("signEdit", signSingle);
        intent.putExtra("hasSign", hasSign);
        startActivityForResult(intent, EDIT_USER_INFO);
    }

    @OnClick(R.id.relativelayout_6)
    public void onDetailClicked() {
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
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", UID + "");
        // 将ClipData内容放到系统剪贴板里。
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
        }
        Toast.makeText(this, "UID已复制到剪切板", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.follower_tv)
    public void onFanClick() {
        Intent intent = new Intent(this, FanListActivity.class);
        intent.putExtra("id", UID);
        intent.putExtra("isFollow", true);
        startActivity(intent);
    }

    @OnClick(R.id.following_tv)
    public void onFollowingClick() {
        Intent intent = new Intent(this, FanListActivity.class);
        intent.putExtra("id", UID);
        intent.putExtra("isFollow", false);
        startActivity(intent);
    }
}