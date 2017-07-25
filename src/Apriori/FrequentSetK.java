package Apriori;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by lenovo on 2017/7/14.
 */
public class FrequentSetK {

    static ArrayList<TreeSet<String>> fC=new ArrayList<TreeSet<String>>();
    static HashMap<TreeSet<String>,Float> supportD=new HashMap<TreeSet<String>,Float>();


    //由频繁k项集找出候选频繁k+1项集
    public static ArrayList<TreeSet<String>> alterFCK(ArrayList<TreeSet<String>> fCK){
        ArrayList<TreeSet<String>> rC=new ArrayList<TreeSet<String>>();
        TreeSet<String> s;
        TreeSet<String> s1;
        TreeSet<String> s2;
        int k=fCK.get(0).size();
        int len=fCK.size();
        int i=0,j=1;

        //求频繁k项集中不同两项的并集，若并集的数据个数刚好为k+1个，则将其作为候选项
        while(i<(len-1)){
            s1= (TreeSet<String>) fCK.get(i).clone();
            j=i+1;
            while(j<len){
                s2= (TreeSet<String>) fCK.get(j).clone();
                s= (TreeSet<String>) s1.clone();

                //将s1和s2的并集保存在s中
                for(String str:s2){
                    s.add(str);
                }

                //若两个项的并集的数据个数超过k+1,则该项放弃
                if(s.size()>k+1){
                    j++;
                    continue;
                }

                //将s作为候选频繁项保存
                if(!rC.contains(s)){
                    rC.add((TreeSet<String>) s.clone());
                }
                j++;
            }
            i++;
        }
        return (ArrayList<TreeSet<String>>) rC.clone();
    }

    //由候选频繁k+1项集找频繁k+1项集
    public static void frequentCK(ArrayList<TreeSet<String>> dataSet,ArrayList<TreeSet<String>> rC,float minSupport){
        int count,flag=0;
        float len=dataSet.size();

        //判断候选频繁k+1项集里的元素是否满足最小支持度
        //若满足最小支持度，则为频繁k项集
        for(TreeSet<String> s:rC) {
            count = 0;
            flag=0;
            for (TreeSet<String> set : dataSet) {
                if(set.containsAll(s)){
                    count++;
                }
            }
            float support = (float) count / len;
            if (support >= minSupport) {
                supportD.put(s, support);
                fC.add(s);
            }
        }

    }
}
