package Tool_C4_5;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/17.
 */
public class InfoGain {

    public ArrayList<ArrayList<String>> datas;
    public ArrayList<String> attributes;


    public InfoGain(ArrayList<ArrayList<String>> datas,ArrayList<String> attributes){
        this.datas= (ArrayList<ArrayList<String>>) datas.clone();
        this.attributes= (ArrayList<String>) attributes.clone();
    }

    //计算返回数据的熵
    public double getInfo(ArrayList<ArrayList<String>> datas){
        if(datas.isEmpty()){
            datas=this.datas;
        }

        ArrayList<Integer> counts=new ArrayList<Integer>();
        ArrayList<String> labels=new ArrayList<String>();
        ArrayList<Double> pc=new ArrayList<Double>();
        double entropy=0;
        int size_D=datas.size();
        int size_A=attributes.size();

        //计算总共有多少种分类，并将类的集合用labels表示
        for(ArrayList<String> data:datas){
            if(!labels.contains(data.get(size_A))){
                labels.add(data.get(size_A));
            }
        }

        //为各个分类建立计数数据的空间
        for(String label:labels){
            counts.add(0);
        }

        //计算各个类中所包含的数据的个数
        int i=0;
        for(String label:labels){
            for(ArrayList<String> data:datas){
                if(label.equals(data.get(size_A))){
                    counts.set(i,counts.get(i)+1);
                }
            }
            i++;
        }

        //计算信息熵
        for(int count:counts){
            if(count==0){
                continue;
            }
            entropy+=(-((double)count/(double)size_D)*Math.log((double)count/(double)size_D)/Math.log(2.0));
        }

        return entropy;
    }

    //计算返回infoAttribute和splitInfo
    public ArrayList<Double> getInfoA_and_splitInfo(int indexAttr){
        ArrayList<ArrayList<ArrayList<String>>> valueDatas=new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<String> values=new ArrayList<String>();
        ArrayList<Double> pcKey=new ArrayList<Double>();
        ArrayList<Double> infoA_splitInfo=new ArrayList<Double>();
        double infoA=0;
        double entropy_K=0;
        double splitInfo=0;
        int size_A=attributes.size();
        int size_D=datas.size();

        //得到指定属性的每个不同的值，并将其保存在values中
        for(ArrayList<String> data:datas){
            if(!values.contains(data.get(indexAttr))){
                values.add(data.get(indexAttr));
            }
        }

        //为该属性不同的值划分出一块存放数据的区域
        for(String value:values){
            valueDatas.add((ArrayList<ArrayList<String>>) (new ArrayList<ArrayList<String>>()).clone());
        }

        //将数据按该属性划分，并将每个划分保存在valueDatas中
        int i=0;
        for(String value:values){
            for(ArrayList<String> data:datas){
                if(value.equals(data.get(indexAttr))){
                    valueDatas.get(i).add((ArrayList<String>) data.clone());
                }
            }
            i++;
        }

        //按该属性划分成多个模块后，计算各个模块在总的数据中占有的比例
        for(ArrayList<ArrayList<String>> valueData:valueDatas){
            pcKey.add((double)valueData.size()/(double)size_D);
        }

        //依据模块占有比例计算分裂信息
        for(double pc:pcKey){
            if(pc==0){
                continue;
            }
            splitInfo+=-pc*Math.log(pc)/Math.log(2.0);
        }

        //计算划分信息熵
        i=0;
        for(ArrayList<ArrayList<String>> valueData:valueDatas){
            entropy_K=getInfo((ArrayList<ArrayList<String>>) valueData.clone());
            infoA+=pcKey.get(i)*entropy_K;
            i++;
        }

        infoA_splitInfo.add(infoA);
        infoA_splitInfo.add(splitInfo);

        return (ArrayList<Double>) infoA_splitInfo.clone();
    }

    //计算返回信息增益率
    public double getInfoRa(int indexAttr){
        ArrayList<Double> infoA_splitInfo=new ArrayList<Double>();
        double infoRa=0;
        double infoGain=0;
        double entropy=0;
        infoA_splitInfo=getInfoA_and_splitInfo(indexAttr);
        entropy=getInfo(new ArrayList<ArrayList<String>>());
        infoGain=entropy-infoA_splitInfo.get(0);
        infoRa=infoGain/infoA_splitInfo.get(1);
        return infoRa;
    }

    //判断数据是否属于同一个类标签
    public String isPure(){
        ArrayList<String> label=new ArrayList<String>();

        for(ArrayList<String> data:datas){
            if(!label.contains(data.get(data.size()-1))){
                label.add(data.get(data.size()-1));
            }
        }

        if(label.size()>1){
            return null;
        }
        return label.get(0);
    }


}
