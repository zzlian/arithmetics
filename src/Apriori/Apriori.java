package Apriori;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by lenovo on 2017/7/14.
 */
public class Apriori {

    //测试apriori算法
    public static void main(String[] arg){
        ArrayList<TreeSet<String>> fCs=new ArrayList<TreeSet<String>>();
        HashMap<TreeSet<String>,Float> supportData=new HashMap<TreeSet<String>,Float>();

        ArrayList<TreeSet<String>> dataSet;
        dataSet=LoadDataSet.loadDataSet();

        HashMap<TreeSet<String>,Float> supportk;
        ArrayList<TreeSet<String>> fCK;
        ArrayList<TreeSet<String>> alterFC;
        float minSupport=0.5f;
        float minConf=0.4f;

        //调用CreatC1中的方法得到频繁1项集，并将其支持度保存在supportk中
        alterFC= FrequentSet1.alterFC1(dataSet);
        FrequentSet1.frequentC1(dataSet,alterFC,minSupport);
        fCK= (ArrayList<TreeSet<String>>) FrequentSet1.L1.clone();
        supportk= (HashMap<TreeSet<String>, Float>) FrequentSet1.supportData.clone();


        //保存频繁1项集和对应的支持度
        for(TreeSet<String> fC1:fCK){
            fCs.add((TreeSet<String>) fC1.clone());
        }
        for(TreeSet<String> key:supportk.keySet()){
            supportData.put((TreeSet<String>) key.clone(),supportk.get(key));
        }


        //计算出dataSet数据中的最大项数
        int k=0,m=0;
        for(TreeSet<String> s:dataSet){
            m=s.size();
            if(k<m){
                k=m;
            }
        }

        //由频繁k-1项集找出频繁k项集
        int i=1;
        while(true){
            //得到候选频繁k集
            alterFC= FrequentSetK.alterFCK((ArrayList<TreeSet<String>>) fCK.clone());
            if(alterFC.isEmpty()){
                break;
            }

            //由候选频繁k项集得到频繁k项集和对应的支持度
            FrequentSetK.frequentCK(dataSet,alterFC,minSupport);
            fCK= (ArrayList<TreeSet<String>>) FrequentSetK.fC.clone();
            supportk= (HashMap<TreeSet<String>, Float>) FrequentSetK.supportD.clone();

            //将频繁k项集保存在总的频繁项集中
            for(TreeSet<String> s1:fCK){
                if(fCs.contains(s1)){
                    continue;
                }
                fCs.add((TreeSet<String>) s1.clone());
            }

            //将频繁k项集对应的支持度保存在总的频繁项集支持度中
            for(TreeSet<String> key:supportk.keySet()){
                if(supportData.containsKey(key)){
                    continue;
                }
                supportData.put((TreeSet<String>) key.clone(),supportk.get(key));
            }
            i++;
            if(i==k||fCK.isEmpty()){
                break;
            }
        }

        System.out.println("原商品组合的集合为：");
        System.out.println(dataSet);

        System.out.println("最小支持度为："+minSupport+",  最小置信度为："+minConf);

        i=0;
        System.out.println("\n频繁项集如下：");
        for(TreeSet<String> set:fCs){
            System.out.print(set+"\t\t");
            i++;
            if(i%7==0){
                System.out.println();
            }
        }

        i=0;
        System.out.println("\n\n频繁项集对应的支持度如下:");
        for(TreeSet<String> s:supportData.keySet()){
            System.out.print(s+"-->");
            System.out.print(supportData.get(s)+"\t\t");
            i++;
            if(i%4==0){
                System.out.println();
            }
        }

        System.out.println("\n强关联规则--->置信度：");
        DeepRules.deepRules((ArrayList<TreeSet<String>>) fCs.clone(), (HashMap<TreeSet<String>, Float>) supportData.clone(),minConf);


    }
}
