package com.patterns.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Predicate;

public class PidLister {
    private Set<String> pidSet = new HashSet<String>();
    private final static String applicationNamme = "chrome.exe";

    public Set<String> getPidSet() throws IOException {

        Predicate<String> predicate = new Predicate <String>() {
            @Override
            public boolean test(String string) {
                if (string.contains(applicationNamme)) {
                    return true;
                } else return false;
            }
        };

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("tasklist");
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        String pid = "";
        while ((line = bufferedReader.readLine()) != null) {
            if (predicate.test(line)) {
                pid = line.replace(applicationNamme,"").trim();
                pid = pid.substring(0,pid.indexOf(" "));
                this.pidSet.add(pid);
            }
        }
        return this.pidSet;
    }

    public void setPidSet(Set<String> pidSet) {
        this.pidSet = pidSet;
    }

    public Set <String> checkNewPids(Set<String> pidSet) {
        Set <String> misfitSet = new HashSet<String>();
        pidSet.forEach(consumer -> {
            if (!this.pidSet.contains(consumer)) {
                misfitSet.add(consumer);
            }
        });
        return misfitSet;
    }

    public List<String> getSortedList(Set<String> setOfPids){
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1)-Integer.valueOf(o2);
            }
        };
        List<String> listOfPids = new ArrayList<>();
        listOfPids.addAll(setOfPids);
        listOfPids.sort(comparator);
        return listOfPids;
    }

    public static void main(String... args) {
        try {
//List all PIDs
            PidLister pidLister1 = new PidLister();

            System.out.println(pidLister1.getSortedList(pidLister1.getPidSet()).toString());
            System.out.println(pidLister1.pidSet.size());
//Run the application
            Runtime.getRuntime().exec(
                    "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
//Check new pids again
            PidLister pidLister2 = new PidLister();
            pidLister2.getPidSet();
            Set<String> result = pidLister2.checkNewPids(pidLister1.getPidSet());

            System.out.println(pidLister2.getSortedList(pidLister2.getPidSet()).toString());
            System.out.println(pidLister2.getSortedList(result).size());
            System.out.println(pidLister2.getSortedList(result).toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
