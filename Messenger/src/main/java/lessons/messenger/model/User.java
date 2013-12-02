package lessons.messenger.model;

public class User
{
    public int id;
    public String first_name;
    public String last_name;
    public String email;
    public String password;
    public String token;

    public String getIdentity()
    {
        return this.first_name + " " + this.last_name;
    }
}