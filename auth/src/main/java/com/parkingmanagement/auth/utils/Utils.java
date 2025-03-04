package com.parkingmanagement.auth.utils;

public class Utils {

    public static String maskEmail(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex > 3) {
            return email.substring(0, 3) + "******" + email.substring(atIndex);
        }
        return email;
    }

    public static String maskCpf(String cpf) {
        if (cpf.length() == 11) {
            return cpf.substring(0, 3) + ".***.***-" + cpf.substring(9);
        } else if (cpf.length() > 3) {
            int remainingLength = cpf.length() - 3;
            StringBuilder maskedCpf = new StringBuilder(cpf.substring(0, 3));
            for (int i = 0; i < remainingLength; i++) {
                maskedCpf.append("*");
            }
            return maskedCpf.toString();
        }
        return cpf;
    }
}
