package com.game.example.common.data;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

@ProtobufClass(description = "虚拟形象数据")
public class AvatarInfo {
	@Protobuf(description = "角色ID")
	private Integer id;
	@Protobuf(description = "捏脸数据oss地址")
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static AvatarInfo valueOf(Integer id, String path){
		AvatarInfo info = new AvatarInfo();
		info.setId(id);
		info.setPath(path);
		return info;
	}
}
