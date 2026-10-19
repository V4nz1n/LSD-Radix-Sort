package br.puc.radix.lista;

import java.util.Objects;

public class LinkedList<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public LinkedList(){
        this.first = null;
    }

    public LinkedList(T value) {
        this.first = new Node<T>(value);
        this.last = this.first;
        this.size = 1;
    }

    public void addFirst(T value){
        Node<T> newNode = new Node<>(value);
        if(this.isEmpty()){
            this.first = newNode;
            this.last = this.first;
            size++;
            return;
        }
        newNode.setNext(this.first);
        this.first = newNode;
        this.size++;
    }

    public void addLast(T value){
        Node<T> newNode = new Node<>(value);
        if(!this.isEmpty()){
            this.last.setNext(newNode);
            this.last = newNode;
            this.size++;
            return;
        }
        this.first = newNode;
        this.last = this.first;
        this.size++;
    }

    public void addNode(Node<T> value) {
        value.setNext(null);
        if(!this.isEmpty()){
            this.last.setNext(value);
            this.last = value;
            value.setNext(null);
            this.size++;
            return;
        }
        this.first = value;
        this.last = this.first;
        this.size++;
    }

    public void clear(){
            this.first = null;
            this.last = null;
            this.size = this.size - getSize();
    }

    public void concat(LinkedList<T> otherList){
        if(otherList.isEmpty()){
            return;
        }
        if(this.isEmpty()){
            this.first = otherList.getFirst();
            this.last = otherList.last;
            this.size = otherList.getSize();
            return;
        }
        this.last.setNext(otherList.getFirst());
        this.last = otherList.last;
        this.size = getSize() + otherList.getSize();
    }

    public int search(T value) {
        Node<T> current = this.first;
        int index = 0;
        while (current != null) {
            if (Objects.equals(current.getData(), value)) {
                return index;
            }
            current = current.getNext();
            index++;
        }
        return -1;
    }

    public boolean remove(T value) {
        Node<T> previous = null;
        Node<T> current = this.first;
        while (current != null) {
            if (Objects.equals(current.getData(), value)) {
                if (previous == null) {
                    this.first = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                if (current == this.last) {
                    this.last = previous;
                }
                current.setNext(null);
                this.size--;
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false;
    }

    public boolean isEmpty(){
        return this.first == null;
    }

    public Node<T> getFirst(){
        return this.first;
    }

    public int getSize(){
        return this.size;
    }

    public void print() {
        Node<T> current = this.first;
        while (current != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println();
    }
}