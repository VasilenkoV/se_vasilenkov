package sef.module18.activity;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import java.sql.*;
import java.util.List;
import java.util.Random;

public class ProjectRepositoryImplTest extends TestCase {

    private Connection conn;

    public void setUp() throws Exception {
        super.setUp();
        String username = "sa";
        String password = "";
        Class.forName("org.h2.Driver");
        String url = "jdbc:h2:~/test";
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

    public void testFindProjectByID() {
        ProjectRepository projectRepositoryImpl = new ProjectRepositoryImpl(conn);
        try {
            Project result1 = projectRepositoryImpl.findProjectByID(1);
            assertTrue(result1.getProjectName().toUpperCase().equalsIgnoreCase("Online Insurance System"));
            assertTrue(result1.getProjectDescription().toUpperCase().equalsIgnoreCase("A web application that automates insurance transactions."));
            Project result2 = projectRepositoryImpl.findProjectByID(-6);
            assertNull(result2);
        } catch (HRSSystemException e) {
            fail();
        }
    }

    public void testFindProjectByName() {
        ProjectRepository projectRepositoryImpl = new ProjectRepositoryImpl(conn);
        try {
            List<Project> result1 = projectRepositoryImpl.findProjectByName("Online Insurance System");
            assertTrue(result1.get(0).getProjectName().toUpperCase().equalsIgnoreCase("Online Insurance System"));
            assertTrue(result1.get(0).getProjectDescription().toUpperCase().equalsIgnoreCase("A web application that automates insurance transactions."));
            List<Project> result2 = projectRepositoryImpl.findProjectByName("123");
            assertTrue(result2.isEmpty());
        } catch (HRSSystemException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testFindProjectByEmployee() {
        ProjectRepository projectRepositoryImpl = new ProjectRepositoryImpl(conn);
        try {
            List<Project> result1 = projectRepositoryImpl.findProjectByEmployee(1);
            assertTrue(result1.get(0).getProjectName().toUpperCase().equalsIgnoreCase("Online Insurance System"));
            assertTrue(result1.get(0).getProjectDescription().toUpperCase().equalsIgnoreCase("A web application that automates insurance transactions."));
            List<Project> result2 = projectRepositoryImpl.findProjectByEmployee(-1);
            assertTrue(result2.isEmpty());
        } catch (HRSSystemException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testInsertProject() {
        ProjectRepository projectRepositoryImpl = new ProjectRepositoryImpl(conn);
        try (PreparedStatement pStmt = conn.prepareStatement("SELECT max(ID) FROM PROJECT");
             ResultSet rs = pStmt.executeQuery()){
            int projectID = 0;
            if (rs.next()){
                projectID = rs.getInt(1);
            }
            int result1 = projectRepositoryImpl.insertProject(new ProjectImpl(++projectID,"ABC","BCD"));
            assertEquals(projectID, result1);
        } catch (HRSSystemException| SQLException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testUpdateProject() {
        ProjectRepository projectRepositoryImpl = new ProjectRepositoryImpl(conn);
        Random rnd = new Random(System.currentTimeMillis());
        int employeeID = rnd.nextInt(10);
        try {
            PreparedStatement pStmt = conn.prepareStatement("select * from Project where ID = ?");
            pStmt.setInt(1, employeeID);
            ResultSet rs = pStmt.executeQuery();
            Project result = null;
            if (rs.next()) {
                result = new ProjectImpl(rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"));
            }
            rs.close();
            pStmt.close();
            if (result != null) {
                boolean result1 = projectRepositoryImpl.updateProject(result);
                assertTrue(result1);
            }
        } catch (HRSSystemException|SQLException e) {
            e.printStackTrace();
        }
    }
}