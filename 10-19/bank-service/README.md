# BankAccount – Banki számlavezető modul

A projekt egy egyszerű bankszámlát (`BankAccount`) modellező Java osztályt és annak JUnit 5 egységtesztjeit tartalmazza.

## Fő funkciók
- Számla létrehozása 0 kezdőegyenleggel
- Befizetés (`deposit`): pozitív összeg jóváírása
- Pénzfelvétel (`withdraw`): egyenleget meg nem haladó pozitív összeg levonása
- Egyenleglekérdezés (`getBalance`)
- Bemenet-ellenőrzés: érvénytelen műveleteknél (0, negatív összeg, egyenleghiány) `IllegalArgumentException` kivétel dobása az egyenleg megváltoztatása nélkül

## Futtatás és tesztelés

A projekt fordítása és a tesztek futtatása a `10-19/bank-service` könyvtárból Maven segítségével végezhető:

```bash
mvn clean test
```

Kifejezetten a `BankAccountTest` osztály futtatása:
```bash
mvn test -Dtest=BankAccountTest
```

A tesztesetek az összes előírt esetet lefedik (kezdőegyenleg, befizetés, felvétel, határértékek, kivételkezelés és egyenleg változatlanságának ellenőrzése sikertelen műveletek után).
