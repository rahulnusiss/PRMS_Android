package sg.edu.nus.iss.phoenix.maintainuser.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by GONG MENGNAN on 2/10/17.
 */
public class UserTest {

    private User user;
    private String id="id1";
    private String name="name2";
    private String password="password3";
    private String r1="r1";
    private String r2="r2";
    private ArrayList<Role> roles = new ArrayList<Role>();

    @Before
    public void setUp() throws Exception {
        roles.add(new Role(r1));
        roles.add(new Role(r2));
        user = new User(id,name,password,roles);
    }

    @After
    public void tearDown() throws Exception {
        roles = new ArrayList<Role>();
        user = null;
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(user.getUserId(),id);
    }

    @Test
    public void getUserName() throws Exception {
        assertEquals(user.getUserName(),name);
    }

    @Test
    public void getUserPassword() throws Exception {
        assertEquals(user.getUserPassword(),password);
    }

    @Test
    public void getRoles() throws Exception {
        assertEquals(user.getRoles().size(),2);
        assertEquals(user.getRoles().get(0).getRole(),r1);
        assertEquals(user.getRoles().get(0).getRole(),r1);
    }

    @Test
    public void setUserId() throws Exception {
        user.setUserId("id4");
        assertEquals(user.getUserId(),"id4");
    }

    @Test
    public void setUserName() throws Exception {
        user.setUserName("name5");
        assertEquals(user.getUserName(),"name5");
    }

    @Test
    public void setUserPassword() throws Exception {
        user.setUserPassword("password6");
        assertEquals(user.getUserPassword(),"password6");
    }

}