package com.game.example.server.stress.testing.action.room;

import com.game.example.server.stress.testing.action.login.condition.AuthCondition;
import com.game.example.server.stress.testing.action.room.condition.JoinRoomCondition;
import com.game.example.server.stress.testing.action.room.condition.QuitRoomCondition;
import org.qiunet.function.ai.node.IBehaviorNode;
import org.qiunet.function.ai.node.executor.SelectorExecutor;
import org.qiunet.game.test.bt.IBehaviorBuilder;
import org.qiunet.game.test.robot.Robot;

public class RoomBehaviorBuilder implements IBehaviorBuilder<Robot> {

    @Override
    public IBehaviorNode<Robot> buildExecutor(Robot robot) {
        SelectorExecutor<Robot> selectorExecutor = new SelectorExecutor<>(new AuthCondition());
        selectorExecutor.addChild(
                new JoinRoomAction(new JoinRoomCondition()),
                new RoomQuitAction(new QuitRoomCondition())
        );
        return selectorExecutor;
    }

}
