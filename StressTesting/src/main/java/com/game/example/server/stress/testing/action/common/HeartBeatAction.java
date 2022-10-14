package com.game.example.server.stress.testing.action.common;

import com.game.example.server.stress.testing.action.RobotRequestAction;
import org.qiunet.function.ai.enums.ActionStatus;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.robot.Robot;

// @BehaviorAction(name="心跳")
public class HeartBeatAction extends RobotRequestAction {

    public HeartBeatAction(IConditions<Robot> preConditions) {
        super(preConditions);
    }

    @Override
    protected ActionStatus execute() {
        // 发起心跳
        // this.sendMessage(new HeartbeatReq());
        return ActionStatus.SUCCESS;
    }
}
