package com.game.example.server.stress.testing.action.login;

import com.game.example.server.stress.testing.action.login.condition.ConnectionCondition;
import com.game.example.server.stress.testing.action.login.condition.HttpLoginCondition;
import com.game.example.server.stress.testing.action.login.condition.LoginCondition;
import org.qiunet.function.ai.node.IBehaviorNode;
import org.qiunet.function.ai.node.decorator.CounterNode;
import org.qiunet.function.ai.node.executor.SequenceExecutor;
import org.qiunet.function.condition.AlwaysSuccessCondition;
import org.qiunet.function.condition.ConditionManager;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.bt.IBehaviorBuilder;
import org.qiunet.game.test.robot.Robot;

public class LoginBehaviorBuilder implements IBehaviorBuilder<Robot> {

    @Override
    public IBehaviorNode<Robot> buildExecutor(Robot robot) {
        // 登录行为
        SequenceExecutor<Robot> sequenceLogin = new SequenceExecutor<>((IConditions<Robot>) ConditionManager.EMPTY_CONDITION);
        SequenceExecutor<Robot> sequenceRegister = new SequenceExecutor<>((IConditions<Robot>) ConditionManager.EMPTY_CONDITION);

        sequenceRegister.addChild(new ConnectionAction(new ConnectionCondition()),
                new LoginAction(new LoginCondition()));

        sequenceLogin.addChild(new HttpLoginAction(new HttpLoginCondition()), sequenceRegister, new KcpConnectionAction(AlwaysSuccessCondition.getInstance()));
        return new CounterNode<>(sequenceLogin, 1);
    }
}
