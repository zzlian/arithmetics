package K_means;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

/**
 * Created by lenovo on 2017/7/15.
 */
public class InitCenters {

    //找一个离数据集的点比较远的点，计算计算个点与它的距离，分段选取k个点作为初始分类中心
    static public ArrayList<ArrayList<Double>> initCenters(ArrayList<ArrayList<Double>> datas,int k){
        ArrayList<ArrayList<Double>> centers=new ArrayList<ArrayList<Double>>();
        ArrayList<Integer> indexs=new ArrayList<Integer>();
        ArrayList<Double> v=new ArrayList<Double>();
        ArrayList<Double> distances=new ArrayList<Double>();
        TreeMap<Double,ArrayList<Double>> dis_datas=new TreeMap<Double,ArrayList<Double>>();
        double distance=0.0;
        int len=datas.size();
        int index;

        //使点V与数据集里的点有共同的维度
        int i=0;
        while(i<datas.get(0).size()){
            v.add(0.0);
            i++;
        }

        //把数据集里所有点的维度加起来作为V点对应的维度值，使得V点离数据集足够远
        for(ArrayList<Double> data:datas){
            i=0;
            for(double s:data){
                v.set(i,v.get(i)+s);
                i++;
            }
        }

        //计算数据集里所有点到V点的距离，并将数据与对应的距离联系起来保存在dis_datas中
        for(ArrayList<Double> data:datas){
            i=0;
            distance=0.0;
            while(i<data.size()){
                distance+=Math.pow(data.get(i)-v.get(i),2);
                i++;
            }
            distances.add(distance);
            dis_datas.put(distance,data);
        }

        //将得到的距离进行排序
        Collections.sort(distances);

        //选取k个相邻差值相同的索引
        i=0;
        int j=0;
        while(i<k){
            indexs.add(j);
            j+=(len-1)/(k-1);
            i++;
        }

        //按与V的距离由近到远选取k个数据作为初始分类中心
        for(int index_0:indexs){
            centers.add(dis_datas.get(distances.get(index_0)));
        }

        return (ArrayList<ArrayList<Double>>) centers.clone();

    }
}
