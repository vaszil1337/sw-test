// 1. DiscountCalculator

// Hibás kód a feladatból
function calculateDiscountedPrice(orderValue, isVip) {
    let discount = 0;
    if (orderValue > 10000 && orderValue < 25000) {
        discount = 0.05;
    } else if (orderValue >= 25000 && orderValue <= 50000) {
        discount = 0.10;
    } else if (orderValue > 50000) {
        discount = 0.15;
    }
    if (isVip === true) {
        discount += 5;
    }

    if (discount > 0.20) {
        discount = 0.20;
    }

    return orderValue - discount;
}

// Javított változat
function calculateDiscountedPriceFixed(orderValue, isVip) {
    if (orderValue < 0) {
        throw new Error("Negatív rendelési érték nem megengedett!");
    }

    let discount = 0;
    if (orderValue >= 10000 && orderValue < 25000) {
        discount = 0.05;
    } else if (orderValue >= 25000 && orderValue < 50000) {
        discount = 0.10;
    } else if (orderValue >= 50000) {
        discount = 0.15;
    }

    if (isVip === true) {
        discount += 0.05; // 5 százalékpont = 0.05
    }

    if (discount > 0.20) {
        discount = 0.20;
    }

    return orderValue * (1 - discount);
}

module.exports = {
    calculateDiscountedPrice,
    calculateDiscountedPriceFixed
};

if (require.main === module) {
    console.log("=== 1. DiscountCalculator Tesztek ===");
    console.log("Hiba 1 - Végső ár levonás (20 000 Ft, nem VIP):");
    console.log("  Hibás:", calculateDiscountedPrice(20000, false), "Ft (Elvárt: 19 000 Ft)");
    console.log("  Javított:", calculateDiscountedPriceFixed(20000, false), "Ft");

    console.log("Hiba 2 - VIP kedvezmény mértéke (+5 vs +0.05) (5 000 Ft, VIP):");
    console.log("  Hibás:", calculateDiscountedPrice(5000, true), "Ft (20% kedvezményt vont le 5% helyett)");
    console.log("  Javított:", calculateDiscountedPriceFixed(5000, true), "Ft");

    console.log("Hiba 3 - 10 000 Ft alsó határérték:");
    console.log("  Hibás:", calculateDiscountedPrice(10000, false), "Ft (0% kedvezmény, pedig 5% járna)");
    console.log("  Javított:", calculateDiscountedPriceFixed(10000, false), "Ft");

    console.log("Hiba 4 - 50 000 Ft felső sávhatár:");
    console.log("  Hibás kedvezményarány:", 50000 - calculateDiscountedPrice(50000, false) > 0 ? "10% sávba esett" : "egyéb");
    console.log("  Javított:", calculateDiscountedPriceFixed(50000, false), "Ft (15% levonva -> 42 500 Ft)");
}
