package org.o2s.app;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class O2sEngineApp {

    public static void main(String[] args) {
        Quarkus.run(args);
    }

}
