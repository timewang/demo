/**
 *
 */

import org.springframework.boot.SpringApplication;

/**
 * ***********************************************************
 *
 * @类名 ：Main.java
 * @DESCRIPTION :
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/10/31
 * ***********************************************************
 */
public class Main {

    public static void main(String [] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(
                BatchConfiguration.class, args)));
    }

}
