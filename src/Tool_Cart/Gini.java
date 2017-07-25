package Tool_Cart;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lenovo on 2017/7/18.
 */
public class Gini {

    public ArrayList<ArrayList<Double>> datas;
    public ArrayList<String> attributes;

    public Gini(ArrayList<ArrayList<Double>> datas, ArrayList<String> attributes){
        this.datas= (ArrayList<ArrayList<Double>>) datas.clone();
        this.attributes= (ArrayList<String>) attributes.clone();
    }

    //返回该属性划分的最小基尼指数和分裂点
    public ArrayList<Double> minGini(int indexAttr){
        ArrayList<Double> attrValues=new ArrayList<Double>();
        ArrayList<Double> ginis=new ArrayList<Double>();
        ArrayList<Double> gini_diValue=new ArrayList<Double>();
        double gi=0.0;

        //将数据中该属性所有不同的值保存在attrValues中
        for(ArrayList<Double> data:datas){
            if(!attrValues.contains(data.get(indexAttr))){
                attrValues.add(data.get(indexAttr));
            }
        }

        //进行排序
        Collections.sort(attrValues);

        if(attrValues.size()==1){
            gini_diValue.add(1.0);
            gini_diValue.add(attrValues.get(0));
            return gini_diValue;
        }

        //得到各个划分的基尼指数，将其保存在ginis中
        int i=0;
        while(i<(attrValues.size()-1)){
            gi=attrGini(attrValues,i,indexAttr);
            ginis.add(gi);
            i++;
        }

        //得到最小的划分基尼指数和对应的索引
        i=0;
        int j=0;

        /*System.out.println("attrValues----"+attrValues.size());
        System.out.println("index---"+indexAttr);
        System.out.println("attrs---"+attributes);
        System.out.println("dattas---"+datas+"\n");*/

        gi=ginis.get(0);
        for(double g:ginis){
            if(gi>g){
                j=i;
                gi=g;
            }
            i++;
        }

        //将最小基尼指数和分裂值保存并返回
        gini_diValue.add(gi);
        gini_diValue.add((attrValues.get(j)+attrValues.get(j+1))/2);
        return (ArrayList<Double>) gini_diValue.clone();
    }

    //计算属性单个划分的基尼指数
    public double attrGini(ArrayList<Double> attrValues,int indexValue,int indexAttr){
        ArrayList<ArrayList<Double>> datas1=new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> datas2=new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<ArrayList<Double>>> valueDatas1=new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<ArrayList<ArrayList<Double>>> valueDatas2=new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<Double> labels1=new ArrayList<Double>();
        ArrayList<Double> labels2=new ArrayList<Double>();
        ArrayList<Integer> counts1=new ArrayList<Integer>();
        ArrayList<Integer> counts2=new ArrayList<Integer>();
        double attrGini=0;
        double diValue=attrValues.get(indexValue);
        double pc1=0;
        double pc2=0;

        //按分裂点将数据划分为两个区域
        for(ArrayList<Double> data:datas){
            if(data.get(indexAttr)<=diValue){
                datas1.add(data);
            }
            else{
                datas2.add(data);
            }
        }

        //计算两个数据区域在原数据中所占的比例
        pc1=(double)datas1.size()/(double)datas.size();
        pc2=(double)datas2.size()/(double)datas.size();

        //各自得到两个数据分区中的标签值
        int indexLabel=datas.get(0).size()-1;
        for(ArrayList<Double> data:datas1){
            if(!labels1.contains(data.get(indexLabel))){
                labels1.add(data.get(indexLabel));
            }
        }
        for(ArrayList<Double> data:datas2){
            if(!labels2.contains(data.get(indexLabel))){
                labels2.add(data.get(indexLabel));
            }
        }

        //计算两个数据区域中各自标签所占数据的个数
        int i=0,j=0;
        for(double label:labels1){
            counts1.add(0);
            for(ArrayList<Double> data:datas1){
                if(label==data.get(indexLabel)){
                    counts1.set(i,counts1.get(i)+1);
                }
            }
            i++;
        }
        for(double label:labels2){
            counts2.add(0);
            for(ArrayList<Double> data:datas2){
                if(label==data.get(indexLabel)){
                    counts2.set(j,counts2.get(j)+1);
                }
            }
            j++;
        }

        //计算基尼指数
        int sizeData1=datas1.size();
        double temp=0;
        for(int count1:counts1){
            temp+=Math.pow((double)count1/(double)sizeData1,2);
        }
        attrGini=(1.0-temp)*pc1;
        temp=0.0;
        int sizeData2=datas2.size();
        for(int count2:counts2){
            temp+=Math.pow((double)count2/(double)sizeData2,2);
        }
        attrGini+=(1.0-temp)*pc2;

        return attrGini;
    }

}
