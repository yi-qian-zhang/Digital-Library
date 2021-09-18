package service;

import domain.Reviewer;

/**
 * Handle services associated with reviewer.
 */
public interface ReviewerService
{
    /**
     * Add reviewer.
     *
     * @param reviewer Reviewer that will be added.
     * @return true if the reviewer is added successfully.
     */
    boolean addReviewer(Reviewer reviewer);

    /**
     * Check whether the email that new reviewer inputs is registered before.
     *
     * @param email Email being checked.
     * @return true if the email has not been registered before.
     */
    boolean checkReviewerEmail(String email);

    /**
     * Check if the major is available by searching in database.
     *
     * @param major The major of the reviewer to be checked.
     * @return True if the major is available.
     */
    boolean checkReviewerMajor(String major);

    /**
     * Provide login function for reviewer.
     *
     * @param reviewer Login reviewer.
     * @return Return a reviewer object if login successfully, otherwise return null.
     */
    Reviewer login(Reviewer reviewer);
}
