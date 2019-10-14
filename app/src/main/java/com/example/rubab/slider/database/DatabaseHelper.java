package com.example.rubab.slider.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rubab.slider.models.CartModel;
import com.example.rubab.slider.models.CategoriesModel;
import com.example.rubab.slider.models.ItemsModel;
import com.example.rubab.slider.models.SliderModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context mycontext;
    private String DB_PATH;

    private static String DB_NAME = "shoes.db";
    public SQLiteDatabase myDataBase;


    public DatabaseHelper(Context context) throws IOException {
        super(context,DB_NAME,null,1);
        this.mycontext=context;
        //DB_PATH = "data/data/com.example.rubab.slider/database/";
//        DB_PATH = context.getDatabasePath(DB_NAME).getAbsolutePath();
        DB_PATH = "/data/user/0/com.example.rubab.slider/databases/";
        boolean dbexist = checkdatabase();
        if (dbexist) {
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(!dbexist) {
            this.getReadableDatabase();
            copyDataBase();
        }
    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copyDataBase()
    {
        Log.i("Database",
                "New database is being copied to device!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        try
        {
            myInput =mycontext.getAssets().open(DB_NAME);
            // transfer bytes from the inputfile to the
            // outputfile
            myOutput =new FileOutputStream(DB_PATH+ DB_NAME);
            while((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "New database has been copied to device!");


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<SliderModel> fetchSlider() {
        List<SliderModel> records = new ArrayList();
        myDataBase = this.getReadableDatabase();
        Cursor cursor = myDataBase.rawQuery("Select * FROM slider_table", null);
        if (cursor.moveToFirst()){
            do {
                SliderModel list = new SliderModel();
                list.setTitle(cursor.getString(cursor.getColumnIndex("description")));
                list.setImageUrl(cursor.getString(cursor.getColumnIndex("imageurl")));
                records.add(list);
            }while (cursor.moveToNext());
        }
        cursor.close();
        myDataBase.close();
        return records;
    }

    public List<ItemsModel> fetchProducts() {
        List<ItemsModel> records = new ArrayList();
        myDataBase = this.getReadableDatabase();
        Cursor cursor = myDataBase.rawQuery("Select * FROM products", null);
        if (cursor.moveToFirst()){
            do {
                ItemsModel list = new ItemsModel();
                list.setId(cursor.getString(cursor.getColumnIndex("id")));
                list.setCatid(cursor.getString(cursor.getColumnIndex("catid")));
                list.setTitle(cursor.getString(cursor.getColumnIndex("productname")));
                list.setImageUrl(cursor.getString(cursor.getColumnIndex("productimage")));
                list.setPrice(cursor.getString(cursor.getColumnIndex("productprice")));
                list.setDescription(cursor.getString(cursor.getColumnIndex("productdetail")));
                records.add(list);
            }while (cursor.moveToNext());
        }
        cursor.close();
        myDataBase.close();
        return records;
    }

    public List<CategoriesModel> fetchCategories() {
        List<CategoriesModel> records = new ArrayList();
        myDataBase = this.getReadableDatabase();
        Cursor cursor = myDataBase.rawQuery("Select * FROM categories", null);
        if (cursor.moveToFirst()){
            do {
                CategoriesModel list = new CategoriesModel();
                list.setId(cursor.getString(cursor.getColumnIndex("id")));
                list.setTitle(cursor.getString(cursor.getColumnIndex("category_name")));
                list.setImageUrl(cursor.getString(cursor.getColumnIndex("category_image")));
                records.add(list);
            }while (cursor.moveToNext());
        }
        cursor.close();
        myDataBase.close();
        return records;
    }

    public void addToCart(CartModel item) {
        myDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("c_id", item.getC_id());
        values.put("p_id", item.getP_id());
        values.put("product_name", item.getProduct_name());
        values.put("product_price", item.getProduct_price());

        try {
            myDataBase.insertWithOnConflict("cart", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

}