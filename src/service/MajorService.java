package service;

import java.util.List;

/**
 * Handle services associated with major.
 */
public interface MajorService
{
    /**
     * Get all the available majors in database.
     *
     * @return The list of all available majors.
     */
    List<String> findMajors();
}
