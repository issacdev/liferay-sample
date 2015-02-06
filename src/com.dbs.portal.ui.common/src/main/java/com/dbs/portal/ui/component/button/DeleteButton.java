package com.dbs.portal.ui.component.button;

import com.dbs.portal.ui.util.LanguageSwitcher;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Runo;

public class DeleteButton extends Button {
    
    /**
     * Creates a new push button. The value of the push button is false and it
     * is immediate by default.
     * 
     */
    public DeleteButton() {
    	super();
//    	this.addStyleName("deleteButton");
    	this.addStyleName("dbsButton");
    }

    /**
     * Creates a new push button.
     * 
     * The value of the push button is false and it is immediate by default.
     * 
     * @param caption
     *            the Button caption.
     */
    public DeleteButton(String caption) {
        super();
        setCaption(caption);
        createStyle();
    }

    /**
     * Creates a new push button with click listener.
     * 
     * @param caption
     *            the Button caption.
     * @param listener
     *            the Button click listener.
     */
    public DeleteButton(String caption, ClickListener listener) {
    	this(caption);
        addListener(listener);
        createStyle();
    }

    /**
     * Creates a new push button with a method listening button clicks. Using
     * this method is discouraged because it cannot be checked during
     * compilation. Use
     * {@link #Button(String, com.vaadin.ui.Button.ClickListener)} instead. The
     * method must have either no parameters, or only one parameter of
     * Button.ClickEvent type.
     * 
     * @param caption
     *            the Button caption.
     * @param target
     *            the Object having the method for listening button clicks.
     * @param methodName
     *            the name of the method in target object, that receives button
     *            click events.
     */
    public DeleteButton(String caption, Object target, String methodName) {
        this(caption);
        addListener(ClickEvent.class, target, methodName);
        createStyle();
    }
	
    protected void createStyle(){
//    	this.addStyleName("deleteButton");
    	this.addStyleName("dbsButton");
    }
    
}
