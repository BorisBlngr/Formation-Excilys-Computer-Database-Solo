package com.formation.cdb.ui.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.formation.cdb.ui.Page;

public class PaginationTag extends TagSupport {
    private static final long serialVersionUID = -5598951267382976924L;

    private int pageIndex;
    private int maxPage;
    private int maxInPage;
    private List<Page> pageList;

    /**
     * doStartTag.
     * @return SKIP_BODY
     * @throws JspException JspException.
     */
    public int doStartTag() throws JspException {
        try {

            // System.out.println("<link:to target=\"dashboard\" index=\"" + 1 +
            // "\" maxInPage=\"" + 2 + "\" />");
            if (pageIndex != 1) {
                pageContext.getOut().println("<li><a href=\"?page=" + new Integer(pageIndex - 1) + "&maxInPage="
                        + maxInPage + "\"aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span></a></li>");
            }

            for (Page page : pageList) {
                pageContext.getOut().println("<li class=\"" + page.getActive() + "\"><a href=\"?page=" + page.getIndex()
                        + "&maxInPage=" + maxInPage + "\">" + page.getIndex() + "</a></li>");
            }

            if (pageIndex != maxPage) {
                pageContext.getOut().println("<li><a href=\"?page=" + new Integer(pageIndex + 1) + "&maxInPage="
                        + maxInPage + "\"aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span></a></li>");
            }

        } catch (IOException e) {
            throw new JspException("I/O Error", e);
        }
        return SKIP_BODY;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getMaxInPage() {
        return maxInPage;
    }

    public void setMaxInPage(int maxInPage) {
        this.maxInPage = maxInPage;
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

}
