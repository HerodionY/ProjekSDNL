/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MST;

/**
 *
 * @author ACER
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    private int maxVerts;
    private int nVerts;
    private Vertex[] vertexList;
    private int[][] adjMatrix;

    public Graph() {
        this.maxVerts = 30;
        this.nVerts = 0;
        vertexList = new Vertex[maxVerts];
        adjMatrix = new int[maxVerts][maxVerts];
        for (int i = 0; i < maxVerts; i++) {
            for (int j = 0; j < maxVerts; j++) {
                adjMatrix[i][j] = 0;
            }
        }
    }
    
    public Graph(int maxVerts) {
        this.maxVerts = maxVerts;
        this.nVerts = 0;
        vertexList = new Vertex[maxVerts];
        adjMatrix = new int[maxVerts][maxVerts];
        for (int i = 0; i < maxVerts; i++) {
            for (int j = 0; j < maxVerts; j++) {
                adjMatrix[i][j] = 0;
            }
        }
    }

    public void addVertex(String label) {
        vertexList[nVerts++] = new Vertex(label);
    }

    public void addEdge(int start, int end,int weight) {
        adjMatrix[start][end] = weight;
        adjMatrix[end][start] = weight;
    }
    
    public void addArrowedEdge(int start, int end) {
        adjMatrix[start][end] = 1; 
    }
    
    public void addArrowedEdge(int start, int end, int weight) {
        adjMatrix[start][end] = weight; 
    }


    public String displayVertex(int v) {
        return vertexList[v].getLabel();
    }
   public boolean containsVertex(String vertexLabel) {
    for (int i = 0; i < nVerts; i++) {
        if (vertexList[i] != null && vertexList[i].getLabel().equals(vertexLabel)) {
            return true; // Jika vertex ditemukan dalam daftar vertex
        }
    }
    return false; // Jika tidak ditemukan
}


    public void tampilMatrik() {
        System.out.print(" ");
        for (int i = 0; i < vertexList.length; i++) {
            if (vertexList[i] != null) {
                System.out.printf("%-3s", vertexList[i].getLabel()+" ");
            }
        }
        System.out.println("");
        for (int i = 0; i < nVerts; i++) {
            System.out.printf("%-3s",vertexList[i].getLabel()+" ");
            for (int j = 0; j < nVerts; j++) {
                System.out.printf("%-3s",adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getnVerts() {
        return nVerts;
    }
    
    public void bfs (){
        int kunjungi = 0;
        Queue<Integer> queue = new LinkedList<>();
        
        queue.add(kunjungi);
        while (!queue.isEmpty()) {            
            int bantu = queue.poll();
            if (!vertexList[bantu].isVisited()) {
                System.out.print(vertexList[bantu].getLabel()+" ");
                vertexList[bantu].setVisited(true);
            }
            for (int i = 0; i < nVerts; i++) {
                if (adjMatrix[bantu][i] >= 1) {
                    if (!vertexList[i].isVisited()) {
                        queue.add(i);
                    }
                }
            }
        }
        for (int i = 0; i < nVerts; i++) {
            vertexList[i].setVisited(false);
        }
    }
    
    public void dfs(){
        int kunjungi = 0;
        int bantu;
        
        Stack<Integer> stack = new Stack<>();
        stack.push(kunjungi);
        
        while (!stack.isEmpty()) {            
            bantu = stack.pop();
            if (!vertexList[bantu].isVisited()) {
                System.out.print(vertexList[bantu].getLabel()+" ");
                vertexList[bantu].setVisited(true);
            }
            for (int i = nVerts; i >= 0; i--) {
                if (adjMatrix[bantu][i] == 1) {
                    if (!vertexList[i].isVisited()) {
                        stack.push(i);
                    }
                }
        }
        }
        for (int i = 0; i < nVerts; i++) {
            vertexList[i].setVisited(false);
        }
    }
    
    public void topologi() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[maxVerts];

        topologi(0, visited, stack);

        System.out.println("Topological Sort:");
        while (!stack.isEmpty()) {
            System.out.print(vertexList[stack.pop()].getLabel() + " ");
        }
    }

    private void topologi(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;

        for (int i = 0; i < nVerts; i++) {
            if (adjMatrix[v][i] == 1 && !visited[i]) {
                topologi(i, visited, stack);
            }
        }

        stack.push(v);
    }
    
    public void shortestPath(int startVertex) {
        // Array untuk menyimpan jarak terpendek dari startVertex ke setiap vertex
        int[] distance = new int[maxVerts];
        Arrays.fill(distance, Integer.MAX_VALUE); // Mengisi awal dengan nilai tak terhingga
        distance[startVertex] = 0;

        // Array untuk menyimpan informasi apakah vertex sudah dikunjungi atau belum
        boolean[] visited = new boolean[maxVerts];

        // Proses Dijkstra
        for (int count = 0; count < nVerts; count++) {
            int u = minDistance(distance, visited);
            visited[u] = true;

            for (int v = 0; v < nVerts; v++) {
                if (!visited[v] && adjMatrix[u][v] != 0 && distance[u] != Integer.MAX_VALUE
                        && distance[u] + adjMatrix[u][v] < distance[v]) {
                    distance[v] = distance[u] + adjMatrix[u][v];
                }
            }
        }

        // Menampilkan hasil shortest path
        System.out.println("Shortest path dari vertex " + vertexList[startVertex].getLabel() + ":");
        for (int i = 0; i < nVerts; i++) {
            System.out.println(
                    "To " + vertexList[i].getLabel() + ": " + (distance[i] == Integer.MAX_VALUE ? "I N F I N I T Y" : distance[i]));
        }
    }

    private int minDistance(int[] distance, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        
        for (int v = 0; v < nVerts; v++) {
            if (!visited[v] && distance[v] <= min) {
                min = distance[v];
                minIndex = v;
            }
        }
        
        return minIndex;
    }
     public void kruskalMST() {
        Edge[] result = new Edge[nVerts - 1];
        int index = 0;

        // Mengurutkan semua edge berdasarkan bobotnya
        Edge[] edges = getAllEdges();

        Arrays.sort(edges);

        // Membuat subset untuk setiap vertex
        Subset[] subsets = new Subset[nVerts];
        for (int i = 0; i < nVerts; i++) {
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        int i = 0;
        while (index < nVerts - 1) {
            Edge nextEdge = edges[i++];

            int x = find(subsets, nextEdge.source);
            int y = find(subsets, nextEdge.destination);

            if (x != y) {
                result[index++] = nextEdge;
                union(subsets, x, y);
            }
        }

        System.out.println("Edges in MST by Kruskal's Algorithm:");
        for (i = 0; i < index; i++) {
            System.out.println(
                    vertexList[result[i].source].getLabel() + " - " + vertexList[result[i].destination].getLabel() +
                            "  Weight: " + result[i].weight);
        }
    }

    // Method untuk mencari semua edge dalam graf
    private Edge[] getAllEdges() {
        Edge[] edges = new Edge[nVerts * nVerts];
        int edgeCount = 0;

        for (int i = 0; i < nVerts; i++) {
            for (int j = 0; j < nVerts; j++) {
                if (adjMatrix[i][j] != 0) {
                    edges[edgeCount++] = new Edge(i, j, adjMatrix[i][j]);
                }
            }
        }

        Edge[] result = new Edge[edgeCount];
        System.arraycopy(edges, 0, result, 0, edgeCount);

        return result;
    }

    // Method untuk mencari representatif dari suatu set
    private int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    // Method untuk menggabungkan dua set
    private void union(Subset[] subsets, int x, int y) {
        int xRoot = find(subsets, x);
        int yRoot = find(subsets, y);

        if (subsets[xRoot].rank < subsets[yRoot].rank) {
            subsets[xRoot].parent = yRoot;
        } else if (subsets[xRoot].rank > subsets[yRoot].rank) {
            subsets[yRoot].parent = xRoot;
        } else {
            subsets[yRoot].parent = xRoot;
            subsets[xRoot].rank++;
        }
    }

    // Kelas untuk merepresentasikan edge
    class Edge implements Comparable<Edge> {
        int source, destination, weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    }

    // Kelas untuk merepresentasikan subset dari himpunan vertex
    class Subset {
        int parent, rank;
    }
     public void shortestPathBetweenVertices(int startVertex, int endVertex) {
        // Array untuk menyimpan jarak terpendek dari startVertex ke setiap vertex
        int[] distance = new int[maxVerts];
        Arrays.fill(distance, Integer.MAX_VALUE); // Mengisi awal dengan nilai tak terhingga
        distance[startVertex] = 0;

        // Array untuk menyimpan informasi apakah vertex sudah dikunjungi atau belum
        boolean[] visited = new boolean[maxVerts];

        // Proses Dijkstra
        for (int count = 0; count < nVerts; count++) {
            int u = minDistance(distance, visited);
            visited[u] = true;

            for (int v = 0; v < nVerts; v++) {
                if (!visited[v] && adjMatrix[u][v] != 0 && distance[u] != Integer.MAX_VALUE
                        && distance[u] + adjMatrix[u][v] < distance[v]) {
                    distance[v] = distance[u] + adjMatrix[u][v];
                }
            }
        }

        // Menampilkan hasil shortest path antara kedua vertex
        System.out.println("Shortest path dari " + vertexList[startVertex].getLabel() +
                " ke " + vertexList[endVertex].getLabel() + " adalah: " +
                (distance[endVertex] == Integer.MAX_VALUE ? "Tidak terhubung" : distance[endVertex]));
    }
     public void shortestPathByVertexNames(String startVertexLabel, String endVertexLabel) {
        int startIndex = -1;
        int endIndex = -1;

        // Mencari indeks dari startVertexLabel dan endVertexLabel
        for (int i = 0; i < nVerts; i++) {
            if (vertexList[i].getLabel().equals(startVertexLabel)) {
                startIndex = i;
            }
            if (vertexList[i].getLabel().equals(endVertexLabel)) {
                endIndex = i;
            }
            if (startIndex != -1 && endIndex != -1) {
                break;
            }
        }

        // Memastikan kedua vertex ditemukan sebelum memanggil shortestPathBetweenVertices
        if (startIndex != -1 && endIndex != -1) {
            shortestPathBetweenVertices(startIndex, endIndex);
            shortestPathBetweenVertices1(startIndex, endIndex);
        } else {
            System.out.println("Vertex tidak ditemukan.");
        }
    }
     
    
private Edge[] generateEdges() {
    Edge[] edges = new Edge[nVerts * nVerts]; // Anda bisa menyesuaikan dengan jumlah maksimal edge yang mungkin
    int edgeCount = 0;
    for (int i = 0; i < nVerts; i++) {
        for (int j = 0; j < nVerts; j++) {
            if (adjMatrix[i][j] != 0) {
                edges[edgeCount++] = new Edge(i, j, adjMatrix[i][j]);
            }
        }
    }
    return Arrays.copyOf(edges, edgeCount);
}
class UnionFind {
    int[] parent;
    int[] rank;

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];

        // Set semua parent awal ke diri sendiri dan rank ke 0
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Method untuk mencari representatif dari suatu set
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Method untuk menggabungkan dua set
    public void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);

        if (xRoot == yRoot) {
            return;
        }

        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot;
        } else if (rank[xRoot] > rank[yRoot]) {
            parent[yRoot] = xRoot;
        } else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }

    // Method untuk mengecek apakah dua elemen berada dalam set yang sama
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}



public void kruskalMST(int startVertex, int endVertex) {
    // Generate edges from the graph
    Edge[] edges = generateEdges();

    // Sort edges based on their weights
    Arrays.sort(edges);

    // Create a Union-Find data structure to track sets
    UnionFind uf = new UnionFind(nVerts);

    // To store the resulting MST
    List<Edge> mst = new ArrayList<>();

    // Traverse through the sorted edges
    for (Edge edge : edges) {
        int source = edge.source;
        int destination = edge.destination;

        // Check if including the edge creates a cycle or not
        if (!uf.connected(source, destination)) {
            mst.add(edge); // Add edge to the MST
            uf.union(source, destination); // Merge sets of source and destination
        }
    }

    // Print the Minimum Spanning Tree
    System.out.println("Minimum Spanning Tree:");
    for (Edge edge : mst) {
        System.out.println(
                vertexList[edge.source].getLabel() + " - " +
                vertexList[edge.destination].getLabel() + ": " +
                edge.weight);
    }
}

public void kruskalMSTByVertexNames(String startVertexLabel, String endVertexLabel) {
    int startIndex = -1;
    int endIndex = -1;

    // Mencari indeks dari startVertexLabel dan endVertexLabel
    for (int i = 0; i < nVerts; i++) {
        if (vertexList[i].getLabel().equals(startVertexLabel)) {
            startIndex = i;
        }
        if (vertexList[i].getLabel().equals(endVertexLabel)) {
            endIndex = i;
        }
        if (startIndex != -1 && endIndex != -1) {
            break;
        }
    }

    // Memastikan kedua vertex ditemukan sebelum memanggil kruskalMST
    if (startIndex != -1 && endIndex != -1) {
        kruskalMST(startIndex, endIndex);
    } else {
        System.out.println("Vertex tidak ditemukan.");
    }
}
public void shortestPathKruskal(String startVertexLabel, String endVertexLabel) {
    int startIndex = -1;
    int endIndex = -1;

    // Mencari indeks dari startVertexLabel dan endVertexLabel
    for (int i = 0; i < nVerts; i++) {
        if (vertexList[i].getLabel().equals(startVertexLabel)) {
            startIndex = i;
        }
        if (vertexList[i].getLabel().equals(endVertexLabel)) {
            endIndex = i;
        }
        if (startIndex != -1 && endIndex != -1) {
            break;
        }
    }

    // Memastikan kedua vertex ditemukan sebelum memanggil kruskalMST
    if (startIndex != -1 && endIndex != -1) {
        List<Edge> mst = kruskalMST1(startIndex, endIndex);
        if (mst != null) {
            // Menampilkan jalur terpendek dari startVertex ke endVertex
            System.out.println("Shortest path from " + startVertexLabel + " to " + endVertexLabel + ":");
            for (Edge edge : mst) {
                System.out.println(
                        vertexList[edge.source].getLabel() + " -> " +
                        vertexList[edge.destination].getLabel() + ": " +
                        edge.weight);
            }
        } else {
            System.out.println("No path found between " + startVertexLabel + " and " + endVertexLabel);
        }
    } else {
        System.out.println("Vertex tidak ditemukan.");
    }
}

public List<Edge> kruskalMST1(int startVertex, int endVertex) {
    Edge[] edges = generateEdges();
    Arrays.sort(edges);

    UnionFind uf = new UnionFind(nVerts);
    List<Edge> mst = new ArrayList<>();

    for (Edge edge : edges) {
        int source = edge.source;
        int destination = edge.destination;

        if (!uf.connected(source, destination)) {
            mst.add(edge);
            uf.union(source, destination);
        }
    }

    return mst;
}

public void shortestPathBetweenVertices1(int startVertex, int endVertex) {
    int[] distance = new int[maxVerts];
    int[] prevVertex = new int[maxVerts]; // Untuk menyimpan vertex sebelumnya dalam jalur terpendek

    Arrays.fill(distance, Integer.MAX_VALUE);
    distance[startVertex] = 0;

    boolean[] visited = new boolean[maxVerts];

    for (int count = 0; count < nVerts; count++) {
        int u = minDistance(distance, visited);
        visited[u] = true;

        for (int v = 0; v < nVerts; v++) {
            if (!visited[v] && adjMatrix[u][v] != 0 && distance[u] != Integer.MAX_VALUE
                    && distance[u] + adjMatrix[u][v] < distance[v]) {
                distance[v] = distance[u] + adjMatrix[u][v];
                prevVertex[v] = u; // Simpan vertex sebelumnya
            }
        }
    }

    // Cetak jalur yang dilalui
    printPath(startVertex, endVertex, prevVertex);
}

private void printPath(int startVertex, int endVertex, int[] prevVertex) {
    LinkedList<Integer> path = new LinkedList<>();
    int currentVertex = endVertex;

    // Bangun jalur dari belakang (dari endVertex ke startVertex)
    while (currentVertex != startVertex) {
        path.addFirst(currentVertex);
        currentVertex = prevVertex[currentVertex];
    }
    path.addFirst(startVertex);

    // Cetak jalur yang dilalui
    System.out.print("Shortest path from " + vertexList[startVertex].getLabel() + " to " +
            vertexList[endVertex].getLabel() + ": ");
    for (int vertex : path) {
        System.out.print(vertexList[vertex].getLabel() + " ");
    }
    System.out.println();
}

}

