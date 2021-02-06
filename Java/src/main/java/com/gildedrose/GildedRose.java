package com.gildedrose;

class GildedRose {
    private static final String PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED = "Conjured Battle Axe";
    private static final int MAX_QUALITY = 50;
    private static final int MIN_SELLIN = 0;
    private static final int MIN_QUALITY = 0;

    Item[] items;

    public GildedRose( Item[] items ) {
        this.items = items;
    }

    public void updateQuality() {
        for ( Item item : items ) {
            switch ( item.name ) {
                case SULFURAS:
                    break;
                case BRIE:
                    adjustBrie( item );
                    break;
                case PASSES:
                    adjustPasses( item );
                    break;
                case CONJURED:
                    adjustConjured( item );
                    break;
                default:
                    adjustDefaultItems( item );
                    break;
            }
        }
    }

    private void adjustConjured( Item item ) {
        decreaseQuality( item );
        adjustDefaultItems( item );
    }

    private void adjustDefaultItems( Item item ) {
        decreaseSellIn( item );
        decreaseQuality( item );
        if ( item.sellIn < MIN_SELLIN ) {
            decreaseQuality( item );
        }
    }

    private void adjustPasses( Item item ) {
        decreaseSellIn( item );
        increaseQuality( item );
        if ( item.sellIn < 10 ) {
            increaseQuality( item );
        }

        if ( item.sellIn < 5 ) {
            increaseQuality( item );
        }

        if ( item.sellIn < MIN_SELLIN ) {
            item.quality = MIN_QUALITY;
        }
    }

    private void adjustBrie( Item item ) {
        decreaseSellIn( item );
        increaseQuality( item );
        if ( item.sellIn < MIN_SELLIN ) {
            increaseQuality( item );
        }
    }

    private void decreaseSellIn( Item item ) {
        --item.sellIn;
    }

    private void decreaseQuality( Item item ) {
        item.quality = Math.max( MIN_QUALITY, item.quality - 1 );
    }

    private void increaseQuality( Item item ) {
        item.quality = Math.min( MAX_QUALITY, item.quality + 1 );
    }
}
