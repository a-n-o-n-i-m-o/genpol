package com.tsoft.bot.both.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean find(String source, String path) {
        Matcher matcher = Pattern.compile(path).matcher(source);
        return matcher.find();
    }
    public static String getString(String source, String path, int match, int group) {
        String[] textInLines = source.split("\n");
        int line = 0, length = textInLines.length;
        Matcher matcher;
        String result;
        ArrayList<ArrayList<String>> allMatches = new ArrayList<>();
        while (line < length) {
            String textLine = textInLines[line] + "\n";
            if (line == 34)
                line = 34;
            matcher = Pattern.compile(path).matcher(textLine);
            while (matcher.find()) {
                int g = 0;
                ArrayList<String> machPoint = new ArrayList<>();
                while (g <= matcher.groupCount()) {
                    machPoint.add(matcher.group(g));
                    g++;
                }
                if (machPoint.size() != 0)
                    allMatches.add(machPoint);
            }
            line += 1;
        }

        result = allMatches.get(match).get(group);
        return result;
    }
}
