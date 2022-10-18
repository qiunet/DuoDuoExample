package com.game.example.server.stress.testing.action.room;

import com.game.example.basic.logic.scene.proto.ScenePositionReq;
import com.game.example.basic.logic.scene.proto.ScenePositionRsp;
import com.game.example.server.stress.testing.TestingConstant;
import com.game.example.server.stress.testing.action.RobotRequestAction;
import com.game.example.server.stress.testing.robot.RoomInfo;
import org.qiunet.flash.handler.context.request.data.IChannelData;
import org.qiunet.function.ai.enums.ActionStatus;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.response.TestResponse;
import org.qiunet.game.test.robot.Robot;

import java.util.concurrent.TimeUnit;

public class RoomMoveAction extends RobotRequestAction {

    public RoomMoveAction(IConditions<Robot> preConditions) {
        super(preConditions, TestingConstant.GAME_KCP_CONNECTOR_NAME);
    }

    @Override
    protected ActionStatus execute() {
        for (int b = 0; b < 30; b++) {
            // 每60毫秒发一个包
            getRobotData().getRobot().scheduleMessage(h -> this.sendRequest(), b * 60L, TimeUnit.MILLISECONDS);
        }
        RoomInfo roomInfo = getRobotData().getRoomInfo();
        int i = roomInfo.getMoveCount();
        roomInfo.setMoveCount(i + 1);
        return ActionStatus.SUCCESS;
    }

    private final void sendRequest() {
        ScenePositionReq req = new ScenePositionReq();
        req.setX(1);
        req.setY(1);
        sendKcpMessage((IChannelData)req, true);
    }

    @TestResponse
    public final void moveRsp(ScenePositionRsp rsp) {

    }
}
