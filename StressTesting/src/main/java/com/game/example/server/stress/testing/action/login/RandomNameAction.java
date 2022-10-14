package com.game.example.server.stress.testing.action.login;

import com.game.example.basic.logic.player.enums.GenderType;
import com.game.example.basic.logic.player.proto.RandomNameReq;
import com.game.example.basic.logic.player.proto.RandomNameRsp;
import com.game.example.server.stress.testing.action.RobotRequestAction;
import org.qiunet.function.ai.enums.ActionStatus;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.response.TestResponse;
import org.qiunet.game.test.robot.Robot;

// @BehaviorAction(name = "随机名字")
public class RandomNameAction extends RobotRequestAction {

    public RandomNameAction(IConditions<Robot> preConditions) {
        super(preConditions);
    }

    @Override
    protected ActionStatus execute() {
        RandomNameReq randomNameReq = new RandomNameReq();
        randomNameReq.setGender(GenderType.MALE);
        sendMessage(randomNameReq);
        return ActionStatus.RUNNING;
    }

    @Override
    protected ActionStatus runningStatusUpdate() {
        return (getRobotData().getRandomNameRsp() != null) ? ActionStatus.SUCCESS : ActionStatus.RUNNING;
    }

    @TestResponse
    public final void randomNameRsp(RandomNameRsp randomNameRsp) {
        getRobotData().setRandomNameRsp(randomNameRsp);
    }

}
