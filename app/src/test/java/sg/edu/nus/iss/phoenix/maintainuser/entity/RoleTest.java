package sg.edu.nus.iss.phoenix.maintainuser.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by GONG MENGNAN on 2/10/17.
 */
public class RoleTest {

    private Role role;
    private String r = "role1";

    @Before
    public void setUp() throws Exception {
        role = new Role(r);
    }

    @After
    public void tearDown() throws Exception {
        role = null;
    }

    @Test
    public void getRole() throws Exception {
        assertEquals(role.getRole(),r);
    }

}