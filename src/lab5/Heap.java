package lab5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * TODO class declaration
 * We want to accept any type which is comparable to itself
 *
 * @param <T>
 */
public class Heap<T extends Comparable<T>> {
    private ArrayList<T> container;

    public Heap() {
        container = new ArrayList<>();
    }

    /**
     * @return If size is 0, throw {@link IllegalStateException}.
     * Otherwise, return the first element of {@link this#container}
     */
    public T peek() throws IllegalStateException{
        //TODO
        return this.container.get(0);
    }

    /**
     *
     * @return If size is 0, throw {@link IllegalStateException}. Otherwise, temporarily save the first element.
     * Afterwards, set the first position to the last element, and remove the last element.
     * Call {@link this#heapifyDown()}, then return the original first element
     */
    public T poll() throws IllegalStateException{
        //TODO
        T temp = this.container.get(0);
        // set the first position to the last element
//        this.container.set(0, this.container.get(this.size()-1));
        swap(0, this.container.size()-1);
        // removing the last element
        this.container.remove(this.container.size()-1);
        this.heapifyDown();
        return temp;
    }

    private void heapifyDown() {
        int pos = 0;
        while (hasLeft(pos)) {
            int smallerChildIndex = getLeftIndex(pos);
            if (hasRight(pos) && container.get(getRightIndex(pos)).compareTo(container.get(getLeftIndex(pos))) < 0) {
                smallerChildIndex = getRightIndex(pos);
            }
            if (container.get(pos).compareTo(container.get(smallerChildIndex)) < 0) {
                break;
            } else {
                swap(pos, smallerChildIndex);
            }
            pos = smallerChildIndex;
        }
    }

    /**
     * Add the object into {@link this#container}, then call {@link this#heapifyUp()}
     *
     * @param obj the object to add
     */
    public void add(T obj) {
        //TODO
        this.container.add(obj);
        this.heapifyUp();
    }

    public void addAll(Collection<T> list) {
        list.forEach(this::add);
    }

    /**
     * While the last element has a parent and is smaller than its parent, swap the two elements. Then, check again
     * with the new parent until there's either no parent or we're larger than our parent.
     */
    private void heapifyUp() {
        int index = this.container.size()-1;
        T last_element = this.container.get(index);
        int parent_pos = getParentIndex(index);
        T parent = this.container.get(parent_pos);
        while(last_element.compareTo(parent) < 0){
            swap(index, parent_pos);
            // update the variables
            index = parent_pos;
            if(hasParent(index)){
                last_element = container.get(index);
                parent_pos = getParentIndex(index);
                parent = container.get(parent_pos);
            }
            else{
                break;
            }
        }
    }
    public void printHeap(){
        System.out.println(this.container);
    }

    public int size() {
        return container.size();
    }

    private int getParentIndex(int i) {
        return (i - 1) / 2;
    }

    private int getLeftIndex(int i) {
        return 2 * i + 1;
    }

    private int getRightIndex(int i) {
        return 2 * i + 2;
    }

    private boolean hasParent(int i) {
        return getParentIndex(i) >= 0;
    }

    private boolean hasLeft(int i) {
        return getLeftIndex(i) < container.size();
    }

    private boolean hasRight(int i) {
        return getRightIndex(i) < container.size();
    }

    private void swap(int i1, int i2) {
        Collections.swap(container, i1, i2);
    }
}
