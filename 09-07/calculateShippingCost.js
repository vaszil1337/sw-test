function calculateShippingCost(orderValue, weight, country, express) {
    let shippingCost = 0;
    // Free shipping in Hungary for orders above 20,000 HUF
    if (country === "HU" && orderValue >= 20000) {
        return 0;
    }
    // Base shipping cost by weight
    if (weight < 5) {
        shippingCost = 1500;
    } else if (weight > 5 && weight < 20) {
        shippingCost = 2500;
    } else {
        shippingCost = 5000;
    }
    // International shipping
    if (country !== "HU") {
        shippingCost = shippingCost * 2;
    }
    // Express delivery surcharge
    if (express === true) {
        shippingCost = shippingCost + 50;
    }

    return shippingCost;
}

// Javitott verzio a megtalalt hibak alapjan
function calculateShippingCostFixed(orderValue, weight, country, express) {
    // Negativ ertekek nem engedelyezettek a specifikacio szerint
    if (orderValue < 0 || weight < 0) {
        throw new Error("A rendelési érték és a tömeg nem lehet negatív!");
    }

    // Az orszagkod case-insensitive (kis es nagybetu is johet)
    const normalizedCountry = country ? country.toUpperCase() : "";

    // Ingyenes szallitas HU eseten 20000 Ft felett
    if (normalizedCountry === "HU" && orderValue > 20000) {
        return 0;
    }

    let shippingCost = 0;

    // Sulyhatarok javitasa: 5-tol 20-ig zart intervallum
    if (weight < 5) {
        shippingCost = 1500;
    } else if (weight <= 20) {
        shippingCost = 2500;
    } else {
        shippingCost = 5000;
    }

    // Kulfoldre dupla szallitasi dij
    if (normalizedCountry !== "HU") {
        shippingCost = shippingCost * 2;
    }

    // Expressz felar +50% (nem +50 Ft!)
    if (express === true) {
        shippingCost = shippingCost * 1.5;
    }

    return shippingCost;
}

module.exports = {
    calculateShippingCost,
    calculateShippingCostFixed
};
