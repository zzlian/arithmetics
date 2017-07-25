package C4_5;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

import Tool_C4_5.Classify_C4_5;
import Tool_C4_5.Node;
import Tool_C4_5.CreatDecisionTree_C4_5;
import Tool_C4_5.PostPruning;

/**
 * Created by lenovo on 2017/7/17.
 */
public class C4_5 {

    static int i=0;

    //测试C4.5算法
    public static void main(String[] arg){
        Node node=new Node();
        TreeMap<String,ArrayList<ArrayList<String>>> label_datas;
        ArrayList<ArrayList<String>> creatDatas=new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> textDatas=new ArrayList<ArrayList<String>>();
        ArrayList<String> attributes=new ArrayList<String>();
        String creatFile="C:/Users/lenovo/Desktop/adult.data";
        String textFile="C:/Users/lenovo/Desktop/adult.data";
        ArrayList<Integer> counts=new ArrayList<Integer>();
        ArrayList<ArrayList<String>> errors=new ArrayList<ArrayList<String>>();
        int count=0;

        counts.add(0);
        counts.add(0);

        //读取数据进行训练和测试
        loadDatas(creatDatas,attributes,creatFile);

        //创建决策树，根节点保存在node中
        try {
            node= CreatDecisionTree_C4_5.creatDecisionTree(creatDatas,attributes);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        System.out.println("----------已由训练集构建完成决策树---------");

        System.out.println("训练集的数据个数为："+creatDatas.size());

        loadDatas(textDatas,new ArrayList<String>(),textFile);

        //采用悲观剪枝的方法进行后剪枝
        //PostPruning.postPruning(node,datas.size());

        //将测试数据进行分类，并将分类结果保存在Map中
        label_datas= Classify_C4_5.classifyTwo(textDatas,node,attributes,counts,errors);

        System.out.println("测试数据个数为："+textDatas.size());

        count=counts.get(0)+counts.get(1);

        System.out.println("\n正确率为："+(double)counts.get(0)/(double)count);
        System.out.println("测试数据中属性的值在决策树中找不到对应的属性的值的个数为："+errors.size());

        //输出分类结果
        System.out.println("\n分类结果为：\n");
        for(String label:label_datas.keySet()){
            System.out.println("类标签-----"+label);
            System.out.println("数据个数为："+label_datas.get(label).size()+"\n");
        }
    }



    //遍历决策树
    public static void printNode(Node node){
        if(node.getAttribute().equals("leafNode")){
            System.out.println("leafNode\n");
            return;
        }

        i++;
        System.out.println(node.getAttribute());
        for(Node nod:node.getChildNodes()){
            printNode(nod);
        }
    }


    //读取数据进行测试
    public static void loadDatas(ArrayList<ArrayList<String>> datas,ArrayList<String> attributes,String fileName){
        String line=null;
        FileReader fileReader= null;
        try {
            fileReader = new FileReader(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader=new BufferedReader(fileReader);
        try {
            while((line=reader.readLine())!=null){
                ArrayList<String> data=new ArrayList<String>();
                for(String s:line.split(",")){
                    data.add(s);
                }
                datas.add((ArrayList<String>) data.clone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i=0;
        while(i<datas.get(0).size()-1){
            attributes.add("c"+i);
            i++;
        }
    }

}
