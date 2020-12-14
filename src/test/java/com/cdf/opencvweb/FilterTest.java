package com.cdf.opencvweb;

import com.cdf.opencvweb.opencv.Filter2d;
import org.opencv.core.Core;

public class FilterTest {

    /* default value */
    private static String fileName = "img-02.jpg";
    private static double kernelSize = 5;

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        new Filter2d().NormalizedBox(fileName, kernelSize);
        new Filter2d().GaussianFilter(fileName, kernelSize);
    }
}
