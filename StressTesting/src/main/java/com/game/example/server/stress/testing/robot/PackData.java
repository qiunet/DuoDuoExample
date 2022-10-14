package com.game.example.server.stress.testing.robot;

import com.game.example.basic.logic.pack.proto.PackItemTo;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class PackData {
    private Map<Integer, PackItemTo> data = Maps.newConcurrentMap();

    public final void init(List<PackItemTo> list) {
        list.forEach(i -> data.put(i.getUid(), i));
    }

    public final PackItemTo get(int uid) {
        return this.data.get(uid);
    }
}
