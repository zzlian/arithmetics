package K_means;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/15.
 */
public class Classify {

    //以k个center为中心对生成的数据分类到k个集合中
    static public ArrayList<ArrayList<ArrayList<Double>>> classify(ArrayList<ArrayList<Double>> datas, ArrayList<ArrayList<Double>> centers){
        ArrayList<ArrayList<ArrayList<Double>>> cs=new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<ArrayList<Double>> c;
        ArrayList<Double> dis=new ArrayList<Double>();
        double d;

        //生成k个分类区域，用于保存分类的点
        for(ArrayList<Double> v:centers){
            c=new ArrayList<ArrayList<Double>>();
            cs.add((ArrayList<ArrayList<Double>>) c.clone());
        }

        //len表示点的维度
        int len=datas.get(0).size();
        int j=0;
        int i=0;
        //遍历数据里的每一个点，计算每一个点跟所有分类中心的距离
        //将该点保存在距离最近的分类中心的集合中
        for(ArrayList<Double> v1:datas){
            i=0;
            d=0;
            //计算每个点跟所有分类中心的聚类
            for(ArrayList<Double> v2:centers){
                while(i<len){
                    d+=Math.pow(v1.get(i)-v2.get(i),2);
                    i++;
                }
                dis.add(d);
                d=0;
                i=0;
            }

            d=dis.get(0);
            i=0;
            j=0;
            //计算离该点最近的分类中心的指标，即第几个分类中心
            //并把该点保存在相应的第几个分类区域中
            for(double d0:dis){
                if(d>d0){
                    j=i;
                    d=d0;
                }
                i++;
            }
            dis.clear();
            cs.get(j).add((ArrayList<Double>) v1.clone());
        }
        return (ArrayList<ArrayList<ArrayList<Double>>>) cs.clone();
    }
}
