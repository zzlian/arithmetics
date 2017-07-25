package Apriori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by lenovo on 2017/7/14.
 */
public class FrequentSet1 {
    static HashMap<TreeSet<String>, Float> supportData = new HashMap<TreeSet<String>, Float>();
    static ArrayList<TreeSet<String>> L1 = new ArrayList<TreeSet<String>>();

    //由最初加载的数据找出候选频繁1项集
    public static ArrayList<TreeSet<String>> alterFC1(ArrayList<TreeSet<String>> dataSet) {
        ArrayList<TreeSet<String>> c1 = new ArrayList<TreeSet<String>>();
        TreeSet<String> s = new TreeSet<String>();

        //遍历最初加载数据里的每个集合的每个元素，并将其保存在c1中
        for (TreeSet<String> set : dataSet) {
            for (String str : set) {
                s.add(str);
                if (c1.contains(s)) {
                    s.clear();
                    continue;
                }
                c1.add((TreeSet<String>) s.clone());
                s.clear();
            }
        }
        return (ArrayList<TreeSet<String>>) c1.clone();
    }


    //由候选频繁1项集找出频繁1项集
    public static void frequentC1(ArrayList<TreeSet<String>> dataSet, ArrayList<TreeSet<String>> c1, float minSupport) {
        int count;
        float len = dataSet.size();

        //判断c1中元素在最初加载数据里出现的频率是否满足最小支持度
        //满足最小支持度的为频繁1项集
        for (TreeSet<String> s : c1) {
            count = 0;
            for (TreeSet<String> set : dataSet) {
                if(set.containsAll(s)){
                    count++;
                }
            }
            float support = (float) count / len;
            if (support >= minSupport) {
                supportData.put(s, support);
                L1.add(s);
            }
        }
    }
}
