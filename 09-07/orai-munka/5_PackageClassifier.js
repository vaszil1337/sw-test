// 5. PackageClassifier

// Hibás kód a feladatból
function classifyPackage(weight, length, width, height) {
    if (weight < 2 &&
        length < 30 &&
        width < 30 &&
        height < 30) {
        return "SMALL";
    }

    if (weight <= 10 &&
        (length <= 60 || width <= 60 || height <= 60)) {
        return "MEDIUM";
    }

    if (weight <= 30 &&
        length <= 120 &&
        width <= 120 &&
        height <= 120) {
        return "LARGE";
    }

    return "OVERSIZE";
}

// Javított változat
function classifyPackageFixed(weight, length, width, height) {
    // Érvénytelen a csomag, ha bármely mérete 0 vagy negatív; tömege 0 vagy negatív
    if (weight <= 0 || length <= 0 || width <= 0 || height <= 0) {
        return "INVALID";
    }

    // Small: legfeljebb 2 kg; mindhárom mérete legfeljebb 30 cm
    if (weight <= 2 &&
        length <= 30 &&
        width <= 30 &&
        height <= 30) {
        return "SMALL"; // Vagy specifikáció szerint "Small"
    }

    // Medium: legfeljebb 10 kg; egyik mérete sem nagyobb 60 cm-nél (mindhárom <= 60)
    if (weight <= 10 &&
        length <= 60 &&
        width <= 60 &&
        height <= 60) {
        return "MEDIUM"; // Vagy "Medium"
    }

    // Large: legfeljebb 30 kg; egyik mérete sem nagyobb 120 cm-nél (mindhárom <= 120)
    if (weight <= 30 &&
        length <= 120 &&
        width <= 120 &&
        height <= 120) {
        return "LARGE"; // Vagy "Large"
    }

    return "OVERSIZE";
}

module.exports = {
    classifyPackage,
    classifyPackageFixed
};

if (require.main === module) {
    console.log("=== 5. PackageClassifier Tesztek ===");
    console.log("Hiba 1 - Érvénytelen (INVALID) kezelés hiánya (-2 kg, 10x10x10):");
    console.log("  Hibás:", classifyPackage(-2, 10, 10, 10), "(INVALID helyett SMALL-t ad)");
    console.log("  Javított:", classifyPackageFixed(-2, 10, 10, 10));

    console.log("Hiba 2 - Small határérték ('legfeljebb' vs '<') (2 kg, 30x30x30):");
    console.log("  Hibás:", classifyPackage(2, 30, 30, 30), "(SMALL helyett MEDIUM)");
    console.log("  Javított:", classifyPackageFixed(2, 30, 30, 30));

    console.log("Hiba 3 - Medium méretfeltétel logikai hibája ('||' vs '&&') (5 kg, 200x200x20 cm):");
    console.log("  Hibás:", classifyPackage(5, 200, 200, 20), "(OVERSIZE helyett MEDIUM, mert height <= 60 teljesült)");
    console.log("  Javított:", classifyPackageFixed(5, 200, 200, 20));
}
