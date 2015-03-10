/**
 * Copyright (C) 2015, GIAYBAC.COM
 * 
* Released under the MIT license
 */
package com.megaads.css.optimizer.test;

import com.megaads.css.optimizer.CSSOptimizer;
import com.megaads.css.optimizer.CSSStyleRule;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author THOQ LUONG Mar 2, 2015 5:27:03 PM
 */
public class TestCSSOptimizer {

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
                + "\\src\\main\\resources\\com\\megaads\\css\\optimizer\\resource";
        //Initialize CSSOptimizer with target css file(file will be optimized) 
        //and output file (result file after optimizing target cssfile)
        CSSOptimizer optimizer = new CSSOptimizer(resourceDirectory + "\\bootstrap.css",
                resourceDirectory + "\\bootstrap.min.css");

        //add sample files
        optimizer.addHtmlFile(resourceDirectory + "\\about-us.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\airhead.com.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\amazon.com.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\apparel-amp-amp-accessories.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\blog.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\bodywear.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\categories.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\choosing-perfect-top-for-date-night.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\home.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\stores.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\modal.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\coupon-code-2015-find-coupons-discount-codes.htm");
        optimizer.addHtmlFile(resourceDirectory + "\\up-to-70-off-electronics-outlet-free-shipping.htm");
        optimizer.addHtmlFile("https://github.com/thoqbk");

        //some classes or tags are added using Javascript code, declare them here:
        optimizer.keepClassName("img-thumbnail");
        optimizer.keepClassName("pagination");
        optimizer.keepClassName("icon");
        optimizer.keepClassName("alert");
        optimizer.keepClassName("alert-danger");
        optimizer.keepClassName("alert-success");
        optimizer.keepClassName("active");
        optimizer.keepClassName("voted");
        optimizer.keepClassName("invalid");
        optimizer.keepClassName("glyphicon");
        optimizer.keepClassName("glyphicon-thumbs-down");
        optimizer.keepClassName("glyphicon-thumbs-up");
        optimizer.keepClassName("voteUp");
        optimizer.keepClassName("messages");
        optimizer.keepClassName("text-success");
        optimizer.keepClassName("hover");
        optimizer.keepTagName("hr");
        
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
