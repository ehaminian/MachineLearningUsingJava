package Utilities;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.MathExpression;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;


public class _Utilities {

    private static String filepathLogfile="D:\\NewFolder\\log.txt";
    private static String workspace="C:\\Users\\Ehsan\\Desktop\\KDD\\workspace\\";

    public static String getWorkspace()
    {
        return workspace;
    }
    public static String getFilepathLogfile()
    {
        return filepathLogfile;
    }

    public static void showStatistics(Instances dataset)
    {
        Instances orgdata=dataset;
        int N=dataset.numInstances();
        int M=dataset.numAttributes();
        weka.filters.unsupervised.attribute.MathExpression m=new MathExpression();
        try {
            m.setExpression("SD");
            m.setInputFormat(dataset);
            Instances ii=Filter.useFilter(dataset,m);
            System.out.println(ii.get(0).toString());
            m.setExpression("MIN");
            m.setInputFormat(dataset);
            ii=Filter.useFilter(dataset,m);
            System.out.println(ii.get(0).toString());
            m.setExpression("MAX");
            m.setInputFormat(dataset);
            ii=Filter.useFilter(dataset,m);
            System.out.println(ii.get(0).toString());
            m.setExpression("MEAN");
            m.setInputFormat(dataset);
            ii=Filter.useFilter(dataset,m);
            System.out.println(ii.get(0).toString());

        }
        catch (Exception e)
        {
            LogError(e);
        }

    }

    public static boolean SaveFile(String FileName, Instances data)
    {
        Instances dataSet = data;
        weka.core.converters.ArffSaver saver = new weka.core.converters.ArffSaver();
        saver.setInstances(dataSet);
        try {
            saver.setFile(new File(_Utilities.getWorkspace()+FileName));
            saver.writeBatch();
            return true;
        }
        catch (Exception e)
        {
            LogError(e);
            System.out.println(e.getMessage());
            return false;
        }


    }

    public static String[] getFeaturesnameindata(Instances inputdata)
    {
        int M=inputdata.numAttributes();
        String[] featuresname=new String[M-1];
        for(int i=0;i<=M-2;i++)
        {
            featuresname[i]=inputdata.attribute(i).name();
        }
        return featuresname;
    }

    public static String convertArrayofstring2string(String[] inputstring,char spiliter)
    {
        String s="";
        for(int i=0;i<inputstring.length;i++)
        {
            s+=spiliter;
            s+=inputstring[i];
        }
        return s;

    }

    public  static  Instances classassignment(Instances data)
    {
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);
        return data;
    }

    public  static  boolean write2file(String[] word,String filepathname,Boolean append)
    {
        try {
            FileWriter fileWriter = new FileWriter(filepathname,append);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (String s:word)
            {
                printWriter.println(s);
            }
            printWriter.close();
            return  true;
        }
        catch (Exception ee)
        {
            return  false;
        }
    }

    public  static  boolean write2file(String[] word,Boolean append)
    {
        try {
            FileWriter fileWriter = new FileWriter(filepathLogfile,append);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (String s:word)
            {
                printWriter.println(s);
            }
            printWriter.close();
            return  true;
        }
        catch (Exception ee)
        {
            return  false;
        }
    }

    public  static  boolean write2file(double var,String filepathname,Boolean append)
    {
        try {
            FileWriter fileWriter = new FileWriter(filepathname,append);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(Double.toString(var));
            printWriter.close();
            return  true;
        }
        catch (Exception ee)
        {
            System.out.println(ee.getMessage());
            return  false;
        }
    }

    public  static  boolean write2file(double[] array,String filepathname,Boolean append)
    {
        try {
            FileWriter fileWriter = new FileWriter(filepathname,append);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (double d:array)
            {
                printWriter.println(Double.toString(d));
            }
            printWriter.close();
            return  true;
        }
        catch (Exception ee)
        {
            return  false;
        }
    }

    public  static  boolean write2file(int[] array,Boolean append)
    {
        try {
            FileWriter fileWriter = new FileWriter(filepathLogfile,append);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (int d:array)
            {
                printWriter.println(Double.toString(d));
            }
            printWriter.close();
            return  true;
        }
        catch (Exception ee)
        {
            return  false;
        }
    }

    public static boolean write2file(String inputstring,String filename,Boolean append)
    {
        try {
            FileWriter fileWriter = new FileWriter(getWorkspace()+filename,append);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(inputstring);
            printWriter.close();
            return  true;
        }
        catch (Exception ee)
        {
            System.out.println(ee.getMessage());
            return  false;
        }

    }


    public static Instances readdata(String nameofFile)
    {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(_Utilities.getWorkspace() + nameofFile);
            return source.getDataSet();
        }catch (Exception e) {
            LogError(e);
            return  null;
        }

    }

    public  static  boolean createresult(int[] result,String filename)
    {
        try {
            FileWriter fileWriter = new FileWriter(getWorkspace()+filename);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (int d:result)
            {
                printWriter.println(d);
            }
            printWriter.close();
            return  true;
        }
        catch (Exception ee)
        {
            return  false;
        }
    }
    public static boolean LogError(Exception e)
    {
        try {
            FileWriter fileWriter = new FileWriter(filepathLogfile,true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(e.getMessage());
            printWriter.close();
            return  true;
        }
        catch (Exception ee)
        {
            return  false;
        }

    }

    public static boolean LogError(String inputstring)
    {
        try {
            FileWriter fileWriter = new FileWriter(filepathLogfile,true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(inputstring);
            printWriter.close();
            return  true;
        }
        catch (Exception ee)
        {
            return  false;
        }

    }
    public static void plot2D(int[] L,double[] f)
    {
        String applicationTitle="Grain vs Wheat";
        String plotTitle="Correctly Classified Instances for different number of features (Slected by ReliefF )";
        String xAxis="Number of Features";
        String yAxis="Correctly Classified Instances";
        LineChart_AWT chart = new LineChart_AWT(L,f,applicationTitle,plotTitle,xAxis,yAxis);
        chart.plot();
    }


}
