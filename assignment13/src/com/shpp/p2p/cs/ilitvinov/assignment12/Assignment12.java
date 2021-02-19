package com.shpp.p2p.cs.ilitvinov.assignment12;

import java.util.Objects;

public class Assignment12 {
    private static final String[] PATH_IMAGE = {
            "assets/10man.jpg", "assets/123.jpg", "assets/football.jpeg", "assets/128x128.jpg", "assets/result.jpg", "assets/siluets.png"};

    public static void main(String[] args) {
        String path = PATH_IMAGE[0];

        if (args.length==1){
            System.out.println("Queue version:\nsilhouettes: " + QueueVersion.findSilhouette(Objects.requireNonNull(BinaryImage.binaryImage(args[0]))));
        }else if (args.length>1){
            System.out.println("To many arguments");
        }else{
            System.out.println("Queue version:\nsilhouettes: " + QueueVersion.findSilhouette(Objects.requireNonNull(BinaryImage.binaryImage(path))));
        }
    }
}
