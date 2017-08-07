package Random_Forest;

import Tool_Cart.LoadDatas;
import Tool_Cart.Node;
import Tool_Trees.ClassifyDatas;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by lenovo on 2017/7/19.
 */
public class RandomForest {

    public static void main(String[] arg){
        ArrayList<Node> nodes;
        ArrayList<ArrayList<Double>> creatDatas=new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> textDatas=new ArrayList<ArrayList<Double>>();
        ArrayList<String> attributes=new ArrayList<String>();
        TreeMap<Double,ArrayList<ArrayList<Double>>> label_datas=new TreeMap<Double,ArrayList<ArrayList<Double>>>();
        CreatRandomForest creatRF=new CreatRandomForest();
        ArrayList<Integer> counts=new ArrayList<Integer>();
        int count;
        String creatFile="C:/Users/lenovo/Desktop/abalone.data";
        String textFile="C:/Users/lenovo/Desktop/abalone.data";
        int k=5;

        counts.add(0);
        counts.add(0);


        //读取数据构建决策树和进行数据的测试
        LoadDatas.loadDatas(creatDatas,attributes,creatFile);

        //由训练集生成k棵树的随机森林
        nodes=creatRF.creatRandomForest(creatDatas,attributes,k);
        System.out.println("--------已生成决策树--------");
        System.out.println("决策树的数量为："+k+"棵");

        //读取测试数据
        LoadDatas.loadDatas(textDatas,new ArrayList<String>(),textFile);


        //多个数据的测试
        label_datas=ClassifyDatas.classifyDatas(nodes,textDatas,attributes,counts);

        count=counts.get(0)+counts.get(1);
        System.out.println("分类正确率为："+(double)counts.get(0)/(double)count);

        System.out.println("分类结果为：");


        int i=0;
        for(double key:label_datas.keySet()){
            System.out.print("标签"+key+"对应的数据个数为："+label_datas.get(key).size()+"\t\t\t");
            //System.out.println("数据个数为："+label_datas.get(key).size()+"\n");
            i++;
            if(i%2==0){
                System.out.println();
            }
        }




    }

    //先序遍历所有节点
    public static  void putNode(Node node){
        if(node.getAttribute().equals("leafNode")){
            /*System.out.println(node.getLabel());*/
            if(node.getAttribute().equals("leafNode")){
                System.out.println(66666);
            }
            return;
        }

        System.out.println(node.getAttribute());
        putNode(node.getChildNodes().get(0));
        putNode(node.getChildNodes().get(1));
    }

}
