package jdbc;

import vo.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * author:      shfq
 * create date: 2015/9/23.
 */
public class DbUtils {
    private static final String driverName = "com.mysql.jdbc.Driver";
    private static final String driverUrl = "jdbc:mysql://localhost:3306/shfqtest";
    private static final String name = "root";
    private static final String password = "123456";

    static {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int[] batchImport(List<Student> students) throws Exception{
        Connection connection = getConnection();
        if (connection == null) {
            throw new Exception("获取不到数据库连接");
        }
        // 数据库主键自动增长
        String insertSql = "insert student(name, age, height) values(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

        for (int i = 0; i < students.size(); i++) {
            preparedStatement.setString(1, students.get(i).getName());
            preparedStatement.setInt(2, students.get(i).getAge());
            preparedStatement.setDouble(3, students.get(i).getHeight());
            preparedStatement.addBatch();
        }

        int[] result = preparedStatement.executeBatch();
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(driverUrl, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

}
