package com.example.apkfin3rev.ui.Detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apkfin3rev.R;
import com.example.apkfin3rev.model.film.Film;
import com.example.apkfin3rev.ui.Film.FilmViewModel;

import java.util.Locale;

import static com.example.apkfin3rev.api.ApiUtils.IMAGE_URL;

public class DetailFilmActivity extends AppCompatActivity {
    public static String INTENT = "movie_id";

    LinearLayout erorlayout;
    ImageView imgDrop, imgPost;
    TextView title,date;
    me.biubiubiu.justifytext.library.JustifyTextView sinopsis;
    ProgressBar progressBar;


    private final Observer<Film> getMovie = new Observer<Film>(){
        @Override
        public void onChanged(Film film) {
            if (film != null) {

                imgDrop.setVisibility(View.VISIBLE);
                imgPost.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                sinopsis.setVisibility(View.VISIBLE);
                Glide.with(DetailFilmActivity.this).load(IMAGE_URL + film.getBackdrop())
                        .into(imgDrop);
                Glide.with(DetailFilmActivity.this).load(IMAGE_URL + film.getPosterPath())
                        .into(imgPost);
                title.setText(film.getTitle());
                date.setText(film.getReleaseDate());
                sinopsis.setText(film.getOverView());
            }
        }
    };

    private FilmViewModel filmViewModel;
    private int MOVIE_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        erorlayout = findViewById(R.id.erorLayout);
        imgDrop = findViewById(R.id.imageView);
        imgPost = findViewById(R.id.imageView2);
        title = findViewById(R.id.etName);
        date = findViewById(R.id.etDate);
        sinopsis = findViewById(R.id.textFrom);
        progressBar = findViewById(R.id.progress);
        connection();
        setupView();
        MOVIE_ID = getIntent().getIntExtra(INTENT, MOVIE_ID);
        setupData();
    }
    private void connection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if(info != null && info.isConnected()) {}
        else {

            progressBar.setVisibility(View.VISIBLE);

            //error();
        }}

    private void  setupView(){
        filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        filmViewModel.getMovie().observe(this,getMovie);
    }

    private void setupData(){
        String language = Locale.getDefault().toString();
        if (language.equals("in_ID")) {
            language = "id_ID";
        }
        filmViewModel.setMovie(MOVIE_ID,language);
    }


    private void error() {
        erorlayout.setVisibility(View.VISIBLE);
    }
}
