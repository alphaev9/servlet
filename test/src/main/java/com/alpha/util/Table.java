package com.alpha.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Table extends TagSupport {
    private String items;
    private String var;
    private String heads;
    private Collection itemList;
    private List headList;
    private int index;

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getHeads() {
        return heads;
    }

    public void setHeads(String heads) {
        this.heads = heads;
    }

    @Override
    public int doStartTag() throws JspException {
        index = 0;
        Object collection = pageContext.getAttribute(items, PageContext.REQUEST_SCOPE);
        if (collection instanceof HashMap) {
            itemList = ((HashMap) collection).entrySet();
        } else {
            itemList = (Collection) collection;
        }
        if (itemList != null && !itemList.isEmpty()) {
            headList = (List) pageContext.getAttribute(heads, PageContext.REQUEST_SCOPE);
            try {
//                PrintWriter writer = pageContext.getResponse().getWriter();

                JspWriter writer = pageContext.getOut();
                writer.write("<table>\n" + "<tr>");
                StringBuilder headStr = new StringBuilder();
                headList.forEach(head -> {
                    headStr.append("<th>" + head + "</th>\n");
                });
                writer.write(headStr.toString());
                writer.write("</tr>");


            } catch (IOException e) {
                e.printStackTrace();

            }
            pageContext.setAttribute(var, itemList.toArray()[0]);

            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        if (itemList != null && !itemList.isEmpty()) {
            if (index == itemList.size() - 1) {
                try {
                    JspWriter writer = pageContext.getOut();
//                    PrintWriter writer = pageContext.getResponse().getWriter();
                    writer.write("</table>");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.doEndTag();
    }

    @Override
    public int doAfterBody() throws JspException {
        index++;
        if (index < itemList.size()) {
            pageContext.setAttribute(var, itemList.toArray()[index]);
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }
}
