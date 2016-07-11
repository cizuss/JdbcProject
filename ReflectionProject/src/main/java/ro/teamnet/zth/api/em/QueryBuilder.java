package ro.teamnet.zth.api.em;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ro.teamnet.zth.api.em.QueryType.*;

/**
 * Created by user on 7/7/2016.
 */
public class QueryBuilder {

    private Object tableName;
    private List<ColumnInfo> queryColumns;
    private QueryType queryType;
    private List<Condition> conditions;

    public QueryBuilder() {
        this.queryColumns = new ArrayList<ColumnInfo>();
        this.conditions = new ArrayList<Condition>();
    }

    private String getValueForQuery(Object value) {
        if (value == null)
            return null;
        if (value instanceof String)
            return "'" + (String) value + "'";
        if (value instanceof java.sql.Date) {
            DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
            return "TO_DATE('" + dateFormat.format((Date) value) + "','mm-dd-YYYY'";
        }
        return value.toString();

    }

    public QueryBuilder addCondition(Condition condition) {
        this.conditions.add(condition);
        return this;
    }
    public QueryBuilder setTableName(Object tableName) {
        this.tableName = tableName;
        return this;
    }

    public QueryBuilder addQueryColumns(List<ColumnInfo> queryColumns) {
        this.queryColumns.addAll(queryColumns);
        return this;
    }

    public QueryBuilder setQueryType(QueryType queryType) {
        this.queryType = queryType;
        return this;
    }

    private StringBuilder createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        for (int i=0; i<queryColumns.size()-1; i++)
            sb.append(queryColumns.get(i).getDbName() + ", ");
        sb.append(queryColumns.get(queryColumns.size()-1).getDbName());
        sb.append(" from " + tableName);
        if (conditions.size() == 0) {
            //sb.append(";");
            return sb;
        }
        sb.append(" where ");
        for (int i=0; i<conditions.size()-1; i++)
            sb.append(conditions.get(i).getColumnName() + "=" + getValueForQuery(conditions.get(i).getValue()) + " and ");
        sb.append(conditions.get(conditions.size()-1).getColumnName() + "=" + getValueForQuery(conditions.get(conditions.size()-1).getValue()));
        return sb;
    }

    private StringBuilder createDeleteQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from " + this.tableName);
        if (conditions.size() == 0) {
            //sb.append(";");
            return sb;
        }
        sb.append(" where ");
        for (int i=0; i<conditions.size()-1; i++)
            sb.append(conditions.get(i).getColumnName() + "=" + getValueForQuery(conditions.get(i).getValue()) + " and ");
        sb.append(conditions.get(conditions.size()-1).getColumnName() + "=" + getValueForQuery(conditions.get(conditions.size()-1).getValue()));
        return sb;
    }

    private StringBuilder createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("update " + this.tableName + " set ");
        for (int i=0; i<queryColumns.size()-1; i++) {
            sb.append(queryColumns.get(i).getDbName() + "=" + getValueForQuery(queryColumns.get(i).getValue()) + ", ");
        }
        sb.append(queryColumns.get(queryColumns.size()-1).getDbName() + "=" + getValueForQuery(queryColumns.get(queryColumns.size()-1).getValue()));
        if (conditions.size() == 0) {
            //sb.append(";");
            return sb;
        }
        sb.append(" where ");
        for (int i=0; i<conditions.size()-1; i++)
            sb.append(conditions.get(i).getColumnName() + "=" + conditions.get(i).getValue() + " and ");
        sb.append(conditions.get(conditions.size()-1).getColumnName() + "=" + getValueForQuery(conditions.get(conditions.size()-1).getValue()));
        return sb;
    }

    private StringBuilder createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into " + this.tableName + " values (");
        for (int i=0; i<queryColumns.size()-1; i++)
            sb.append(getValueForQuery(queryColumns.get(i).getValue()) + ",");
        sb.append(getValueForQuery(queryColumns.get(queryColumns.size()-1).getValue()) + ")");
        return sb;
    }

    public String createQuery() {
        switch (this.queryType) {
            case SELECT:
                return createSelectQuery().toString();
            case DELETE:
                return createDeleteQuery().toString();
            case UPDATE:
                return createUpdateQuery().toString();
            case INSERT:
                return createInsertQuery().toString();
            default:
                return null;
        }
    }
}
