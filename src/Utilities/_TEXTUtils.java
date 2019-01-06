package Utilities;

import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.tokenizers.NGramTokenizer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.core.stemmers.LovinsStemmer;
import weka.classifiers.Evaluation;


public class _TEXTUtils {
    public static Instances StringToWordVectorfilter(Instances data) throws Exception
    {
        StringToWordVector swv = new StringToWordVector();
        swv.setIDFTransform(true);
        swv.setTFTransform(true);
        swv.setOutputWordCounts(true);
        swv.setAttributeIndices("first-last");
        swv.setLowerCaseTokens(true);
        swv.setMinTermFreq(1);
        swv.setNormalizeDocLength(new SelectedTag(StringToWordVector.FILTER_NORMALIZE_ALL, StringToWordVector.TAGS_FILTER));
        //swv.setStemmer(new LovinsStemmer());
        swv.setStopwordsHandler(new RegExStopwords("([0-9]|\\*|-|@|n\\/a|[\\%\\€\\$\\£])"));
        /*NGramTokenizer tokenizer = new NGramTokenizer();
        tokenizer.setNGramMaxSize(3);
        tokenizer.setNGramMinSize(1);*/

        WordTokenizer tokenizer =new WordTokenizer();

        swv.setTokenizer(tokenizer);
        swv.setWordsToKeep(1000);
        swv.setDoNotOperateOnPerClassBasis(false);
        swv.setInputFormat(data);
        Instances newData=Filter.useFilter(data, swv);
        newData.setClassIndex(0);
        return newData;
    }

    public static void writeresult(Evaluation eval,String tag)
    {
        _Utilities.write2file("***** "+tag+" *******","Measures.txt",true);
        //_Utilities.write2file(eval.toSummaryString(),"Measures.txt",true);
        //_Utilities.write2file(Double.toString(1-eval.errorRate()),"Measures.txt",true);
        _Utilities.write2file(Double.toString(eval.errorRate()),"Measures.txt",true);
        _Utilities.write2file(Double.toString(eval.unweightedMacroFmeasure()),"Measures.txt",true);
        _Utilities.write2file(Double.toString(eval.precision(0)),"Measures.txt",true);
        _Utilities.write2file(Double.toString(eval.recall(0)),"Measures.txt",true);
        _Utilities.write2file(Double.toString(eval.fMeasure(0)),"Measures.txt",true);
        _Utilities.write2file(Double.toString(eval.precision(1)),"Measures.txt",true);
        _Utilities.write2file(Double.toString(eval.recall(1)),"Measures.txt",true);
        _Utilities.write2file(Double.toString(eval.fMeasure(1)),"Measures.txt",true);
        _Utilities.write2file("*******************","Measures.txt",true);
    }

}
