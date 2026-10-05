package hu.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TableTest {
    @Test
    void areaIsWidthTimesLength() {
        assertEquals(12000, new Table(100, 120, 75).area());
    }

    @Test
    void capacityIsOnePersonPerSixtyCentimetresOfPerimeter() {
        assertEquals(7, new Table(120, 90, 75).getCapacity());
    }

    @Test
    void adjustableTableAcceptsHeightInRange() {
        Table table = new Table(100, 120, 75, true);

        table.setHeight(150);

        assertEquals(150, table.getCurrentHeight());
    }

    @Test
    void setHeightRejectsNonAdjustableTableAndInvalidValues() {
        Table fixed = new Table(100, 120, 75);
        Table adjustable = new Table(100, 120, 75, true);

        assertThrows(IllegalStateException.class, () -> fixed.setHeight(100));
        assertThrows(IllegalArgumentException.class, () -> adjustable.setHeight(-1));
        assertThrows(IllegalArgumentException.class, () -> adjustable.setHeight(201));
    }

    @Test
    void extraTableOperationsWork() {
        Table table = new Table(100, 120, 75, true);
        table.setNumberOfLegs(4);
        table.repaint("blue");

        assertEquals("blue", table.getColor());
        assertTrue(table.isStable());
        assertTrue(table.isFoldable());
        assertEquals(440, table.getPerimeter());
    }

    @Test
    void threeLegTableIsStableButNotFoldableWhenNotAdjustable() {
        Table table = new Table(100, 120, 75);
        table.setNumberOfLegs(3);

        assertTrue(table.isStable());
        assertFalse(table.isFoldable());
    }
}
