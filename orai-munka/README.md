# Órai munka - hibakeresés

## 1. DiscountCalculator
- **Rossz a végső ár számítása:** `return orderValue - discount`. A `discount` egy arány (pl. 0.05), a kód viszont simán kivonja ezt a számból ahelyett, hogy levonná a százalékot (`orderValue * (1 - discount)`). Emiatt nem 5%-ot, hanem fixen 0.05 Ft-ot von le.
- **VIP kedvezmény elrontva:** `discount += 5`-öt ad hozzá `0.05` helyett. Ezután a `discount` értéke 5 felett lesz, amit a rákövetkező if egyből levág 0.20-ra. Így egy VIP vásárló minden esetben 20% kedvezményt kap, még 10 ezer Ft alatti vásárlásnál is, ahol csak 5% járna.
- **10 000 Ft-os határ:** `orderValue > 10000` van beírva `>=` helyett. A leírás szerint 10 ezertől jár a kedvezmény, így pont 10 000 Ft-nál 0% kedvezményt kap.
- **50 000 Ft-os sáv:** a kód a `<=` miatt az 50 ezret a 10%-os ágba teszi (`orderValue <= 50000`), miközben 50 ezer Ft-tól már 15% járna.
- **Nincs ellenőrzés negatív értékekre:** a specifikáció tiltja a negatív rendelési értéket, a kód viszont nem dob hibát és nem kezeli.

## 2. ParkingFeeCalculator
- **Megkezdett óra számítása lefelé kerekít:** `Math.floor(minutes / 60)` van a kódban, ami lefelé kerekít, pedig minden megkezdett óra fizetős, szóval felfelé kéne (`Math.ceil`). Emiatt 15 és 59 perc között `Math.floor` = 0 órát számol, és 0 Ft lesz a parkolás 600 Ft helyett.
- **15 perces határ:** `minutes < 15` van benne. Az első 15 perc ingyenes, tehát pontosan 15 percnél is 0 Ft kellene legyen (`minutes <= 15`).
- **VIP kedvezmény elszámolása:** `fee = fee - 20`, 20% kedvezmény helyett fix 20 forintot von le a díjból (`fee * 0.8` kéne).
- **Napi maximális díj elhelyezése:** a legvégén van a levágás 5000-re, a kedvezmények után. Hétvégén a napi díj felét kéne fizetni (max 2500 Ft), de ha valaki sokat parkol, a kód a kedvezményes árat vágja le 5000-nél.
- **Negatív parkolási idő:** nincs rá semmilyen ellenőrzés vagy hiba, simán visszaad 0-t.

## 3. CinemaTicketCalculator
- **Kedvezmények halmozódnak:** a kód az életkori kedvezmény után külön még rászorozza a diákot is (`price = price * 0.8`). Így pl. egy 16 éves diák 30% helyett 44% kedvezményt kap, pedig a szabály szerint csak a legnagyobb kedvezményt kaphatná meg.
- **3D felár rosszul van számolva:** fix 800 Ft helyett `price * 1.8`-at csinál, ami 80%-os drágítás. Ráadásul a kedvezményes jegyárat szorozza, emiatt egy 6 év alatti gyereknél (0 Ft jegy) 0 Ft marad a 3D ára is, pedig a specifikáció szerint a 3D felárra nem vonatkozik semmilyen kedvezmény.
- **6 éves határérték:** `age <= 6` miatt egy 6 éves gyerek ingyen kap jegyet, pedig 6 éves kortól már 30%-os jegyet kéne fizetnie (`age < 6` kéne).
- **65 éves határérték:** `age > 65` miatt a pontosan 65 éves ember nem kapja meg a 40%-os nyugdíjas kedvezményt, csak teljes árat (`age >= 65` kellene).
- **Életkor validáció hiánya:** negatív életkorra ingyenes jegyet ad, 120 év felettire meg nyugdíjas jegyet hibajelzés helyett.

## 4. ExamGradeCalculator
- **Bukási feltétel rossz logikai operátora:** `theory < 30 && practice < 20`. `&&` van `||` helyett, ezért csak akkor bukik meg a diák, ha mindkét részből elbukott. Ha elméletből elért 50 pontot, gyakorlatból meg csak 10-et, a kód átengedi a vizsgán megbuktatás helyett.
- **Jegyek határai el vannak csúszva:** a `<=` miatt:
  - `total <= 60`-ra 2-est ad, miközben 60 ponttól már 3-as jár.
  - `total <= 70`-re 3-ast ad, miközben 70 ponttól már 4-es jár.
  - `total <= 85`-re 4-est ad, miközben 85 ponttól már 5-ös jár.
- **Érvénytelen pontszámok kezelése hiányzik:** a specifikáció szerint elmélet max 60, gyakorlat max 40 pont lehet, és nem lehet negatív sem. A kód ezt egyáltalán nem nézi, és nem jelez semmilyen hibát érvénytelen pontoknál.

## 5. PackageClassifier
- **INVALID eset teljesen hiányzik:** ha bármelyik méret vagy súly 0 vagy negatív, INVALID-nak kéne lennie. A kódban nincs ilyen ág, negatív értékekre simán "SMALL"-t ad vissza.
- **Small határértékek:** legfeljebb 2 kg és legfeljebb 30 cm a leírás, a kódban viszont `<` van `<= ` helyett. emiatt a pontosan 2 kg-os vagy 30 centis csomag átcsúszik Mediumba.
- **Medium méretek feltétele:** `(length <= 60 || width <= 60 || height <= 60)` van benne `||`-lal. Így elég, ha csak az egyik mérete kisebb vagy egyenlő 60 cm, és már Mediumnak minősíti (pl. egy 200x200x10 centis csomagot is), miközben mindhárom méretének legfeljebb 60-nak kéne lennie (`&&`).
- **Nagybetűs visszatérési értékek:** a kód "SMALL", "MEDIUM", "LARGE" stringekkel tér vissza a leírásban szereplő "Small", "Medium", "Large" helyett.
