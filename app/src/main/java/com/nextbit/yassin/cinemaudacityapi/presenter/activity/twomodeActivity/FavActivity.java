package com.nextbit.yassin.cinemaudacityapi.presenter.activity.twomodeActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.nextbit.yassin.cinemaudacityapi.R;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Movie;
import com.nextbit.yassin.cinemaudacityapi.presenter.OnVersionNameSelectionChangeListener;
import com.nextbit.yassin.cinemaudacityapi.presenter.activity.MainActivity;
import com.nextbit.yassin.cinemaudacityapi.presenter.fragment.DetailsFra;
import com.nextbit.yassin.cinemaudacityapi.presenter.fragment.FavMoviesFra;


public class FavActivity extends AppCompatActivity implements OnVersionNameSelectionChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        if (findViewById(R.id.fragment_containerfav) != null) {
            if (savedInstanceState != null) {//for rotation not over create activity
                return;

            }
            FavMoviesFra mainFragment = new FavMoviesFra();
            //set the Activity to be listener

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerfav, mainFragment, "") .addToBackStack(null).commit();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void OnSelectionChanged(Movie m, int key) {
        DetailsFra descriptionFragment = (DetailsFra) getSupportFragmentManager()
                .findFragmentById(R.id.description_fragment);
        if (descriptionFragment!=null) {//two pane mode
            descriptionFragment.setDescription(m,key);
        }else {
            DetailsFra newDesriptionFragment = new DetailsFra();
            Bundle args = new Bundle();

            args.putString("image",m.getPosterPath());
            args.putString("name",m.getTitle());
            args.putDouble("rate",m.getVoteAverage());
            args.putInt("count",m.getVoteCount());
            args.putString("overview",m.getOverview());
            args.putString("poster",m.getPosterPath());
            args.putInt("id",m.getId());



            newDesriptionFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,newDesriptionFragment,"")
                    .addToBackStack(null).commit();
        }

    }
}
