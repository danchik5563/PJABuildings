package com.example.danilwelter.pjabuildings.Model;



public abstract class Building{

    //Адрес с методами get и set
    protected String _address;
    public String get_address() {
        return _address;
    }
    public void set_address(String _address) {
        this._address = _address;
    }

    //Кол-во этажей
    protected int _floorsCount;
    public int get_floorsCount() {
        return _floorsCount;
    }
    public void set_floorsCount(int _floorsCount) {
        this._floorsCount = _floorsCount;
    }

    public abstract String GetInfo();

}
