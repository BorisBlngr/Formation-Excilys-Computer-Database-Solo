package com.formation.cdb.ui.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class LinkTag extends TagSupport {

    private static final long serialVersionUID = -9188622442320522262L;
    private String target;
    private int index;
    private int maxInPage;

    /**
     * doStartTag.
     * @return SKIP_BODY
     * @throws JspException JspException.
     */
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().println("<a href=\"" + target + "?page=" + index + "&maxInPage=" + maxInPage + "\" >");

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
}
