package domain;

/**
 * Class that represents paper.
 */
public class Paper
{
    private int id;
    private String title;
    private String author;
    private String university;
    private String outline;
    private String keyword;
    private String major;
    private int rev_id_1;
    private int rev_id_2;
    private int rev_id_3;
    private String comment_1;
    private String comment_2;
    private String comment_3;
    private int acceptance_1;
    private int acceptance_2;
    private int acceptance_3;
    private int is_published;
    private String submit_date;
    private String publish_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getRev_id_1() {
        return rev_id_1;
    }

    public void setRev_id_1(int rev_id_1) {
        this.rev_id_1 = rev_id_1;
    }

    public int getRev_id_2() {
        return rev_id_2;
    }

    public void setRev_id_2(int rev_id_2) {
        this.rev_id_2 = rev_id_2;
    }

    public int getRev_id_3() {
        return rev_id_3;
    }

    public void setRev_id_3(int rev_id_3) {
        this.rev_id_3 = rev_id_3;
    }

    public String getComment_1() {
        return comment_1;
    }

    public void setComment_1(String comment_1) {
        this.comment_1 = comment_1;
    }

    public String getComment_2() {
        return comment_2;
    }

    public void setComment_2(String comment_2) {
        this.comment_2 = comment_2;
    }

    public String getComment_3() {
        return comment_3;
    }

    public void setComment_3(String comment_3) {
        this.comment_3 = comment_3;
    }

    public int getAcceptance_1() {
        return acceptance_1;
    }

    public void setAcceptance_1(int acceptance_1) {
        this.acceptance_1 = acceptance_1;
    }

    public int getAcceptance_2() {
        return acceptance_2;
    }

    public void setAcceptance_2(int acceptance_2) {
        this.acceptance_2 = acceptance_2;
    }

    public int getAcceptance_3() {
        return acceptance_3;
    }

    public void setAcceptance_3(int acceptance_3) {
        this.acceptance_3 = acceptance_3;
    }

    public int getIs_published() {
        return is_published;
    }

    public void setIs_published(int is_published) {
        this.is_published = is_published;
    }

    public String getSubmit_date() {
        return submit_date;
    }

    public void setSubmit_date(String submit_date) {
        this.submit_date = submit_date;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }
}
