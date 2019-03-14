package com.alpha.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 1、从某个目录下，获得所有class后缀的文件
 * 2、获得所有类名称
 */
public class PackageScanner {
    private static Set<String> set = new HashSet<>();
    private static Set<String> getSet() {
        return set;
    }
    public static Set<String> getClassNameFromDir(File baseDir) {
        getPath(baseDir);
        Set<String> collect = set.stream()
                .map(f -> {
                    String s = f.substring(baseDir.getPath().length() + 1);
                    String replace = s.replace("\\", ".");
                    int i = replace.lastIndexOf(".class");
                    String result = replace.substring(0, i);
                    return result;
                })
                .collect(Collectors.toSet());
        return collect;
    }
    private static void getPath(File baseDir) {
        File[] files = baseDir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String path = file.getPath();
                if (path.endsWith(".class")) {
                    set.add(path);
                }
            } else {
                getPath(file);
            }
        }
    }
}
