package club.zhangliyuanblog;

import java.util.*;

/**
 * @author liyuan.zhang
 * @date 2021/3/30 16:49
 */
public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        int[] ints = new int[]{10, 30, 90, 70};
        for (int i = 0; i < ints.length; i++) {
            for (int j = i + 1; j < ints.length; j++) {
                if (ints[i] + ints[j] == 100) {
                    System.out.println("[" + i + "," + j + "]");
                }
            }
        }
    }


    @org.junit.jupiter.api.Test
    public void test1() {
        String str = "akc129sf";
        char[] chars = str.toCharArray();
        Map<Integer, Character> map = new HashMap<>();
        List<Character> characters = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if (!Character.isLowerCase(chars[i])) {
                map.put(i, chars[i]);
            } else {
                characters.add(chars[i]);
            }
        }
        Collections.sort(characters);
        for (Integer i : map.keySet()) {
            characters.add(i, map.get(i));
        }
        StringBuilder builder = new StringBuilder();
        for (Character character : characters) {
            builder.append(character);
        }
        System.out.println(builder.toString());
    }
}
