# BankAccount – Egységtesztelés JUnit 5 használatával

A projekt a `BankAccount` osztály megvalósítását és annak JUnit 5 egységtesztjeit tartalmazza.

## Fő funkciók
- Számla inicializálása 0 kezdőegyenleggel
- Befizetés (`deposit`): pozitív összeg hozzáadása az egyenleghez
- Pénzfelvétel (`withdraw`): az egyenleget meg nem haladó pozitív összeg levonása
- Egyenleg lekérdezése (`getBalance`)
- Bemenet-ellenőrzés: érvénytelen összegek (0 vagy negatív érték, illetve fedezethiány) esetén `IllegalArgumentException` kivétel dobása az egyenleg megváltoztatása nélkül

## Futtatás és tesztelés

A projekt gyökérkönyvtárából (`10-05/bank-account`) Maven segítségével futtathatók a tesztek:

```bash
mvn clean test
```

A tesztek ellenőrzik a kezdőegyenleget, a befizetést és pénzfelvételt, a határértékeket (teljes egyenleg felvétele), a kivételkezelést és az egyenleg változatlanságát sikertelen tranzakciók után.
