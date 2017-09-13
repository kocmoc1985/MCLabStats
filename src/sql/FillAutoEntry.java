/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

/**
 *
 * @author KOCMOC
 */
public class FillAutoEntry {
    private String colName;
    private String value;
    private boolean number;

    public FillAutoEntry(String tableName, String value,boolean number) {
        this.colName = tableName;
        this.value = value;
        this.number = number;
    }

    public String getColName() {
        return colName;
    }

    public String getValue() {
        return value;
    }

    public boolean isNumber() {
        return number;
    }
    
    
    
    
}
