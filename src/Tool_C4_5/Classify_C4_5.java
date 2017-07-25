package Tool_C4_5;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by lenovo on 2017/7/17.
 */
public class Classify_C4_5 {

    //将单个的数据依据决策树进行分类，并返回标签
    public static String classify(ArrayList<String> data, Node node, ArrayList<String> attributes){
        String label=null;
        String attribute=null;
        String attrValue=null;
        Node childNode;

        //若该节点为叶子节点，返回标签
        if(node.getAttribute().equals("leafNode")){
            return node.getLabel();
        }

        attribute=node.getAttribute();

        //计算该节点属性在属性列表中对应的索引
        int i=0;
        for(String attr:attributes){
            if(attr.equals(attribute)){
                break;
            }
            i++;
        }

        //依据索引找到数据中该属性的值
        attrValue = data.get(i);

        //计算符合该值的对应的子节点的索引
        int j = 0;
        for (String value : node.getPathNames()) {
            if (value.equals(attrValue)) {
                break;
            }
            j++;
        }

        //当测试数据在判断该属性时找不到对应子节点，则数据不能分类
        if(j==node.getPathNames().size()){
            return null;
        }

        //得到子节点
        childNode = node.getChildNodes().get(j);

        //若子节点为叶子节点，返回子节点的标签
        if (childNode.getAttribute().equals("leafNode")) {
            return childNode.getLabel();
        }

        //删除测过的属性和数据中该属性对应的值，继续查找
        ArrayList<String> attributes_0= (ArrayList<String>) attributes.clone();
        attributes_0.remove(attribute);
        data.remove(i);
        label=classify(data,childNode,attributes_0);
        return label;
    }

    //将多个数据进行分类，并返回标签和对应数据的Map
    public static TreeMap<String,ArrayList<ArrayList<String>>> classifyTwo(ArrayList<ArrayList<String>> datas, Node node, ArrayList<String> attributes,ArrayList<Integer> counts,ArrayList<ArrayList<String>> errors){
        TreeMap<String,ArrayList<ArrayList<String>>> label_datas=new TreeMap<String,ArrayList<ArrayList<String>>>();
        String label=null;

        int flag=0;
        for(ArrayList<String> data:datas){
            //得到单个数据的标签
            label=classify((ArrayList<String>) data.clone(),node,(ArrayList<String>)attributes.clone());

            //数据中某个属性的值在建成的决策树的对应属性中找不到对应的值存在，不能分类
            if(label==null){
                errors.add((ArrayList<String>) data.clone());
                continue;
            }

            //计算被正确和错误分类的个数
            if(label.equals(data.get(data.size()-1))){
                counts.set(0,counts.get(0)+1);
            }
            else{
                counts.set(1,counts.get(1)+1);
            }

            //按标签进行分类
            for(String key:label_datas.keySet()){
                if(key.equals(label)){
                    label_datas.get(key).add((ArrayList<String>) data.clone());
                    flag=1;
                    break;
                }
                flag=0;
            }
            if(flag==0) {
                ArrayList<ArrayList<String>> label_data = new ArrayList<ArrayList<String>>();
                label_data.add((ArrayList<String>) data.clone());
                label_datas.put(label, label_data);
            }
        }
        return label_datas;
    }

}
