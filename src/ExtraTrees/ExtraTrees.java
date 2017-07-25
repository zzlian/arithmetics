package ExtraTrees;

import Tool_Cart.LoadDatas;
import Tool_Cart.Node;
import Tool_Trees.ClassifyDatas;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by lenovo on 2017/7/20.
 */
public class ExtraTrees {

    public static void main(String[] arg){
        ArrayList<Node> nodes;
        ArrayList<ArrayList<Double>> creatDatas=new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> textDatas=new ArrayList<ArrayList<Double>>();
        ArrayList<String> attributes=new ArrayList<String>();
        CreatExtraTrees creatET=new CreatExtraTrees();
        TreeMap<Double,ArrayList<ArrayList<Double>>> label_datas;
        ArrayList<Integer> counts=new ArrayList<Integer>();
        int count;
        String creatFile="C:/Users/lenovo/Desktop/abalone.data";
        String textFile="C:/Users/lenovo/Desktop/abalone.data";
        double label=0;
        int k=2;

        counts.add(0);
        counts.add(0);

        //读取数据构建决策树和进行数据的测试
        LoadDatas.loadDatas(creatDatas,attributes,creatFile);


        nodes=creatET.creatExtraTrees(creatDatas,attributes,k);
        System.out.println("------------已成功构建完决策树-----------");
        System.out.println("决策树的数目为："+k+"棵");

        //读取测试数据
        LoadDatas.loadDatas(textDatas,new ArrayList<String>(),textFile);

        //多个数据的测试
        label_datas=ClassifyDatas.classifyDatas(nodes,textDatas,attributes,counts);

        count=counts.get(0)+counts.get(1);
        System.out.println("分类正确率为："+(double)counts.get(0)/(double)count);

        int i=0;
        System.out.println("\n分类结果为：");
        for(double lab:label_datas.keySet()){
            System.out.print("标签为"+lab+"对应的数据个数为：");
            System.out.print(label_datas.get(lab).size()+"\t\t\t");
            i++;
            if(i%2==0){
                System.out.println();
            }
        }


        //遍历每棵树的每个节点
        /*for(Node node:nodes){
            putNode(node);
            System.out.println();
        }*/

    }

    public static void putNode(Node node){
        if(node.getAttribute().equals("leafNode")||node.getChildNodes().size()==0){
            System.out.println("leafNode");
            System.out.println(node.getLabel());
            return;
        }

        System.out.println("nodeSize----"+node.getChildNodes().size());

        System.out.println(node.getAttribute());
        putNode(node.getChildNodes().get(0));
        putNode(node.getChildNodes().get(1));
    }
}
