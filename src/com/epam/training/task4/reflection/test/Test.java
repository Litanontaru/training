package com.epam.training.task4.reflection.test;

import com.epam.training.task4.reflection.InjectException;
import com.epam.training.task4.reflection.Injector;
import com.epam.training.task4.reflection.Util;
import com.epam.training.task4.reflection.cache.TypeCache;

import java.util.Map;


public class Test {

    public static void test() {
        try {
            final Map<TypeCache, Class> classes
                    = Util.loadClasses("com.epam.training.task4.reflection.cache.implementation");

            ChangerString changerString = new ChangerString();
            Injector.inject(changerString, classes);

            changerString.put(1, "text in lower case.");
            changerString.put(2, "reverse");

            System.out.println("Inject reverseCache:");
            System.out.println(changerString.get(1));
            System.out.println(changerString.get(2));


            Inheritor inheritor = new Inheritor();
            Injector.inject(inheritor, classes);

            System.out.println();
            System.out.println("Inject upperCache:");
            inheritor.put(1, "text in lower case.");
            inheritor.put(2, "reverse");

            System.out.println(inheritor.get(1));
            System.out.println(inheritor.get(2));
        } catch (InjectException e) {
            System.out.println(e.getMessage());
        }
    }


}
