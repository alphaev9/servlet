import java.util.Date;
import java.util.List;

public class User {
    private String name;
    private String password;
    private Gender gender;
    private Date birthday;
    List<String> hobbys;

    public User() {
    }

    public User(String name, String password, Gender gender, Date birthday, List<String> hobbys) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.hobbys = hobbys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<String> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<String> hobbys) {
        this.hobbys = hobbys;
    }
}
