/**
 * Graph.java
 * org.zxc.algorithm.graph
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.algorithm.graph;

/**
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年9月14日 	 
 */
public class Graph {

	private int V;

	private int E;

	public Graph(int v) {

	}

	/**顶点数*/
	public int V() {
		return 0;
	}

	/**边数*/
	public int E() {
		return 0;
	}

	/**添加一条边*/
	public void addEdge(int v, int w) {
	}

	/**和v相邻的所有顶点*/
	public Iterable<Integer> adj(int v) {

		return null;
	}

	/**
	 * 计算顶点的度数
	 * <p>
	 *
	 * @param g 图
	 * @param v 顶点v
	 */
	public int degree(Graph g, int v) {
		int degree = 0;
		for (int w : g.adj(v)) {
			degree++;
		}
		return degree;
	}

	/**计算图的最大深度*/
	public int maxDegree(Graph g) {
		int max = 0;
		for (int v = 0; v < g.V(); v++) {
			int vDegree = degree(g, v);
			if (vDegree > max) {
				max = vDegree;
			}
		}
		return max;
	}

	/**
	 * 计算所有顶点的平均度数
	 * <p>
	 * 2倍的边数除以顶点数
	 */
	public static double avgDegree(Graph G) {
		return 2 * G.E() / G.V();
	}

	/**计算自环的个数*/
	public static int numberOfSelfLoops(Graph G) {
		int count = 0;
		for (int v = 0; v < G.V(); v++)
			for (int w : G.adj(v))
				if (v == w)
					count++;
		return count / 2; //每条边都被记过两次
	}

	/**
	 * 图的邻接表的字符串表示（Graph的实例方法）
	 */
	@Override
	public String toString() {
		String s = V + " vertices, " + E + " edges\n";
		for (int v = 0; v < V; v++) {
			s += v + ": ";
			for (int w : this.adj(v))
				s += w + " ";
			s += "\n";
		}
		return s;
	}

}
