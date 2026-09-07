// Szallitasi dij tesztek futtatasa
const { calculateShippingCost, calculateShippingCostFixed } = require('./calculateShippingCost');

// Tesztesetek a specifikacio es a hatarertekek alapjan
const tests = [
    {
        id: "TC-01",
        nev: "Alap eset belfoldre, 5 kg alatt",
        orderValue: 8000,
        weight: 3,
        country: "HU",
        express: false,
        elvart: 1500
    },
    {
        id: "TC-02",
        nev: "Hatarertek: pontosan 5 kg (5-tol 20-ig savba kene esnie)",
        orderValue: 12000,
        weight: 5,
        country: "HU",
        express: false,
        elvart: 2500
    },
    {
        id: "TC-03",
        nev: "Kozepes suly belfoldre (5 es 20 kg kozott)",
        orderValue: 10000,
        weight: 12,
        country: "HU",
        express: false,
        elvart: 2500
    },
    {
        id: "TC-04",
        nev: "Hatarertek: pontosan 20 kg (meg a 2500 Ft-os sav)",
        orderValue: 15000,
        weight: 20,
        country: "HU",
        express: false,
        elvart: 2500
    },
    {
        id: "TC-05",
        nev: "Nehez csomag (20 kg felett)",
        orderValue: 10000,
        weight: 25,
        country: "HU",
        express: false,
        elvart: 5000
    },
    {
        id: "TC-06",
        nev: "Ingyenes szallitas HU 20 000 Ft felett",
        orderValue: 25000,
        weight: 10,
        country: "HU",
        express: false,
        elvart: 0
    },
    {
        id: "TC-07",
        nev: "Kisbetus orszagkod ingyenes szallitasnal ('hu')",
        orderValue: 25000,
        weight: 8,
        country: "hu",
        express: false,
        elvart: 0
    },
    {
        id: "TC-08",
        nev: "Kisbetus orszagkod normal szallitasnal ('hu')",
        orderValue: 10000,
        weight: 3,
        country: "hu",
        express: false,
        elvart: 1500
    },
    {
        id: "TC-09",
        nev: "Kulfoldi szallitas (dupla dij)",
        orderValue: 10000,
        weight: 3,
        country: "DE",
        express: false,
        elvart: 3000 // 1500 * 2
    },
    {
        id: "TC-10",
        nev: "Kulfoldre nincs ingyenes szallitas 20k felett sem",
        orderValue: 35000,
        weight: 4,
        country: "DE",
        express: false,
        elvart: 3000
    },
    {
        id: "TC-11",
        nev: "Expressz szallitas (+50% felar)",
        orderValue: 10000,
        weight: 4,
        country: "HU",
        express: true,
        elvart: 2250 // 1500 * 1.5
    },
    {
        id: "TC-12",
        nev: "Kulfold + expressz egyben",
        orderValue: 10000,
        weight: 10,
        country: "AT",
        express: true,
        elvart: 7500 // (2500 * 2) * 1.5
    },
    {
        id: "TC-13",
        nev: "Ervenytelen negativ tomeg",
        orderValue: 10000,
        weight: -2,
        country: "HU",
        express: false,
        elvart: "HIBA"
    },
    {
        id: "TC-14",
        nev: "Ervenytelen negativ rendelesi ertek",
        orderValue: -2000,
        weight: 4,
        country: "HU",
        express: false,
        elvart: "HIBA"
    },
    {
        id: "TC-15",
        nev: "Hatarertek: pontosan 20 000 Ft rendelesi ertek (spec: 20k felett ingyenes)",
        orderValue: 20000,
        weight: 3,
        country: "HU",
        express: false,
        elvart: 1500
    }
];

console.log("--- Eredeti fuggveny tesztelese (calculateShippingCost) ---\n");

let hibasDb = 0;
let sikeresDb = 0;

for (const t of tests) {
    let kapott;
    let dobottHibát = false;

    try {
        kapott = calculateShippingCost(t.orderValue, t.weight, t.country, t.express);
    } catch (err) {
        dobottHibát = true;
        kapott = "HIBA";
    }

    const sikeres = (t.elvart === "HIBA") ? dobottHibát : (kapott === t.elvart);

    if (sikeres) {
        sikeresDb++;
        console.log(`[PASS] ${t.id}: ${t.nev}`);
    } else {
        hibasDb++;
        console.log(`[FAIL] ${t.id}: ${t.nev}`);
        console.log(`       Bemenet: orderValue=${t.orderValue}, weight=${t.weight}, country="${t.country}", express=${t.express}`);
        console.log(`       Elvárt: ${t.elvart} | Kapott: ${kapott}`);
    }
}

console.log(`\nOsszesen: ${tests.length} teszt | Sikeres: ${sikeresDb} | Hibas: ${hibasDb}`);
