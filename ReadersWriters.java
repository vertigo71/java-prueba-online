/*
2.1. Lectores y escritores
Cree un programa en el que un conjunto de hebras acceden a un único recurso compartido:
un número entero (realmente, podría ser cualquier tipo de dato). Estas hebras pueden ser de uno
de los siguientes dos tipos:
Lectora: Lee una vez el entero y lo imprime por pantalla.
Escritora: Sustituye el entero por otro valor entero generado aleatoriamente, entre 0 y
10.000.
El número y tipo de cada hebra deberá ser configurable, ya sea mediante argumentos del
método main() o mediante atributos estáticos de una clase. Su programa deberá permitir que:
Varias hebras lectoras accedan al entero al mismo tiempo.
Impedir que, cuando una hebra escritora está accediendo al entero, ninguna otra hebra (de
ninguno de los dos tipos) acceda al entero
*/

import java.util.concurrent.locks.*;

public class ReadersWriters {
    int recursoCompartido = 0;
    boolean isWriter;
    static final int numPeople = 10;
    private final ReadWriteLock rw = new ReentrantReadWriteLock(true);
    
    ReadersWriters( boolean isWriter) {
        this.isWriter = isWriter;
    }
    
    public int leer() throws InterruptedException {
        rw.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " empieza a leer");
            Thread.sleep(200); // a propósito, para ver que varios leen a la vez
			System.out.println(Thread.currentThread().getName() + " termina de leer");
			return recursoCompartido;
        } finally {
            rw.readLock().unlock();
        }
    }
    public void escribir( int num ) throws InterruptedException {
        rw.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + " empieza a escribir");
			Thread.sleep(200);
			recursoCompartido = num;
			System.out.println(Thread.currentThread().getName() + " termina de escribir");
		} finally {
			rw.writeLock().unlock();
		}
    }
    
      public static void main(String args[]) {
          ReadersWriters rw = new ReadersWriters();
          Thread people = new Thread [numPeople];
          
          for (int i = 0; i < numPeople ; i++ ){
              boolean isWriter = ( ThreadLocalRandom.current().nextInt(10_001) % 5  == 0 );
              people [i] = new Thread ( () -> {
                  
                  }, ( isWriter ? "WRITER-" + i ; "READER-"+i ));
          }
        
      }
}