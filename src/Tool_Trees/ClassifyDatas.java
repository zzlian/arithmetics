package Tool_Trees;

import Tool_Cart.Node;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by lenovo on 2017/7/20.
 */
public class ClassifyDatas {

    public static TreeMap<Double,ArrayList<ArrayList<Double>>> classifyDatas(ArrayList<Node> nodes,ArrayList<ArrayList<Double>>datas,ArrayList<String> attributes,ArrayList<Integer> counts){
        TreeMap<Double,ArrayList<ArrayList<Double>>> label_datas=new TreeMap<Double,ArrayList<ArrayList<Double>>>();
        double result;

        //多个数据的测试
        //遍历每一个数据，每个数据进行测试返回标签
        //并将对应的结果保存在TreeMap中，一个标签对应一个划分的数据的区域
        for(ArrayList<Double> d:datas){
            result=ClassifyData.classifyData(nodes,d,attributes);

            //计算样本被正确和错误分类的个数
            if(result >= d.get(d.size()-1) - 2 && result <= d.get(d.size()-1) + 2){
                counts.set(0,counts.get(0)+1);
            }
            else{
                counts.set(1,counts.get(1)+1);
            }

            //将数据按得到的标签划分区域保存
            if(!label_datas.keySet().contains(result)){
                label_datas.put(result,new ArrayList<ArrayList<Double>>());
                label_datas.get(result).add(d);
            }
            else{
                label_datas.get(result).add(d);
            }
        }

        return label_datas;
    }
}
