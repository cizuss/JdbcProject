package ro.teamnet.zth.api.em;

import org.junit.Test;
import ro.teamnet.zth.appl.domain.Department;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImplTest {
    @Test
    public void testFindById() {
        Department d = EntityManagerImpl.findById(Department.class, (long) 30);
        System.out.println(d);
    }
}
