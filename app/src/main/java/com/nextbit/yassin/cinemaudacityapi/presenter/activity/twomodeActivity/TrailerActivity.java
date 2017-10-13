package com.nextbit.yassin.cinemaudacityapi.presenter.activity.twomodeActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nextbit.yassin.cinemaudacityapi.R;
import com.nextbit.yassin.cinemaudacityapi.presenter.activity.MainActivity;
import com.nextbit.yassin.cinemaudacityapi.presenter.fragment.TrailerFra;

public class TrailerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        TrailerFra newDesriptionFragment = new TrailerFra();
                Bundle args = new Bundle();

                args.putString("image",getIntent().getStringExtra("image"));
                args.putString("name",getIntent().getStringExtra("name"));
                args.putString("overview",getIntent().getStringExtra("overview"));
                args.putString("poster",getIntent().getStringExtra("poster"));
                args.putInt("id",getIntent().getIntExtra("id",426));



                newDesriptionFragment.setArguments(args);
             //   getSupportFragmentManager().beginTransaction().remove(descriptionFragment);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containertrailer,newDesriptionFragment,"")
                        .addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }
}
