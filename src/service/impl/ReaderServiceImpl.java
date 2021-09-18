package service.impl;

import dao.ReaderDao;
import dao.impl.ReaderDaoImpl;
import domain.Reader;
import service.ReaderService;

public class ReaderServiceImpl implements ReaderService
{
    private final ReaderDao dao = new ReaderDaoImpl();

    @Override
    public Reader login(Reader reader)
    {
        return dao.findReaderByEmailAndPassword(reader.getEmail(), reader.getPassword());
    }

    @Override
    public boolean checkReaderEmail(String email) {
        return dao.checkReaderEmail(email);
    }

    @Override
    public boolean addReader(Reader reader) {
        return dao.addReader(reader);
    }
}
