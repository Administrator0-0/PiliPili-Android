package com.example.pilipili_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pilipili_android.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutMeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutMeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.button_scancode)
    ImageButton buttonScancode;
    @BindView(R.id.button_theme)
    ImageButton buttonTheme;
    @BindView(R.id.button_daytonight)
    ImageButton buttonDaytonight;
    @BindView(R.id.button_picture)
    ImageButton buttonPicture;
    @BindView(R.id.text_topace)
    TextView textTopace;
    @BindView(R.id.text_numberofcoin)
    TextView textNumberofcoin;
    @BindView(R.id.text_numberoftrends)
    TextView textNumberoftrends;
    @BindView(R.id.text_numberoffans)
    TextView textNumberoffans;
    @BindView(R.id.text_numberoffocus)
    TextView textNumberoffocus;
    @BindView(R.id.button_offlinecaching)
    ImageButton buttonOfflinecaching;
    @BindView(R.id.button_history)
    ImageButton buttonHistory;
    @BindView(R.id.button_collection)
    ImageButton buttonCollection;
    @BindView(R.id.button_publish)
    QMUIRoundButton buttonPublish;
    @BindView(R.id.button_publish_mainpage)
    ImageButton buttonPublishMainpage;
    @BindView(R.id.button_publish_history)
    ImageButton buttonPublishHistory;
    @BindView(R.id.button_callserver)
    QMUIRoundButton buttonCallserver;
    @BindView(R.id.button_younger)
    QMUIRoundButton buttonYounger;
    @BindView(R.id.button_getcoin)
    QMUIRoundButton buttonGetcoin;
    @BindView(R.id.button_settings)
    QMUIRoundButton buttonSettings;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutMeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_aboutme.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutMeFragment newInstance(String param1, String param2) {
        AboutMeFragment fragment = new AboutMeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aboutme, container, false);
    }

}