package pl.lodz.p.it.spjava.debug;

import java.time.Instant;
import java.util.concurrent.Semaphore;
import pl.lodz.p.it.spjava.debug.model.PhilosopherModel;
import pl.lodz.p.it.spjava.debug.model.TableModel;

/**
 *
 * Zadanie realizujące funkcjonalność filozofa siedzącego przy stole.
 */
public class PhilosopherRunnable implements Runnable {

    private final TableModel table;
    private final PhilosopherModel philosopher;

    public PhilosopherRunnable(final TableModel table, final PhilosopherModel philosopher) {
        this.table = table;
        this.philosopher = philosopher;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //zastosowanie semafora uniemożliwia wszystkim filozofom na jednoczesne podniesienie lewego widelca
                table.lock();
                //rezerwacja lewego widelca poprzez założenie blokady wewnętrznej na reprezentującym go obiekcie
                synchronized (table.getFork(philosopher.getLeftFork())) {
                    System.out.println(Thread.currentThread().getName() + " pobrał lewy widelec-"
                              + philosopher.getLeftFork() % StartApp.NUMBER_OF_FORKS + " i czeka na pobranie widelca prawego "
                              + " " + Instant.now().getNano());
                    //rezerwacja prawego widelca poprzez założenie blokady wewnętrznej na reprezentującym go obiekcie
                    synchronized (table.getFork(philosopher.getRightFork())) {
                        System.out.println(Thread.currentThread().getName() + " pobrał prawy widelec-"
                                  + philosopher.getRightFork() % StartApp.NUMBER_OF_FORKS + " " + Instant.now().getNano());
                        philosopher.eating();
                    }
                }
                table.unlock();
                philosopher.resting();
            }
        } catch (InterruptedException ie) {
            System.err.println(Thread.currentThread().getName() + " zakończył działanie.");
        }
    }

}
