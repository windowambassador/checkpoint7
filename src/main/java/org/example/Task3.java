package org.example;

import java.util.*;

public class Task3 {
    static class Edge {
        int to; long w;
        Edge(int to, long w) { this.to = to; this.w = w; }
    }

    public static void solve(String[] input) {
        String[] first = input[0].trim().split("\\s+");
        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);
        int k = Integer.parseInt(first[2]);

        String[] srcStr = input[1].trim().split("\\s+");
        int[] sources = new int[k];
        for (int i = 0; i < k; i++) sources[i] = Integer.parseInt(srcStr[i]);

        List<List<Edge>> g = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) g.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            String[] p = input[2 + i].trim().split("\\s+");
            int u = Integer.parseInt(p[0]);
            int v = Integer.parseInt(p[1]);
            long w = Long.parseLong(p[2]);
            g.get(u).add(new Edge(v, w));
            g.get(v).add(new Edge(u, w));
        }

        long[] dist = new long[n + 1];
        int[] best = new int[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);

        // pq: {dist, vertex, sourceId}
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return Long.compare(a[0], b[0]);
            return Long.compare(a[2], b[2]); // меньший номер пункта
        });

        for (int s : sources) {
            if (dist[s] > 0 || (dist[s] == 0 && s < best[s])) {
                if (dist[s] != 0 || best[s] == 0) {
                    dist[s] = 0;
                    best[s] = s;
                    pq.add(new long[]{0, s, s});
                }
            }
        }
        // на случай дублирующихся источников — используем корректную инициализацию:
        for (int s : sources) {
            if (best[s] == 0 || s < best[s]) {
                dist[s] = 0;
                best[s] = s;
            }
        }
        // перестроим очередь чисто:
        pq.clear();
        for (int s : sources) pq.add(new long[]{0, s, s});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0];
            int u = (int) cur[1];
            int src = (int) cur[2];
            if (d > dist[u]) continue;
            if (d == dist[u] && src > best[u]) continue;
            for (Edge e : g.get(u)) {
                long nd = d + e.w;
                if (nd < dist[e.to] || (nd == dist[e.to] && src < best[e.to])) {
                    dist[e.to] = nd;
                    best[e.to] = src;
                    pq.add(new long[]{nd, e.to, src});
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Long.MAX_VALUE) sb.append("-1 -1\n");
            else sb.append(best[i]).append(' ').append(dist[i]).append('\n');
        }
        System.out.print(sb);
    }
}