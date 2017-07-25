package KNN;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/15.
 */
public class KNN {

    //测试KNN算法
    //将新加入的点进行分类
    public static void main(String[] arg){
        ArrayList<ArrayList<ArrayList<Double>>> cs=new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<Double> v=new ArrayList<Double>();
        String fileName="C:/Users/lenovo/Desktop/iris.data";
        int k=7;

        //得到数据类的集合
        /*cs= CreatDataClasses.creatDataClasses();*/

        //获取类集合和测试数据
        LoadDatas.loadDatas(cs,fileName,v);

        //获取测试点
        /*v.add(5.2);v.add(2.7);v.add(3.9);v.add(1.4);*/

        //显示原始各个类的数据
        int i=1;
        System.out.println("原始数据的集合：");
        for(ArrayList<ArrayList<Double>> ck:cs){
            System.out.println("集合"+i+"的个数为："+ck.size());
            i++;
        }

        System.out.println("\n新加入的点：");
        System.out.println(v);

        //将点加入到属性最接近的集合中
        Classify.classify(cs,v,k);

        i=1;
        System.out.println("\n加入新点后的集合：");

        for(ArrayList<ArrayList<Double>> ck:cs){
            System.out.println("集合"+i+"的个数为："+ck.size());
            i++;
        }
    }
}
