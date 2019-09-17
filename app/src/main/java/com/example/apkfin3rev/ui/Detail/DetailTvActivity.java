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
import com.example.apkfin3rev.model.tv.Tv;
import com.example.apkfin3rev.ui.TvShow.TvShowViewModel;

import java.util.Locale;

import static com.example.apkfin3rev.api.ApiUtils.IMAGE_URL;

public class DetailTvActivity extends AppCompatActivity {

    public static String INTENT;
    LinearLayout erorlayout;
    ImageView imgDrop, imgPost;
    TextView title,date;
    me.biubiubiu.justifytext.library.JustifyTextView sinopsis;
    ProgressBar progressBar;

    private final Observer<Tv> getTvs = new Observer<Tv>() {
        @Override
        public void onChanged(Tv tv) {
            if (tv != null) {
                imgDrop.setVisibility(View.VISIBLE);
                imgPost.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                sinopsis.setVisibility(View.VISIBLE);
                Glide.with(DetailTvActivity.this).load(IMAGE_URL + tv.getBackdrop())
                        .into(imgDrop);
                Glide.with(DetailTvActivity.this).load(IMAGE_URL + tv.getPosterPath())
                        .into(imgPost);
                title.setText(tv.getTitle());
                date.setText(tv.getReleaseDate());
                sinopsis.setText(tv.getOverView());
            }
        }
    };

    private TvShowViewModel tvShowViewModel;
    private int TV_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        erorlayout = findViewById(R.id.erorLayout);
        imgDrop = findViewById(R.id.imageView);
        imgPost = findViewById(R.id.imageView2);
        title = findViewById(R.id.etName);
        date = findViewById(R.id.etDate);
        sinopsis = findViewById(R.id.textFrom);
        progressBar = findViewById(R.id.progress);
        connection();
        setupView();
        TV_ID = getIntent().getIntExtra(INTENT, TV_ID);
        setupData();
    }
    private void connection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if(info != null && info.isConnected()) {}
        else {
            //error();
            progressBar.setVisibility(View.VISIBLE);
        }}

    private void  setupView(){
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvs().observe(this,getTvs);
    }

    private void setupData(){
        String language = Locale.getDefault().toString();
        if (language.equals("in_ID")) {
            language = "id_ID";
        }
        tvShowViewModel.setTvs(TV_ID,language);
    }


   /* private void error() {
        erorlayout.setVisibility(View.VISIBLE);
    } */
}
