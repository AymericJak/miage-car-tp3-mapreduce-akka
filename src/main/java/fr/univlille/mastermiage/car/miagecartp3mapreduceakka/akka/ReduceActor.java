package fr.univlille.mastermiage.car.miagecartp3mapreduceakka.akka;

import akka.actor.UntypedActor;

public class ReduceActor extends UntypedActor {
    @Override
    public void onReceive(Object message) {
        System.out.println("Mapper reçu : " + message);
    }
}
