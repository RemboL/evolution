package pl.rembol.jme3.evolution.deck.json;

import java.util.List;

public class CardDTO {

    private List<TraitDTO> traits;

    private int count = 1;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<TraitDTO> getTraits() {
        return traits;
    }

    public void setTraits(List<TraitDTO> traits) {
        this.traits = traits;
    }
}
