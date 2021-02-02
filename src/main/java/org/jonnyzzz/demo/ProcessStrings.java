package org.jonnyzzz.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProcessStrings {
    private static final String prefix = "prefix";

    public static List<String> main1(List<String> args) {
        final List<String> result = new ArrayList<>();
        for (String arg : args) {
            if (arg.startsWith(prefix)) {
                result.add(arg.substring(prefix.length()));
            }
        }

        return result;
    }

    public static List<String> main2(List<String> args) {

        return
                args.stream()
                .filter(it -> it.startsWith(prefix))
                .map(it -> it.substring(prefix.length()))
                .collect(Collectors.toList());

    }

    public static List<String> main3(List<String> args) {

        return
                args.stream()
                        .map(it -> {
                            if (it.startsWith(prefix)) {
                                return it.substring(prefix.length());
                            } else {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

    }
}
