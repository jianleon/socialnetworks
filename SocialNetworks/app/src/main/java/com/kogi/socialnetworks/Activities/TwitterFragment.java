package com.kogi.socialnetworks.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kogi.socialnetworks.R;

import com.kogi.socialnetworks.Utils.Configuration;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TweetUtils;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterFragment extends Fragment {

    LinearLayout tweetList;

    public TwitterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.twitter_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        tweetList = (LinearLayout) getActivity().findViewById(R.id.tweets_list);

        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();
        Call<List<Tweet>> call = statusesService.userTimeline(Configuration.user_id, null, null, null, null, false, false, false, false);
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                for (Tweet tweet : result.data) {
                    tweetList.addView(new CompactTweetView(getActivity(), tweet));
                }
            }

            public void failure(TwitterException exception) {
                Toast.makeText(getActivity(), "Error: No se pudo obtener los tweets", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
