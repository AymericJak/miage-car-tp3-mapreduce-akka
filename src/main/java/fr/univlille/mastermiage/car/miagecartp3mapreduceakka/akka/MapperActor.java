package fr.univlille.mastermiage.car.miagecartp3mapreduceakka.akka;

import akka.actor.UntypedActor;

public class MapperActor extends UntypedActor {

    @Override
    public void onReceive(Object message) {
        if (message instanceof String line) {
            System.out.println("Mapper " + getSelf().path().name() + " traite : " + line);
        }
    }
}
