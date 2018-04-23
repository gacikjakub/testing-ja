package testingja;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.*;

public class TestClass {

    public TestClass() {
        classToTest.add(this.getClass());
//        Arrays.stream(getClass().getClasses()).forEach(c -> System.out.println(c.getSimpleName()));
    }

    public static void main(String[] args){
        // TODO: runTest must be static and many more
    }

    private List<Throwable> caughtExceptions = new LinkedList<>();
    private Method beforeTest;
    private Method afterTest;
    private static int passedCounter=0;
    private static int failedCounter=0;
    private Method[] declaredMethods;
    private static List<Class> classToTest = new LinkedList<>();

    static {
        classToTest.add( new Object() { }.getClass().getEnclosingClass());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Test {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface BeforeTest {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AfterTest {}

//    @Retention(RetentionPolicy.RUNTIME)
//    @Target(ElementType.TYPE)
//    public @interface TestingClass {}

    private void emptyMethod() {}


    private void checkAndInitBeforeAndAfter(Method[] declaredMethods) throws ThereCanBeOnlyOneMethod, NoSuchMethodException {
        afterTest = returnOnlyOne(declaredMethods, AfterTest.class);
        beforeTest = returnOnlyOne(declaredMethods, BeforeTest.class);
        if (beforeTest == null) {
            beforeTest = TestClass.class.getDeclaredMethod("emptyMethod");
        }
        if (afterTest == null) {
            afterTest = TestClass.class.getDeclaredMethod("emptyMethod");
        }
    }

    private Method returnOnlyOne(Method[] declaredMethods, Class klazz) throws ThereCanBeOnlyOneMethod {
        Method[] result = {null};
        if (Arrays.stream(declaredMethods).filter(m -> m.isAnnotationPresent(klazz)).map(m -> {result[0]=m; return m;}).count() > 1) {
            throw new ThereCanBeOnlyOneMethod(String.format("found more than one method marked with %s", klazz.getSimpleName()));
        }
        return result[0];
    }


    private void showCaughtException() {
        caughtExceptions.stream().forEach(e -> {
            Arrays.stream(e.getStackTrace()).forEach(ste -> System.out.println(ste));
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        });
    }

    //
    public void runTests() {
        for(Class toTest : classToTest) {
            runClassTests(toTest);
        }
        showCaughtException();
        System.out.println("PASSED: " + passedCounter);
        System.out.println("FAILED: " + failedCounter);
    }

    public void runClassTests(Class toTest) {
        declaredMethods = toTest.getDeclaredMethods();
        try {
            checkAndInitBeforeAndAfter(declaredMethods);
        } catch (Throwable t) {
            Arrays.stream(t.getStackTrace()).forEach(ste -> System.out.println(ste));
            System.out.println(t.getMessage());
            System.exit(1);
        }
        try {
            Arrays.stream(declaredMethods).filter(m -> m.isAnnotationPresent(Test.class)).forEach(m -> {
                try {
                    beforeTest.invoke(this);
                } catch (Throwable t) {
                    throw new ExceptionInBeforeTestMethod(t);
                }
                try {
                    m.invoke(this);
                    passedCounter++;
                } catch (Throwable t) {
                    caughtExceptions.add(t);
                    failedCounter++;
                }
                try {
                    afterTest.invoke(this);
                } catch (Throwable t) {
                    throw new ExceptionInAfterTestMethod(t);
                }
            });
        } catch (ExceptionInAfterTestMethod | ExceptionInBeforeTestMethod e) {
            showCaughtException();
            throw e;
        }
    }


    class ThereCanBeOnlyOneMethod extends Throwable {
        public ThereCanBeOnlyOneMethod(String s) {
            super(s);
        }
    }

    class ExceptionInBeforeTestMethod extends RuntimeException {
        public ExceptionInBeforeTestMethod(String s) {super(s);}
        public ExceptionInBeforeTestMethod(Throwable t) {super(t);}
    }

    class ExceptionInAfterTestMethod extends RuntimeException {
        public ExceptionInAfterTestMethod(String s) {super(s);}
        public ExceptionInAfterTestMethod(Throwable t) {super(t);}
    }
}
