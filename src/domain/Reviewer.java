package domain;

/**
 * Class that represents reviewer, a type of user who may log in to the system.
 *
 * <p>Reviewers log in with permissions including reading papers, downloading papers
 * and reviewing papers.</p>
 */
public class Reviewer
{
    private int id;
    private String email;
    private String password;
    private String name;
    private String major;

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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString()
    {
        return "Reviewer{" + "id=" + id + ", email='" + email + '\'' + ", password='" + password + '\''
                + ", name='" + name + '\'' + ", major='" + major + '\'' + '}';
    }
}
