// 2. ParkingFeeCalculator

// Hibás kód a feladatból
function calculateParkingFee(minutes, isWeekend, isVip) {
    if (minutes < 15) {
        return 0;
    }

    let hours = Math.floor(minutes / 60);
    let fee = hours * 600;

    if (isWeekend) {
        fee = fee * 0.5;
    }

    if (isVip) {
        fee = fee - 20;
    }

    if (fee > 5000) {
        fee = 5000;
    }

    return fee;
}

// Javított változat
function calculateParkingFeeFixed(minutes, isWeekend, isVip) {
    if (minutes < 0) {
        throw new Error("Negatív parkolási idő nem megengedett!");
    }

    // Az első 15 perc ingyenes (15 percnél még 0 Ft)
    if (minutes <= 15) {
        return 0;
    }

    // 15 perc felett minden megkezdett óra
    let hours = Math.ceil(minutes / 60);
    let baseFee = hours * 600;

    // Napi maximális díj 5000 Ft
    if (baseFee > 5000) {
        baseFee = 5000;
    }

    let fee = baseFee;

    // Hétvégi 50% kedvezmény
    if (isWeekend) {
        fee = fee * 0.5;
    }

    // VIP további 20% kedvezmény (nem -20 Ft!)
    if (isVip) {
        fee = fee * 0.8;
    }

    return fee;
}

module.exports = {
    calculateParkingFee,
    calculateParkingFeeFixed
};

if (require.main === module) {
    console.log("=== 2. ParkingFeeCalculator Tesztek ===");
    console.log("Hiba 1 - Megkezdett óra kerekítése (30 perc, hétköznap):");
    console.log("  Hibás:", calculateParkingFee(30, false, false), "Ft (0 Ft-ot ad, mert Math.floor(30/60)=0)");
    console.log("  Javított:", calculateParkingFeeFixed(30, false, false), "Ft (Elvárt: 600 Ft)");

    console.log("Hiba 2 - Pontosan 15 perces határérték:");
    console.log("  Hibás logikában 'minutes < 15', ha a kerekítés javítva lenne, 15 percre számlázna.");

    console.log("Hiba 3 - VIP kedvezmény (-20 Ft vs 20%) (120 perc = 1200 Ft, hétköznap, VIP):");
    console.log("  Hibás:", calculateParkingFee(120, false, true), "Ft (1200 - 20 = 1180 Ft)");
    console.log("  Javított:", calculateParkingFeeFixed(120, false, true), "Ft (1200 * 0.8 = 960 Ft)");

    console.log("Hiba 4 - Negatív idő kezelése (-10 perc):");
    console.log("  Hibás:", calculateParkingFee(-10, false, false), "Ft (Nem dob hibát)");
}
