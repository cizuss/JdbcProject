package ro.teamnet.zth.appl.dao;

import org.junit.Test;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 7/11/2016.
 */
public class LocationDaoTest {
    @Test
    public void testFindById() {
        Location d = new LocationDao().callFindById(Location.class, (long)1000);
        assert (d.getId()==1000);
    }
    @Test
    public void testInsert() {
        Location newLocation = new Location((long) 50, "fdgfdgfd", "42232", "vsdfsd", "dfgfdgd");
        Location entity = (Location) new LocationDao().callInsert(newLocation);
        assert (entity.getPostalCode().equals("42232"));
    }
    @Test
    public void testFindAll() {
        List<Location> departmentList =  new LocationDao().callFindAll(Location.class);
        System.out.println(departmentList.size());
    }
    @Test
    public void testGetNextIdVal() {
        Long nextId = new LocationDao().callGetNextIdVal("locations", "location_id");
        System.out.println(nextId);
    }
    @Test
    public void testCallByParams() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("location_id",1700);
        List<Location> objectsFound = new LocationDao().CallFindByParams(Location.class, paramMap);
        System.out.println(objectsFound.size());
    }
}
