package com.example.pilipili_android.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.pilipili_android.R;
import com.example.pilipili_android.databinding.ActivityEditSignBinding;
import com.example.pilipili_android.databinding.ActivityEditUserInfoBinding;
import com.example.pilipili_android.view_model.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditSignActivity extends AppCompatActivity {

    @BindView(R.id.sign_et)
    EditText signEt;
    @BindView(R.id.left_count)
    TextView leftCount;

    private ActivityEditSignBinding activityEditSignBinding;

    private String sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditSignBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_sign);
        activityEditSignBinding.setUserViewModel(new ViewModelProvider(this).get(UserViewModel.class));
        ButterKnife.bind(this);

        sign = getIntent().getStringExtra("sign");
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
    }

    @OnClick(R.id.button_back)
    public void onButtonBackClicked() {
        this.finish();
    }

    @OnClick(R.id.save)
    public void onSaveClicked() {
        String newSign = signEt.getText().toString();
        if(!newSign.equals(sign))
            activityEditSignBinding.getUserViewModel().uploadSign(newSign);
        this.finish();
    }
}
