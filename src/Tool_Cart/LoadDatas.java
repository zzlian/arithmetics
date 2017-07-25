package Tool_Cart;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/23.
 */
public class LoadDatas {

    public static void loadDatas(ArrayList<ArrayList<Double>> datas,ArrayList<String> attributes,String fileName){
        //读取数据构建决策树和进行数据的测试
        int i=0;
        String line=null;
        FileReader fileReader= null;
        try {
            fileReader = new FileReader(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader=new BufferedReader(fileReader);
        try {
            while((line=reader.readLine())!=null){
                ArrayList<Double> data=new ArrayList<Double>();
                i=0;
                for(String s:line.split(",")){
                    if(i==0){
                        i++;
                        continue;
                    }
                    data.add(Double.parseDouble(s));
                    i++;
                }
                datas.add((ArrayList<Double>) data.clone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        i=0;
        while(i<datas.get(0).size()-1){
            attributes.add("c"+i);
            i++;
        }
    }
}
