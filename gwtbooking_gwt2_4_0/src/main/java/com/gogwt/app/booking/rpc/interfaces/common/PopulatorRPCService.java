package com.gogwt.app.booking.rpc.interfaces.common;

import java.util.List;
import java.util.Map;

import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;
import com.google.gwt.user.client.rpc.RemoteService;

public interface PopulatorRPCService extends RemoteService {
	Map<String, List<PopulatorItem>> getPopulatorInServerSide();
}
