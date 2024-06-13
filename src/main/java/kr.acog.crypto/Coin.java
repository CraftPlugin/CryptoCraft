package kr.acog.crypto;

import java.text.DecimalFormat;

/**
 * Create by. Acog
 */
public record Coin(String symbol, String name, double price, double tradeValue) {

    private static final DecimalFormat df = new DecimalFormat("#,###.########");

    public String getPrice() {
        return df.format(price);
    }

    public String getTradeValue() {
        return df.format(tradeValue);
    }

    public Coin withPrice(double price) {
        return new Coin(symbol, name, price, tradeValue);
    }

    public Coin withTradeValue(double tradeValue) {
        return new Coin(symbol, name, price, this.tradeValue + tradeValue);
    }

    @Override
    public String toString() {
        return "Coin{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", price=" + getPrice() +
                ", tradeValue=" + getTradeValue() +
                '}';
    }

}
