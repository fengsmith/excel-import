package jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vo.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * author:      shfq
 * create date: 2015/9/23.
 */
public class DbUtilsTest {
    List<Student> students;

    @Before
    public void setUp() throws Exception {
        students = new ArrayList<Student>();
        Student student1 = new Student();
        student1.setName("mike");
        student1.setAge(27);
        student1.setHeight(1.75);
        students.add(student1);

        Student student2 = new Student();
        student2.setName("john");
        student2.setAge(28);
        student2.setHeight(1.80);
        students.add(student2);

        Student student3 = new Student();
        student3.setName("mary");
        student3.setAge(32);
        student3.setHeight(1.70);
        students.add(student3);

    }

    @After
    public void tearDown() throws Exception {
        Connection connection = DbUtils.getConnection();
        String sql = "delete from student where name in ('mike', 'john', 'mary');";
        Statement statement = connection.createStatement();
        statement.execute(sql);

        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Test
    public void testBatchImport() throws Exception {
        int[] results = DbUtils.batchImport(students);
        assert (students.size() == results.length);
        for (int i : results) {
            assert (results[i] == 1);
        }
    }
}