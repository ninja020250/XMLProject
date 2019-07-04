/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cuonghn.wellformer.SyntaxChecker;

/**
 *
 * @author nhatc
 */
public class TextUtilities {

    public static final String EXPRESSION_CONTAINS_NUMBER = ".*\\d.*";

    public static String getOnlyNumber(String src) {
        src = src.replaceAll("\\D+", "");
        return src;
    }

       public static String removeWhiteSpace(String src) {
        src = src.replace(" ", "");
        return src;
    }
    public static String removeSymbols(String src) {
        src = src.replace("•", "");
        src = src.replace("~", "");
        src = src.replace(",", ".");
        return src;
    }

    public static String refineHtml(String src) {
        src = getBody(src);
        src = removeMiscellaneousTags(src);

        SyntaxChecker xmlSyntaxChecker = new SyntaxChecker();
        src = xmlSyntaxChecker.check(src);

        //crop one more time
        src = getBody(src);
        return src;
    }

    public static String getByExpression(String src, String expression) {
        String result = "";
        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(src);

        if (matcher.find()) {
            result = matcher.group();
        }

        return result;
    }

    private static String getBody(String src) {
        String result = src;
        String expression = "<body.*?</body>";
        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(result);

        if (matcher.find()) {
            result = matcher.group(0);
        }

        return result;
    }

    public static String removeMiscellaneousTags(String src) {
        String result = src;

        //Remove all <script> tags
        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");

        expression = "<noscript.*?</noscript>";
        result = result.replaceAll(expression, "");
//        
//        // remove alt>
//        expression = "alt=\".*?\"";
//        result = result.replaceAll(expression, "");

        //Remove all comments
        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");

        //Remove all <script> tags
        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");

        expression = "&amp;?";
        result = result.replaceAll(expression, "");

        return result;
    }
}
