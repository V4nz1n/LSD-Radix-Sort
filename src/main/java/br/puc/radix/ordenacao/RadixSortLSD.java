package br.puc.radix.ordenacao;

import br.puc.radix.lista.LinkedList;
import br.puc.radix.lista.Node;

public class RadixSortLSD {
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
}
