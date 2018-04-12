
import java.util.*;
import java.lang.reflect.*;

public class ListTests {

    private static List<String> list;
    final static String FIRST_TO_ADD = "firstNew";
    final static String LAST_TO_ADD = "thirdNew";

    public static void main(String[] args) throws Exception {
        for( Method method: ListTests.class.getDeclaredMethods()) {
            if (!method.getName().equals("main") && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
                setUpArrayList();
                method.invoke(null);
                setUpLinkedList();
                method.invoke(null);
            }
        }
    }

    private static void setUpArrayList() {
        list = new ArrayList<>();
    }

    private static void setUpLinkedList() {
        list = new LinkedList<>();
    }

    public static void addElementToEmptyListTest() {
        // given
        // when
        boolean wasAdded = list.add("first");
        String onPositionWhereHasBeenAdded = list.get(0);
        //then
        assert wasAdded : "Element couldn't be added";
        assert onPositionWhereHasBeenAdded.equals("first") : "Last element is not correct";
    }

    public static void addElementToListOnLastPositionTest() {
        // given
        list.add("first");
        // when
        boolean wasAdded= list.add("last");
        String onPositionWhereHasBeenAdded = list.get(1);
        //then
        assert wasAdded : "Element couldn't be added";
        assert onPositionWhereHasBeenAdded.equals("last") : "Last element is not correct";
    }

    public static void addSameElementTwoTimesTest() {
        // given
            String phrase = "It's raining men";
        // when
            list.add(phrase);
            boolean wasAdded = list.add(phrase);
            String first = list.get(0);
            String second = list.get(1);
        // then
        assert wasAdded : "Same value has not been added second time";
        assert second.equals(phrase) : "Value added second time is not correct";
        assert second.equals(first) : "Value added second time is not the same as first one";
    }

    public static void addElementToListOnCertainPositionTest() {
        // given
            String text = "... at the afternoon - it makes me feel alright";
        // when
            list.add("first");
            list.add("I'll be on pos with index 2 at the end");
            list.add(1, text);
        // then
        assert list.get(1).equals(text) : "Element pos 1 is not correct";
    }

    public static void addElementOnPositionLessThenZeroShouldThrowExceptionTest() {
        // given
        boolean wasCaught = false;
        // when
        try {
            list.add(-1,"so stupid position...");
        } catch (IndexOutOfBoundsException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

    public static void addElementOnPositionBiggerThenLastElementShouldThrowExceptionTest() {
        // given
        boolean wasCaught = false;
        list.add("first");
        // when
        try {
            list.add(2,"so stupid position...");
        } catch (IndexOutOfBoundsException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

    private static Set<String> getExampleSet() {
        Set<String> toAdd = new LinkedHashSet<>();
        toAdd.add(FIRST_TO_ADD);
        toAdd.add("secondNew");
        toAdd.add(LAST_TO_ADD);
        return toAdd;
    }

    public static void addAllShouldAddGivenCollectionAtTheEndOfChangingListTest() {
        // given
        List<AssertionError> linkedErrors = new LinkedList<>();
        Set<String> toAdd = getExampleSet();
        // when
        list.add("first");
        boolean wasAdded = list.addAll(toAdd);
        // then
        assert wasAdded : "Collection has not been added";
        assert list.get(1).equals(FIRST_TO_ADD) : "Added first element is not correct";
        assert list.get(3).equals(LAST_TO_ADD) : "Added last element is not correct";
    }

    public static void addAllShouldPropertlyAddCollectionToEmptyListTest() {
        // given
        Set<String> toAdd = getExampleSet();
        // when
        boolean wasAdded = list.addAll(toAdd);
        // then
        assert wasAdded : "Collection has not been added";
        assert list.get(0).equals(FIRST_TO_ADD) : "Added first element is not correct";
        assert list.get(2).equals(LAST_TO_ADD) : "Added last element is not correct";
    }

    public static void addAllThrowNullPointerExceptionWhenArgumentIsNull() {
        // given
        boolean wasCaught = false;
        // when
        try {
            list.addAll(null);
        } catch (NullPointerException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "NullPointerException has not been caught";
    }

    public static void addAllShouldPropertlyAddCollectionInTheMiddleOfList() {
        // given
        final String willBeLast = "second";
        list.add("first");
        list.add(willBeLast);
        // when
        boolean wasAdded = list.addAll(1,getExampleSet());
        // then
        assert wasAdded : "Collection has not been added";
        assert list.get(1).equals(FIRST_TO_ADD) : "First from added elements is not correct";
        assert list.get(3).equals(LAST_TO_ADD) : "Last from added elements is not correct";
        assert list.get(4).equals(willBeLast) : "Last element before add before them is not on last position after add";
    }

    public static void addAllCollectionOnPositionLessThenZeroShouldThrowExceptionTest() {
        // given
        boolean wasCaught = false;
        // when
        try  {
            list.addAll(-1, getExampleSet());
        } catch (IndexOutOfBoundsException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

    public static void addAllColectionOnPositionBiggerThenLastElementShouldThrowExceptionTest() {
        // given
        boolean wasCaught = false;
        list.add("first");
        // when
        try  {
            list.addAll(2, getExampleSet());
        } catch (IndexOutOfBoundsException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

    public static void clearShouldRemoveAllElementsFromList() {
        // given
        String elemToAdd = "one";
        list.add(elemToAdd);
        list.add("two");
        list.add("three");
        // when
        list.clear();
        // then
        assert !list.contains(elemToAdd) : "List contain element added before clear";
    }

    public static void clearShouldChangeSizeToZero() {
        // given
        list.add("one");
        // when
        list.clear();
        // then
        assert list.size()==0 : "List contain element added before clear";
    }

//    public static void clear

}


// TODO: Own testing framework to make independent tests