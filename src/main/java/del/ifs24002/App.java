package del.ifs24002;

import java.util.*;

public class App {

    static final int INF = Integer.MAX_VALUE;

    // Dijkstra: kembalikan jarak terpendek dari src ke dst
    // adjList: adjList.get(u) = list of int[]{v, weight}
    public static int dijkstra(List<List<int[]>> adjList, int src, int dst, int n) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        // PriorityQueue: {jarak, node}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, src});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0], u = cur[1];

            if (d > dist[u]) continue; // sudah diproses dengan jarak lebih kecil

            for (int[] edge : adjList.get(u)) {
                int v = edge[0], w = edge[1];
                if (dist[u] != INF && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }

        return dist[dst] == INF ? -1 : dist[dst];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); // jumlah node
        int m = scanner.nextInt(); // jumlah edge
        int p = scanner.nextInt(); // ukuran hash table (bilangan prima)

        // Build adjacency list (undirected)
        List<List<int[]>> adjList = new ArrayList<>();
        for (int i = 0; i <= n; i++) adjList.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            adjList.get(u).add(new int[]{v, w});
            adjList.get(v).add(new int[]{u, w}); // undirected
        }

        int q = scanner.nextInt(); // jumlah query

        // Inisialisasi Hash Table dengan chaining
        Program.HashTable hashTable = new Program.HashTable(p);

        for (int i = 0; i < q; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();

            int shortestDist = dijkstra(adjList, a, b, n);

            if (shortestDist == -1) {
                // Tidak ada jalur, skip
                continue;
            }

            // Masukkan ke hash table
            hashTable.insert(shortestDist);
        }

        // Ambil semua (nilai, frekuensi) dari hash table
        List<int[]> entries = hashTable.getAllEntries();

        // Bangun BST berdasarkan frekuensi
        Program.BST bst = new Program.BST();
        for (int[] entry : entries) {
            int nilai = entry[0];
            int frek  = entry[1];
            bst.insert(nilai, frek);
        }

        // In-order traversal dan cetak hasilnya
        List<String> result = new ArrayList<>();
        bst.inOrder(bst.root, result);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(result.get(i));
        }
        System.out.println(sb.toString());

        scanner.close();
    }
}
