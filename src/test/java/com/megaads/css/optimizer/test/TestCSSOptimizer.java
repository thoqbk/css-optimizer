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

    private final String openBracket = "\\{";
    private final String closeBracket = "\\}";

    private final String notOpenAndCloseBracketPattern = "[^" + openBracket + closeBracket + "]";

    private final String singleRulePattern = notOpenAndCloseBracketPattern + "+" + openBracket + notOpenAndCloseBracketPattern + "*?" + closeBracket;

    private final String mediaRulePattern = "@media" + notOpenAndCloseBracketPattern + "+" + openBracket + "(" + singleRulePattern + ")*.*?" + closeBracket;
    //private final String mediaRulePattern = "@media" + notOpenAndCloseBracketPattern + "+" + openBracket + "(.*?)*" + closeBracket;

    private final Pattern rulePattern = Pattern.compile("(" + mediaRulePattern + "|" + singleRulePattern + ")", Pattern.DOTALL | Pattern.MULTILINE);

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

    //@Test
    public void test() throws IOException {
        String[] sampleSiteFileNames = new String[]{
            "about-us.htm",
            "airhead.com.htm",
            "amazon.com.htm",
            "apparel-amp-amp-accessories.htm",
            "blog.htm",
            "bodywear.htm",
            "categories.htm",
            "choosing-perfect-top-for-date-night.htm",
            "home.htm",
            "stores.htm",
            "bootstrap-theme.min.css",
            "main.css"
        };

        String[] analyzedFiles = new String[]{
            "bootstrap.css"
        };

        Set<String> classes = new TreeSet<>();
        for (String sampleSiteFileName : sampleSiteFileNames) {
            classes.addAll(extractClasses(readFileContent(sampleSiteFileName)));
        }
        //Print out all classes
        for (String clazz : classes) {
            logger.info(clazz);
        }
    }

    public void testParseCssFiles() {

    }

    //@Test
    public void testSplitCssRules() throws IOException {
        String html = readFileContent("test.css");
        //List<String> rules = extractCssRule(html);
        Matcher matcher = rulePattern.matcher(html);
        //Matcher matcher = Pattern.compile(mediaRulePattern, Pattern.DOTALL | Pattern.MULTILINE).matcher(html);
        while (matcher.find()) {
            logger.debug("Found rule: " + matcher.group());
        }

    }

    //--------------------------------------------------------------------------
    //  Implement N Override
    //--------------------------------------------------------------------------
    //  Utils
    private Set<String> extractClasses(String htmlContent) {
        Set<String> retVal = new HashSet<>();
        Elements elements = Jsoup.parse(htmlContent)
                .body()
                .getAllElements();
        for (Element element : elements) {
            for (String className : element.classNames()) {
                retVal.add(className);
            }
        }
        //return
        return retVal;
    }

    private String readFileContent(String fileName) throws IOException {
        StringBuilder retVal = new StringBuilder();
        String path = "/com/megaads/coupon/site/support/resource/c4s/" + fileName;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(TestCSSOptimizer.class
                .getResourceAsStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                retVal.append(line)
                        .append("\n");
            }
        }
        //return
        return retVal.toString();
    }

    private final Pattern simpleCssRulePattern = Pattern.compile("[\\s^$]*[^\\{\\}]+\\{.*?\\}[\\s^$]*", Pattern.DOTALL | Pattern.MULTILINE);

    private final Pattern commentPattern = Pattern.compile("\\/\\*.*?\\*\\/", Pattern.DOTALL | Pattern.MULTILINE);

    private final Pattern selectorPattern = Pattern.compile("(\\.[^\\{\\}\\s]+)", Pattern.DOTALL | Pattern.MULTILINE);

    private List<String> extractSimpleCssRules(String html) {
        String stdContent = removeContentByPattern(html, commentPattern);
        //String stdContentWoMediaRules = removeContentByPattern(stdContent, mediaRulePattern);
        //return extractCssRulesByPattern(stdContentWoMediaRules, simpleCssRulePattern);
        return null;
    }

    private String removeContentByPattern(String content, Pattern pattern) {
        String retVal = content;
        boolean found = true;
        while (found) {
            Matcher matcher = pattern.matcher(retVal);
            if (matcher.find()) {
                StringBuilder retValBuilder = new StringBuilder();
                int start = matcher.start();
                int end = matcher.end();
                if (start > 0) {
                    retValBuilder.append(retVal.substring(0, start));
                }
                retValBuilder.append(retVal.substring(end + 1));
                retVal = retValBuilder.toString();
            } else {
                found = false;
            }
        }
        return retVal;
    }

    private List<String> extractCssRulesByPattern(String stdContent, Pattern pattern) {
        List<String> retVal = new ArrayList<>();
        //logger.info("Remove all comments: " + stdContent);
        //Matcher matcher = cssRulePattern.matcher(stdContent);
        Matcher matcher = pattern.matcher(stdContent);
        while (matcher.find()) {
            String rule = stdContent.substring(matcher.start(), matcher.end());
            retVal.add(rule);
        }
        //return
        return retVal;
    }
    //--------------------------------------------------------------------------
    //  Inner class
}
