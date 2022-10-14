package com.game.example.server.stress.testing.action;

import com.game.example.server.stress.testing.robot.RobotData;
import org.qiunet.flash.handler.context.status.StatusResult;
import org.qiunet.game.test.robot.Robot;
import org.qiunet.game.test.robot.condition.RobotCondition;

public abstract class RobotRequestCondition extends RobotCondition {
    @Override
    public StatusResult verify(Robot robot) {
        return verify0(robot, RobotData.get(robot)) ? StatusResult.SUCCESS : StatusResult.FAIL;
    }

    protected abstract boolean verify0(Robot robot, RobotData data);
}
