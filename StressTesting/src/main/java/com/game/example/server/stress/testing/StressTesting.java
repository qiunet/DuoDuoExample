package com.game.example.server.stress.testing;

import kotlin.jvm.internal.Intrinsics;
import org.qiunet.game.test.server.StressTestingServer;

public class StressTesting {

    public static void main(String[] args) {
        String cmd = "start";
        if (args.length > 0) {
            cmd = args[0];
        }
        if (Intrinsics.areEqual(cmd, "start")) {
            StressTestingServer.scanner("com.game.example").startup();
            return;
        }
        if (Intrinsics.areEqual(cmd, "add")) {
            int count = Integer.parseInt(args[1]);
            String msg = StressTestingServer.ADD_ROBOT + " " + count;
            StressTestingServer.sendHookMsg(msg);
            return;
        }
        if (Intrinsics.areEqual(cmd, "stop"))
            StressTestingServer.sendHookMsg(StressTestingServer.STOP);
    }
}
