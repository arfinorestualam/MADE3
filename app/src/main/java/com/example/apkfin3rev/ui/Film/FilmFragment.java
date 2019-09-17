package com.example.apkfin3rev.ui.Film;

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
import com.example.apkfin3rev.adapter.FilmAdapter;
import com.example.apkfin3rev.model.film.Film;
import com.example.apkfin3rev.ui.Detail.DetailFilmActivity;


import java.util.ArrayList;
import java.util.Locale;

public class FilmFragment extends Fragment {


    View view;
    RecyclerView rvCategory;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    FilmAdapter filmAdapter;

    private Observer<ArrayList<Film>> getMovies = new Observer<ArrayList<Film>>() {
        @Override
        public void onChanged(ArrayList<Film> films) {
            if (films != null) {
                filmAdapter.addFilm(films);
                show(false);
            }
        }
    };


    private FilmViewModel homeViewModel;

    public FilmFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        view = inflater.inflate(R.layout.fragment_film, container, false);
        rvCategory = view.findViewById(R.id.rv1);
        linearLayout = view.findViewById(R.id.erorLayout);
        progressBar = view.findViewById(R.id.progress);
        filmAdapter = new FilmAdapter(getContext(), new ArrayList<Film>(), new FilmAdapter.CustomItemListener() {
            @Override
            public void onCustomClick(int id) {
                Intent i = new Intent(getContext(), DetailFilmActivity.class);
                i.putExtra(DetailFilmActivity.INTENT, id);
                startActivity(i);
            }
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(filmAdapter);
        filmAdapter.notifyDataSetChanged();

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

        homeViewModel.setMovies(LANGUAiGE);
    }

    private void viewModel() {
        homeViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        homeViewModel.getMovies().observe(this, getMovies);
    }

    private void show(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    // void showError() {
        //linearLayout.setVisibility(View.VISIBLE);
   // }

}