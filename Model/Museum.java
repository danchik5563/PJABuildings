package com.example.danilwelter.pjabuildings.Model;

public class Museum extends Building {

    public String get_address() {
        return _address;
    }
    public void set_address(String address) {
        _address = address;
    }
    public int get_floorsCount() {
        return _floorsCount;
    }
    public void set_floorsCount(int _floorsCount) {
        this._floorsCount = _floorsCount;
    }

    private String _startTime;
    public String get_startTime() {
        return _startTime;
    }
    public void set_startTime(String _startTime) {
        this._startTime = _startTime;
    }

    private String _endTime;
    public String get_endTime() {
        return _endTime;
    }
    public void set_endTime(String _endTime) {
        this._endTime = _endTime;
    }

    public Museum(String _address, int _floorsCount, String _startTime, String _endTime){
        this.set_address(_address);
        this.set_floorsCount(_floorsCount);
        this.set_startTime(_startTime);
        this.set_endTime(_endTime);
    }
    public Museum(){}


    @Override
    public String GetInfo() {
        return "Адрес: " + this.get_address() +
                "\nКол-во этажей: " + this.get_floorsCount() +
                "\nНачало работы: " + this.get_startTime() +
                "\nКонец работы: " + this.get_endTime();
    }
}