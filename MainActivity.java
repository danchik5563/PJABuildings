package com.example.danilwelter.pjabuildings;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.danilwelter.pjabuildings.DataBase.BuildingDBHelper;
import com.example.danilwelter.pjabuildings.ListAdapters.DwellingHouseAdapter;
import com.example.danilwelter.pjabuildings.ListAdapters.MuseumAdapter;
import com.example.danilwelter.pjabuildings.Model.Building;
import com.example.danilwelter.pjabuildings.Model.DwellingHouse;
import com.example.danilwelter.pjabuildings.Model.Museum;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String LOG_TAG = "myLogs";

    Calendar dateAndTime = Calendar.getInstance();
    FloatingActionButton fabAddItem;
    LinearLayout linearLayoutDefaultScreen;
    LinearLayout linearLayoutMuseum;
    LinearLayout linearLayoutDwellingHouse;
    LinearLayout linearLayoutListBuildings;

    EditText tbMuseumAddress;
    EditText tbMuseumFloorsCount;
    EditText tbMuseumStartTime;
    EditText tbMuseumEndTime;
    EditText tbDwellingHouseAddress;
    EditText tbDwellingHouseFloorsCount;
    EditText tbDwellingHouseApartmentsCount;


    RecyclerView recyclerView;
    MuseumAdapter museumAdapter;
    DwellingHouseAdapter dwellingHouseAdapter;

    BuildingDBHelper dbHelper;
    SQLiteDatabase db;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbMuseumAddress = (EditText) findViewById(R.id.tbMuseumAddress);
        tbMuseumFloorsCount = (EditText) findViewById(R.id.tbMuseumFloorsCount);
        tbMuseumStartTime = (EditText) findViewById(R.id.tbMuseumStartTime);
        tbMuseumEndTime = (EditText) findViewById(R.id.tbMuseumEndTime);
        tbDwellingHouseAddress = (EditText) findViewById(R.id.tbDwellingHouseAddress);
        tbDwellingHouseFloorsCount = (EditText) findViewById(R.id.tbDwellingHouseFloorsCount);
        tbDwellingHouseApartmentsCount = (EditText) findViewById(R.id.tbDwellingHouseApartmentsCount);
        linearLayoutDefaultScreen = (LinearLayout) findViewById(R.id.LinearLayoutDefaultScreen);
        linearLayoutMuseum = (LinearLayout) findViewById(R.id.LinearLayoutMuseum);
        linearLayoutDwellingHouse = (LinearLayout) findViewById(R.id.LinearLayoutDwellingHouse);
        linearLayoutListBuildings = (LinearLayout) findViewById(R.id.LinearLayoutListBuildings);
        recyclerView = (RecyclerView) findViewById(R.id.RecycleViewListBuildings);
        fabAddItem = (FloatingActionButton)findViewById(R.id.fabAddItem);

        DbFillSingletonLists();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //region onFabAddItemClick
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toastText = "";

                if(linearLayoutMuseum.getVisibility() == View.VISIBLE){
                    if (tbMuseumAddress.getText().toString().isEmpty() |
                            tbMuseumFloorsCount.getText().toString().isEmpty() |
                            tbMuseumStartTime.getText().toString().isEmpty() |
                            tbMuseumEndTime.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Сначала заполните поля!", Toast.LENGTH_SHORT).show();

                    } else {
                        try{
                            Museum museum = new Museum(tbMuseumAddress.getText().toString(),
                                    Integer.parseInt(tbMuseumFloorsCount.getText().toString()),
                                    tbMuseumStartTime.getText().toString(),
                                    tbMuseumEndTime.getText().toString());

                            //Если в ней что то есть, значит окно открылось не для создания, а для редактирования
                            if(Singleton.getInstance().getEditableMuseumObject() != null){
                                if(Singleton.getInstance().get_listMuseums().contains(Singleton.getInstance().getEditableMuseumObject())){
                                    int index = Singleton.getInstance().get_listMuseums().indexOf(Singleton.getInstance().getEditableMuseumObject());

                                    museum.set_id(Singleton.getInstance().getEditableMuseumObject().get_id());
                                    Singleton.getInstance().get_listMuseums().set(index, museum);
                                    DbUpdateObject(Singleton.getInstance().getEditableMuseumObject().get_id(), museum);



                                    toastText = "Объект изменен\nАдрес: " + museum.get_address() +
                                            "\nКол-во этажей: " + museum.get_floorsCount() +
                                            "\nНачало работы: " + museum.get_startTime() +
                                            "\nКонец работы: " + museum.get_endTime();
                                    Singleton.getInstance().setEditableMuseumObject(null);
                                }
                            } else {
                                museum.set_id(DbInsertObject(museum));
                                Singleton.getInstance().get_listMuseums().add(museum);

                                toastText = "Объект создан\nАдрес: " + museum.get_address() +
                                        "\nКол-во этажей: " + museum.get_floorsCount() +
                                        "\nНачало работы: " + museum.get_startTime() +
                                        "\nКонец работы: " + museum.get_endTime();
                            }

                            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
                            ClearFields(1);
                        } catch (Exception ex){
                            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG);
                        }

                    }
                } else if (linearLayoutDwellingHouse.getVisibility() == View.VISIBLE){
                    if (tbDwellingHouseAddress.getText().toString().isEmpty() |
                            tbDwellingHouseFloorsCount.getText().toString().isEmpty() |
                            tbDwellingHouseApartmentsCount.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Сначала заполните поля!", Toast.LENGTH_SHORT).show();
                    } else {
                        try{
                            DwellingHouse dwellingHouse = new DwellingHouse(tbDwellingHouseAddress.getText().toString(),
                                    Integer.parseInt(tbDwellingHouseFloorsCount.getText().toString()),
                                    Integer.parseInt(tbDwellingHouseApartmentsCount.getText().toString()));

                            //Если в ней что то есть, значит окно открылось не для создания, а для редактирования
                            if(Singleton.getInstance().getEditableDwellingHouseObject() != null){
                                if(Singleton.getInstance().get_listDwellingHouses().contains(Singleton.getInstance().getEditableDwellingHouseObject())){
                                    int index = Singleton.getInstance().get_listDwellingHouses().indexOf(Singleton.getInstance().getEditableDwellingHouseObject());

                                    dwellingHouse.set_id(Singleton.getInstance().getEditableDwellingHouseObject().get_id());
                                    Singleton.getInstance().get_listDwellingHouses().set(index, dwellingHouse);
                                    DbUpdateObject(Singleton.getInstance().getEditableDwellingHouseObject().get_id(), dwellingHouse);

                                    toastText = "Объект изменен\nАдрес: " + dwellingHouse.get_address() +
                                            "\nКол-во этажей: " + dwellingHouse.get_floorsCount() +
                                            "\nКол-во квартир: " + dwellingHouse.get_apartmentsCount();
                                    Singleton.getInstance().setEditableDwellingHouseObject(null);
                                }
                            } else {
                                dwellingHouse.set_id(DbInsertObject(dwellingHouse));
                                Singleton.getInstance().get_listDwellingHouses().add(dwellingHouse);

                                toastText = "Объект создан\nАдрес: " + dwellingHouse.get_address() +
                                        "\nКол-во этажей: " + dwellingHouse.get_floorsCount() +
                                        "\nКол-во квартир: " + dwellingHouse.get_apartmentsCount();
                            }

                            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
                            ClearFields(2);
                        } catch (Exception ex){
                            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();}
                    }
                }
            }
        });
        //endregion

        View.OnClickListener tbMuseumStartTimeClickListenner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {setTime(1);
            }
        };
        View.OnClickListener tbMuseumEndTimeClickListenner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(2);
            }
        };
        tbMuseumStartTime = (EditText)findViewById(R.id.tbMuseumStartTime);
        tbMuseumEndTime = (EditText) findViewById(R.id.tbMuseumEndTime);
        tbMuseumStartTime.setOnClickListener(tbMuseumStartTimeClickListenner);
        tbMuseumEndTime.setOnClickListener(tbMuseumEndTimeClickListenner);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            fabAddItem.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.btAddMuseum) {
            linearLayoutDefaultScreen.setVisibility(View.INVISIBLE);
            linearLayoutDwellingHouse.setVisibility(View.INVISIBLE);
            linearLayoutMuseum.setVisibility(View.VISIBLE);
            linearLayoutListBuildings.setVisibility(View.INVISIBLE);
            fabAddItem.setVisibility(View.VISIBLE);
        } else if (id == R.id.btAddDwellingHouse) {
            linearLayoutDefaultScreen.setVisibility(View.INVISIBLE);
            linearLayoutDwellingHouse.setVisibility(View.VISIBLE);
            linearLayoutMuseum.setVisibility(View.INVISIBLE);
            linearLayoutListBuildings.setVisibility(View.INVISIBLE);
            fabAddItem.setVisibility(View.VISIBLE);
        } else if (id == R.id.btListMuseums){
            linearLayoutDefaultScreen.setVisibility(View.INVISIBLE);
            linearLayoutDwellingHouse.setVisibility(View.INVISIBLE);
            linearLayoutMuseum.setVisibility(View.INVISIBLE);
            linearLayoutListBuildings.setVisibility(View.VISIBLE);
            fabAddItem.setVisibility(View.INVISIBLE);

            dbHelper = new BuildingDBHelper(this);
            db = dbHelper.getWritableDatabase();
            museumAdapter = new MuseumAdapter(Singleton.getInstance().get_listMuseums(), this, db);
            museumAdapter.SettersViews(fabAddItem, linearLayoutMuseum,
                    linearLayoutDwellingHouse,linearLayoutListBuildings,
                    tbMuseumAddress, tbMuseumFloorsCount, tbMuseumStartTime, tbMuseumEndTime);
            recyclerView.setAdapter(museumAdapter);
            if(Singleton.getInstance().get_listMuseums().isEmpty()) Toast.makeText(this, "Список пуст", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.btListDwellingHouses){
            linearLayoutDefaultScreen.setVisibility(View.INVISIBLE);
            linearLayoutDwellingHouse.setVisibility(View.INVISIBLE);
            linearLayoutMuseum.setVisibility(View.INVISIBLE);
            linearLayoutListBuildings.setVisibility(View.VISIBLE);
            fabAddItem.setVisibility(View.INVISIBLE);

            dbHelper = new BuildingDBHelper(this);
            db = dbHelper.getWritableDatabase();
            dwellingHouseAdapter = new DwellingHouseAdapter(Singleton.getInstance().get_listDwellingHouses(), this, db);
            dwellingHouseAdapter.SettersViews(fabAddItem, linearLayoutMuseum,
                    linearLayoutDwellingHouse,linearLayoutListBuildings,tbDwellingHouseAddress, tbDwellingHouseFloorsCount, tbDwellingHouseApartmentsCount);
            recyclerView.setAdapter(dwellingHouseAdapter);
            if(Singleton.getInstance().get_listDwellingHouses().isEmpty()) Toast.makeText(this, "Список пуст", Toast.LENGTH_SHORT).show();}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void ClearFields(int index){
        switch (index){
            case 1:{
                tbMuseumAddress.setText("");
                tbMuseumFloorsCount.setText("");
                tbMuseumStartTime.setText("");
                tbMuseumEndTime.setText("");
                break;
            }

            case 2:{
                tbDwellingHouseAddress.setText("");
                tbDwellingHouseFloorsCount.setText("");
                tbDwellingHouseApartmentsCount.setText("");
                break;
            }

            default: break;
        }
    }

    public void setTime(int index) {
        switch (index){
            case 1: {
                new TimePickerDialog(MainActivity.this, timeSetListenner1,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true)
                        .show();
                break;
            }

            case 2:{
                new TimePickerDialog(MainActivity.this, timeSetListenner2,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true)
                        .show();
                break;
            }

            default: break;
        }

    }

    public long DbInsertObject(Building b){
        dbHelper = new BuildingDBHelper(this);
        db = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();
        long objectId = Long.MIN_VALUE;

        if (b.getClass() == Museum.class){
            Museum m = (Museum)b;
            contentValues.put(dbHelper.KEY_ADDRESS, m.get_address());
            contentValues.put(dbHelper.KEY_FLOORSCOUNT, m.get_floorsCount());
            contentValues.put(dbHelper.KEY_STARTTIME, m.get_startTime());
            contentValues.put(dbHelper.KEY_ENDTIME, m.get_endTime());

            objectId = db.insert(dbHelper.TABLE_MUSEUMS, null, contentValues);
        }

        if(b.getClass() == DwellingHouse.class){
            DwellingHouse d = (DwellingHouse) b;
            contentValues.put(dbHelper.KEY_ADDRESS, d.get_address());
            contentValues.put(dbHelper.KEY_FLOORSCOUNT, d.get_floorsCount());
            contentValues.put(dbHelper.KEY_APARTMENTSCOUNT, d.get_apartmentsCount());

            objectId = db.insert(dbHelper.TABLE_DWELLINGHOUSES, null, contentValues);
        }
        db.close();
        dbHelper.close();
        return objectId;

    }

    public void DbUpdateObject(long id, Building b){
        dbHelper = new BuildingDBHelper(this);
        db = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();

        String stringId = String.valueOf(id);

        if (b.getClass() == Museum.class){
            Museum m = (Museum)b;
            contentValues.put(dbHelper.KEY_ADDRESS, m.get_address());
            contentValues.put(dbHelper.KEY_FLOORSCOUNT, m.get_floorsCount());
            contentValues.put(dbHelper.KEY_STARTTIME, m.get_startTime());
            contentValues.put(dbHelper.KEY_ENDTIME, m.get_endTime());

            db.update(dbHelper.TABLE_MUSEUMS, contentValues, "_id = ?", new String[]{stringId});
        }

        if(b.getClass() == DwellingHouse.class){
            DwellingHouse d = (DwellingHouse) b;
            contentValues.put(dbHelper.KEY_ADDRESS, d.get_address());
            contentValues.put(dbHelper.KEY_FLOORSCOUNT, d.get_floorsCount());
            contentValues.put(dbHelper.KEY_APARTMENTSCOUNT, d.get_apartmentsCount());

            db.update(dbHelper.TABLE_DWELLINGHOUSES, contentValues, "_id = ?", new String[]{stringId});
        }
        db.close();
        dbHelper.close();
    }

    public void DbFillSingletonLists(){
        Singleton.getInstance().get_listDwellingHouses().clear();
        Singleton.getInstance().get_listMuseums().clear();

        dbHelper = new BuildingDBHelper(this);
        db = dbHelper.getWritableDatabase();

       Cursor museumCursor = db.query(dbHelper.TABLE_MUSEUMS,null,null,null,null,null,null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (museumCursor.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = museumCursor.getColumnIndex(dbHelper.KEY_ID);
            int addressColIndex = museumCursor.getColumnIndex(dbHelper.KEY_ADDRESS);
            int floorsCountColIndex = museumCursor.getColumnIndex(dbHelper.KEY_FLOORSCOUNT);
            int startTimeColIndex = museumCursor.getColumnIndex(dbHelper.KEY_STARTTIME);
            int endTimeColIndex = museumCursor.getColumnIndex(dbHelper.KEY_ENDTIME);

            do {
                Singleton.getInstance().get_listMuseums().add(new Museum(
                        museumCursor.getInt(idColIndex),
                        museumCursor.getString(addressColIndex),
                        museumCursor.getInt(floorsCountColIndex),
                        museumCursor.getString(startTimeColIndex),
                        museumCursor.getString(endTimeColIndex)));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (museumCursor.moveToNext());
        } else
            //Ничего нет
            museumCursor.close();

        Cursor dwellingHouseCursos = db.query(dbHelper.TABLE_DWELLINGHOUSES,null,null,null,null,null,null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (dwellingHouseCursos.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = dwellingHouseCursos.getColumnIndex(dbHelper.KEY_ID);
            int addressColIndex = dwellingHouseCursos.getColumnIndex(dbHelper.KEY_ADDRESS);
            int floorsCountColIndex = dwellingHouseCursos.getColumnIndex(dbHelper.KEY_FLOORSCOUNT);
            int apartmentsCountColIndex = dwellingHouseCursos.getColumnIndex(dbHelper.KEY_APARTMENTSCOUNT);

            do {
                Singleton.getInstance().get_listDwellingHouses().add(new DwellingHouse(
                        dwellingHouseCursos.getInt(idColIndex),
                        dwellingHouseCursos.getString(addressColIndex),
                        dwellingHouseCursos.getInt(floorsCountColIndex),
                        dwellingHouseCursos.getInt(apartmentsCountColIndex)));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (dwellingHouseCursos.moveToNext());
        } else
            //Ничего нет
            dwellingHouseCursos.close();

        db.close();
        dbHelper.close();
    }


    //region timeSetListenner1
    TimePickerDialog.OnTimeSetListener timeSetListenner1 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String strHourOfDay = "" + hourOfDay;
            String strMinute = "" + minute;
            if(hourOfDay<10)strHourOfDay = "0" + hourOfDay;
            if(minute < 10) strMinute = "0" + minute;
            tbMuseumStartTime.setText(strHourOfDay + ":" + strMinute);
        }
    };
    //endregion

    //region timeSetListenner2
    TimePickerDialog.OnTimeSetListener timeSetListenner2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String strHourOfDay = "" + hourOfDay;
            String strMinute = "" + minute;
            if(hourOfDay<10)strHourOfDay = "0" + hourOfDay;
            if(minute < 10) strMinute = "0" + minute;
            tbMuseumEndTime.setText(strHourOfDay + ":" + strMinute);
        }
    };
    //endregion

}
