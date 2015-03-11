/**
* Copyright (C) 2015, MEGAADS
*
* Released under the MIT license
*/
package com.megaads.css.optimizer;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author THOQ LUONG Mar 9, 2015 3:51:23 PM
 */
public class CSSStyleRule extends CSSRule {
    //--------------------------------------------------------------------------
    //  Members
    protected final Set<String> classNames = new HashSet<>();
    protected final Set<String> tagNames = new HashSet<>();
    
    //--------------------------------------------------------------------------
    //  Initialization and releasation
    public CSSStyleRule(String content) {
        super(content);
    }

    //--------------------------------------------------------------------------
    //  Getter N Setter
    
    public Set<String> getTagNames() {
        return tagNames;
    }
    
    
    public Set<String> getClassNames(){
        return this.classNames;
    }
    //--------------------------------------------------------------------------
    //  Method binding
    public boolean containsTagName(String tagName){
        String lowerCaseTagName = tagName.toLowerCase();
        return tagNames.contains(lowerCaseTagName);
    }
    
    public boolean containsClassName(String className){
        return classNames.contains(className);
    }
    //--------------------------------------------------------------------------
    //  Implement N Override
    //--------------------------------------------------------------------------
    //  Utils
    //--------------------------------------------------------------------------
    //  Inner class
}
