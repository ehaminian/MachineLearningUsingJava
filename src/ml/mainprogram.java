package ml;

import weka.classifiers.trees.RandomForest;
import java.io.File;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import Utilities.*;
import weka.experiment.Experiment;


public class mainprogram {

    private static String traindatafilename="kaggleHW1.arff";
    private static String testdatafilename="testforclassification.arff";
    private static int resamplingPercentage=20;
    private static int minPts = 3;
    private static double eps = 0.4;//eps=.94;
    private static File f;

    static {
        f = new File("input");
    }

    public static void main(String[] args)
    {
        try {
            RandomForest randomforest = new RandomForest();
            randomforest.setOptions(weka.core.Utils.splitOptions("-P 100 -I 100 -num-slots 1 -K 0 -M 1 -V 0.001 -S 1"));

            Instances traindata=_Utilities.readdata(traindatafilename);
            traindata = _Utilities.classassignment(traindata);

            Instances testdata=_Utilities.readdata(testdatafilename);
            testdata=_Utilities.classassignment(testdata);
            Instances testdataforIDgetting=_Utilities.readdata("testorg.arff");

            //_Utilities.showStatistics(traindata);


            traindata = neededFilters.Treatmissingvalue(traindata);
            traindata=neededFilters.Subsample(traindata,resamplingPercentage);
            traindata=OutlierRemover.OutlierRemoverusingOPTICS(traindata,minPts,eps,true);
/*
            _Utilities.SaveFile("Resampled.arff",traindata);

            Evaluation evaluation = new Evaluation(traindata);
            evaluation.crossValidateModel(randomforest, traindata, 10, new Random(1));
            double Fmeasure=evaluation.weightedFMeasure();
            System.out.println(Fmeasure);
            randomforest.buildClassifier(traindata);


            for(int i=0;i<=testdata.numInstances()-1;i++)
            {
                Instance test=testdata.get(i);
                int y=(int)randomforest.classifyInstance(test);
                String predictedClassLabel =test.classAttribute().value(y);
                String item=((int)(testdataforIDgetting.instance(i).value(0)))+","+predictedClassLabel;
                System.out.println(item);
                _Utilities.write2file(item,"RESULT.TXT",true);
                //System.out.println(predictedClassLabel);
            }


            _Utilities.SaveFile("resampledMINI.arff",data1resampled);
                Instances data11=neededFilters.RelifeF(data1resampled);
                _Utilities.write2file(_Utilities.convertArrayofstring2string(_Utilities.getFeaturesnameindata(data11),' '),_Utilities.getWorkspace()+"Attributesorder.txt",false);
                double[] f=new double[data11.numAttributes()-1];
                int[] L=new int[data11.numAttributes()-1];
                int M=data11.numAttributes()-1;
                for(int i=0;i<M;i++)
                {
                    String range="1-"+Integer.toString(i+1)+","+data11.numAttributes();
                    Instances newData = neededFilters.Selectattribute(data11,range);
                    System.out.println(Integer.toString(i+1));
                    _Utilities.write2file(_Utilities.convertArrayofstring2string(_Utilities.getFeaturesnameindata(newData),' '),_Utilities.getWorkspace()+"Attributesorder.txt",true);
                    Evaluation evaluation = new Evaluation(newData);
                    evaluation.crossValidateModel(randomforest, newData, 10, new Random(1));
                    f[i]=evaluation.weightedFMeasure();
                    L[i]=i+1;
               }
                double max = Arrays.stream(f).max().getAsDouble();
                OptionalDouble maximum = DoubleStream.of(f).max();
                int t= DoubleStream.of(f).boxed().collect(Collectors.toList()).indexOf(maximum.getAsDouble());
                System.out.println("Maximum is : "+Double.toString(max) +"\n At the position of: "+Integer.toString(t));
                _Utilities.plot2D(L,f);
                _Utilities.write2file(f,_Utilities.getWorkspace()+"Fmeasures.txt",false);*/



        }
        catch (Exception ee)
        {
            System.out.println(ee.toString());
        }
    }


}
