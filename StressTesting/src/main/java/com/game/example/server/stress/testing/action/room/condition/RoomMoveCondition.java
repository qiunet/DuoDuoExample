package com.game.example.server.stress.testing.action.room.condition;

import com.game.example.server.stress.testing.action.RobotRequestCondition;
import com.game.example.server.stress.testing.robot.RobotData;
import com.game.example.server.stress.testing.robot.RoomInfo;
import org.qiunet.game.test.robot.Robot;
import org.qiunet.utils.math.MathUtil;

public class RoomMoveCondition extends RobotRequestCondition {

    @Override
    protected boolean verify0(Robot robot, RobotData data) {
        if (data.getRoomInfo().getRoomData() == null)
            return false;
        if (data.getRoomInfo().getMoveCount() < RoomInfo.getNeedMoveCount())
            return true;
        return MathUtil.isHit(8000);
    }

}
