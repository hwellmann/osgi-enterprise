package com.googlecode.osgienterprise.blog.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class Layout extends WebPage {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("title", getPageTitle()));
    }
    
    protected String getPageTitle() {
        return "";
    }

}
