package ro.teamnet.zth.api.em;

import org.junit.Test;
import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Location;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 7/7/2016.
 */
public class EntityUtilsTest {
    @Test
    public void testGetTableNameMethod() {
        String tableName = EntityUtils.getTableName(Department.class);
        assertEquals("Table name should be departments!", "departments", tableName);
    }

    @Test
    public void testGetColumnsMethod() {
        // testing for Department
        List<ColumnInfo> expectedStuff = new ArrayList<ColumnInfo>();
        expectedStuff.add(new ColumnInfo("department_name", String.class, "Column", false));
        expectedStuff.add(new ColumnInfo("department_id", Long.class, "Id", true));
        expectedStuff.add(new ColumnInfo("location_id", Location.class, "Column", false));
        assert EntityUtils.getColumns(Department.class).size() == 3;
    }

    @Test
    public void testCastFromSqlType() {
        Object testInteger = EntityUtils.castFromSqlType(new BigDecimal(45), Integer.class);
        Object testLong = EntityUtils.castFromSqlType(new BigDecimal(45), Long.class);
        Object testFloat = EntityUtils.castFromSqlType(new BigDecimal(45), Float.class);
        Object testDouble = EntityUtils.castFromSqlType(new BigDecimal(45), Double.class);
        Object testOther = EntityUtils.castFromSqlType(new String("foo"), Double.class);
        assert (testOther instanceof String && testInteger instanceof Integer && testLong instanceof Long && testFloat instanceof Float && testDouble instanceof Double);
    }

    @Test
    public void testGetFieldsByAnnotations() {
        assert (EntityUtils.getFieldsByAnnotations(Department.class, Column.class).size() == 2 && EntityUtils.getFieldsByAnnotations(Department.class, Id.class).size() == 1);
    }

    @Test
    public void testGetSqlValue() {
        Department dummyDepartment = new Department();
        dummyDepartment.setId((long) 5);
        assert (long)EntityUtils.getSqlValue(dummyDepartment) == 5;
    }
}
