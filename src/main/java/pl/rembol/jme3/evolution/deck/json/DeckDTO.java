package pl.rembol.jme3.evolution.deck.json;

import java.util.List;

public class DeckDTO {

    private List<CardDTO> cards;

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }
}
