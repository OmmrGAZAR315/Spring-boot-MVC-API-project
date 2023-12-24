package com.example.costaricaCaffeAPI.Requests;

public class WhereObject {
    private String updateColumn;
    private String updateValue;
    private String where;
    private String whereValue;

    public WhereObject(String updateColumn, String updateValue, String where, String whereValue) {
        setWhere(where);
        setWhereValue(whereValue);
        setUpdateColumn(updateColumn);
        setUpdateValue(updateValue);
    }

    public String getUpdateColumn() {
        return updateColumn;
    }

    public void setUpdateColumn(String updateColumn) {
        this.updateColumn = updateColumn;
    }

    public String getUpdateValue() {
        return updateValue;
    }

    public void setUpdateValue(String updateValue) {
        this.updateValue = updateValue;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getWhereValue() {
        return whereValue;
    }

    public void setWhereValue(String whereValue) {
        this.whereValue = whereValue;
    }
}