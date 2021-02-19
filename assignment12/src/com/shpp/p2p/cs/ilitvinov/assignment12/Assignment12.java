package com.shpp.p2p.cs.ilitvinov.assignment12;

public class Assignment12 {
    private static final String[] PATH_IMAGE = {
            "assets/10man.jpg", "assets/123.jpg", "assets/football.jpeg", "assets/128x128.jpg", "assets/result.jpg", "assets/siluets.png"};

    public static void main(String[] args) {
        String path = PATH_IMAGE[0];

        System.out.println("Queue version:\nsilhouettes: " + QueueVersion.findSilhouette(BinaryImage.binaryImage(path)));
        System.out.println("\nStack version:\nsilhouettes: " + StackVersion.findSilhouette(BinaryImage.binaryImage(path)));
        System.out.println("\nRecursive version:\nsilhouettes: " + RecursiveVersion.findSilhouette(BinaryImage.binaryImage(path)));
    }
}
