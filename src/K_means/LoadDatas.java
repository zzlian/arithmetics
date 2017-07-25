package K_means;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/15.
 */
public class LoadDatas {


    //读取数据进行测试
    static public void loadDatas(ArrayList<ArrayList<Double>> datas,String fileName,ArrayList<String> labels){
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
                    if(i==4){
                        labels.add(s);
                        break;
                    }
                    data.add(Double.parseDouble(s));
                    i++;
                }
                datas.add((ArrayList<Double>) data.clone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
