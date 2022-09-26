package com.sample.tracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointCheck {
    private static final List<Integer> listEye1 = Arrays.asList(94, 1, 53, 59, 67, 12);
    private static final List<Integer> listEye2 = Arrays.asList(27, 104, 85, 20, 47, 51);
    private static final List<Integer> listMouth = Arrays.asList(61, 40, 25, 42, 2, 63);

//    private static final List<Integer> listEye1 = Arrays.asList(1, 53, 59, 67, 12, 94);
//    private static final List<Integer> listEye2 = Arrays.asList(104, 85, 20, 47, 51, 27);
//    private static final List<Integer> listMouth = Arrays.asList(40, 25, 42, 2, 63, 61);

    public static boolean check(int d) {
        return listEye1.contains(d) || listEye2.contains(d) || listMouth.contains(d);
    }
}