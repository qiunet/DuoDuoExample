package com.game.example.server.stress.testing.action.login.condition;

import com.game.example.server.stress.testing.action.RobotRequestCondition;
import com.game.example.server.stress.testing.robot.RobotData;
import org.qiunet.game.test.robot.Robot;

public class NeedRegisterCondition extends RobotRequestCondition {

    @Override
    protected boolean verify0(Robot robot, RobotData data) {
		// 本身有这个, 就说明是添加进来的注册节点.
        return data.getLoginRsp() == null
                && data.getRegisterRsp() == null;
    }

}
