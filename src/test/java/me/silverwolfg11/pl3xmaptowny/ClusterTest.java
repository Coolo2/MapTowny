package me.silverwolfg11.pl3xmaptowny;

import me.silverwolfg11.pl3xmaptowny.objects.StaticTB;
import me.silverwolfg11.pl3xmaptowny.objects.TBCluster;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static me.silverwolfg11.pl3xmaptowny.TestHelpers.clustersOf;
import static me.silverwolfg11.pl3xmaptowny.TestHelpers.list;
import static me.silverwolfg11.pl3xmaptowny.TestHelpers.tb;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Test that TBCluster forms proper clusters
public class ClusterTest {

    @Test
    @DisplayName("Cluster: Empty")
    void testClusterEmpty() {
        List<TBCluster> clusters = clustersOf(Collections.emptyList());
        assertTrue(clusters.isEmpty());
    }

    @Test
    @DisplayName("Cluster: Single Townblock")
    void testClusterSingleTownblock() {
        StaticTB tb = tb(0, 0);
        List<TBCluster> clusters = clustersOf(tb);

        assertEquals(clusters.size(), 1);

        TBCluster cluster = clusters.get(0);
        assertEquals(cluster.size(), 1);
        assertTrue(cluster.has(tb));
    }

    //  + + +
    //  +++++
    //    +
    //    +
    @Test
    @DisplayName("Cluster: Trident Shape")
    void testClusterTrident() {
        Collection<StaticTB> tridentTBs = list(tb(-2, 1), tb(0, 1), tb(2, 1),
                tb(-2, 0), tb(-1, 0), tb(0, 0), tb(1, 0), tb(2, 0),
                tb(0, -1), tb(0, -2));

        List<TBCluster> clusters = clustersOf(tridentTBs);
        assertEquals(clusters.size(), 1);

        TBCluster cluster = clusters.get(0);
        assertEquals(cluster.size(), tridentTBs.size());
        tridentTBs.forEach(tb -> assertTrue(cluster.has(tb)));
    }

    // +  +
    @Test
    @DisplayName("Cluster: Two Individual Townblocks")
    void testClusterTwoSingles() {
        StaticTB tb1 = tb(-1, 0);
        StaticTB tb2 = tb(1, 0);

        List<TBCluster> clusters = clustersOf(tb1, tb2);
        assertEquals(clusters.size(), 2);

        TBCluster cluster1 = clusters.get(0);
        assertEquals(cluster1.size(), 1);

        TBCluster cluster2 = clusters.get(1);
        assertEquals(cluster1.size(), 1);

        // We don't actually know which cluster has which block
        // But we do know each cluster only has one townblock so

        assertTrue(cluster1.has(tb1) || cluster2.has(tb1));
        assertTrue(cluster1.has(tb2) || cluster2.has(tb2));
    }

    // +++ +++
    @Test
    @DisplayName("Cluster: Two Townblock Rows")
    void testClusterTwoRows() {
        List<StaticTB> tbs1 = list(tb(-3,0), tb(-2, 0), tb(-1, 0));
        List<StaticTB> tbs2 = list(tb(3, 0), tb(2, 0), tb(1, 0));
        Collection<StaticTB> combined = new ArrayList<>(tbs1);
        combined.addAll(tbs2);

        List<TBCluster> clusters = clustersOf(combined);
        assertEquals(clusters.size(), 2);

        assertEquals(clusters.get(0).size(), 3);
        assertEquals(clusters.get(1).size(), 3);

        TBCluster cluster1 = clusters.get(0).has(tbs1.get(0)) ? clusters.get(0) : clusters.get(1);
        TBCluster cluster2 = clusters.get(0) != cluster1 ? clusters.get(0) : clusters.get(1);

        tbs1.forEach(tb -> assertTrue(cluster1.has(tb)));
        tbs2.forEach(tb -> assertTrue(cluster2.has(tb)));
    }



}
