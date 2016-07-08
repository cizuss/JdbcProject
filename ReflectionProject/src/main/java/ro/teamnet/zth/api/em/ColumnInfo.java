package ro.teamnet.zth.api.em;

/**
 * Created by user on 7/7/2016.
 */
public class ColumnInfo {
    private String columnName;
    private Class columnType;
    private String dbName;
    private boolean isId;
    private Object value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnInfo)) return false;

        ColumnInfo that = (ColumnInfo) o;

        if (isId != that.isId) return false;
        if (!columnName.equals(that.columnName)) return false;
        if (!columnType.equals(that.columnType)) return false;
        return dbName.equals(that.dbName);

    }

    @Override
    public int hashCode() {
        int result = columnName.hashCode();
        result = 31 * result + columnType.hashCode();
        result = 31 * result + dbName.hashCode();
        result = 31 * result + (isId ? 1 : 0);
        return result;
    }

    public ColumnInfo() {

    }

    public ColumnInfo(String columnName) {
        this.columnName = columnName;
    }

    public ColumnInfo(String columnName, Object value) {
        this.columnName = columnName;
        this.value = value;
    }

    public ColumnInfo(String columnName, Class columnType, String dbName, boolean isId) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.dbName = dbName;
        this.isId = isId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Class getColumnType() {
        return columnType;
    }

    public void setColumnType(Class columnType) {
        this.columnType = columnType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "columnName='" + columnName + '\'' +
                ", columnType=" + columnType +
                ", dbName='" + dbName + '\'' +
                ", isId=" + isId +
                ", value=" + value +
                '}';
    }
}
