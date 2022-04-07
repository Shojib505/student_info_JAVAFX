package models;

public class StudentSS {

    private int id;
    private String name;
    private int age;
    private String address;
    private String email;
    private String profession;

    public StudentSS() {
    }

    public StudentSS(String name, int age, String address, String email, String profession) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.email = email;
        this.profession = profession;
    }

    public StudentSS(int id, String name, int age, String address, String email, String profession) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.email = email;
        this.profession = profession;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "StudentSS{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", profession='" + profession + '\'' +
                '}';
    }
}
