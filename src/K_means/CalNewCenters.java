package K_means;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/15.
 */
public class CalNewCenters {

    //在生成的各个分类区域中，找出各个区域新的分类中心
    static public ArrayList<ArrayList<Double>> calNewCenters(ArrayList<ArrayList<ArrayList<Double>>> cs,ArrayList<ArrayList<Double>> centers){
        ArrayList<Double> center=new ArrayList<Double>();
        ArrayList<Double> values=new ArrayList<Double>();

        //len_d表示点的维度
        //len_c表示每个类点的个数
        int len_d=centers.get(0).size();
        int len_c=0;
        int d;

        int i=0;
        //用values计算一个分类区域中所有点的坐标的平均值
        while(i<len_d){
            values.add(0.0);
            i++;
        }

        int k=0;
        //计算一个分类区域中所有点各个维度的和
        for(ArrayList<ArrayList<Double>> c:cs){
            for(ArrayList<Double> v:c){
                i=0;
                while(i<len_d){
                    values.set(i,values.get(i)+v.get(i));
                    i++;
                }
            }

            len_c=c.size();
            //在分类区域不为空的情况下，计算values中保存的维度的平均值
            //并将结果作为新的分类中心
            if(values.get(0)!=0){
                i=0;
                while(i<len_d){
                    values.set(i,values.get(i)/len_c);
                    center.add(values.get(i));
                    values.set(i,0.0);
                    i++;
                }
                centers.set(k, (ArrayList<Double>) center.clone());
            }
            k++;
            center.clear();
        }

        return (ArrayList<ArrayList<Double>>) centers.clone();
    }
}
