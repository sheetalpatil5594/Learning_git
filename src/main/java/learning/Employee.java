package learning;

public class Employee {
    private String name;
    private  String dept;

    static String id;

    public Employee(String name, String dept) {
        this.name = name;
        this.dept = dept;
    }

    public Employee() {
        this.name = "name";
        this.dept = "dept";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
