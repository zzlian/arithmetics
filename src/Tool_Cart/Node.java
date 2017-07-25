package Tool_Cart;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/18.
 */
public class Node {

    //用attribute来保存节点的分裂属性
    //用childNodes来保存节点的子节点
    //用diviValue来保存节点分裂点的值
    private String attribute;
    private double diviValue=0.0;
    private ArrayList<Node> childNodes=new ArrayList<Node>();
    private Double label;


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

    public void setLabel(double label){
        this.label=label;
    }

    public double getLabel(){
        return this.label;
    }

    public void addChildNode(Node node){
        this.childNodes.add(node);
    }


}
