package com.example.pilipili_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pilipili_android.R;
import com.example.pilipili_android.view_model.UserViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditSignFragment extends Fragment {

    @BindView(R.id.sign_et)
    EditText signEt;
    @BindView(R.id.left_count)
    TextView leftCount;

    private String sign;
    private UserViewModel userViewModel;
    private Unbinder unbinder;

    public EditSignFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_sign, container, false);
        unbinder = ButterKnife.bind(this, view);
        userViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(UserViewModel.class);
        signEt.setText(sign);
        signEt.setSelection(sign.length());
        leftCount.setText(70 - sign.length() + "");
        signEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                leftCount.setText(70 - signEt.getText().toString().length() + "");
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.button_back)
    void onButtonBackClicked() {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @OnClick(R.id.save)
    void onSaveClicked() {
        String newSign = signEt.getText().toString();
        if(!newSign.equals(sign)){
            String signEdit = newSign.substring(0, signEt.getLayout().getLineEnd(0));
            userViewModel.uploadSign(newSign, signEdit);
        }
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
