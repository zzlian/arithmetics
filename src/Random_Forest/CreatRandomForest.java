package Random_Forest;

import java.util.ArrayList;
import Tool_Cart.CreatDecisionTree_Cart;
import Tool_Cart.Node;
import Tool_Cart.Gini;


/**
 * Created by lenovo on 2017/7/18.
 */
public class CreatRandomForest extends CreatDecisionTree_Cart {


    //构建随机森林，采用CART树的构建方法，在一些细节上进行了修改
    public ArrayList<Node> creatRandomForest(ArrayList<ArrayList<Double>> datas, ArrayList<String> attributes, int k){
        ArrayList<Node> nodes=new ArrayList<Node>();
        ArrayList<ArrayList<Double>> datask=new ArrayList<ArrayList<Double>>();
        int sizeDatas=datas.size();
        int i=0,j=0;

        //生成k棵决策树
        while(i<k){
            j=0;
            Node node=new Node();
            //用bagging的方法生成新的训练集
            while(j<sizeDatas){
                datask.add((ArrayList<Double>) (datas.get((int)(sizeDatas*Math.random()))).clone());
                j++;
            }
            //由新的训练集构建决策树
            node=creatDecisionTree(datask,(ArrayList<String>)attributes.clone());
            nodes.add(node);
            datask.clear();
            i++;
        }

        return nodes;
    }


    //单棵决策树的构建
    public Node creatDecisionTree(ArrayList<ArrayList<Double>> datask, ArrayList<String> attributes){
        Node node=new Node();
        double label=0.0;

        //判断节点中数据的标签是否一致
        label=isPure(datask);

        //在标签一致时，将该节点设置在叶子节点
        if(label!=-1.0){
            node.setAttribute("leafNode");
            node.setLabel(label);
            return node;
        }
        //当节点数据少于某一指定阈值时，停止分裂，将该节点设为叶子节点，贴上多类标签
        /*else if(datask.size()<3){
            label=maLabel(datask);
            node.setAttribute("leafNode");
            node.setLabel(label);
            return node;
        }*/
        //递归构建决策树
        else {
            ArrayList<Integer> indexAttrs = new ArrayList<Integer>();
            ArrayList<ArrayList<Double>> gini_diValues = new ArrayList<ArrayList<Double>>();
            ArrayList<Double> gini_diValue;
            Gini gini = new Gini(datask, attributes);
            int sizeAttr = attributes.size();
            int indexAttr = 0;
            int i = 0;

            //随机选取节点中一半的属性进行比较，在选取的属性中，择取最优的分裂属性
            while (i < (sizeAttr / 2)) {
                indexAttr = (int) (sizeAttr * Math.random());
                if (!indexAttrs.contains(indexAttr)) {
                    gini_diValue = gini.minGini(indexAttr);
                    if(gini_diValue.get(0)==1.0){
                        continue;
                    }
                    gini_diValues.add((ArrayList<Double>) gini_diValue.clone());
                    indexAttrs.add(indexAttr);
                    i++;
                }
            }
            //当节点中的属性只有一个的时候，将该属性作为分裂属性
            if (sizeAttr == 1) {
                gini_diValue = gini.minGini(0);
                indexAttrs.add(0);
                gini_diValues.add((ArrayList<Double>) gini_diValue.clone());
            }

            //比较被选取的属性的最优划分的基尼指数，选取具有最小基尼指数的属性
            gini_diValue = gini_diValues.get(0);
            indexAttr = indexAttrs.get(0);
            i = 0;
            for (ArrayList<Double> gi_dv : gini_diValues) {
                if (gini_diValue.get(0) > gi_dv.get(0)) {
                    gini_diValue = gi_dv;
                    indexAttr = indexAttrs.get(i);
                }
                i++;
            }

            //设置节点分裂属性和分裂点
            node.setAttribute(attributes.get(indexAttr));
            node.setDiviValue(gini_diValue.get(1));

            //依据分裂属性将节点数据分为两个子数据
            ArrayList<ArrayList<ArrayList<Double>>> childDatas;
            childDatas = creatChildDatas(datask, gini_diValue.get(1), indexAttr);

            //递归生成子树
            i=0;
            for (ArrayList<ArrayList<Double>> childData : childDatas) {
                Node childNode;

                childNode = creatDecisionTree(childData, attributes);
                node.addChildNode(childNode);

                i++;
            }

            return node;
        }

    }
}
