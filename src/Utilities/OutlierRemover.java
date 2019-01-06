package Utilities;

import weka.clusterers.OPTICS;
import weka.clusterers.forOPTICSAndDBScan.DataObjects.DataObject;
import weka.clusterers.forOPTICSAndDBScan.Databases.Database;
import weka.core.EuclideanDistance;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveRange;


import java.io.File;

public class OutlierRemover {

    public static Instances OutlierRemoverusingOPTICS(Instances data,int minPts,double eps,boolean showGUI)
    {
        Instances orgdata=data;
        System.out.println("The number of original Instances is : "+data.size());
        Database db=new Database(new EuclideanDistance() , data );
        OPTICS otcs = new OPTICS();
        data = neededFilters.Selectattribute(data, "1-" + Integer.toString(data.numAttributes() - 1));
        otcs.setEpsilon(eps);
        otcs.setMinPoints(minPts);
        //if you want to write the result in a file
        //otcs.setDatabaseOutput(new File(_Utilities.getWorkspace()+"output"));
        //otcs.setWriteOPTICSresults(true);
        try {
            otcs.buildClusterer(data);
            System.out.println();
        }
        catch (Exception e)
        {
            _Utilities.LogError(e);
            return null;
        }
        otcs.setShowGUI(showGUI);

        double rd=0;
        double cd=0;
        String range="";
        int num=0;
        for(int i=1;i<=data.numInstances();i++) {
            DataObject dataObject = (DataObject) otcs.getResultVector().get(i-1);
            cd=dataObject.getCoreDistance() == 2.147483647E9D ? 2.147483647E9D : Double.parseDouble(Utils.doubleToString(dataObject.getCoreDistance(), 3, 5));
            rd=dataObject.getReachabilityDistance() == 2.147483647E9D ? 2.147483647E9D : Double.parseDouble(Utils.doubleToString(dataObject.getCoreDistance(), 3, 5));
            if(cd==2.147483647E9D && rd==2.147483647E9D)
            {
                range+= dataObject.getKey()+",";
                num++;
            }
        }
        System.out.println("OPTICS has been done. Lets remove the detected outliers...");
        System.out.println("These samples has been detected as outliers : "+range);
        data=orgdata;
        if(num!=0) {
            range = range.substring(0, range.length() - 1);
            RemoveRange filter=new RemoveRange();
            String[] Option=new String[2];
            Option[0]="-R";
            Option[1]=range;
            try {
                filter.setOptions(Option);
                filter.setInputFormat(orgdata);
                data=Filter.useFilter(orgdata,filter);
            }catch (Exception e) {
                _Utilities.LogError(e);
                return null;
            }

        }

        System.out.println(num+" samples were removed by Optics outlier remover.");
        return data;

                    /*
            Database db=new Database(new EuclideanDistance() , data );
            int mpts = 3;
            //double eps=.94;
            double eps = .1;
            int r = data.numAttributes() - 1;
            data = neededFilters.Selectattribute(data, "1-" + Integer.toString(r));
            OPTICS otcs = new OPTICS();
            otcs.setEpsilon(eps);
            otcs.setMinPoints(mpts);
            otcs.setDatabaseOutput(new File(workspace+"output"));
            otcs.setWriteOPTICSresults(true);
            otcs.buildClusterer(data);
            otcs.setShowGUI(true);
            double[] rd=new double[data.numInstances()];
            double[] cd=new double[data.numInstances()];
            for(int i=1;i<=data.numInstances();i++) {
                DataObject dataObject = (DataObject) otcs.getResultVector().get(i-1);
                cd[i-1]=dataObject.getCoreDistance() == 2.147483647E9D ? 2.147483647E9D : Double.parseDouble(Utils.doubleToString(dataObject.getCoreDistance(), 3, 5));
                rd[i-1]=dataObject.getReachabilityDistance() == 2.147483647E9D ? 2.147483647E9D : Double.parseDouble(Utils.doubleToString(dataObject.getCoreDistance(), 3, 5));
                System.out.print(dataObject.getKey());System.out.print('\t');System.out.print(cd[i-1]);System.out.print(' ');System.out.print(rd[i-1]);
                if(cd[i-1]==2.147483647E9D && rd[i-1]==2.147483647E9D)
                {System.out.print(' ');System.out.println('*');}
                else
                    System.out.println();
            }
            */
    }
}
