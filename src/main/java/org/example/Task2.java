package org.example;

import java.util.*;

public class Task2 {
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
        for (int i = 0; i < m; i++) {
            String[] p = input[1 + i].trim().split("\\s+");
            edges[i] = new Edge(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Long.parseLong(p[2]));
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[s] = 0;

        // n-1 итераций (В гарантии нет отрицательных циклов, достижимых из s)
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

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (i > 1) sb.append(' ');
            sb.append(dist[i] == Long.MAX_VALUE ? "INF" : dist[i]);
        }
        System.out.println(sb);
    }
}