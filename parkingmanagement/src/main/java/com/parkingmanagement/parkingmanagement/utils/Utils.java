package com.parkingmanagement.parkingmanagement.utils;

public class Utils {

    public static String maskEmail(String email) {
        if (email == null) return null;

        int atIndex = email.indexOf("@");
        if (atIndex > 3) {
            return email.substring(0, 3) + "******" + email.substring(atIndex);
        }
        return email;
    }

    public static String maskCpf(String cpf) {
        if (cpf == null) return null;

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

    public static String maskPhone(String phone) {
        if (phone == null) return null;

        if (phone.length() == 11) {
            return "(" + phone.substring(0, 2) + ") " + phone.substring(2, 7) + "-" + phone.substring(7);
        } else if (phone.length() == 10) {
            return "(" + phone.substring(0, 2) + ") " + phone.substring(2, 6) + "-" + phone.substring(6);
        }
        return phone;
    }
}
