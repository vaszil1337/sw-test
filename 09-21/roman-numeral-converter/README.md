# Arab szám → római szám konverter

Arab számok (1–3999) római számmá alakítása JavaScriptben, TDD megközelítéssel és QUnit tesztkeretrendszerrel.

## Futtatás és tesztelés

A projekt nem igényel külön telepítést (a QUnit CDN-ről töltődik be). Futtatásához elegendő az `index.html` fájl megnyitása egy tetszőleges webböngészőben.

Az oldalon elérhető funkciók:
- **Automatikus tesztek:** Az oldal betöltésekor a fejlécben automatikusan lefutnak a QUnit tesztek (alapszámok, kivonásos esetek mint a IV és IX, összetett számok, valamint a határértékek és érvénytelen bemenetek ellenőrzése).
- **Manuális tesztelés:** A tesztek alatti beviteli mezőben közvetlenül is kipróbálható a konverzió. Érvénytelen bemenet esetén (pl. 0, negatív szám, tört vagy 3999 feletti érték) a felület hibaüzenetet jelenít meg.
