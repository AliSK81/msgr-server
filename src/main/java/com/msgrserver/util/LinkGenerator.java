package com.msgrserver.util;

public class LinkGenerator {
    public static String generate(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        String link=sb.toString();
        return "@msgrInviteLink." + link; //todo replace name of app by msgr
    }
}
