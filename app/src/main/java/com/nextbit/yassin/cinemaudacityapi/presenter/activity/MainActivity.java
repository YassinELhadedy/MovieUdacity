package com.nextbit.yassin.cinemaudacityapi.presenter.activity;

import android.content.Context;
import android.content.Intent;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.nextbit.yassin.cinemaudacityapi.R;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Movie;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.cache.CacheImpl;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.MovieDataRepository;
import com.nextbit.yassin.cinemaudacityapi.infrastructure.repository.datasource.MovieDataStoreFactory;
import com.nextbit.yassin.cinemaudacityapi.domain.model.MoviesList;
import com.nextbit.yassin.cinemaudacityapi.domain.service.SOService;
import com.nextbit.yassin.cinemaudacityapi.presenter.activity.twomodeActivity.FavActivity;
import com.nextbit.yassin.cinemaudacityapi.presenter.activity.twomodeActivity.TrailerActivity;
import com.nextbit.yassin.cinemaudacityapi.presenter.fragment.DetailsFra;
import com.nextbit.yassin.cinemaudacityapi.presenter.fragment.FavMoviesFra;
import com.nextbit.yassin.cinemaudacityapi.presenter.fragment.MovieListFra;
import com.nextbit.yassin.cinemaudacityapi.presenter.OnVersionNameSelectionChangeListener;
import com.nextbit.yassin.cinemaudacityapi.presenter.fragment.TrailerFra;
import com.nextbit.yassin.cinemaudacityapi.presenter.recycleviewadapter.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;



import static com.nextbit.yassin.cinemaudacityapi.presenter.fragment.DetailsFra.fragkey;

public class MainActivity extends AppCompatActivity implements OnVersionNameSelectionChangeListener, SwipeRefreshLayout.OnRefreshListener {
    private SOService soService;
    private TextView rs;


    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initCollapsingToolbar();

        fragkey=1;
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null){//for rotation not over create activity
                return;
            }

            swipeRefreshLayout.setOnRefreshListener(this);
            /**
             * Showing Swipe Refresh animation on activity create
             * As animation won't start on onCreate, post runnable is used
             */
            swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(true);

                                            fetchMovies();
                                        }
                                    }
            );



        }


    }
    void fetchMovies(){
        if(fragkey !=0){//to refersh first fragment only .not details fragment
        MovieListFra mainFragment = new MovieListFra();
        //set the Activity to be listener

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment, "").commit();

        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);
        }else {  // stopping swipe refresh
            swipeRefreshLayout.setRefreshing(false);}

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,PrefsActivity.class));
            return true;
        }else if (id==R.id.action_fav){
            if (isTablet(this)){
                startActivity(new Intent(this, FavActivity.class));
            }
            else {
                FavMoviesFra mainFragment = new FavMoviesFra();
                //set the Activity to be listener

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment, "") .addToBackStack(null).commit();

            }



            return true;
        }

        return super.onOptionsItemSelected(item);
    }








    @Override
    public void OnSelectionChanged(Movie m,int key) {

        DetailsFra descriptionFragment = (DetailsFra) getSupportFragmentManager()
                .findFragmentById(R.id.description_fragment);
        if (descriptionFragment!=null){//two pane mode

            if (key==0){// in click movie
                // If description is available, we are in two pane layout
                // so we call the method in DescriptionFragment to update its content
                descriptionFragment.setDescription(m,key);
            }
            else if(key==1){//show trailer
//                TrailerFra newDesriptionFragment = new TrailerFra();
//                Bundle args = new Bundle();
//
//                args.putString("image",m.getPosterPath());
//                args.putString("name",m.getTitle());
//                args.putString("overview",m.getOverview());
//                args.putString("poster",m.getPosterPath());
//                args.putInt("id",m.getId());
//
//
//
//                newDesriptionFragment.setArguments(args);
//             //   getSupportFragmentManager().beginTransaction().remove(descriptionFragment);
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.description_fragment,newDesriptionFragment,"")
//                        .addToBackStack(null).commit();

                startActivity(new Intent(this,TrailerActivity.class)
                .putExtra("image",m.getPosterPath())
                .putExtra("name",m.getTitle())
                .putExtra("overview",m.getOverview())
                .putExtra("poster",m.getPosterPath())
                .putExtra("id",m.getId()));
            }
            else {//add as fav

                ArrayList<Movie>movies=new ArrayList<>();
                MoviesList moviesList=new MoviesList();
                movies.add(m);
                moviesList.setmovies(movies);

                MovieDataRepository movieDataRepository=new MovieDataRepository(new MovieDataStoreFactory(MainActivity.this,new CacheImpl(MainActivity.this)));
                movieDataRepository.putFavMovie(moviesList);

            }


        }else {// in one pane

            if (key==0){// in click movie
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
            else if(key==1){//show trailer

               // Toast.makeText(this," "+ key, Toast.LENGTH_SHORT).show();

                TrailerFra newDesriptionFragment = new TrailerFra();
                Bundle args = new Bundle();

                args.putString("image",m.getPosterPath());
                args.putString("name",m.getTitle());
                args.putString("overview",m.getOverview());
                args.putString("poster",m.getPosterPath());
                args.putInt("id",m.getId());



                newDesriptionFragment.setArguments(args);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,newDesriptionFragment,"")
                        .addToBackStack(null).commit();
            }
            else {//add as fav

                ArrayList<Movie>movies=new ArrayList<>();
                MoviesList moviesList=new MoviesList();
                movies.add(m);
                moviesList.setmovies(movies);

                MovieDataRepository movieDataRepository=new MovieDataRepository(new MovieDataStoreFactory(MainActivity.this,new CacheImpl(MainActivity.this)));
                movieDataRepository.putFavMovie(moviesList);
            }


        }











//        Intent intent = new Intent(getContext(), FragmentGet.class);
//        intent.putExtra("Student", model);
//
//        Fragment fragmentGet = new FragmentGet();
//        fragmentGet.setArguments(intent.getExtras());
    }


    @Override
    public void onRefresh() {
        fetchMovies();

    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
