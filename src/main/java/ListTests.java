import java.sql.SQLOutput;
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

    private static boolean checkProperSequence(Iterator<String> itr, List<String> list,List<String> list2) {
        boolean isProperSequence = true;
        int i = 0;
        itr = list.listIterator();
        while(itr.hasNext()) {
            String temp = itr.next();
            if (!(temp.equals(list2.get(i)))) {
                isProperSequence = false;
                break;
            }
            i++;
        }
        return isProperSequence;
    }

    public static void iteratorAllowToIterateInProperSequenceTest() {
       // given
       List<String> list2 = getTheSameTypeOfList(list);
       list.addAll(getExampleSet());
       list2.addAll(getExampleSet());
       Iterator<String> itr = list.iterator();
       // when - then
       assert checkProperSequence(itr, list, list2) : "Iterator did not allow for iterate in proper sequence";
    }

    public static void iteratorHasNextReturnFalseWhenIsNoNextElementTest() {
        // given
        list.add("firstAndLast");
        Iterator<String> iter = list.iterator();
        iter.next();
        // when - then
        assert !iter.hasNext() : "Has next should return false when in fact returned true";
    }

    public static void iteratorNextThrowNoSuchElementExceptionWhenHasNoMoreElementTest() {
        // given
        list.add("firstAndLast");
        Iterator<String> iter = list.iterator();
        iter.next();
        boolean wasCaught = false;
        // when
        try {
            iter.next();
        } catch (NoSuchElementException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
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

    public static void listIteratorAllowToIterateInProperSequenceTest() {
        // given
        List<String> list2 = getTheSameTypeOfList(list);
        list.addAll(getExampleSet());
        list2.addAll(getExampleSet());
        ListIterator<String> itr = list.listIterator();
        // when - then
        assert checkProperSequence(itr,list,list2) : "Iterator did not allow for iterate in proper sequence";
    }

    public static void listIteratorHasPreviousReturnFalseAtTheBegginingOfListTest() {
        // given
        list.add("firstAndLast");
        ListIterator<String> iter = list.listIterator();
        // when - then
        assert !iter.hasPrevious() : "Has previous should return false when in fact returned true";
    }

    public static void listIteratorPreviousThrowNoSuchElementExceptionAtTheBegginingOfListTest() {
        // given
        list.add("firstAndLast");
        ListIterator<String> iter = list.listIterator();
        boolean wasCaught = false;
        // when
        try {
            iter.previous();
        } catch (NoSuchElementException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

    public static void listIteratorAllowToIterateInProperSequenceStartedFromCertainPositionTest() {
        // given
        List<String> list2 = getTheSameTypeOfList(list);
        list.addAll(getExampleSet());
        list.addAll(getExampleSet());
        list2.addAll(getExampleSet());
        list2.addAll(getExampleSet());
        boolean isProperSequence = true;
        int i = list2.size()-1;
        ListIterator<String> itr = list.listIterator(list.size());
        // when
        while(itr.hasPrevious()) {
            String temp = itr.previous();
            if (!(temp.equals(list2.get(i)))) {
                isProperSequence = false;
                break;
            }
            i--;
        }
        // then
        assert isProperSequence : "Iterator did not allow for iterate in proper sequence";
    }

    public static void listIteratorHasNextReturnFalseAtTheEndOfListTest() {
        // given
        list.addAll(getExampleSet());
        ListIterator<String> iter = list.listIterator(list.size());
        // when - then
        assert !iter.hasNext() : "Has next should return false when in fact returned true";
    }

    public static void listIteratorNextThrowNoSuchElementExceptionAtTheEndOfListTest() {
        // given
        list.addAll(getExampleSet());
        ListIterator<String> iter = list.listIterator(list.size());
        boolean wasCaught = false;
        // when
        try {
            iter.next();
        } catch (NoSuchElementException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

    public static void removePropertlyDeleteFirstOccurenceOfElementInListTest() {
        // given
        list.addAll(getExampleSet());
        list.addAll(getExampleSet());
        list.remove(FIRST_TO_ADD);
        int occurence = 0;
        // when
        for (String temp : list) {
            if (temp.equals(FIRST_TO_ADD)) {
                occurence++;
            }
        }
        // then
        assert occurence==1 : "Element is still in list after remove";
    }

    public static void removeReturnTrueWhenElementHasBeenRemovedFromListTest() {
        // given
        list.addAll(getExampleSet());
        // when - then
        assert list.remove(FIRST_TO_ADD) : "Remove should return true but returned false";
    }

    public static void removeReturnFalseWhenElementDoesNotExistInListTest() {
        // given
        String toDelete = "I'm not in list";
        list.addAll(getExampleSet());
        // when - then
        assert !list.remove(toDelete) : "Remove should return false but returned true";
    }

    public static void removeReturnRemovedElementByIndexTest() {
        // given
        list.addAll(getExampleSet());
        // when - then
        assert list.remove(2).equals(LAST_TO_ADD) : "Incorrect element was returned by remove method";
    }

    public static void removeByIndexPropertlyDeleteElementFromListTest() {
        // given
        list.addAll(getExampleSet());
        // when
        list.remove(0);
        // then
        assert !list.contains(FIRST_TO_ADD) : "Element is still in list after remove";
    }

    public static void removeByIndexThrowIndexOutOfBoundExceptionWhenGivenIndexIsNotInRangeTest() {
        // given
        list.addAll(getExampleSet());
        boolean wasCaught = false;
        // when
        try {
            list.remove(10);
        }
        catch (IndexOutOfBoundsException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not benn caught";
    }

    public static void removeAllRemovesAllOccurencesOfElementsInGivenCollectiontest() {
        // given
        list.add("Random text");
        list.addAll(getExampleSet());
        list.add("Another Random Text");
        list.addAll(getExampleSet());
        boolean allWasRemoved = true;
        // when
        list.removeAll(getExampleSet());
        for (String temp : list) {
            if (getExampleSet().contains(temp)) {
                allWasRemoved = false;
                break;
            }
        }
        // then
        assert allWasRemoved : "Elements has not been removed correctly";
    }

    public static void removeAllReturnTrueWhenRemovedAtLeastOneElementsGivenAsCollectionWhenAnotherNotExistInListTest() {
        // given
        list.add("Random text");
        list.addAll(getExampleSet());
        list.add("Another Random Text");
        // when -  then
        assert list.removeAll(getExampleSet()) : "removeAll should return true but in fact it returned false";
    }

    public static void removeAllReturnFalseWhenNoOneElementsGivenAsCollectionWasNotRemoved() {
        // given
        list.add("Random text");
        list.add("Another Random Text");
        // when - then
        assert !list.removeAll(getExampleSet()) : "removeAll should return false but in fact it returned true";
    }

    public static void replaceAllChangeAllElementsInListInCerteinKindOfThisTest() {
        // given
        boolean allWasChanged = true;
        list.addAll(getExampleSet());
        // when
        list.replaceAll(temp -> {return temp.toUpperCase();});
        // then
        int i = 0;
        for (String temp : getExampleSet()) {
            if (!temp.toUpperCase().equals(list.get(i))) {
                allWasChanged = false;
                break;
            }
            i++;
        }
        assert allWasChanged : "Not All elements have been changed";
    }
    // TODO: Make more tests for replaceAll

    public static void retainAllRemovesAllElementsFromListWithIsNotInGivenCollectionTest() {
        // given
        List<String> list2 = getTheSameTypeOfList(list);
        list.add("Random first");
        list.addAll(getExampleSet());
        list.add("Random second");
        list.addAll(getExampleSet());
        list2.addAll(getExampleSet());
        list2.addAll(getExampleSet());
        // when
        list.retainAll(getExampleSet());
        // then
        assert list.equals(list2) : "Not All Elements Have Been Removed";
    }

    public static void retainAllReturnTrueWhenListHasBeenChangedTest() {
        // given
        list.add("Random first");
        list.addAll(getExampleSet());
        list.add("Random second");
        list.addAll(getExampleSet());
        // when - then
        assert list.retainAll(getExampleSet()) : "retainAll returned false but in fact should return true";
    }

    public static void retainAllReturnFalseWhenListHasNotBeenChangedTest() {
        // given
        list.addAll(getExampleSet());
        list.addAll(getExampleSet());
        // when - then
        assert !list.retainAll(getExampleSet()) : "retainAll returned true but in fact should return false";
    }

    public static void setMethodChangeElementOfListPropertlyTest() {
        // given
        String elemToReplace = "I'm pretty new";
        list.addAll(getExampleSet());
        // when
        list.set(1,elemToReplace);
        // then
        assert list.get(1).equals(elemToReplace) : "Chosen element has not been changed";
    }

    public static void setMethodReturnOldElementOfListPropertlyTest() {
        // given
        String elemToReplace = "I'm pretty new";
        list.addAll(getExampleSet());
        // when - then
        assert list.set(2,elemToReplace).equals(LAST_TO_ADD) : "set method did not return previous element of list";
    }

    public static void setMethodThrowIndexOutOfBoundExceptionWhenGivenIndexIsOutOfRangeTest() {
        // given
        boolean wasCaught = false;
        list.addAll(getExampleSet());
        // when
        try {
            list.set(10, "sth");
        } catch (IndexOutOfBoundsException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

    public static void sizeReturnZeroWhenAllElementsHaveBeenRemovedTest() {
        // given
        list.addAll(getExampleSet());
        list.removeAll(getExampleSet());
        // when - then
        assert list.size()==0 : "size returned value different than 0";
    }

    public static void sizeReturnProperValueAfterAddingSomeElementsTest() {
        // given
        list.addAll(getExampleSet());
        // when - then
        assert list.size()==3 : "size returned value different than quantity of elements";
    }

    public static void sizeReturnProperValueAfterRemovingElementWhichNotExistInListTest() {
        // given
        list.addAll(getExampleSet());
        list.remove("different");
        // when - then
        assert list.size()==3 : "size returned incorrect value";
    }

    public static void sortAllowToChangeIndexOfElementsInListRelatedWithGivenComparatorTest() {
        // given
        List<String> list2 = getTheSameTypeOfList(list);
        list.addAll(Arrays.asList("C","D","F","B","E","A"));
        list2.addAll(Arrays.asList("A","B","C","D","E","F"));
        // when
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
               return o1.compareTo(o2);
            }
        });
        // then
        assert list.equals(list2) : "List has not been sorted correctly";
    }
    //TODO: Make more tests for sort method

    public static void spliteratorCreatesAndReturnsCorrectSpliteratorTest() {
        // given
        list.add("some First");
        list.addAll(getExampleSet());
        list.add("some middle");
        list.addAll(getExampleSet());
        Spliterator<String> spliterator = list.spliterator();
        // when - then
        assert spliterator.estimateSize()==8 : "spliterator method returned incorrect Spliterator";
    }

    //TODO: Make more tests for spliterator

    public static void subListReturnsProperListTest() {
        // given
        list.add("some First");
        list.addAll(getExampleSet());
        List<String> list2 = getTheSameTypeOfList(list);
        list2.addAll(getExampleSet());
        // when - then
        assert list.subList(1,4).equals(list2) : "subList method return incorrect list";
    }

    public static void subListThrowIndexOutOfBoundExceptionWhenGivenIndexesWhichAreOutOfRangeTest() {
        // given
        list.addAll(getExampleSet());
        list.addAll(getExampleSet());
        boolean wasCaught = false;
        // when
        try {
            list.subList(4,20);
        } catch (IndexOutOfBoundsException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

    public static void subListReturnsEmptyListWhenStartAndEndPointIsTheSameTest() {
        // given
        list.addAll(getExampleSet());
        // when - then
        assert list.subList(1,1).isEmpty() : "subList is not Empty";
    }

    public static void toArrayReturnArrayFormListInProperSequenceTest() {
        // given
        list.addAll(getExampleSet());
        boolean inProperSequence = true;
        int i=0;
        // when
        String[] sArray = list.toArray(new String[0]);
        for(String temp :list) {
            if(!temp.equals(sArray[i])) {
                inProperSequence = false;
                break;
            }
            i++;
        }
        // then
        assert inProperSequence : "toArray return array in not proper sequence";
    }

    public static void toArrayThrowNullPointerExceptionWhenSpecifiedArrayIsNull() {
        // given
        list.addAll(getExampleSet());
        boolean wasCaught = false;
        // when
        try {
            list.toArray(null);
        } catch (NullPointerException e) {
            wasCaught = true;
        }
        // then
        assert wasCaught : "Exception has not been caught";
    }

}


// TODO: Own testing framework to make independent tests