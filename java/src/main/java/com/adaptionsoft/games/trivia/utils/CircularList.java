package com.adaptionsoft.games.trivia.utils;

public class CircularList<T> {

    private Node<T> current;
    private Node<T> last;
    private int size;

    public void add(Node<T> node) {
        if (last == null) {
            node.setNext(node);
            current = node;
            last = node;
        } else {
            node.setNext(last.getNext());
            last.setNext(node);
            last = node;
        }
        size++;
    }

    public int size() {
        return size;
    }

    public void next() {
        current = current.getNext();
    }

    public T current() {
        return current.getValue();
    }
}
