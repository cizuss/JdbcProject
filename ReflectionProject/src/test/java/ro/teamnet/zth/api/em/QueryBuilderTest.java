package ro.teamnet.zth.api.em;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/7/2016.
 */
public class QueryBuilderTest {
    @Test
    public void testCreateSelect() {
        QueryBuilder testQuery = new QueryBuilder();
        testQuery = testQuery.setQueryType(QueryType.SELECT);
        testQuery = testQuery.setTableName("dummytable");
        List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
        columnInfoList.add(new ColumnInfo("firstcolumn"));
        columnInfoList.add(new ColumnInfo("secondcolumn"));
        testQuery = testQuery.addQueryColumns(columnInfoList);
        testQuery = testQuery.addCondition(new Condition("salary", 500));
        testQuery = testQuery.addCondition(new Condition("cnp", 1000));
        testQuery.addCondition(new Condition("textfield", "blablabla"));
        System.out.println(testQuery.createQuery());
    }
    @Test
    public void testCreateInsert() {
        QueryBuilder testQuery = new QueryBuilder();
        testQuery = testQuery.setQueryType(QueryType.INSERT);
        testQuery.setTableName("dummytable");
        List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
        columnInfoList.add(new ColumnInfo("firstcolumn", 3));
        columnInfoList.add(new ColumnInfo("secondcolumn", 5));
        testQuery = testQuery.addQueryColumns(columnInfoList);
        System.out.println(testQuery.createQuery());
    }
    @Test
    public void testCreateUpdate() {
        QueryBuilder testQuery = new QueryBuilder();
        testQuery.setQueryType(QueryType.UPDATE);
        testQuery.setTableName("dummytable");
        List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
        columnInfoList.add(new ColumnInfo("firstcolumn", 3));
        columnInfoList.add(new ColumnInfo("secondcolumn", 5));
        testQuery = testQuery.addQueryColumns(columnInfoList);
        testQuery = testQuery.addCondition(new Condition("salary", 500));
        testQuery = testQuery.addCondition(new Condition("cnp", 1000));
        System.out.println(testQuery.createQuery());
    }
    @Test
    public void testCreateDelete() {
        QueryBuilder testQuery = new QueryBuilder();
        testQuery.setQueryType(QueryType.DELETE);
        testQuery.setTableName("dummytable");
        testQuery = testQuery.addCondition(new Condition("salary", 500));
        testQuery = testQuery.addCondition(new Condition("cnp", 1000));
        System.out.println(testQuery.createQuery());

    }

    public static void main(String[] args) {
        QueryBuilderTest qb = new QueryBuilderTest();
        qb.testCreateDelete();
        qb.testCreateInsert();
        qb.testCreateUpdate();
        qb.testCreateSelect();
    }
}
