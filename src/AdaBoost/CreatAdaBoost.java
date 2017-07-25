package AdaBoost;

import Tool_Cart.CreatDecisionTree_Cart;
import Tool_Cart.Node;
import Tool_Cart.Classify_Cart;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by lenovo on 2017/7/21.
 */
public class CreatAdaBoost extends CreatDecisionTree_Cart {

    //构建AdaBoost森林
    public TreeMap<Double,Node> creatAdaBoost(ArrayList<ArrayList<Double>> datas, ArrayList<String> attributes, int k){
        ArrayList<Double> dataWs=new ArrayList<Double>();
        ArrayList<ArrayList<Double>> datask=new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> datas_0;
        TreeMap<Double,Node> weight_nodes=new TreeMap<Double,Node>();
        int sizeDs=datas.size();
        int sizeDatas_0;
        double treeWeight=0.0;
        Node node;
        int i=0;

        //保存每一个数据的权重
        while(i<sizeDs){
            dataWs.add(1.0/(double)sizeDs);
            i++;
        }

        i=0;
        int j=0;
        while(i<k){
            j=0;
            //以每个数据权重的不同重新生成训练集，保证数据被抽取的机会与权重成正比
            datas_0=updateDatas((ArrayList<ArrayList<Double>>) datas.clone(),dataWs);
            sizeDatas_0=datas_0.size();

            //有放回的抽取数据进行构建决策树
            while(j<sizeDs){
                datask.add((ArrayList<Double>) datas_0.get((int)(sizeDatas_0*Math.random())).clone());
                j++;
            }
            //构建决策树
            node=creatDecisionTree(datask,attributes);
            datask.clear();
            treeWeight=getTreeWeight(node,datas,attributes,dataWs);
            //判断决策树的权重，若为0则放弃重构
            if(treeWeight==0.0){
                continue;
            }
            weight_nodes.put(treeWeight,node);
            i++;
        }
        return weight_nodes;
    }

    //在放回抽样的过程中，以每个元组的权重来增加被抽取的机会
    public ArrayList<ArrayList<Double>> updateDatas(ArrayList<ArrayList<Double>> datas,ArrayList<Double> dataWs){
        double minWeight=dataWs.get(0);
        int multiple;

        //得到数据集中得到数据的最小权重
        for(double dW:dataWs){
            if(minWeight>dW){
                minWeight=dW;
            }
        }

        //以最小权重的数据为基准，其它数据依据权重跟最小权重的倍数关系增加个数，以达到被抽取机会增大的效果
        int i=1;
        int j=0;
        for(double dW:dataWs){
            i=1;
            multiple=(int)(dW/minWeight);
            while(i<multiple){
                datas.add((ArrayList<Double>) datas.get(j).clone());
                i++;
            }
            j++;
        }

        return datas;
    }

    //以生成的决策树对原训练集的数据进行测试，计算错误率
    //在该决策树的错误率小于0.5的情况下，计算返回该决策树的决策权重，该决策树构建完成
    //若错误率大于等于0.5，返回0，重构该决策树
    public double getTreeWeight(Node node,ArrayList<ArrayList<Double>> datas,ArrayList<String> attributes,ArrayList<Double> dataWs){
        ArrayList<Integer> errorCorrect=new ArrayList<Integer>();
        int sizeD=datas.get(0).size();
        double weight=0.0;
        double mt=0.0;
        double label;
        int i=0;

        //判断数据分类是否正确，进行标记，同时计算错误率
        for(ArrayList<Double> data:datas){
            label= Classify_Cart.classifyData(node,data,attributes);
            if(label==data.get(sizeD-1)){
                errorCorrect.add(0);
            }
            else{
                errorCorrect.add(1);
                mt+=dataWs.get(i);
            }
            i++;
        }

        //计算决策树的决策权重,更新数据权重
        if(mt<0.5&&mt!=0.0){
            weight=Math.log((1.0-mt)/mt);
            if(mt!=0.0){
                updateDataWs(dataWs,errorCorrect,mt);
            }
        }

        return weight;
    }

    //每个元组所占权重的更新
    public void updateDataWs(ArrayList<Double> dataWs,ArrayList<Integer> errorCorrect,double mt){
        double weightSum=0;
        int sizeDW=dataWs.size();
        int i=0;

        //将分类正确的数据的权重进行更新
        for(int errorC:errorCorrect){
            if(errorC==0){
                dataWs.set(i,dataWs.get(i)*(mt/(1.0-mt)));
            }
            i++;
        }

        for(double dW:dataWs){
            weightSum+=dW;
        }

        //使得总权重始终为1
        i=0;
        while(i<sizeDW){
            dataWs.set(i,dataWs.get(i)/weightSum);
            i++;
        }
    }
}
