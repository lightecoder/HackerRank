package leetcode;

public class ValidNumber {
    /**
     * "2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10",
     * "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"
     */
    public static void main(String[] args) {
        new ValidNumber().isNumber("1e.");
    }

    public boolean isNumber(String s) {
        int length = s.length();
        //case s<1 && s >20
        if (length < 1 || length > 20) {
            return false;
        }
        int difSign = s.length() - s.replaceAll("[-+]", "").length();
        //case allow e,E ; exclude [aA-zZ], eee, EE
        int difE = s.length() - s.replaceAll("[eE]", "").length();
        int diffDot = s.length() - s.replaceAll("\\.", "").length();
        //case exclude ".."
        if (diffDot > 1) {
            return false;
        }
        if (difE > 1) {
            return false;
        } else if (difE == 1) {
            int indexE = s.contains("e") ? s.indexOf("e") : s.indexOf("E");
            if (s.replaceAll("\\D", "").length() == 0
                    || s.substring(0, indexE).replaceAll("\\D", "").length() == 0) {
                return false;
            }
            String check = s.substring(indexE);
            //case e+
            if (check.length() < 2) {
                return false;
            }
            //case "4e+", "4e, exclude no digit after e
            int difSignCheck = check.length() - check.replaceAll("[-+]", "").length();
            if (difSignCheck > 0 && check.length() == 2) {
                return false;
            }
            //case only digit after "e+"
            for (char i : check.substring(1).replaceAll("[+-]", "").toCharArray()) {
                if (!((i - '0' >= 0) && (i - '0' <= 9))) {
                    return false;
                }
            }
            //case 1e , no digit after e
            if (s.indexOf("e") == s.length() - 1 || s.indexOf("E") == s.length() - 1) {
                return false;
            }
            //case e3 , no digit before e
            if (s.indexOf("e") == 0 || s.indexOf("E") == 0) {
                return false;
            }
            //case 99e2.5 , no . after e
            if (diffDot == 1) {
                if ((s.contains("e") || s.contains("E")) && (s.indexOf("e") > 0 ? s.indexOf("e") : s.indexOf("E")) - s.indexOf(".") < 0) {
                    return false;
                }
                //case exclude ".e1"
                else if (s.indexOf(".") == 0 && s.substring(1, 2).matches("\\D")) {
                    return false;
                }
            }

            if (s.replaceAll("[eE]", "").length() != s.replaceAll("[aA-zZ]", "").length()) {
                return false;
            }
            if (difSign > 2) {
                return false;
            } else if (difSign == 2) {
                if (!(s.charAt(s.indexOf("e") + 1) == '-' || s.charAt(s.indexOf("e") + 1) == '+')
                        || !(s.charAt(s.indexOf("E") + 1) == '-' || s.charAt(s.indexOf("E") + 1) == '+')) {
                    return false;
                }
            } else if (difSign == 1 && s.charAt(0) != '+' && s.charAt(0) != '-') {
                if (!(s.charAt(s.indexOf("e") + 1) == '-' || s.charAt(s.indexOf("e") + 1) == '+')
                        && !(s.charAt(s.indexOf("E") + 1) == '-' || s.charAt(s.indexOf("E") + 1) == '+')) {
                    return false;
                }
            }
        }
        //case +- first char
        if (difSign > 1 && difE == 0) {
            return false;
        } else if (difSign == 1 && difE == 0) {
            if (!(s.charAt(0) == '-' || s.charAt(0) == '+')) {
                return false;
            }
        }
        //case exclude "."
        if (length == 1 && s.charAt(0) == '.') {
            return false;
        }

        //case exclude abc
        int difLetters = s.length() - s.replaceAll("[aA-zZ]", "").length();
        if (difLetters - difE > 0) {
            return false;
        }
        if (s.replaceAll("\\D", "").length() == 0) {
            return false;
        }

        return true;
    }
}

//else if (diffDot == 1 && (s.substring(s.indexOf(".")).length() > 1)) {
//        if (s.substring(s.indexOf(".") + 1, s.indexOf(".") + 2).matches("\\D")) {
//        return false;
//        }
//        }
