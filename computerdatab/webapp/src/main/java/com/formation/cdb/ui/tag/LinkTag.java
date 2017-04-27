package com.formation.cdb.ui.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class LinkTag extends TagSupport {

    private static final long serialVersionUID = -9188622442320522262L;
    private String target;
    private int index;
    private int maxInPage;
    private String search;
    private String searchBy;
    private String order;
    private String filterBy;

    /**
     * doStartTag.
     * @return SKIP_BODY
     * @throws JspException JspException.
     */
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut()
                    .println("<a href=\"" + target + "?page=" + index + "&maxInPage=" + maxInPage + "&search=" + search
                            + "&searchBy=" + searchBy + "&order=" + order + "&filterBy=" + filterBy + "\" >");

        } catch (IOException e) {
            throw new JspException("I/O Error", e);
        }
        return EVAL_PAGE;
    }

    /**
     * doEndTag.
     * @return EVAL_PAGE
     * @throws JspException JspException.
     */
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().println("</a>");

        } catch (IOException e) {
            throw new JspException("I/O Error", e);
        }
        return EVAL_PAGE;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMaxInPage() {
        return maxInPage;
    }

    public void setMaxInPage(int maxInPage) {
        this.maxInPage = maxInPage;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }
}
