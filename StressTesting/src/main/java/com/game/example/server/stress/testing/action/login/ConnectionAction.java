package com.game.example.server.stress.testing.action.login;

import com.game.example.server.stress.testing.action.RobotRequestAction;
import org.qiunet.flash.handler.context.session.ISession;
import org.qiunet.flash.handler.netty.client.param.TcpClientParams;
import org.qiunet.flash.handler.netty.server.constants.ServerConstants;
import org.qiunet.flash.handler.netty.server.message.ConnectionReq;
import org.qiunet.flash.handler.netty.server.message.ConnectionRsp;
import org.qiunet.flash.handler.netty.server.param.adapter.message.ClockTickPush;
import org.qiunet.function.ai.enums.ActionStatus;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.response.TestResponse;
import org.qiunet.game.test.robot.Robot;

// @BehaviorAction(name = "tcp连接")
public class ConnectionAction extends RobotRequestAction {

    public ConnectionAction(IConditions<Robot> preConditions) {
        super(preConditions);
    }

    @Override
    protected ActionStatus execute() {
        ISession connector = this.connector(TcpClientParams.custom()
                .setAddress(getRobotData().getHttpLoginResponse().getServerHost(), getRobotData().getHttpLoginResponse().getServerPort())
                .setEncryption(false)
                .build());
        connector.attachObj(ServerConstants.MESSAGE_ACTOR_KEY, getRobotData().getRobot());

        ConnectionReq req = new ConnectionReq();
        req.setIdKey(getRobotData().getRobot().getAccount());
        this.sendMessage(req);
        return ActionStatus.RUNNING;
    }

    @Override
    protected ActionStatus runningStatusUpdate() {
        return !getRobotData().isConnectionSuccess() ? ActionStatus.RUNNING : ActionStatus.SUCCESS;
    }

    @TestResponse
    public void connectionSuccess(ConnectionRsp pong) {
        getRobotData().setConnectionSuccess(true);
    }

    @TestResponse
    public final void clockPush(ClockTickPush clock) {

    }
}
