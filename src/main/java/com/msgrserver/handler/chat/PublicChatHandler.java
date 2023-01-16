package com.msgrserver.handler.chat;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.chat.*;

public interface PublicChatHandler {
    ActionResult joinChatWithLink(PublicChatJoinWithLinkRequestDto dto);

    ActionResult leavePublicChat(PublicChatLeaveRequestDto dto);

    ActionResult deletePublicChat(PublicChatDeleteRequestDto dto);

    ActionResult selectNewAdminPublicChat(PublicChatSelectNewAdminRequestDto dto);

    ActionResult deleteAdminPublicChat(PublicChatDeleteAdminRequestDto dto);

    ActionResult addUserToPublicChat(Long adminId, PublicChatAddUserRequestDto dto);

    ActionResult deleteUserFromPublicChat(Long deleterId, PublicChatDeleteUserRequestDto dto);

    ActionResult editProfilePublicChat(PublicChatEditProfileRequestDto dto);
}
