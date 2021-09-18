package dao;

import java.util.List;

/**
 * Handle interactions with database associated with major.
 */
public interface MajorDao
{
    /**
     * Get all the available majors in database.
     *
     * @return A list of all the majors.
     */
    List<String> findMajors();
}
