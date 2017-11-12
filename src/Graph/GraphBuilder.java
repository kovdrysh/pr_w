package Graph;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphBuilder {

    public static XYSeries buildSeries(String name, List<Integer> x, List<Integer> y){

        XYSeries series = new XYSeries(name);
        for (int i = 0; i < x.size() && i < y.size(); i++){
            series.add(x.get(i), y.get(i));
        }
        return series;
    }

    public static void buildGraph(String title, XYSeries...series) {
        XYSeriesCollection collection = new XYSeriesCollection();
        for (XYSeries serie : series) {
            collection.addSeries(serie);
        }
        buildGraph(title, collection);
    }

    public static void buildGraph(String title, XYSeriesCollection collection){
        final JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Х",
                "Y",
                collection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
//        XYPlot plot = (XYPlot) chart.getPlot();
//        String[] grade = x.toArray(new String[]{});
//        for (int i = 0; i < grade.length; i++){
//            System.out.println(i + " " +grade[i]);
//        }
//        SymbolAxis rangeAxis = new SymbolAxis("Words", grade);
//        rangeAxis.setTickUnit(new NumberTickUnit(1));
//        rangeAxis.setRange(0,grade.length);
//        rangeAxis.setVerticalTickLabels(true);
//        plot.setDomainAxis(rangeAxis);

        final ChartPanel panel = new ChartPanel(chart);
        JFrame frameGraph = new JFrame(title);
        frameGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGraph.setContentPane(panel);
        frameGraph.setSize(new Dimension(1000, 500));
        frameGraph.setVisible(true);
    }
}
