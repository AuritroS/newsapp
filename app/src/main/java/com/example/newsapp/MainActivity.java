package com.example.newsapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import com.example.newsapp.adapters.NewsAdapter;
import com.example.newsapp.fragments.NewsDetailFragment;
import com.example.newsapp.models.NewsItem;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerTopStories;
    private RecyclerView recyclerLatestNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerTopStories = findViewById(R.id.recycler_top_stories);
        recyclerLatestNews = findViewById(R.id.recycler_latest_news);

        List<NewsItem> topStories = getSampleNews();
        List<NewsItem> latestNews = getSampleNews();

        setupRecyclerView(recyclerTopStories, topStories);
        setupRecyclerView(recyclerLatestNews, latestNews);
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            findViewById(R.id.fragment_container).setVisibility(View.GONE);
            findViewById(R.id.content_container).setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }


    private void setupRecyclerView(RecyclerView recyclerView, List<NewsItem> newsList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        NewsAdapter adapter = new NewsAdapter(newsList, newsItem -> {
            // Hide the news lists
            findViewById(R.id.content_container).setVisibility(View.GONE);
            // Show the fragment container
            findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, NewsDetailFragment.newInstance(newsItem))
                    .addToBackStack(null)
                    .commit();
        });
        recyclerView.setAdapter(adapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new CenterSpacingItemDecoration(100));
    }


    private List<NewsItem> getSampleNews() {
        List<NewsItem> list = new ArrayList<>();
        list.add(new NewsItem("Earthquake hits Tokyo", "https://picsum.photos/300/200?random=1", "An earthquake of magnitude 6.5 struck Tokyo..."));
        list.add(new NewsItem("Mars Rover Discovers Ice", "https://picsum.photos/300/200?random=2", "NASA's rover has found evidence of ice on Mars..."));
        list.add(new NewsItem("Olympics Opening Ceremony", "https://picsum.photos/300/200?random=3", "The grand opening of the 2028 Olympics dazzled viewers..."));
        return list;
    }
}
