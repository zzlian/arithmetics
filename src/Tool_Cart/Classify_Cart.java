package Tool_Cart;

import java.util.ArrayList;
import java.util.TreeMap;


/**
 * Created by lenovo on 2017/7/18.
 */
public class Classify_Cart {

    //将多个数据进行分类，并将分类结果保存在TreeMap中返回
    public static TreeMap classifyDatas(Node node, ArrayList<ArrayList<Double>> datas, ArrayList<String> attributes,ArrayList<Integer> counts){
        TreeMap<Double,ArrayList<ArrayList<Double>>> result_datas=new TreeMap<Double,ArrayList<ArrayList<Double>>>();
        double result;

        int flag=0;
        for(ArrayList<Double> data:datas){
            //得到单个数据的标签
            result=classifyData(node,(ArrayList<Double>)data.clone(), (ArrayList<String>) attributes.clone());

            //记录样本被正确和错误分类的个数
            if(result >= data.get(data.size()-1) - 2 && result <= data.get(data.size()-1) + 2){
                counts.set(0,counts.get(0)+1);
            }
            else{
                counts.set(1,counts.get(1)+1);
            }

            //按标签进行分类
            for(double key:result_datas.keySet()){
                if(key==result){
                    result_datas.get(key).add((ArrayList<Double>) data.clone());
                    flag=1;
                    break;
                }
                flag=0;
            }
            if(flag==0) {
                ArrayList<ArrayList<Double>> label_data = new ArrayList<ArrayList<Double>>();
                label_data.add((ArrayList<Double>) data.clone());
                result_datas.put(result, label_data);
            }
        }
        return result_datas;

    }

    //将单个数据进行分类，返回标签
    public static double classifyData(Node node, ArrayList<Double> data, ArrayList<String> attributes){
        String nodeAttr=node.getAttribute();
        int indexAttr=0;
        double result;

        //若节点为叶子节点，返回节点的标签
        if(node.getAttribute().equals("leafNode")){
            return node.getResult();
        }

        //得到节点中划分属性在属性列表中的索引
        for(String attr:attributes){
            if(attr.equals(nodeAttr)){
                break;
            }
            indexAttr++;
        }

        //得到数据中对应属性的值
        double attrValue=data.get(indexAttr);

        //将该值与节点的分裂点进行比较，找到子节点
        Node childNode=new Node();
        if(attrValue<=node.getDiviValue()){
            childNode=node.getChildNodes().get(0);
        }
        else{
            childNode=node.getChildNodes().get(1);
        }

        //递归寻找数据对应的标签
        result=classifyData(childNode,data,attributes);

        return result;
    }
}
