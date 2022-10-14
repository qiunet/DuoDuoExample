package com.game.example.server.stress.testing.action.room;

import com.game.example.basic.logic.game.room.proto.JoinPublicRoomReq;
import com.game.example.basic.logic.game.room.proto.JoinRoomFailRsp;
import com.game.example.basic.logic.game.room.proto.RoomInfoPush;
import com.game.example.server.stress.testing.action.RobotRequestAction;
import org.qiunet.function.ai.enums.ActionStatus;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.response.TestResponse;
import org.qiunet.game.test.robot.Robot;

// @BehaviorAction(name = "进入房间")
public class JoinRoomAction extends RobotRequestAction {

    private boolean fail;

    public JoinRoomAction(IConditions<Robot> preConditions) {
        super(preConditions);
    }

    @Override
    protected ActionStatus execute() {
        fail = false;
        JoinPublicRoomReq req = new JoinPublicRoomReq();
        this.sendMessage(req);
        return ActionStatus.RUNNING;
    }

    @Override
    protected ActionStatus runningStatusUpdate() {
        if (this.fail) {
            return ActionStatus.FAILURE;
        }
        return (getRobotData().getRoomInfo().getRoomData() != null) ? ActionStatus.SUCCESS : ActionStatus.RUNNING;
    }

    @TestResponse
    public final void joinFail(JoinRoomFailRsp fail) {
        this.fail = true;
    }

    @TestResponse
    public final void enterRoomPush(RoomInfoPush push) {
        getRobotData().getRoomInfo().setSceneId(push.getSceneId());
        getRobotData().getRoomInfo().setRoomData(push.getRoomData());
    }
}
