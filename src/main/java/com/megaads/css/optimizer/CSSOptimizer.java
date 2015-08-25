/**
 * Copyright (C) 2015, MEGAADS
 * 
* Released under the MIT license
 */
package com.megaads.css.optimizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author THOQ LUONG Mar 9, 2015 11:21:26 PM
 */
public class CSSOptimizer {

    //--------------------------------------------------------------------------
    //  Members
    private final Logger logger = LoggerFactory.getLogger(CSSOptimizer.class);

    private final String openBracket = "\\{";
    private final String closeBracket = "\\}";

    private final String notOpenAndCloseBracketPattern = "[^" + openBracket + closeBracket + "]";

    private final String singleRulePattern = notOpenAndCloseBracketPattern + "+" + openBracket + notOpenAndCloseBracketPattern + "*?" + closeBracket;

    private final String mediaRulePattern = "@media" + notOpenAndCloseBracketPattern + "+" + openBracket + "(" + singleRulePattern + ")*.*?" + closeBracket;
    //private final String mediaRulePattern = "@media" + notOpenAndCloseBracketPattern + "+" + openBracket + "(.*?)*" + closeBracket;

    private final Pattern rulePattern = Pattern.compile("(" + mediaRulePattern + "|" + singleRulePattern + ")", Pattern.DOTALL | Pattern.MULTILINE);

    private final Pattern commentPattern = Pattern.compile("\\/\\*.*?\\*\\/", Pattern.DOTALL | Pattern.MULTILINE);

    private final Pattern mediaRuleBodyPattern = Pattern.compile(openBracket + "((?:" + singleRulePattern + ")*.*?)" + closeBracket, Pattern.DOTALL | Pattern.MULTILINE);

    private final Pattern classNamePattern = Pattern.compile("\\.([_a-zA-Z]+[_a-zA-Z0-9-]*)");
    private final Pattern tagNamePattern = Pattern.compile("(?:^|\\s)([a-zA-Z][a-zA-Z0-9]*)");

    private final Pattern selectorPattern = Pattern.compile("(" + notOpenAndCloseBracketPattern + "+)" + openBracket, Pattern.DOTALL | Pattern.MULTILINE);

    private final Pattern isCSSMediaRulePattern = Pattern.compile("\\s*@media");
    private final Pattern isNonCSSStyleRulePattern = Pattern.compile("\\s*@");

    private final Set<String> htmlFiles = new HashSet<>();
    private final String targetFilePath;
    private final String resultFilePath;

    private final Set<String> usedClassNames = new HashSet<>();
    private final Set<String> usedTagNames = new HashSet<>();

    private int cssStyleRulesCount = 0;
    private int removedCssStyleRulesCount = 0;

    //--------------------------------------------------------------------------
    //  Initialization and releasation
    /**
     *
     * @param targetFilePath file will be optimized
     * @param resultFilePath output file after optimizing target file
     */
    public CSSOptimizer(String targetFilePath, String resultFilePath) {
        this.targetFilePath = targetFilePath;
        this.resultFilePath = resultFilePath;
    }
    //--------------------------------------------------------------------------
    //  Getter N Setter

    //--------------------------------------------------------------------------
    //  Method binding
    /**
     * Add html file content to extract used css rule
     *
     * @param path must be a http(s) url or a local file path
     */
    public void addHtmlFile(String path) {
        this.htmlFiles.add(path);
    }

    public void keepTagName(String tagName) {
        usedTagNames.add(tagName.toLowerCase());
    }

    public void keepClassName(String className) {
        usedClassNames.add(className);
    }

    public void optimize() throws IOException {
        //Parse input html files and extract used class and tag
        extractUsedClassNamesNTagNames();
        String targetFileContent = readFileContent(targetFilePath);
        List<CSSRule> cssRules = extractCSSRules(targetFileContent);
        //debug
        logger.debug("Found " + cssStyleRulesCount + " style rule(s); begin optimizing ...");
        String result = buildResult(cssRules);
        try (OutputStream fileOutputStream = new FileOutputStream(resultFilePath)) {//write down optimized result
            fileOutputStream.write(result.getBytes("UTF-8"));
        }
        logger.debug("Finish optmizing css file, total style rule: " + cssStyleRulesCount
                + "; total removed style rule: " + removedCssStyleRulesCount + "(" + (removedCssStyleRulesCount * 100 / cssStyleRulesCount) + "%)");
    }

    //--------------------------------------------------------------------------
    //  Implement N Override
    //--------------------------------------------------------------------------
    //  Utils
    private void extractUsedClassNamesNTagNames() throws IOException {
        logger.debug("Begin parsing input files");
        for (String inputFilePath : htmlFiles) {
            logger.debug("Parsing file: " + inputFilePath);
            extractUsedClassNamesNTagNames(readFileContent(inputFilePath));
        }
    }

    private void extractUsedClassNamesNTagNames(String htmlContent) {
        Elements elements = Jsoup.parse(htmlContent)
                .body()
                .getAllElements();
        for (Element element : elements) {
            for (String className : element.classNames()) {
                if (className != null && className.trim().length() > 0) {
                    usedClassNames.add(className);
                    logger.debug("Found class: " + className);
                }
            }
            usedTagNames.add(element.tagName().toLowerCase());
            logger.debug("Found tag: " + element.tagName());
        }
    }

    private String readFileContent(String filePath) throws IOException {
        StringBuilder retVal = new StringBuilder();
        boolean b = filePath.toLowerCase().startsWith("http") || filePath.toLowerCase().startsWith("https");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(b ? new URL(filePath).openStream() : new FileInputStream(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                retVal.append(line)
                        .append("\n");
            }
        }
        //return
        return retVal.toString();
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

    protected List<String> matchAll(String inputString, Pattern pattern, int group) {
        List<String> retVal = new ArrayList<>();
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            retVal.add(matcher.group(group));
        }
        //return
        return retVal;
    }

    private String extractMediaRuleBody(String ruleContent) {
        List<String> matchedStrings = matchAll(ruleContent, mediaRuleBodyPattern, 1);
        return matchedStrings.get(0);
    }

    /**
     * Extract CSS rules from a string containing multiple rules
     *
     * @param content
     * @return
     */
    private List<CSSRule> extractCSSRules(String rulesInString) {
        //debug
        logger.debug("Begin parsing: " + rulesInString);
        List<CSSRule> retVal = new ArrayList<>();
        String stdContent = removeContentByPattern(rulesInString, commentPattern);
        List<String> cssRuleStrings = matchAll(stdContent, rulePattern, 0);
        for (String cssRuleString : cssRuleStrings) {
            List<String> matchedStrings = matchAll(cssRuleString, selectorPattern, 1);
            if (matchedStrings.isEmpty()) {
                throw new RuntimeException("Invalid css rule: " + stdContent);
            }
            String selector = matchedStrings.get(0);
            if (isCSSMediaRulePattern.matcher(cssRuleString).find()) {
                String ruleBody = extractMediaRuleBody(cssRuleString);
                List<CSSRule> children = extractCSSRules(ruleBody);
                CSSMediaRule mediaRule = new CSSMediaRule(cssRuleString);
                mediaRule.getChildren().addAll(children);
                mediaRule.setSelector(selector);
                //save
                retVal.add(mediaRule);
            } else if (isNonCSSStyleRulePattern.matcher(cssRuleString).find()) {
                NonCSSStyleRule nonCSSStyleRule = new NonCSSStyleRule(cssRuleString);
                nonCSSStyleRule.setSelector(selector);
                retVal.add(nonCSSStyleRule);
            } else {//style rule
                CSSStyleRule cssStyleRule = new CSSStyleRule(cssRuleString);
                //selector
                cssStyleRule.setSelector(selector);
                //get class names
                List<String> classNames = matchAll(selector, classNamePattern, 1);
                cssStyleRule.getClassNames().addAll(classNames);
                //get tag names
                List<String> tagNames = matchAll(selector, tagNamePattern, 1);
                tagNames.stream().map(tagName -> tagName.toLowerCase())
                        .forEach(lowerCaseTagName -> cssStyleRule.getTagNames().add(lowerCaseTagName));
                //save
                retVal.add(cssStyleRule);
                cssStyleRulesCount++;
            }
        }
        //return
        return retVal;
    }

    private String buildResult(List<CSSRule> cssRules) {
        StringBuilder retVal = new StringBuilder();
        for (CSSRule cssRule : cssRules) {
            if (cssRule instanceof CSSMediaRule) {
                String mediaRuleResult = buildResult(((CSSMediaRule) cssRule).getChildren());
                if (!mediaRuleResult.isEmpty()) {
                    retVal.append("\n")
                            .append(cssRule.getSelector())
                            .append("{\n")
                            .append(mediaRuleResult)
                            .append("\n}");
                }
            } else if (cssRule instanceof NonCSSStyleRule) {//if rule is grouping rule but is not a media rule
                retVal.append("\n")
                        .append(cssRule.getContent())
                        .append("\n");
            } else {//style rule
                CSSStyleRule styleRule = (CSSStyleRule) cssRule;
                if (styleRule.getClassNames().isEmpty() && styleRule.getTagNames().isEmpty()) {// example rule: * {}
                    retVal.append("\n")
                            .append(styleRule.getContent())
                            .append("\n");
                } else {
                    boolean isUsedRule = false;
                    logger.debug("Begin checking rule: " + styleRule.getSelector());
                    for (String className : styleRule.getClassNames()) {
                        if (usedClassNames.contains(className)) {
                            isUsedRule = true;
                            logger.info("Found an used class name: " + className);
                            break;
                        }
                    }
                    if (!isUsedRule) {
                        for (String tagName : styleRule.getTagNames()) {
                            if (usedTagNames.contains(tagName)) {
                                isUsedRule = true;
                                logger.info("Found an used tag name: " + tagName);
                                break;
                            }
                        }
                    }
                    if (isUsedRule) {
                        retVal.append("\n")
                                .append(styleRule.getContent())
                                .append("\n");
                        logger.debug("Found an used rule: " + styleRule.getSelector());
                    } else {
                        logger.debug("Remove unsed rule: " + styleRule.getSelector());
                        removedCssStyleRulesCount++;
                    }
                }
            }
        }
        //return
        return retVal.toString();
    }
    //--------------------------------------------------------------------------
    //  Inner class
}
