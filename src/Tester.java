
import java.util.Iterator;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Surya Wono
 */
public class Tester {
    
    
    public static void main(String[] args){
        MinHeap<Double> mh=new MinHeap();
        Random r=new Random();
        for (int i=0;i<100;i++){
            mh.add(r.nextDouble());
        }
        while (!mh.isEmpty()){
            System.out.println(mh.extractMin());
        }
    }
}
