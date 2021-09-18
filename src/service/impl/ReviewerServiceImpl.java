package service.impl;

import dao.ReviewerDao;
import dao.impl.ReviewerDaoImpl;
import domain.Reviewer;
import service.ReviewerService;

public class ReviewerServiceImpl implements ReviewerService
{
    private final ReviewerDao dao = new ReviewerDaoImpl();

    @Override
    public Reviewer login(Reviewer reviewer)
    {
        return dao.findReviewerByEmailAndPassword(reviewer.getEmail(), reviewer.getPassword());
    }

    @Override
    public boolean checkReviewerEmail(String email) {
        return dao.checkReviewerEmail(email);
    }

    @Override
    public boolean checkReviewerMajor(String major) {
        return dao.checkReviewerMajor(major);
    }

    @Override
    public boolean addReviewer(Reviewer reviewer) {
        return dao.addReviewer(reviewer);
    }
}
