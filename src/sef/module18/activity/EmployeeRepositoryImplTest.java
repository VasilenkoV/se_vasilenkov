package sef.module18.activity;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import java.sql.*;
import java.util.List;
import java.util.Random;

public class EmployeeRepositoryImplTest extends TestCase {

    private Connection conn;
    private String url ;
    private String username;
    private String password;

    public void setUp() throws Exception {
        super.setUp();
        username = "sa";
        password = "";
        Class.forName("org.h2.Driver");
        url = "jdbc:h2:~/test";
        conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(false);
        System.out.println("Connection successfully established!");

    }

    public void tearDown() throws Exception {
        try{
            super.tearDown();
            conn.close();
            System.out.println("Connection closed!");
        } catch (AssertionFailedError e) {
            e.printStackTrace();
        }
    }

    public void testFindEmployeeByID() {
        EmployeeRepository employeeRepositoryImpl = new EmployeeRepositoryImpl(conn);
        try {
            Employee result1 = employeeRepositoryImpl.findEmployeeByID(1);
            assertEquals(result1.getFirstName().toUpperCase(), "JOHN");
            assertEquals(result1.getLastName().toUpperCase(), "DOE");
            assertEquals(result1.getProfLevel(), 1);
            Employee result2 = employeeRepositoryImpl.findEmployeeByID(-6);
            assertNull(result2);
        } catch (HRSSystemException e) {
            fail();
        }

    }

    public void testFindEmployeeByName() {
        EmployeeRepository employeeRepositoryImpl = new EmployeeRepositoryImpl(conn);
        try {
            List<Employee> result1 = employeeRepositoryImpl.findEmployeeByName("JOHN", "DOE");
            assertEquals(result1.get(0).getFirstName().toUpperCase(), "JOHN");
            assertEquals(result1.get(0).getLastName().toUpperCase(), "DOE");
            assertEquals(result1.get(0).getProfLevel(), 1);
            List<Employee> result2 = employeeRepositoryImpl.findEmployeeByName("JOHP", "DUOE");
            assertTrue(result2.isEmpty());
        } catch (HRSSystemException e) {
            fail();
        }
    }

    public void testFindEmployeeByProfLevel() {
        EmployeeRepository employeeRepositoryImpl = new EmployeeRepositoryImpl(conn);
        try {
            List<Employee> result1 = employeeRepositoryImpl.findEmployeeByProfLevel(1);
            assertEquals(result1.get(0).getFirstName().toUpperCase(), "JOHN");
            assertEquals(result1.get(0).getLastName().toUpperCase(), "DOE");
            assertEquals(result1.get(0).getProfLevel(), 1);
            List<Employee> result2 = employeeRepositoryImpl.findEmployeeByProfLevel(-8);
            assertTrue(result2.isEmpty());
        } catch (HRSSystemException e) {
            fail();
        }
    }

    public void testFindEmployeeByProject() {
        EmployeeRepository employeeRepositoryImpl = new EmployeeRepositoryImpl(conn);
        try {
            List<Employee> result1 = employeeRepositoryImpl.findEmployeeByProject(1);
            assertEquals(result1.get(0).getFirstName().toUpperCase(), "JOHN");
            assertEquals(result1.get(0).getLastName().toUpperCase(), "DOE");
            assertEquals(result1.get(0).getProfLevel(), 1);
            List<Employee> result2 = employeeRepositoryImpl.findEmployeeByProject(-8);
            assertTrue(result2.isEmpty());
        } catch (HRSSystemException e) {
            fail();
        }
    }

    public void testInsertEmployee() {
        EmployeeRepository employeeRepositoryImpl = new EmployeeRepositoryImpl(conn);
        try (PreparedStatement pStmt = conn.prepareStatement("SELECT EMPLOYEE_SEQ.CURRVAL FROM DUAL");
             ResultSet rs = pStmt.executeQuery()){
            int employeeID = 0;
            if (rs.next()){
                employeeID = rs.getInt(1);
            }
            int result1 = employeeRepositoryImpl.insertEmployee(new EmployeeImpl(employeeID,"ABC","BCD",1));
            assertEquals(employeeID, result1);
        } catch (HRSSystemException|SQLException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testUpdateEmployee() {
        EmployeeRepository employeeRepositoryImpl = new EmployeeRepositoryImpl(conn);
        Random rnd = new Random(System.currentTimeMillis());
        int employeeID = rnd.nextInt(10);
        try {
            PreparedStatement pStmt = conn.prepareStatement("select * from EMPLOYEE where ID = ?");
            pStmt.setInt(1, employeeID);
            ResultSet rs = pStmt.executeQuery();
            Employee result = null;
            if (rs.next()) {
                result = new EmployeeImpl(rs.getInt("ID"),
                        rs.getString("FIRSTNAME"),
                        rs.getString("LASTNAME"),
                        rs.getInt("PROF_LEVEL"));
            }
            rs.close();
            pStmt.close();
            if (result != null) {
                boolean result1 = employeeRepositoryImpl.updateEmployee(result);
                assertTrue(result1);
            }
        } catch (HRSSystemException|SQLException e) {
            e.printStackTrace();
        }
    }
}