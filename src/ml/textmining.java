package ml;
import java.io.*;
import Utilities._Utilities;
import Utilities._TEXTUtils;
import Utilities.*;
import Utilities._TEXTUtils.*;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.supervised.instance.StratifiedRemoveFolds;
import weka.filters.unsupervised.attribute.StringToWordVector;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import weka.classifiers.Evaluation;

import static Utilities.Classifiers.makeJ48;
import static Utilities.Classifiers.makeMLP;
import static Utilities.Classifiers.makeNB;
import static Utilities.Classifiers.makeKNN;
import static Utilities.Classifiers.makeSVM;
import static Utilities.Classifiers.makeSMO;


public class textmining {
    public static void main(String[] args) throws Exception
    {
        File sourcefolder = new File(_Utilities.getWorkspace()+"\\textmining");
        TextDirectoryLoader td=new TextDirectoryLoader();
        td.setDirectory(sourcefolder );
        td.getDirectory();
        Instances data= td.getDataSet();
        data=_TEXTUtils.StringToWordVectorfilter(data);
        _Utilities.SaveFile("ALLDATA.arff",data);
        StratifiedRemoveFolds filter = new StratifiedRemoveFolds();
        String[] options = new String[6];
        options[0] = "-N";
        options[1] = Integer.toString(5);
        options[2] = "-F";
        options[3] = Integer.toString(1);
        options[4] = "-S";
        options[5] = Integer.toString(1);
        filter.setOptions(options);
        filter.setInputFormat(data);
        filter.setInvertSelection(false);
        Instances testData = Filter.useFilter(data, filter);
        testData.setClassIndex(0);
        filter.setInvertSelection(true);
        Instances trainData = Filter.useFilter(data, filter);
        testData.setClassIndex(0);
        System.out.println("Number of classes in data is: "+trainData.numClasses());
        System.out.println("Number of Features in data is: "+trainData.numAttributes());
        _Utilities.SaveFile("trainData.arff",trainData);
        _Utilities.SaveFile("testData.arff",testData);

        //trainData=neededFilters.RelifeF(trainData,0);
        trainData=neededFilters.InfoGain(trainData,0);
        trainData=_Utilities.classassignment(trainData);
        _Utilities.SaveFile("trainDataFiltered.arff",trainData);
        System.out.println("Number of Features in after feature selection is: "+trainData.numAttributes());
        int NumberOfSelectedfeatures=trainData.numAttributes();


        //testData=neededFilters.RelifeF(testData,-1.7976931348623157E308);
        trainData=neededFilters.InfoGain(trainData,-1.7976931348623157E308);
        testData=_Utilities.classassignment(testData);
        String range="1-"+(trainData.numAttributes()-1)+","+testData.numAttributes();
        System.out.println(range);
        testData = neededFilters.Selectattribute(testData,range);
        testData=_Utilities.classassignment(testData);

        SMO sm=makeSMO(trainData);
        Evaluation eval = new Evaluation(trainData);
        eval.crossValidateModel(sm, trainData, 5, new Random(1));
        System.out.println(eval.toSummaryString());
        eval.evaluateModel(sm, testData);
        _TEXTUtils.writeresult( eval,"SMO");

        J48 tree=makeJ48(trainData);
        eval = new Evaluation(trainData);
        eval.crossValidateModel(tree, trainData, 5, new Random(1));
        System.out.println(eval.toSummaryString());
        eval.evaluateModel(tree, testData);
        _TEXTUtils.writeresult( eval,"Tree");

        NaiveBayes NB = makeNB(trainData);
        eval = new Evaluation(trainData);
        eval.crossValidateModel(NB, trainData, 5, new Random(1));
        System.out.println(eval.toSummaryString());
        eval.evaluateModel(NB, testData);
        _TEXTUtils.writeresult( eval,"NB");

        IBk knn=makeKNN(trainData);
        eval = new Evaluation(trainData);
        eval.crossValidateModel(knn, trainData, 5, new Random(1));
        System.out.println(eval.toSummaryString());
        eval.evaluateModel(knn, testData);
        _TEXTUtils.writeresult( eval,"knn");

        LibSVM linearSVM=makeSVM(trainData,LibSVM.KERNELTYPE_LINEAR);
        eval = new Evaluation(trainData);
        eval.crossValidateModel(linearSVM, trainData, 5, new Random(1));
        System.out.println(eval.toSummaryString());
        eval.evaluateModel(linearSVM, testData);
        _TEXTUtils.writeresult( eval,"linearSVM");

        LibSVM radialSVM=makeSVM(trainData,LibSVM.KERNELTYPE_RBF);
        eval = new Evaluation(trainData);
        eval.crossValidateModel(radialSVM, trainData, 5, new Random(1));
        System.out.println(eval.toSummaryString());
        eval.evaluateModel(radialSVM, testData);
        _TEXTUtils.writeresult( eval,"radialSVM");

    }
}
