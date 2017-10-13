package com.nextbit.yassin.cinemaudacityapi.infrastructure.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Movie;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Review;
import com.nextbit.yassin.cinemaudacityapi.domain.model.Trailer;

import java.util.ArrayList;

import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.ID_REV_MOV;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.ID_TRAMOV;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.KEY_AUTHOR;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.KEY_ID;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.KEY_KEY;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.KEY_NAME;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.KEY_NAME2;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.KEY_NAMET;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.KEY_TRA_ID2;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.TABLE_NAME;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.TABLE_NAME2;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.TABLE_NAME3;
import static com.nextbit.yassin.cinemaudacityapi.domain.confiq.Constants.TABLE_NAME4;


public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Movie> movieList = new ArrayList<>();
    private final ArrayList<Movie> movoffline = new ArrayList<>();

    private final ArrayList<Trailer> TrraList = new ArrayList<>();
    private final ArrayList<Review> ReviewList = new ArrayList<>();




    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create our offline
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME +
                " TEXT, " + Constants.KEY_MOV_IMAGE + " TEXT, "+Constants.KEY_MOV_OV+" TEXT, " +Constants.KEY_MOV_RAT+" TEXT, "+Constants.KEY_V_C+"  TEXT, "+Constants.KEY_TRA_ID+"  TEXT, "+ Constants.KEY_R_D + " TEXT  );";

        db.execSQL(CREATE_MOVIES_TABLE);
        String CREATE_FAV_TABLE = "CREATE TABLE " + TABLE_NAME2 + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME2 +
                " TEXT, " + Constants.KEY_MOV_IMAGE2 + " TEXT, " + Constants.KEY_MOV_OV2 + " TEXT, " + Constants.KEY_MOV_RAT2 + " TEXT, " + Constants.KEY_V_C2 + "  TEXT, " + KEY_TRA_ID2 + "  TEXT, " + Constants.KEY_R_D2 + " TEXT  );";

        db.execSQL(CREATE_FAV_TABLE);

        //create our table
        String CREATE_TRAILER_TABLE = "CREATE TABLE " + TABLE_NAME3 + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "+ID_TRAMOV + " TEXT, " +KEY_KEY + " TEXT, " + KEY_NAMET +
                  " TEXT  );";

        db.execSQL(CREATE_TRAILER_TABLE);
        String CREATE_Review_TABLE = "CREATE TABLE " + TABLE_NAME4 + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_AUTHOR +
                " TEXT, " + Constants.KEY_CONTENT + "  TEXT, "+ID_REV_MOV + " TEXT, "  + Constants.KEY_URL +  " TEXT  );";

        db.execSQL(CREATE_Review_TABLE);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        Log.v("ONUPGRADE", "DROPING THE TABLE AND CREATING A NEW ONE!");

        //create a new one
        onCreate(db);


    }


     public void deleteTable(){
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_NAME, null,
            null);

    db.close();
}

    public void deleteTableReview(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME4, null,
                null);

        db.close();
    }

    public void deleteTableTrailer(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME3, null,
                null);

        db.close();
    }

     public boolean isEmpty(){
         SQLiteDatabase db = this.getReadableDatabase();
         String count = "SELECT * FROM "+ TABLE_NAME;
         Cursor mcursor = db.rawQuery(count, null);
         int icount = mcursor.getCount();
         if(icount !=0){
             return true;}
        else {return false;
        }




     }



    //add content to table
    public void addOffline(ArrayList<Movie> movie){
        for (int i = 0; i < movie.size(); i++) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, movie.get(i).getTitle());
            values.put(Constants.KEY_MOV_IMAGE, movie.get(i).getPosterPath());
            values.put(Constants.KEY_MOV_OV, movie.get(i).getOverview());

            values.put(Constants.KEY_MOV_RAT, movie.get(i).getVoteAverage());
            values.put(Constants.KEY_V_C, movie.get(i).getVoteCount());
            values.put(Constants.KEY_TRA_ID, movie.get(i).getId());
            values.put(Constants.KEY_R_D, movie.get(i).getReleaseDate());


            db.insert(TABLE_NAME, null, values);
            //db.insert(Constants.TABLE_NAME, null, values);

            Log.i("Wish successfully!", "yeah!!");

            db.close();

        }

    }

    public void addMovies(Movie movie) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME2, movie.getTitle());
        values.put(Constants.KEY_MOV_IMAGE2, movie.getPosterPath());
        values.put(Constants.KEY_MOV_OV2, movie.getOverview());

        values.put(Constants.KEY_MOV_RAT2, movie.getVoteAverage());
        values.put(Constants.KEY_V_C2, movie.getVoteCount());
        values.put(KEY_TRA_ID2, movie.getId());
        values.put(Constants.KEY_R_D2, movie.getReleaseDate());




        db.insert(TABLE_NAME2, null, values);
        //db.insert(Constants.TABLE_NAME, null, values);

        Log.i("Wish successfully!", "yeah!!");

        db.close();


    }

    public void addtrailers(ArrayList<Trailer> tra,int id){
        for (int i = 0; i < tra.size(); i++) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_KEY, tra.get(i).getName());

            values.put(KEY_NAMET, tra.get(i).getKey());
            values.put(ID_TRAMOV, ""+id);


            db.insert(TABLE_NAME3, null, values);
            //db.insert(Constants.TABLE_NAME, null, values);

            Log.i("Wish successfully!", "yeah!!");

            db.close();
    }}
    public void addReviews(ArrayList<Review> review,int id){
        String x=review.get(0).getAuthor();

        for (int i = 0; i < review.size(); i++) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_AUTHOR, review.get(i).getAuthor());



            values.put(Constants.KEY_CONTENT, review.get(i).getContent());
            values.put(Constants.KEY_URL, review.get(i).getUrl());
            values.put(Constants.ID_REV_MOV, ""+id);


            db.insert(TABLE_NAME4, null, values);
            //db.insert(Constants.TABLE_NAME, null, values);

            Log.i("review successfully!", "yeah!!");

            db.close();

        }
    }

    public void add_fav_movies (ArrayList<Movie>movieList){

        for (int i = 0; i < movieList.size(); i++) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME2, movieList.get(i).getTitle());
            values.put(Constants.KEY_MOV_IMAGE2, movieList.get(i).getPosterPath());
            values.put(Constants.KEY_MOV_OV2, movieList.get(i).getOverview());

            values.put(Constants.KEY_MOV_RAT2, movieList.get(i).getVoteAverage());
            values.put(Constants.KEY_V_C2, movieList.get(i).getVoteCount());
            values.put(Constants.KEY_TRA_ID2, movieList.get(i).getId());
            values.put(Constants.KEY_R_D2, movieList.get(i).getReleaseDate());


            db.insert(TABLE_NAME2, null, values);
            //db.insert(Constants.TABLE_NAME, null, values);

            Log.i("Wish successfully!", "yeah!!");

            db.close();

        }


    }

    //Get DATA
    public ArrayList<Movie> getMoviesOffline() {

        movoffline.clear();

        // String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, Constants.KEY_MOV_IMAGE, Constants.KEY_MOV_OV,Constants.KEY_MOV_RAT,Constants.KEY_V_C,Constants.KEY_TRA_ID,Constants.KEY_R_D},null,null, null,null,null);

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {
                Log.i("first line in do","nn");

                Movie movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_IMAGE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_OV)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_RAT))));
                movie.setVoteCount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_V_C))));
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_TRA_ID))));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(Constants.KEY_R_D)));








                movoffline.add(movie);
                Log.i("last in do","ss");

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return movoffline;

    }

    public ArrayList<Movie> getMovies() {

        movieList.clear();

       // String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_NAME2, new String[]{KEY_ID, KEY_NAME2, Constants.KEY_MOV_IMAGE2, Constants.KEY_MOV_OV2,Constants.KEY_MOV_RAT2,Constants.KEY_V_C2, KEY_TRA_ID2,Constants.KEY_R_D2},null,null, null,null,null);

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {
                Log.i("first line in do","nn");

                Movie movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NAME2)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_IMAGE2)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_OV2)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_RAT2))));
                movie.setVoteCount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_V_C2))));
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRA_ID2))));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(Constants.KEY_R_D2)));








                movieList.add(movie);
                Log.i("last in do","ss");

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return movieList;

    }

    public ArrayList<Trailer> getTrailers(String id) {

        TrraList.clear();
        String selection=ID_TRAMOV+" = ?";
        String[] selectionargs = new String[1];
        selectionargs[0]=id;

        // String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_NAME3, new String[]{KEY_ID, ID_TRAMOV,KEY_KEY, KEY_NAMET},selection,selectionargs, null,null,null);

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {
                Log.i("first line in do","nn");

                Trailer trailer = new Trailer();
                trailer.setName(cursor.getString(cursor.getColumnIndex(KEY_KEY)));

                trailer.setKey(cursor.getString(cursor.getColumnIndex(KEY_NAMET)));
                trailer.setId(cursor.getString(cursor.getColumnIndex(ID_TRAMOV)));








                TrraList.add(trailer);
                Log.i("last in do","ss");

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return TrraList;

    }

    public ArrayList<Review> getReviews(String id) {

        ReviewList.clear();
        String selection=ID_REV_MOV+" = ?";
        String[] selectionargs = new String[1];
        selectionargs[0]=id;
        // String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_NAME4, new String[]{KEY_ID, KEY_AUTHOR, Constants.KEY_CONTENT,ID_REV_MOV,Constants.KEY_URL},selection,selectionargs, null,null,null);

        int x=cursor.getCount();
        //loop through cursor
        if (cursor.moveToFirst()) {

            do {
                Log.i("first line in do","nn");

                Review movie = new Review();
                movie.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));

                movie.setContent(cursor.getString(cursor.getColumnIndex(Constants.KEY_CONTENT)));
                movie.setUrl(cursor.getString(cursor.getColumnIndex(Constants.KEY_URL)));
                movie.setId(cursor.getString(cursor.getColumnIndex(Constants.ID_REV_MOV)));









                ReviewList.add(movie);
                Log.i("last in do","ss");

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return ReviewList;

    }
    public ArrayList<Movie> get_fav_movies(){
        movoffline.clear();

        // String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_NAME2, new String[]{KEY_ID, KEY_NAME2, Constants.KEY_MOV_IMAGE2, Constants.KEY_MOV_OV2,Constants.KEY_MOV_RAT2,Constants.KEY_V_C2,Constants.KEY_TRA_ID2,Constants.KEY_R_D2},null,null, null,null,null);

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {
                Log.i("first line in do","nn");

                Movie movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NAME2)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_IMAGE2)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_OV2)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_RAT2))));
                movie.setVoteCount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_V_C2))));
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_TRA_ID2))));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(Constants.KEY_R_D2)));








                movoffline.add(movie);
                Log.i("last in do","ss");

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return movoffline;


    }


    public boolean isCaschedTrailer(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selection=ID_TRAMOV+" = ?";
        String[] selectionargs = new String[1];
        selectionargs[0]=id;

        Cursor cursor=  db.rawQuery("SELECT * FROM  "+TABLE_NAME3+"  WHERE  "+ID_TRAMOV+ " = ?",
                selectionargs);

//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE "+ KEY_NAME2+" = " + name.get(0).getTitle(), null);
//        Cursor cursor = db.rawQuery(TABLE_NAME3, new String[]{KEY_ID, ID_TRAMOV,KEY_KEY, KEY_NAMET},selection,selectionargs, null,null,null);

        int c=cursor.getCount();


        if (cursor.getCount() >0) {
            db.close();
            return true;
        }
        else {




            db.close();
            return false;



        }
    }
    public boolean isCaschedReview(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selection=ID_REV_MOV+" = ?";
        String[] selectionargs = new String[1];
        selectionargs[0]=id;

//        Cursor cursor=  db.rawQuery("SELECT * FROM  "+TABLE_NAME4+"  WHERE  "+ID_REV_MOV+ " = ?",
//                selectionargs);
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE "+ KEY_NAME2+" = " + name.get(0).getTitle(), null);
        Cursor cursor = db.query(TABLE_NAME4, new String[]{KEY_ID, KEY_AUTHOR, Constants.KEY_CONTENT,ID_REV_MOV,Constants.KEY_URL},selection,selectionargs, null,null,null);

//        String count = "SELECT * FROM "+ TABLE_NAME4;
//        Cursor cursor = db.rawQuery(count, null);
        int c=cursor.getCount();


        if (cursor.getCount() >0) {
            db.close();
            return true;
        }
        else {




            db.close();
            return false;



        }
    }


    public void deleteMovie(Movie name) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME2, KEY_NAME2 + " = ? ",
                new String[]{ String.valueOf(name.getTitle())});

        db.close();

    }
    public void isExist(ArrayList<Movie> name){
        SQLiteDatabase db = this.getReadableDatabase();
        String selection=KEY_NAME2+" = ?";
        String[] selectionargs = new String[1];
        selectionargs[0]=name.get(0).getTitle();


//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE "+ KEY_NAME2+" = " + name.get(0).getTitle(), null);
        Cursor cursor = db.query(TABLE_NAME2, new String[]{KEY_ID, KEY_NAME2, Constants.KEY_MOV_IMAGE2, Constants.KEY_MOV_OV2,Constants.KEY_MOV_RAT2,Constants.KEY_V_C2,Constants.KEY_TRA_ID2,Constants.KEY_R_D2},selection,selectionargs, null,null,null);

        int c=cursor.getCount();


        if (cursor.getCount() !=0) {
            db.close();

        }
        else {

            add_fav_movies(name);


            db.close();



        }


          /*  Cursor cursor=db.rawQuery("select "+Constants.KEY_NAME+" from "+ TABLE_NAME+" where "+Constants.KEY_NAME+"="+name,null);
        if(cursor.getCount()>=0){db.close();
            return true;}
        else {db.close();

            return false;

        }
*/

    }
    // Getting single contact
    boolean getName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME2, new String[] { KEY_ID,
                        KEY_NAME2, KEY_TRA_ID2 }, KEY_NAME2 + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor.getCount() !=0) {db.close();
            return true;}
        else {db.close();

            return false;

        }}




}
