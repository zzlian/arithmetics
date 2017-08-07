package Tool_Trees;

import java.util.ArrayList;

import Tool_Cart.Node;
import Tool_Cart.Classify_Cart;

/**
 * Created by lenovo on 2017/7/20.
 */
public class ClassifyData {

    //单个新数据的测试分类
    public static double classifyData(ArrayList<Node> nodes,ArrayList<Double> data,ArrayList<String> attributes){
        double result;
        int treeNum = nodes.size();
        ArrayList<Double> results=new ArrayList<Double>();

        //将数据在没一棵决策树上进行测试，得到k个标签
        for(Node node:nodes){
            result= Classify_Cart.classifyData(node, (ArrayList<Double>) data.clone(), (ArrayList<String>) attributes.clone());
            results.add(result);
        }

        result = 0.0;
        for(double r : results){
            result = result + r;
        }
        return result / treeNum;
    }


    public static double getLabel(ArrayList<Double> labels){
        ArrayList<Integer> counts=new ArrayList<Integer>();
        ArrayList<Double> difLabels=new ArrayList<Double>();
        for(double lab:labels){
            if(!difLabels.contains(lab)){
                difLabels.add(lab);
            }
        }

        //记录不同标签占有的数量
        int i=0;
        for(double lab1:difLabels){
            counts.add(0);
            for(double lab2:labels){
                if(lab1==lab2){
                    counts.set(i,counts.get(i)+1);
                }
            }
            i++;
        }

        //取得占有数量最多的标签的索引j
        i=0;
        int j=0;
        int count=counts.get(0);
        for(int c:counts){
            if(count<c){
                count=c;
                j=i;
            }
            i++;
        }
        return difLabels.get(j);
    }
}
