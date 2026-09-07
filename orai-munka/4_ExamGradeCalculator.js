// 4. ExamGradeCalculator

// Hibás kód a feladatból
function calculateGrade(theory, practice) {
    let total = theory + practice;

    if (theory < 30 && practice < 20) {
        return 1;
    }

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
}

// Javított változat
function calculateGradeFixed(theory, practice) {
    // Érvénytelen pontszámok validációja
    if (
        typeof theory !== "number" || typeof practice !== "number" ||
        theory < 0 || theory > 60 ||
        practice < 0 || practice > 40
    ) {
        throw new Error("Érvénytelen pontszám! (Elmélet: 0-60, Gyakorlat: 0-40)");
    }

    // A vizsga csak akkor sikeres, ha mindkét rész eléri a minimumot
    if (theory < 30 || practice < 20) {
        return 1;
    }

    let total = theory + practice;

    // Sávok határai
    if (total < 50) {
        return 1;
    } else if (total < 60) {      // 50–59
        return 2;
    } else if (total < 70) {      // 60–69
        return 3;
    } else if (total < 85) {      // 70–84
        return 4;
    } else {                      // 85–100
        return 5;
    }
}

module.exports = {
    calculateGrade,
    calculateGradeFixed
};

if (require.main === module) {
    console.log("=== 4. ExamGradeCalculator Tesztek ===");
    console.log("Hiba 1 - Részvizsga minimum logika ('&&' vs '||') (elmélet = 50, gyakorlat = 10, total = 60):");
    console.log("  Hibás jegy:", calculateGrade(50, 10), "(Átengedi 3-assal, holott a gyakorlat miatt buknia kéne!)");
    console.log("  Javított jegy:", calculateGradeFixed(50, 10), "(Sikertelen: 1)");

    console.log("Hiba 2 - Osztályzat határértékek ('<=' miatti elcsúszás) (total = 60: elmélet = 35, gyakorlat = 25):");
    console.log("  Hibás jegy:", calculateGrade(35, 25), "(2-est ad 3-as helyett a <= 60 miatt)");
    console.log("  Javított jegy:", calculateGradeFixed(35, 25), "(Elvárt: 3)");

    console.log("Hiba 2b - Határérték total = 85 (elmélet = 55, gyakorlat = 30):");
    console.log("  Hibás jegy:", calculateGrade(55, 30), "(4-est ad 5-ös helyett a <= 85 miatt)");
    console.log("  Javított jegy:", calculateGradeFixed(55, 30), "(Elvárt: 5)");

    console.log("Hiba 3 - Érvénytelen pontszám validáció hiánya (elmélet = 90, gyakorlat = 50):");
    console.log("  Hibás jegy:", calculateGrade(90, 50), "(Hibajelzés helyett 5-öst ad)");
}
