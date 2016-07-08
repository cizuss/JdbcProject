package ro.teamnet.zth.api.em;

/**
 * Created by user on 7/7/2016.
 */
public class Condition {
    private String columnName;
    private Object value;

    public Condition(String columnName, Object value) {
        this.columnName = columnName;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
