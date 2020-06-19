package com.example.pilipili_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.LoginActivity;
import com.example.pilipili_android.activity.MainActivity;
import com.example.pilipili_android.activity.PayActivity;
import com.example.pilipili_android.databinding.FragmentLoginBinding;
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


public class LoginFragment extends Fragment {

    private UserViewModel userViewModel;
    private FragmentLoginBinding fragmentLoginBinding;
    private Unbinder bind;

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
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.clear_image_password)
    ImageView clearImagePassword;
    @BindView(R.id.relativelayout_password)
    RelativeLayout relativelayoutPassword;
    @BindView(R.id.button_sign)
    QMUIRoundButton buttonSign;
    @BindView(R.id.button_login)
    QMUIRoundButton buttonLogin;
    @BindView(R.id.relativelayout_box)
    RelativeLayout relativelayoutBox;

    private RegisterFragment registerFragment;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        userViewModel = new ViewModelProvider(LoginFragment.this).get(UserViewModel.class);
        fragmentLoginBinding.setUserViewModel(userViewModel);
        View view = fragmentLoginBinding.getRoot();
        bind = ButterKnife.bind(this, view);
        EventBus.getDefault().post(FragmentMsg.getInstance("login", "show"));

        userViewModel.getIsSuccessLogin().observe(getViewLifecycleOwner(), isSuccessLogin -> {
            if (isSuccessLogin) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        userViewModel.getLoginInfo().observe(getViewLifecycleOwner(), loginSend -> {
            fragmentLoginBinding.setLoginInfo(loginSend);
        });

        return view;
    }

    @OnClick(R.id.button_login)
    void login(View view){
        userViewModel.login(editAccount.getText().toString(), editPassword.getText().toString());
    }

    public void autoLogin(String email, String password) {
        userViewModel.login(email, password);
    }

    @OnClick(R.id.clear_image_account)
    void clearAccount(View view) {
        editAccount.setText("");
    }

    @OnClick(R.id.clear_image_password)
    void clearPassword(View view) {
        editPassword.setText("");
    }

    @OnClick(R.id.button_sign)
    void register(View view) {
        try{
            registerFragment.clearEditText();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().hide(this)
                    .show(registerFragment).commit();
            EventBus.getDefault().post(FragmentMsg.getInstance("register", "show"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    public void setRegisterFragment(RegisterFragment registerFragment) {
        this.registerFragment = registerFragment;
    }

    void clearEditText() {
        editAccount.setText("");
        editPassword.setText("");
    }

    @OnFocusChange(R.id.edit_account)
    void showAccountHint(View v, boolean hasFocus){
        if(hasFocus) {
            EventBus.getDefault().post(FragmentMsg.getInstance("login", "open_eyes"));
        }
    }

    @OnFocusChange(R.id.edit_password)
    void showPasswordHint(View v, boolean hasFocus){
        if(hasFocus){
            EventBus.getDefault().post(FragmentMsg.getInstance("login", "close_eyes"));
        }
    }
}
