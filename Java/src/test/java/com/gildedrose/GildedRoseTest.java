package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GildedRoseTest {
    private GildedRose store;

    private Item item;

    private void advanceNDays( int days ) {
        for ( int i = 0; i < days; i++ ) {
            store.updateQuality();
        }
    }

    private void createStore() {
        Item[] items = new Item[] { item };
        store = new GildedRose( items );
    }

    @Nested
    class SingleRegularItem {

        @BeforeEach
        void setUp() {
            item = new Item( "foo", 5, 10 );
            createStore();
        }

        @Test
        void decreasesQualityOnePerDay() {
            advanceNDays( 1 );
            assertEquals( 9, item.quality );
        }

        @Test
        void decreasesSellInOnePerDay() {
            advanceNDays( 1 );
            assertEquals( 4, item.sellIn );
        }

        @Test
        void qualityNeverGoesBelowZero() {
            advanceNDays( 11 );
            assertEquals( 0, item.quality );
        }
        @Test
        void qualityDegradesTwiceAsFastAfterSellDateIsPast() {
            advanceNDays( 5 );
            assertEquals( 5, item.quality );
            advanceNDays( 1 );
            assertEquals( 3, item.quality );
        }

    }

    @Nested
    class AgedBrie {

        @BeforeEach
        void setUp() {
            item = new Item("Aged Brie", 2, 0);
            createStore();
        }

        @Test
        void increasesQualityOnePerDay() {
            advanceNDays( 1 );
            assertEquals( 1, item.quality );
        }

        @Test
        void increasesQualityTwiceAsFastAfterSellDate() {
            advanceNDays( 3 );
            assertEquals( 4, item.quality );
        }

        @Test
        void increasesOnlyUntilMax() {
            advanceNDays( 55 );
            assertEquals( 50, item.quality );
        }
    }

    @Test
    void sulfurasNeverDecreasesQuality() {
        item = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        createStore();
        advanceNDays( 10 );
        assertEquals( 80, item.quality );
    }

    @Test
    void sulfurasNeverHasToBeSold() {
        item = new Item("Sulfuras, Hand of Ragnaros", -1, 80);
        createStore();
        advanceNDays( 10 );
        assertEquals( -1, item.sellIn );
    }

    @Test
    void conjuredDecreasesQualityTwoPerDay() {
        item = new Item("Conjured Battle Axe", 10, 20);
        createStore();
        advanceNDays( 1 );
        assertEquals( 18, item.quality );
    }

    @Nested
    class BackstagePasses {

        @BeforeEach
        void setUp() {
            item = new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20);
            createStore();
        }

        @Test
        void haveZeroQualityAfterConcert() {
            advanceNDays( 12 );
            assertEquals( 0, item.quality );
        }

        @Test
        void increaseQualityOnePerDayWhenConcertIsMoreThenTenDaysAway() {
            advanceNDays( 1 );
            assertEquals( 21, item.quality );
        }

        @Test
        void increasesInQualityTwoPerDayWhenConcertIsLessThanTenDaysAway() {
            advanceNDays( 2 );
            assertEquals( 23, item.quality );
        }

        @Test
        void increasesInQualityThreePerDayWhenConcertIsLessThanFiveDaysAway() {
            advanceNDays( 7 );
            assertEquals( 34, item.quality );
        }
    }
}
