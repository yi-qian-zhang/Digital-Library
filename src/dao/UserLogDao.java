package dao;

/**
 * Record user login, logout, register information in database.
 */
public interface UserLogDao
{
    /**
     * Record user sign in information in database.
     *
     * @param time     User sign in time in format of yyyy-MM-dd hh:mm:ss.
     * @param identity User sign in identity.
     * @param email    User sign in email.
     * @param ip       User sign in IP address.
     */
    void logSignIn(String time, String identity, String email, String ip);

    /**
     * Record user sign out information in database.
     *
     * @param time     User sign in time in format of yyyy-MM-dd hh:mm:ss.
     * @param identity User sign out identity.
     * @param email    User sign out email.
     * @param ip       User sign out IP address.
     */
    void logSignOut(String time, String identity, String email, String ip);

    /**
     * Record user sign up information in database.
     *
     * @param time     User sign up time in format of yyyy-MM-dd hh:mm:ss.
     * @param identity User sign up identity.
     * @param email    User sign up email.
     * @param ip       User sign up IP address.
     */
    void logSignUp(String time, String identity, String email, String ip);
}
