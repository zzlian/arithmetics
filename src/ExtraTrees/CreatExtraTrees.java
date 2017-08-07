package ExtraTrees;

import Tool_Cart.CreatDecisionTree_Cart;
import Tool_Cart.Node;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/20.
 */
public class CreatExtraTrees extends CreatDecisionTree_Cart {

    public ArrayList<Node> creatExtraTrees(ArrayList<ArrayList<Double>> datas,ArrayList<String> attributes,int k){
        ArrayList<Node> nodes=new ArrayList<Node>();
        Node node;
        int i=0;

        while(i<k){
            node=creatDecisionTree(datas, attributes);
            nodes.add(node);
            i++;
        }
        return nodes;
    }

    public Node creatDecisionTree(ArrayList<ArrayList<Double>> datas,ArrayList<String> attributes){
        Node node=new Node();
        double label=0.0;
        double result;

        label=isPure(datas);

        //若节点中的数据属于同一标签，则贴上标签，作为叶节点返回
        if(label!=-1.0){
            result = getResult(datas);
            node.setAttribute("leafNode");
            node.setResult(result);
            return node;
        }
        //当节点中的数据少于指定阈值时，贴上多类标签，作为叶节点返回
        else if(datas.size()<50){
            result = getResult(datas);
            node.setAttribute("leafNode");
            node.setResult(result);
            return node;
        }
        //创建子节点
        else{
            ArrayList<ArrayList<Double>> sE_diValues=new ArrayList<ArrayList<Double>>();
            ArrayList<Double> sE_diValue=new ArrayList<Double>();
            ExtraDivGini splitAttr=new ExtraDivGini(datas,attributes);
            int i=0;

            //计算得到所有属性的所有划分中的最小基尼指数和分裂点
            for(String attr:attributes){
                sE_diValue=splitAttr.splitA(i);
                sE_diValues.add((ArrayList<Double>) sE_diValue.clone());
                i++;
            }

            //得到最小基尼指数的属性的索引
            i=0;
            int j=0;
            double sE=sE_diValues.get(0).get(0);
            for(ArrayList<Double> sE_dv:sE_diValues){
                if(sE>sE_dv.get(0)){
                    sE=sE_dv.get(0);
                    j=i;
                }
                i++;
            }

            //当平方残差小于某一阈值时，停止分裂
            if(sE < 200){
                result = getResult(datas);
                node.setAttribute("leafNode");
                node.setResult(result);
                return node;
            }

            //设置属性和分裂点
            node.setAttribute(attributes.get(j));
            node.setDiviValue(sE_diValues.get(j).get(1));

            //依据分裂点将数据分成两部分
            ArrayList<ArrayList<ArrayList<Double>>> childDatas=new ArrayList<ArrayList<ArrayList<Double>>>();
            childDatas=creatChildDatas((ArrayList<ArrayList<Double>>) datas.clone(),node.getDiviValue(),j);

            //子节点递归创建子树
            for(ArrayList<ArrayList<Double>> childData:childDatas){
                Node childNode;

                childNode=creatDecisionTree(childData,attributes);
                node.addChildNode(childNode);
            }
        }
        return node;

    }
}
