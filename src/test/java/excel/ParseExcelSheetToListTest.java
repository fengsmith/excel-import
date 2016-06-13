package excel;

import mapping.FieldNameAndType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vo.Student;

import java.io.InputStream;
import java.util.List;

/**
 * author:      shfq
 * create date: 2015/9/23.
 */
public class ParseExcelSheetToListTest {
    private FieldNameAndType[] nameAndTypes;

    @Before
    public void setUp() throws Exception {
        nameAndTypes = new FieldNameAndType[3];
        nameAndTypes[0] = new FieldNameAndType("name", String.class);
        nameAndTypes[1] = new FieldNameAndType("age", int.class);
        nameAndTypes[2] = new FieldNameAndType("height", double.class);
    }


    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetList() throws Exception {
//        String excelPath = "students.xls";
        String excelPath = "test.xlsx";

        InputStream inputStream = ClassLoader.getSystemResourceAsStream(excelPath);
        ParseExcelSheetToList<Student> parseExcelSheetToList = new ParseExcelSheetToList<Student>(inputStream,
                                                                       Student.class, nameAndTypes);
        List<Student> students = parseExcelSheetToList.getList();
        assert (students != null && students.size() == 3);

    }
}