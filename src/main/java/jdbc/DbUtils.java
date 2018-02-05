package jdbc;

import vo.Student;

import java.io.File;
import java.sql.*;
import java.util.List;

/**
 * author:      shfq
 * create date: 2015/9/23.
 */
public class DbUtils {
    private static final String driverName = "com.mysql.jdbc.Driver";
    private static final String driverUrl = "jdbc:mysql://";
    private static final String name = "";
    private static final String password = "";

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

    public static void main(String[] args) {
//        String s = "'new_answer_card_order_info'\n" +
//                "'new_merchandise_order_info'\n" +
//                "'new_order'\n" +
//                "'new_order_payment_info'\n" +
//                "'new_parent_order'\n" +
//                "'new_print_info'\n" +
//                "'new_receive_info'\n" +
//                "'new_refund_info'\n" +
//                "'new_ship'\n" +
//                "'new_task_info'\n" +
//                "'new_work_flow_info'\n" +
//                "'new_work_flow_log'";
//        String[] ss = s.split("\n");
//
//        for (String tableName : ss) {
//            String sql = getCreateTableSql(tableName.replace("'", ""));
//            sql += ";";
//            System.out.println(sql);
//        }
//
        long l1 = System.currentTimeMillis();
        int count = recursiveCount(new File("/Users/shifengqiang/projects/mybatis-3"));
        long l2 = System.currentTimeMillis();
        System.out.println("耗时:" + (l2 - l1));
        System.out.println(count);
    }

    private static String getCreateTableSql(String tableName) {
        String createSql = "";
        try {
            Connection connection = getConnection();
            String template = "show create table %s ";
            String sql = String.format(template, tableName);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    if (i == 1) {
                        continue;
                    }
                    createSql = rs.getString(i);
                }
            }
            rs.close();
            preparedStatement.close();
            connection.close();;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return createSql;
    }

    public static int recursiveCount(File file) {
        if (!file.isDirectory()) {
            String name = file.getName();
            if (name.endsWith(".java") && !name.endsWith("Test.java")) {
                return 1;
            }
            return 0;
        }
        File[] files = file.listFiles();
        if (files.length == 0) {
            return 0;
        }
        int count = 0;
        for (File temp : files) {
            count += recursiveCount(temp);
        }

        return count;
    }

}
