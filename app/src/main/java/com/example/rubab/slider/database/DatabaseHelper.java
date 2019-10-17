package com.example.rubab.slider.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.example.rubab.slider.R;
import com.example.rubab.slider.fragments.CartFragment;
import com.example.rubab.slider.models.CartModel;
import com.example.rubab.slider.models.CategoriesModel;
import com.example.rubab.slider.models.ItemsModel;
import com.example.rubab.slider.models.SliderModel;
import com.example.rubab.slider.models.User;

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
    // User table name
    private  String TABLE_USER = "user";

    // User Table Columns names
    private String COLUMN_USER_ID = "user_id";
    private String COLUMN_USER_NAME = "user_name";
    private String COLUMN_USER_EMAIL = "user_email";
    private String COLUMN_USER_PASSWORD = "user_password";

    // create table sql query
    public  String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    public  String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    public DatabaseHelper(Context context) throws IOException {
        super(context,DB_NAME,null,4);
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
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

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

    public void addUser(User user) {
        myDataBase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        myDataBase.insert(TABLE_USER, null, values);
        myDataBase.close();
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }



    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String email) {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USER, columns,selection,selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER,columns,selection,selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public List<ItemsModel> fetchProducts(String id) {
        List<ItemsModel> records = new ArrayList();
        myDataBase = this.getReadableDatabase();
        Cursor cursor = myDataBase.rawQuery("Select * FROM products WHERE catid ='"+id+"'", null);
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
        values.put("productid", item.getP_id());
        values.put("catid", item.getC_id());
        values.put("productname", item.getProduct_name());
        values.put("price", item.getProduct_price());
        values.put("qty", item.getQty());
        values.put("product_img", item.getProduct_image());
        values.put("product_detail", item.getProduct_detail());

        try {
            myDataBase.insertWithOnConflict("cart", null, values, SQLiteDatabase.CONFLICT_REPLACE);
            Toast.makeText(mycontext,"Item added to cart", Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public boolean updateCart(CartModel item) {
        boolean isUpdated = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("price", item.getProduct_price());
        values.put("qty", item.getQty());
        // updating row
        try{
            db.update("cart", values, "id = ?", new String[]{item.getId()});
            Toast.makeText(mycontext, "Item Updated", Toast.LENGTH_SHORT).show();
            isUpdated = true;
        }catch (Exception e){
            isUpdated = false;
            e.printStackTrace();
        }
        db.close();
        return isUpdated;
    }

    public void deleteProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete("cart", "id = ?", new String[]{String.valueOf(id)});
        Toast.makeText(mycontext, "Item Updated", Toast.LENGTH_SHORT).show();
        setupHomeFragment(new CartFragment());
        db.close();
    }

    public boolean confirmOrder(String id, String email, String f_name, String l_name, String cell, String address, String state, String city, String total_amount) {
        boolean isBooked = false;
        myDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("email", email);
        values.put("f_name", f_name);
        values.put("l_name", l_name);
        values.put("cell", cell);
        values.put("address", address);
        values.put("state", state);
        values.put("city", city);
        values.put("total_amount", total_amount);

        try {
            myDataBase.insertWithOnConflict("ConfirmedOrderTable", null, values, SQLiteDatabase.CONFLICT_REPLACE);
            Toast.makeText(mycontext,"Order Confirmed", Toast.LENGTH_SHORT).show();
            isBooked = true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            isBooked = false;
        }

        return isBooked;
    }

    public List<CartModel> fetchCartItems() {
        List<CartModel> records = new ArrayList();
        myDataBase = this.getReadableDatabase();
        Cursor cursor = myDataBase.rawQuery("Select * FROM cart", null);
        if (cursor.moveToFirst()){
            do {
                CartModel list = new CartModel();
                list.setId(cursor.getString(cursor.getColumnIndex("id")));
                list.setP_id(cursor.getString(cursor.getColumnIndex("productid")));
                list.setC_id(cursor.getString(cursor.getColumnIndex("catid")));
                list.setProduct_name(cursor.getString(cursor.getColumnIndex("productname")));
                list.setProduct_price(cursor.getString(cursor.getColumnIndex("price")));
                list.setQty(cursor.getString(cursor.getColumnIndex("qty")));
                list.setProduct_image(cursor.getString(cursor.getColumnIndex("product_img")));
                list.setProduct_detail(cursor.getString(cursor.getColumnIndex("product_detail")));
                records.add(list);
            }while (cursor.moveToNext());
        }
        cursor.close();
        myDataBase.close();
        return records;
    }

    public int totalSum(){
        myDataBase = this.getReadableDatabase();
        Cursor mCount = myDataBase.rawQuery("SELECT SUM(price) AS totalSUM FROM cart", null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();
        return count;
    }

    private void setupHomeFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) mycontext).getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().add(R.id.content_main, fragment).commit();
    }
}