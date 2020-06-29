package com.example.pilipili_android.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.PayActivity;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.example.pilipili_android.widget.CommonDialog;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditUsernameFragment extends Fragment {

    public EditUsernameFragment() {

    }

    @BindView(R.id.name_et)
    EditText nameEt;

    private UserViewModel userViewModel;
    private String username;
    private Unbinder unbinder;

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_username, container, false);
        userViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(UserViewModel.class);
        unbinder = ButterKnife.bind(this, view);
        nameEt.setText(username);
        nameEt.setSelection(username.length());
        return view;
    }

    @OnClick(R.id.button_back)
    public void onButtonBackClicked() {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @OnClick(R.id.save)
    public void onSaveClicked() {
        String newName = nameEt.getText().toString();
        if(newName.equals(username)) {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else if (!EncryptUtil.isUsernameValid(newName)) {
            Toast.makeText(getContext(), "昵称无效哦~", Toast.LENGTH_SHORT).show();
        } else {
            if(UserBaseDetail.getCoin(getContext()) < 6) {
                Toast.makeText(getContext(), "亲，P币不够了哦~", Toast.LENGTH_SHORT).show();
            } else {
                CommonDialog commonDialog = new CommonDialog(getContext());
                commonDialog.setTitle("修改昵称").setSingle(false).setMessage("修改昵称需要6个P币哦~\n你确定要重新做人吗")
                        .setPositive("确定").setNegative("取消")
                        .setOnConfirmDialogClickListener(() -> {
                            commonDialog.dismiss();
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
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
        Intent intent = new Intent(getActivity(), PayActivity.class);
        startActivity(intent);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
