package KNN;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/15.
 */
public class CreatDataClasses {


    //创建多个数据的集合用于分类
    static public ArrayList<ArrayList<ArrayList<Double>>> creatDataClasses(){
        ArrayList<ArrayList<ArrayList<Double>>> cs=new ArrayList<ArrayList<ArrayList<Double>>>();

        //用k表示类集合个数的递增个数，i表示每个类元素个数的递增个数，j则表示维度的递增数
        int i=0,j=0,k=0;
        while(k<5){
            //创建一个类集合
            ArrayList<ArrayList<Double>> datas=new ArrayList<ArrayList<Double>>();
            i=0;
            while(i<20){
                //创建一个类集合中的元素
                ArrayList<Double> data=new ArrayList<Double>();
                j=0;
                while(j<4){
                    data.add(40*Math.random());
                    j++;
                }
                //将数据加入类中
                datas.add((ArrayList<Double>) data.clone());
                i++;
            }
            cs.add((ArrayList<ArrayList<Double>>) datas.clone());
            k++;
        }

        return (ArrayList<ArrayList<ArrayList<Double>>>) cs.clone();
    }
}
