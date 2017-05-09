package com.example.danilwelter.pjabuildings.Model;



public abstract class Building{

    protected long _id;
    public long get_id(){
        return _id;
    }
    public void set_id(long id){
        this._id = id;
    }

    //Адрес с методами get и set
    protected String _address;
    public String get_address() {
        return _address;
    }
    public void set_address(String address) {
        this._address = address;
    }

    //Кол-во этажей
    protected int _floorsCount;
    public int get_floorsCount() {
        return _floorsCount;
    }
    public void set_floorsCount(int floorsCount) {
        this._floorsCount = floorsCount;
    }

    public abstract String GetInfo();

}
