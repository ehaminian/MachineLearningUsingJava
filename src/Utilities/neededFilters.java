package Utilities;

import weka.attributeSelection.Ranker;
import weka.attributeSelection.ReliefFAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.supervised.attribute.*;

import java.util.Random;

public  class   neededFilters  {

    public static Instances Treatmissingvalue(Instances inputdata)
    {
        try {
            ReplaceMissingValues filter = new ReplaceMissingValues();
            filter.setInputFormat(inputdata);
            return Filter.useFilter(inputdata,filter);
        }
        catch (Exception e)
        {
            return null;
        }

    }

    public static Instances Selectattribute(Instances inputdata,String range)
    {
        try {
            Remove r = new Remove();
            r.setAttributeIndices(range);
            r.setInvertSelection(true);
            r.setInputFormat(inputdata);
            return Filter.useFilter(inputdata,r);
        }catch (Exception e)
        {
            return null;
        }
    }

    public static Instances Subsample(Instances inputdata, double percentage) {
        Resample rs = new Resample();
        rs.setSampleSizePercent(percentage);
        rs.setNoReplacement(true);
        Random ran=new Random();
        rs.setRandomSeed(ran.nextInt(1000));
        Instances newData=null;

        try {
            rs.setInputFormat(inputdata);
            newData = Filter.useFilter(inputdata, rs);
        } catch (Exception ee)
        {
            System.out.println(ee.getMessage());
        }

        return newData;
    }

    public static Instances RelifeF(Instances data) throws Exception {
        AttributeSelection filter = new AttributeSelection();
        ReliefFAttributeEval evarf=new ReliefFAttributeEval();
        Ranker serachr=new Ranker();
        filter.setEvaluator(evarf);
        filter.setSearch(serachr);
        filter.setInputFormat(data);
        Instances newData = Filter.useFilter(data, filter);
        return newData;
    }

    public static Instances RelifeF(Instances data,double threshold) throws Exception {
        AttributeSelection filter = new AttributeSelection();
        ReliefFAttributeEval evarf=new ReliefFAttributeEval();
        Ranker serachr=new Ranker();
        serachr.setThreshold(threshold);
        filter.setEvaluator(evarf);
        filter.setSearch(serachr);
        filter.setInputFormat(data);
        Instances newData = Filter.useFilter(data, filter);
        return newData;
    }


    //CfsSubsetEval eval = new CfsSubsetEval();
    //GreedyStepwise search = new GreedyStepwise();

        /*filter.setEvaluator(eval);
        filter.setSearch(search);
        filter.setInputFormat(data);
        Instances newData = Filter.useFilter(data, filter);*/

    public static Instances InfoGain(Instances data,double threshold) throws Exception {
        AttributeSelection filter = new AttributeSelection();
        InfoGainAttributeEval evaig=new InfoGainAttributeEval();
        Ranker serachr=new Ranker();
        serachr.setThreshold(threshold);
        filter.setEvaluator(evaig);
        filter.setSearch(serachr);
        filter.setInputFormat(data);
        Instances newData = Filter.useFilter(data, filter);
        return newData;
    }

}
