package com.game.example.server.stress.testing.action.login;

import com.game.example.common.logger.GameLogger;
import com.game.example.server.stress.testing.TestingConstant;
import com.game.example.server.stress.testing.action.RobotRequestAction;
import org.qiunet.flash.handler.context.session.ISession;
import org.qiunet.flash.handler.netty.client.param.KcpClientParams;
import org.qiunet.flash.handler.netty.server.kcp.shakehands.message.KcpBindAuthReq;
import org.qiunet.flash.handler.netty.server.kcp.shakehands.message.KcpBindAuthRsp;
import org.qiunet.flash.handler.netty.server.kcp.shakehands.message.KcpTokenReq;
import org.qiunet.flash.handler.netty.server.kcp.shakehands.message.KcpTokenRsp;
import org.qiunet.function.ai.enums.ActionStatus;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.response.TestResponse;
import org.qiunet.game.test.robot.Robot;

import java.util.concurrent.TimeUnit;

// @BehaviorAction(name = "KCP连接")
public class KcpConnectionAction extends RobotRequestAction {

    private boolean bindSuccess;

    public KcpConnectionAction(IConditions<Robot> preConditions) {
        super(preConditions);
    }

    @Override
    protected ActionStatus execute() {
        getOwner().getConnector("GameServerTcp").sendMessage(new KcpTokenReq());
        return ActionStatus.RUNNING;
    }

    @Override
    protected ActionStatus runningStatusUpdate() {
        return this.bindSuccess ? ActionStatus.SUCCESS : ActionStatus.RUNNING;
    }

    @TestResponse
    public final void bindSuccess(KcpBindAuthRsp bind) {
        this.bindSuccess = bind.isSuccess();
    }

    @TestResponse
    public final void kcpTokenRsp(KcpTokenRsp rsp) {
        getRobotData().setKcpTokenRsp(rsp);
        ISession connector = this.connector(KcpClientParams.custom()
                .setAddress(getRobotData().getHttpLoginResponse().getServerHost(), rsp.getPort())
                .setConvId(rsp.getConvId())
                .setEncryption(false).build());
        this.addCloseListener(connector);
        this.kcpBind();
    }

    private void addCloseListener(ISession connector) {
        connector.addCloseListener("Reconnect", (session, cause) -> {
            getRobotData().getRobot().scheduleMessage(robot -> {
                ISession reconnect = robot.reconnect(TestingConstant.GAME_KCP_CONNECTOR_NAME);
                this.addCloseListener(reconnect);
                this.kcpBind();
            }, 100, TimeUnit.MILLISECONDS);
        });
    }

    private final void kcpBind() {
        KcpBindAuthReq req = new KcpBindAuthReq();
        req.setPlayerId(getRobotData().getPlayerData().getObjectTo().getObjectId());
        req.setToken(getRobotData().getKcpTokenRsp().getToken());
        sendMessage(req);
        GameLogger.COMM_LOGGER.info("Kcp connected");
    }
}
