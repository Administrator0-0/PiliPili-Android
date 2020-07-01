package com.example.pilipili_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.pilipili_android.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChannelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.channel_anime)
    RelativeLayout channelAnime;
    @BindView(R.id.channel_cartoon)
    RelativeLayout channelCartoon;
    @BindView(R.id.channel_music)
    RelativeLayout channelMusic;
    @BindView(R.id.channel_games)
    RelativeLayout channelGames;
    @BindView(R.id.channel_life)
    RelativeLayout channelLife;
    @BindView(R.id.channel_vlog)
    RelativeLayout channelVlog;
    @BindView(R.id.channel_guichu)
    RelativeLayout channelGuichu;
    @BindView(R.id.channel_fun)
    RelativeLayout channelFun;
    @BindView(R.id.channel_film)
    RelativeLayout channelFilm;
    @BindView(R.id.channel_dianying)
    RelativeLayout channelDianying;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChannelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_channel.
     */
    // TODO: Rename and change types and number of parameters
    public static ChannelFragment newInstance(String param1, String param2) {
        ChannelFragment fragment = new ChannelFragment();
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
        return inflater.inflate(R.layout.fragment_channel, container, false);
    }
}