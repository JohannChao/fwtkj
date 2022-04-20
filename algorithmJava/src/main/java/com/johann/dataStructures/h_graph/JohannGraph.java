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

    /**
     * 打印当前顶点信息
     * @param index
     */
    public void showVertex(int index){
        System.out.println(vertexArray[index].data);
    }

    /**
     * 搜索完毕，重置各个顶点的“访问情况”
     */
    public void resetVertexVisited(){
        for (int i=0;i < elementCount;i++){
            vertexArray[i].setVisited(false);
        }
    }

    /**
     * 深度优先搜索
     * 规则1：如果可能，访问一个邻接的未访问顶点，标记它，并将它放入栈中。
     * 规则2：当不能执行规则 1 时，如果栈不为空，就从栈中弹出一个顶点。
     * 规则3：如果栈为空，不能执行规则 1 和规则 2 时，就完成了整个搜索过程。
     */
    public void depthFirstSearch(){
        //先访问第一个顶点，将该顶点的“访问情况”更新为 true
        vertexArray[0].setVisited(true);
        //展示当前顶点
        showVertex(0);
        //int currentIndex = 0;
        //将当前顶节点压入栈内
        depthSearchStack.push(0);

        //如果深度搜索栈不为空，则循环处理
        while(!depthSearchStack.isEmpty()){
            // 使用 depthSearchStack.peak() 减少一个变量 currentIndex
            //int adjacentIndex = findNonVisiteAdjacentVertex(currentIndex);
            //获取栈顶顶点，还没有被访问过的邻近顶点
            int adjacentIndex = findNonVisiteAdjacentVertex(depthSearchStack.peak());
            //如果栈顶顶点有邻近顶点
            if (adjacentIndex != -1){
                //将这个邻近顶点的“访问情况”更新为 true
                vertexArray[adjacentIndex].setVisited(true);
                //展示这个邻近顶点
                showVertex(adjacentIndex);
                //将这个邻近顶点压入栈内
                depthSearchStack.push(adjacentIndex);
                //currentIndex = adjacentIndex;
            //如果栈顶顶点有邻近顶点，栈顶顶点出栈
            }else{
                depthSearchStack.pop();
                //currentIndex = depthSearchStack.peak();
            }
        }

        //搜索完毕，重置各个顶点的“访问情况”
        resetVertexVisited();
    }


    /**
     * 广度优先搜索
     * 规则1：访问下一个未访问的邻接点（如果存在），这个顶点必须是当前顶点的邻接点，标记它，并把它插入到队列中。
     * 规则2：如果已经没有未访问的邻接点而不能执行规则 1 时，那么从队列列头取出一个顶点（如果存在），并使其成为当前顶点。
     * 规则3：如果队列为空，不能执行规则 2，则搜索结束。
     */
    public void breadthFirstSearch(){
        vertexArray[0].setVisited(true);
        showVertex(0);
        //将当前顶点插入到队列中
        breadthSearchQueue.insert(0);

        //如果队列不为空，循环处理
        while(!breadthSearchQueue.isEmpty()){
            //循环检索队首顶点，获取该顶点还未被访问的邻近顶点，直至没有“还未被访问的邻近顶点”
            int adjacentIndex = findNonVisiteAdjacentVertex((int)breadthSearchQueue.peakFront());
            //如果队首顶点的邻近顶点不为空
            if (adjacentIndex != -1){
                //将邻近顶点设置为“已访问”，并插入到队尾等待后续检索
                vertexArray[adjacentIndex].setVisited(true);
                showVertex(adjacentIndex);
                breadthSearchQueue.insert(adjacentIndex);
            //如果队首顶点的邻近顶点检索完毕，将队首顶点移除队列
            }else{
                breadthSearchQueue.remove();
            }
        }

        //搜索完毕，重置各个顶点的“访问情况”
        resetVertexVisited();
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
        //graph.addEdge(1,7);//BH
        //graph.addEdge(2,5);//CF
        graph.addEdge(3,4);//DE
        graph.addEdge(3,6);//DG
        graph.addEdge(4,6);//EG
        graph.addEdge(4,7);//EG
        graph.addEdge(5,7);//FH
        graph.addEdge(6,7);//GH

//        int adjacentIndex = graph.findNonVisiteAdjacentVertex(0);
//        while(adjacentIndex != -1){
//            System.out.println(graph.vertexArray[adjacentIndex].data);
//            graph.vertexArray[adjacentIndex].setVisited(true);
//            adjacentIndex = graph.findNonVisiteAdjacentVertex(0);
//        }

        System.out.println("深度优先搜索结果：");
        graph.depthFirstSearch();

        System.out.println("广度优先搜索结果：");
        graph.breadthFirstSearch();
    }


}
