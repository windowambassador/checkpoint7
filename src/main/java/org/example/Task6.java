package org.example;

import java.util.*;

public class Task6 {
    static final long MOD = 1_000_000_007L;

    static class Edge {
        int u, v; long w;
        Edge(int u, int v, long w) { this.u = u; this.v = v; this.w = w; }
    }

    public static void solve(String[] input) {
        String[] first = input[0].trim().split("\\s+");
        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);
        int s = Integer.parseInt(first[2]);
        int t = Integer.parseInt(first[3]);
        int K = Integer.parseInt(first[4]);

        List<Edge> edges = new ArrayList<>(m);
        List<List<Edge>> g = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) g.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            String[] p = input[1 + i].trim().split("\\s+");
            int u = Integer.parseInt(p[0]);
            int v = Integer.parseInt(p[1]);
            long w = Long.parseLong(p[2]);
            Edge e = new Edge(u, v, w);
            edges.add(e);
            g.get(u).add(e);
        }

        final long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[K + 1][n + 1];
        long[][] cnt = new long[K + 1][n + 1];
        for (long[] row : dp) Arrays.fill(row, INF);

        dp[0][s] = 0;
        cnt[0][s] = 1;

        for (int k = 0; k < K; k++) {
            for (int u = 1; u <= n; u++) {
                if (dp[k][u] == INF) continue;
                for (Edge e : g.get(u)) {
                    long nd = dp[k][u] + e.w;
                    if (nd < dp[k + 1][e.v]) {
                        dp[k + 1][e.v] = nd;
                        cnt[k + 1][e.v] = cnt[k][u];
                    } else if (nd == dp[k + 1][e.v]) {
                        cnt[k + 1][e.v] = (cnt[k + 1][e.v] + cnt[k][u]) % MOD;
                    }
                }
            }
        }

        long bestCost = INF;
        long ways = 0;
        for (int k = 0; k <= K; k++) {
            if (dp[k][t] < bestCost) {
                bestCost = dp[k][t];
                ways = cnt[k][t];
            } else if (dp[k][t] == bestCost) {
                ways = (ways + cnt[k][t]) % MOD;
            }
        }

        if (bestCost == INF) System.out.println("IMPOSSIBLE");
        else System.out.println(bestCost + " " + ways);
    }
}