package com.game.example.server.stress.testing.action.room;

import com.game.example.basic.logic.game.room.proto.RoomQuitReq;
import com.game.example.basic.logic.game.room.proto.RoomQuitRsp;
import com.game.example.server.stress.testing.action.RobotRequestAction;
import org.qiunet.function.ai.enums.ActionStatus;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.response.TestResponse;
import org.qiunet.game.test.robot.Robot;

// @BehaviorAction(name = "退出房间")
public class RoomQuitAction extends RobotRequestAction {

    public RoomQuitAction(IConditions<Robot> preConditions) {
        super(preConditions);
    }

    @Override
    protected ActionStatus execute() {
        this.sendMessage(new RoomQuitReq());
        return ActionStatus.RUNNING;
    }

    @Override
    protected ActionStatus runningStatusUpdate() {
        return (getRobotData().getRoomInfo().getRoomData() == null) ? ActionStatus.SUCCESS : ActionStatus.RUNNING;
    }

    @TestResponse
    public final void quit(RoomQuitRsp rsp) {
        getRobotData().getRoomInfo().clear();
    }
}
