package Tool_Cart;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/18.
 */
public class Node {

    private String attribute;     //保存节点的分裂属性
    private double diviValue=0.0;  //保存节点分裂点的值
    private ArrayList<Node> childNodes=new ArrayList<Node>();    //保存节点的子节点
    private Double result;


    private  ArrayList<ArrayList<Double>> datas;

    public ArrayList<ArrayList<Double>> getDatas() {
        return this.datas;
    }

    public void setDatas(ArrayList<ArrayList<Double>> datas) {
        this.datas = datas;
    }

    public void setAttribute(String attribute){
        this.attribute=attribute;
    }

    public String getAttribute(){
        return this.attribute;
    }

    public ArrayList<Node> getChildNodes(){
        return this.childNodes;
    }

    public void setChildNodes(ArrayList<Node> nodes){
        this.childNodes=nodes;

    }

    public double getDiviValue(){
        return this.diviValue;
    }

    public void setDiviValue(double value){
        this.diviValue= value;
    }

    public void setResult(double label){
        this.result=label;
    }

    public double getResult(){
        return this.result;
    }

    public void addChildNode(Node node){
        this.childNodes.add(node);
    }


}
