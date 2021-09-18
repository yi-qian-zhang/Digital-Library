package service.impl;

import dao.MajorDao;
import dao.impl.MajorDaoImpl;
import service.MajorService;
import java.util.List;

public class MajorServiceImpl implements MajorService
{
    private final MajorDao dao = new MajorDaoImpl();

    @Override
    public List<String> findMajors() {
        return dao.findMajors();
    }
}
