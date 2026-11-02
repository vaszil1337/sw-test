# 1. Feladat: Hibakeresés a megadott kódokban

A feladat az 5 darab JavaScript függvény átvizsgálása a feladatleírás alapján, az eltérések és hibák feltárása.

## 1. DiscountCalculator

### 1. Végső ár számítása
- **Hibás kód:**
  ```javascript
  return orderValue - discount;
  ```
- **Hiba magyarázata:** A `discount` egy arányszám (pl. 0.05 az 5%-hoz). A kód viszont ezt a törtet vonja ki közvetlenül a végösszegből ahelyett, hogy a százalékot vonná le. Így pl. egy 20 000 Ft-os rendelésnél nem 19 000 Ft lesz a fizetendő, hanem 19 999.95 Ft (csak 5 fillért von le).
- **Javítás:** A kedvezménnyel csökkentett árat kell kiszámolni:
  ```javascript
  return orderValue * (1 - discount);
  ```

### 2. VIP kedvezmény mértéke
- **Hibás kód:**
  ```javascript
  if (isVip === true) {
      discount += 5;
  }
  ```
- **Hiba magyarázata:** VIP vásárlónak +5 százalékpont járna (ami `0.05`), a kód viszont egyből `5`-öt ad hozzá a kedvezményhez. Mivel a következő sorban van egy `discount > 0.20` vizsgálat, a kedvezmény azonnal lecsapódik a max 20%-ra. Emiatt egy VIP vevő még a 10 ezer alatti vásárlásra is 20%-ot kap az 5% helyett.
- **Javítás:** 5 helyett 0.05-öt kell hozzáadni:
  ```javascript
  if (isVip === true) {
      discount += 0.05;
  }
  ```

### 3. 10 000 Ft-os alsó határ
- **Hibás kód:**
  ```javascript
  if (orderValue > 10000 && orderValue < 25000)
  ```
- **Hiba magyarázata:** A leírás szerint 10 000 Ft-tól jár az 5% kedvezmény. A kód szigorú `>` jelet használ, emiatt pontosan 10 000 Ft-os rendelésnél 0% kedvezményt ad.
- **Javítás:** `>=` operátort kell használni:
  ```javascript
  if (orderValue >= 10000 && orderValue < 25000)
  ```

### 4. 50 000 Ft-os sávhatár
- **Hibás kód:**
  ```javascript
  } else if (orderValue >= 25000 && orderValue <= 50000) {
      discount = 0.10;
  } else if (orderValue > 50000) {
      discount = 0.15;
  }
  ```
- **Hiba magyarázata:** 50 000 Ft-tól már 15% járna, de a kód a `<=` miatt az 50 000 Ft-ot még a 10%-os sávba sorolja be.
- **Javítás:** Az 50 000 Ft-ot a 15%-os ágba kell tenni:
  ```javascript
  } else if (orderValue >= 25000 && orderValue < 50000) {
      discount = 0.10;
  } else if (orderValue >= 50000) {
      discount = 0.15;
  }
  ```

### 5. Negatív érték ellenőrzésének hiánya
- **Hibás kód:** A függvényben nincs bemeneti ellenőrzés a negatív értékekre.
- **Hiba magyarázata:** A leírás szerint negatív rendelési érték nem megengedett. A kód nem dob hibát negatív szám esetén sem.
- **Javítás:** A függvény elején le kell ellenőrizni és hibát dobni:
  ```javascript
  if (orderValue < 0) {
      throw new Error("Negatív rendelési érték nem megengedett!");
  }
  ```

---

## 2. ParkingFeeCalculator

### 1. Megkezdett órák lefelé kerekítése
- **Hibás kód:**
  ```javascript
  let hours = Math.floor(minutes / 60);
  let fee = hours * 600;
  ```
- **Hiba magyarázata:** Minden megkezdett óra fizetős (600 Ft), a `Math.floor` viszont lefelé kerekít. Emiatt 16 és 59 perc között 0 órát számol, így a parkolásért 0 Ft-ot kér 600 Ft helyett (de pl. 65 percnél is csak 1 órát számláz 2 helyett).
- **Javítás:** `Math.ceil`-lel felfelé kell kerekíteni:
  ```javascript
  let hours = Math.ceil(minutes / 60);
  let fee = hours * 600;
  ```

### 2. 15 perces türelmi idő
- **Hibás kód:**
  ```javascript
  if (minutes < 15) {
      return 0;
  }
  ```
- **Hiba magyarázata:** Az első 15 perc ingyenes, viszont a `<` miatt ha valaki pontosan 15 percet parkol, az már fizetős ágra kerül.
- **Javítás:** `<=`-t kell használni:
  ```javascript
  if (minutes <= 15) {
      return 0;
  }
  ```

### 3. VIP kedvezmény hibás levonása
- **Hibás kód:**
  ```javascript
  if (isVip) {
      fee = fee - 20;
  }
  ```
- **Hiba magyarázata:** A VIP ügyfélnek 20% kedvezmény jár, a kód viszont fix 20 Ft-ot von le a díjból.
- **Javítás:** A díjat 20%-kal kell csökkenteni:
  ```javascript
  if (isVip) {
      fee = fee * 0.8;
  }
  ```

### 4. Napi maximális díj sorrendje
- **Hibás kód:**
  ```javascript
  if (fee > 5000) {
      fee = 5000;
  }
  ```
- **Hiba magyarázata:** A kód a legvégén, a hétvégi 50%-os felezés után vágja le a díjat 5000-re. Hétvégén a normál díj fele fizetendő, vagyis a napi maximum sem lehetne több 2500 Ft-nál. Így ha valaki hétvégén egész nap ott áll (pl. 24 óra = 7200 Ft), a kód 5000 Ft-ot kér tőle 2500 Ft helyett.
- **Javítás:** A napi maximumot az alapdíjra kell alkalmazni a kedvezmények előtt:
  ```javascript
  let hours = Math.ceil(minutes / 60);
  let fee = Math.min(hours * 600, 5000);
  if (isWeekend) fee *= 0.5;
  if (isVip) fee *= 0.8;
  ```

### 5. Negatív parkolási idő kezelése
- **Hibás kód:** Nincs ellenőrzés negatív percekre.
- **Hiba magyarázata:** Negatív parkolási idő nem megengedett. A kód a `minutes < 15` miatt simán visszaad 0-t hibadobás helyett.
- **Javítás:**
  ```javascript
  if (minutes < 0) {
      throw new Error("Negatív parkolási idő nem megengedett!");
  }
  ```

---

## 3. CinemaTicketCalculator

### 1. Kedvezmények összeadódása
- **Hibás kód:**
  ```javascript
  if (age <= 6) {
      price = 0;
  } else if (age < 18) {
      price = price * 0.7;
  } else if (age > 65) {
      price = price * 0.6;
  }

  if (isStudent) {
      price = price * 0.8;
  }
  ```
- **Hiba magyarázata:** A feladat szerint csak a legnagyobb kedvezmény vehető igénybe. A kód viszont az életkori kedvezmény után még a diák kedvezményt is rászámolja. Így egy 16 éves diák 30% helyett 44%-ot kap (`3000 * 0.7 * 0.8 = 1680 Ft`).
- **Javítás:** Meg kell keresni a legnagyobb kedvezményt, és csak azt levonni az alapárból.

### 2. 3D felár számolása
- **Hibás kód:**
  ```javascript
  if (is3D) {
      price = price * 1.8;
  }
  ```
- **Hiba magyarázata:** Fix 800 Ft felár helyett 80%-os szorzót (`* 1.8`) használ. Ráadásul a már kedvezményes jegyárat szorozza fel, így 6 év alatti gyereknél `0 * 1.8 = 0 Ft` marad a 3D felár, pedig erre nem járhat kedvezmény.
- **Javítás:** Fixen hozzá kell adni a 800 Ft-ot:
  ```javascript
  if (is3D) {
      price += 800;
  }
  ```

### 3. 6 éves kor határa
- **Hibás kód:**
  ```javascript
  if (age <= 6) {
      price = 0;
  }
  ```
- **Hiba magyarázata:** Csak 6 év alatt ingyenes a jegy, 6–17 éves korig 30% kedvezmény jár. A `<=` miatt a 6 éves gyerek ingyen kap jegyet 2100 Ft helyett.
- **Javítás:** `<` kell a `<=` helyett:
  ```javascript
  if (age < 6) {
      price = 0;
  }
  ```

### 4. 65 éves határérték
- **Hibás kód:**
  ```javascript
  } else if (age > 65) {
      price = price * 0.6;
  }
  ```
- **Hiba magyarázata:** 65 éves kortól jár a 40%-os nyugdíjas kedvezmény. A szigorú `>` miatt a pont 65 éves vásárló nem kapja meg, teljes árat kell fizetnie.
- **Javítás:** `>=` kell:
  ```javascript
  } else if (age >= 65) {
      price = price * 0.6;
  }
  ```

### 5. Érvénytelen életkor ellenőrzése
- **Hibás kód:** Nincs életkor validáció.
- **Hiba magyarázata:** Negatív életkor és 120 év felett érvénytelennek kell lennie a bemenetnek. A kód negatív számra ingyenes jegyet ad, 120 felett pedig nyugdíjas jegyet ad hiba helyett.
- **Javítás:**
  ```javascript
  if (age < 0 || age > 120) {
      throw new Error("Negatív életkor és 120 év feletti életkor érvénytelen!");
  }
  ```

---

## 4. ExamGradeCalculator

### 1. Bukási feltétel rossz logikai művelete (`&&` vs `||`)
- **Hibás kód:**
  ```javascript
  if (theory < 30 && practice < 20) {
      return 1;
  }
  ```
- **Hiba magyarázata:** A vizsga akkor sikeres, ha elméletből legalább 30 ÉS gyakorlatból legalább 20 pont megvan. Tehát elégtelen jár, ha az elmélet < 30 VAGY a gyakorlat < 20. A kód `&&`-t használ, így ha valaki elméletből 50 pontot szerez, gyakorlatból meg csak 10-et, a kód átengedi 2-essel megbuktatás helyett.
- **Javítás:** `||`-t kell használni:
  ```javascript
  if (theory < 30 || practice < 20) {
      return 1;
  }
  ```

### 2. Jegyhatárok elcsúszása
- **Hibás kód:**
  ```javascript
  if (total < 50) {
      return 1;
  } else if (total <= 60) {
      return 2;
  } else if (total <= 70) {
      return 3;
  } else if (total <= 85) {
      return 4;
  } else {
      return 5;
  }
  ```
- **Hiba magyarázata:** A határértékeken a `<=` miatt eggyel rosszabb jegyet ad a kód:
  - 60 pontnál 2-est ad 3-as helyett (60–69 a hármas).
  - 70 pontnál 3-ast ad 4-es helyett (70–84 a négyes).
  - 85 pontnál 4-est ad 5-ös helyett (85–100 az ötös).
- **Javítás:** Szigorú `<` jelek kellenek a sávokhoz:
  ```javascript
  if (total < 50) {
      return 1;
  } else if (total < 60) {
      return 2;
  } else if (total < 70) {
      return 3;
  } else if (total < 85) {
      return 4;
  } else {
      return 5;
  }
  ```

### 3. Érvénytelen pontszámok kezelése
- **Hibás kód:** Nincs pontszám-ellenőrzés.
- **Hiba magyarázata:** Az elmélet max 60, a gyakorlat max 40 pont lehet, negatív nem lehet, és érvénytelen pontszámra hibát kell jelezni. A kód nem ellenőrzi ezt (pl. 100 és 50 pontra 5-öst ad).
- **Javítás:**
  ```javascript
  if (typeof theory !== "number" || typeof practice !== "number" ||
      theory < 0 || theory > 60 || practice < 0 || practice > 40) {
      throw new Error("Érvénytelen pontszám!");
  }
  ```

---

## 5. PackageClassifier

### 1. INVALID eset hiánya
- **Hibás kód:** Nincs érvénytelen méreteket vizsgáló ág a függvény elején.
- **Hiba magyarázata:** Ha bármely méret vagy tömeg <= 0, akkor "INVALID"-dal kell visszatérni. A kódból ez hiányzik, így negatív adatokra is "SMALL"-t ad.
- **Javítás:**
  ```javascript
  if (weight <= 0 || length <= 0 || width <= 0 || height <= 0) {
      return "INVALID";
  }
  ```

### 2. Small határok egyenlőtlensége
- **Hibás kód:**
  ```javascript
  if (weight < 2 &&
      length < 30 &&
      width < 30 &&
      height < 30) {
      return "SMALL";
  }
  ```
- **Hiba magyarázata:** A leírás szerint „legfeljebb 2 kg” és „mindhárom mérete legfeljebb 30 cm”. A `<` miatt egy pontosan 2 kg-os vagy 30 centis csomag átkerül Mediumba.
- **Javítás:** `<=` kell:
  ```javascript
  if (weight <= 2 &&
      length <= 30 &&
      width <= 30 &&
      height <= 30) {
      return "Small";
  }
  ```

### 3. Medium logikai hiba (`||` vs `&&`)
- **Hibás kód:**
  ```javascript
  if (weight <= 10 &&
      (length <= 60 || width <= 60 || height <= 60)) {
      return "MEDIUM";
  }
  ```
- **Hiba magyarázata:** A szabály: „egyik mérete sem nagyobb 60 cm-nél”, ami azt jelenti, hogy mindegyik méretnek `<= 60`-nak kell lennie. A kódban lévő `||` miatt viszont ha akár csak egyetlen méret <= 60 cm, már Mediumnak veszi (pl. egy 200x200x10 cm-es csomagot is, aminek OVERSIZE-nak kéne lennie).
- **Javítás:** `&&` kapcsolat kell:
  ```javascript
  if (weight <= 10 &&
      length <= 60 &&
      width <= 60 &&
      height <= 60) {
      return "Medium";
  }
  ```

### 4. Visszatérési értékek formátuma
- **Hibás kód:**
  ```javascript
  return "SMALL";
  return "MEDIUM";
  return "LARGE";
  ```
- **Hiba magyarázata:** A feladat szövegében a kategóriák: `Small`, `Medium`, `Large` (míg az OVERSIZE és INVALID csupa nagybetűs). A kód viszont csupa nagybetűvel tér vissza.
- **Javítás:** `"Small"`, `"Medium"`, `"Large"` értékeket kell visszaadni.
