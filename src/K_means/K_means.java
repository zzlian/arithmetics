package K_means;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/15.
 */
public class K_means {

    //测试k_means算法
    public static void main(String[] arg){
        ArrayList<ArrayList<ArrayList<Double>>> cs;
        ArrayList<ArrayList<Double>> datas=new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> centers;
        ArrayList<ArrayList<Double>> newCenters;
        ArrayList<String> labels=new ArrayList<String>();
        String fileName="C:/Users/lenovo/Desktop/iris.data";
        int k=3;

        //用datas保存测试数据
        //用centers保存分类中心
        //用cs表示各个分类区域的集合
        LoadDatas.loadDatas(datas,fileName,labels);
        centers=InitCenters.initCenters((ArrayList<ArrayList<Double>>) datas.clone(),k);
        cs=Classify.classify((ArrayList<ArrayList<Double>>)datas.clone(),(ArrayList<ArrayList<Double>>)centers.clone());

        //在已经分好的各个区域中，重新寻找合适的分类中心
        //直到第n次和第n+1次寻找的分类中心一致时，循坏结束，分类完成
        while(true){
            newCenters=CalNewCenters.calNewCenters((ArrayList<ArrayList<ArrayList<Double>>>) cs.clone(), (ArrayList<ArrayList<Double>>) centers.clone());
            if(centers.equals(newCenters)){
                break;
            }
            centers=(ArrayList<ArrayList<Double>>)newCenters.clone();
            cs=Classify.classify((ArrayList<ArrayList<Double>>) datas.clone(),(ArrayList<ArrayList<Double>>)centers.clone());

        }


        /*System.out.println("原始数据为：");
        int i=1;
        for(ArrayList<Double> data:datas){
            System.out.print(data+", ");
            if(i%30==0){
                System.out.println();
            }
            i++;
        }*/

        System.out.println("......已经分好类........");
        int rCount=rightCount(cs,datas);
        System.out.println("分类正确率为："+(double)rCount/150.0+"\n");

        int i=0;
        for(ArrayList<ArrayList<Double>> c:cs){
            System.out.println("分类中心为："+centers.get(i));
            System.out.println("属于该类的数据个数为："+c.size()+"\n");
            i++;
        }
    }


    //计算被正确分类的个数
    public static int rightCount(ArrayList<ArrayList<ArrayList<Double>>> cs,ArrayList<ArrayList<Double>> datas){
        int count=0,rCount=0;
        ArrayList<Integer> counts=new ArrayList<Integer>();
        ArrayList<Integer> indexs=new ArrayList<Integer>();
        int i=0;

        while(i<cs.size()){
            counts.add(0);
            i++;
        }

        //计算被正确分类的个数
        for(ArrayList<ArrayList<Double>> c:cs){
            //计算每一个分类区域里被正确分类的个数
            for(ArrayList<Double> data:c){
                //计算在一个划分区域里，数据在原数据集中的索引值在哪个范围的个数多
                //不在多数索引区域的数据均为被分类错误的数据
                if(datas.indexOf(data)<50&&!indexs.contains(0)){
                    counts.set(0,counts.get(0)+1);
                }
                else if(datas.indexOf(data)<100&&!indexs.contains(1)){
                    counts.set(1,counts.get(1)+1);
                }
                else if(datas.indexOf(data)>=100&&!indexs.contains(2)){
                    counts.set(2,counts.get(2)+1);
                }
            }

            //记录前面考虑过的索引区域的指标，不再考虑这个区域
            i=0;
            int j=0;
            count=counts.get(0);
            for(int count_0:counts){
                if(count<count_0){
                    count=count_0;
                    j=i;
                }
                i++;
            }
            //记录区域指标和记录被正确分类的个数
            indexs.add(j);
            rCount+=count;

            count=0;
            i=0;
            while(i<cs.size()){
                counts.set(i,0);
                i++;
            }
        }
        return rCount;
    }
}
