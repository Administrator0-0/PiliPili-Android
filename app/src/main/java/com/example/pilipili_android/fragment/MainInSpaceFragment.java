package com.example.pilipili_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilipili_android.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainInSpaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainInSpaceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.button_morepictures_inspace)
    QMUIRoundButton buttonMorepicturesInspace;
    @BindView(R.id.recyclerview_pictures_inspace)
    RecyclerView recyclerviewPicturesInspace;
    @BindView(R.id.button_morecollections_inspace)
    QMUIRoundButton buttonMorecollectionsInspace;
    @BindView(R.id.recyclerview_collection_inspace)
    RecyclerView recyclerviewCollectionInspace;
    @BindView(R.id.button_moremovies_givencoin_inspace)
    QMUIRoundButton buttonMoremoviesGivencoinInspace;
    @BindView(R.id.recyclerview_moviesgivencoin_inspace)
    RecyclerView recyclerviewMoviesgivencoinInspace;
    @BindView(R.id.button_moremoviespraised_inspace)
    QMUIRoundButton buttonMoremoviespraisedInspace;
    @BindView(R.id.recyclerview_moviespraised_inspace)
    RecyclerView recyclerviewMoviespraisedInspace;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainInSpaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPageInSpaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainInSpaceFragment newInstance(String param1, String param2) {
        MainInSpaceFragment fragment = new MainInSpaceFragment();
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
        return inflater.inflate(R.layout.fragment_main_in_space, container, false);
    }
}