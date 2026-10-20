package org.example;

import java.util.*;

public class Task1 {
    static class Edge {
        int to; long w;
        Edge(int to, long w) { this.to = to; this.w = w; }
    }

    public static void solve(String[] input) {
        String[] first = input[0].trim().split("\\s+");
        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);
        int s = Integer.parseInt(first[2]);

        List<List<Edge>> g = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) g.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            String[] p = input[1 + i].trim().split("\\s+");
            int u = Integer.parseInt(p[0]);
            int v = Integer.parseInt(p[1]);
            long w = Long.parseLong(p[2]);
            g.get(u).add(new Edge(v, w));
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[s] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.add(new long[]{0, s});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0];
            int u = (int) cur[1];
            if (d > dist[u]) continue;
            for (Edge e : g.get(u)) {
                long nd = d + e.w;
                if (nd < dist[e.to]) {
                    dist[e.to] = nd;
                    pq.add(new long[]{nd, e.to});
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (i > 1) sb.append(' ');
            sb.append(dist[i] == Long.MAX_VALUE ? "INF" : dist[i]);
        }
        System.out.println(sb);
    }
}