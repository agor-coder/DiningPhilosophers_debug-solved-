echo "Po uruchomieniu programu java debugger (jdb) nalezy uruchomić jeden z planów nadzorowanego wykonania kodu programu z użyciem polecenia read (np. read plan.jdb)."

echo "Uruchamiając plan.jdb można zauważyć, że w przypadku wstrzymania (suspend) jednego z wątków reprezentujących filozofów problem wzajemnej blokady (deadlock) nie występuje."
echo "Uruchamiając plan2.jdb wstrzymany zostaje wątek główny, co uniemożliwia zakończenie aplikacji w sytuacji wystąpienia wzajemnej blokady (deadlock). Należy wówczas poczekać"
echo "aż wystąpi wzajemna blokada i uruchomić plan2after_deadlock i zaobserwować, który wątek oczekuje (waiting in monitor) na blokadę wewnętrzną na obiekcie reprezentującym widelec."

jdb -classpath target/classes
