package ExtraTrees;

import Tool_Cart.Gini;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lenovo on 2017/7/20.
 */
public class ExtraDivGini extends Gini {

    public ExtraDivGini(ArrayList<ArrayList<Double>> datas,ArrayList<String> attributes){
        super(datas,attributes);
    }

    public ArrayList<Double> minGini(int indexAttr){
        ArrayList<Double> attrValues=new ArrayList<Double>();
        ArrayList<Double> gini_diValue=new ArrayList<Double>();
        double gi;
        int rdIndex=0;

        //将数据中该属性所有不同的值保存在attrValues中
        for(ArrayList<Double> data:datas){
            if(!attrValues.contains(data.get(indexAttr))){
                attrValues.add(data.get(indexAttr));
            }
        }

        //进行排序
        Collections.sort(attrValues);

        //若数据总该属性对应的值一样，则设置基尼指数为1，与其它属性的基尼指数进行比较
        if(attrValues.size()==1){
            gini_diValue.add(1.0);
            gini_diValue.add(attrValues.get(0));
            return gini_diValue;
        }

        //随机选取属性的一个划分
        while(attrValues.size()>=2){
            if(rdIndex==attrValues.size()-1){
                rdIndex=(int)(attrValues.size()*Math.random());
            }
            else{
                break;
            }
        }

        /*System.out.println(attributes.get(indexAttr)+"---index---"+rdIndex);*/

        gi=attrGini(attrValues,rdIndex,indexAttr);
        gini_diValue.add(gi);
        gini_diValue.add(attrValues.get(rdIndex));
        return gini_diValue;
    }
}
