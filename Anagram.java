import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.math.*;


@SuppressWarnings("unchecked")
public class Anagram {
    public static Scanner x;
    public static Scanner y;
    public static List<String> anagrams;
    public static List<String>[] hashmap;

    //for(int i=0;i<40009;i++){
      //  hashmap[i]=new ArrayList<>();
    //}

    public static int hashval(String str) {

        int sum = 0;
        String temp = str;
        //temp= str+"0";
        //int len = str.length();
        int hash = 0;
        char[] chars = temp.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        for (int i = 0; i < sorted.length(); i++) {
            hash = (hash * 31 + (int) sorted.charAt(i)) % 40009;
        }
        return hash;
    }

    public static String sort(String str) {
        char[] temp = str.toCharArray();
        Arrays.sort(temp);
        String st = new String(temp);
        return st;
    }

    public static boolean contains(String str) {
        String st = sort(str);
        int hashcode = hashval(st);
        //System.out.println("st "+st);
        //System.out.println("hashcode: "+hashcode);
        int i = 0;
        while (i < 40009) {
            if (hashmap[hashcode].isEmpty())
                return false;
            else if(sort(hashmap[hashcode].get(0)).equals(st))
                return true;
            else{
                hashcode = (hashcode + i) % 40009;
                i = i + 1;
            }
        }
        return false;
    }

    public static List<String> nospace(String str) {
        List<String> list = new ArrayList<>();
        String st = sort(str);
        int hashcode = hashval(st);
        int i = 0;
        while (i < 40009) {
            if (hashmap[hashcode].isEmpty())
                return list;
            else if (st.equals(sort(hashmap[hashcode].get(0)))) {
                return hashmap[hashcode];
            } else {
                hashcode = (hashcode + i) % 40009;
                i++;

            }
        }
        return list;
    }

    public static List<String> singlespaces(String str) {
        List<String> list = new ArrayList<>();
        char[] set = str.toCharArray();
        int n = set.length;
        String word = "";
        String wordcomp = "";
        for (int i = 0; i < (1 << n); i++) {
            word = "";
            wordcomp = "";
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    word += set[j];
                } else {
                    wordcomp += set[j];
                }
                if (word.length() >= 3 && wordcomp.length() >= 3 && word.length()+wordcomp.length()==str.length())
                    list.add(word + " " + wordcomp);
            }
        }
        return list;
    }

    public static List<String> doublespacesleft(String str, String str1) {
        char[] set = str.toCharArray();
        List<String> list = new ArrayList<>();
        int n = set.length;
        String word = "";
        String wordcomp = "";
        HashMap hp = new HashMap();
        for (int i = 0; i < (1 << n); i++) {
            word = "";
            wordcomp = "";
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    word += set[j];
                } else {
                    wordcomp += set[j];
                }
            }
            if (word.length() >= 3 && wordcomp.length() >= 3 && word.length()+wordcomp.length()==str.length())
                list.add(str1 + " " + word + " " + wordcomp);
        }
        return list;
    }

    public static List<String> doublespacesright(String str, String str1) {
        char[] set = str.toCharArray();
        List<String> list = new ArrayList<>();
        int n = set.length;
        String word = "";
        String wordcomp = "";
        for (int i = 0; i < (1 << n); i++) {
            word = "";
            wordcomp = "";
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    word += set[j];
                } else {
                    wordcomp += set[j];
                }
            }
            if (word.length() >= 3 && wordcomp.length() >= 3 && word.length()+wordcomp.length()==str.length())
                list.add(word + " " + wordcomp + " " + str1);
        }
        return list;
    }

    public static List<String> doublespaces(String str) {
        List<String> list = singlespaces(str);
        List<String> doublespace = new ArrayList<>();
        for (String stri : list) {
            String[] word = stri.split(" ");
            List<String> list1 = doublespacesleft(word[1], word[0]);
            List<String> list2 = doublespacesright(word[0], word[1]);
            for (String s : list1) {
                doublespace.add(s);
            }
            for (String s : list2) {
                doublespace.add(s);
            }
        }
        return doublespace;
    }

    public static void main(String[] args) {

        long stime = System.currentTimeMillis();

        try {
            x = new Scanner(new File(args[0]));
            y = new Scanner(new File(args[1]));
        } catch (Exception e) {
            System.out.println("Couldn't find file");
        }
        hashmap = new ArrayList[40009];
        for (int i = 0; i < 40009; i++) {
            hashmap[i] = new ArrayList<>();
        }
        HashMap hp = new HashMap();
        int nofvocab = x.nextInt();
        int nofinput = y.nextInt();
        String word = "";
        for (int i = 0; i < nofvocab; i++) {
            word = x.next();
            //System.out.println("word: "+word);
            String st = sort(word);
            //System.out.println("st: "+st);
            int hashcode = hashval(st);
            //System.out.println("hashcode: "+hashcode);
            if (hashmap[hashcode].isEmpty()) {
                hashmap[hashcode].add(word);
            } else {
                if (st.equals(sort(hashmap[hashcode].get(0)))) {
                    hashmap[hashcode].add(word);
                } else {
                    int j = 0;
                    while (true) {
                        if (hashmap[hashcode].isEmpty()) {
                            hashmap[hashcode].add(word);
                            break;
                        } else if (st.equals(sort(hashmap[hashcode].get(0)))) {
                            hashmap[hashcode].add(word);
                            break;
                        } else {
                            hashcode = (hashcode + j) % 40009;
                            j++;
                        }
                    }
                }
            }
        }
        //for(int i=0;i<40009;i++){
        //    System.out.println(hashmap[i]);
        //}

        String inp = "";
        List<String> anag;
        for (int i = 0; i < nofinput; i++) {
            inp = y.next();
            //System.out.println("inp: "+inp);
            int len = inp.length();
            anag = new ArrayList<>();
            if (len < 6) {
                if(contains(inp)) {
                    List<String> lis = nospace(inp);
                    for (int j = 0; j < lis.size(); j++) {
                        if (!hp.containsKey(lis.get(j))) {
                            anag.add(lis.get(j));
                            hp.put(sort(lis.get(j)), lis.get(j));
                        }
                    }
                }
            }
            if (len >= 6 && len < 9) {
                if (contains(inp)) {
                    List<String> lis1 = nospace(inp);
                    for (int j = 0; j < lis1.size(); j++) {
                        if (!hp.containsKey(lis1.get(j))) {
                            anag.add(lis1.get(j));
                            hp.put(sort(lis1.get(j)), lis1.get(j));
                        }
                    }
                }
                List<String> lis2 = singlespaces(inp);
                for (int j = 0; j < lis2.size(); j++) {
                    String[] str = lis2.get(j).split(" ");
                    if (contains(str[0]) && contains(str[1])) {
                        List<String> str1 = nospace(str[0]);
                        List<String> str2 = nospace(str[1]);
                        for (String strii : str1) {
                            for (String stri : str2) {
                                if (!hp.containsKey(strii + " " + stri)) {
                                    anag.add(strii + " " + stri);
                                    hp.put(sort(strii + " " + stri), strii + " " + stri);
                                }
                            }
                        }
                    }
                }
            }

            if (len >= 9 && len <= 12) {
                if (contains(inp)) {
                    List<String> lis1 = nospace(inp);
                    for (int j = 0; j < lis1.size(); j++) {
                        if (!hp.containsKey(lis1.get(j))) {
                            anag.add(lis1.get(j));
                            hp.put(sort(lis1.get(j)), lis1.get(j));
                        }
                    }
                }
                List<String> lis2 = singlespaces(inp);
                for (int j = 0; j < lis2.size(); j++) {
                    String[] str = lis2.get(i).split(" ");
                    if (contains(str[0]) && contains(str[1])) {
                        List<String> list1 = nospace(str[0]);
                        List<String> list2 = nospace(str[1]);
                        for (String s : list1) {
                            for (String ss : list2) {
                                if (!hp.containsKey(s + " " + ss)) {
                                    anag.add(s + " " + ss);
                                    hp.put(sort(s + " " + ss), s + " " + ss);
                                }
                            }
                        }

                    }
                }
                List<String> lis3 = doublespaces(inp);
                for (int j = 0; j < lis3.size(); j++) {
                    String[] str = lis3.get(j).split(" ");
                    if (contains(str[0]) && contains(str[1]) && contains(str[2])) {
                        List<String> lis1 = nospace(str[0]);
                        List<String> listt2 = nospace(str[1]);
                        List<String> listt3 = nospace(str[2]);
                        for (String s : lis1) {
                            for (String ss : listt2) {
                                for (String sss : listt3) {
                                    if (!hp.containsKey(s + " " + ss + " " + sss)) {
                                        anag.add(s + " " + ss + " " + sss);
                                        hp.put(sort(s + " " + ss + " "), s + " " + ss + " " + sss);
                                    }
                                }
                            }
                        }

                    }
                }
            }
            List<String> ls =new ArrayList<String>();
            int sz=anag.size();
            for(int k=0;k<sz;k++)
            {
                if(ls.indexOf(anag.get(k))==-1)
                    ls.add(anag.get(k));
            }
            sz=ls.size();
            Collections.sort(ls);
            for (int j = 0; j < sz; j++) {
                System.out.println(ls.get(j));
            }
        }


        System.out.println("-1");
        x.close();
        y.close();

        long etime = System.currentTimeMillis();

        System.out.println(etime - stime);




    }
}
