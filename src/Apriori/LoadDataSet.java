package Apriori;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by lenovo on 2017/7/14.
 */
public class LoadDataSet {

  /*  public static void main(String[] arg){
        LoadDataSet text=new LoadDataSet();
        text.loadDataSet();
    }*/

    //加载进行测试的数据
    public static ArrayList<TreeSet<String>> loadDataSet(){
        ArrayList<TreeSet<String>> dataSet=new ArrayList<TreeSet<String>>();
        TreeSet<String> set=new TreeSet<String>();
        String[] str1={"1","2","4"};
        String[] str2={"2","3"};
        String[] str3={"1","3"};
        String[] str4={"1","3","4"};
        String[] str5={"1","2","3","4"};
        for(String str:str1){set.add(str);}
        dataSet.add((TreeSet<String>) set.clone());
        set.clear();
        for(String str:str2){set.add(str);}
        dataSet.add((TreeSet<String>) set.clone());
        set.clear();
        for(String str:str3){set.add(str);}
        dataSet.add((TreeSet<String>) set.clone());
        set.clear();
        for(String str:str4){set.add(str);}
        dataSet.add((TreeSet<String>) set.clone());
        set.clear();
        for(String str:str5){set.add(str);}
        dataSet.add((TreeSet<String>) set.clone());
        return (ArrayList<TreeSet<String>>) dataSet.clone();
    }

}
