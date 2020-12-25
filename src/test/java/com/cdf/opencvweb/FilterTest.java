package com.cdf.opencvweb;

import com.cdf.opencvweb.opencv.OpencvProcess;
import org.opencv.core.Core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FilterTest {

    /* default value */
    private static String fileName = "img-02.jpg";
    private static double kernelSize = 5;

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        new Filter2d().NormalizedBox(fileName, kernelSize);
        OpencvProcess.GaussianFilter(fileName, kernelSize);
        // 利用反射调用
        Class clazz = OpencvProcess.class;
        Method mtd = clazz.getMethod("GaussianFilter", String.class, double.class);
        mtd.invoke(null, fileName, kernelSize);
    }
}
