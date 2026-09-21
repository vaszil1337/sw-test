// 3. CinemaTicketCalculator

// Hibás kód a feladatból
function calculateTicketPrice(age, isStudent, is3D) {
    let price = 3000;

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

    if (is3D) {
        price = price * 1.8;
    }

    return price;
}

// Javított változat
function calculateTicketPriceFixed(age, isStudent, is3D) {
    if (age < 0 || age > 120) {
        throw new Error("Negatív életkor és 120 év feletti életkor érvénytelen!");
    }

    const basePrice = 3000;

    // 6 év alatt ingyenes (100% kedvezmény)
    if (age < 6) {
        let price = 0;
        if (is3D) {
            price += 800; // a 3D felárra nem vonatkozik kedvezmény
        }
        return price;
    }

    // Lehetséges kedvezmények összegyűjtése (egyszerre csak a legnagyobb érvényesíthető)
    let maxDiscount = 0;

    if (age >= 6 && age <= 17) {
        maxDiscount = Math.max(maxDiscount, 0.30);
    } else if (age >= 65) {
        maxDiscount = Math.max(maxDiscount, 0.40);
    }

    if (isStudent) {
        maxDiscount = Math.max(maxDiscount, 0.20);
    }

    let ticketPrice = basePrice * (1 - maxDiscount);

    // 3D film esetén 800 Ft felár fizetendő (nem 1.8x szorzó!)
    if (is3D) {
        ticketPrice += 800;
    }

    return ticketPrice;
}

module.exports = {
    calculateTicketPrice,
    calculateTicketPriceFixed
};

if (require.main === module) {
    console.log("=== 3. CinemaTicketCalculator Tesztek ===");
    console.log("Hiba 1 - Kedvezmények halmozódása (16 éves diák):");
    console.log("  Hibás:", calculateTicketPrice(16, true, false), "Ft (3000 * 0.7 * 0.8 = 1680 Ft -> 44% kedvezmény)");
    console.log("  Javított:", calculateTicketPriceFixed(16, true, false), "Ft (Csak a max 30% érvényesül -> 2100 Ft)");

    console.log("Hiba 2 - 3D felár (+800 Ft helyett * 1.8) (25 éves felnőtt, 3D):");
    console.log("  Hibás:", calculateTicketPrice(25, false, true), "Ft (3000 * 1.8 = 5400 Ft)");
    console.log("  Javított:", calculateTicketPriceFixed(25, false, true), "Ft (3000 + 800 = 3800 Ft)");

    console.log("Hiba 3 - 6 éves határérték:");
    console.log("  Hibás:", calculateTicketPrice(6, false, false), "Ft (0 Ft, pedig már fizetős 30% kedvezménnyel)");
    console.log("  Javított:", calculateTicketPriceFixed(6, false, false), "Ft (2100 Ft)");

    console.log("Hiba 4 - 65 éves határérték:");
    console.log("  Hibás:", calculateTicketPrice(65, false, false), "Ft (3000 Ft, kimarad a > 65 miatt a 40%-os kedvezményből)");
    console.log("  Javított:", calculateTicketPriceFixed(65, false, false), "Ft (1800 Ft)");

    console.log("Hiba 5 - Életkor validáció hiánya (-3 év):");
    console.log("  Hibás:", calculateTicketPrice(-3, false, false), "Ft (0 Ft hibadobás helyett)");
}
