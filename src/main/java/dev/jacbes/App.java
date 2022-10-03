package dev.jacbes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {

        SortParameters sortParameters = new SortParameters(args);
        String outputFilename = Arrays.stream(args)
                .filter(s -> s.contains(".txt"))
                .findFirst()
                .orElse(null);
        if (outputFilename == null) {
            System.out.println("Not found .txt files");
            return;
        }

        List<ScannerAndVar> savList = Arrays.stream(args)
                .filter(s -> s.contains(".txt"))
                .skip(1)
                .map((s -> {
                    try {
                        Scanner scanner = new Scanner(new File(s));
                        ScannerAndVar sav = new ScannerAndVar(scanner, null);
                        sav.var = sortParameters.hasNextPredicate.apply(sav)
                                ? sortParameters.nextFunction.apply(sav)
                                : null;
                        return sav;
                    } catch (FileNotFoundException e) {
                        System.out.println("File " + s + " not found.");
                        return null;
                    }
                }))
                .filter(Objects::nonNull)
                .filter(scannerAndVar -> scannerAndVar.var != null)
                .collect(Collectors.toList());

        try {
            FileOutputStream outputStream = new FileOutputStream(outputFilename);

            while (!savList.isEmpty()) {
                ScannerAndVar sav = sortParameters.sortWay.apply(savList, sortParameters);

                if (sav != null) {
                    outToFile(outputStream, sav.var);

                    if (sortParameters.hasNextPredicate.apply(sav)) {
                        sav.var = sortParameters.nextFunction.apply(sav);
                    } else {
                        sav.scanner.close();
                        sav.scanner = null;
                    }
                }

                if (!savList.stream().allMatch(scannerAndVar -> scannerAndVar != null && scannerAndVar.scanner != null)) {
                    savList = savList.stream()
                            .filter(scannerAndVar -> scannerAndVar != null && scannerAndVar.scanner != null)
                            .collect(Collectors.toList());
                }
            }

            outputStream.close();
        } catch (IOException e) {
            System.out.println("Error to write in file " + outputFilename);
        }
    }

    public static void outToFile(FileOutputStream outputStream, Object outString) throws IOException {
        outputStream.write((outString + "\n").getBytes());
    }
}
