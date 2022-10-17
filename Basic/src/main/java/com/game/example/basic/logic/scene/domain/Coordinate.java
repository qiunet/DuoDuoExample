package com.game.example.basic.logic.scene.domain;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import org.qiunet.flash.handler.util.proto.CommonModuleProto;

@CommonModuleProto
@ProtobufClass(description = "3d 坐标以及朝向")
public class Coordinate {
    @Protobuf(description = "x")
    private float x;

    @Protobuf(description = "y")
    private float y;

    @Protobuf(description = "朝向")
    private float dir;

    public Coordinate() {
    }

    public Coordinate(float x, float y, float dir) {
        this.set(x, y, dir);
    }

    public void set(float x, float y, float dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    /**
     * 以当前坐标(x,y) 为原点. 以给定半径. 随机一个坐标
     */
    public Coordinate rand(float radius) {
        double d = radius * Math.sqrt(Math.random());
        double theta = Math.random() * 2 * Math.PI;

        return new Coordinate((float) (d * Math.cos(theta) + getX()), (float) (d * Math.sin(theta) + getY()), dir);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDir() {
        return dir;
    }

    public void setDir(float dir) {
        this.dir = dir;
    }
}
