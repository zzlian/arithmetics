package Tool_C4_5;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/22.
 */
public class PostPruning {

    public static Node postPruning(Node node,int sizeD){
        double errorT=0.0,errorN=0.0;

        errorT=getErrorT(node)+0.5;
        pruning(node,errorT,sizeD);

        return node;

    }

    public static double getErrorT(Node node){
        double error=0;

        if(node.getAttribute().equals("leafNode")){
            for(ArrayList<String> data:node.getNodeDatas()){
                if(!data.get(data.size()-1).equals(node.getLabel())){
                    error++;
                }
            }
            return error;
        }
        for(Node nod:node.getChildNodes()){
            error+=getErrorT(nod);
        }
        return error;
    }

    public static void pruning(Node node,double errorT,int sizeD){
        double errorN=0.0;

        if(node.getAttribute().equals("leafNode")){
            return;
        }

        errorN=getErrorN(node);

        if(errorT<(errorN+Math.sqrt(errorN*(sizeD-errorN)/sizeD))){
            String label=CreatDecisionTree_C4_5.maLabel(node.getNodeDatas());

            if(node.getNodeDatas().size()==sizeD){
                node.setLabel(label);
                return;
            }
            node.setAttribute("leafNode");
            node.setLabel(label);
            return;
        }

        for(Node nod:node.getChildNodes()){
            pruning(nod,errorT,sizeD);
        }
    }

    public static double getErrorN(Node node){
        double error=0;

        if(node.getAttribute().equals("leafNode")){
            for(ArrayList<String> data:node.getNodeDatas()){
                if(!data.get(data.size()-1).equals(node.getLabel())){
                    error++;
                }
            }
            return error+0.5;
        }
        for(Node nod:node.getChildNodes()){
            error+=getErrorT(nod);
        }
        return error;
    }

}
