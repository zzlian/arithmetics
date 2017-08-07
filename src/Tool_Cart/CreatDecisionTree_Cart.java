package Tool_Cart;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/18.
 */
public class CreatDecisionTree_Cart {

    //依据训练集创建决策树
    public Node creatDecisionTree(ArrayList<ArrayList<Double>> datas, ArrayList<String> attributes){
        Node node=new Node();
        Double label=0.0;
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
            SplitAttr splitAttr=new SplitAttr(datas,attributes);
            int i=0;

            //计算得到所有属性的所有划分中的最小基尼指数和分裂点
            for(String attr:attributes){
                sE_diValue=splitAttr.split(i);
                sE_diValues.add((ArrayList<Double>) sE_diValue.clone());
                i++;
            }

            //得到最小基尼指数的属性的索引
            i=0;
            int j=0;
            double sE=sE_diValues.get(0).get(0);
            for(ArrayList<Double> gi_dv:sE_diValues){
                if(sE>gi_dv.get(0)){
                    sE=gi_dv.get(0);
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

    //判断节点中的数据是否属于同一类标签
    public double isPure(ArrayList<ArrayList<Double>> datas){
        ArrayList<Double> labels=new ArrayList<Double>();

        /*System.out.println(datas);*/

        int indexLabel=datas.get(0).size()-1;

        for(ArrayList<Double> data:datas){
            if(!labels.contains(data.get(indexLabel))){
                labels.add(data.get(indexLabel));
            }
        }

        if(labels.size()>1){
            return -1.0;
        }
        return labels.get(0);
    }


    //计算叶子节点数据集各属性的平均值
    public double getResult(ArrayList<ArrayList<Double>> datas){
        double mV = 0;
        int dataNum = datas.size();
        int lastIndex = datas.get(0).size() - 1;

        for(ArrayList<Double> data : datas){
            mV = mV +data.get(lastIndex);
        }
        mV = mV / dataNum;
        return mV;
    }

    //返回数据中出现次数最多的标签
    public double maLabel(ArrayList<ArrayList<Double>> datas){
        ArrayList<Double> labels=new ArrayList<Double>();
        ArrayList<Integer> counts=new ArrayList<Integer>();
        int indexLabel=datas.get(0).size()-1;

        //得到数据中的标签
        for(ArrayList<Double> data:datas){
            if(!labels.contains(data.get(indexLabel))){
                labels.add(data.get(indexLabel));
            }
        }

        //计算数据在贴有各个标签的数目
        int i=0;
        for(double label:labels){
            counts.add(0);
            for(ArrayList<Double> data:datas){
                if(label==data.get(indexLabel)){
                    counts.set(i,counts.get(i)+1);
                }
            }
            i++;
        }

        //得到含有数据最多的标签的索引
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
        return labels.get(j);
    }

    //依据分裂点将节点数据分成两部分
    public ArrayList<ArrayList<ArrayList<Double>>> creatChildDatas(ArrayList<ArrayList<Double>> datas,double diValue,int indexAttr){
        ArrayList<ArrayList<ArrayList<Double>>> childDatas=new ArrayList<ArrayList<ArrayList<Double>>>();
        childDatas.add(new ArrayList<ArrayList<Double>>());
        childDatas.add(new ArrayList<ArrayList<Double>>());
        for(ArrayList<Double> data:datas){
            if(data.get(indexAttr)<=diValue){
                childDatas.get(0).add(data);
            }
            else{
                childDatas.get(1).add(data);
            }
        }
        return (ArrayList<ArrayList<ArrayList<Double>>>) childDatas.clone();
    }
}
