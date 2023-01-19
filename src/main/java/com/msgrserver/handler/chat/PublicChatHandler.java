package com.msgrserver.handler.chat;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.chat.*;

public interface PublicChatHandler {
    ActionResult joinChatWithLink(Long userId, PublicChatJoinWithLinkRequestDto dto);

    ActionResult leavePublicChat(Long userId, PublicChatLeaveRequestDto dto);

    ActionResult selectNewAdminPublicChat(Long selectorId, PublicChatSelectNewAdminRequestDto dto);

    ActionResult deleteAdminPublicChat(Long selectorId, PublicChatDeleteAdminRequestDto dto);

    ActionResult addMembersToPublicChat(Long adminId, PublicChatAddMembersRequestDto dto);

    ActionResult deleteUserFromPublicChat(Long deleterId, PublicChatDeleteMemberRequestDto dto);

    ActionResult editProfilePublicChat(Long userId, PublicChatEditProfileRequestDto dto);

    ActionResult createPublicChat(Long creatorId, PublicChatCreateRequestDto dto);

    ActionResult getPublicChatMembers(Long userId, PublicChatGetMembersRequestDto dto);
}
