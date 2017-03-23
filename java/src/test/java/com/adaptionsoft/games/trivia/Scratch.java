package com.adaptionsoft.games.trivia;

import org.junit.Test;

import java.util.Random;

public class Scratch {

    @Test
    public void seed_for_tests(){
        Random random = new Random(1L);
        for(int i=0; i<10; i++){
            System.out.println(random.nextInt(10));
        }
    }
}
