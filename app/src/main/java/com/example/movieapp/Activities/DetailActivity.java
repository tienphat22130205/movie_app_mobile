package com.example.movieapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    private WebView webView;
    private static final String TAG = "DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        webView = findViewById(R.id.webView);

        String slug = getIntent().getStringExtra("slug");
        Log.d(TAG, "Slug: " + slug);

        if (slug != null && !slug.isEmpty()) {
            loadMovie(slug);
        } else {
            Toast.makeText(this, "Không có thông tin phim.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMovie(String slug) {
        String url = "https://ophimapi.vercel.app/phim/" + slug;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Log.d(TAG, "Response: " + response);
                        JSONObject json = new JSONObject(response);

                        if (!json.getBoolean("status")) {
                            Toast.makeText(this, "Phim không tồn tại trên hệ thống.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JSONArray servers = json.getJSONArray("episodes");
                        if (servers.length() > 0) {
                            JSONObject firstServer = servers.getJSONObject(0);
                            JSONArray episodes = firstServer.getJSONArray("server_data");

                            if (episodes.length() > 0) {
                                String videoUrl = episodes.getJSONObject(0).getString("link_embed");
                                Log.d(TAG, "Video URL: " + videoUrl);

                                webView.setWebViewClient(new WebViewClient());
                                WebSettings webSettings = webView.getSettings();
                                webSettings.setJavaScriptEnabled(true);

                                webView.loadUrl(videoUrl);
                            } else {
                                Toast.makeText(this, "Không tìm thấy tập phim.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Không có server khả dụng.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "JSON parse error: " + e.getMessage());
                        Toast.makeText(this, "Lỗi đọc dữ liệu phim.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e(TAG, "Volley error: " + error.toString());
                    Toast.makeText(this, "Không thể tải dữ liệu phim.", Toast.LENGTH_LONG).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }
}