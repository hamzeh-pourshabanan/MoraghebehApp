package com.hamzeh.moraghebehapp.ui.amal;

public class AmalUtils {

    public static String[] days = {"یکم", "یکم", "دوّم", "سوّم", "چهارم", "پنجم", "ششم", "هفتم", "هشتم", "نهم", "دهم",
            "یازدهم", "دوازدهم", "سیزدهم", "چهاردهم", "پانزدهم", "شانزدهم", "هفدهم", "هجدهم", "نوزدهم", "بیستم"};

    public String[] dahGan = {"بیست", "سی", "چهل", "پنجاه", "شصت", "هفتاد", "هشتاد", "نود"};
    public String[] sadGan = {"صد", "دویصت", "سیصد", "چهارصد", "پانصد", "ششصد", "هفتصد", "هشتصد", "نهصد"};


    public String getDay(int count) {
//        int length = String.valueOf(count).length();
//        if (length >= 2) {
//            int decimal = String.valueOf(count).indexOf(1);
//
//        }

        if (count <= 20) {
            return days[count];
        } else if (count < 100) {
            if (count % 10 != 0) {
                return dahGan[count / 10 - 2] + " و " + days[count % 10];
            } else {
                return dahGan[count / 10 - 2] + "م";
            }

        } else {
            return null;
        }
    }
}