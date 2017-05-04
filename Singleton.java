package com.example.danilwelter.pjabuildings;

import com.example.danilwelter.pjabuildings.Model.DwellingHouse;
import com.example.danilwelter.pjabuildings.Model.Museum;

import java.util.ArrayList;


public class Singleton {
    private static final Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private ArrayList<Museum> _listMuseums = new ArrayList<>();
    private ArrayList<DwellingHouse> _listDwellingHouses = new ArrayList<>();
    private Museum EditableMuseumObject;
    private DwellingHouse EditableDwellingHouseObject;

    public ArrayList<DwellingHouse> get_listDwellingHouses() {
        return _listDwellingHouses;
    }
    public void set_listDwellingHouses(ArrayList<DwellingHouse> _listDwellingHouses) {
        this._listDwellingHouses = _listDwellingHouses;
    }

    public ArrayList<Museum> get_listMuseums() {
        return _listMuseums;
    }
    public void set_listMuseums(ArrayList<Museum> _listMuseums) {
        this._listMuseums = _listMuseums;
    }

    public Museum getEditableMuseumObject() {
        return EditableMuseumObject;
    }
    public void setEditableMuseumObject(Museum editableMuseumObject) {
        EditableMuseumObject = editableMuseumObject;
    }

    public DwellingHouse getEditableDwellingHouseObject() {
        return EditableDwellingHouseObject;
    }
    public void setEditableDwellingHouseObject(DwellingHouse editableDwellingHouseObject) {
        EditableDwellingHouseObject = editableDwellingHouseObject;
    }
}
