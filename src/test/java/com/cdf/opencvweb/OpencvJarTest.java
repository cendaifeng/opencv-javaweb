package com.cdf.opencvweb;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class OpencvJarTest {
    public static void main( String[] args )
    {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        Mat mat = Mat.eye( 4, 4, CvType.CV_8UC1 );
        System.out.println( "mat = \n" + mat.dump() );
    }
}