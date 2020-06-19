package com.example.pilipili_android.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pilipili_android.R;
import com.example.pilipili_android.bean.LoginSend;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;

public class RegisterFragment extends Fragment {

    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.linearlayout_username)
    LinearLayout linearlayoutUsername;
    @BindView(R.id.clear_image_username)
    ImageView clearImageUsername;
    @BindView(R.id.relativelayout_username)
    RelativeLayout relativelayoutUsername;
    @BindView(R.id.edit_account)
    EditText editAccount;
    @BindView(R.id.linearlayout_account)
    LinearLayout linearlayoutAccount;
    @BindView(R.id.clear_image_account)
    ImageView clearImageAccount;
    @BindView(R.id.relativelayout_account)
    RelativeLayout relativelayoutAccount;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.linearlayout_password)
    LinearLayout linearlayoutPassword;
    @BindView(R.id.clear_image_password)
    ImageView clearImagePassword;
    @BindView(R.id.relativelayout_password)
    RelativeLayout relativelayoutPassword;
    @BindView(R.id.relativelayout_box)
    RelativeLayout relativelayoutBox;
    @BindView(R.id.hint_tv)
    TextView hintTv;
    @BindView(R.id.button_back)
    QMUIRoundButton buttonBack;
    @BindView(R.id.button_register)
    QMUIRoundButton buttonRegister;

    private LoginFragment loginFragment;
    private Unbinder bind;
    private UserViewModel userViewModel;

    public RegisterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        bind = ButterKnife.bind(this, view);
        userViewModel = new ViewModelProvider(RegisterFragment.this).get(UserViewModel.class);
        userViewModel.getLoginInfo().observe(getViewLifecycleOwner(), loginSend ->{
            loginFragment.autoLogin(loginSend.getEmail(), loginSend.getPassword());
        });
        hintTv.setVisibility(View.GONE);
        return view;
    }

    public void setLoginFragment(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    @OnClick(R.id.clear_image_account)
    void clearAccount(View view) {
        editAccount.setText("");
    }

    @OnClick(R.id.clear_image_password)
    void clearPassword(View view) {
        editPassword.setText("");
    }

    @OnClick(R.id.clear_image_username)
    void clearUsername(View view) {
        editUsername.setText("");
    }

    @OnClick(R.id.button_back)
    void back(View view) {
        try{
            hintTv.setText("");
            loginFragment.clearEditText();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().hide(this)
                    .show(loginFragment).commit();
            EventBus.getDefault().post(FragmentMsg.getInstance("login", "show"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.button_register)
    void register(View view) {
        userViewModel.register(editAccount.getText().toString(), editUsername.getText().toString(),
                editPassword.getText().toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    void clearEditText() {
        editUsername.setText("");
        editAccount.setText("");
        editPassword.setText("");
    }

    @OnFocusChange(R.id.edit_account)
    void showAccountHint(View v, boolean hasFocus){
        if(hasFocus) {
            hintTv.setVisibility(VISIBLE);
            hintTv.setText("请填写邮箱~");
        }
    }

    @OnFocusChange(R.id.edit_password)
    void showPasswordHint(View v, boolean hasFocus){
        if(hasFocus){
            hintTv.setVisibility(VISIBLE);
            hintTv.setText("密码为6~20位，只能由英文、数字以及下划线组成哦~");
        }
    }

    @OnFocusChange(R.id.edit_username)
    void showUsernameHint(View v, boolean hasFocus){
        if(hasFocus){
            hintTv.setVisibility(VISIBLE);
            hintTv.setText("用户名为2~10位，只能由中英文、数字以及下划线组成哦~");
        }
    }

}
