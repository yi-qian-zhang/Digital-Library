package dao;

import domain.University;

/**
 * Handle interactions with database associated with university.
 */
public interface UniversityDao
{
    /**
     * Insert new university into the database.
     *
     * @param university University that will be inserted.
     * @return true if the university is inserted successfully.
     */
    boolean addUniversity(University university);

    /**
     * Check whether the given email is existed in database.
     *
     * @param email Email being checked.
     * @return true if the email does not exist in database.
     */
    boolean checkUniversityEmail(String email);

    /**
     * Query university by given email and password.
     *
     * @param email    Email of university being searched.
     * @param password Password of university being searched.
     * @return Return university object if corresponding university is found.
     */
    University findUniversityByEmailAndPassword(String email, String password);

    /**
     * Query university name for given email.
     *
     * @param email Email of university being queried.
     * @return Name of the university for given email.
     */
    String findNameByEmail(String email);
}
