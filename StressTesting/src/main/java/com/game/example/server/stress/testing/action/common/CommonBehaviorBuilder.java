package com.game.example.server.stress.testing.action.common;

import com.game.example.server.stress.testing.action.login.condition.AuthCondition;
import org.qiunet.function.ai.node.IBehaviorNode;
import org.qiunet.function.ai.node.executor.RandomExecutor;
import org.qiunet.game.test.bt.IBehaviorBuilder;
import org.qiunet.game.test.robot.Robot;

public class CommonBehaviorBuilder implements IBehaviorBuilder<Robot> {

    @Override
    public IBehaviorNode<Robot> buildExecutor(Robot robot) {
        return new RandomExecutor<>(new AuthCondition())
                .addChild(new HeartBeatAction(new AuthCondition()));
    }
}
