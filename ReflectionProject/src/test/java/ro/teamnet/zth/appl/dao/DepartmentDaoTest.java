package ro.teamnet.zth.appl.dao;

import org.junit.Test;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 7/11/2016.
 */
public class DepartmentDaoTest {
    @Test
    public void testFindById() {
        Department d = new DepartmentDao().callFindById(Department.class, (long)130);
        System.out.println(d);
    }
    @Test
    public void testInsert() {
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("dummydepname");
        newDepartment.setLocation(555);
        Department entity = (Department) new DepartmentDao().callInsert(newDepartment);
        assert (entity.getDepartmentName().equals("dummydepname") && entity.getLocation() == 555);
    }
    @Test
    public void testFindAll() {
        List<Department> departmentList =  new DepartmentDao().callFindAll(Department.class);
        System.out.println(departmentList.size());
    }
    @Test
    public void testGetNextIdVal() {
        Long nextId = new DepartmentDao().callGetNextIdVal("departments", "department_id");
        System.out.println(nextId);
    }
    @Test
    public void testCallByParams() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("location_id",1700);
        List<Department> objectsFound = new DepartmentDao().CallFindByParams(Department.class, paramMap);
        assert objectsFound.size() == 21;
    }
}
