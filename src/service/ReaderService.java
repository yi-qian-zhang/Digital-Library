package service;

import domain.Reader;

/**
 * Handle services associated with reader.
 */
public interface ReaderService
{
    /**
     * Add reader.
     *
     * @param reader Reader that will be added.
     * @return true the reader is added successfully.
     */
    boolean addReader(Reader reader);

    /**
     * Check whether the email that new reader inputs has been registered before.
     *
     * @param email Email being checked.
     * @return true if the email has not been registered.
     */
    boolean checkReaderEmail(String email);

    /**
     * Provide login function for reader.
     *
     * @param user Login reader.
     * @return Return a reader object if login successfully, otherwise return null.
     */
    Reader login(Reader user);
}
