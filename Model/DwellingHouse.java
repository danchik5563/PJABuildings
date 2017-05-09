package com.example.danilwelter.pjabuildings.Model;


public class DwellingHouse extends Building {

    private int _apartmentsCount;
    public int get_apartmentsCount() {
        return _apartmentsCount;
    }
    public void set_apartmentsCount(int apartmentsCount) {
        this._apartmentsCount = apartmentsCount;
    }

    public DwellingHouse(String _address, int _floorsCount, int _apartmentsCount){
        this.set_address(_address);
        this.set_floorsCount(_floorsCount);
        this.set_apartmentsCount(_apartmentsCount);
    }

    public DwellingHouse(int id, String _address, int _floorsCount, int _apartmentsCount){
        this._id = id;
        this.set_address(_address);
        this.set_floorsCount(_floorsCount);
        this.set_apartmentsCount(_apartmentsCount);
    }

    public DwellingHouse(){}

    @Override
    public String GetInfo() {
        return "Адрес: " + this.get_address() +
                "\nКол-во этажей: " + this.get_floorsCount() +
                "\nКол-во квартир: " + this.get_apartmentsCount();

    }
}