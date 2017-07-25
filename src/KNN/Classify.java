package KNN;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lenovo on 2017/7/15.
 */
public class Classify {

    //找出新加入的点周围最近的k个点
    //判断k个点出现在哪个集合中出现的次数多，并将该点加入进去
    static public ArrayList<ArrayList<ArrayList<Double>>> classify(ArrayList<ArrayList<ArrayList<Double>>> cs,ArrayList<Double> v,int k){
        ArrayList<ArrayList<Double>> ds=new ArrayList<ArrayList<Double>>();
        ArrayList<Integer> counts=new ArrayList<Integer>();
        ArrayList<Integer> delete_count=new ArrayList<Integer>();
        double d;
        int count=0;
        int len_d=v.size();

        //建立新加入的到各个类中各点距离的集合的区域
        for(ArrayList<ArrayList<Double>> ck:cs){
            ds.add((ArrayList<Double>) (new ArrayList<Double>()).clone());
        }

        int i=0,j=0;
        //计算新加入的点到各个类中各个点的距离
        for(ArrayList<ArrayList<Double>> ck:cs){
            for(ArrayList<Double> vk:ck){
                d=0;
                while(j<len_d){
                    d+=Math.pow(vk.get(j)-v.get(j),2);
                    j++;
                }
                ds.get(i).add(d);
            }
            i++;
        }


        //将新加入的点到各个类中的点的距离进行排序
        for(ArrayList<Double> dk:ds){
            Collections.sort(dk);
        }

        //根据集合的个数，建立相应的计数变量，供下面比较
        for(ArrayList<ArrayList<Double>> ck:cs){
            delete_count.add(0);
        }

        count=0;
        j=0;
        //比较各个距离集合的第一个元素
        //将最小的元素删除,同时记录各个集合被删除的元素的个数
        while(count<k){
            d=ds.get(0).get(0);
            i=0;
            j=0;
            for(ArrayList<Double> dk:ds){
                if(dk.size()==0){
                    i++;
                    continue;
                }
                if(d>dk.get(0)){
                    d=dk.get(0);
                    j=i;
                }
                i++;
            }
            delete_count.set(j,delete_count.get(j)+1);
            ds.get(j).remove(0);
            count++;
        }

        //比较哪个集合被删除的元素的个数
        // 被删除最多的那个集合与新加入的点的属性最接近
        count=delete_count.get(0);
        i=0;
        j=0;
        int flag=1;
        for(int count_d:delete_count){
            if(count==count_d&&j!=0){
                flag=0;
            }
            if(count<count_d){
                count=count_d;
                j=i;
                flag=1;
            }
            i++;
        }

        if(flag==0){
            System.out.println("新加入的点与属性最接近的集合的个数有多个\n分类失败！！！");
        }
        else{
            System.out.println("\n分类成功！！！");
            cs.get(j).add((ArrayList<Double>) v.clone());
        }

        return (ArrayList<ArrayList<ArrayList<Double>>>) cs.clone();

    }
}
