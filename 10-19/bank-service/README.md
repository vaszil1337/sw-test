# Mock objektumok használata (Mockito)

A projekt a Mockito keretrendszer használatát mutatja be egységtesztekben, külső függőségek szimulálásával a korábbi `BankAccount` osztály kiegészítéseként.

## Fő összetevők
- `NotificationService`: Értesítési interfész (`send(String message)`).
- `BankService`: Szolgáltatás, amely a számlára történő befizetés után értesítést küld a konstruktoron keresztül kapott `NotificationService` segítségével (Dependency Injection).
- `ExchangeRateService`: Devizaárfolyam-lekérdező interfész (`getRate(String from, String to)`).

## Alkalmazott Mockito funkciók
- **`mock()`**: Dinamikus ál-objektum létrehozása interfészekből valódi hálózati/külső hívások nélkül.
- **`@Mock` és `@InjectMocks`**: Deklaratív mockolás és automatikus függőséginjektálás tesztkörnyezetben.
- **`verify()`**: Annak ellenőrzése, hogy a sikeres befizetés után meghívódott-e az értesítésküldés.
- **`never()`**: Annak igazolása, hogy érvénytelen/sikertelen befizetés esetén nem történik értesítés.
- **`when(...).thenReturn(...)`**: Előre definiált válaszértékek (pl. EUR/HUF árfolyam) beállítása és tesztelése.

## Futtatás és tesztelés

A tesztek futtatása a `10-19/bank-service` könyvtárból:

```bash
mvn clean test
```

Külön a Mockito tesztek futtatása:
```bash
mvn test -Dtest=BankServiceTest,ExchangeRateServiceTest
```
