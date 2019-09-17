package com.example.apkfin3rev.ui.TvShow;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apkfin3rev.R;
import com.example.apkfin3rev.adapter.TvAdapter;
import com.example.apkfin3rev.model.tv.Tv;
import com.example.apkfin3rev.ui.Detail.DetailTvActivity;


import java.util.ArrayList;
import java.util.Locale;

public class TvShowFragment extends Fragment {

    View view;
    RecyclerView rvCategory;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    TvAdapter tvAdapter;

    private Observer<ArrayList<Tv>> getTv = new Observer<ArrayList<Tv>>() {
        @Override
        public void onChanged(ArrayList<Tv> tvs) {
            if (tvs != null) {
                tvAdapter.addTv(tvs);
                show(false);
            }
        }
    };

    private TvShowViewModel notificationsViewModel;

    public TvShowFragment(){}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        rvCategory = view.findViewById(R.id.rv2);
        linearLayout = view.findViewById(R.id.erorLayout);
        progressBar = view.findViewById(R.id.progress);

        tvAdapter = new TvAdapter(getContext(), new ArrayList<Tv>(), new TvAdapter.CustomItemListener() {
            @Override
            public void onCustomClick(int id) {
                Intent i = new Intent(getContext(), DetailTvActivity.class);
                i.putExtra(DetailTvActivity.INTENT, id);
                startActivity(i);
            }
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(tvAdapter);
        tvAdapter.notifyDataSetChanged();


        connection();
        show(true);
        viewModel();
        setData();
        return view;
    }

    private void connection() {
        ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {
        } else {
            show(false);
            //showError();
        }
    }

    private void setData() {
        String LANGUAiGE = Locale.getDefault().toString();
        if (LANGUAiGE.equals("in_ID")) {
            LANGUAiGE = "id_ID";
        }

        notificationsViewModel.setTv(LANGUAiGE);
    }

    private void viewModel() {
        notificationsViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        notificationsViewModel.getTv().observe(this, getTv);
    }

    private void show(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    //private void showError() {
        //linearLayout.setVisibility(View.VISIBLE);
    //}


}