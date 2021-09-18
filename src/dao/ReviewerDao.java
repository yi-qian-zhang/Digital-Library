package dao;

import domain.Reviewer;

/**
 * Handle interactions with database associated with reviewer.
 */
public interface ReviewerDao
{
    /**
     * Insert new reviewer into the database.
     *
     * @param reviewer Reviewer that will be inserted.
     * @return true if reviewer is inserted successfully.
     */
    boolean addReviewer(Reviewer reviewer);

    /**
     * Check whether the given email is exited in database.
     *
     * @param email Email being checked.
     * @return true if the email does not exist in database.
     */
    boolean checkReviewerEmail(String email);

    /**
     * Check if the major is available by searching in the database.
     *
     * @param major The major of the reviewer to be checked.
     * @return True if the major is available.
     */
    boolean checkReviewerMajor(String major);

    /**
     * Query reviewer by given email and password.
     *
     * @param email    Email of reviewer being searched.
     * @param password Password of reviewer being searched.
     * @return Return reviewer object if corresponding reviewed is found.
     */
    Reviewer findReviewerByEmailAndPassword(String email, String password);
}
