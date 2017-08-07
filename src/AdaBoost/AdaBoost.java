package AdaBoost;

import Tool_Cart.LoadDatas;
import Tool_Cart.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by lenovo on 2017/7/21.
 */
public class AdaBoost {

    public static void main(String[] arg){
        TreeMap<Double,Node> weight_nodes=new TreeMap<Double,Node>();
        ArrayList<ArrayList<Double>> creatDatas=new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> textDatas=new ArrayList<ArrayList<Double>>();
        TreeMap<Double,ArrayList<ArrayList<Double>>> result_datas;
        ArrayList<String> attributes=new ArrayList<String>();
        CreatAdaBoost creatAB=new CreatAdaBoost();
        ArrayList<Integer> counts=new ArrayList<Integer>();
        int count;
        String creatFile="C:/Users/lenovo/Desktop/abalone.data";
        String textFile="C:/Users/lenovo/Desktop/abalone.data";
        int k=5;

        counts.add(0);
        counts.add(0);

        //读取数据构建决策树和进行数据的测试
        LoadDatas.loadDatas(creatDatas,attributes,creatFile);

        //生成AdaBoost森林
        weight_nodes=creatAB.creatAdaBoost(creatDatas,attributes,k);
        System.out.println("-------------已成功构建完决策树-------------");

        System.out.println("决策树的个数为："+k+"棵");

        LoadDatas.loadDatas(textDatas,new ArrayList<String>(),textFile);

        //将测试数据进行分类
        result_datas=Classify_AdaB.classifyDatas_AdaB(weight_nodes,textDatas,attributes,counts);

        count=counts.get(0)+counts.get(1);
        System.out.println("分类正确率为："+(double)counts.get(0)/(double)count);

        int i=0;
        System.out.println("分类结果为：");
        for(double label:result_datas.keySet()){
            System.out.print("标签为"+label+"对应的数据个数为：");
            System.out.print(result_datas.get(label).size()+"\t\t\t");
            i++;
            if(i%2==0){
                System.out.println();
            }
        }


        /*//遍历决策树
        for(Node node:weight_nodes.values()){
            printNodes(node);
            System.out.println();
        }*/

    }


    //遍历决策树的每个节点
    public static void printNodes(Node node){
        if(node.getAttribute().equals("leafNode")){
            System.out.println(node.getResult());
            return;
        }

        System.out.println(node.getAttribute());

        if(node.getChildNodes().size()==0){
            return;
        }

        printNodes(node.getChildNodes().get(0));
        printNodes(node.getChildNodes().get(1));
    }
}
