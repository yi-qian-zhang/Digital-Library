package domain;

/**
 * Class that represents university, a type of user who may log in to the system.
 *
 * <p>Universities log in with permissions including reading papers, downloading
 * papers, uploading papers and deleting papers.</p>
 */
public class University
{
    private int id;
    private String email;
    private String password;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "University{" + "id=" + id + ", email='" + email + '\'' + ", password='" + password + '\'' +
                ", name='" + name + '\'' + '}';
    }
}
