/**
* Copyright (C) 2015, MEGAADS
*
* Released under the MIT license
*/
package com.megaads.css.optimizer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author THOQ LUONG Mar 9, 2015 6:20:46 PM
 */
public class CSSMediaRule extends NonCSSStyleRule {

    //--------------------------------------------------------------------------
    //  Members
    private final List<CSSRule> children = new ArrayList<>();

    //--------------------------------------------------------------------------
    //  Initialization and releasation
    public CSSMediaRule(String content) {
        super(content);
    }

    //--------------------------------------------------------------------------
    //  Getter N Setter

    public List<CSSRule> getChildren() {
        return children;
    }

    //--------------------------------------------------------------------------
    //  Method binding
    //--------------------------------------------------------------------------
    //  Implement N Override
    //--------------------------------------------------------------------------
    //  Utils
    //--------------------------------------------------------------------------
    //  Inner class
}
