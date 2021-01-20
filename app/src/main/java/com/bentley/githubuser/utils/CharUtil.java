package com.bentley.githubuser.utils;

public class CharUtil {
    public static boolean isEnglish(char ch) {
        return (ch >= (int) 'A' && ch <= (int) 'Z')
                || (ch >= (int) 'a' && ch <= (int) 'z');
    }

    public static boolean isKorean(char ch) {
        return ch >= Integer.parseInt("AC00", 16)
                && ch <= Integer.parseInt("D7A3", 16);
    }

    public static boolean isNumber(char ch) {
        return ch >= (int) '0' && ch <= (int) '9';
    }

    public static boolean isSpecial(char ch) {
        return (ch >= (int) '!' && ch <= (int) '/') // !"#$%&'()*+,-./
                || (ch >= (int) ':' && ch <= (int) '@') //:;<=>?@
                || (ch >= (int) '[' && ch <= (int) '`') //[\]^_`
                || (ch >= (int) '{' && ch <= (int) '~'); //{|}~
    }

    /**
     * 입력값이 한글일 때, 초성값을 반환하는 함수
     */
    public static String getInitialSound(String text) {
        // 초성값 모음
        String[] chs = {
                "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
                "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
                "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",
                "ㅋ", "ㅌ", "ㅍ", "ㅎ"
        };

        if(text.length() > 0) {
            char chName = text.charAt(0);

            if(chName >= 0xAC00) // 0xAC00('ㄱ'): 한글 유니코드 시작값
            {

                /**
                 *  한글 코드의 값 계산 = ((초성 * 21) + 중성) * 28 + 종성 + 0xAC00
                 *  초성 계산 = ((문자 - 0xAC00) / 28) / 21
                 *  http://yoonbumtae.com/?p=745
                 */

                int uniVal = chName - 0xAC00;
                int cho = ((uniVal - (uniVal % 28))/28)/21;

                return chs[cho];
            }
        }

        return null;
    }
}

