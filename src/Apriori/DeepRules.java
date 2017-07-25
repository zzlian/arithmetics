package Apriori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by lenovo on 2017/7/15.
 */
public class DeepRules {

    //判断频繁项集中项与项之间是否存在强关联规则
    static public void deepRules(ArrayList<TreeSet<String>> fCs, HashMap<TreeSet<String>,Float> supportData, float minConf){
        int i=0,j=0,flag=1,n=0;
        float conf;

        //将频繁项集中不同的两项的支持相除，结果与最小置信度进行比较，判断是否存在强关联规则
        for(TreeSet<String> s1:fCs){
            j=0;
            for(TreeSet<String> s2:fCs){
                flag=1;
                if(i!=j){
                    for(String e:s1){
                        if(!s2.contains(e)){
                            flag=0;
                        }
                    }
                }
                else{
                    j++;
                    continue;
                }
                if(flag==1){
                    conf=supportData.get(s2)/supportData.get(s1);
                    if(conf>=minConf){
                        System.out.print(s1+"-->"+s2+" :");
                        System.out.print(conf+",\t\t\t\t\t\t");
                        n++;
                        if(n%2==0){
                            System.out.println();
                        }
                    }
                }
                j++;
            }
            i++;
        }

    }
}
