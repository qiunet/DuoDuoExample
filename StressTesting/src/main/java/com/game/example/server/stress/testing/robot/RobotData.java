package com.game.example.server.stress.testing.robot;

import com.game.example.basic.http.response.LoginResponse;
import com.game.example.basic.logic.player.proto.LoginRsp;
import com.game.example.basic.logic.player.proto.PlayerTo;
import com.game.example.basic.logic.player.proto.RandomNameRsp;
import com.game.example.basic.logic.player.proto.RegisterRsp;
import org.qiunet.flash.handler.netty.server.kcp.shakehands.message.KcpTokenRsp;
import org.qiunet.game.test.robot.Robot;
import org.qiunet.utils.args.ArgumentKey;
import org.qiunet.utils.args.IArgsContainer;

public class RobotData {

    private static final ArgumentKey<RobotData> robotData = new ArgumentKey();

    private final Robot robot;

    private KcpTokenRsp kcpTokenRsp;

    private boolean connectionSuccess;

    private LoginResponse httpLoginResponse;

    private LoginRsp loginRsp;

    private RandomNameRsp randomNameRsp;

    private RegisterRsp registerRsp;

    private PlayerTo playerData;

    private RoomInfo roomInfo = new RoomInfo();

    private PackData pack = new PackData();

    public RobotData(Robot robot) {
        robot.setVal(robotData, this);
        this.robot = robot;
    }

    public static RobotData get(Robot robot) {
        return RobotData.robotData.computeIfAbsent((IArgsContainer)robot, () -> new RobotData(robot));
    }

    public Robot getRobot() {
        return robot;
    }

    public KcpTokenRsp getKcpTokenRsp() {
        return kcpTokenRsp;
    }

    public void setKcpTokenRsp(KcpTokenRsp kcpTokenRsp) {
        this.kcpTokenRsp = kcpTokenRsp;
    }

    public boolean isConnectionSuccess() {
        return connectionSuccess;
    }

    public void setConnectionSuccess(boolean connectionSuccess) {
        this.connectionSuccess = connectionSuccess;
    }

    public LoginResponse getHttpLoginResponse() {
        return httpLoginResponse;
    }

    public void setHttpLoginResponse(LoginResponse httpLoginResponse) {
        this.httpLoginResponse = httpLoginResponse;
    }

    public LoginRsp getLoginRsp() {
        return loginRsp;
    }

    public void setLoginRsp(LoginRsp loginRsp) {
        this.loginRsp = loginRsp;
    }

    public RandomNameRsp getRandomNameRsp() {
        return randomNameRsp;
    }

    public void setRandomNameRsp(RandomNameRsp randomNameRsp) {
        this.randomNameRsp = randomNameRsp;
    }

    public RegisterRsp getRegisterRsp() {
        return registerRsp;
    }

    public void setRegisterRsp(RegisterRsp registerRsp) {
        this.registerRsp = registerRsp;
    }

    public PlayerTo getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerTo playerData) {
        this.playerData = playerData;
    }

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }
}
