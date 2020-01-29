package pl.lodz.p.it.spjava.debug;

import pl.lodz.p.it.spjava.debug.model.PhilosopherModel;
import pl.lodz.p.it.spjava.debug.model.TableModel;

/**
 * Kod aplikacji zawiera rozwiązanie problemu ucztujących filozofów (poprawne
 * rozwiązanie kontrprzykładu 3), Należy zwrócić na zastosowanie semafora w celu
 * wyeliminowania problemu wzajemnego blokowania przez wątki, realizujące
 * działania poszczególnych filozofów.
 *
 */
public class StartApp {

    public static final int PHILOSOPHERS_NUMBER = 5;
    public static final int THREADS_STATE_DELAY = 30;
    private static Thread[] threads = new Thread[PHILOSOPHERS_NUMBER];

    public static void main(String[] args) {
        try {
            TableModel table = new TableModel(PHILOSOPHERS_NUMBER);
            for (int i = 0; i < PHILOSOPHERS_NUMBER; i++) {
                PhilosopherModel philosopher = new PhilosopherModel(i + 1);
                threads[i] = new Thread(new PhilosopherRunnable(table, philosopher));
                threads[i].setName("Wątek reprezentujący filozofa " + (i + 1));
                threads[i].start();
            }
            try {
                //poniższy mechanizm zapobiegający wystąpienia sytuacji zakleszczenia
                while (true) {
                    Thread.sleep(THREADS_STATE_DELAY * 1000);
                    int blockedThreadsNumber = 0;
                    for (int i = 0; i < PHILOSOPHERS_NUMBER; i++) {
                        Thread.State threadState = threads[i].getState();
                        System.out.println(threads[i].getName() + " ma status " + threadState);
                        if (Thread.State.BLOCKED.equals(threadState)) {
                            blockedThreadsNumber++;
                        }
                    }
                    if (blockedThreadsNumber == PHILOSOPHERS_NUMBER) {
                        System.err.println("Wymuszone zamknięcie aplikacji ze względu na wykrycie wzajemnej blokady wątków reprezentujących filozofów (deadlock).");
                        System.exit(1);
                    }
                }
            } catch (InterruptedException ex) {
                System.err.println("Główny wątek aplikacji został zakończony, brak możliwości wykrycia systuacji wzajemnego zakleszczenia.");
            }
        } catch (Throwable ex) {
            System.err.println("Wystąpił wyjątek typu: " + ex.getClass().getName() + " Szczegóły: " + ex.getMessage());
            System.exit(10);
        }
    }
}
