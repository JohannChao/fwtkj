package com.johann.dataStructures.h_graph;

import com.johann.dataStructures.b_stack.JohannStack;
import com.johann.dataStructures.c_queue.JohannQueue;
import com.johann.dataStructures.d_linkedList.DoublelyLinkedList;

/**
 * @ClassName: JohannGraph
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class JohannGraph {

    //顶点个数
    private int vertexSize;
    //当前有效的顶点个数，插入顶点时，用于判断索引位置
    private int elementCount;
    //顶点集合
    private Vertex[] vertexArray;
    //当前顶点对应的邻近顶点的集合
    private DoublelyLinkedList[] edgesArray;
    //深度优先搜索使用的栈
    private JohannStack depthSearchStack;
    //广度优先搜索使用的队列
    private JohannQueue breadthSearchQueue;



    public JohannGraph(){
        this(10);
    }

    public JohannGraph(int initialCapacity){
        this.elementCount = 0;
        this.vertexSize = initialCapacity;
        this.vertexArray = new Vertex[initialCapacity];
        this.edgesArray = new DoublelyLinkedList[initialCapacity];
        this.depthSearchStack = new JohannStack(initialCapacity);
        this.breadthSearchQueue = new JohannQueue(initialCapacity);
    }

    /**
     * 顶点类
     */
    private class Vertex{
        //顶点数据
        private Object data;
        //当前顶点是否被访问
        private boolean visited;

        public Vertex(){

        }

        public Vertex(Object data){
            this.data = data;
            this.visited = false;
        }

        public void setVisited(boolean flag){
            this.visited = flag;
        }
        public boolean getVisited(){
            return this.visited;
        }
    }

    /**
     * 新增顶点
     * @param o
     */
    public void addVertex(Object o){
        vertexArray[elementCount++] = new Vertex(o);
    }

    /**
     * 新增边
     * @param leftIndex 新增边对应的左顶点索引
     * @param rightIndex 新增边对应的右顶点索引
     */
    public void addEdge(int leftIndex,int rightIndex){
        DoublelyLinkedList leftEdge = edgesArray[leftIndex];
        if (leftEdge==null){
            leftEdge = new DoublelyLinkedList();
        }
        leftEdge.addHead(rightIndex);
        edgesArray[leftIndex] = leftEdge;

        DoublelyLinkedList rightEdge = edgesArray[rightIndex];
        if (rightEdge==null){
            rightEdge = new DoublelyLinkedList();
        }
        rightEdge.addHead(leftIndex);
        edgesArray[rightIndex] = rightEdge;
    }

    /**
     * 获取当前顶点未被访问的邻近顶点的索引。
     * 注意，这个方法只会返回符合查询条件的第一条结果。如果没有满足条件的顶点，返回 -1
     * @param vertexIndex 当前顶点的索引号
     * @return
     */
    public int findNonVisiteAdjacentVertex(int vertexIndex){
        DoublelyLinkedList edges  = edgesArray[vertexIndex];
        if (edges!=null){
            //遍历当前顶点的邻近顶点链表
            for (int i=0;i<edges.size();i++){
                //邻近顶点未被访问
                if (!vertexArray[(int)edges.get(i)].getVisited()){
                    return (int)edges.get(i);
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        JohannGraph graph = new JohannGraph(8);
        graph.addVertex('A');//0
        graph.addVertex('B');//1
        graph.addVertex('C');//2
        graph.addVertex('D');//3
        graph.addVertex('E');//4
        graph.addVertex('F');//5
        graph.addVertex('G');//6
        graph.addVertex('H');//7

        graph.addEdge(0,1);//AB
        graph.addEdge(0,3);//AD
        graph.addEdge(0,4);//AE
        graph.addEdge(1,2);//BC
        graph.addEdge(1,7);//BH
        graph.addEdge(2,5);//CF
        graph.addEdge(3,4);//DE
        graph.addEdge(3,6);//DG
        graph.addEdge(4,6);//EG
        graph.addEdge(4,7);//EG
        graph.addEdge(5,7);//FH
        graph.addEdge(6,7);//GH

        int adjacentIndex = graph.findNonVisiteAdjacentVertex(0);
        while(adjacentIndex != -1){
            System.out.println(graph.vertexArray[adjacentIndex].data);
            graph.vertexArray[adjacentIndex].setVisited(true);
            adjacentIndex = graph.findNonVisiteAdjacentVertex(0);
        }

    }


}
