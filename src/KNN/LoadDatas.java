package KNN;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/24.
 */
public class LoadDatas {

    //读取数据进行测试
    public static void loadDatas(ArrayList<ArrayList<ArrayList<Double>>> cs,String fileName,ArrayList<Double> v){
        ArrayList<ArrayList<Double>> datas=new ArrayList<ArrayList<Double>>();
        String line=null;
        FileReader fileReader= null;
        int i=0,j=0,flag=0;

        try {
            fileReader = new FileReader(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader=new BufferedReader(fileReader);
        try {
            while((line=reader.readLine())!=null){
                ArrayList<Double> data=new ArrayList<Double>();
                flag=0;
                i=0;
                j++;

                for(String s:line.split(",")){
                    if(i==4){
                        break;
                    }
                    if(j==44){
                        v.add(Double.parseDouble(s));
                        flag=1;
                    }
                    //获取数据进行分类
                    else{
                        data.add(Double.parseDouble(s));
                    }
                    i++;
                }
                if(flag==0){
                    datas.add((ArrayList<Double>) data.clone());
                }

                if(j%50==0){
                    cs.add((ArrayList<ArrayList<Double>>) datas.clone());
                    datas.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
