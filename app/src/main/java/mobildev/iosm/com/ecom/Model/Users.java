package mobildev.iosm.com.ecom.Model;

public class Users
{
    private  String password, phone, username;

    public Users()
    {

    }

    public Users(String password, String phone, String username) {
        this.password = password;
        this.phone = phone;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
