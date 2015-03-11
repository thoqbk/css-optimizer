/**
* Copyright (C) 2015, MEGAADS
*
* Released under the MIT license
*/
package com.megaads.css.optimizer;

/**
 *
 * @author THOQ LUONG Mar 9, 2015 5:56:56 PM
 */
public abstract class CSSRule {

    //--------------------------------------------------------------------------
    //  Members

    protected String content;
    private String selector;

    //--------------------------------------------------------------------------
    //  Initialization and releasation

    public CSSRule(String content) {
        this.content = content;
    }
    //--------------------------------------------------------------------------
    //  Getter N Setter
    
    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }
    //--------------------------------------------------------------------------
    //  Method binding

    

    public String getContent() {
        return this.content;
    }

    //--------------------------------------------------------------------------
    //  Implement N Override
    //--------------------------------------------------------------------------
    //  Utils    
    //--------------------------------------------------------------------------
    //  Inner class
}
