package pl.lodz.p.it.spjava.debug.model;

import java.util.concurrent.Semaphore;
/**
 *
 * Model stołu z widelcami.
 */
public class TableModel {

    private final Object forks[];
    private final Semaphore tableSync;

    public TableModel(final int forksNumber) {
        if (forksNumber < 2) {
            throw new IllegalArgumentException("Liczba widelców na stole musi być wartością dodatnią i zgodną z liczbą filozofów, a podano " + forksNumber);
        }
        forks = new Object[forksNumber];
        for (int i = 0; i < forksNumber; i++) {
            forks[i] = new Object();
        }
        tableSync = new Semaphore(forksNumber - 1, true); //semafor tworzony jest zgodnie z regułą sprawiedliwości z wartością początkową N-1 filozofów
    }

    public void lock() throws InterruptedException {
        tableSync.acquire(); //opuszczenie semafora
    }

    public void unlock() {
        tableSync.release(); //podniesienie semafora
    }

    public Object getFork(final int number) {
        return forks[number % forks.length];
    }

    @Override
    public String toString() {
        return "Tablica z widelcami: " + forks.toString();
    }
}
