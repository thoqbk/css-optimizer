# css-optimizer
css-optimizer is a tool that helps you to remove un-used css rules basing sample html file of your website

## Usage

```java
//Step 01.
//Initialize CSSOptimizer with target css file(file will be optimized) 
//and output file (result file after optimizing target cssfile)
CSSOptimizer optimizer = new CSSOptimizer("bootstrap.css","bootstrap.min.css");

//Step 02. Download sample files from your website and add it to optimizer or use an url
optimizer.addHtmlFile("about-us.htm");
optimizer.addHtmlFile(resourceDirectory + "\\amazon.com.htm");
optimizer.addHtmlFile(resourceDirectory + "\\blog.htm");
optimizer.addHtmlFile("https://github.com/thoqbk");

//Step 03. Some classes or tags are added using Javascript code, declare them here:
optimizer.keepClassName("img-thumbnail");
optimizer.keepClassName("pagination");
optimizer.keepTagName("hr");
        
//Step 04. Execute optimizing, result file will be stored in path that is declared in Step 01.
optimizer.optimize();
```

## Example
Checkout and run test file [TestCSSOptimizer.java](https://github.com/thoqbk/css-optimizer/blob/master/src/test/java/com/megaads/css/optimizer/test/TestCSSOptimizer.java) to see example result