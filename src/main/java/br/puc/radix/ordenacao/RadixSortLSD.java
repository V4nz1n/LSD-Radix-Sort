package br.puc.radix.ordenacao;

import br.puc.radix.lista.LinkedList;
import br.puc.radix.lista.Node;

public class RadixSortLSD {
    private LinkedList<Integer>[] buckets;
    public int findMax(LinkedList<Integer> list){
        Node<Integer> current = list.getFirst();
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < list.getSize(); i++){
            if (current.getData() > max) {
                max = current.getData();
            }
            current = current.getNext();
        }
        return max;
    }

    public void createBuckets(){
        this.buckets = new LinkedList[10];
        for(int i = 0; i < this.buckets.length; i++){
            this.buckets[i] = new LinkedList<>();
            System.out.println("bucket[" + i + "] = " + this.buckets[i].getSize());
        }
    }

    public void distributeNodes(LinkedList<Integer> list, int exp){
        Node<Integer> current = list.getFirst();
        while(current != null){
            Node<Integer> varTemp = current.getNext();
            int digit = ((current.getData() / exp) % 10);
            buckets[digit].addNode(current);
            current = varTemp;
        }
    }

    public void gatherBuckets(LinkedList<Integer> list){
        list.clear();
        for (int i = 0; i < buckets.length; i++){
            list.concat(buckets[i]);
        }
    }


    public void printBuckets() {
        for (int i = 0; i < this.buckets.length; i++) {
            System.out.print("balde " + i + ": ");
            this.buckets[i].print();
        }
    }
}
