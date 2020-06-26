package com.example.pilipili_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pilipili_android.R;
import com.example.pilipili_android.inteface.OnDialogClickListener;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.example.pilipili_android.widget.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditUsernameActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @BindView(R.id.name_et)
    EditText nameEt;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_username);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        ButterKnife.bind(this);
        username = getIntent().getStringExtra("username");
        nameEt.setText(username);
        nameEt.setSelection(username.length());
    }

    @OnClick(R.id.button_back)
    public void onButtonBackClicked() {
        finish();
    }

    @OnClick(R.id.save)
    public void onSaveClicked() {
        String newName = nameEt.getText().toString();
        if(newName.equals(username)) {
            finish();
        } else if (!EncryptUtil.isUsernameValid(newName)) {
            Toast.makeText(this, "昵称无效哦~", Toast.LENGTH_SHORT).show();
        } else {
            if(UserBaseDetail.getCoin(this) < 6) {
                Toast.makeText(this, "亲，P币不够了哦~", Toast.LENGTH_SHORT).show();
            } else {
                CommonDialog commonDialog = new CommonDialog(this);
                commonDialog.setTitle("修改昵称").setSingle(false).setMessage("修改昵称需要6个P币哦~\n你确定要重新做人吗")
                        .setPositive("确定").setNegative("取消")
                        .setOnConfirmDialogClickListener(() -> {
                            commonDialog.dismiss();
                            EditUsernameActivity.this.finish();
                            userViewModel.rename(newName);
                        })
                        .setOnCancelDialogClickListener(commonDialog::dismiss).show();
            }
        }
    }

    @OnClick(R.id.clear)
    public void onClearClicked() {
        nameEt.setText("");
    }

    @OnClick(R.id.get_coin_P)
    public void onGetCoinPClicked() {
        Intent intent = new Intent(EditUsernameActivity.this, PayActivity.class);
        startActivity(intent);
    }
}
