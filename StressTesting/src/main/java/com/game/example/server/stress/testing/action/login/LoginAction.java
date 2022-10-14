package com.game.example.server.stress.testing.action.login;

import com.game.example.basic.logic.pack.proto.AllPackItemPush;
import com.game.example.basic.logic.player.proto.LoginReq;
import com.game.example.basic.logic.player.proto.LoginRsp;
import com.game.example.basic.logic.player.proto.PlayerDataPush;
import com.game.example.common.logger.GameLogger;
import com.game.example.server.stress.testing.action.RobotRequestAction;
import com.game.example.server.stress.testing.action.login.condition.NeedRegisterCondition;
import org.qiunet.flash.handler.common.player.proto.PlayerLogoutPush;
import org.qiunet.function.ai.enums.ActionStatus;
import org.qiunet.function.condition.ConditionManager;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.response.TestResponse;
import org.qiunet.game.test.robot.Robot;

// @BehaviorAction(name = "tcp登录")
public class LoginAction extends RobotRequestAction {

    public LoginAction(IConditions<Robot> preConditions) {
        super(preConditions);
    }

    @Override
    protected ActionStatus execute() {
        LoginReq loginReq = new LoginReq();
        loginReq.setTicket(getRobotData().getHttpLoginResponse().getTicket());
        this.sendMessage(loginReq);
        return ActionStatus.RUNNING;
    }

    @Override
    protected ActionStatus runningStatusUpdate() {
        return (getRobotData().getLoginRsp() != null) ? ActionStatus.SUCCESS : ActionStatus.RUNNING;
    }

    @TestResponse
    public final void loginResponse(LoginRsp loginRsp) {
        if (loginRsp.isNeedRegister()) {
            parent().addChild(new RandomNameAction(new NeedRegisterCondition()),
                    new RegisterAction((IConditions<Robot>) ConditionManager.EMPTY_CONDITION));
        }
        getRobotData().setLoginRsp(loginRsp);
    }

    @TestResponse
    public final void playerDataPush(PlayerDataPush playerDataPush) {
        getRobotData().getRobot().setId(playerDataPush.getPlayerTo().getObjectTo().getObjectId());
        getRobotData().setPlayerData(playerDataPush.getPlayerTo());
    }

    @TestResponse
    public final void allPackData(AllPackItemPush allPackData) {

    }

    @TestResponse
    public final void logout(PlayerLogoutPush dataPush) {
        GameLogger.COMM_LOGGER.error("Robot {} logout, cause: {}", getRobotData().getRobot().getId(), dataPush.getCause());
    }

}
