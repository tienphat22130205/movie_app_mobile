package com.example.movieapp.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.Adapters.KKPhimAdapter;
import com.example.movieapp.Adapters.SliderAdapter;
import com.example.movieapp.Domain.MovieResponse;
import com.example.movieapp.Domain.SliderItems;
import com.example.movieapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterBestMovies, AdapterUpComming, adapterCategory;
    private RecyclerView recyclerViewBestMovies, recyclerViewUpComming, recyclerViewCategory;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2, mStringRequest3;
    private ProgressBar loading1, loading2, loading3;
    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        banner();
        sendRequest();
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        loading2.setVisibility(View.VISIBLE);
        loading3.setVisibility(View.VISIBLE);

        // Best Movies
        String urlBest = "https://ophim1.com/danh-sach/phim-moi-cap-nhat";
        mStringRequest = new StringRequest(Request.Method.GET, urlBest,
                response -> {
                    loading1.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);
                    adapterBestMovies = new KKPhimAdapter(movieResponse.getItems());
                    recyclerViewBestMovies.setAdapter(adapterBestMovies);
                },
                error -> {
                    loading1.setVisibility(View.GONE);
                    Log.e("KKPhim", "BestMovies error: " + error.toString());
                });
        mRequestQueue.add(mStringRequest);

        // Upcomming Movies
        String urlUpcomming = "https://ophim1.com/danh-sach/phim-moi-cap-nhat";
        mStringRequest2 = new StringRequest(Request.Method.GET, urlUpcomming,
                response -> {
                    loading2.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);
                    AdapterUpComming = new KKPhimAdapter(movieResponse.getItems());
                    recyclerViewUpComming.setAdapter(AdapterUpComming);
                },
                error -> {
                    loading2.setVisibility(View.GONE);
                    Log.e("KKPhim", "Upcomming error: " + error.toString());
                });
        mRequestQueue.add(mStringRequest2);

        // Category Movies
            String urlCategory = "https://ophim1.com/danh-sach/phim-moi-cap-nhat";
        mStringRequest3 = new StringRequest(Request.Method.GET, urlCategory,
                response -> {
                    loading3.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);
                    adapterCategory = new KKPhimAdapter(movieResponse.getItems());
                    recyclerViewCategory.setAdapter(adapterCategory);
                },
                error -> {
                    loading3.setVisibility(View.GONE);
                    Log.e("KKPhim", "Category error: " + error.toString());
                });
        mRequestQueue.add(mStringRequest3);
    }


    private void banner() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide));
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide3));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
            }
        });
    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    @Override
    protected void onPause(){
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);

    }
    @Override
    protected void onResume(){
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 2000);

    }

    private void initView() {
        viewPager2 = findViewById(R.id.viewpagerSlider);

        recyclerViewBestMovies = findViewById(R.id.view1);
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewUpComming = findViewById(R.id.view2);
        recyclerViewUpComming.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewCategory = findViewById(R.id.view3);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));

        loading1 = findViewById(R.id.progressBar1);
        loading2 = findViewById(R.id.progressBar2);
        loading3 = findViewById(R.id.progressBar3);
    }

}