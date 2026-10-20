package org.example;

import java.util.*;

public class Task4 {
    static class Edge {
        int to; long w;
        Edge(int to, long w) { this.to = to; this.w = w; }
    }

    public static void solve(String[] input) {
        String[] first = input[0].trim().split("\\s+");
        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);
        int s = Integer.parseInt(first[2]);
        int t = Integer.parseInt(first[3]);

        List<List<Edge>> g = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) g.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            String[] p = input[1 + i].trim().split("\\s+");
            int u = Integer.parseInt(p[0]);
            int v = Integer.parseInt(p[1]);
            long w = Long.parseLong(p[2]);
            g.get(u).add(new Edge(v, w));
        }

        long[][] dist = new long[2][n + 1];
        for (long[] row : dist) Arrays.fill(row, Long.MAX_VALUE);
        dist[0][s] = 0;

        // pq: {dist, vertex, used}
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.add(new long[]{0, s, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0];
            int u = (int) cur[1];
            int used = (int) cur[2];
            if (d > dist[used][u]) continue;
            for (Edge e : g.get(u)) {
                // без купона
                if (d + e.w < dist[used][e.to]) {
                    dist[used][e.to] = d + e.w;
                    pq.add(new long[]{d + e.w, e.to, used});
                }
                // с купоном (только если ещё не использован)
                if (used == 0) {
                    long w2 = e.w / 2;
                    if (d + w2 < dist[1][e.to]) {
                        dist[1][e.to] = d + w2;
                        pq.add(new long[]{d + w2, e.to, 1});
                    }
                }
            }
        }

        long ans = Math.min(dist[0][t], dist[1][t]);
        System.out.println(ans == Long.MAX_VALUE ? -1 : ans);
    }
}