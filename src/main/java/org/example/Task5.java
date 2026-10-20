package org.example;

import java.util.*;

public class Task5 {
    static class Edge {
        int u, v; long w;
        Edge(int u, int v, long w) { this.u = u; this.v = v; this.w = w; }
    }

    public static void solve(String[] input) {
        String[] first = input[0].trim().split("\\s+");
        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);
        int s = Integer.parseInt(first[2]);

        Edge[] edges = new Edge[m];
        List<List<Integer>> g = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) g.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            String[] p = input[1 + i].trim().split("\\s+");
            int u = Integer.parseInt(p[0]);
            int v = Integer.parseInt(p[1]);
            long w = Long.parseLong(p[2]);
            edges[i] = new Edge(u, v, w);
            g.get(u).add(v);
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[s] = 0;

        for (int it = 0; it < n - 1; it++) {
            boolean changed = false;
            for (Edge e : edges) {
                if (dist[e.u] != Long.MAX_VALUE && dist[e.u] + e.w < dist[e.v]) {
                    dist[e.v] = dist[e.u] + e.w;
                    changed = true;
                }
            }
            if (!changed) break;
        }

        // вершины, где расстояние ещё улучшается → источник «-∞»
        boolean[] negInf = new boolean[n + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        for (Edge e : edges) {
            if (dist[e.u] != Long.MAX_VALUE && dist[e.u] + e.w < dist[e.v]) {
                if (!negInf[e.v]) {
                    negInf[e.v] = true;
                    queue.add(e.v);
                }
            }
        }
        // распространяем по достижимым
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : g.get(u)) {
                if (!negInf[v]) {
                    negInf[v] = true;
                    queue.add(v);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Long.MAX_VALUE && !negInf[i]) sb.append('*');
            else if (negInf[i]) sb.append('-');
            else sb.append(dist[i]);
            sb.append('\n');
        }
        System.out.print(sb);
    }
}