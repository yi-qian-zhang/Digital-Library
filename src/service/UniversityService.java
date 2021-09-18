package service;

import domain.University;

/**
 * Handle services associated with university.
 */
public interface UniversityService
{
    /**
     * Add university.
     *
     * @param university University that will be added.
     * @return true if the university if added successfully.
     */
    boolean addUniversity(University university);

    /**
     * Check whether the email that new university inputs has been registered before.
     *
     * @param email Email being checked.
     * @return true if the email has not be registered.
     */
    boolean checkUniversityEmail(String email);

    /**
     * Provide login function for university.
     *
     * @param loginUniversity Login university.
     * @return true if the university login successfully, otherwise return null.
     */
    University login(University loginUniversity);

    /**
     * Find name of university for given university email.
     *
     * @param email Email for searching.
     * @return University name of given email.
     */
    String findNameByEmail(String email);
}
