/**
 * Copyright (C) 2015, MEGAADS - All Rights Reserved
 *
 * This software is released under the terms of the proprietary license.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.megaads.css.optimizer.test;

import com.megaads.css.optimizer.CSSOptimizer;
import java.io.IOException;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description of TestCouponsohot
 *
 * @author THO Q LUONG Aug 25, 2015 11:54:02 AM
 */
public class TestCouponsohot {

    //--------------------------------------------------------------------------
    //  Members
    private final Logger logger = LoggerFactory.getLogger(TestCSSOptimizer.class);

    //--------------------------------------------------------------------------
    //  Initialization and releasation
    @BeforeClass
    public static void setup() {
        PropertyConfigurator.configure(TestCSSOptimizer.class.getResource("/com/megaads/css/optimizer/resource/log4j.properties"));
    }

    //--------------------------------------------------------------------------
    //  Getter N Setter
    //--------------------------------------------------------------------------
    //  Method binding
    @Test
    public void testOptimizer() throws IOException {

        String projectDirectory = System.getProperty("user.dir");
        String resourceDirectory = projectDirectory
                + "/src/main/resources/com/megaads/css/optimizer/resource/couponsohot";
        //Initialize CSSOptimizer with target css file(file will be optimized) 
        //and output file (result file after optimizing target cssfile)
        CSSOptimizer optimizer = new CSSOptimizer(resourceDirectory + "/bootstrap.css",
                resourceDirectory + "/bootstrap.min.css");

        //add sample files
        optimizer.addHtmlFile(resourceDirectory + "/home.html");
        optimizer.addHtmlFile(resourceDirectory + "/ebay-coupons.html");
        optimizer.addHtmlFile(resourceDirectory + "/stores.html");
        optimizer.addHtmlFile(resourceDirectory + "/categories.html");
        optimizer.addHtmlFile(resourceDirectory + "/shoe.html");
        optimizer.addHtmlFile(resourceDirectory + "/extra-20-off-select-juniors-kids-clearance.html");
        optimizer.addHtmlFile(resourceDirectory + "/about.html");
        optimizer.addHtmlFile(resourceDirectory + "/blog.html");
        optimizer.addHtmlFile(resourceDirectory + "/father-s-day-giveaway-2014.html");
        optimizer.addHtmlFile(resourceDirectory + "/login.html");

        //some classes or tags are added using Javascript code, declare them here:
        optimizer.keepClassName("placeholder");
        optimizer.keepClassName("none-click");
        optimizer.keepClassName("moreellipses");
        optimizer.keepClassName("morecontent");
        optimizer.keepClassName("morelink");
        optimizer.keepClassName("less");
        optimizer.keepClassName("less-info");
        optimizer.keepClassName("more-info");
        optimizer.keepClassName("img-thumbnail");
        optimizer.keepClassName("post");
        optimizer.keepClassName("post-content");
        optimizer.keepClassName("img-thumbnail");
        optimizer.keepClassName("related_posts");
        optimizer.keepClassName("yiiPager");
        optimizer.keepClassName("pagination");
        optimizer.keepClassName("hover");
        optimizer.keepClassName("coupon-code");
        optimizer.keepClassName("reveal-code");
        optimizer.keepClassName("btn");
        optimizer.keepClassName("btn-info");
        optimizer.keepClassName("btn-inner");
        optimizer.keepClassName("curl");
        optimizer.keepClassName("selected");
        optimizer.keepClassName("comment-list");
        optimizer.keepClassName("loading-comment");
        optimizer.keepClassName("loading-form");
        optimizer.keepClassName("has-error");
        optimizer.keepClassName("text-error");
        optimizer.keepClassName("control-label");
        optimizer.keepClassName("comment-desc");
        optimizer.keepClassName("comment-author");
        optimizer.keepClassName("auto-complete");
        optimizer.keepClassName("invalid");
        optimizer.keepClassName("text-coupon-code");
        optimizer.keepClassName("icon");
        optimizer.keepClassName("peelie");
        optimizer.keepClassName("in");
        optimizer.keepClassName("disabled");
        optimizer.keepClassName("acticeCmm");
        optimizer.keepClassName("hide-object");
        optimizer.keepClassName("loading");
        optimizer.keepClassName("rendered");
        optimizer.keepClassName("alert");
        optimizer.keepClassName("alert-danger");
        optimizer.keepClassName("alert-success");
        optimizer.keepClassName("active");
        optimizer.keepClassName("voted");
        
        optimizer.keepClassName("tooltip");
        optimizer.keepClassName("fade");
        optimizer.keepClassName("right");
        
        optimizer.keepClassName("tooltip-arrow");
        optimizer.keepClassName("tooltip-inner");
        
        optimizer.keepClassName("popover");
        optimizer.keepClassName("bottom");
        optimizer.keepClassName("arrow");
        optimizer.keepClassName("popover-title");
        optimizer.keepClassName("popover-content");
        optimizer.keepClassName("clearfix");
        optimizer.keepClassName("hide");
        
        
        
        //execute optimizing
        optimizer.optimize();
    }

    //--------------------------------------------------------------------------
    //  Implement N Override
    //--------------------------------------------------------------------------
    //  Utils    
    //--------------------------------------------------------------------------
    //  Inner class
}
