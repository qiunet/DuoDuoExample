package com.game.example.server.stress.testing.action;

import com.game.example.server.stress.testing.TestingConstant;
import com.game.example.server.stress.testing.robot.RobotData;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.robot.Robot;
import org.qiunet.game.test.robot.action.BaseRobotAction;

public abstract class RobotRequestAction extends BaseRobotAction {

    public RobotRequestAction(IConditions<Robot> preConditions) {
        super(preConditions, TestingConstant.GAME_TCP_CONNECTOR_NAME);
    }

    public RobotRequestAction(IConditions<Robot> preConditions, String connectorName) {
        super(preConditions, connectorName);
    }

    public RobotData getRobotData() {
        return RobotData.get(getOwner());
    }

}
