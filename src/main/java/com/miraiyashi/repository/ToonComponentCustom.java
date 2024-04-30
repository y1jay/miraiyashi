package com.miraiyashi.repository;

import com.miraiyashi.entity.ToonComponent;

import java.util.List;

public interface ToonComponentCustom {
    List<ToonComponent> list(Integer tcIdx,Integer page);
}
