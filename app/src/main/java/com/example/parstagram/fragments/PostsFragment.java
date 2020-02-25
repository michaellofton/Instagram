package com.example.parstagram.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parstagram.R;
import com.example.parstagram.adapters.PostsAdapter;
import com.example.parstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {
    private static final String TAG = PostsFragment.class.getSimpleName();
    private RecyclerView rvPosts;
    private PostsAdapter postsAdapter;
    private List<Post> allPosts;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();

        /* Recycler view */
        //0. create layout for one row in the list
        //1. create the adapter
        postsAdapter = new PostsAdapter(getContext(), allPosts);
        //2. create the data source
        //3. set the adapter on the recycler view
        rvPosts.setAdapter(postsAdapter);
        //4. set the layout manager on the recyler view
         rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

    private void queryPosts(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //Include the user keys in our query of data to retrieve from database
        query.include(Post.KEY_USER);
        query.include(Post.KEY_IMAGE);

        //Query for all the objects (Posts) in the background
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                //objects is the list of all post objects from our database
                if (e != null) {
                    Log.e(TAG, "done: ", e);
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Unable to retrieve posts.", Toast.LENGTH_LONG).show();
                    return;
                }
                for (Post post : objects) {
                    Log.i(TAG, "done: post description: " + post.getDescription());
                    Log.i(TAG, "done: post username: " + post.getUser().getUsername());
                }
                allPosts.addAll(objects);
                postsAdapter.notifyDataSetChanged();
            }
        });
    }
}
