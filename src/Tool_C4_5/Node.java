package Tool_C4_5;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/17.
 */
public class Node {

    //用attribute来保存节点的分裂属性
    //用childNodes来保存节点的子节点
    //用pathName来保存子节点子节点相对应的属性值，用该值来选择子节点
    private String attribute;
    private ArrayList<String> pathNames=new ArrayList<String>();
    private ArrayList<Node> childNodes=new ArrayList<Node>();
    private ArrayList<ArrayList<String>> nodeDatas;
    private String label;

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

    public ArrayList<String> getPathNames(){
        return this.pathNames;
    }

    public void setPathNames(ArrayList<String> pathName){
        this.pathNames= pathName;
    }

    public void setNodeDatas(ArrayList<ArrayList<String>> nodeDatas){
        this.nodeDatas=(ArrayList<ArrayList<String>>)nodeDatas.clone();
    }

    public ArrayList<ArrayList<String>> getNodeDatas(){
        return (ArrayList<ArrayList<String>>) this.nodeDatas.clone();
    }

    public void setLabel(String label){
        this.label=label;
    }

    public String getLabel(){
        return this.label;
    }

    public void addChildNode(Node node){
        this.childNodes.add(node);
    }

    public void addPathName(String pathName){
        pathNames.add(pathName);
    }
}
