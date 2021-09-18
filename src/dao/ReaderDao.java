package dao;

import domain.Reader;

/**
 * Handle interactions with database associated with reader.
 */
public interface ReaderDao
{
    /**
     * Insert new reader into database.
     *
     * @param reader Reader that will be inserted.
     * @return true if reader is successfully inserted.
     */
    boolean addReader(Reader reader);

    /**
     * Check whether the given email is existed in database.
     *
     * @param email Email being checked.
     * @return true if given email does not exist in database.
     */
    boolean checkReaderEmail(String email);

    /**
     * Query reader by given email and password.
     *
     * @param email    Email of reader being searched.
     * @param password Password of reader being searched.
     * @return Return reader object if corresponding reader is found.
     */
    Reader findReaderByEmailAndPassword(String email, String password);
}
