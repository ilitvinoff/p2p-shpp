package com.shpp.p2p.cs.ilitvinov.assignment17.silhouettes.presilhouettes;

import com.shpp.p2p.cs.ilitvinov.assignment17.silhouettes.Testing;

public class PreSil implements Testing {

    public long[] proceed(String path) {

        long time1 = System.currentTimeMillis();
        QueueVersion.findSilhouette(BinaryImage.binaryImage(path));

        long time2 = System.currentTimeMillis();
        StackVersion.findSilhouette(BinaryImage.binaryImage(path));

        long time3 = System.currentTimeMillis();
        RecursiveVersion.findSilhouette(BinaryImage.binaryImage(path));
        long time4 = System.currentTimeMillis();

        return new long[]{time2-time1,time3-time2,time4-time3};
    }
}
