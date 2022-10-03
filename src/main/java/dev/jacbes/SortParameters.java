package dev.jacbes;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortParameters {

    BiFunction<ScannerAndVar, ScannerAndVar, Integer> compareFunction = SortParameters::compareInt;
    Function<ScannerAndVar, Boolean> hasNextPredicate = SortParameters::hasNextInt;
    Function<ScannerAndVar, Object> nextFunction = SortParameters::nextInt;
    BiFunction<List<ScannerAndVar>, SortParameters, ScannerAndVar> sortWay = SortParameters::apSort;

    public SortParameters(String[] args) {
        for (String arg : Arrays.stream(args).distinct().limit(2).collect(Collectors.toList())) {
            switch (arg) {
                case "-d":
                    setSortWayToDown();
                    break;
                case "-s":
                    setNextFunctionString();
                    setCompareFunctionString();
                    setCompareFunctionString();
                    setHasNextPredicateString();
                    break;
            }
        }
    }

    public void setSortWayToDown() {
        sortWay = SortParameters::downSort;
    }

    public void setCompareFunctionString() {
        compareFunction = SortParameters::compareString;
    }

    public void setHasNextPredicateString() {
        hasNextPredicate = SortParameters::hasNextString;
    }

    public void setNextFunctionString() {
        nextFunction = SortParameters::nextString;
    }

    public static int compareInt(ScannerAndVar int1, ScannerAndVar int2) {
        return Integer.compare((Integer) int1.var, (Integer) int2.var);
    }

    public static int compareString(ScannerAndVar str1, ScannerAndVar str2) {
        String cstr1 = (String) str1.var;
        String cstr2 = (String) str2.var;
        return cstr1.compareTo(cstr2);
    }

    public static boolean hasNextInt(ScannerAndVar sav) {
        while (sav.scanner.hasNext()) {
            if (sav.scanner.hasNextInt()) {
                return true;
            }
            sav.scanner.next();
        }

        return false;
    }

    public static boolean hasNextString(ScannerAndVar sav) {
        return sav.scanner.hasNext();
    }

    private static Integer nextInt(ScannerAndVar sav) {
        return sav.scanner.nextInt();
    }

    private static String nextString(ScannerAndVar sav) {
        return sav.scanner.next();
    }

    private static ScannerAndVar apSort(List<ScannerAndVar> savList, SortParameters sp) {
        return savList.stream().min((min, next) -> sp.compareFunction.apply(min, next)).orElse(null);
    }

    private static ScannerAndVar downSort(List<ScannerAndVar> savList, SortParameters sp) {
        return savList.stream().max((max, next) -> sp.compareFunction.apply(max, next)).orElse(null);
    }
}
