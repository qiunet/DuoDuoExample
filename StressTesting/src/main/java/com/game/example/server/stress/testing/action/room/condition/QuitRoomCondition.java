package com.game.example.server.stress.testing.action.room.condition;

import com.game.example.server.stress.testing.action.RobotRequestCondition;
import com.game.example.server.stress.testing.robot.RobotData;
import com.game.example.server.stress.testing.robot.RoomInfo;
import org.qiunet.game.test.robot.Robot;

public class QuitRoomCondition extends RobotRequestCondition {
    @Override
    protected boolean verify0(Robot robot, RobotData data) {
        return (data.getRoomInfo().getRoomData() != null &&
                data.getRoomInfo().getMoveCount() > RoomInfo.getNeedMoveCount());
    }
}
