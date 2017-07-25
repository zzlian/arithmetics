package Tool_C4_5;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/17.
 */
public class CreatDecisionTree_C4_5 {

    //使用递归的方法构建决策树
    public static Node creatDecisionTree(ArrayList<ArrayList<String>> datas, ArrayList<String> attributes) throws CloneNotSupportedException {
        Node node=new Node();
        InfoGain gain=new InfoGain(datas,attributes);
        String label;
        label=gain.isPure();

        node.setNodeDatas(datas);

        //判断节点中数据是否属于同一个
        //若是，贴上标签，设置为叶节点返回
        if(label!=null){
            node.setAttribute("leafNode");
            node.setLabel(label);
            return node;
        }
        else if(attributes.size()==0){
            label=maLabel(datas);
            node.setAttribute("leafNode");
            node.setLabel(label);
            return node;
        }
        //前剪枝，当节点的数据低于某阈值时，停止分裂，将该节点标记为叶子节点，贴上多类标签
        /*else if(datas.size()<7){
            node.setAttribute("leafNode");
            label=maLabel(datas);
            node.setLabel(label);
        }*/
        //若不是，为节点选择分裂属性，进行划分
        else{

            double infoGainR=0;
            ArrayList<Double> infoGR=new ArrayList<Double>();
            int i=0;
            for(String attr:attributes){
                infoGainR=gain.getInfoRa(i);
                infoGR.add(infoGainR);
                i++;
            }

            if(infoGR.size()==0){
                System.out.println(attributes);
            }

            //计算增益率，将增益率最大的属性的索引保存在j中
            i=0;
            int j=0;
            infoGainR=infoGR.get(0);
            for(double infoR:infoGR){
                if(infoGainR<infoR){
                    j=i;
                    infoGainR=infoR;
                }
                i++;
            }

            //为节点设置分裂属性
            node.setAttribute(attributes.get(j));

            //将该分裂属性所有的取值保存在attrValues中
            ArrayList<String> attrValues=new ArrayList<String>();
            for(ArrayList<String> data:datas){
                if(!attrValues.contains(data.get(j))){
                    attrValues.add(data.get(j));
                }
            }

            for(String attrValue:attrValues){
                //得到该属性某个取值的数据集合
                ArrayList<ArrayList<String>> childDatas=new ArrayList<ArrayList<String>>();
                childDatas=creatChildTree(datas,attrValue,j);

                Node childNode=new Node();

                //将已经划分的数据的该属性的值去除
                //将属性列表中的该分裂属性去除
                //然后将去除后得到的数据和属性重新调用该方法进行分裂
                ArrayList<ArrayList<String>> childDatas_0= (ArrayList<ArrayList<String>>) childDatas.clone();
                ArrayList<String> attributes_0= (ArrayList<String>) attributes.clone();
                for(ArrayList<String> childData:childDatas_0){
                    childData.remove(j);
                }
                attributes_0.remove(j);
                childNode=creatDecisionTree(childDatas_0, attributes_0);

                node.getChildNodes().add(childNode);
                node.getPathNames().add(attrValue);
            }

        }
        return node;
    }

    //当节点中的数据量小于某一阈值时，停止划分，调用该方法，返回节点数据中标签最多的标签
    public static String maLabel(ArrayList<ArrayList<String>> datas){
        ArrayList<String> labels=new ArrayList<String>();
        ArrayList<Integer> counts=new ArrayList<Integer>();
        int count=0;

        //存储数据中的各个不同的标签
        for(ArrayList<String> data:datas){
            if(!labels.contains(data.get(data.size()-1))){
                labels.add(data.get(data.size()-1));
            }
        }

        //计算各个标签在数据中出现的次数
        int i=0;
        for(String label:labels){
            counts.add(0);
            for(ArrayList<String> data:datas){
                if(label.equals(data.get(data.size()-1))){
                    counts.set(i,counts.get(i)+1);
                }
            }
            i++;
        }

        //得到出现次数最多的标签的索引
        i=0;
        int j=0;
        count=counts.get(0);
        for(int c:counts){
            if(count<c){
                count=c;
                j=i;
            }
            i++;
        }

        return labels.get(j);
    }

    //划分节点时，调用该方法，返回子节点的数据集合
    public static ArrayList<ArrayList<String>> creatChildTree(ArrayList<ArrayList<String>> datas,String attrValue,int index){
        ArrayList<ArrayList<String>> childDatas=new ArrayList<ArrayList<String>>();
        for(ArrayList<String> data:datas){
            if(attrValue.equals(data.get(index))){
                childDatas.add((ArrayList<String>) data.clone());
            }
        }
        return (ArrayList<ArrayList<String>>) childDatas.clone();
    }
}
