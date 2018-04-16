import java.util.*;
import java.lang.reflect.*;
import java.util.logging.Level;

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

    public static void clearShouldRemoveAllElementsFromListTest() {
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

    public static void clearShouldChangeSizeToZeroTest() {
        // given
        list.add("one");
        // when
        list.clear();
        // then
        assert list.size()==0 : "List contain element added before clear";
    }

    public static void clearCanBeUsedOnEmptyListTest() {
        // given
        boolean wasCaught = false;
        // when
        try {
            list.clear();
        } catch (Exception e) {
            wasCaught = true;
        }
        // then
        assert list.isEmpty() : "List is not empty after clear";
        assert !wasCaught : "Exception has been caught";
    }

    public static void containsShouldReturnTrueIfElementIsInTheListTest() {
        // given
        list.add(FIRST_TO_ADD);
        // when - then
        assert list.contains(FIRST_TO_ADD) : "Contains did not find element which in fact is in the list";
    }

    public static void containsAllowExecuteWithNullAsArgumentTest() {
        // given
        boolean wasCaught = false;
        boolean wasReturned = false;
        list.add(null);
        // when
        try {
            wasReturned = list.contains(null);
        } catch (Exception e) {
            wasCaught = true;
        }
        // then
        assert !wasCaught : "Exception has been caught";
        assert wasReturned: "contains(null) returned false, but null was added to List";
    }

    public static void containsShouldReturnFalseIfElementIsNotInListTest() {
        // given
        list.add(FIRST_TO_ADD);
        // when - then
        assert !list.contains(LAST_TO_ADD) : "Contains returned true, but in fact should returned false because element is not in the list";
    }

    public static void constainsAllShouldReturnTrueWhenThisListHasAllEmenetsFromSpecifiedColectionTest() {
        // given
        list.add("first");
        list.addAll(getExampleSet());
        list.add("last");
        // when - then
        assert list.containsAll(getExampleSet()) : "ConstainsAll returned false when actually list has all elements from specified collection";
    }

    public static void constainsAllShouldReturnTrueWhenThisListHasAllEmenetsFromSpecifiedColectionNotInRowTest() {
        // given
        list.add("first");
        list.addAll(getExampleSet());
        list.add(2, "last");
        // when - then
        assert list.containsAll(getExampleSet()) : "ConstainsAll returned false when actually list has all elements from specified collection";
    }

    public static void containsAllShouldThrowNullPointerExceptionWhenSpecifiedCollectionIsNullTest() {
        // given
        boolean wasCaught = false;
        // when
        try {
            list.containsAll(null);
        } catch (Exception e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been Caught";
    }

    private static List getTheSameTypeOfList(List l) {
        try {
            return l.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public static void equalsReturnTrueWhenTwoListHasTheSameElementsInTheSameOrderTest() {
        // given
        list.addAll(getExampleSet());
        List<String> list2 = getTheSameTypeOfList(list);
        list2.addAll(getExampleSet());
        // when - then
        assert list.equals(list2) : "Equals returned false when in fact lists has the same elements and the same arder";
    }

    public static void equalsReturnFalseWhenTwoListHasTheSameElementsInDifferentOrderTest() {
        // given
        String toAdd = "This text must be unique";
        list.add(toAdd);
        list.addAll(getExampleSet());
        List<String> list2 = getTheSameTypeOfList(list);
        list2.addAll(getExampleSet());
        list2.add(toAdd);
        // when - then
        assert !list.equals(list2) : "Equals returned false when in fact lists has the same elements and the same arder";
    }

    public static void equalsReturnFalseWhenCompareTwoDifferentTypesCollectionAlthoughTheHaveTheSameElementsInTheSameOrderTest() {
        // given
        list.addAll(getExampleSet());
        // when - then
        assert !list.equals(getExampleSet()) : "Equals returned true when in fact it compared different types of collection although the same order of elements";
    }

    public static void getReturnElementFromListCorrectlyTest() {
        // given
        list.add("This is also unique - first");
        list.addAll(getExampleSet());
        // when - then
        assert list.get(1).equals(FIRST_TO_ADD) : "Get returned incorrect element";
    }

    public static void getThrowIndexOfBoundExceptionWhenGivenArgumentIsLowerThenZeroTest() {
        // given
        boolean wasCaught = false;
        list.addAll(getExampleSet());
        // when
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

    public static void getThrowIndexOfBoundExceptionWhenGivenArgumentIsBiggerThenSizeTest() {
        // given
        boolean wasCaught = false;
        list.addAll(getExampleSet());
        // when
        try {
            list.get(10);
        } catch (IndexOutOfBoundsException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

    public static void twoListsWithTheSameContentShouldHaveTheSameHashCodesTest() {
        // given
        list.addAll(getExampleSet());
        List<String> list2 = getTheSameTypeOfList(list);
        list2.addAll(getExampleSet());
        // when - then
        assert list.hashCode() == list2.hashCode() : "Same lists have the different hashCode";
    }

    public static void twoListsWithDifferentContentShouldDifferentHashCodesTest() {
        // given
        list.addAll(getExampleSet());
        List<String> list2 = getTheSameTypeOfList(list);
        list2.add("different1");
        list2.add("different2");
        list2.add("different3");
        // when - then
        assert list.hashCode() != list2.hashCode() : "Different lists have the same hashCode";
    }

    public static void emptyListsHashCodeIsEqualOneTest() {
        // when - then
        assert list.hashCode() == 1 : "Empty list has hashCode different that one";
        assert getTheSameTypeOfList(list).hashCode() == 1 : "Another empty list has hashCode different than one";
    }

    public static void indexOfReturnProperIndexOfFSearchedElementTest() {
        // given
        list.add("initElemOfList");
        list.addAll(getExampleSet());
        // when - then
        assert list.indexOf(FIRST_TO_ADD) == 1 : "Returned first index in not correct";
        assert list.indexOf(LAST_TO_ADD) == 3 : "Returned last index in not correct";
    }

    public static void indexOfReturnMinusOneValueWhenSearchedElementIsNotInListTest() {
        // given
        list.addAll(getExampleSet());
        // when - then
        assert list.indexOf("notExistingElem") == -1 : "Returned first index in not correct";
    }

    public static void indexOfReturnFirstIndexOfTheSameObjectInListTest() {
        // given
        String toCheck = "toCheck";
        list.addAll(getExampleSet());
        list.add(toCheck);
        list.addAll(getExampleSet());
        list.add(toCheck);
        // when - then
        assert list.indexOf(toCheck) == 3 : "Returned first index in not correct";
    }

    public static void isEmptyReturnTrueWhenWasNothingAddedToListTest() {
        // when - then
        assert list.isEmpty() : "isEmpty returned false when in fact should return true";
    }

    public static void isEmptyReturnFalseWhenWasAddedAtLeastOneElementToListTest() {
        // given
        list.add(null);
        // when - then
        assert !list.isEmpty() : "isEmpty returned true when in fact should return false";
    }

    public static void isEmptyReturnTrueWhenSthWasAddedAndRemovedFromListTest() {
        //given
        String temp = "I'll be remove";
        list.add(temp);
        list.remove(temp);
        // when - then
        assert list.isEmpty() : "isEmpty returned false when in fact should return true";
    }

    public static void iteratorAllowToIterateInProperSequenceTest() {
       // given
       List<String> list2 = getTheSameTypeOfList(list);
       list.addAll(getExampleSet());
       list2.addAll(getExampleSet());
       boolean isProperSequence = true;
       int i = 0;
       // when
        Iterator<String> itr = list.iterator();
       while(itr.hasNext()) {
           String temp = itr.next();
           if (!(temp.equals(list2.get(i)))) {
               isProperSequence = false;
               break;
           }
           i++;
       }
       // then
       assert isProperSequence : "Iterator did not allow for iterate in proper sequence";
    }

    public static void lastIndexOfReturnProperIndexOfFSearchedElementTest() {
        // given
        list.add("initElemOfList");
        list.addAll(getExampleSet());
        // when - then
        assert list.lastIndexOf(FIRST_TO_ADD) == 1 : "Returned first index in not correct";
        assert list.lastIndexOf(LAST_TO_ADD) == 3 : "Returned last index in not correct";
    }

    public static void lastIndexOfReturnMinusOneValueWhenSearchedElementIsNotInListTest() {
        // given
        list.addAll(getExampleSet());
        // when - then
        assert list.lastIndexOf("notExistingElem") == -1 : "Returned first index in not correct";
    }

    public static void lastIndexOfReturnLastIndexOfTheSameObjectInListTest() {
        // given
        String toCheck = "toCheck";
        list.addAll(getExampleSet());
        list.add(toCheck);
        list.addAll(getExampleSet());
        list.add(toCheck);
        // when - then
        assert list.lastIndexOf(toCheck) == 7 : "Returned last index in not correct";
    }

}


// TODO: Own testing framework to make independent tests