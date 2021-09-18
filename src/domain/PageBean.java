package domain;

import java.util.List;

/**
 * Class that encapsulates the information and method for pagination function.
 *
 * @param <T> Type of elements that will be displayed on the web page.
 */
public class PageBean<T>
{
    private int totalCount; // Total numbers of papers
    private int totalPage; // Total numbers of pages
    private List<T> list; // Papers in each page
    private int currentPage; // Current page
    private int rows; // Total numbers of papers for each page

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
