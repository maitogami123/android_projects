package com.example.lab1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TableLayout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableLayout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TableLayout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TableLayout.
     */
    // TODO: Rename and change types and number of parameters
    public static TableLayout newInstance(String param1, String param2) {
        TableLayout fragment = new TableLayout();
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
        return inflater.inflate(R.layout.fragment_table_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        SeekBar seekBar = (SeekBar) getView().findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new seekBarChangeHandler());

    }

    private class seekBarChangeHandler implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int color1 = Math.round( -100000  * progress / 1000) - 100000;
            int color2 = Math.round( -200000  * progress / 1000) - 200000;
            int color3 = Math.round( -300000  * progress / 1000) - 300000;
            int color4 = Math.round( -400000  * progress / 1000) - 400000;
            int color5 = Math.round( -500000  * progress / 1000) - 500000;

            ImageView img1 = (ImageView) getView().findViewById(R.id.imageView);
            ImageView img2 = (ImageView) getView().findViewById(R.id.imageView2);
            ImageView img3 = (ImageView) getView().findViewById(R.id.imageView3);
            ImageView img4 = (ImageView) getView().findViewById(R.id.imageView4);
            ImageView img5 = (ImageView) getView().findViewById(R.id.imageView5);

            img1.setBackgroundColor(color1);
            img2.setBackgroundColor(color2);
            img3.setBackgroundColor(color3);
            img4.setBackgroundColor(color4);
            img5.setBackgroundColor(color5);

        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }
}