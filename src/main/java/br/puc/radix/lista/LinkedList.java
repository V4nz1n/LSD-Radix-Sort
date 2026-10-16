package br.puc.radix.lista;

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