import excel.ParseExcelSheetToList;
import jdbc.DbUtils;
import mapping.FieldNameAndType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vo.Student;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * author:      shfq
 * create date: 2015/9/23.
 */
public class ExcelImportTest {
    private FieldNameAndType[] nameAndTypes;
    List<Student> students;

    @Before
    public void setUp() throws Exception {
        nameAndTypes = new FieldNameAndType[3];
        nameAndTypes[0] = new FieldNameAndType("name", String.class);
        nameAndTypes[1] = new FieldNameAndType("age", int.class);
        nameAndTypes[2] = new FieldNameAndType("height", double.class);

        String excelPath = "students.xls";

        InputStream inputStream = ClassLoader.getSystemResourceAsStream(excelPath);
        ParseExcelSheetToList<Student> parseExcelSheetToList = new ParseExcelSheetToList<Student>(inputStream,
                Student.class, nameAndTypes);
        students = parseExcelSheetToList.getList();
    }

    @Test
    public void excelImportTest() throws Exception{
        int[] results = DbUtils.batchImport(students);
        assert (students.size() == results.length);
        for (int i : results) {
            assert (results[i] == 1);
        }
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = DbUtils.getConnection();
        String sql = "delete from student where name in ('张三', '李四', '王五');";
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

}
