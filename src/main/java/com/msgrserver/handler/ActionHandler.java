package com.msgrserver.handler;

import com.msgrserver.action.ActionRequest;
import com.msgrserver.action.ActionResult;

public interface ActionHandler {
    ActionResult handle(ActionRequest request);
}
