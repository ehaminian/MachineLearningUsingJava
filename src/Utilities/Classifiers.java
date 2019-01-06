package Utilities;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.SMO;
import java.io.File;
import weka.core.SelectedTag;
import weka.core.neighboursearch.LinearNNSearch;

import static weka.classifiers.lazy.IBk.TAGS_WEIGHTING;
import static weka.classifiers.lazy.IBk.WEIGHT_INVERSE;


public class Classifiers {
    public static SMO makeSMO(Instances data) throws Exception
    {
        String[] options = new String[1];
        options[0] = "-U";
        SMO sm = new SMO();
        sm.buildClassifier(data);
        return sm;
    }
    public static J48 makeJ48(Instances data) throws Exception
    {
        String[] options = new String[1];
        options[0] = "-U";
        J48 tree = new J48();
        tree.setOptions(options);
        tree.buildClassifier(data);
        return tree;
    }
    public static NaiveBayes makeNB(Instances data) throws Exception
    {
        NaiveBayes NB = new NaiveBayes();
        NB.setUseKernelEstimator(true);
        NB.setUseSupervisedDiscretization(true);
        NB.buildClassifier(data);
        return NB;
    }
    public static MultilayerPerceptron makeMLP(Instances data) throws Exception
    {
        MultilayerPerceptron MLP = new MultilayerPerceptron();
        MLP.setGUI(true);
        MLP.setOptions(" -L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H a".split(" "));
        MLP.buildClassifier(data);
        return MLP;
    }
    public static IBk makeKNN(Instances data) throws Exception
    {
        IBk knn=new IBk();
        knn.setKNN(3);
        knn.setCrossValidate(false);
        knn.setNearestNeighbourSearchAlgorithm(new LinearNNSearch());
        knn.setDistanceWeighting(new SelectedTag(WEIGHT_INVERSE, TAGS_WEIGHTING));
        knn.buildClassifier(data);
        return knn;
    }

    public static LibSVM makeSVM(Instances data,int tag) throws Exception
    {
        LibSVM svm=new LibSVM();
        svm.setCacheSize(40);
        svm.setCoef0(0.0);
        svm.setCost(1);
        svm.setDegree(3);
        svm.setDoNotReplaceMissingValues(false);
        svm.setEps(0.001);
        svm.setLoss(0.1);
        File svmmodel=new File(_Utilities.getWorkspace()+"svmmodel.model");
        svm.setModelFile(svmmodel);
        svm.setNormalize(true);
        svm.setNu(0.5);
        svm.setProbabilityEstimates(false);
        svm.setShrinking(true);
        svm.setKernelType(new SelectedTag(tag, LibSVM.TAGS_KERNELTYPE));
        svm.buildClassifier(data);
        return  svm;
    }



}
