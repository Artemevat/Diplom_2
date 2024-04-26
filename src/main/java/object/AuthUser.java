package object;

public class AuthUser {
    private String email;
    private String password;

    //конструктор с параметрами
    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //конструктор без параметров
    public AuthUser() {
    }

    //геттеры и сеттеры полей
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
