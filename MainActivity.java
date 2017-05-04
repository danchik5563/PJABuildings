package com.example.danilwelter.pjabuildings;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.danilwelter.pjabuildings.Model.Building;
import com.example.danilwelter.pjabuildings.Model.DwellingHouse;
import com.example.danilwelter.pjabuildings.Model.Museum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Calendar dateAndTime = Calendar.getInstance();
    FloatingActionButton fabAddItem;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 100; i++){
            Singleton.getInstance().get_listMuseums() .add(new Museum("Проспект мира " + i, 3, "08:00", "18:00"));
            Singleton.getInstance().get_listDwellingHouses().add(new DwellingHouse("Копылова, " + i, 1 + i, 10 + i));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.RecycleViewListBuildings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        linearLayoutMuseum = (LinearLayout) findViewById(R.id.LinearLayoutMuseum);
        linearLayoutDwellingHouse = (LinearLayout) findViewById(R.id.LinearLayoutDwellingHouse);
        linearLayoutListBuildings = (LinearLayout) findViewById(R.id.LinearLayoutListBuildings);

        fabAddItem = (FloatingActionButton)findViewById(R.id.fabAddItem);
        //region onFabAddItemClick
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tbMuseumAddress = (EditText) findViewById(R.id.tbMuseumAddress);
                tbMuseumFloorsCount = (EditText) findViewById(R.id.tbMuseumFloorsCount);
                tbMuseumStartTime = (EditText) findViewById(R.id.tbMuseumStartTime);
                tbMuseumEndTime = (EditText) findViewById(R.id.tbMuseumEndTime);
                tbDwellingHouseAddress = (EditText) findViewById(R.id.tbDwellingHouseAddress);
                tbDwellingHouseFloorsCount = (EditText) findViewById(R.id.tbDwellingHouseFloorsCount);
                tbDwellingHouseApartmentsCount = (EditText) findViewById(R.id.tbDwellingHouseApartmentsCount);

                String toastText;
                String fileText;

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
                            Singleton.getInstance().get_listMuseums().add(museum);
                            toastText = "Объект создан\nАдрес: " + museum.get_address() +
                                    "\nКол-во этажей: " + museum.get_floorsCount() +
                                    "\nНачало работы: " + museum.get_startTime() +
                                    "\nКонец работы: " + museum.get_endTime();
                            fileText = "\n --- \n " + "\nАдрес: " + museum.get_address() +
                                    "\nКол-во этажей: " + museum.get_floorsCount() +
                                    "\nНачало работы: " + museum.get_startTime() +
                                    "\nКонец работы: " + museum.get_endTime() + "\n";
                            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
                            ClearFields(1);
                            //TODO WriteFile(fileText);
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
                            Singleton.getInstance().get_listDwellingHouses().add(dwellingHouse);
                            toastText = "Объект создан\nАдрес: " + dwellingHouse.get_address() +
                                    "\nКол-во этажей: " + dwellingHouse.get_floorsCount() +
                                    "\nКол-во квартир: " + dwellingHouse.get_apartmentsCount();
                            fileText = "\n --- \n " + "\nАдрес: " + dwellingHouse.get_address() +
                                    "\nКол-во этажей: " + dwellingHouse.get_floorsCount() +
                                    "\nКол-во квартир: " + dwellingHouse.get_apartmentsCount() + "\n";
                            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
                            ClearFields(2);
                            //TODO WriteFile(fileText);
                        } catch (Exception ex){
                            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }


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
            linearLayoutDwellingHouse.setVisibility(View.INVISIBLE);
            linearLayoutMuseum.setVisibility(View.VISIBLE);
            linearLayoutListBuildings.setVisibility(View.INVISIBLE);
            fabAddItem.setVisibility(View.VISIBLE);
        } else if (id == R.id.btAddDwellingHouse) {
            linearLayoutDwellingHouse.setVisibility(View.VISIBLE);
            linearLayoutMuseum.setVisibility(View.INVISIBLE);
            linearLayoutListBuildings.setVisibility(View.INVISIBLE);
            fabAddItem.setVisibility(View.VISIBLE);
        } else if (id == R.id.btListMuseums){
            linearLayoutDwellingHouse.setVisibility(View.INVISIBLE);
            linearLayoutMuseum.setVisibility(View.INVISIBLE);
            linearLayoutListBuildings.setVisibility(View.VISIBLE);
            fabAddItem.setVisibility(View.INVISIBLE);

            museumAdapter = new MuseumAdapter(Singleton.getInstance().get_listMuseums(), this);



            recyclerView.setAdapter(museumAdapter);
        } else if (id == R.id.btListDwellingHouses){
            linearLayoutDwellingHouse.setVisibility(View.INVISIBLE);
            linearLayoutMuseum.setVisibility(View.INVISIBLE);
            linearLayoutListBuildings.setVisibility(View.VISIBLE);
            fabAddItem.setVisibility(View.INVISIBLE);

            dwellingHouseAdapter = new DwellingHouseAdapter(Singleton.getInstance().get_listDwellingHouses(), this);
            recyclerView.setAdapter(dwellingHouseAdapter);}

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





    public void EditMuseum(Museum museum){

            linearLayoutDwellingHouse.setVisibility(View.INVISIBLE);
            linearLayoutMuseum.setVisibility(View.VISIBLE);
            linearLayoutListBuildings.setVisibility(View.INVISIBLE);
            fabAddItem.setVisibility(View.VISIBLE);
            tbMuseumAddress = (EditText) findViewById(R.id.tbMuseumAddress);
            tbMuseumFloorsCount = (EditText) findViewById(R.id.tbMuseumFloorsCount);
            tbMuseumStartTime = (EditText) findViewById(R.id.tbMuseumStartTime);
            tbMuseumEndTime = (EditText) findViewById(R.id.tbMuseumEndTime);

            tbMuseumAddress.setText(museum.get_address());
            tbMuseumFloorsCount.setText(museum.get_floorsCount());
            tbMuseumStartTime.setText(museum.get_startTime());
            tbMuseumEndTime.setText(museum.get_endTime());

    }
}
