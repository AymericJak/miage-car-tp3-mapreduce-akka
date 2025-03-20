package fr.univlille.mastermiage.car.miagecartp3mapreduceakka.akka;

import akka.actor.UntypedActor;

import java.util.HashMap;
import java.util.Map;

public class ReduceActor extends UntypedActor {
    private final Map<String, Integer> wordCounts = new HashMap<>();

    @Override
    public void onReceive(Object message) {
        if (message instanceof String word) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            System.out.println("Reducer " + getSelf().path().name() + " → " + word + " : " + wordCounts.get(word));
        }

    }

    public Map<String, Integer> getWordCounts() {
        return wordCounts;
    }
}
