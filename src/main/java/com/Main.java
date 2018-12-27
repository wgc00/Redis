package com;

import redis.clients.jedis.Jedis;

public class Main {
    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        int i = 0;
        try {
            long startTime = System.currentTimeMillis();
            while (true) {
                if (System.currentTimeMillis() - startTime <=  1000) {
                    break;
                }
            }
            i++;
            jedis.set("test" + i, i + "");
        } finally {
            System.out.println("灭此错周"+ i +"asfa");
        }


    }
}
