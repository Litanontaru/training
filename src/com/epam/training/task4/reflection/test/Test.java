package com.epam.training.task4.reflection.test;

import com.epam.training.task4.reflection.Injector;


public class Test {

    public static void test() {

        ChangerString changerString = new ChangerString();
        Injector.inject(changerString);

        changerString.put(1, "text in lower case.");
        changerString.put(2, "reverse");

        System.out.println("Inject reverseCache:");
        System.out.println(changerString.get(1));
        System.out.println(changerString.get(2));


        Inheritor inheritor = new Inheritor();
        Injector.inject(inheritor);

        System.out.println();
        System.out.println("Inject upperCache:");
        inheritor.put(1, "text in lower case.");
        inheritor.put(2, "reverse");

        System.out.println(inheritor.get(1));
        System.out.println(inheritor.get(2));
    }


}
