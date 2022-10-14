package com.game.example.server.stress.testing.action.login;

import com.game.example.basic.logic.player.proto.RegisterReq;
import com.game.example.basic.logic.player.proto.RegisterRsp;
import com.game.example.server.stress.testing.action.RobotRequestAction;
import org.jetbrains.annotations.NotNull;
import org.qiunet.function.ai.enums.ActionStatus;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.response.TestResponse;
import org.qiunet.game.test.robot.Robot;

// @BehaviorAction(name = "注册")
public class RegisterAction extends RobotRequestAction {

    public RegisterAction(IConditions<Robot> preConditions) {
        super(preConditions);
    }

    @Override
    protected ActionStatus execute() {
        RegisterReq registerReq = new RegisterReq();
        getRobotData().getRandomNameRsp().setName(getRobotData().getRandomNameRsp().getName());
        sendMessage(registerReq);
        return ActionStatus.RUNNING;
    }

    @Override
    protected ActionStatus runningStatusUpdate() {
        return (getRobotData().getRegisterRsp() != null) ? ActionStatus.SUCCESS : ActionStatus.RUNNING;
    }

    @TestResponse
    public final void registerRsp(@NotNull RegisterRsp registerRsp) {
        getRobotData().setRegisterRsp(registerRsp);
    }

}
