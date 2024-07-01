package com.threec.docker.pull;


import com.threec.docker.pull.core.DockerPull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * @author Fetters
 */
public class CommandStartup {

    static Logger log = Logger.getLogger(CommandStartup.class.getName());

    public static void main(String[] args)
            throws URISyntaxException, IOException, InterruptedException {
        String[] strings = new String[3];
        strings[0] = "maven:3.9.7-sapmachine-17";
        strings[1] = "127.0.0.1";
        strings[2] = "7890";
        DockerPull.pull(strings[0], strings[1], Integer.parseInt(strings[2]));
    }

}