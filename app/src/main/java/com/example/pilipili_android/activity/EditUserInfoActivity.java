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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pilipili_android.R;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.constant.UrlConstant;
import com.example.pilipili_android.databinding.ActivityEditUserInfoBinding;
import com.example.pilipili_android.util.UCropUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

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

public class EditUserInfoActivity extends AppCompatActivity {

    @BindView(R.id.avatar_img)
    QMUIRadiusImageView avatarImg;
    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.gender_tv)
    TextView genderTv;
    @BindView(R.id.sign_tv)
    TextView signTv;
    @BindView(R.id.uid_tv)
    TextView uidTv;
    @BindView(R.id.relativelayout_top)
    RelativeLayout relativelayoutTop;

    private ActivityEditUserInfoBinding activityEditUserInfoBinding;
    private String signOrigin;//指sign全文原文
    private String signEdit;//指sign单行显示
    private int UID;
    private boolean gender;
    private String avatarUrl = "";
    private String username;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_CHOOSE_AVATAR = 666;
    private static final int UCROP_CODE_CHOOSE_AVATAR = 888;
    private static final int EDIT_USERNAME = 233;
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private static final String AVATAR = "996";
    private static final String GENDER = "007";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditUserInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user_info);
        activityEditUserInfoBinding.setUserViewModel(new ViewModelProvider(this).get(UserViewModel.class));
        ButterKnife.bind(this);

        Intent intent = getIntent();
        signOrigin = intent.getStringExtra("signOrigin");
        if (intent.getBooleanExtra("hasSign", false)) {
            signEdit = intent.getStringExtra("signEdit");
        } else {
            signOrigin = "";
            signEdit = "介绍一下自己吧~";
        }

        UID = UserBaseDetail.getUID(this);
        gender = UserBaseDetail.getGenderBoolean(this);
        username = UserBaseDetail.getUsername(this);

        if (UserBaseDetail.isAvatarDefault(this)) {
            avatarImg.setImageDrawable(getDrawable(DefaultConstant.AVATAR_IMAGE_DEFAULT));
        } else {
            Glide.with(this).load(UserBaseDetail.getAvatarPath(this))
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(avatarImg);
        }
        signTv.setText(signEdit);
        if (!gender) {
            genderTv.setText("男");
        } else {
            genderTv.setText("女");
        }
        usernameTv.setText(username);
        uidTv.setText(UID + "");

        activityEditUserInfoBinding.getUserViewModel().getSpaceAvatarUrl().observe(this, url -> {
            avatarUrl = url;
            Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).into(avatarImg);
        });

        activityEditUserInfoBinding.getUserViewModel().getIsSetGenderSuccess().observe(this, gender -> {
            if(gender) {
                genderTv.setText("女");
                this.gender = true;
            } else {
                genderTv.setText("男");
                this.gender = false;
            }
        });

        activityEditUserInfoBinding.getUserViewModel().getNewUsername().observe(this, username -> {
            usernameTv.setText(username);
            this.username = username;
        });

        activityEditUserInfoBinding.getUserViewModel().getNewSign().observe(this, sign -> {
            signOrigin = sign;
            if(signOrigin.equals("")) {
                signEdit = "介绍一下自己吧~";
            } else {
                signTv.setText(signOrigin);
                signEdit = signTv.getText().toString().substring(0, signTv.getLayout().getLineEnd(0));
            }
            signTv.setText(signEdit);
        });
    }

    @OnClick(R.id.button_back)
    public void onBackClicked() {
        Intent intent = new Intent();
        intent.putExtra("username", username);
        intent.putExtra("gender", gender);
        intent.putExtra("sign", signOrigin);
        intent.putExtra("avatarUrl", avatarUrl);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("username", username);
        intent.putExtra("gender", gender);
        intent.putExtra("avatarUrl", avatarUrl);
        intent.putExtra("sign", signOrigin);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @OnClick(R.id.avatar)
    public void onAvatarClicked() {
        showBottomDialog(AVATAR);
    }

    @OnClick(R.id.username)
    public void onUsernameClicked() {
        Intent intent = new Intent(EditUserInfoActivity.this, EditUsernameActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @OnClick(R.id.gender)
    public void onGenderClicked() {
        showBottomDialog(GENDER);
    }

    @OnClick(R.id.sign)
    public void onSignClicked() {
        Intent intent = new Intent(EditUserInfoActivity.this, EditSignActivity.class);
        intent.putExtra("sign", signOrigin);
        startActivity(intent);
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

    private void showBottomDialog(String what) {
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        View view = View.inflate(this, R.layout.two_and_cancel_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        if (window != null) {
            //设置弹出位置
            window.setGravity(Gravity.BOTTOM);
            //设置弹出动画
            window.setWindowAnimations(R.style.main_menu_animStyle);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCancelable(true);
        dialog.show();

        TextView cancelTv = view.findViewById(R.id.cancel);
        cancelTv.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        if (what.equals(AVATAR)) {
            TextView selectTv = view.findViewById(R.id.tv1);
            TextView defaultTv = view.findViewById(R.id.tv2);
            selectTv.setText("从相册选择");
            defaultTv.setText("设为默认");
            selectTv.setOnClickListener(view1 -> {
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
            defaultTv.setOnClickListener(view1 -> {
                Bitmap img = ((BitmapDrawable) Objects.requireNonNull(getDrawable(DefaultConstant.AVATAR_IMAGE_DEFAULT))).getBitmap();
                File file = new File(CROP_CACHE);
                if(!file.exists()) //noinspection ResultOfMethodCallIgnored
                    file.mkdir();
                String fileName = CROP_CACHE + System.currentTimeMillis() + ".png";
                try{
                    OutputStream outputStream = new FileOutputStream(fileName);
                    img.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                    activityEditUserInfoBinding.getUserViewModel().uploadUserAvatar(fileName);
                }catch(Exception e){
                    e.printStackTrace();
                }
                dialog.dismiss();
            });
        } else if (what.equals(GENDER)) {
            TextView boyTv = view.findViewById(R.id.tv1);
            TextView girlTv = view.findViewById(R.id.tv2);
            boyTv.setText("男");
            girlTv.setText("女");
            boyTv.setOnClickListener(view1 -> {
                dialog.dismiss();
                activityEditUserInfoBinding.getUserViewModel().setGender(false);
            });
            girlTv.setOnClickListener(view1 -> {
                dialog.dismiss();
                activityEditUserInfoBinding.getUserViewModel().setGender(true);
            });
        }

    }

    private void openAlbum() {
        Matisse.from(this).choose(MimeType.ofImage()).showSingleMediaType(true).capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.pilipili.fuckthisshit"))
                .countable(false).maxSelectable(1).restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.8f).theme(R.style.Matisse_Dracula).forResult(REQUEST_CODE_CHOOSE_AVATAR);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "啊啦~被残忍拒绝了呢~", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                openAlbum();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_AVATAR && resultCode == RESULT_OK) {
            List<String> pathList = Matisse.obtainPathResult(data);
            File dir = new File(UrlConstant.COMPRESS_CACHE);
            if (!dir.exists()) //noinspection ResultOfMethodCallIgnored
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
                            int height = relativelayoutTop.getWidth();
                            int width = relativelayoutTop.getWidth();
                            UCropUtil.startUCrop(EditUserInfoActivity.this, file.getPath(), UCROP_CODE_CHOOSE_AVATAR, width, height);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    }).launch();
        } else if (requestCode == UCROP_CODE_CHOOSE_AVATAR) {
            final Uri croppedUri;
            if (data != null) {
                croppedUri = UCrop.getOutput(data);
                try {
                    if (croppedUri != null) {
                            activityEditUserInfoBinding.getUserViewModel().uploadUserAvatar(croppedUri.getPath());
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
