/*
 * Copyright (c) 2021 Silverwolfg11
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.silverwolfg11.maptowny.util;

import me.silverwolfg11.maptowny.objects.StaticTB;
import me.silverwolfg11.maptowny.objects.TBCluster;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class NegativeSpaceFinder {

    /**
     * Find a list of static TBs that represent negative space within the cluster.
     *
     * @deprecated Use {@link PolygonUtil#getPolyInfoFromCluster(TBCluster, int, boolean)} instead.
     */
    @NotNull
    @Deprecated
    public static List<StaticTB> findNegativeSpace(TBCluster cluster) {
        // There can be no negative space if a cluster has less than 8 townblocks
        if (cluster.size() < 8)
            return Collections.emptyList();

        PolygonUtil.PolyFormResult result = PolygonUtil.getPolyInfoFromCluster(cluster, 1, true);
        List<TBCluster> negativeSpaceClusters = result.getNegativeSpaceClusters();

        List<StaticTB> negativeSpaceBlocks = new ArrayList<>();
        for (TBCluster negativeSpaceCluster : negativeSpaceClusters) {
            negativeSpaceBlocks.addAll(negativeSpaceCluster.getBlocks());
        }

        return negativeSpaceBlocks;
    }

}
