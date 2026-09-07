# Szállítási díj függvény tesztelése (09-07)

A feladat a `calculateShippingCost` JavaScript függvény ellenőrzése a megadott specifikáció alapján, tesztesetek összeállítása, kézi végrehajtás és a fellelt hibák dokumentálása.

---

## 1. Specifikáció áttekintése

A leírás szerinti elvárások:
- **Tömeg szerinti díjszabás:**
  - 5 kg alatt: **1500 Ft**
  - 5 kg-tól 20 kg-ig: **2500 Ft** (5 és 20 kg is ebbe a sávba tartozik)
  - 20 kg felett: **5000 Ft**
- **Magyarországi szállítás:**
  - 20 000 Ft rendelési érték felett ingyenes (0 Ft).
  - Az országkód kétbetűs ISO kód, a kis- és nagybetűk egyenértékűek (pl. `HU` és `hu` is érvényes).
- **Külföldi szállítás:**
  - A szállítási díj duplázódik (2x).
  - Külföldre nem jár az ingyenességi kedvezmény.
- **Expressz kézbesítés:**
  - Az alapértelmezett szállítási díjra +50% felár jön rá (* 1.5).
- **Korlátozások / Validáció:**
  - A tömeg és a rendelési érték sem lehet negatív (nem megengedett bemenetek).

---

## 2. Tesztesetek és elvárt eredmények

A teszteléshez 15 tesztesetet készítettem (ekvivalencia partícionálás és határérték elemzés alapján):

| ID | Teszteset célja | orderValue | weight | country | express | Elvárt érték | Tényleges eredmény | Eredmény |
|---|---|---|---|---|---|---|---|---|
| TC-01 | Alap eset, 5 kg alatti csomag belföldre | 8 000 | 3 | "HU" | false | **1500** | 1500 | PASS |
| TC-02 | Határérték: pontosan 5 kg belföldre | 12 000 | 5 | "HU" | false | **2500** | 5000 | **FAIL** |
| TC-03 | Közepes súly (5 és 20 kg közt) | 10 000 | 12 | "HU" | false | **2500** | 2500 | PASS |
| TC-04 | Határérték: pontosan 20 kg belföldre | 15 000 | 20 | "HU" | false | **2500** | 5000 | **FAIL** |
| TC-05 | 20 kg feletti nehéz csomag belföldre | 10 000 | 25 | "HU" | false | **5000** | 5000 | PASS |
| TC-06 | Ingyenes szállítás HU 20 000 Ft felett | 25 000 | 10 | "HU" | false | **0** | 0 | PASS |
| TC-07 | Kisbetűs "hu" kód 20k felett (ingyenes kéne legyen) | 25 000 | 8 | "hu" | false | **0** | 5000 | **FAIL** |
| TC-08 | Kisbetűs "hu" kód normál értéknél (nem külföld!) | 10 000 | 3 | "hu" | false | **1500** | 3000 | **FAIL** |
| TC-09 | Külföldi szállítás (dupla díj) | 10 000 | 3 | "DE" | false | **3000** | 3000 | PASS |
| TC-10 | Külföldre nincs ingyenes szállítás 20k felett sem | 35 000 | 4 | "DE" | false | **3000** | 3000 | PASS |
| TC-11 | Expressz szállítás (+50% felár) | 10 000 | 4 | "HU" | true | **2250** | 1550 | **FAIL** |
| TC-12 | Külföld + expressz szállítás egyszerre | 10 000 | 10 | "AT" | true | **7500** | 5050 | **FAIL** |
| TC-13 | Érvénytelen negatív súly | 10 000 | -2 | "HU" | false | **HIBA** | 1500 | **FAIL** |
| TC-14 | Érvénytelen negatív rendelési érték | -2000 | 4 | "HU" | false | **HIBA** | 1500 | **FAIL** |
| TC-15 | Határérték: pontosan 20 000 Ft rendelési összeg | 20 000 | 3 | "HU" | false | **1500** | 0 | **FAIL** |

---

## 3. Kézi futtatás (lépésről lépésre)

Pár jellemző eset lekövetése kézzel a forráskódban:

### TC-02 (5 kg-os határérték teszt)
Bemenet: `orderValue = 12000, weight = 5, country = "HU", express = false`
1. `shippingCost = 0`
2. `country === "HU" && orderValue >= 20000` -> `12000 >= 20000` hamis, nem lép be.
3. `if (weight < 5)` -> `5 < 5` hamis.
4. `else if (weight > 5 && weight < 20)` -> `5 > 5` hamis, kiesik a feltételből.
5. `else` -> ide fut be: `shippingCost = 5000`.
6. Külföld és expressz nem teljesül.
7. Visszatér: `5000`.
-> **Hibás**, mert 5 kg-ra 2500 Ft-ot kellett volna adnia, de a szigorú `>` miatt a legdrágább ágba ugrott.

### TC-11 (Expressz felár teszt)
Bemenet: `orderValue = 10000, weight = 4, country = "HU", express = true`
1. Súly alapján: `weight < 5` -> `shippingCost = 1500`.
2. Külföld nem teljesül.
3. `if (express === true)` -> `shippingCost = shippingCost + 50`.
4. `1500 + 50 = 1550`.
-> **Hibás**, mert a specifikáció szerint 50%-kal kell növelni (`1500 * 1.5 = 2250`), itt viszont a kód fix 50 forintot ad hozzá.

### TC-07 (Kisbetűs országkód teszt)
Bemenet: `orderValue = 25000, weight = 8, country = "hu", express = false`
1. `country === "HU"` vizsgálatnál `"hu" === "HU"` hamis, ezért nem adja meg az ingyenes szállítást.
2. Súly ág: 8 kg -> `shippingCost = 2500`.
3. `if (country !== "HU")` -> `"hu" !== "HU"` igaz, ezért megduplázza a díjat: `2500 * 2 = 5000`.
-> **Hibás**, mert a kisbetűs "hu"-t külföldnek nézte, így 0 Ft helyett 5000 Ft lett a díj.

---

## 4. Talált hibák jegyzéke

### 1. Hiba: Súlyhatárok hibás kezelése (5 kg és 20 kg)
- **Hiba helye:** 19. sor (`else if (weight > 5 && weight < 20)`)
- **Probléma:** Szigorú egyenlőtlenségeket (`>`, `<`) használt a fejlesztő `>=` és `<=` helyett. Így ha a csomag pontosan 5 kg vagy pontosan 20 kg, egyik feltétel sem teljesül, és a feleslegesen magas 5000 Ft-os sávba (`else`) kerül.
- **Javítás:** `weight >= 5 && weight <= 20`

### 2. Hiba: Expressz szállítás felára (+50 Ft az 50% helyett)
- **Hiba helye:** 30. sor (`shippingCost = shippingCost + 50;`)
- **Probléma:** Százalékos növelés helyett fix 50 forintot ad a díjhoz.
- **Javítás:** `shippingCost = shippingCost * 1.5;`

### 3. Hiba: Kisbetűs országkódok kezelése hiányzik
- **Hiba helye:** 13. és 25. sor (`country === "HU"`, `country !== "HU"`)
- **Probléma:** Nincs nagybetűsítés (`toUpperCase()`), emiatt `"hu"` esetén nem adja meg az ingyenes szállítást, ráadásul külföldiként kezeli és megduplázza a díjat.
- **Javítás:** Az országkódot a vizsgálat előtt nagybetűre kell konvertálni.

### 4. Hiba: Nincs bemeneti validáció negatív értékekre
- **Hiba helye:** A függvény eleje
- **Probléma:** Ha negatív értéket kap (pl. `weight = -5`), a `weight < 5` miatt szó nélkül 1500 Ft-ot ad vissza hibaüzenet helyett. Ugyanez igaz a negatív rendelési értékre is.
- **Javítás:** A függvény elején ellenőrizni kell: `if (orderValue < 0 || weight < 0) throw new Error(...)`.

### 5. Hiba / Eltérés: 20 000 Ft feletti érték vizsgálata
- **Hiba helye:** 13. sor (`orderValue >= 20000`)
- **Probléma:** A szöveg szerint "20 000 Ft feletti", ami szigorúan `> 20000`. A kód `>= 20000`-et használ, így pont 20 000 Ft-nál is 0 Ft-ot ad 1500 Ft helyett.

---

## 5. Hibákat reprodukáló egyszerű tesztek

```javascript
const { calculateShippingCost } = require('./calculateShippingCost');

// Bug 1: 5 kg és 20 kg határérték
console.log("5 kg teszt:", calculateShippingCost(10000, 5, "HU", false));   // 5000-et ad (2500 helyett)
console.log("20 kg teszt:", calculateShippingCost(10000, 20, "HU", false)); // 5000-et ad (2500 helyett)

// Bug 2: Expressz 50% felár
console.log("Expressz teszt:", calculateShippingCost(10000, 3, "HU", true)); // 1550-et ad (2250 helyett)

// Bug 3: Kisbetűs országkód
console.log("Kisbetűs hu:", calculateShippingCost(10000, 3, "hu", false));   // 3000-et ad (1500 helyett)

// Bug 4: Negatív súly
console.log("Negatív súly:", calculateShippingCost(10000, -2, "HU", false)); // 1500-at ad (nem dob hibát)

// Bug 5: 20k határérték
console.log("Pont 20000 Ft:", calculateShippingCost(20000, 3, "HU", false)); // 0-t ad (1500 helyett)
```

---

## 6. Futtatás

A tesztek futtatása a mappából:

```bash
node 09-07/test.js
```
