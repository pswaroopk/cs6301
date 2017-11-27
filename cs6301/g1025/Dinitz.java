/**
 * @author Antriksh, Gunjan, Saikumar, Swaroop
 * Version 1: Implementing
 */

package cs6301.g1025;

import cs6301.g1025.Graph.Edge;
import cs6301.g1025.Graph.Vertex;
import cs6301.g1025.XGraph.XEdge;
import cs6301.g1025.XGraph.XVertex;

import java.util.Iterator;

import static cs6301.g1025.Flow.xgraph;
import static java.lang.Integer.min;

public class Dinitz {
    public static final int INFINITY = Integer.MAX_VALUE;
    BFS bfshandle;
    Graph g;
    Vertex source;
    Vertex sink;

    Dinitz(Graph g, Graph.Vertex src, Graph.Vertex sink) {
        this.g = (XGraph) g;
        this.source = ((XGraph) this.g).getVertex(src);
        this.sink = ((XGraph) this.g).getVertex(sink);
        bfshandle = new BFS(g, (XVertex) source);
    }

    /**
     * Approach:
     * updating minflow for a path while enumerating augmenting paths-
     * (optimizing like reward problem  in lp4 without storing all paths) and
     * skipping over edges with the help of iterator
     *
     * @param s    Vertex
     * @param flow Min flow calculated
     * @param itr  Edge iterator
     * @return Max Flow through the path
     */
    int EnumBFSTree(Vertex s, int flow, Iterator<Edge> itr) {
        if (s.equals(sink)) {
            System.out.println("Flow: " + flow);
            return flow;
        }
        while (itr.hasNext()) {
            Edge e = itr.next();
            XEdge xe = (XEdge) e;
            if (bfshandle.distance(xe.otherEnd(s)) == bfshandle.distance(s) + 1) {
                if (e.toVertex().equals(s)) {
                    flow = min(flow, xgraph(g).flow(e));
                } else {
                    flow = min(flow, xgraph(g).capacity(e) - xgraph(g).flow(e));
                }

                int minedgeflow = EnumBFSTree(xe.otherEnd(s), flow, xe.otherEnd(s).iterator());

                if (minedgeflow > 0) {
                    if (e.fromVertex().equals(s)) {
                        xgraph(g).setFlow(e, xgraph(g).flow(e) + minedgeflow);
                    } else {
                        xgraph(g).setFlow(e, xgraph(g).flow(e) - minedgeflow);
                    }
                    return minedgeflow;
                }
            }
        }

        return -1; // meaning didnt find the augmenting path
    }

    /**
     * Caclulating maximum flow
     *
     * @return
     */
    int maxFlow() {

        int flow = 0;
        while (true) {
            XVertex xsource = (XVertex) source;
            bfshandle.bfs();
            if (!bfshandle.getVertex(sink).seen)
                break;
            while (true) {
                int minflow = EnumBFSTree(source, INFINITY, xsource.iterator());
                if (minflow == -1) {
                    break;
                } else {
                    flow = flow + minflow;
                }
            }
            bfshandle.reinitialize(xsource);
        }
        return flow;
    }

}
