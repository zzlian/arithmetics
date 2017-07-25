package AdaBoost;

import Tool_Cart.Node;
import Tool_Cart.Classify_Cart;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by lenovo on 2017/7/21.
 */
public class Classify_AdaB {

    public static TreeMap<Double,ArrayList<ArrayList<Double>>> classifyDatas_AdaB(TreeMap<Double,Node> weight_nodes,ArrayList<ArrayList<Double>> datas,ArrayList<String> attributes,ArrayList<Integer> counts){
        TreeMap<Double,ArrayList<ArrayList<Double>>> label_datas=new TreeMap<Double,ArrayList<ArrayList<Double>>>();
        double label;


        //多个数据的测试
        //遍历每一个数据，每个数据进行测试返回标签
        //并将对应的结果保存在TreeMap中，一个标签对应一个划分的数据的区域
        for(ArrayList<Double> d:datas){
            label=classifyData_AdaB(weight_nodes,d,attributes);

            //记录被正确和错误分类的个数
            if(label==d.get(d.size()-1)){
                counts.set(0,counts.get(0)+1);
            }
            else{
                counts.set(1,counts.get(1)+1);
            }

            //将数据按得到的标签划分区域保存
            if(!label_datas.keySet().contains(label)){
                label_datas.put(label,new ArrayList<ArrayList<Double>>());
                label_datas.get(label).add(d);
            }
            else{
                label_datas.get(label).add(d);
            }
        }
        return label_datas;
    }

    public static double classifyData_AdaB(TreeMap<Double,Node> weight_nodes,ArrayList<Double> data,ArrayList<String> attributes){
        TreeMap<Double,Double> label_weights=new TreeMap<Double,Double>();
        double label=0.0;
        double weight;
        int i=0;


        for(double w:weight_nodes.keySet()){
            label= Classify_Cart.classifyData(weight_nodes.get(w),data,attributes);

            if(!label_weights.keySet().contains(label)){
                label_weights.put(label,w);
            }
            else{
                label_weights.put(label,label_weights.get(label)+w);
            }
        }

        for(double lab:label_weights.keySet()){
            if(label_weights.get(label)<label_weights.get(lab)){
                label=lab;
            }
        }
        return label;
    }

}
