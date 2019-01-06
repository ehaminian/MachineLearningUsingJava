package Utilities;
import weka.core.stopwords.StopwordsHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExStopwords implements StopwordsHandler{
    private final Pattern pattern;
    public RegExStopwords(String regexString) {
        pattern = Pattern.compile(regexString);
    }
    @Override
    public boolean isStopword(String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
