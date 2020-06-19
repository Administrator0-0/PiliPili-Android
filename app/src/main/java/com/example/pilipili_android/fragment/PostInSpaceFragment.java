package com.example.pilipili_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilipili_android.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrendsInSpaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrendsInSpaceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerview_trends_inspace)
    RecyclerView recyclerviewTrendsInspace;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrendsInSpaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrendsInSpaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrendsInSpaceFragment newInstance(String param1, String param2) {
        TrendsInSpaceFragment fragment = new TrendsInSpaceFragment();
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
        return inflater.inflate(R.layout.fragment_trends_in_space, container, false);
    }
}