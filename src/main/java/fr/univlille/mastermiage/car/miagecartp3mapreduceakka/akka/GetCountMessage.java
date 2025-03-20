package fr.univlille.mastermiage.car.miagecartp3mapreduceakka.akka;

public class GetCountMessage {
    private final String word;

    public GetCountMessage(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
