package vo;

/**
 * author:      shfq
 * create date: 2015/9/23.
 */
public class Student {
    private int no;
    private String name;
    private int age;
    private double height;

    public String getName() {
        return name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
