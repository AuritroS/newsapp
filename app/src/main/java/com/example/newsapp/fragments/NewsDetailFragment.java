package com.example.newsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.adapters.NewsAdapter;
import com.example.newsapp.models.NewsItem;
import java.util.ArrayList;
import java.util.List;

public class NewsDetailFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_IMAGE = "imageUrl";
    private static final String ARG_DESC = "description";

    public static NewsDetailFragment newInstance(NewsItem item) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, item.getTitle());
        args.putString(ARG_IMAGE, item.getImageUrl());
        args.putString(ARG_DESC, item.getDescription());

        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        ImageView image = view.findViewById(R.id.detail_image);
        TextView description = view.findViewById(R.id.detail_description);
        RecyclerView recyclerRelated = view.findViewById(R.id.recycler_related_news);
        TextView closeBtn = view.findViewById(R.id.btn_close);

        if (getArguments() != null) {
            String title = getArguments().getString(ARG_TITLE);
            String imageUrl = getArguments().getString(ARG_IMAGE);
            String desc = getArguments().getString(ARG_DESC);

            Glide.with(this).load(imageUrl).into(image);
            description.setText(desc);

            recyclerRelated.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerRelated.setAdapter(new NewsAdapter(getRelatedNews(title), item -> {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, newInstance(item))
                        .addToBackStack(null)
                        .commit();
            }));
        }

        closeBtn.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().findViewById(R.id.fragment_container).setVisibility(View.GONE);
            requireActivity().findViewById(R.id.content_container).setVisibility(View.VISIBLE);
        });

        return view;
    }

    private List<NewsItem> getRelatedNews(String title) {
        List<NewsItem> list = new ArrayList<>();
        list.add(new NewsItem("More on: " + title, "https://picsum.photos/300/200?random=4", "More details about " + title));
        list.add(new NewsItem("Background: " + title, "https://picsum.photos/300/200?random=5", "Background info on " + title));
        list.add(new NewsItem("Update: " + title, "https://picsum.photos/300/200?random=6", "Latest update about " + title));
        return list;
    }
}
