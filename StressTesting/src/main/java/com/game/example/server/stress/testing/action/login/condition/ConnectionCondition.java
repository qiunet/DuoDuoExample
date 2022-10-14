package com.game.example.server.stress.testing.action.login.condition;

import com.game.example.server.stress.testing.action.RobotRequestCondition;
import com.game.example.server.stress.testing.robot.RobotData;
import org.qiunet.game.test.robot.Robot;

public class ConnectionCondition extends RobotRequestCondition {
    @Override
    protected boolean verify0(Robot robot, RobotData data) {
        return !data.isConnectionSuccess();
    }
}
