package Utilities;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart_AWT extends ApplicationFrame {

    private String applicationTitle;
    private String chartTitle;
    private String Xlablename;
    private String YvalueName;
    private double[] Y;
    private int[] X;


    public LineChart_AWT(int[] x,double[] y, String appTitle , String chTitle,String Xlenm,String Yluenm ) {
        super(appTitle);
        applicationTitle=appTitle;
        chartTitle=chTitle;
        X=x;
        Y=y;
        Xlablename=Xlenm;
        YvalueName=Yluenm;
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                Xlablename,YvalueName,
                createDataset(X,Y),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }



    public DefaultCategoryDataset createDataset(int[] x,double[] y ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );


        if(x.length!=y.length) return dataset;
        else {
            for(int i=0;i<x.length;i++) {
                dataset.addValue(y[i] , Xlablename, Integer.toString(x[i]));
            }

            return dataset;
        }
    }

    public void plot()
    {
        this.pack();
        RefineryUtilities.centerFrameOnScreen( this );
        this.setVisible( true );
    }
}
