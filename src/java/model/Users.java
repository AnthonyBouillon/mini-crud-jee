package model;

public class Users {

    // Attribute corresponding to the database fields
    private Integer id = 0;
    private String name = "";
    private String password = "";
    private String email = "";
    private String sex = "";
    private String country = "";

    // -------------------------------------------------------------------------
    //                          GETTERS AND SETTERS
    // -------------------------------------------------------------------------
    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSex() {
        return this.sex;
    }

    public String getCountry() {
        return this.country;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
